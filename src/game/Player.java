package game;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import ddf.minim.*;

public class Player extends Character{
	public final static double speed = 0.006;
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
		this.anchorX = 5800;
		this.anchorY = 6000;
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
				
		if(collisionMap[curX][curY]>0) {
			this.collide.rewind();
			this.collide.play();
			Ani.killAll();				
		    if(moveX>curX) curX-=10;
		    if(moveX<curX) curX+=10;
		    if(moveY>curY) curY-=10;
		    if(moveY<curY) curY+=10;
		    
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

