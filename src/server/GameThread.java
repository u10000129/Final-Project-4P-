package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;



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
	public int windowWidth = 800;
	public int windowHeight = 600;
	
	private Hunter[] hunter;
	private ArrayList<Hunter> hunters;
	private int hunterNum;
	private View view;
	private Map map;
	
	
	
	public GameThread(Main transmission) {
		this.transmission = transmission;
		json = new JSON();
		
		MyApplet myApplet = new MyApplet(hunter, hunters, hunterNum, view, map);
		myApplet.init();
		
		JFrame window = new JFrame("Final Project");
		window.setContentPane(myApplet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		window.setVisible(true);	
		window.setLocation(300, 50);
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
		
		
		setPlayerMap();
		setHunterMap();
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
	public void setPlayerMap() {
		ArrayList<Integer> position = new ArrayList<Integer>(2);
		for(int i=0; i<playerNum ;i++) {
			jsonString = transmission.receiveMessage(i);
			json.decode(jsonString);
			position = (ArrayList<Integer>) json.getPlayers();			
			playersMap.put(i, position);			
		}
	}
	public void setHunterMap() {
		ArrayList<Integer> position = new ArrayList<Integer>(2);
		if(hunters!=null) {
		for(int i = 0;i<hunters.size();i++) {
			position.clear();
			position.add(hunters.get(i).getX());
			position.add(hunters.get(i).getY());
			huntersMap.put(0, position);
		}
		}
	}

}
