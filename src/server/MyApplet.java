package server;

import processing.core.PApplet;
import de.looksgood.ani.Ani;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{
	
	private Hunter[] hunter;
	private ArrayList<Hunter> hunters;
	private int hunterNum;
	private View view;
	private Map map;
	private Control control;
	public final static double speed = 0.005;
	public final static int width = 800, height = 600;
	
	public MyApplet(Hunter[] hunter, ArrayList<Hunter> hunters, int hunterNum) {
		this.hunter = hunter;
		this.hunters = hunters;
		this.hunterNum = hunterNum;		
	}
	
	public void setup(){
		size(width, height);
		map = new Map(this);		
		
		HunterGraph g = new HunterGraph();
		
		for(int i=0;i<15;i++) {
			hunter[i] = new Hunter(map, g);
		}	
		
		for(int i=0; i<this.hunterNum; i++) hunters.add(hunter[i]);
		view = new View(this, map, hunters, hunterNum);
		
		Ani.init(this);
		control = new Control(map, hunters, hunterNum, view);
		control.start();
		smooth();
	}
	
	public void draw() {
		background(255);
		view.display();
	}
	
	public void setPlayersMap(HashMap<Integer, List<Integer>> playersMap){
		this.control.setPlayersMap(playersMap);
		for(int i=0; i<this.hunterNum; i++) 
			this.hunters.get(i).setPlayersMap(playersMap);
	}
	
	public void setPlayersLife(HashMap<Integer, Integer> playersLife){
		this.control.setPlayersLife(playersLife);
	}
}
