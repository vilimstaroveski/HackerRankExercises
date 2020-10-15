package challange.barelyAcute;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

/**
 * See the problem here: https://www.hackerrank.com/contests/projecteuler/challenges/euler223/problem
 * @author gilles
 *
 */
public class Solution {

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
	
	private static final Comparator<int[]> TRIPLET_COMPARATOR = new Comparator<int[]>() {

		@Override
		public int compare(int[] o1, int[] o2) {
			int r = getPerimiter(o1) - getPerimiter(o2);
			if(r == 0) {
				r = o1[2] - o2[2];
				if(r == 0) {
					r = o1[1] - o2[1];
					if(r == 0) {
						r = o1[0] - o2[0];
					}
				}
			}
			return r;
		}
	};
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, ExecutionException {
		File file = new File("./barelyAcuteSamples/sample.txt");
		Scanner sc = new Scanner(file);
		sc.next(); // skip q
		
		List<Integer> listOfNs = new ArrayList<>();
		sc.forEachRemaining(x -> listOfNs.add(Integer.parseInt(x)));
		int maxN = Collections.max(listOfNs);
		
		// generate all triplets till maxN
		Instant start = Instant.now();
		Set<int[]> solutions = execute(maxN);
		Instant finish = Instant.now();

		int counter = 0;
		for(Integer N : listOfNs) {
			if(N == maxN)
				System.out.println(solutions.size());
			else {
				counter = 0;
				for(int[] t : solutions) {
					if(getPerimiter(t) > N)
						break;
					counter++;
				}
				System.out.println(counter);
			}
		}
		 
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Execution time:"+timeElapsed);
		
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
	
	private static int getPerimiter(int[] triplet) {
		return triplet[0] + triplet[1] + triplet[2];
	}
	
	private static Set<int[]> execute(int N) {
		List<int[]> candidateTriplets = new ArrayList<int[]>();
		Set<int[]> solutions = new TreeSet<>(TRIPLET_COMPARATOR);
		int[] triplet = null;
		candidateTriplets.add(ROOT_1);
		candidateTriplets.add(ROOT_2);
		int currIndex = 0;
		while(true) {
			try {
				triplet = candidateTriplets.get(currIndex++);
			} catch (IndexOutOfBoundsException e) {
				return solutions;
			}
			if(isPerimiterWithinLimits(triplet, N)) {
				if(solutions.add(triplet)) {
					candidateTriplets.add(matrixDotTriplet(MATRIX_A, triplet));
					candidateTriplets.add(matrixDotTriplet(MATRIX_B, triplet));
					if(triplet[0] != triplet[1])
						candidateTriplets.add(matrixDotTriplet(MATRIX_C, triplet));
				}
			}
		}
	}
}
