package game;

import de.looksgood.ani.Ani;

public class Player extends Character{
	public final static double speed = 0.004;
	private MyApplet parent;
	private Map map;
	Ani aniX;
	Ani aniY;
	Player(MyApplet parent, Map map ){
		this.parent = parent;
		this.map = map;
		this.anchorX = 7010;
		this.anchorY = 7000;
		this.curX = this.anchorX;
		this.curY = this.anchorY;
	}
	
	public void display(){
		this.collisionDetect();
		this.parent.fill(0, 255);
		this.parent.noStroke(); 		
		this.parent.ellipse(this.curX, this.curY, 40, 40);
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
		float distance = parent.dist(this.curX, this.curY, moveX, moveY);
		aniX = Ani.to(this, (float)(speed*distance), "curX", moveX, Ani.LINEAR);
		aniY = Ani.to(this, (float)(speed*distance), "curY", moveY, Ani.LINEAR);
	}
	
	public void collisionDetect() {		
		int[][] collisionMap = map.getSubCollisionMap(curX,curY);
		if(collisionMap[400][300]>0) {
			System.out.println(collisionMap[400][300]);
			aniX.pause();
		    aniY.pause();
		}
	}
}
