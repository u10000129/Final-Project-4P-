package game;

import java.util.ArrayList;
import java.util.Observable;

import controlP5.Button;
import controlP5.CColor;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import controlP5.RadioButton;
import processing.core.PApplet;

public class QuestionPanel extends Observable{
	public static final int btnWidth = 200;
	public static final int btnHeight =50;
	public static final int btnGap = 30;
	
	private QuestionApplet qApplet;
	private ArrayList<String> qandA;
	
	private class QuestionApplet extends PApplet{

		private static final long serialVersionUID = 1L;
		private final int BOUNDRY_WIDTH = 50;
		private ControlP5 cp5;
		private RadioButton r;
		private Button confirm;
		
		public QuestionApplet() {
			super();
		}
		
		public void setup() {
			
			super.setup();
			size(MyApplet.width, MyApplet.height);
			
			cp5 = new ControlP5(this);
			
			cp5.setFont(createFont("/res/NotoSansHantRegular",20));
			
			//create radio buttons for choices
			r = cp5.addRadioButton("")
			         .setPosition(20,160)
			         .setSize(40,20)
			         .setColorForeground(color(120))
			         .setColorActive(color(255))
			         .setColorLabel(color(255))
			         .setItemsPerRow(1)
			         .setSpacingColumn(50)
					 .setSpacingRow(50);
			
			for(int i=1;i<qandA.size()-1;i++)
				r.addItem(qandA.get(i), i);
			
			//create confirm button
			
			confirm = cp5.addButton("buttonConfirm")
								.setLabel("Confirm")
								.setPosition(Main.windowWidth/2, Main.windowHeight*2/3)
								.setSize(btnWidth, btnHeight)
								.setColorLabel(color(255,255,255));
			
			
			confirm.setColor(new CColor(color(255,255,255),
									  	color(49,236,111),
									  	color(23,160,94),
									  	color(255,255,255),
									  	color(255,255,255)));
			
			confirm.addCallback(new CallbackListener() {
				@SuppressWarnings("deprecation")
				public void controlEvent(CallbackEvent theEvent) {
					if(theEvent.getAction() == ControlP5.ACTION_PRESSED) {
						buttonPressed();
					}
				}
			});
			
		}
		
		public void draw() {
			
			background(32,74,145);
			
			//Question
			fill(255,255,255);
			textFont(createFont("sans-serif", 28));
			text(qandA.get(0),BOUNDRY_WIDTH, BOUNDRY_WIDTH);
			

		}
		
		//check the answer and notify Observer
		private void buttonPressed() {
			Boolean ret = false;
			
			if(qandA.get(qandA.size()-1).charAt(0) - 'A' + 1 == (int) r.getValue()) {
				ret = true;
			}
			
			setChanged();
			notifyObservers(ret);
		}
		
	}
	
	
	public QuestionPanel(Mission m) {

		qandA = m.getQuestion();
		qApplet = new QuestionApplet();
		
		qApplet.init();
		
	}
	
	public QuestionApplet getQApplet() {
		return qApplet;
	}
	
}
