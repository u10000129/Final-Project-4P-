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
										int jewelId,
										java.util.Map <Integer, List<Integer>>hunters,
										int lifeStatus) {
		
		JSONObject obj = new JSONObject();
		obj.setLong("time",System.currentTimeMillis());
		
		// player array
		JSONArray playerArray = new JSONArray();
		JSONObject player = new JSONObject();
		player.setString("name", name);
		player.setString("characterX", String.valueOf(characterX));
		player.setString("characterY", String.valueOf(characterY));
		player.setInt("lifeStatus", lifeStatus);
		playerArray.setJSONObject(0, player);
		obj.setJSONArray("players", playerArray);
		
		obj.setLong("jewelId",jewelId);		
		
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

public java.util.Map<Integer, Integer> getPlayersLife() {
	
	JSONArray playerArray = json.getJSONArray("players");
	
	java.util.Map<Integer, Integer> players = new HashMap<Integer, Integer>();
	for(int i=0;i<playerArray.size();i++) {
		JSONObject playerObject = playerArray.getJSONObject(i);			
		players.put(playerObject.getInt("id"), playerObject.getInt("lifeStatus"));
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
		for(int i=1;i<jewelArray.size();i++) {
			JSONObject jewelObject = jewelArray.getJSONObject(i);
			List<Integer> list = new ArrayList<Integer>();
			list.add(jewelObject.getInt("x"));
			list.add(jewelObject.getInt("y"));
			list.add(jewelObject.getInt("time"));
			
			jewels.put(jewelObject.getInt("id"), list);
		}
		
		return jewels;
	}
	
}
