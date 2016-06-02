package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import processing.data.JSONArray;
import processing.data.JSONObject;


public class Transfer {
	private JSONObject json;
	
	
	public String encode(String name,
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
		return obj.toString().replace("\n", "");
	}
	
	public void decode(String str) {		
		json = JSONObject.parse(str);
	}
	
	public Boolean getGameStatus() {
		
		return json.getBoolean("status");
	}
	
	public Long getTime() {
		
		return json.getLong("time");
	}
	
	public java.util.Map<Integer, List<Integer>> getPlayers() { // Map of  ID->(x,y)
		
		JSONArray playerArray = json.getJSONArray("players");
		
		java.util.Map<Integer, List<Integer>> players = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<playerArray.size();i++) {
			JSONObject playerObject = playerArray.getJSONObject(i);
			List<Integer> list = new ArrayList<Integer>();
			list.add(playerObject.getInt("x"));
			list.add(playerObject.getInt("y"));			
			players.put(playerObject.getInt("id"), list);
		}
		
		return players;
	}
	
public java.util.Map<Integer, String> getPlayersName() {
		
		JSONArray playerArray = json.getJSONArray("players");
		
		java.util.Map<Integer, String> players = new HashMap<Integer, String>();
		for(int i=0;i<playerArray.size();i++) {
			JSONObject playerObject = playerArray.getJSONObject(i);			
			players.put(playerObject.getInt("id"), playerObject.getString("name"));
		}
		
		return players;
	}
	
	public java.util.Map<Integer, List<Integer>>   getHunters() { // Map of ID->(x,y)
		
		JSONArray hunterArray = json.getJSONArray("hunters");
		
		java.util.Map<Integer, List<Integer>> hunters = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<hunterArray.size();i++) {
			JSONObject hunterObject = hunterArray.getJSONObject(i);
			List<Integer> list = new ArrayList<Integer>();
			list.add(hunterObject.getInt("x"));
			list.add(hunterObject.getInt("y"));
			
			hunters.put(hunterObject.getInt("id"), list);
		}
		
		return hunters;
	}
	
	public java.util.Map<Integer, List<Integer>> getJewel() {		// Map of  ID->(x,y)
		
		JSONArray jewelArray = json.getJSONArray("jewels");
		
		java.util.Map<Integer, List<Integer>> jewels = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<jewelArray.size();i++) {
			JSONObject jewelObject = jewelArray.getJSONObject(i);
			List<Integer> list = new ArrayList<Integer>();
			list.add(jewelObject.getInt("x"));
			list.add(jewelObject.getInt("y"));
			
			jewels.put(jewelObject.getInt("id"), list);
		}
		
		return jewels;
	}
	
}
