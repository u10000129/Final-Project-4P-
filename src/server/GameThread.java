package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class GameThread extends Thread{
	
	public Main transmission;
	public JSON json;
	public String jsonString;
	public HashMap<Integer, List<Integer>> playersMap = null;
	public HashMap<Integer, List<Integer>> huntersMap = null;
	public HashMap<Integer, List<Integer>> jewelsMap = null;
	public boolean gameStatus = false;
	public long time = 0;
	public int playerNum = 1;
	
	public GameThread(Main transmission) {
		this.transmission = transmission;
		json = new JSON();
	}

	
	public void run() {		
		init();
		while(transmission.getClientNum()<playerNum) {try {
			sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}	//wait for client		
		for(int i=0; i<playerNum ;i++) {
			transmission.receiveMessage(i);
			System.out.println("received first msg");
			transmission.sendMessage(Integer.toString(i), i);
			System.out.println("sending ID...");
			transmission.receiveMessage(i);	
			System.out.println("received confirm msg");
		}
		
		gameStatus = true;
		addTime timer = new addTime();		
		timer.start();
		while(true) {
		jsonString = json.encode(time, gameStatus, playersMap, huntersMap, jewelsMap);
		transmission.broadcast(jsonString);
		
		
		ArrayList<Integer> position = new ArrayList<Integer>(2);
		for(int i=0; i<playerNum ;i++) {
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
		huntersMap.put(0, initPos);
		jewelsMap.put(0, initPos);		
		jsonString = json.encode(time, gameStatus, playersMap, huntersMap, jewelsMap);
	}
	
	class addTime extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				time++;
			}
		}		
	}

}
