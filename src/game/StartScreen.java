package game;

import java.util.Observable;

import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import processing.core.PApplet;

public class StartScreen extends Observable{
	public static final int btnWidth = 200;
	public static final int btnHeight =50;
	public static final int btnGap = 30;
	
	private PApplet parent;
	private ControlP5 cp5;
	private controlP5.Button start, quit;
	private Boolean startPressed;
	
	
	public StartScreen(PApplet parent) {
		this.parent = parent;
		cp5 = new ControlP5(parent);
		startPressed = false;
		
		
		start = cp5.addButton("buttonStart")
								.setLabel("Start")
								.setPosition(MyApplet.width*2/3, MyApplet.height*2/3)
								.setSize(btnWidth, btnHeight);
		start.addCallback(new CallbackListener() {
			@SuppressWarnings("deprecation")
			public void controlEvent(CallbackEvent theEvent) {
				if(theEvent.getAction() == ControlP5.ACTION_PRESSED) {
					buttonStart();
				}
			}
		});
								
		quit = cp5.addButton("buttonQuit")
								.setLabel("Quit")
								.setPosition(MyApplet.width*2/3, MyApplet.height*2/3+btnHeight+btnGap)
								.setSize(btnWidth, btnHeight);
		
		quit.addCallback(new CallbackListener() {
			@SuppressWarnings("deprecation")
			public void controlEvent(CallbackEvent theEvent) {
				if(theEvent.getAction() == ControlP5.ACTION_PRESSED) {
					buttonQuit();
				}
			}
		});
		
	}
	
	public void display() {
				
		parent.background(106,165, 202);
		parent.fill(0);
		parent.textSize(30);
		parent.text("The Game", MyApplet.width/2, MyApplet.height/5);		
	}
	
	
	
	public void buttonStart() {
		startPressed = true;
		start.hide();
		quit.hide();
		setChanged();
		notifyObservers();
	}
	
	public void buttonQuit() {
		System.exit(0);
	}
	
	public Boolean getStartPressed() {
		return startPressed;
	}
}

