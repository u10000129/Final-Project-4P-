package game;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class View {
	private final int diameter = 40;
	private final int FieldOfView = 250;
	private PApplet mainapplet;
	private Player player;
	private Map map;
	private PImage mapImage;
	
	private JSONObject data;
	private JSONArray character, mapdata;
	


	public View (PApplet mainapplet, Map map, Player player) {
		this.mainapplet = mainapplet;
		this.map =  map;
		this.player = player;
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
			mainapplet.image(map.getFullMap(), 0, 0,MyApplet.width, MyApplet.height);
			player.collisionDetect();
			mainapplet.ellipse(transformX(player.getX()), transformY(player.getY()), diameter/4, diameter/4);
			
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
