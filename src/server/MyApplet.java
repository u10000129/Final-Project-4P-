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
		
		hunter[0]=new Hunter(map, 5340, 5036);
		hunter[1]=new Hunter(map, 6840, 4144);
		hunter[2]=new Hunter(map, 3116, 5968);
		hunter[3]=new Hunter(map, 5384, 4136);
		hunter[4]=new Hunter(map, 3180, 3204);
		hunter[5]=new Hunter(map, 7940, 2996);
		hunter[6]=new Hunter(map, 5164, 7452);
		hunter[7]=new Hunter(map, 3680, 7544);
		hunter[8]=new Hunter(map, 6800, 4200);
		hunter[9]=new Hunter(map, 3000, 6000);
		hunter[10]=new Hunter(map, 5400, 4200);
		hunter[11]=new Hunter(map, 3200, 3200);
		hunter[12]=new Hunter(map, 8000, 3000);
		hunter[13]=new Hunter(map, 5200, 7500);
		hunter[14]=new Hunter(map, 3700, 7500);
		
		
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
