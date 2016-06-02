package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class View {
	private int radius = 0;
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
	
	public Ani ani;

	public View (PApplet mainapplet, Map map, Player player, Transmission transmission) {
		this.mainapplet = mainapplet;
		this.map =  map;
		this.player = player;
		this.mission = new Mission();
		this.location = mission.getLocation();
		this.transmission = transmission;
		this.ani =  Ani.to(this, (float) 0.5, "radius", 30, Ani.LINEAR);
	}
	
	private int transformX(int x) {
		return x * MyApplet.width / map.getImageWidth();
	}
	
	private int transformY(int y) {
		return y * MyApplet.height / map.getImageHeight();
	}
	
	private int[] boundsDetet(int x, int y){
		int returnX, returnY;
		Bounds hBound = map.horizontalWall(x, y);
		Bounds vBound = map.verticalWall(x, y);
		//HorizonBound detect
		if(hBound == Bounds.LEFT){
			returnX = x;
			
		}else if (hBound == Bounds.RIGHT){
			returnX = x - map.getFullMap().width + MyApplet.width;
			
		}else {
			returnX = MyApplet.width / 2;				
		}
		
		//verticalBound detect
		if(vBound == Bounds.UP){
			returnY = y;

		}else if (vBound == Bounds.DOWN){
			returnY = y - map.getFullMap().height + MyApplet.height;
		
		}else {
			returnY = MyApplet.height / 2;		
		}
		return  new int[] {returnX, returnY}; 
	}
	
	public void display(){			
		
		playersMap = transmission.getPlayers();
		huntersMap = transmission.gethunters();	
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
					mainapplet.stroke(255, 0, 0);
					mainapplet.strokeWeight(2);
					mainapplet.line(x-CrossLineLenght, y-CrossLineLenght, x+CrossLineLenght, y+CrossLineLenght);
					mainapplet.line(x+CrossLineLenght, y-CrossLineLenght, x-CrossLineLenght, y+CrossLineLenght);
														
				}
				else {
					mainapplet.fill(150, 0, 0);
					mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
					mainapplet.text(location.get(i).get(2), x, y);	
				}
				//If player get close to this position, draw a scaling up circle to cover it .
				if(PApplet.dist(location.get(i).get(0), location.get(i).get(1), player.getX(), player.getY()) < 100){
					mainapplet.stroke(255, 0, 0);
					mainapplet.noFill();
					mainapplet.ellipse(x, y, radius, radius);
					if(!ani.isPlaying()){
						ani.start();
					}
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
			int[] playerPosition = this.boundsDetet(player.getX(), player.getY());			
			mainapplet.fill(0);
			mainapplet.noStroke(); 		
			player.collisionDetect();
			mainapplet.ellipse(playerPosition[0], playerPosition[1], diameter, diameter);	
			/*
			 * Draw players and hunters.
			 */
			int[][] collisionMap = map.getCollisionMap();	
			//Players			
			/*				
			int myPlayerId = transmission.getMyId();
			for(int i = 0; i < playersMap.size(); i++){		
				if(i == myPlayerId) continue;
				ArrayList<Integer> position = new ArrayList<Integer>(2);
				position = (ArrayList<Integer>) playersMap.get(i);
				float dst =  PApplet.dist(player.getX(), player.getY(), position.get(0), position.get(1));
				float deltaX = (float) (player.getX() - position.get(0))/ dst;
				float deltaY = (float) (player.getY() - position.get(1))/ dst;
				if( dst <= FieldOfView){
					boolean isVisible = true;
					for(int j = 0; j <= dst; j++) {
						if(collisionMap[(int)(j*deltaX)+ position.get(0)]
									   [(int)(j*deltaY)+ position.get(1)] > 0){
							isVisible = false;
							break;
						}
					}
					if(isVisible == true){
						mainapplet.noStroke();
						mainapplet.fill(playerColor);
						int[] playerlocation = this.boundsDetet(position.get(0), position.get(1));
						mainapplet.ellipse(location[0], location[1], diameter, diameter);
					}					
				} 				
			}*/	
			
			//Hunters		
			for(int i = 0; i < huntersMap.size(); i++){	
				ArrayList<Integer> position = new ArrayList<Integer>(2);
				position = (ArrayList<Integer>) huntersMap.get(i);
				float dst =  PApplet.dist(player.getX(), player.getY(), position.get(0), position.get(1));
				float deltaX = (float) (player.getX() - position.get(0))/ dst;
				float deltaY = (float) (player.getY() - position.get(1))/ dst;
				if( dst <= FieldOfView){
					boolean isVisible = true;
					for(int j = 0; j <= dst; j++) {
						if(collisionMap[(int)(j*deltaX)+ position.get(0)]
									   [(int)(j*deltaY)+ position.get(1)] > 0){
							isVisible = false;
							break;
						}
					}
					if(isVisible == true){
						mainapplet.noStroke();
						mainapplet.fill(0);
						mainapplet.ellipse(playerPosition[0] + position.get(0) - player.getX(), playerPosition[1] + position.get(1) - player.getY(), diameter, diameter);
					}					
				} 				
			}					
			
			/*
			 * Draw a circle field of view. To highlight mission's position, draw them here. 
			 */					
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
						shadowImage.line(playerPosition[0] + x, playerPosition[1] + y,
								playerPosition[0] + (FieldOfView-1)* MyApplet.cos( MyApplet.radians(i) ), 
								playerPosition[1] + (FieldOfView-1)* MyApplet.sin( MyApplet.radians(i) )
								);	
						break;
					}				
				}		
			}
			//Mission
			for(int i = 1; i <= mission.getPointNum(); i++){
				int x = location.get(i).get(0),
					y = location.get(i).get(1);
					if(location.get(i).get(2) != 0) continue;
				float dst =  PApplet.dist(player.getX(), player.getY(), x, y);
				if( dst <= FieldOfView){
					shadowImage.stroke(255, 0, 0);
					shadowImage.strokeWeight(2);
					shadowImage.line(playerPosition[0] + x - player.getX() - CrossLineLenght*2, playerPosition[1] + y - player.getY() - CrossLineLenght*2, 
									playerPosition[0] + x - player.getX() + CrossLineLenght*2, playerPosition[1] + y - player.getY() + CrossLineLenght*2);
					shadowImage.line(playerPosition[0] + x - player.getX() + CrossLineLenght*2, playerPosition[1] + y - player.getY() - CrossLineLenght*2,
									playerPosition[0] + x - player.getX() - CrossLineLenght*2, playerPosition[1] + y - player.getY() + CrossLineLenght*2);
					if(PApplet.dist(x, y, player.getX(), player.getY()) < 100){
						shadowImage.stroke(255, 0, 0);
						shadowImage.noFill();
						shadowImage.ellipse(playerPosition[0] + x - player.getX(), playerPosition[1] + y - player.getY(), radius*2, radius*2);
						if(!ani.isPlaying()){
							ani.start();
						}
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
			shadowImage.ellipse(playerPosition[0], playerPosition[1], FieldOfView*2, FieldOfView*2);
			shadowImage.endDraw();	
			mainapplet.image(shadowImage, 0, 0, MyApplet.width, MyApplet.height);			
		}
		/*
		 * Display Time
		 */		
		time = transmission.getTime();
		
		mainapplet.fill(0);
		mainapplet.textSize(20);
		mainapplet.text((int)time, 650, 50);
		
	}
	

}
