package game;

import java.util.Random;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PVector;

public class StartApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;
	private PVector randompt;
	private float curX, curY;
	private float invX, invY;
	private int count;
	private int color;
	
	public StartApplet() {		
		count = 0;
		color = 0;
		curX = 0;
		curY = 0;
		invX = 0;
		invY = 0;		
		randompt = getRandomPt();
		Ani.init(this);
		
	}
	public void setup() {
		
	}
	
	
	public void draw() {
		
		background(0,0,0);		
		if(count < 200)
			count++;
		else
			count = 0;
		
		//Draw big circle
		fill(252,212,64);
		ellipse(curX, curY, 250, 250);
		//Draw hunter circle.
		if(dist(invX, invY, curX, curY) <= 125) {
			fill(0,0,0);
			ellipse(invX, invY, 40, 40);
			fill(255);
			textAlign(PApplet.CENTER,PApplet.CENTER);
			text("H", invX, invY - 5);

		}
		//Draw small circle.
		fill(color);
		noStroke();
		ellipse(curX, curY, 40, 40);
		Ani.to(this,(float) 1.5,"curX",mouseX);
		Ani.to(this,(float) 1.5,"curY",mouseY);
		
	
		//Draw the game's name.
		textSize(30);
		fill(244,130,29);
		textFont(createFont("sans-serif",30));
		this.textAlign(PApplet.CENTER);
		text("The Game", Main.windowWidth/2, Main.windowHeight/10);	
		
		
		//Get new random point for hunter.
		if(count % 120 == 0) {
			randompt = getRandomPt();
			invX = randompt.x;
			invY = randompt.y;
		}
		//Get new color of small circle.
		if(count % 90 == 0) 			
			color = color(getColorTerm(), getColorTerm(), getColorTerm());		
	}
	
	
	private PVector getRandomPt() {
		Random rand = new Random();
		PVector vec = new PVector();
		int x = rand.nextInt(MyApplet.width);
		int y = rand.nextInt(MyApplet.height);
		vec.set(x, y);
		return vec;
	}
	
	private int getColorTerm() {
		Random rand = new Random();
		return rand.nextInt(256);
	}
}
