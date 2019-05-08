package logick;

public enum StoneColor {

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
