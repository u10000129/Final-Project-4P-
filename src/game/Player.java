package game;

public class Player extends Character{
	private MyApplet parent;
	Player(MyApplet parent ){
		this.parent = parent;
		this.anchorX = 7000;
		this.anchorY = 7000;
		this.curX = this.anchorX;
		this.curY = this.anchorY;
	}
	
	public void display(){
		this.parent.fill(0, 255);
		this.parent.noStroke(); 		
		//this.parent.ellipse(this.curX, this.curY, 40, 40);
	}
	
	public void setX(int X){
		curX=X;
	}
	
	public void setY(int Y){
		curY=Y;
	}

	@Override
	public int getX() {
		return curX;
	}

	@Override
	public int getY() {
		return curY;
	}
}
