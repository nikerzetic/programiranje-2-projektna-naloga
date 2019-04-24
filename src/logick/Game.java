package logick;

import java.util.*;

public class Game {

	// velikost plosce 
	private static int size = 15;
	
	// seznam vseh moznih verig na plosci
	private static List<Chain> chains = new LinkedList<Chain>();
	
	// matrika vseh presecisc
	private Intersection[][] grid;
	
	public Game() {
		
		grid = new Intersection[size][size];
		
		// sestavi seznam vseh moznih peteric na igralnem polju in
		// doda vsa presecisce na mrezo in
		// doda vse mozne poteze v seznam potez
		
		int[][] vectors = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				
				// doda prazno presecisce na mrezo
				grid[x][y] = Intersection.EMPTY;
				
				// doda vse mozne vrste z zacetkom v (x, y)
				for (int[] v : vectors) {
					int vx = v[0];
					int vy = v[1];
					if (0 <= (x + 4 * vx) && (x + 4 * vx) < size &&
							0 <= (y + 4 * vy) && (y + 4 * vy) < size
							) {
						int[] xs = new int[5];						
						int[] ys = new int[5];
						for (int i = 0; i < 5; i++) {
							xs[i] = x + i * vx;
							ys[i] = y + i* vy;
						}
						chains.add(new Chain(xs, ys));
					}
				}
			}
		}
		
	}
	
	public List<Move> possibleMoves() {
		List<Move> moves = new LinkedList<Move>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (grid[i][j] == Intersection.EMPTY) moves.add(new Move(i, j));
			}
		}
		return moves;
	}
	
	public void deleteChain(Chain chain) {
		chains.remove(chain);
	}
	
	private boolean isDead(Chain chain) {
		boolean black = false;
		boolean white = false;
		int[] xs = chain.getXS();
		int[] ys = chain.getYS();
		for (int i = 0; i < 5; i++) {
			if (grid[xs[i]][ys[i]] == Intersection.BLACK) black = true;
			else if (grid[xs[i]][ys[i]] == Intersection.WHITE) white = true;
		}
		if (black && white) return true; else return false;
	}
	
	public void deleteDead() {
		for (Chain chain : chains) if (isDead(chain)) deleteChain(chain);
	}
	
	// doslednost
	// A.K.A. get in set metode
	
	
	public void setSize(int n) {
		size = n;
	}
	
	public List<Chain> getChains() {
		return chains;
	}
	
	public Intersection[][] getGrid() {
		return grid;
	}
	
}