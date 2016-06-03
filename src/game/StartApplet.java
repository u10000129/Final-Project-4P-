package game;

import java.util.Random;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PVector;

public class StartApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;
	private PVector begin, end, third;
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
		begin = getRandomPt();
		end = getRandomPt();
		third = getRandomPt();
		Ani.init(this);
	}
	
	public void draw() {
		
		background(0,0,0);
		fill(0);
		textSize(30);
		fill(244,130,29);
		textFont(createFont("sans-serif",30));
		text("The Game", Main.windowWidth/3, Main.windowHeight/10);	
		
		if(count < 200)
			count++;
		else
			count = 0;
		
		fill(252,212,64);
		ellipse(curX, curY, 250, 250);
		
		fill(color);
		ellipse(curX, curY, 40, 40);
		
		if(dist(invX, invY, curX, curY) <= 250) {
			fill(0,0,0);
			ellipse(invX, invY, 40, 40);
		}
		
		if(count % 120 == 0) {
			third = getRandomPt();
			invX = third.x;
			invY = third.y;
		}
		
		if(count % 90 == 0) {
			begin = getRandomPt();
			end = getRandomPt();
			color = color(getColorTerm(), getColorTerm(), getColorTerm());
			
			curX = begin.x;
			curY = begin.y;
			
		} else {
			Ani.to(this,(float) 1.5,"curX",end.x);
			Ani.to(this,(float) 1.5,"curY",end.y);
		}
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
