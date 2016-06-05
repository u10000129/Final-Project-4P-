package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;


public class View {
	private final int diameter = 40;
	private int FieldOfView = 250;
	private final int CrossLineLenght = 5;
	private MyApplet mainapplet;
	private Player player;
	private Map map;
	private PImage mapImage;
	
	private Mission mission;
	private java.util.Map<Integer, List<Integer>> location;
	
	private Transmission transmission;	
	private HashMap<Integer, List<Integer>> playersMap;
	private HashMap<Integer, List<Integer>> huntersMap;
	private HashMap<Integer, String> playersName;	
	public HashMap<Integer, Integer> playersLife;
	private long time = 0;
	
	private Ani missionCircleAni;
	private int missionCircle = 0;
	private Ani playerCircleAni;
	private int playerCircle = 0;
	
	private Minim minim;
	private AudioPlayer die;
	private Ani dieTextAni;
	private int dieTextHeight = 0;
	

	public View (MyApplet mainapplet, Map map, Player player, Transmission transmission, Mission mission) {
		this.mainapplet = mainapplet;
		this.map =  map;
		this.player = player;
		this.mission = mission;
		this.location = mission.getLocation();
		this.transmission = transmission;
		this.missionCircleAni =  Ani.to(this, (float) 0.5, "missionCircle", 30, Ani.LINEAR);
		this.playerCircleAni =  Ani.to(this, (float) 0.5, "playerCircle", 50, Ani.LINEAR);
		this.dieTextAni = Ani.to(this, (float) 1, "dieTextHeight", 300, Ani.ELASTIC_OUT);
		
		minim = new Minim(mainapplet);	
		die = minim.loadFile("res/Die.mp3");
	}
	
	private int transformX(int x) {
		return x * MyApplet.width / map.getImageWidth();
	}
	
