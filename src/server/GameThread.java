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
	public HashMap<Integer, String> playersName = null;
	public HashMap<Integer, Integer> playersLife = null;
	public HashMap<Integer, Double> playersSpeed = null;
	public HashMap<Integer, List<Integer>> huntersMap = null;	
	public HashMap<Integer, List<Integer>> jewelsMap = null;
	public int gameStatus = 0;
	public long time = 0;
	public int playerNum = 1;
	public int windowWidth = 800;
	public int windowHeight = 600;
	public Mission mission;
	
	private Hunter[] hunter;
	private ArrayList<Hunter> hunters;
	private int hunterNum = 15;
	boolean valid;
	private List<ReceiveThread> receiver = new ArrayList<ReceiveThread>();
	private List<String> line = new ArrayList<String>();
	private List<JSON> myjson = new ArrayList<JSON>();
	private MyApplet myApplet;
	
	
	
	
	public GameThread(Main transmission) {
		this.transmission = transmission;
		json = new JSON();
		
		hunter = new Hunter[hunterNum];
		hunters = new ArrayList<Hunter>();
		mission = new Mission();
		
		myApplet = new MyApplet(hunter, hunters, hunterNum);
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
			ReceiveThread receThread = new ReceiveThread(i);
			receiver.add(receThread);
			
		}
		
		gameStatus = 1;
		addTime timer = new addTime();		
		timer.start();
		for(int i=0; i<playerNum ;i++) receiver.get(i).start();
		while(true) {
			jsonString = json.encode(time, gameStatus, playersMap, playersName, playersLife, playersSpeed, huntersMap, jewelsMap);
			transmission.broadcast(jsonString);
		
		
	
			

			//setPlayerMapAndNameAndJewel();				
			myApplet.setPlayersMap(playersMap);
			myApplet.setPlayersLife(playersLife);
			setHunterMap();
			
			valid = false;		
			for(int i=0;i<playersLife.size();i++) {						
				if(playersLife.get(i)>0) valid = true;							
			}
			if(valid == false) gameStatus = 2;
			else gameStatus = 1;
			
			
			jewelsMap = (HashMap<Integer, List<Integer>>) mission.getJewels();
			try {
				sleep(10);
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
		playersName = new HashMap<Integer, String>();
		playersLife = new HashMap<Integer, Integer>();
		playersSpeed = new HashMap<Integer, Double>();
		huntersMap = new HashMap<Integer, List<Integer>>();
		jewelsMap = new HashMap<Integer, List<Integer>>();
		playersMap.put(0, initPos);
		playersName.put(0,"jack");
		playersLife.put(0,1);
		playersSpeed.put(0,0.5);
		huntersMap.put(0, initPos);
		
		jewelsMap = (HashMap<Integer, List<Integer>>) mission.getJewels();
		jsonString = json.encode(time, gameStatus, playersMap, playersName, playersLife, playersSpeed, huntersMap, jewelsMap);
	}
	
	class addTime extends Thread {
		@Override
		public void run() {
			while(gameStatus == 1) {
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
	public void setPlayerMapAndNameAndJewel() {
		for(int i=0; i<playerNum ;i++) {
			ArrayList<Integer> position = new ArrayList<Integer>(2);			
			jsonString = transmission.receiveMessage(i);
			json.decode(jsonString);
			position = (ArrayList<Integer>) json.getPlayers();
			playersMap.put(i, position);
			String name = json.getName();
			int lifeStatus = json.getLife();
			double speed = json.getSpeed();
			playersName.put(i, name);
			playersLife.put(i, lifeStatus);
			playersSpeed.put(i, speed);
			if(json.getJewelId()!=0)
			mission.setMission(json.getJewelId());					
		}
	}
	public void setHunterMap() {
		
		if(hunters!=null) {
		for(int i = 0;i<hunters.size();i++) {
			ArrayList<Integer> position = new ArrayList<Integer>(2);
			position.add(hunters.get(i).getX());
			position.add(hunters.get(i).getY());
			huntersMap.put(i, position);			
		}
		}	
	}
	
	class ReceiveThread extends Thread {
		int i;
		public ReceiveThread(int a) {
			i = a;
		}
		public void run() {
			while(true) {
			ArrayList<Integer> position = new ArrayList<Integer>(2);	
			String line = transmission.receiveMessage(i);
			JSON myjson = new JSON();
			myjson.decode(line);
			position = (ArrayList<Integer>) myjson.getPlayers();
			playersMap.put(i, position);
			String name = myjson.getName();
			int lifeStatus = myjson.getLife();
			double speed = myjson.getSpeed();
			playersName.put(i, name);
			playersLife.put(i, lifeStatus);
			playersSpeed.put(i, speed);
			if(myjson.getJewelId()!=0)
			mission.setMission(myjson.getJewelId());	
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		
		
	}

}
