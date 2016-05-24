package game;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Player player;
	private View view;
	private Map map;
	private Transmission transmission;
	public final static double speed = 0.004;
	public final static int width = 800, height = 600;
	
	public MyApplet(Transmission transmission) {
		this.transmission = transmission;
	}

	public void setup(){
		size(width, height);
		Ani.init(this);		
		map = new Map(this);
		player = new Player(this, map);
		view = new View(this, map, player);
		smooth();
		transmission.sendMessage("test123");
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
		}
		else if (hBound == Bounds.RIGHT){ // At Right bounds.
			moveX = map.getFullMap().width - MyApplet.width +  mouseX;
		}
		else {
			moveX = player.getX() + mouseX - MyApplet.width/2 ;
		}
		
		//verticalBound detect
		if(vBound == Bounds.UP){ // At Up bounds.
			moveY = mouseY;
		}
		else if (vBound == Bounds.DOWN){ // At Down bounds.
			moveY = map.getFullMap().height - MyApplet.height + mouseY;
		}
		else {
			moveY = player.getY()+ mouseY - MyApplet.height/2;	
		}
		player.move(moveX, moveY);		
		
	}
}
