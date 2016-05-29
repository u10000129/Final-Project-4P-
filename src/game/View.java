package game;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class View {
	private final int diameter = 40;
	private final int FieldOfView = 250;
	private final int CrossLineLenght = 5;
	private PApplet mainapplet;
	private Player player;
	private Map map;
	private PImage mapImage;
	private Mission mission;
	
	private JSONObject data;
	private JSONArray character, mapdata;
	private java.util.Map<Integer, List<Integer>> location;
	


	public View (PApplet mainapplet, Map map, Player player) {
		this.mainapplet = mainapplet;
		this.map =  map;
		this.player = player;
		this.mission = new Mission();
		this.location = mission.getLocation();
	}
	
	private int transformX(int x) {
		return x * MyApplet.width / map.getImageWidth();
	}
	
	private int transformY(int y) {
		return y * MyApplet.height / map.getImageHeight();
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
		if(mainapplet.keyPressed && mainapplet.key == '\t') {
			mainapplet.image(map.getFullMap(), 0, 0, MyApplet.width, MyApplet.height);	
			player.collisionDetect();
			mainapplet.fill(0);
			mainapplet.noStroke();
			mainapplet.ellipse(transformX(player.getX()), transformY(player.getY()), diameter/4, diameter/4);
			
			
			for(int i = 1; i <= mission.getPointNum(); i++){
				int x = transformX(location.get(i).get(0)),
					y = transformY(location.get(i).get(1));
				if(location.get(i).get(2) == 0) {
					mainapplet.stroke(0, 255, 0);
					mainapplet.strokeWeight(2);
					mainapplet.line(x-CrossLineLenght, y-CrossLineLenght, x+CrossLineLenght, y+CrossLineLenght);
					mainapplet.line(x+CrossLineLenght, y-CrossLineLenght, x-CrossLineLenght, y+CrossLineLenght);
														
				}
				else {
					mainapplet.fill(150, 0, 0);
					mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
					mainapplet.text(location.get(i).get(2), x, y);	
				}
					
			
			}
			
		} else {
			//Draw map image.
			mapImage = map.getSubMap(player.getX(), player.getY());
			mainapplet.image(mapImage, 0, 0, MyApplet.width, MyApplet.height);
			
		
			//Draw my player.
			int playerx, playery;
			Bounds hBound = map.horizontalWall(player.getX(), player.getY());
			Bounds vBound = map.verticalWall(player.getX(), player.getY());
			//HorizonBound detect
			if(hBound == Bounds.LEFT){
				playerx = player.getX();
				
			}else if (hBound == Bounds.RIGHT){
				playerx = player.getX() - map.getFullMap().width + MyApplet.width;
				
			}else {
				playerx = MyApplet.width / 2;				
			}
			
			//verticalBound detect
			if(vBound == Bounds.UP){
				playery = player.getY();
	
			}else if (vBound == Bounds.DOWN){
				playery = player.getY() - map.getFullMap().height + MyApplet.height;
			
			}else {
				playery = MyApplet.height / 2;		
			}
			mainapplet.fill(0, 255);
			mainapplet.noStroke(); 		
			player.collisionDetect();
			mainapplet.ellipse(playerx, playery, diameter, diameter);			
			  
			 
			//Draw a circle field of view. 
			int[][] collisionMap = map.getCollisionMap();
			//Draw a full screen rectangle shadow, and subtract a circle.
			PGraphics shadowImage = mainapplet.createGraphics(MyApplet.width, MyApplet.height);
			shadowImage.beginDraw();
			shadowImage.fill(0, 50);
			shadowImage.rect(0, 0, MyApplet.width, MyApplet.height);
			shadowImage.blendMode(PApplet.SUBTRACT);		
			shadowImage.fill(255, 0);		
			shadowImage.noStroke();
			shadowImage.ellipse(playerx, playery, FieldOfView*2, FieldOfView*2);
			shadowImage.endDraw();	
			mainapplet.image(shadowImage, 0, 0, MyApplet.width, MyApplet.height);		
			//Scan the circle field of view. If there is collision , draw a line to cover the area ,which  means that the area is invisible.
			shadowImage = mainapplet.createGraphics(MyApplet.width, MyApplet.height);
			shadowImage.beginDraw();
			for(float i = 0; i < 360; i+=1) {
				for(float j = 0; j < FieldOfView ; j++ ){
					float x = j * MyApplet.cos( MyApplet.radians(i) ); 
					float y = j * MyApplet.sin( MyApplet.radians(i) ); 
					//ArrayIndexOutOfBoundsException detect.
					if(player.getX() + (int)x < 0 || player.getX() + (int)x >= map.getFullMap().width
						||player.getY() + (int)y < 0 ||player.getY() + (int)y >= map.getFullMap().height
						) break;
					if(collisionMap[player.getX() + (int)x ][player.getY() + (int)y ] == 1){
						shadowImage.stroke(0, 75);	
						shadowImage.strokeWeight(5);						
						shadowImage.line(playerx + x, playery + y,
								playerx + (FieldOfView-1)* MyApplet.cos( MyApplet.radians(i) ), 
								playery + (FieldOfView-1)* MyApplet.sin( MyApplet.radians(i) )
								);	
						break;
					}				
				}		
			}
			shadowImage.endDraw();	
			mainapplet.image(shadowImage, 0, 0, MyApplet.width, MyApplet.height);
			
				
			//Draw other players if I can see it.
			//Todo:
		}
	}
	
	

}
