package logick;

public class Chain {
	
	public boolean alive = true; 
	// this value turns to false when two or more intersections are covered with stones of different colours 
	// (have values of BLACK and WHITE)
	// alternatively: the Chain could be deleted from the list, once it cannot form a winning combination
	
	public int[] x;
	public int[] y;	
	
	public Chain(int[] x, int[] y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		String chainString = "{";
		for (int i = 0; i < 4; i++) {
			chainString += "{" + x[i] + ", " + y[i] + "}, ";
		}
		chainString += "{" + x[4] + ", " + y[4] + "}}";
		return chainString;
	}

}
