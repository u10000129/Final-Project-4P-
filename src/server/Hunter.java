package server;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

public class Hunter extends Character{
	public static final double speed = 0.005;
	private MyApplet parent;
	private Map map;
	Ani aniX;
	Ani aniY;
	int moveX, moveY;
	int[][] collisionMap;
	
	Hunter(MyApplet parent, Map map) {
		this.parent = parent;
		this.map = map;
		this.anchorX = 5000;
		this.anchorY = 5010;
		this.curX = this.anchorX;
		this.curY = this.anchorY;
		this.collisionMap = map.getCollisionMap();
	}

	@Override
	int getX() {
		// TODO Auto-generated method stub
		return curX;
	}

	@Override
	int getY() {
		// TODO Auto-generated method stub
		return curY;
	}
	
	public void move(int moveX, int moveY){
		this.moveX = moveX;
		this.moveY = moveY;
		float distance = PApplet.dist(this.curX, this.curY, moveX, moveY);
		aniX = Ani.to(this, (float)(speed*distance), "curX", moveX, Ani.LINEAR);
		aniY = Ani.to(this, (float)(speed*distance), "curY", moveY, Ani.LINEAR);
	}
	
	public void collisionDetect() {				
		if(collisionMap[curX][curY]>0) {			
			Ani.killAll();				
		    if(moveX>curX) curX-=10;
		    if(moveX<curX) curX+=10;
		    if(moveY>curY) curY-=10;
		    if(moveY<curY) curY+=10;
		    
		}
	}
}
