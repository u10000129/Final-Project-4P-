package server;

import processing.core.PApplet;
import de.looksgood.ani.Ani;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	private Hunter hunter;
	private ArrayList<Hunter> hunters;
	private View view;
	private Map map;
	private Control control;
	public final static double speed = 0.005;
	public final static int width = 800, height = 600;
	
	public void setup(){
		size(width, height);
		map = new Map(this);
		hunter = new Hunter(map, 5000, 5010);
		hunters = new ArrayList<Hunter>();
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
	
}
