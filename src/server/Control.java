package server;

import java.util.Random;
import processing.core.PApplet;

public class Control implements Runnable{
	private Random ran;
	private Hunter hunter;
	private Map map;
	private int nextX, nextY;
	private int radius;
	private float radian;
	private float cos, sin;
	private int [][]collisionMap;
	
	Control(Map map, Hunter hunter) {
		this.ran = new Random();
		this.map = map;
		this.hunter = hunter;
		this.radius = 300;
		this.collisionMap = map.getCollisionMap();
	}
	
	private boolean judge_path() {
		for(int i=0; i<=radius; i++) {
			if(collisionMap[(int)(i*cos)+hunter.getX()][(int)(i*sin)+hunter.getY()]>0) return false;
		}
		return true;
	}
	
	private void generate() {
		while(true) {
			radian = PApplet.radians(90*ran.nextInt(4));
			//System.out.println(radian);
			cos = PApplet.cos( radian );
			sin = PApplet.sin( radian );
			nextX =  (int)(radius * cos) + hunter.getX();
			nextY =  (int)(radius * sin) + hunter.getY();
			if(map.inside(nextX, nextY)==true && judge_path()==true) break;
		}
	}
	
	public void start(){
		Thread w = new Thread(this);
		w.start();
	}
	
	public void run(){
		while(true) {
			generate();
			hunter.move(nextX, nextY);	
			//System.out.println(nextX + " " + nextY);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
