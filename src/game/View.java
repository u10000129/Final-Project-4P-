package game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class View {
	private Map map;
	private Player player;
	PApplet mainapplet;
	JSONObject data;
	JSONArray character, mapdata;
	
	PImage mapImage;
	
	public View (PApplet mainapplet, Map map, Player player) {
		this.mainapplet = mainapplet;
		this.map =  map;
		this.player = player;
	}
	
	public void display(JSONObject data){
		this.data = data;
		//Get other players's position from JSON file.
		//Draw map image.
		/*
		 mapImage = map.getSubMap(player.getX, player.getY);
		 mainapplet.image(mapImage, 0, 0);
		 
		 */
		//Draw my player.
		/*
		 
		 
		 */
		//Draw other players if I can see it.
		/*
		 
		 
		 */
		//Draw a circle field of view. 
		/*
		 
		 
		 */
	}

}
