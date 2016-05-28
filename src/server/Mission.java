package server;

import java.util.Arrays;


// The Mission class at server side only tracks the jewel status(open/close) and count down time
public class Mission {
	private final int RESET_TIME = 600;
	private int JEWEL_NUM;
	private Boolean[] isSet;
	private java.util.Map<Integer, Integer> jewels;	// id -> count time
	
	public Mission() {
		
		jewels = new java.util.TreeMap<Integer, Integer>();
		//get jewels & jewel number
		isSet = new Boolean[JEWEL_NUM];
		Arrays.fill(isSet, false);
			
		//print jewel number
		
		Thread t = new Thread(new Runnable( ) {
			
			public void run() {
				
				while(true) {
					for(java.util.Map.Entry<Integer, Integer> entry : jewels.entrySet()) {		//update time
							if(entry.getValue() > 0) {
								int prevTime = entry.getValue();
								entry.setValue(prevTime-1);
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
			int oldValue = jewels.get(id);
			jewels.replace(id, oldValue, RESET_TIME);		//set time
		}
	}
	
	public  java.util.Map<Integer, Integer> getJewels() {
		return jewels;
	}
	
}
