package Utils;

import java.util.ArrayList;

/**
 * Provides a method to round numbers fairly.
 *
 */
public class Rounder {
	private ArrayList<Double> history = new ArrayList<Double>();
	
	private ArrayList<Integer> roundedHistory = new ArrayList<Integer>();
	
	/**
	 * Rounds a double to an integer fairly, based on the previously rounded numbers
	 * so that the total difference between actual and rounded values
	 * never exceeds 1
	 * @param toRound		Number to round
	 */
	public int round(double toRound){
		int rounded = (int) toRound;
		history.add(toRound);
		roundedHistory.add(rounded);
		int totalRounded = roundedHistory.stream().mapToInt(e -> (int)e ).sum();
		double totalHistory = history.stream().mapToDouble(e -> (double) e).sum();
		if (Math.abs(totalHistory - totalRounded) >= 1){
			int newRounded = rounded + (int)(totalHistory-totalRounded);
			roundedHistory.remove(roundedHistory.indexOf(rounded));
			roundedHistory.add(newRounded);
			return newRounded;
		}
		else
			return rounded;
	}
	
}
