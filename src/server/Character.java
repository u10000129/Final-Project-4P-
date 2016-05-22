package server;

public abstract class Character {
	int anchorX, anchorY;
	int curX, curY;
	
	abstract int getX();
	abstract int getY();
}

