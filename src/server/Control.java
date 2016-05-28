package server;

import java.util.Random;
import game.Bounds;
import processing.core.PApplet;

public class Control implements Runnable{
	private Random ran;
	private Hunter hunter;
	private Map map;
	private float X, Y;
	private int nextX, nextY;
	private int radius;
	private float radian;
	
	Control(Map map, Hunter hunter) {
		this.ran = new Random();
		this.map = map;
		this.hunter = hunter;
		this.radius = 300;
	}
	
	private void generate() {
		while(true) {
			radian = PApplet.radians(90*ran.nextInt(4));
			X =  radius * PApplet.cos( radian );
			//System.out.println(radian);
			//System.out.println(PApplet.cos( radian ));
			//System.out.println(X);
			Y =  radius * PApplet.sin( radian );
			nextX = (int)X; 
			nextY = (int)Y;
			if(map.inside(hunter.getX()+nextX, hunter.getY()+nextY)==true) break;
		}
	}
	
	public void start(){
		Thread w = new Thread(this);
		w.start();
	}
	 
	public void run(){
		while(true) {
			generate();
			Bounds hBound = map.horizontalWall(hunter.getX(), hunter.getY());
			Bounds vBound = map.verticalWall(hunter.getX(), hunter.getY());
			int moveX, moveY;
			
			//HorizonBound detect
			if(hBound == Bounds.LEFT){ // At Left bounds.
				moveX = nextX;
			}
			else if (hBound == Bounds.RIGHT){ // At Right bounds.
				moveX = map.getFullMap().width - MyApplet.width +  nextX;
			}
			else {
				moveX = hunter.getX() + nextX - MyApplet.width/2 ;
			}
			
			//verticalBound detect
			if(vBound == Bounds.UP){ // At Up bounds.
				moveY = nextY;
			}
			else if (vBound == Bounds.DOWN){ // At Down bounds.
				moveY = map.getFullMap().height - MyApplet.height + nextY;
			}
			else {
				moveY = hunter.getY()+ nextY - MyApplet.height/2;	
			}
			hunter.move(moveX, moveY);	
			
			System.out.println(moveX + " " + moveY);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
