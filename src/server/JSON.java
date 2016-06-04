package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import processing.data.JSONArray;
import processing.data.JSONObject;

public class JSON {
	private JSONObject json;
	
	public JSON() {
		
	}
	
	public String encode(
									Long time,
									Boolean gameStatus,
									java.util.Map<Integer, List<Integer>> players,
									java.util.Map<Integer, String> playersName,
									java.util.Map<Integer, List<Integer>> hunters,
									java.util.Map<Integer, List<Integer>> jewels)
	{
		JSONObject obj = new JSONObject();
		
		obj.setLong("time", time);
		obj.setBoolean("status", gameStatus);
		
		JSONArray playerArray = new JSONArray();
		for(Entry<Integer, List<Integer>> player : players.entrySet()) {
			JSONObject p = new JSONObject();
			
			p.setInt("id", player.getKey());
			p.setInt("x", player.getValue().get(0));
			p.setInt("y", player.getValue().get(1));
			
			playerArray.setJSONObject(player.getKey(), p);
		}
		
		for(int i=0;i<playerArray.size();i++) {
			JSONObject p = playerArray.getJSONObject(i);
			p.setString("name", playersName.get(i));
		}
		
		obj.setJSONArray("players", playerArray);
		
		
		JSONArray hunterArray = new JSONArray();
		for(Entry<Integer, List<Integer>> hunter: hunters.entrySet()) {
			JSONObject h = new JSONObject();
			
			h.setInt("id", hunter.getKey());
			h.setInt("x", hunter.getValue().get(0));
			h.setInt("y", hunter.getValue().get(1));
			hunterArray.setJSONObject(hunter.getKey(), h);
		}
		obj.setJSONArray("hunters", hunterArray);
		
		
		JSONArray jewelArray = new JSONArray();
		for(Entry<Integer, List<Integer>> jewel : jewels.entrySet()) {
			JSONObject j = new JSONObject();
			
			j.setInt("id", jewel.getKey());
			j.setInt("x", jewel.getValue().get(0));
			j.setInt("y", jewel.getValue().get(1));
			j.setInt("time", jewel.getValue().get(2));
			jewelArray.setJSONObject(jewel.getKey(), j);
		}
		obj.setJSONArray("jewels", jewelArray);		
		
		return obj.toString().replace("\n", "");
	}
	
	public JSONObject decode(String str) {
		json = JSONObject.parse(str);
		return json;
	}
	
	public List<Integer> getPlayers() { // Map of  ID->(x,y)
		
		JSONArray playerArray = json.getJSONArray("players");
		List<Integer> list = new ArrayList<Integer>();		
		for(int i=0;i<playerArray.size();i++) {
			JSONObject playerObject = playerArray.getJSONObject(i);
			
			list.add(playerObject.getInt("characterX"));
			list.add(playerObject.getInt("characterY"));
		}
		
		return list;
	}
	
	public String getName() {		
		JSONArray playerArray = json.getJSONArray("players");
		JSONObject playerObject = playerArray.getJSONObject(0);
		return playerObject.getString("name");
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
