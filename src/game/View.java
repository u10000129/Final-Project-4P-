package game;


import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class View {
	private final int diameter = 40;
	private final int FieldOfView = 250;
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
			
			 
			 
			 
			//Draw a circle field of view. 
			
			int[][] collisionMap = map.getCollisionMap();
			mainapplet.fill(0, 0, 0, 128);
			for(int i = 0; i <= MyApplet.width; i++ ){
				for(int j = 0; j <= MyApplet.height; j++ ){
					if(PApplet.dist(playerx, playery, i, j) > FieldOfView) 
							mainapplet.rect(i, j, 1, 1);				
				}		
			}
			for(int i = -100; i <= 100; i++ ){
				collisionMap[7010 + i][7100] = 1;
			
			}
			mainapplet.stroke(0, 0, 0, 128);
			mainapplet.strokeWeight(5);
			for(float i = 0; i < 360; i++) {
				for(float j = 0; j < FieldOfView ; j++ ){
					float x = j * PApplet.cos( PApplet.radians(i) ); 
					float y = j * PApplet.sin( PApplet.radians(i) ); 
					if(collisionMap[player.getX() + (int)x ][player.getY() + (int)y ] == 1){
						mainapplet.line(playerx + x, playery + y,
										playerx + (FieldOfView-1)* PApplet.cos( PApplet.radians(i) ), 
										playery + (FieldOfView-1)* PApplet.sin( PApplet.radians(i) )
										);	
						break;
					}				
				}		
			}
		}
		
		
		
		
	}

}
