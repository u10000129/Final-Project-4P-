package server;

import java.util.HashMap;
import java.util.List;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PVector;

public class Hunter extends Character{
	public double speed = 0.007;
	private Map map;
	Ani aniX;
	Ani aniY;
	int moveX, moveY;
	int[][] collisionMap;
	View view;
	HashMap<Integer, List<Integer>> playersMap;
	boolean isHunting;
	
	private HunterGraph hunterGraph;
	private Boolean isOnTrack;
	private List<PVector> nextVertices;
	private PVector endOfPath;
		
	Hunter(Map map, HunterGraph h) {
		this.map = map;
		endOfPath = h.getRandomVertex();
		this.anchorX = (int)endOfPath.x;
		this.anchorY = (int)endOfPath.y;
		this.curX = this.anchorX;
		this.curY = this.anchorY;
		this.collisionMap = this.map.getCollisionMap();
		this.playersMap = null;
		this.isHunting = false;
		
		this.nextVertices = new java.util.ArrayList<PVector>();
		this.hunterGraph = h;
		this.isOnTrack = true;
		setNextVertices();
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
		//System.out.println("move hunter to "+moveX+" "+moveY);
	}
	
	public void speedy(){
		if(speed==0.0065) speed = 0.0045;
		else if(speed==0.0045) speed = 0.0065;
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
	
	private void setNextVertices() {
		
		//System.out.println("endOfPath: "+endOfPath.x+" "+endOfPath.y);
		nextVertices.addAll(hunterGraph.getRandomPathEdgeList(endOfPath));
		endOfPath = nextVertices.get(nextVertices.size()-1);
	}
	
	public PVector getNextVertex() {
		return nextVertices.get(0);
	}
	
	public void removeAndSetVertex() {
		
		nextVertices.remove(0);
		
		if(nextVertices.size()<3) {
			setNextVertices();
		}
	}
	
	public Boolean reachedNextVertex() {
		
		return (PApplet.dist(curX, curY, nextVertices.get(0).x, nextVertices.get(0).y) < 10);
	}
	
}
