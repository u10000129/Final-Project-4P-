package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map.Entry;

import processing.data.JSONArray;
import processing.data.JSONObject;



public class Transfer {
	private Socket socket;
	private String destinationIP;
	private int destinationPortNum;
	private String line;
	private PrintWriter writer;
	private boolean gameStart;
	
	public Transfer(String IP, int portNum) {
		socket = null;
		destinationIP = IP;
		destinationPortNum = portNum;
		gameStart = false;
	}
	class ClientThread extends Thread {
		private BufferedReader reader;
		
		@Override
		public void run() {
			if(socket != null){
				try{
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while(true) {
						line = reader.readLine();
					}
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void connect() {
		try {
			socket = new Socket(destinationIP, destinationPortNum);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		ClientThread t1 = new ClientThread();
		t1.start();
		
		if(socket != null) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setGameStatus(Boolean b) {
		gameStart = b;
	}
	
	public void encode(String name, 
										int characterX, int characterY,
										java.util.Map<Integer, List<Integer>> jewels,
										java.util.Map <Integer, List<Integer>>hunters) {
		JSONObject obj = new JSONObject();
		obj.setLong("time",System.currentTimeMillis());
		obj.setString("name", name);
		obj.setString("characterX", String.valueOf(characterX));
		obj.setString("characterY", String.valueOf(characterY));
		
		JSONArray jewelArray =  new JSONArray();
		int count = 0;
		for(Entry<Integer, List<Integer>> jewel : jewels.entrySet()) {
			JSONObject jw = new JSONObject();
			
			try {
				jw.setInt("id", jewel.getKey());
				jw.setInt("x", jewel.getValue().get(0));
				jw.setInt("y", jewel.getValue().get(1));
				
				jewelArray.setJSONObject(count, jw);
				count++;
			}
			catch(Exception e) {
				System.out.println("Error occurred while setting jewelArray");
				e.printStackTrace();
			}
		}
		obj.setJSONArray("jewels", jewelArray);
		
		JSONArray hunterArray = new JSONArray();
		count = 0;
		for(Entry<Integer, List<Integer>> hunter : hunters.entrySet()) {
			JSONObject ht = new JSONObject();
			
			try {
				ht.setInt("id", hunter.getKey());
				ht.setInt("x", hunter.getValue().get(0));
				ht.setInt("y", hunter.getValue().get(1));
				
				jewelArray.setJSONObject(count, ht);
				count++;
			}
			catch(Exception e) {
				System.out.println("Error occurred while setting hunterArray");
				e.printStackTrace();
			}
		}
		obj.setJSONArray("hunters", hunterArray);
		
	}
	
	public Boolean getGameStatus() {
		
	}
	
	public Long getTime() {
		
	}
	
	public Map getPlayers() { // returns a Map<int, List<int>>, which is ID->(x,y)
		
	}
	
	public Map getHunters() { // returns a Map<int, List<int>>, which is ID->(x,y)
		
	}
	
	public Map getJewel() {		// returns a Map<int, List<int>>,  which is  ID->(x,y)
		
	}
	
}
