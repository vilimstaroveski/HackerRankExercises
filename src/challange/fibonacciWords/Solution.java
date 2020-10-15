package challange.fibonacciWords;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * See the problem here: https://www.hackerrank.com/contests/projecteuler/challenges/euler230/problem
 * @author gilles
 *
 */
public class Solution {
	
	private final static Pattern PATTERN = Pattern.compile("(.+)\\s(.+)\\s(\\d+)");
	
	private static class SubTest {
		String a;
		String b;
		BigInteger n;
		public SubTest(String a, String b, BigInteger n) {
			this.a = a;
			this.b = b;
			this.n = n;
		}
		@Override
		public String toString() {
			return "a="+a+"\nb="+b+"\nn="+n+"\n";
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./sample0.txt");
		Scanner sc = new Scanner(file);
		sc.nextLine(); // skipping 'q' - not needed
		
		// creating subtests
		List<SubTest> subtests = new ArrayList<>();
		Matcher matcher = null;
		while (sc.hasNextLine()) {
			matcher = PATTERN.matcher(sc.nextLine());
			if(matcher.find()) {
				subtests.add(new SubTest(matcher.group(1), // a
										 matcher.group(2), // b
										 new BigInteger(matcher.group(3)))); // n
			}
		}
		
		// execute subtests
		for(SubTest st : subtests) {
			System.out.println(executeSubTest(st));
		}
		
		sc.close();
	}
	
	private static char executeSubTest(SubTest st) {
		List<BigInteger> fibElementLengths = new ArrayList<>();
		fibElementLengths.add(BigInteger.valueOf(st.a.length()));
		fibElementLengths.add(BigInteger.valueOf(st.b.length()));
		
		while(st.n.compareTo(fibElementLengths.get(fibElementLengths.size()-1)) >= 0) {
			fibElementLengths.add(generateNextFibElement(fibElementLengths));
		}
		int i = fibElementLengths.size() - 3; // point to the third from the top
		BigInteger curr = null;
		while(i >= 0) {
			curr = fibElementLengths.get(i);
			if(st.n.compareTo(curr) == 1) {
				st.n = st.n.subtract(curr);
				i--;
			}
			else {
				if(i == 0) return st.a.charAt(st.n.intValue() - 1);
				if(i == 1) return st.b.charAt(st.n.intValue() - 1);
				i -= 2;
			}
		}
		return st.b.charAt(st.n.intValue() - 1);
	}
	
	private static BigInteger generateNextFibElement(List<BigInteger> sequence) {
		return sequence.get(sequence.size()-2).add(sequence.get(sequence.size()-1));
	}
}