	private int transformY(int y) {
		return y * (MyApplet.height-50) / map.getImageHeight();
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
	
	private void setColor(int i){
		switch(i) {
		case 0:
			mainapplet.fill(200, 0, 200, 160);
			mainapplet.stroke(200, 0, 200, 160);
			break;
		case 1:
			mainapplet.fill(0, 200, 0, 160);
			mainapplet.stroke(0, 200, 0, 160);
			break;
		case 2:
			mainapplet.fill(0, 0, 200, 160);
			mainapplet.stroke(0, 0, 200, 160);
			break;
		case 3:
			mainapplet.fill(200, 200, 0, 160);
			mainapplet.stroke(200, 200, 0, 160);
			break;
		}
	}
	
	public void display(){		
		
		/**
		 *  Is dead.
		 */
		if(mainapplet.getLifeStatus() == 0){ 				
			this.location = mission.getLocation();
			playersMap = transmission.getPlayers();
			huntersMap = transmission.gethunters();	
			playersName = transmission.getPlayersName();
			playersLife = transmission.getPlayersLife();
			int myPlayerId = transmission.getMyId();
			if(mainapplet.keyPressed && mainapplet.key == '\t') { //Display full map.
				/*
				 * Draw full map.
				 */
				mainapplet.image(map.getFullMap(), 0, 0, MyApplet.width, MyApplet.height-50);	
				/*
				 * Draw my player in full map.
				 */
				
				this.setColor(transmission.getMyId());
				mainapplet.ellipse(transformX(player.getGhostX()), transformY(player.getGhostY()), diameter/4, diameter/4);
				mainapplet.textSize(8);
				mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
				if(myPlayerId +1 <= playersName.size())
					mainapplet.text(playersName.get(transmission.getMyId()), transformX(player.getGhostX())+diameter/4/2, transformY(player.getGhostY())-diameter/4/2);
				mainapplet.ellipse(20, 100+ 15*transmission.getMyId(), diameter/4, diameter/4);
				mainapplet.textSize(12);
				if(myPlayerId +1 <= playersName.size())
					mainapplet.text(playersName.get(transmission.getMyId()), 30, 100+ 15*transmission.getMyId());
				mainapplet.line(20, 100 + 15*transmission.getMyId(), 20 + 10 + mainapplet.textWidth(playersName.get(transmission.getMyId())), 100 + 15*transmission.getMyId());

				mainapplet.noFill();
				mainapplet.ellipse(transformX(player.getGhostX()), transformY(player.getGhostY()), playerCircle/2, playerCircle/2);
				if(!playerCircleAni.isPlaying()){
					playerCircleAni.start();
				}
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
						mainapplet.textSize(16);
						mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
						mainapplet.text(location.get(i).get(2), x, y);	
					}
					
					
					
				}
				/*
				 * Draw other players in full map.
				 */
				for(int i = 0; i < playersMap.size(); i++){		
					if(i == myPlayerId) continue;
					ArrayList<Integer> position = new ArrayList<Integer>(2);
					position = (ArrayList<Integer>) playersMap.get(i);
					int trsX = transformX( position.get(0) );
					int trsY = transformY( position.get(1) );
					mainapplet.noStroke(); 		
					this.setColor(i);
					mainapplet.ellipse(trsX, trsY, diameter/4, diameter/4);
					mainapplet.textSize(8);
					mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
					mainapplet.text(playersName.get(i), trsX+diameter/4/2, trsY-diameter/4/2);
					
					mainapplet.ellipse(20, 100 + 15*i, diameter/4, diameter/4);
					mainapplet.textSize(12);
					mainapplet.text(playersName.get(i), 30, 100+ 15*i);
					if(playersLife.get(i) == 0) mainapplet.line(20, 100 + 15*i, 20 + 10 + mainapplet.textWidth(playersName.get(i)), 100 + 15*i);
				}
				
			} else { // Display local map.
				
				/*
				 * Draw map image.
				 */
				mapImage = map.getSubMap(player.getGhostX(), player.getGhostY());
				mainapplet.image(mapImage, 0, 0, MyApplet.width, MyApplet.height);	
				/*
				 * Draw my player.
				 */
				int[] playerPosition = this.boundsDetet(player.getGhostX(), player.getGhostY());	
				this.setColor(transmission.getMyId());
				mainapplet.ellipse(playerPosition[0], playerPosition[1], diameter, diameter);
				mainapplet.textSize(16);
				mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
				if(myPlayerId +1 <= playersName.size())
					mainapplet.text(playersName.get(myPlayerId), playerPosition[0]+diameter/2, playerPosition[1]-diameter/2);
				mainapplet.noFill();
				mainapplet.ellipse(playerPosition[0], playerPosition[1], playerCircle, playerCircle);
				if(!playerCircleAni.isPlaying()){
					playerCircleAni.start();
				}
				/*
				 * Draw players, hunters and msiions.
				 */
					
				//Players						
				for(int i = 0; i < playersMap.size(); i++){		
					if(i == myPlayerId) continue;
					ArrayList<Integer> position = new ArrayList<Integer>(2);
					position = (ArrayList<Integer>) playersMap.get(i);
					mainapplet.noStroke();
					this.setColor(i);
					int x = playerPosition[0] + position.get(0) - player.getGhostX();
					int y = playerPosition[1] + position.get(1) - player.getGhostY();
					mainapplet.ellipse(x, y, diameter, diameter);
					mainapplet.textSize(16);
					mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
					mainapplet.text(playersName.get(i), x + diameter/2, y - diameter/2);					 				
				}	
				
				//Hunters		
				for(int i = 0; i < huntersMap.size(); i++){	
					ArrayList<Integer> position = new ArrayList<Integer>(2);
					position = (ArrayList<Integer>) huntersMap.get(i);
					mainapplet.noStroke();
					mainapplet.fill(0);
					mainapplet.ellipse(playerPosition[0] + position.get(0) - player.getGhostX(), playerPosition[1] + position.get(1) - player.getGhostY(), diameter, diameter);
					mainapplet.fill(255);
					mainapplet.textSize(32);
					mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
					mainapplet.text("H", playerPosition[0] + position.get(0) - player.getGhostX(), playerPosition[1] + position.get(1) - player.getGhostY() - 5);
									
				}				
							
				//Mission
				for(int i = 1; i <= mission.getPointNum(); i++){
					int x = playerPosition[0] + location.get(i).get(0) - player.getGhostX(),
						y = playerPosition[1] + location.get(i).get(1) - player.getGhostY();
					if(location.get(i).get(2) != 0) {
						mainapplet.fill(150, 0, 0);
						mainapplet.textSize(16);
						mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
						mainapplet.text(location.get(i).get(2), x, y);	
						
					} else {
						mainapplet.stroke(255, 0, 0);
						mainapplet.strokeWeight(2);
						mainapplet.line(x - CrossLineLenght*2, y - CrossLineLenght*2, 
										x + CrossLineLenght*2, y + CrossLineLenght*2);
						mainapplet.line(x + CrossLineLenght*2, y - CrossLineLenght*2,
										x - CrossLineLenght*2, y + CrossLineLenght*2);
					}		
				}			
				// Cover a shadow full of window.
				mainapplet.fill(0, 128);			
				mainapplet.rect(0, 0, MyApplet.width, MyApplet.height);	
				//Show die text.
				mainapplet.fill(255, 0, 0);
				mainapplet.textSize(50);
				mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
				mainapplet.text("You have been killed...", MyApplet.width/2, dieTextHeight);
			}
		
			
		/**
		 *  Is Alive.
		 */
		} else {
			if(FieldOfView < 450) // FieldOfView will get larger when get score.
				FieldOfView = ( mainapplet.getMissionScore()/3 )*50 +250;
			//Update data.
			this.location = mission.getLocation();
			playersMap = transmission.getPlayers();
			huntersMap = transmission.gethunters();	
			playersName = transmission.getPlayersName();
			playersLife = transmission.getPlayersLife();
			int myPlayerId = transmission.getMyId();
			/*
			 * If user press TAB, display full map, or display local map.
			 */
			if(mainapplet.keyPressed && mainapplet.key == '\t') { //Display full map.
				/*
				 * Draw full map.
				 */
				mainapplet.image(map.getFullMap(), 0, 0, MyApplet.width, MyApplet.height-50);	
				/*
				 * Draw my player in full map.
				 */
				player.collisionDetect();
				this.setColor(transmission.getMyId());
				mainapplet.ellipse(transformX(player.getX()), transformY(player.getY()), diameter/4, diameter/4);
				mainapplet.textSize(8);
				mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
				if(myPlayerId +1 <= playersName.size())
					mainapplet.text(playersName.get(transmission.getMyId()), transformX(player.getX())+diameter/4/2, transformY(player.getY())-diameter/4/2);
				mainapplet.ellipse(20, 100+ 15*transmission.getMyId(), diameter/4, diameter/4);
				mainapplet.textSize(12);
				if(myPlayerId +1 <= playersName.size())
					mainapplet.text(playersName.get(transmission.getMyId()), 30, 100+ 15*transmission.getMyId());
				mainapplet.noFill();
				mainapplet.ellipse(transformX(player.getX()), transformY(player.getY()), playerCircle/2, playerCircle/2);
				if(!playerCircleAni.isPlaying()){
					playerCircleAni.start();
				}
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
						//If player get close to this position, draw a scaling up circle to cover it .
						if(PApplet.dist(location.get(i).get(0), location.get(i).get(1), player.getX(), player.getY()) < 100){
							mainapplet.stroke(255, 0, 0);
							mainapplet.noFill();
							mainapplet.ellipse(x, y, missionCircle, missionCircle);
							if(!missionCircleAni.isPlaying()){
								missionCircleAni.start();
							}
						}
															
					}
					else {
						mainapplet.fill(150, 0, 0);
						mainapplet.textSize(16);
						mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
						mainapplet.text(location.get(i).get(2), x, y);	
					}		
				}
				/*
				 * Draw other players in full map.
				 */
				
