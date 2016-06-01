package server;

import processing.core.PApplet;
import de.looksgood.ani.Ani;
import game.Transmission;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	
	/*private Hunter[] hunter;
	private ArrayList<Hunter> hunters;
	private int hunterNum;
	private View view;
	private Map map;*/
	private Control control;
	public final static double speed = 0.005;
	public final static int width = 800, height = 600;
	
	public MyApplet(Hunter[] hunter, ArrayList<Hunter> hunters, int hunterNum, View view, Map map) {
		
	}
	
	public void setup(){
		size(width, height);
		map = new Map(this);
		hunterNum = 8;
		hunter = new Hunter[hunterNum];
		hunter[0]=new Hunter(map, 5340, 5036);
		hunter[1]=new Hunter(map, 6840, 4144);
		hunter[2]=new Hunter(map, 3116, 5968);
		hunter[3]=new Hunter(map, 5384, 4136);
		hunter[4]=new Hunter(map, 3180, 3204);
		hunter[5]=new Hunter(map, 7940, 2996);
		hunter[6]=new Hunter(map, 5164, 7452);
		hunter[7]=new Hunter(map, 3680, 7544);
		
		hunters = new ArrayList<Hunter>();
		for(int i=0; i<8; i++) hunters.add(hunter[i]);
		view = new View(this, map, hunters, hunterNum);
		
		Ani.init(this);
		control = new Control(map, hunters, hunterNum);
		control.start();
		smooth();
	}
	
	public void draw() {
		background(255);
		view.display();
	}
}
