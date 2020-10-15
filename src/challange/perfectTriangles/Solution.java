package challange.perfectTriangles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * See the problem here: https://www.hackerrank.com/contests/projecteuler/challenges/euler218/problem
 * 
 * THIS PROBLEM HAS 0 SOLUTIONS.
 * 
 * Run it to see the explanation (NOT FINISHED).
 * 
 * @author gilles
 *
 */
public class Solution {

	private static final int[] PERFECT_NUMBERS = {6, 28};
	private static final String SQRD = "\u00B2";
	private static final String E = "\u2208";
	
	
	private static final List<String> CONDITIONS = new ArrayList<String>(
														Arrays.asList("(I)\t a"+SQRD+" + b"+SQRD+" = c"+SQRD,
																	  "(II)\t gcd(a,b) = 1",
																	  "(III)\t gcd(b,c) = 1",
																	  "(IV)\t c = d"+SQRD+", d "+E+" N",
																	  "(V)\t !(a*b = 12*k & a*b = 56*k), k "+E+" N"));
	private static final String HERONIAN_TRIANGLE_PROPERTY = "(H) !(isOdd(a) & isOdd(b))";
	private static final List<String> EUCLID_FORMULAS = new ArrayList<String>(
															Arrays.asList("(E_I)\t a = k * (m"+SQRD+" - n"+SQRD+")",
																		  "(E_II)\t b = 2 * k * m * n",
																		  "(E_III)\t c = k * (m"+SQRD+" + n"+SQRD+")"));
	private static final List<String> EUCLID_FORMULAS_CONDITIONS = new ArrayList<String>(
																		Arrays.asList("(VI)\t m,n,k "+E+" N",
																					  "(VII)\t m > n",
																					  "(VIII)\t gcd(m,n) = 1",
																					  "(IX)\t !(isOdd(m) & isOdd(n))"));
	private static final List<String> MISC = new ArrayList<String>(
													Arrays.asList("(X)\t if isOdd(x) then isOdd(x"+SQRD+")",
																  "(XI)\t if isEven(x) then isEven(x"+SQRD+")"));
	public static void main(String[] args) {
		System.out.println("Pythagorean triplet (a, b, c) that form sides a perfect right \n"
						+ "angle triangle (i.e. perfect RAT) that is not super perfect, satisfies \n"
						+ "these conditions:");
		CONDITIONS.forEach(x -> System.out.println(x));
		System.out.println("\n---------------------------------------");
		System.out.println("Used definitions:");
		System.out.println("* HERONIAN TRIANGLES:");
		System.out.println("Triangles based on pythagorean triplates have INTEGER area \n"
						+ "as well as integer sides (i.e. a*b % 2 == 0). At least one \n"
						+ "a or b, needs to be EVEN.");
		System.out.println(HERONIAN_TRIANGLE_PROPERTY);
		System.out.println();
		
		System.out.println("* EUCLIDS FORMULA FOR GENERATING PYTHAGOREAN TRIPLETS:");
		EUCLID_FORMULAS.forEach(x -> System.out.println(x));
		System.out.println("where");
		EUCLID_FORMULAS_CONDITIONS.forEach(x -> System.out.println(x));
		System.out.println();
		
		System.out.println("* MISC:");
		
		System.out.println("\n---------------------------------------");
		
	}

//	private static int executeSubTest(int nan) {
//		
//		// generate all c's that are PERFECT SQUARES -> condition (IV)
//		Set<Integer> cs = new TreeSet<>();
//		int sqrtOfN = (int) Math.sqrt(nan);
//		int dSquared = 0;
//		for(int d = 5; d <= sqrtOfN; d++) {
//			dSquared = d*d;
//			if(isOdd(dSquared))
//				cs.add(dSquared);
//		}
////		System.out.println(cs.size());
//		int k = 0;
//		int a = 0;
//		int b = 0;
//		int ab = 0;
//		for(Integer c : cs) {
//			int sqrtOfC = (int) Math.sqrt(c);
//			for(int m = 2; m < sqrtOfC; m++) {
//				for(int n = 1; n < m; n++) {
//					if(c % (m*m+n*n) == 0) { // generate Pythagorean triplets -> condition(I)
//						k = c / (m*m+n*n);
//						a = k*(m*m-n*n);
//						b = k*2*m*n;
//						ab = a*b;
//						if(isDivisibleByPerfectNumbers(ab/2) &&	// condition (VI)
//						   gcd(a,b) == 1 && 					// condition (II)
//						   gcd(b,c) == 1) {						// condition (III)
//							System.out.println("a="+k*(m*m-n*n));
//							System.out.println("b="+k*2*m*n);
//							System.out.println("c="+c+" (d="+sqrtOfC+")");
//						}
//						else {
//							System.err.println("ERR");
//						}
////						System.out.println("a="+k*(m*m-n*n));
////						System.out.println("b="+k*2*m*n);
////						System.out.println("c="+c);
////						System.out.println("k="+k);
////						System.out.println("m="+m);
////						System.out.println("n="+n);
//					}
//				}
//			}
//		}
//		return 0;
//	}
////	
////	private static boolean isPerfectSquare(int n) {
////		return Math.sqrt((double) n) - Math.floor(Math.sqrt((double) n)) == 0;
////	}
////	
//	private static boolean isDivisibleByPerfectNumbers(int n) {
//		return n % PERFECT_NUMBERS[0] == 0 && n % PERFECT_NUMBERS[1] == 0;
//	}
////
//	private static boolean isOdd(int x) {
//		return x % 2 != 0;
//	}
//	private static int gcd(int a, int b) {
//		TreeSet<Integer> commonDivisors = getDivisors(a);
//		commonDivisors.retainAll(getDivisors(b));
//		return commonDivisors.last();
//	}
//	
//	private static TreeSet<Integer> getDivisors(int a) {
//		TreeSet<Integer> divisors = new TreeSet<Integer>();
//		for(int i = 1; i <= a; i++) {
//			if(a % i == 0)
//				divisors.add(i);
//		}
//		return divisors;
//	}
}
