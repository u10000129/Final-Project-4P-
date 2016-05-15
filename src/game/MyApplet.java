package game;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Player player;
	//private View view;
	//private Map map;
	public final static double speed = 0.004;
	public final static int width = 800, height = 600;
	
	public void setup(){
		size(width, height);
		Ani.init(this);
		player = new Player(this);
		//map = new Map(this);
		//view = new View(this, map, player);
		smooth();
	}
	
	public void draw(){
		background(255);
		player.display();
		//view.display();
	}
	
	public void mouseClicked(){
		Ani.to(player, (float)(speed*dist(player.curX, player.curY, mouseX, mouseY)), 
				"curX", mouseX, Ani.SINE_IN_OUT);
		Ani.to(player, (float)(speed*dist(player.curX, player.curY, mouseX, mouseY)), 
				"curY", mouseY, Ani.SINE_IN_OUT);
	}
}
