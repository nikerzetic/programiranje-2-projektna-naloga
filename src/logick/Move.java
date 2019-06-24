package logick;

// Poteza je podana s koordinatama x in y. Ti teceta od 0 do SIZE-1.

public class Move {

	private int x;
	private int y;
	
	// Konstruktor.
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	// Get in set metode.
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
