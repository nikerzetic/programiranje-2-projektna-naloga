package logick;

public enum Intersection {

	EMPTY,
	BLACK,
	WHITE;
	
	@Override
	public String toString() {
		switch (this) {
		case EMPTY: return " ";
		case BLACK: return "B";
		case WHITE: return "W";
		default: return "?";
		}
	}
	
}
