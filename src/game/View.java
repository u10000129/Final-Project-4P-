package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	private java.util.Map<Integer, List<Integer>> location;
	
	private Transmission transmission;	
	public HashMap<Integer, List<Integer>> playersMap;
	public HashMap<Integer, List<Integer>> huntersMap;
	public HashMap<Integer, List<Integer>> jewelsMap;
	public long time = 0;
	


	public View (PApplet mainapplet, Map map, Player player, Transmission transmission) {
		this.mainapplet = mainapplet;
		this.map =  map;
		this.player = player;
		this.mission = new Mission();
		this.location = mission.getLocation();
		this.transmission = transmission;
	}
	
	private int transformX(int x) {
		return x * MyApplet.width / map.getImageWidth();
	}
	
	private int transformY(int y) {
		return y * MyApplet.height / map.getImageHeight();
	}
	
	public void display(){		
		/*
		 * Get transmission's message and display the time.
		 */
		playersMap = transmission.getPlayers();	
		time = transmission.getTime();
		mainapplet.textSize(20);
		mainapplet.text((int)time, 650, 50);
		
		/*
		 * If user press TAB, display full map, or display local map.
		 */
		if(mainapplet.keyPressed && mainapplet.key == '\t') { //Display full map.
			/*
			 * Draw full map.
			 */
			mainapplet.image(map.getFullMap(), 0, 0, MyApplet.width, MyApplet.height);	
			/*
			 * Draw my player in full map.
			 */
			player.collisionDetect();
			mainapplet.fill(0);
			mainapplet.noStroke();
			mainapplet.ellipse(transformX(player.getX()), transformY(player.getY()), diameter/4, diameter/4);			
			/*
			 * Draw missions in full map.
			 */
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
			/*
			 * Draw other players in full map.
			 */
			int myPlayerId = transmission.getMyId();
			for(int i = 0; i < playersMap.size(); i++){		
				if(i == myPlayerId) continue;
				ArrayList<Integer> position = new ArrayList<Integer>(2);
				position = (ArrayList<Integer>) playersMap.get(i);
				//mainapplet.fill(???);
				mainapplet.noStroke(); 		
				mainapplet.ellipse(transformX( position.get(0) ), transformY( position.get(1) ), diameter/4, diameter/4);
			}
			
		} else { // Display local map.
			/*
			 * Draw map image.
			 */
			mapImage = map.getSubMap(player.getX(), player.getY());
			mainapplet.image(mapImage, 0, 0, MyApplet.width, MyApplet.height);	
			/*
			 * Draw my player.
			 */
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
			mainapplet.fill(0);
			mainapplet.noStroke(); 		
			player.collisionDetect();
			mainapplet.ellipse(playerx, playery, diameter, diameter);	
			/*
			 * Scan the players's and hunter's position, and store the result in 2D-array. Which is used when draw the circle view.
			 */
			//Players
			int[][] playerIsInside = new  int[2*FieldOfView+1][2*FieldOfView+1] ;
			int myPlayerId = transmission.getMyId();
			for(int i = 0; i < playersMap.size(); i++){		
				if(i == myPlayerId) continue;
				ArrayList<Integer> position = new ArrayList<Integer>(2);
				position = (ArrayList<Integer>) playersMap.get(i);
				if(PApplet.dist(player.getX(), player.getY(), position.get(0), position.get(1)) <= FieldOfView){
					//playerIsInside[position.get(0) - player.getX() + FieldOfView][position.get(1) - player.getY() + FieldOfView] = ???;
				} 				
			}	
			
			//Hunters
			boolean[][] hunterIsInside = new  boolean[2*FieldOfView+1][2*FieldOfView+1] ;
			for(int i = 0; i < playersMap.size(); i++){	
				ArrayList<Integer> position = new ArrayList<Integer>(2);
				position = (ArrayList<Integer>) playersMap.get(i);
				if(PApplet.dist(player.getX(), player.getY(), position.get(0), position.get(1)) <= FieldOfView){
					//hunterIsInside[position.get(0) - player.getX() + FieldOfView][position.get(1) - player.getY() + FieldOfView] = true;
				} 					
			}
		
			/*
			 * Draw a circle field of view. 
			 */
			int[][] collisionMap = map.getCollisionMap();			
			PGraphics shadowImage;
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
					//If collide, draw a line-shape shadow.
					if(collisionMap[player.getX() + (int)x ][player.getY() + (int)y ] == 1){
						shadowImage.stroke(0, 128);	
						shadowImage.strokeWeight(5);						
						shadowImage.line(playerx + x, playery + y,
								playerx + (FieldOfView-1)* MyApplet.cos( MyApplet.radians(i) ), 
								playery + (FieldOfView-1)* MyApplet.sin( MyApplet.radians(i) )
								);	
						break;
					}
					//Draw other players if they are in the view.
					int playerColor = playerIsInside[FieldOfView + (int)x][FieldOfView + (int)y];
					if(playerColor != 0){						
						mainapplet.fill(playerColor);
						mainapplet.ellipse(playerx + x, playery + y, diameter, diameter);
					}
					//Draw hunters if they are in the view.
					boolean hunter = hunterIsInside[FieldOfView + (int)x][FieldOfView + (int)y];
					if(hunter != false){
						mainapplet.fill(0);
						mainapplet.ellipse(playerx + x, playery + y, diameter, diameter);
					}
				}		
			}
			shadowImage.endDraw();	
			mainapplet.image(shadowImage, 0, 0, MyApplet.width, MyApplet.height);			
			
			//Draw a full screen rectangle shadow, and subtract a circle.
			shadowImage = mainapplet.createGraphics(MyApplet.width, MyApplet.height);
			shadowImage.beginDraw();
			shadowImage.fill(0, 50);
			shadowImage.rect(0, 0, MyApplet.width, MyApplet.height);
			shadowImage.blendMode(PApplet.SUBTRACT);		
			shadowImage.fill(255, 0);		
			shadowImage.noStroke();
			shadowImage.ellipse(playerx, playery, FieldOfView*2, FieldOfView*2);
			shadowImage.endDraw();	
			mainapplet.image(shadowImage, 0, 0, MyApplet.width, MyApplet.height);			
		}
	}
	

}
