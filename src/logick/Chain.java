package logick;

public class Chain {
	
	public boolean alive = true; 
	// this value turns to false when two or more intersections are covered with stones of different colours 
	// (have values of BLACK and WHITE)
	// alternatively: the Chain could be deleted from the list, once it cannot form a winning combination
	
	private int[] xs;
	private int[] ys;	
	
	public Chain(int[] x, int[] y) {
		this.xs = x;
		this.ys = y;
	}
	
	public String toString() {
		String chainString = "{";
		for (int i = 0; i < 4; i++) {
			chainString += "{" + xs[i] + ", " + ys[i] + "}, ";
		}
		chainString += "{" + xs[4] + ", " + ys[4] + "}}";
		return chainString;
	}
	
	public int[] getXS() {
		return xs;
	}

	public int[] getYS() {
		return ys;
	}
	
}
