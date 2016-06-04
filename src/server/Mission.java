package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// The Mission class at server side only tracks the jewel status(open/close) and count down time
public class Mission {
	private final int RESET_TIME = 600;
	private Boolean[] isSet;
	private java.util.Map<Integer, List<Integer>> jewels;	// id ->(x,y)+count time
	
	public Mission() {
		
		// load point.txt
		jewels = new java.util.HashMap<Integer, List<Integer>>();
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
				jewels.put(Integer.parseInt(data[0]), list);
			}
			reader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//get jewels & jewel number
		isSet = new Boolean[jewels.size()];
		Arrays.fill(isSet, false);
			
		//print jewel number
		
		Thread t = new Thread(new Runnable( ) {
			
			public void run() {
				
				while(true) {
					for(java.util.Map.Entry<Integer, List<Integer>> entry : jewels.entrySet()) {		//update time
							if(entry.getValue().get(2) > 0) {
								int prevTime = entry.getValue().get(2);
								entry.getValue().set(2, prevTime-1);
							}	else  {
								isSet[entry.getKey()] = false;		// reset 
							}
					}
					
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("mission countdown update stopped");
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		
	}
	
	public void setMission(int id) {
		if(jewels.containsKey(id)) {
			isSet[id] = true;
			List<Integer> lst = jewels.get(id);
			lst.set(2, RESET_TIME);
			jewels.put(id, lst);		//set time
		}
	}
	
	public  java.util.Map<Integer, List<Integer>> getJewels() {
		return jewels;
	}
	
}
