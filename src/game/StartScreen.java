package game;

import java.util.Observable;

import controlP5.CColor;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;

public class StartScreen extends Observable{
	public static final int btnWidth = 200;
	public static final int btnHeight =50;
	public static final int btnGap = 30;
	
	@SuppressWarnings("unused")
	private PApplet parent;
	private ControlP5 cp5;
	private controlP5.Textfield textField;
	private controlP5.Button start, quit;
	private Boolean startPressed;
	private String name;
	
	
	public StartScreen(PApplet parent) {
		this.parent = parent;
		cp5 = new ControlP5(parent);
		startPressed = false;
		
		PFont font = parent.createFont("sans-serif", 25);
		
		
		textField = cp5.addTextfield("Please enter your name")
							.setPosition(Main.windowWidth/2-btnWidth/2, Main.windowHeight*2/5)
							  .setSize(200, 40)
								.setFont(font)
								  .setFocus(true)
									.setColor(parent.color(255,255,255));
		
		
		start = cp5.addButton("buttonStart")
								.setLabel("Start")
								.setPosition(Main.windowWidth/2-btnWidth/2, Main.windowHeight*2/3)
								.setSize(btnWidth, btnHeight);
		start.getCaptionLabel().setSize(20);
								
		start.setColor(new CColor(parent.color(49,236,111),
								  parent.color(49,236,111),
								  parent.color(23,160,94),
								  parent.color(255,255,255),
								  parent.color(255,255,255)));
		
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
								.setPosition(Main.windowWidth/2-btnWidth/2, Main.windowHeight*2/3+btnHeight+btnGap)
								.setSize(btnWidth, btnHeight);
		quit.getCaptionLabel().setSize(20);
		quit.setColor(new CColor(parent.color(49,236,111),
				  				 parent.color(49,236,111),
							     parent.color(23,160,94),
							     parent.color(255,255,255),
							     parent.color(255,255,255)));

		
		quit.addCallback(new CallbackListener() {
			@SuppressWarnings("deprecation")
			public void controlEvent(CallbackEvent theEvent) {
				if(theEvent.getAction() == ControlP5.ACTION_PRESSED) {
					buttonQuit();
				}
			}
		});
		
	}
	/*
	public void display() {
		
		//parent.background(106,165, 202);
		parent.background(0,0,0);
		parent.fill(0);
		parent.textSize(30);
		parent.fill(244,130,29);
		parent.textFont(parent.createFont("sans-serif",30));
		parent.text("The Game", Main.windowWidth/3, Main.windowHeight/10);	
	}
	*/
	public void buttonStart() {
		startPressed = true;
		name = textField.getText();
		start.hide();
		quit.hide();
		setChanged();
		notifyObservers(name);
	}
	
	public void buttonQuit() {
		System.exit(0);
	}
	
	public Boolean getStartPressed() {
		return startPressed;
	}
}

