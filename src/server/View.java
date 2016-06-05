package server;

import game.Bounds;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class View {
	private final int diameter = 40;
	public final int FieldOfView = 250;
	private Map map;
	private ArrayList<Hunter> hunters;
	private int hunterNum;
	private int curHunter;
	private PApplet mainApplet;
	private PImage mapImage;
	private int hunterX, hunterY;
	private int[][][] hunterSightMap;
	//private boolean unLookableFlag;
	
	public View(PApplet pApplet, Map map, ArrayList<Hunter> hunters, int hunterNum){
		this.mainApplet = pApplet;
		this.map = map;
		this.hunters = hunters;
		this.hunterNum = hunterNum;
		this.curHunter = 0;
	}
	
	private int transformX(int x) {
		return x * MyApplet.width / map.getImageWidth();
	}
	
	
	private int transformY(int y) {
		return y * MyApplet.height / map.getImageHeight();
	}
	
	/*public int[][] getHunterSightMap(int index){
		return this.hunterSightMap[index];
 	}*/
	
	public void display(){
		/* change hunter */
		if(this.mainApplet.keyPressed && this.mainApplet.key =='\t') {
			System.out.println(this.mainApplet.key);
			mainApplet.image(map.getFullMap(), 0, 0,MyApplet.width, MyApplet.height);
			for(int i=0; i<hunterNum; i++) 
				mainApplet.ellipse(transformX(hunters.get(i).getX()), 
								transformY(hunters.get(i).getY()), 
									diameter/4, diameter/4);
		}
		else {
			if(this.mainApplet.keyPressed) {
				int key = this.mainApplet.key - '0';
				if(key>=0 && key<hunterNum) {
					curHunter = key;
				}	
			}
			/* draw map */
			mapImage = map.getSubMap(hunters.get(curHunter).getX(), hunters.get(curHunter).getY());
			mainApplet.image(mapImage, 0, 0, 800, 600);
			
			/* draw hunters */
			Bounds hBound = map.horizontalWall(hunters.get(curHunter).getX(), hunters.get(curHunter).getY());
			Bounds vBound = map.verticalWall(hunters.get(curHunter).getX(), hunters.get(curHunter).getY());
			/* HorizonBound detect */
			if(hBound == Bounds.LEFT){
				hunterX = hunters.get(curHunter).getX();
			}
			else if (hBound == Bounds.RIGHT){
				hunterX = hunters.get(curHunter).getX() - map.getFullMap().width + MyApplet.width ;
			}
			else {
				hunterX = MyApplet.width / 2;
			}
			//verticalBound detect
			if(vBound == Bounds.UP){
				hunterY = hunters.get(curHunter).getY();
			}
			else if (vBound == Bounds.DOWN){
				hunterY = hunters.get(curHunter).getY() - map.getFullMap().height + MyApplet.height ;
			}
			else {
				hunterY = MyApplet.height / 2;
			}
			hunters.get(curHunter).collisionDetect();
			hunters.get(curHunter).huntingDetect();
			mainApplet.fill(125, 255);
			mainApplet.noStroke(); 
			mainApplet.ellipse(hunterX, hunterY, diameter, diameter);
			
			//Draw a circle field of view. 
			/* not useful for hunter */
			/*int[][] collisionMap = map.getCollisionMap();
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
				for(int i=0; i<hunters.size(); i++) this.hunterSightMap[i] = collisionMap;
				for(float i = 0; i < 360; i++) {
					for(float j = 0; j < FieldOfView ; j++ ){
						float x = j * PApplet.cos( PApplet.radians(i) ); 
						float y = j * PApplet.sin( PApplet.radians(i) ); 
						if(hunters.get(curHunter).getX() + (int)x < 0 || hunters.get(curHunter).getX() + (int)x >= map.getFullMap().width
								||hunters.get(curHunter).getY() + (int)y < 0 ||hunters.get(curHunter).getY() + (int)y >= map.getFullMap().height
								) break;
						if(collisionMap[hunters.get(curHunter).getX() + (int)x ][hunters.get(curHunter).getY() + (int)y ] == 1){
							mainApplet.line(hunterX + x, hunterY + y,
									hunterX + (FieldOfView-1)* PApplet.cos( PApplet.radians(i) ), 
									hunterY + (FieldOfView-1)* PApplet.sin( PApplet.radians(i) ));	
							break;
						}
					}		
				}*/
				
		}
		
	}
}