				for(int i = 0; i < playersMap.size(); i++){		
					if(i == myPlayerId) continue;
					ArrayList<Integer> position = new ArrayList<Integer>(2);
					position = (ArrayList<Integer>) playersMap.get(i);
					int trsX = transformX( position.get(0) );
					int trsY = transformY( position.get(1) );
					mainapplet.noStroke(); 		
					this.setColor(i);
					mainapplet.ellipse(trsX, trsY, diameter/4, diameter/4);
					mainapplet.textSize(8);
					mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
					mainapplet.text(playersName.get(i), trsX+diameter/4/2, trsY-diameter/4/2);
					mainapplet.ellipse(20, 100 + 15*i, diameter/4, diameter/4);
					mainapplet.textSize(12);
					mainapplet.text(playersName.get(i), 30, 100+ 15*i);
					if(playersLife.get(i) == 0) mainapplet.line(20, 100 + 15*i, 20 + 10 + mainapplet.textWidth("playersName.get(i)"), 100 + 15*i);
				
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
				this.setColor(transmission.getMyId());
				player.collisionDetect();
				mainapplet.ellipse(playerPosition[0], playerPosition[1], diameter, diameter);
				mainapplet.textSize(16);
				mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
				if(myPlayerId +1 <= playersName.size())
					mainapplet.text(playersName.get(myPlayerId), playerPosition[0]+diameter/2, playerPosition[1]-diameter/2);
				mainapplet.noFill();
				mainapplet.ellipse(playerPosition[0], playerPosition[1], playerCircle, playerCircle);
				if(!playerCircleAni.isPlaying()){
					playerCircleAni.start();
				}
				/*
				 * Draw players and hunters.
				 */
				int[][] collisionMap = map.getCollisionMap();	
				//Players						
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
							this.setColor(i);
							int x = playerPosition[0] + position.get(0) - player.getX();
							int y = playerPosition[1] + position.get(1) - player.getY();
							mainapplet.ellipse(x, y, diameter, diameter);
							mainapplet.textSize(16);
							mainapplet.textAlign(MyApplet.LEFT, MyApplet.CENTER);
							mainapplet.text(playersName.get(i), x + diameter/2, y - diameter/2);
						}					
					} 				
				}	
				
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
							mainapplet.fill(255);
							mainapplet.textSize(32);
							mainapplet.textAlign(MyApplet.CENTER, MyApplet.CENTER);
							mainapplet.text("H", playerPosition[0] + position.get(0) - player.getX(), playerPosition[1] + position.get(1) - player.getY() - 5);
						}					
					}
					//If player is caught.
					if(dst <= 10){
						dieTextAni.start();
						die.rewind();
						die.play();
						player.stopAni();
						player.setGhostPosition(player.getX(), player.getY());
						mainapplet.setLifeStatus(0);
					}
				}					
				
				/*
				 * Draw a circle field of view. To highlight mission's position, draw them here. 
				 */					
				PGraphics shadowImage;
				//Scan the circle field of view. If there is collision , draw a line to cover the area ,which  means that the area is invisible.			 
				shadowImage = mainapplet.createGraphics(MyApplet.width, MyApplet.height);
				shadowImage.beginDraw();
				for(float i = 0; i < 360; i+=0.5) {
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
							shadowImage.ellipse(playerPosition[0] + x - player.getX(), playerPosition[1] + y - player.getY(), missionCircle*2, missionCircle*2);
							if(!missionCircleAni.isPlaying()){
								missionCircleAni.start();
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
					
		
		}
		
		
			
		/*
		 * Display Time and Score
		 */		
		time = transmission.getTime();		
		mainapplet.fill(0, 255, 0);
		mainapplet.textSize(15);
		mainapplet.textAlign(MyApplet.LEFT, MyApplet.BOTTOM);
		mainapplet.text("t", 40, 40);
		mainapplet.textSize(30);
		mainapplet.textAlign(MyApplet.RIGHT, MyApplet.BOTTOM);
		mainapplet.text(mainapplet.getMissionScore() + "P", 40, 40);		
		if(FieldOfView < 450)
			mainapplet.text("Time: " + (int)time + "    FieldOfView: " + this.FieldOfView , 780, 40);
		else
			mainapplet.text("Time: " + (int)time + "    FieldOfView: " + "MAX", 780, 40);
	}
	

}
