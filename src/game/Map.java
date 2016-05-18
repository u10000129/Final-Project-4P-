package game;

import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

public class Map {
	private PApplet parent;
	private PImage map;
	private int[][] collisionMap; 
	
	public Map(PApplet p) {
		parent = p;
		map = parent.requestImage("SubMap.jpg");
		
	}
	
	public Bounds horizontalWall(int x) {
		if(x+MyApplet.width/2 >= map.width)
			return Bounds.RIGHT;
		else if(x-MyApplet.width/2 < 0) 
			return Bounds.LEFT;
		else
			return Bounds.NONE;
	}
	
	public Bounds verticalWall(int y) {
		if(y+MyApplet.height/2 >= map.height)
			return Bounds.DOWN;
		else if(y-MyApplet.height/2 < 0 )
			return Bounds.UP;
		else
			return Bounds.NONE;
	}
	
	public PImage getFullMap() {
		return map;
	}
	
	public PImage getSubMap(int x, int y) {
		int X=0, Y=0;
		if(horizontalWall(x) == Bounds.RIGHT)
			X = map.width - MyApplet.width - 1;
		else if(horizontalWall(x) == Bounds.LEFT)
			X = 0;
		else 
			X = x - MyApplet.width/2;
		if(verticalWall(y) == Bounds.DOWN)
			Y = map.height - MyApplet.height -1 ;
		else if(verticalWall(y) == Bounds.UP)
			Y = 0;
		else
			Y = y - MyApplet.height;
		return map.get(X, Y, MyApplet.width, MyApplet.height);
	}
	
	public int[][] getCollisionMap() {
		return collisionMap;
	}
	
	public int[][] getSubCollisionMap(int x, int y) {
		int[][] ret = new int[MyApplet.width][MyApplet.height];
		int initRow = 0;
		int from = 0;
		if(verticalWall(x) == Bounds.UP)
			initRow = 0;
		else if(verticalWall(x) == Bounds.DOWN)
			initRow = map.height - MyApplet.height - 1;
		else
			initRow = y - MyApplet.height/2;
		if(horizontalWall(y) == Bounds.LEFT)
			from = 0;
		else if(horizontalWall(y) == Bounds.RIGHT)
			from = map.width - MyApplet.width - 1;
		else
			from = x - MyApplet.width/2;
		for(int i=initRow;i<MyApplet.height;i++) {
			//ret[i] = Arrays.copyOfRange(map[i+initRow], from, from + MyApplet.width);
		}
		return ret ;
	}
}
