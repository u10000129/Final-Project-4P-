package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import ddf.minim.*;
import server.JSON;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private StartScreen startScreen;
	private Player player;
	private View view;
	private Map map;
	private Transmission transmission;
	public final static double speed = 0.004;
	public final static int width = 800, height = 600;
	private Minim minim;
	private AudioPlayer bgm, click;
	
	public JSON json;
	public String jsonString;
	public HashMap<Integer, List<Integer>> playersMap;
	public HashMap<Integer, List<Integer>> huntersMap;
	public HashMap<Integer, List<Integer>> jewelsMap;
	public boolean gameStatus;
	public long time = 0;
	
	public int myId;
	private Boolean firstTime;
	
	public MyApplet(Transmission transmission) {
		this.transmission = transmission;
	}

	public void setup(){
		size(width, height);
		Ani.init(this);
		startScreen = new StartScreen(this);
		map = new Map(this);
		minim = new Minim(this);
		player = new Player(this, map, minim);
		view = new View(this, map, player, transmission);
		smooth();
		bgm=minim.loadFile("res/Sugar_Zone.mp3");
		click=minim.loadFile("res/Fire_Ball.mp3");
		bgm.loop();
		firstTime = true;
		//gameStatus = transmission.getGameStatus();
		//transmission.receiveMessage();
		//transmission.sendMessage("id reveived : "+myId);
		myId = transmission.getMyId();		
	}
	
	public void draw(){
		
		startScreen.display(gameStatus);
		
		if(startScreen.getStartPressed()) {
			if(firstTime) {
				transmission.sendMessage("ready");		
				firstTime = false;
			}
			else {
			gameStatus = transmission.getGameStatus();
				if(gameStatus) {
					background(255);				
					view.display();
				
					jewelsMap = transmission.getJewel();
					transmission.setMyPosition(player.getX(), player.getY());			
					transmission.setHunters(huntersMap);
					transmission.setJewel(jewelsMap);
				}
			}
		}
	}
	
	public void mousePressed(){
		/* Play sound */
		click.rewind();
		click.play();
		Bounds hBound = map.horizontalWall(player.getX(), player.getY());
		Bounds vBound = map.verticalWall(player.getX(), player.getY());
		int moveX, moveY;
		
		//HorizonBound detect
		if(hBound == Bounds.LEFT){ // At Left bounds.
			moveX = mouseX;
		}
		else if (hBound == Bounds.RIGHT){ // At Right bounds.
			moveX = map.getFullMap().width - MyApplet.width +  mouseX;
		}
		else {
			moveX = player.getX() + mouseX - MyApplet.width/2 ;
		}
		
		//verticalBound detect
		if(vBound == Bounds.UP){ // At Up bounds.
			moveY = mouseY;
		}
		else if (vBound == Bounds.DOWN){ // At Down bounds.
			moveY = map.getFullMap().height - MyApplet.height + mouseY;
		}
		else {
			moveY = player.getY()+ mouseY - MyApplet.height/2;	
		}
		player.move(moveX, moveY);		
		
	}
}
