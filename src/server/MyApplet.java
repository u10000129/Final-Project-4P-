package server;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Hunter hunter;
	private View view;
	private Map map;
	public final static double speed = 0.005;
	public final static int width = 800, height = 600;
	
	public void setup(){
		size(width, height);
		map = new Map();
		view = new View();
		smooth();
	}
	
	public void draw() {
		background(255);
	}
	
	
}
