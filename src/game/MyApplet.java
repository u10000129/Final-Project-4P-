package game;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Player player;
	private View view;
	private Map map;
	public final static double speed = 0.004;
	public final static int width = 800, height = 600;
	
	public void setup(){
		size(width, height);
		Ani.init(this);
		player = new Player(this);
		map = new Map(this);
		view = new View(this, map, player);
		smooth();
	}
	
	public void draw(){
		background(255);
		player.display();
		view.display();
	}
	
	public void mousePressed(){
		Bounds hBound = map.horizontalWall(player.getX(), player.getY());
		Bounds vBound = map.verticalWall(player.getX(), player.getY());
		int moveX, moveY;
		
		//HorizonBound detect
		if(hBound == Bounds.LEFT){ // At Left bounds.
			moveX = mouseX;
			//Ani.to(player, (float)(speed*dist(player.curX, player.curY, moveX, player.curY)), 
					//"curX", moveX, Ani.SINE_IN_OUT);
		}
		else if (hBound == Bounds.RIGHT){ // At Right bounds.
			moveX = map.getFullMap().width - MyApplet.width +  mouseX;
			//Ani.to(player, (float)(speed*dist(player.curX, player.curY, moveX, player.curY)), 
					//"curX", moveX, Ani.SINE_IN_OUT);
		}
		else {
			moveX = player.getX() + mouseX - MyApplet.width/2 ;
			//Ani.to(player, (float)(speed*dist(player.curX, player.curY, moveX, player.curY)), 
					//"curX", moveX, Ani.SINE_IN_OUT);
		}
		
		//verticalBound detect
		if(vBound == Bounds.UP){ // At Up bounds.
			moveY = mouseY;
			//Ani.to(player, (float)(speed*dist(player.curX, player.curY, player.curX, moveY)), 
					//"curY", moveY, Ani.SINE_IN_OUT);
		}
		else if (vBound == Bounds.DOWN){ // At Down bounds.
			moveY = map.getFullMap().height - MyApplet.height + mouseY;
			//Ani.to(player, (float)(speed*dist(player.curX, player.curY, player.curX, moveY)), 
					//"curY", moveY, Ani.SINE_IN_OUT);
		}
		else {
			moveY = player.getY()+ mouseY - MyApplet.height/2;		
			//Ani.to(player, (float)(speed*dist(player.curX, player.curY, player.curX, moveY)), 
					//"curY", moveY, Ani.SINE_IN_OUT);
		}
		Ani.to(player, (float)(speed*dist(player.curX, player.curY, moveX, moveY)), 
				"curX", moveX, Ani.SINE_IN_OUT);
		Ani.to(player, (float)(speed*dist(player.curX, player.curY, moveX, moveY)), 
				"curY", moveY, Ani.SINE_IN_OUT);
		
		
	}
}
