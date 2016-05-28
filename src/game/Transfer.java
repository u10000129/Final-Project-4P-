package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import processing.data.JSONArray;
import processing.data.JSONObject;


public class Transfer {
	private JSONObject json;
	
	
	public String encode(String name,													//player name
										int characterX, int characterY,							//player coordinate
										java.util.Map<Integer, Boolean> jewels				// jewel id -> status -> open=true
										) {
		
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
		for(Entry<Integer, Boolean> jewel : jewels.entrySet()) {
			JSONObject jw = new JSONObject();
			
			try {
				jw.setInt("id", jewel.getKey());
				jw.setBoolean("status", jewel.getValue());
				
				jewelArray.setJSONObject(jewel.getKey(), jw);
			}
			catch(Exception e) {
				System.out.println("Error occurred while setting jewelArray");
				e.printStackTrace();
			}
		}
		obj.setJSONArray("jewels", jewelArray);
		
		return obj.toString();
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
	
	public java.util.Map<Integer, Integer> getJewel() {		// Map of  ID->count down time
		
		JSONArray jewelArray = json.getJSONArray("jewels");
		
		java.util.Map<Integer, Integer> jewels = new HashMap<Integer, Integer>();
		for(int i=0;i<jewelArray.size();i++) {
			
			jewels.put(json.getInt("id"), json.getInt("time"));
		}
		
		return jewels;
	}
	
}
