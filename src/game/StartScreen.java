package game;

import controlP5.ControlP5;
import processing.core.PApplet;

public class StartScreen {
	private PApplet parent;
	private ControlP5 cp5;
	
	public StartScreen(PApplet parent) {
		final int btnWidth=200;
		final int btnHeight=50;
		final int btnGap = 30;
		this.parent = parent;
		cp5 = new ControlP5(parent);
		
		
		cp5.addButton("buttonStart")
								.setLabel("Start")
								.setPosition(MyApplet.width/2-btnWidth, MyApplet.height*2/3)
								.setSize(btnWidth, btnHeight);
								
		cp5.addButton("buttonQuit")
								.setLabel("Quit")
								.setPosition(MyApplet.width/2-btnWidth, MyApplet.height*2/3+btnHeight+btnGap)
								.setSize(btnWidth, btnHeight);
		
	}
	
	public void display() {
		parent.background(106,165, 202);
		parent.fill(0);
		parent.textSize(26);
		text();
	}
	
	public void buttonStart() {
		
	}
	
	public void buttonQuit() {
		System.exit(0);
	}
}

