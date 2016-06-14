package server;

import java.util.Random;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;

public class Control implements Runnable{
	private Random ran;
	private ArrayList<Hunter> hunters;
	private int hunterNum;
	private Map map;
	private int []nextX, nextY;
	private int radius;
	private float [] radian;
	private float [] cos, sin;
	private int [][] collisionMap;
	private int [] huntingMap;
	private HashMap<Integer, Integer> playersLife;
	//private HashMap<Integer, List<Integer>> hunter_information;
	private HashMap<Integer, List<Integer>> playersMap;
	//private List<Integer> list;
	private View view;
	
	Control(Map map, ArrayList<Hunter> hunters, int hunterNum, View view) {
		this.ran = new Random();
		this.map = map;
		this.hunters = hunters;
		this.hunterNum = hunterNum;
		this.radius = 300;
		this.collisionMap = map.getCollisionMap();
		this.radian = new float[this.hunterNum];
		this.cos = new float[this.hunterNum];
		this.sin = new float[this.hunterNum];
		this.nextX = new int[this.hunterNum];
		this.nextY = new int[this.hunterNum];
		this.huntingMap = new int[this.hunterNum];
		for(int i=0; i<this.hunterNum; i++) 
			this.huntingMap[i]=0;
		this.view = view;
		this.playersMap=null;
		this.playersLife=null;
	}
	
	
	public void setPlayersMap(HashMap<Integer, List<Integer>> playersMap){
		if(playersMap!=null) this.playersMap = playersMap;
	}
	
	public void setPlayersLife(HashMap<Integer, Integer> playersLife) {
		if(playersLife!=null) this.playersLife = playersLife;
	}
	
	private boolean judge_path(int index) {
		for(int i=0; i<=radius; i++) {
			if(this.map.inside((int)(i*cos[index])+hunters.get(index).getX(), 
					(int)(i*sin[index])+hunters.get(index).getY())==false) return false;
			if(collisionMap[(int)(i*cos[index])+
			                hunters.get(index).getX()][(int)(i*sin[index])
			                                   +hunters.get(index).getY()]>0) return false;
		}
		return true;
	}
	
	
	//check if hunter should chase player
	private int hunting_start(int index){
		if(this.playersMap==null) return -1;
		for(int i=0; i<this.playersMap.size(); i++) {
			if(this.playersLife.get(i)==0) continue;
			if(this.huntingMap[index]<0) {
				this.huntingMap[index]++;
				continue;
			}
			if(this.huntingMap[index]==5) {
				this.huntingMap[index]=-3;
				continue;
			}
			/* judge whether player is inside hunter's sight */
			float distance = PApplet.dist(this.hunters.get(index).getX(), this.hunters.get(index).getY()
											,this.playersMap.get(i).get(0), this.playersMap.get(i).get(1));
			if(distance<=this.view.FieldOfView) {
				/* judge whether player is reachable */
				Point hunter = new Point(hunters.get(index).getX(), hunters.get(index).getY());
				Point player = new Point(playersMap.get(i).get(0), playersMap.get(i).get(1));
				double angle = Math.atan2(hunter.y-player.y, hunter.x-player.x);
				for(int j=0; j<distance; j++) {
					float x = j * PApplet.cos((float)angle);
					float y = j * PApplet.sin((float)angle);
					if(this.collisionMap[(int)(hunter.x+x)][(int)(hunter.y+y)]==1) continue;
					if(hunters.get(index).isHunting==false) {
						hunters.get(index).setHuntState(true);
						hunters.get(index).speedy();
					}
					this.huntingMap[index]++;
					return i;
				}
			}
		}
		if(hunters.get(index).isHunting==true) {
			hunters.get(index).setHuntState(false);
			hunters.get(index).speedy();
		}
		return -1;
	}
	
	private void generate(int index) {
		
		int target=hunting_start(index);
		if(target==-1) {	//not discovered
			
			if(hunters.get(index).reachedNextVertex()) {
				hunters.get(index).removeAndSetVertex();
				PVector v = hunters.get(index).getNextVertex();
				hunters.get(index).move((int)v.x, (int)v.y);
			}
			
		}
		else {		//player discovered
			chasePlayer(index, target);
		}
	}
	
	private void walkRandomly(int index) {
		
		while(true) {			
			radian[index] = PApplet.radians(90*ran.nextInt(4));
			cos[index] = PApplet.cos( radian[index] );
			sin[index] = PApplet.sin( radian[index] );
			nextX[index] =  (int)(radius * cos[index]) + hunters.get(index).getX();
			nextY[index] =  (int)(radius * sin[index]) + hunters.get(index).getY();
			if(map.inside(nextX[index], nextY[index])==true)
				if(judge_path(index)==true) 
					break;
		}
	}
	
	private void chasePlayer(int index, int target) {
		
		while(true) {
			
			nextX[index] =  playersMap.get(target).get(0);
			nextY[index] =  playersMap.get(target).get(1);
			if(map.inside(nextX[index], nextY[index])==true) break;
		}
	}
	
	public void start(){
		Thread w = new Thread(this);
		w.start();
	}
	
	public void run(){
		while(true) {
			for(int i=0; i<hunterNum; i++) {
				generate(i);
				//hunters.get(i).move(nextX[i], nextY[i]);	
			}
			
			//System.out.println(nextX + " " + nextY);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
