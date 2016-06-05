package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import ddf.minim.*;
import server.JSON;

@SuppressWarnings("serial")
public class MyApplet extends PApplet implements Observer{
	
	public final int JEWEL_NOT_OPENED = 0;
	private Player player;
	private View view;
	private Map map;
	private Transmission transmission;
	private Mission mission;
	public final static double speed = 0.004;
	public final static int width = 800, height = 600;
	private Minim minim;
	private AudioPlayer bgm, click;
	private AudioPlayer CorrectAnswer, WrongAnswer;
	private int missionScore;
	private int jewelID;
	private boolean isAnswering = false;
	
	public JSON json;
	public String jsonString;
	public HashMap<Integer, List<Integer>> playersMap;
	public HashMap<Integer, String> playersName;
	public HashMap<Integer, Integer> playersLife;
	public HashMap<Integer, List<Integer>> huntersMap;
	//public HashMap<Integer, List<Integer>> jewelsMap;
	public boolean gameStatus;
	public long time = 0;
	private String name;
	public int lifeStatus = 1;	
	
	public int myId;
	ArrayList<Integer> position = new ArrayList<Integer>(2);
	
	public MyApplet(Transmission transmission, String name) {
		this.transmission = transmission;
		this.name = name;
		System.out.println(name);
	}

	public void setup(){
		
		size(width, height);
		Ani.init(this);
		map = new Map(this);
		minim = new Minim(this);
		missionScore = 0;
		mission = new Mission();
		player = new Player(this, map, minim);
		view = new View(this, map, player, transmission, mission);
		smooth();
		bgm=minim.loadFile("res/Sugar_Zone.mp3");
		click=minim.loadFile("res/Fire_Ball.mp3");
		CorrectAnswer = minim.loadFile("res/CorrectAnswer.mp3");
		WrongAnswer = minim.loadFile("res/WrongAnswer.mp3");		
		bgm.loop();
		
		myId = transmission.getMyId();	
		transmission.setName(name);
		transmission.sendMessage("ready");
		
		jewelID = JEWEL_NOT_OPENED;
	}
	
	public void draw(){
		if(mousePressed && !isAnswering){
			if(lifeStatus == 0) {
				int[] move = this.boundsDetet(mouseX, mouseY, player.getGhostX(), player.getGhostY());
				player.ghostMove(move[0], move[1]);	
				
			} else {
				int[] move = this.boundsDetet(mouseX, mouseY, player.getX(), player.getY());
				player.move(move[0], move[1]);	
			}			
			
		}
		if(mission.checkMissionSet(jewelID)) {
			setJewelID(JEWEL_NOT_OPENED);
		}
		
		gameStatus = transmission.getGameStatus();
		if(gameStatus) {
			background(255);				
			view.display();
				
			transmission.setJewelId(jewelID);
			transmission.setMyPosition(player.getX(), player.getY());			
			transmission.setHunters(huntersMap);
			mission.setLocation(transmission.getJewel());
			transmission.setLifeStatus(lifeStatus);
			transmission.setSpeed(speed);
						
			
		}	else {

			background(221, 79, 67);
			fill(0);
			textSize(30);
			text("Waiting for other clients...", MyApplet.width/2-StartScreen.btnWidth, MyApplet.height/2);
		}
	}
	
	public void mousePressed(){
		if(lifeStatus == 0) return;
		/* Play sound */
		click.rewind();
		click.play();	
		
	}
	
	
	public void keyPressed() {
		if(lifeStatus == 0) return;
		if(key == ' ') {
			int x = player.getX();
			int y = player.getY();
			java.util.Map<Integer, List<Integer>> locations = mission.getLocation();
			
			
			for(Entry<Integer, List<Integer>> entry : locations.entrySet()) {
				if(PApplet.dist(entry.getValue().get(0),entry.getValue().get(1),x,y)<100 && entry.getValue().get(2)==0) {
					
					isAnswering = true;
					jewelID = entry.getKey();	//update the opened jewel ID
					QuestionPanel qPanel = new QuestionPanel(mission);
					qPanel.addObserver(this);
					Main.window.setContentPane(qPanel.getQApplet());
				}
			}
		}
	}
	
	public void update(Observable obs, Object o) {
		if(obs.getClass() == QuestionPanel.class) {
			
			if((Boolean) o == true) {
				missionScore++;
				CorrectAnswer.rewind();
				CorrectAnswer.play();
			} else {
				WrongAnswer.rewind();
				WrongAnswer.play();
			}
			Main.window.setContentPane(this);
			isAnswering = false;
		}
	}
	
	public int getMissionScore() {
		return missionScore;
	}
	
	public void setJewelID(int id) {
		jewelID = id;
	}
	
	public int getJewelID() {
		return jewelID;
	}
	
	private int[] boundsDetet(int x, int y, int fx, int fy){
		int returnX, returnY;
		Bounds hBound = map.horizontalWall(fx, fy);
		Bounds vBound = map.verticalWall(fx, fy);
		//HorizonBound detect
		if(hBound == Bounds.LEFT){ // At Left bounds.
			returnX = x;
		}
		else if (hBound == Bounds.RIGHT){ // At Right bounds.
			returnX = map.getFullMap().width - MyApplet.width +  x;
		}
		else {
			returnX = fx + x - MyApplet.width/2 ;
		}
		
		//verticalBound detect
		if(vBound == Bounds.UP){ // At Up bounds.
			returnY = y;
		}
		else if (vBound == Bounds.DOWN){ // At Down bounds.
			returnY = map.getFullMap().height - MyApplet.height + y;
		}
		else {
			returnY = fy + y - MyApplet.height/2;	
		}
		return  new int[] {returnX, returnY}; 
	}
	
	public void setLifeStatus (int life){
		this.lifeStatus = life;
	}
	public int getLifeStatus (){
		return this.lifeStatus;
	}
}
