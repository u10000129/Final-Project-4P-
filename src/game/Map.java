package game;

import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

public class Map {
	private PApplet parent;
	private PImage map;
	private int[][] collisionMap;
	private int subMapX, subMapY;
	
	public Map(PApplet p) {
		parent = p;
		map = parent.loadImage("SubMap.jpg");
		collisionMap = new int[map.height+5][map.width+5];
		
		System.out.printf("image size: %dx%d\n",map.width,map.height);
		
		for(int i=0;i<map.height;i++) {
			for(int j=0;j<map.width;j++) {
				float r = p.red(map.get(i, j));
				float g = p.green(map.get(i, j));
				float b = p.blue(map.get(i, j));
				if(r>=110 && r<160 && g>=110 && g<160 && b>=110 && b<160) {
					collisionMap[i][j] =1;
					//System.out.println(i+" "+j);
				}
				else {
					collisionMap[i][j] = 0;
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
			Y = y - MyApplet.height/2;
		subMapX = X;
		subMapY = Y;
		return map.get(X, Y, MyApplet.width, MyApplet.height);
	}
	
	public int[][] getCollisionMap() {
		return collisionMap;
	}
	
	public int[][] getSubCollisionMap(int x, int y) {
		if(x < 0 || y< 0)
			return null;
		if(x > map.width || y > map.height)
			return null;
		
		int[][] ret = new int[MyApplet.height][MyApplet.width];
		for(int j=subMapX;j<MyApplet.width;j++) {
			for(int i=subMapY;j<MyApplet.height;j++) {
				ret[i][j] = collisionMap[i][j];
			}
		}
		return ret;
	}
}
