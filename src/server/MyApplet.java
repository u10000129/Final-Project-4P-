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
		
		hunter[0]=new Hunter(map, 3016, 639);
		hunter[1]=new Hunter(map, 3733, 870);
		hunter[2]=new Hunter(map, 3782, 1723);
		hunter[3]=new Hunter(map, 3894, 2042);
		hunter[4]=new Hunter(map, 4042, 1865);
		hunter[5]=new Hunter(map, 4932, 1865);
		hunter[6]=new Hunter(map, 4685, 1385);
		hunter[7]=new Hunter(map, 4475, 1190);
		hunter[8]=new Hunter(map, 4005, 3055);
		hunter[9]=new Hunter(map, 3968, 3499);
		hunter[10]=new Hunter(map, 2670, 3463);
		hunter[11]=new Hunter(map, 3968, 4867);
		hunter[12]=new Hunter(map, 3510, 5329);
		hunter[13]=new Hunter(map, 2274, 4938);
		hunter[14]=new Hunter(map, 1916, 6501);
		
		
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
