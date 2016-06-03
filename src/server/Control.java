package server;

import java.util.Random;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Control implements Runnable{
	private final static int FieldOfView = 250;
	private Random ran;
	private ArrayList<Hunter> hunters;
	private int hunterNum;
	private Map map;
	private int []nextX, nextY;
	private int radius;
	private float [] radian;
	private float [] cos, sin;
	private int [][] collisionMap;
	private HashMap<Integer, List<Integer>> hunter_information;
	private HashMap<Integer, List<Integer>> playersMap;
	private List<Integer> list;
	
	Control(Map map, ArrayList<Hunter> hunters, int hunterNum) {
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
		this.hunter_information = new HashMap<Integer, List<Integer>>();
		this.list = new ArrayList<Integer>();
	}
	
	public HashMap<Integer, List<Integer>> getHunterInformation(){
		for(int i=0; i<hunterNum; i++) {
			this.list=new ArrayList<Integer>();
			this.list.add(hunters.get(i).getX());
			this.list.add(hunters.get(i).getY());
			this.hunter_information.put(i, this.list);
		}
		return hunter_information;
	}
	
	public void setPlayersMap(HashMap<Integer, List<Integer>> playersMap){
		this.playersMap = playersMap;
	}
	
	private boolean judge_path(int index) {
		for(int i=0; i<=radius; i++) {
			if(collisionMap[(int)(i*cos[index])+
			                hunters.get(index).getX()][(int)(i*sin[index])
			                                   +hunters.get(index).getY()]>0) return false;
		}
		return true;
	}
	
	private void generate(int index) {
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
	
	public void start(){
		Thread w = new Thread(this);
		w.start();
	}
	
	public void run(){
		while(true) {
			for(int i=0; i<hunterNum; i++) {
				generate(i);
				hunters.get(i).move(nextX[i], nextY[i]);	
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
