package Utils;

import java.util.ArrayList;

public class Rounder {
	private ArrayList<Double> history = new ArrayList<Double>();
	
	private ArrayList<Integer> roundedHistory = new ArrayList<Integer>();
	
	public int round(double toRound){
		int rounded = (int) toRound;
		history.add(toRound);
		roundedHistory.add(rounded);
		int totalRounded = roundedHistory.stream().mapToInt(e -> (int)e ).sum();
		double totalHistory = history.stream().mapToDouble(e -> (double) e).sum();
		System.out.println("RoundedHistory = " +  roundedHistory);
		System.out.println("TotalHistory = " + history);
		if (Math.abs(totalHistory - totalRounded) >= 1){
			int newRounded = rounded + (int)(totalHistory-totalRounded);
			roundedHistory.remove(roundedHistory.indexOf(rounded));
			roundedHistory.add(newRounded);
			return newRounded;
		}
		else
			return rounded;
	}
	
	public static void main(String[] args){
		Rounder r = new Rounder();
		System.out.println(r.round((double)4/3));
		System.out.println(r.round((double)4/3));
		System.out.println(r.round((double)4/3));
		System.out.println(r.round((double)4/3));
		System.out.println(r.round((double)4/3));
		System.out.println(r.round((double)4/3));
	}
	
}
