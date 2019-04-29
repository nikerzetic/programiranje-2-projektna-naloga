package logick;

public enum Player {
	BLACK, WHITE;
	
	public Player opponent() {
		if (this == BLACK) return BLACK; else return WHITE;
	}
	
	public Intersection getIntersection() {
		if (this == BLACK) return Intersection.BLACK; else return Intersection.WHITE;
	}
}
