import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

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
	
	private static class Triplet {
		int a;
		int b;
		int c;
		public Triplet(int[] t) {
			super();
			this.a = Math.min(t[0], t[1]);
			this.b = Math.max(t[0], t[1]);
			this.c = t[2];
		}
		@Override
		public boolean equals(Object obj) {
	        if (obj == this) return true; 
	        if (!(obj instanceof Triplet)) return false;
	        Triplet t = (Triplet) obj; 
			return (this.a == t.a && this.b == t.b && this.c == t.c) || 
				   (this.a == t.b && this.b == t.a && this.c == t.c);
		}
		@Override
		public int hashCode() {
			int result = 17;
	        result = 31 * result + this.toString().hashCode();
	        return result;
		}
		public int getPerimiter() {
			return a+b+c;
		}
		@Override
		public String toString() {
			return a+","+b+","+c;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int q = sc.nextInt();
		List<Integer> listOfNs = new ArrayList<>();
		sc.forEachRemaining(x -> listOfNs.add(Integer.parseInt(x)));
		int maxN = Collections.max(listOfNs);
		// generate all triplets till maxN
		Set<Triplet> allTriplets = execute(maxN);
		int counter = 0;
		for(Integer N : listOfNs) {
			if(N == maxN)
				System.out.println(allTriplets.size());
			else {
				counter = 0;
				for(Triplet t : allTriplets) {
					if(t.getPerimiter() <= N)
						counter++;
				}
				System.out.println(counter);
			}
		}
		
		sc.close();
	}
	
	private static boolean isPerimiterWithinLimits(int[] triplet, int N) {
		int perimiter = (triplet[0] + triplet[1] + triplet[2]);
		return 3 <= perimiter && perimiter <= N;
	}
	
	private static int[] matrixDotTriplet(int[][] matrix, int[] triplet) {
		return new int[]{(matrix[0][0] * triplet[0] + matrix[0][1] * triplet[1] + matrix[0][2] * triplet[2]),
						 (matrix[1][0] * triplet[0] + matrix[1][1] * triplet[1] + matrix[1][2] * triplet[2]),
						 (matrix[2][0] * triplet[0] + matrix[2][1] * triplet[1] + matrix[2][2] * triplet[2])};
	}
	
	private static boolean isTriangle(int[] triplet) {
		return triplet[0] > 0 && triplet[1] > 0 && triplet[2] > 0;
	}
	
	private static Set<Triplet> execute(int N) {
		Set<Triplet> solutions = new HashSet<>();
		List<int[]> candidateTriplets = new ArrayList<int[]>();
		int[] triplet = null;
		candidateTriplets.add(ROOT_1);
		candidateTriplets.add(ROOT_2);
		for(int currIndex = 0; currIndex < candidateTriplets.size(); currIndex++) {
			triplet = candidateTriplets.get(currIndex);
			if(isTriangle(triplet) && isPerimiterWithinLimits(triplet, N)) {
				if(solutions.add(new Triplet(triplet))) {
					candidateTriplets.add(matrixDotTriplet(MATRIX_A, triplet));
					candidateTriplets.add(matrixDotTriplet(MATRIX_B, triplet));
					candidateTriplets.add(matrixDotTriplet(MATRIX_C, triplet));
				}
			}
		}
		return solutions;
	}
}
