package logick;

// Mozne barve kamenckov.

public enum StoneColor {

	EMPTY,
	BLACK,
	WHITE;
	
	@Override
	public String toString() {
		switch (this) {
		case EMPTY: return "O";
		case BLACK: return "B";
		case WHITE: return "W";
		default: return "?";
		}
	}
	
}
