package server;

import game.Bounds;
import processing.core.PApplet;
import processing.core.PImage;

public class View {
	private final int diameter = 40;
	private final int FieldOfView = 250;
	private Map map;
	private Hunter hunter;
	private PApplet mainApplet;
	private PImage mapImage;
	private int hunterX, hunterY;
	
	public View(PApplet pApplet, Map map, Hunter hunter){
		this.mainApplet = pApplet;
		this.map = map;
		this.hunter = hunter;
	}
	
	public void display(){
		/* draw map */
		mapImage = map.getSubMap(hunter.getX(), hunter.getY());
		mainApplet.image(mapImage, 0, 0, 800, 600);
		
		/* draw player */
		Bounds hBound = map.horizontalWall(hunter.getX(), hunter.getY());
		Bounds vBound = map.verticalWall(hunter.getX(), hunter.getY());
		/* HorizonBound detect */
		if(hBound == Bounds.LEFT){
			hunterX = hunter.getX();
		}
		else if (hBound == Bounds.RIGHT){
			hunterX = hunter.getX() - map.getFullMap().width + MyApplet.width ;
		}
		else {
			hunterX = MyApplet.width / 2;
		}
		//verticalBound detect
		if(vBound == Bounds.UP){
			hunterY = hunter.getY();
		}
		else if (vBound == Bounds.DOWN){
			hunterY = hunter.getY() - map.getFullMap().height + MyApplet.height ;
		}
		else {
			hunterY = MyApplet.height / 2;
		}
		hunter.collisionDetect();
		mainApplet.fill(125, 255);
		mainApplet.noStroke(); 
		mainApplet.ellipse(hunterX, hunterY, diameter, diameter);
		
		//Draw a circle field of view. 
		
		int[][] collisionMap = map.getCollisionMap();
		mainApplet.fill(0, 0, 0, 128);
		for(int i = 0; i <= MyApplet.width; i++ ){
			for(int j = 0; j <= MyApplet.height; j++ ){
				if(PApplet.dist(hunterX, hunterY, i, j) > FieldOfView) 
						mainApplet.rect(i, j, 1, 1);				
					}		
				}
			for(int i = -100; i <= 100; i++ ){
				collisionMap[7010 + i][7100] = 1;	
			}
			mainApplet.stroke(0, 0, 0, 128);
			mainApplet.strokeWeight(5);
			for(float i = 0; i < 360; i++) {
				for(float j = 0; j < FieldOfView ; j++ ){
					float x = j * PApplet.cos( PApplet.radians(i) ); 
					float y = j * PApplet.sin( PApplet.radians(i) ); 
					if(collisionMap[hunter.getX() + (int)x ][hunter.getY() + (int)y ] == 1){
						mainApplet.line(hunterX + x, hunterY + y,
								hunterX + (FieldOfView-1)* PApplet.cos( PApplet.radians(i) ), 
								hunterY + (FieldOfView-1)* PApplet.sin( PApplet.radians(i) )
						);	
						break;
					}				
				}		
			}	
	}
}
