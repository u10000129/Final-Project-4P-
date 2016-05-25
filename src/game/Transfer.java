package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
	private JSONObject json;
	
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
						json = JSONObject.parse(line);
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
	
	public void encode(String name, 
										int characterX, int characterY,
										java.util.Map<Integer, List<Integer>> jewels,
										java.util.Map <Integer, List<Integer>>hunters) {
		
		JSONObject obj = new JSONObject();
		obj.setLong("time",System.currentTimeMillis());
		
		// player array
		JSONArray playerArray = new JSONArray();
		JSONObject player = new JSONObject();
		player.setString("name", name);
		player.setString("characterX", String.valueOf(characterX));
		player.setString("characterY", String.valueOf(characterY));
		playerArray.setJSONObject(0, player);
		obj.setJSONArray("players", playerArray);
		
		// jewel array
		JSONArray jewelArray =  new JSONArray();
		for(Entry<Integer, List<Integer>> jewel : jewels.entrySet()) {
			JSONObject jw = new JSONObject();
			
			try {
				jw.setInt("id", jewel.getKey());
				jw.setInt("x", jewel.getValue().get(0));
				jw.setInt("y", jewel.getValue().get(1));
				
				jewelArray.setJSONObject(jewel.getKey(), jw);
			}
			catch(Exception e) {
				System.out.println("Error occurred while setting jewelArray");
				e.printStackTrace();
			}
		}
		obj.setJSONArray("jewels", jewelArray);
		
		//hunter array
		JSONArray hunterArray = new JSONArray();
		for(Entry<Integer, List<Integer>> hunter : hunters.entrySet()) {
			JSONObject ht = new JSONObject();
			
			try {
				ht.setInt("id", hunter.getKey());
				ht.setInt("x", hunter.getValue().get(0));
				ht.setInt("y", hunter.getValue().get(1));
				
				hunterArray.setJSONObject(hunter.getKey(), ht);
			}
			catch(Exception e) {
				System.out.println("Error occurred while setting hunterArray");
				e.printStackTrace();
			}
		}
		obj.setJSONArray("hunters", hunterArray);
		
		writer.println(obj.toString());
		writer.flush();
	}
	
	public Boolean getGameStatus() {
		
		gameStart =json.getBoolean("status");
		return gameStart;
	}
	
	public Long getTime() {
		
		return json.getLong("time");
	}
	
	public java.util.Map<Integer, List<Integer>> getPlayers() { // Map of  ID->(x,y)
		
		JSONArray playerArray = json.getJSONArray("players");
		
		java.util.Map<Integer, List<Integer>> players = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<playerArray.size();i++) {
			
			List<Integer> list = new ArrayList<Integer>();
			list.add(json.getInt("x"));
			list.add(json.getInt("y"));
			
			players.put(json.getInt("id"), list);
		}
		
		return players;
	}
	
	public java.util.Map<Integer, List<Integer>>   getHunters() { // Map of ID->(x,y)
		
		JSONArray hunterArray = json.getJSONArray("hunters");
		
		java.util.Map<Integer, List<Integer>> hunters = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<hunterArray.size();i++) {
			
			List<Integer> list = new ArrayList<Integer>();
			list.add(json.getInt("x"));
			list.add(json.getInt("y"));
			
			hunters.put(json.getInt("id"), list);
		}
		
		return hunters;
	}
	
	public java.util.Map<Integer, List<Integer>> getJewel() {		// Map of  ID->(x,y)
		
		JSONArray jewelArray = json.getJSONArray("jewels");
		
		java.util.Map<Integer, List<Integer>> jewels = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<jewelArray.size();i++) {
			
			List<Integer> list = new ArrayList<Integer>();
			list.add(json.getInt("x"));
			list.add(json.getInt("y"));
			
			jewels.put(json.getInt("id"), list);
		}
		
		return jewels;
	}
	
}
