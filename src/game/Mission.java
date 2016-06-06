package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mission {
	public final int COUNTDOWN = 600; 
	private java.util.Map<Integer, ArrayList<String>> questions;
	private java.util.HashMap<Integer, List<Integer>> location;
	
	
	public Mission() {
		// load point.txt
		location = new java.util.HashMap<Integer, List<Integer>>();
		BufferedReader reader;
		String line;
		
		System.out.println("reading mission points");
		try {									
			reader = new BufferedReader(new FileReader("point.txt"));
			while((line = reader.readLine()) != null) {
				String data[] = line.split(" ");
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(Integer.parseInt(data[1]));
				list.add(Integer.parseInt(data[2]));
				list.add(0);	//time
				location.put(Integer.parseInt(data[0]), list);
			}
			reader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//load question.txt
		questions = new java.util.TreeMap<Integer, ArrayList<String>>();
		System.out.println("reading questions");
		try {	
			InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("question.txt")), "UTF-8");
			reader = new BufferedReader(isr);
			Boolean firstTime = true;
			while((line = reader.readLine()) != null) {
				String data[] = line.split(" ");
				ArrayList<String> list = new ArrayList<String>();
				if(firstTime) {
					data[0] = String.valueOf(1);
					firstTime = false;
				}
				int id = Integer.parseInt(data[0]);
				list.add(data[1]);	//question
				
				while( ! (line = reader.readLine()).startsWith("+") ) {
					list.add(line.split(" ")[1]);
				}
				list.add(line.split(" ")[1]);
				questions.put(id, list);
				
			}
			reader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	public int getPointNum() {
		return location.size();
	}
	
	public int getQNum() {
		return questions.size();
	}
	
	public void setLocation(java.util.HashMap<Integer, List<Integer>> map) {
		this.location = map;
	}
	
	public java.util.HashMap<Integer, List<Integer>> getLocation() {
		return location;
	}
	
	public void setCountDown(int id, int t) {
		List<Integer> list =location.get(id);
		list.set(2, t);
		//location.put(id, list);		
	}
	
	public Boolean checkMissionSet(int id) {
		try {
			if(location.containsKey(id)) {
				if(location.get(id).get(2) > 0)
					return true;
				else
					return false;
			} else {
				return false;
			}
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	public ArrayList<String> getQuestion() {		//[0]: question , rest: choices , note: please use
																				// ArrayList.size() to get the questions
		Random rand = new Random();		
		return questions.get(rand.nextInt(getQNum())+1);
	}
	
}
