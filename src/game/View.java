package game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class View {
	private final int diameter = 40;
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
	
	public void display(){
		//this.data = data;
		//Get other players's position from JSON file.
		/*
		data = loadJSONObject("");
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		for (int i = 0; i < nodes.size(); i++) {
			JSONObject node = nodes.getJSONObject(i);
			String name = node.getString("name");
			characters.add(new Character(this, name, 0,100+ i*50));
		}
		for (int i = 0; i < links.size(); i++) {
			JSONObject link = links.getJSONObject(i);
			int source = link.getInt("source");		
			int target = link.getInt("target");
			characters.get(source).addTarget(characters.get(target));
		}
		*/
		//Draw map image.
		
		mapImage = map.getSubMap(player.getX(), player.getY());
		mainapplet.image(mapImage, 0, 0, 800, 600);
		
		 
	
		//Draw my player.
		
		int playerx, playery;
		Bounds hBound = map.horizontalWall(player.getX(), player.getY());
		Bounds vBound = map.verticalWall(player.getX(), player.getY());
		//HorizonBound detect
		if(hBound == Bounds.LEFT){
			playerx = player.getX();
		}
		else if (hBound == Bounds.RIGHT){
			playerx = player.getX() - map.getFullMap().width + MyApplet.width ;
		}
		else {
			playerx = MyApplet.width / 2;
		}
		//verticalBound detect
		if(vBound == Bounds.UP){
			playery = player.getY();

		}
		else if (vBound == Bounds.DOWN){
			playery = player.getY() - map.getFullMap().height + MyApplet.height ;
		}
		else {
			playery = MyApplet.height / 2;
		}
		mainapplet.ellipse(playerx, playery, diameter, diameter);
		 
		//Draw other players if I can see it.
		/*
		 
		 
		 */
		//Draw a circle field of view. 
		/*
		 
		 
		 */
		//Test collision map.
		/*
		int[][] array = map.getSubCollisionMap(player.getX(), player.getY());
		for(int y=0;y<MyApplet.height;y++) {
			for(int x=0;x<MyApplet.width;x++) {
				System.out.printf("%d", array[x][y]);
			}
			System.out.println("");		
		}
		*/
	}

}
