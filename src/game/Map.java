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
		map = parent.loadImage("SubMap.jpg");
		collisionMap = new int[map.width][map.height];
		
		parent.loadPixels();
		for(int y=0;y<map.height;y++) {
			for(int x=0;x<map.width;x++) {
				if(p.red(p.pixels[y*map.width+x])>=110 && p.red(p.pixels[y*map.width+x])<160 && p.green(p.pixels[y*map.width+x])>=110 && p.green(p.pixels[y*map.width+x])<160 && p.blue(p.pixels[y*map.width+x])>=110 && p.blue(p.pixels[y*map.width+x])<160) {
					collisionMap[x][y] = 1;
				}
				else {
					collisionMap[x][y] = 0;
				}
			}
		}
	}
	
	public Bounds horizontalWall(int x, int y) {
		if(x+MyApplet.width/2 >= map.width)
			return Bounds.RIGHT;
		else if(x-MyApplet.width/2 < 0) 
			return Bounds.LEFT;
		else
			return Bounds.NONE;
	}
	
	public Bounds verticalWall(int x, int y) {
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
		if(horizontalWall(x,y) == Bounds.RIGHT)
			X = map.width - MyApplet.width - 1;
		else if(horizontalWall(x,y) == Bounds.LEFT)
			X = 0;
		else 
			X = x - MyApplet.width/2;
		if(verticalWall(x,y) == Bounds.DOWN)
			Y = map.height - MyApplet.height -1 ;
		else if(verticalWall(x,y) == Bounds.UP)
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
		if(verticalWall(x,y) == Bounds.UP)
			initRow = 0;
		else if(verticalWall(x,y) == Bounds.DOWN)
			initRow = map.height - MyApplet.height - 1;
		else
			initRow = y - MyApplet.height/2;
		if(horizontalWall(x,y) == Bounds.LEFT)
			from = 0;
		else if(horizontalWall(x,y) == Bounds.RIGHT)
			from = map.width - MyApplet.width - 1;
		else
			from = x - MyApplet.width/2;
		for(int i=initRow;i<MyApplet.height;i++) {
			ret[i] = Arrays.copyOfRange(collisionMap[i+initRow], from, from + MyApplet.width);
		}
		return ret;
	}
}
