package logick;

import java.util.*;

public class Game {

	// velikost plosce 
	private static int size = 15;
	
	// seznam vseh moznih verig na plosci
	private static List<Chain> chains = new LinkedList<Chain>();
	
	// matrika vseh presecisc
	private StoneColor[][] grid;
	
	// igralec na potezi
	public Player onMove;
	
	public Game() {
		
		grid = new StoneColor[size][size];
		
		// sestavi seznam vseh moznih peteric na igralnem polju in
		// doda vsa presecisce na mrezo in
		// doda vse mozne poteze v seznam potez
		
		int[][] vectors = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				
				// doda prazno presecisce na mrezo
				grid[x][y] = StoneColor.EMPTY;
				
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
				if (grid[i][j] == StoneColor.EMPTY) moves.add(new Move(i, j));
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
			if (grid[xs[i]][ys[i]] == StoneColor.BLACK) black = true;
			else if (grid[xs[i]][ys[i]] == StoneColor.WHITE) white = true;
		}
		if (black && white) return true; else return false;
	}
	
	// preveri, ali je veriga zmagovalna
	// ne preveri, ali so vsi kamencki iste barve
	private boolean isWinning(Chain chain) {
		int counter = 0;
		int[] xs = chain.getXS();
		int[] ys = chain.getYS();
		for (int i = 0; i < 5; i++) {
			if (grid[xs[i]][ys[i]] != StoneColor.EMPTY) counter++;
		}
		return (counter == 5);
	}
	
	public void deleteDead() {
		for (Chain chain : chains) if (isDead(chain)) deleteChain(chain);
	}
	
	public boolean play(Move move) {
		if (grid[move.getX()][move.getY()] == StoneColor.EMPTY) {
			grid[move.getX()][move.getY()] = onMove.getPlayerColor();
			return true;
		}
		else return false;
	}
	
	public Status status() {
		// zbriše mrtve verige
		deleteDead();
		// preveri, ce je kaksna veriga zmagovalna
		for (Chain chain : chains) {
			if (isWinning(chain)) {
				switch (grid[chain.getXS()[0]][chain.getYS()[0]]) {
				case BLACK: return Status.BLACK_WIN;
				case WHITE: return Status.WHITE_WIN;
				case EMPTY: assert false;
				}
			}
		}
		// preveri, ce je se kako polje prazno in vrne igralca na potezi
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (grid[x][y] == StoneColor.EMPTY) {
					if (onMove.getPlayerColor() == StoneColor.BLACK) return Status.BLACK_MOVE;
					else if (onMove.getPlayerColor() == StoneColor.WHITE) return Status.WHITE_MOVE;
				}
			}
		}
		// v primeru, da ni praznih polj in zmagovalne verige, vrne izenacen izid
		return Status.DRAW;
	}
	
	// doslednost
	// A.K.A. get in set metode
	
	public static int getSize() {
		return size;
	}
	
	public void setSize(int n) {
		size = n;
	}
	
	public static List<Chain> getChains() {
		return chains;
	}
	
	public StoneColor[][] getGrid() {
		return grid;
	}
	
	public void setGrid(StoneColor[][] grid) {
		this.grid = grid;
	}
}