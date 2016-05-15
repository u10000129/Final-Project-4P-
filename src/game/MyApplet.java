package game;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Player player;
	public final static int width = 800, height = 600;
	
	public void setup(){
		size(width, height);
		Ani.init(this);
		player = new Player(this);
		smooth();
		}
	
	public void draw(){
		background(255);
		player.display();
	}
	
	public void mouseClicked(){
		Ani.to(player, (float)0.5, "curX", mouseX);
		Ani.to(player, (float)0.5, "curY", mouseY);
	}
}
