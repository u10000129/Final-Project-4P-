package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameThread extends Thread{
	
	public Main transmission;
	public JSON json;
	public String jsonString;
	public HashMap<Integer, List<Integer>> playersMap = null;
	public HashMap<Integer, List<Integer>> huntersMap = null;
	public HashMap<Integer, List<Integer>> jewelsMap = null;
	public boolean gameStatus = false;
	public long time = 0;
	
	public GameThread(Main transmission) {
		this.transmission = transmission;
		json = new JSON();
	}

	
	public void run() {		
		init();
		while(transmission.getClientNum()<2) {for(int i=0;i<100;i++);}	//wait for client		
		for(int i=0; i<2 ;i++) {
			transmission.receiveMessage(i);
		}
		gameStatus = true;
		while(true) {
		jsonString = json.encode(time, gameStatus, playersMap, huntersMap, jewelsMap);
		transmission.broadcast(jsonString);
		Timer timer = new Timer();		
		timer.schedule(new addTime(),1000, 1000);
		
		ArrayList<Integer> position = new ArrayList<Integer>(2);
		for(int i=0; i<2 ;i++) {
			jsonString = transmission.receiveMessage(i);
			json.decode(jsonString);
			position = (ArrayList<Integer>) json.getPlayers();			
			playersMap.put(i, position);			
		}
		try {
			sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void init() {
		ArrayList<Integer> initPos = new ArrayList<Integer>(2);
		initPos.add(7110);
		initPos.add(7100);
		playersMap = new HashMap<Integer, List<Integer>>();
		huntersMap = new HashMap<Integer, List<Integer>>();
		jewelsMap = new HashMap<Integer, List<Integer>>();
		playersMap.put(0, initPos);
		playersMap.put(1, initPos);
		huntersMap.put(0, initPos);
		jewelsMap.put(0, initPos);		
		jsonString = json.encode(time, gameStatus, playersMap, huntersMap, jewelsMap);
	}
	
	class addTime extends TimerTask {
		@Override
		public void run() {
			time++;			
		}		
	}

}
