package SC231_StackSortStuff;

import java.util.*;
import java.io.*;

public class Statistical_a_n_Estimation {
	
	public static final double conf95CI = 1.966;
	public static final double conf99CI = 2.588;

	public static void main(String[] args) throws IOException {
		// Console output is a "loading bar" for the code, file output is the actual output
		PrintWriter pw = new PrintWriter("stuffAgain/SC231_StackSortStuff/statsOutputv3"); // File path for output
		double[][] data = new double[1001][400];
		for (int length = 15; length <= 1000; length++) { // Runs from length 15 to 1000 inclusive in this case
			System.out.println(length);
			for (int t = 0; t < 400; t++) { // t for trial number
				int[] temp = randPerm(length);
				while (vshaped(temp) == false) {
					temp = SC231(temp);
					data[length][t]++;
				}
			}
		}
		double[] means = new double[1001];
		double[] stdevs = new double[1001];
		double[] lower_bounds = new double[1001];
		double[] upper_bounds = new double[1001];
		for (int i = 15; i <= 1000; i++) {
			double sum = 0;
			for (int j = 0; j < 400; j++) {
				sum += data[i][j];
			}
			means[i] = sum/400;
			double variance = 0;
			for (int j = 0; j < 400; j++) {
				variance += (data[i][j] - means[i])*(data[i][j] - means[i]);
			}
			stdevs[i] = Math.sqrt(variance/399);
			lower_bounds[i] = means[i] - conf99CI*stdevs[i]/Math.sqrt(399);
			upper_bounds[i] = means[i] + conf99CI*stdevs[i]/Math.sqrt(399);
		}
		// Output
		for (int i = 0; i < data.length; i++) {
			pw.print(i + ",");
		}
		pw.println();
		for (int i = 0; i < data.length; i++) {
			pw.print(means[i] + ",");
		}
		pw.println();
		for (int i = 0; i < data.length; i++) {
			pw.print(stdevs[i] + ",");
		}
		pw.println();
		for (int i = 0; i < data.length; i++) {
			pw.print(lower_bounds[i] + ",");
		}
		pw.println();
		for (int i = 0; i < data.length; i++) {
			pw.print(upper_bounds[i] + ",");
		}
		pw.println();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				pw.print(data[i][j] + ",");
			}
			pw.println();
		}
		pw.close();
	}
	
	public static boolean vshaped(int[] perm) {
		boolean uphill = false;
		for (int i = 0; i < perm.length-1; i++) {
			if (perm[i+1] > perm[i]) {
				uphill = true;
			} else {
				if (uphill == true) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static int[] SC231(int[] perm) {
		// The expected number of pre-pops is likely O(n).
		int[] ans = new int[perm.length];
		int counter = 0; // Keeps track of output array's index.
		boolean[] prePopped = new boolean[perm.length]; // Keeps track which entries are pre-popped.
		
		// Basically whenever you see 132 consecutive pattern, you pre-pop the 3.
		int theOne = perm[0];
		for (int i = 1; i < perm.length-1; i++) {
			// If not a 132 consecutive pattern.
			if (perm[i] < theOne || perm[i+1] < theOne || perm[i] < perm[i+1]) {
				theOne = perm[i];
			} else {
				prePopped[i] = true;
				ans[counter] = perm[i];
				counter++;
			}
		}
		// Then you pop the remaining entries in the stack.
		for (int i = perm.length-1; i >= 0; i--) {
			if (prePopped[i] == false) {
				ans[counter] = perm[i];
				counter++;
			}
		}
		
		return ans;
	}
	
	public static int[] randPerm(int length) {
		int[] perm = new int[length];
		for (int i = 0; i < length; i++) {
			perm[i] = i+1;
		}
		for (int i = length-1; i >= 1; i--) {
			int swapIndex = (int) (Math.random()*(i+1));
			int temp = perm[i];
			perm[i] = perm[swapIndex];
			perm[swapIndex] = temp;
		}
		return perm;
	}
}