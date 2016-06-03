package game;

import java.util.ArrayList;
import java.util.Observable;

import controlP5.ControlP5;
import processing.core.PApplet;

public class QuestionPanel extends Observable{
	private Mission mission;
	private QuestionApplet qApplet;
	private Boolean confirmPressed;
	private ArrayList<String> qandA;
	
	private class QuestionApplet extends PApplet{

		private static final long serialVersionUID = 1L;
		private final int BOUNDRY_WIDTH = 50;
		ControlP5 cp5;
		
		public QuestionApplet() {
			
			super();
		}
		
		public void setup() {
			
			super.setup();
			size(MyApplet.width, MyApplet.height);
			
			cp5 = new ControlP5(this);
			
			//create radio buttons for choices
			
			
			
			//create confirm button
			
		}
		
		public void draw() {
			
			background(32,74,145);
			
			//Question
			fill(255,255,255);
			textFont(createFont("sans-serif", 28));
			text(qandA.get(0),BOUNDRY_WIDTH, BOUNDRY_WIDTH);
			

		}
		
		//pressed confirm button
		private void buttonPressed() {
			
		}
		
	}
	
	
	public QuestionPanel() {
		
		mission = new Mission();
		qApplet = new QuestionApplet();
		confirmPressed = false;
		qandA = mission.getQuestion();
		mission = null;		//free the memory
		
		qApplet.init();
		
	}
	
}
