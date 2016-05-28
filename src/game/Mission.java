package game;

import java.util.ArrayList;
import java.util.Random;

public class Mission {
	private final int POINT_NUM = 20; 
	private final int Q_NUM;
	private java.util.Map<Integer, ArrayList<String>> questions;
	
	
	public Questions() {
		// load file
		questions = new java.util.TreeMap<Integer, ArrayList<String>>();
		//set Q_NUM,
	}
	
	public ArrayList<String> getQuestion() {		//[0]: question , rest: choices , note: please use
																				// ArrayList.size() to get the questions
		Random rand = new Random();
		return questions.get(rand.nextInt(Q_NUM));
	}
	
}
