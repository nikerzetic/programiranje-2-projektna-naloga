package logick;

public class Move {

	private int x;
	private int y;
	
	// Konstruktor poteze.
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
