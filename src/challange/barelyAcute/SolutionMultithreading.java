package challange.barelyAcute;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * See the problem here: https://www.hackerrank.com/contests/projecteuler/challenges/euler223/problem
 * @author gilles
 *
 */
public class SolutionMultithreading {

	private static final int[][] MATRIX_A = {{1, -2, 2},
											 {2, -1, 2},
											 {2, -2, 3}};
	
	private static final int[][] MATRIX_B = {{1, 2, 2},
											 {2, 1, 2},
											 {2, 2, 3}};
	
	private static final int[][] MATRIX_C = {{-1, 2, 2},
											 {-2, 1, 2},
											 {-2, 2, 3}};
	
	private static final int[] ROOT_1 = {1, 1, 1};
	private static final int[] ROOT_2 = {1, 2, 2};
	
	private static Set<int[]> ALL_SOLUTIONS = Collections.synchronizedSet(new TreeSet<>(new Comparator<int[]>() {

		@Override
		public int compare(int[] o1, int[] o2) {
			int r = getPerimiter(o1) - getPerimiter(o2);
			if(r == 0) {
				r = o1[0] - o2[0];
				if(r == 0) {
					r = o1[1] - o2[1];
					if(r == 0) {
						r = o1[2] - o2[2];
					}
				}
			}
			return r;
		}
	}));
	private static final List<int[]> ROOTS = Arrays.asList(new int[]{1,3,3},
														   new int[]{5,5,7},
														   new int[]{1,4,4},
														   new int[]{8,9,12},
														   new int[]{4,7,8});
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, ExecutionException {
		File file = new File("./barelyAcuteSamples/sample.txt");
		Scanner sc = new Scanner(file);
		sc.next(); // skip q
		
		List<Integer> listOfNs = new ArrayList<>();
		sc.forEachRemaining(x -> listOfNs.add(Integer.parseInt(x)));
		int maxN = Collections.max(listOfNs);
		
		ALL_SOLUTIONS.add(ROOT_1);
		ALL_SOLUTIONS.add(ROOT_2);
		
		// generate all triplets till maxN
		Instant start = Instant.now();
		allSolutions(maxN);
		Instant finish = Instant.now();
		int counter = 0;
		for(Integer N : listOfNs) {
			if(N == maxN)
				System.out.println(ALL_SOLUTIONS.size());
			else {
				counter = 0;
				for(int[] t : ALL_SOLUTIONS) {
					if(getPerimiter(t) > N)
						break;
					counter++;
				}
				System.out.println(counter);
			}
		}
		 
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println(timeElapsed);
		
		sc.close();
	}
	
	private static boolean isPerimiterWithinLimits(int[] triplet, int N) {
		return getPerimiter(triplet) <= N;
	}
	
	private static int[] matrixDotTriplet(int[][] matrix, int[] triplet) {
		return new int[]{(matrix[0][0] * triplet[0] + matrix[0][1] * triplet[1] + matrix[0][2] * triplet[2]),
						 (matrix[1][0] * triplet[0] + matrix[1][1] * triplet[1] + matrix[1][2] * triplet[2]),
						 (matrix[2][0] * triplet[0] + matrix[2][1] * triplet[1] + matrix[2][2] * triplet[2])};
	}
	
	private static boolean isTriangle(int[] triplet) {
		return triplet[0] > 0 && triplet[1] > 0 && triplet[2] > 0;
	}
	
	private static int getPerimiter(int[] triplet) {
		return triplet[0] + triplet[1] + triplet[2];
	}
	
	private static void swapAB(int[] triplet) {
		triplet[0] = triplet[0] + triplet[1];
		triplet[1] = triplet[0] - triplet[1];
		triplet[0] = triplet[0] - triplet[1];
	}
	
	private static int execute(int[] root, int N) {
		List<int[]> candidateTriplets = new ArrayList<int[]>();
		int[] triplet = null;
		candidateTriplets.add(root);
		for(int currIndex = 0; currIndex < candidateTriplets.size(); currIndex++) {
			triplet = candidateTriplets.get(currIndex);
			if(isTriangle(triplet) && isPerimiterWithinLimits(triplet, N)) {
				if(triplet[0] > triplet[1]) {
					swapAB(triplet);
				}
				if(ALL_SOLUTIONS.add(triplet)) {
					candidateTriplets.add(matrixDotTriplet(MATRIX_A, triplet));
					candidateTriplets.add(matrixDotTriplet(MATRIX_B, triplet));
					candidateTriplets.add(matrixDotTriplet(MATRIX_C, triplet));
				}
			}
		}
		return 0;
	}
	
	public static void allSolutions(final int N) throws InterruptedException, ExecutionException {
 
        int threadNum = ROOTS.size();
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();

        // create all tasks using initial roots
        for(int[] r : ROOTS) {
        	taskList.add(new FutureTask<Integer>(new Callable<Integer>() {
	            @Override
	            public Integer call() {
	                return execute(r, N);
	            }
	        }));
        }
 
        // start all threads
        taskList.forEach(task -> executor.execute(task));
        
        // Wait until all results are available and combine them at the same time
        for(FutureTask<Integer> task : taskList) {
        	task.get();
        }
        executor.shutdown();
 
        return;
    }
}
