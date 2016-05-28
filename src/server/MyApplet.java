package server;

import processing.core.PApplet;
import de.looksgood.ani.Ani;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Hunter hunter;
	private View view;
	private Map map;
	private Control control;
	public final static double speed = 0.005;
	public final static int width = 800, height = 600;
	
	public void setup(){
		size(width, height);
		map = new Map(this);
		hunter = new Hunter(this, map);
		view = new View(this, map, hunter);
		Ani.init(this);
		control = new Control(map, hunter);
		control.start();
		smooth();
	}
	
	public void draw() {
		background(255);
		view.display();
	}
	
	private void AI() {
		
	}
	
}
