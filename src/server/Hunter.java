package server;

import java.util.HashMap;
import java.util.List;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

public class Hunter extends Character{
	public double speed = 0.005;
	private Map map;
	Ani aniX;
	Ani aniY;
	int moveX, moveY;
	int[][] collisionMap;
	View view;
	HashMap<Integer, List<Integer>> playersMap;
	boolean isHunting;
		
	Hunter(Map map, int x, int y) {
		this.map = map;
		this.anchorX = x;
		this.anchorY = y;
		this.curX = this.anchorX;
		this.curY = this.anchorY;
		this.collisionMap = this.map.getCollisionMap();
		this.playersMap = null;
		this.isHunting = false;
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
	
	public void speedy(){
		if(speed==0.005) speed = 0.003;
		else if(speed==0.003) speed = 0.005;
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
	
	public void setPlayersMap(HashMap<Integer, List<Integer>> playersMap){
		if(playersMap!=null) this.playersMap = playersMap;
	}
	
	public void setHuntState(boolean flag) {
		this.isHunting = flag;
	}
	
	public void huntingDetect(){
		if(this.playersMap!=null) {
			for(int i=0; i<this.playersMap.size(); i++) {
				if(PApplet.dist(curX, curY, this.playersMap.get(i).get(0), this.playersMap.get(i).get(0))<view.FieldOfView) {
					if(this.isHunting==false) Ani.killAll();
				}
			}
		}
	}
}
