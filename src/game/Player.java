package game;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import ddf.minim.*;

public class Player extends Character{
	public final static double speed = 0.004;
	private MyApplet parent;
	private Map map;
	private Minim minim;
	private AudioPlayer collide;
	Ani aniX;
	Ani aniY;
	int moveX, moveY;	//these are for collision detection
	int[][] collisionMap;
	
	private int ghostX, ghostY;
	
	Player(MyApplet parent, Map map, Minim minim){
		this.parent = parent;
		this.map = map;
		this.anchorX = 7010;
		this.anchorY = 7000;
		this.curX = this.anchorX;
		this.curY = this.anchorY;
		this.collisionMap = map.getCollisionMap();
		this.minim = minim;
		this.collide=this.minim.loadFile("res/Collision.wav");
	}
	
	public void setX(int X){
		curX=X;
	}
	
	public void setY(int Y){
		curY=Y;
	}

	@Override
	public int getX() {
		return curX;
	}

	@Override
	public int getY() {
		return curY;
	}
	
	public void move(int moveX, int moveY) {
		this.moveX = moveX;
		this.moveY = moveY;
		float distance = PApplet.dist(this.curX, this.curY, moveX, moveY);
		aniX = Ani.to(this, (float)(speed*distance), "curX", moveX, Ani.LINEAR);
		aniY = Ani.to(this, (float)(speed*distance), "curY", moveY, Ani.LINEAR);
	}
	
	public void collisionDetect() {
		if(curX>0 && curX<map.getImageWidth())
			if(curY>0 && curY<map.getImageHeight()) {
				/*
				if(collisionMap[curX+15][curY]>0) {
					curX-=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX-15][curY]>0) {
					curX+=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX][curY+15]>0) {
					curY-=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX][curY-15]>0) {
					curY+=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX+15][curY+15]>0) {
					curX-=20;curY-=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX-15][curY+15]>0) {
					curX+=20;curY-=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX+15][curY-15]>0) {
					curX-=20;curY+=20;
					this.collide.rewind();
					this.collide.play();
				}
				else if(collisionMap[curX-15][curY-15]>0) {
					curX+=20;curY+=20;
					this.collide.rewind();
					this.collide.play();
				}*/
				/*
				if(collisionMap[curX][curY]>0 || collisionMap[curX+10][curY+10]>0
						|| collisionMap[curX-10][curY-10]>0  || collisionMap[curX-10][curY+10]>0
						  || collisionMap[curX+10][curY-10]>0) {
					this.collide.rewind();
					this.collide.play();
					Ani.killAll();				
				    
				    	curX-=(moveX-curX)/10;				    
				    	curY-=(moveY-curY)/10;
				    	if(moveX>curX) curX-=5;
					    if(moveX<curX) curX+=5;
					    if(moveY>curY) curY-=5;
					    if(moveY<curY) curY+=5;
					    moveX = curX;
					    moveY = curY;
					    
				}
			}*/
				
		if(collisionMap[curX][curY]>0 || collisionMap[curX+2][curY+2]>0
				|| collisionMap[curX-2][curY-2]>0  || collisionMap[curX-2][curY+2]>0
				  || collisionMap[curX+2][curY-2]>0) {
			this.collide.rewind();
			this.collide.play();
			Ani.killAll();				
		    if(moveX>curX) curX-=20;
		    if(moveX<curX) curX+=20;
		    if(moveY>curY) curY-=20;
		    if(moveY<curY) curY+=20;
		    
		}
			}
	}
	
	public void stopAni(){
		Ani.killAll();
	}
	public void ghostMove(int moveX, int moveY){
		this.moveX = moveX;
		this.moveY = moveY;
		float distance = PApplet.dist(this.ghostX, this.ghostY, moveX, moveY);
		aniX = Ani.to(this, (float)(speed*distance), "ghostX", moveX, Ani.LINEAR);
		aniY = Ani.to(this, (float)(speed*distance), "ghostY", moveY, Ani.LINEAR);
	}
	public void setGhostPosition(int x, int y){
		this.ghostX = x;
		this.ghostY = y;		
	}
	public int getGhostX() {
		return ghostX;
	}
	public int getGhostY() {
		return ghostY;
	}
}

