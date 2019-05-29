package logick;

import java.util.*;

public class Game {

	// velikost plosce 
	private static int size = 15;
	
	// seznam vseh moznih verig na plosci
	private List<Chain> chains = new LinkedList<Chain>();
	
	// matrika vseh presecisc
	private StoneColor[][] grid;
	
	// igralec na potezi
	private Player onMove; // TODO ta spremenljivka je najbrz neuporabna, ker lahko, kdo je na potezi, dolocimo iz statusa
	
	private Status status = Status.BLACK_MOVE;
	
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
	// TODO neuporabna metoda
	private boolean isWinning(Chain chain) {
		int counter = 0;
		int[] xs = chain.getXS();
		int[] ys = chain.getYS();
		for (int i = 0; i < 5; i++) {
			if (grid[xs[i]][ys[i]] != StoneColor.EMPTY) counter++;
		}
		return (counter == 5);
	}

	public StoneColor findStatus() {
		List<Chain> chainsToBeDeleted = new LinkedList<Chain>();
		for (Chain chain : chains) {
			if (chain.getStrength() == 5) return chain.getColor();
			else if (isDead(chain)) chainsToBeDeleted.add(chain);
		}
		chains.removeAll(chainsToBeDeleted);
		return StoneColor.EMPTY;
	}

	// TODO to metodo bi lahko razbili na vec pomoznih funkcij
	public void play(Move move) {
		int x = move.getX();
		int y = move.getY();
		StoneColor moveColor = StoneColor.EMPTY;
		
		if (this.getStatus() == Status.WHITE_MOVE) moveColor = StoneColor.WHITE;
		else if (this.getStatus() == Status.BLACK_MOVE) moveColor = StoneColor.BLACK;
		
		grid[move.getX()][move.getY()] = moveColor;
		
		// za vsako verigo preveri, ali je mrtva in ji doloci moc
		List<Chain> chainsToBeDeleted = new LinkedList<Chain>();
		for (Chain chain : this.chains) {
			int[] xs = chain.getXS();
			int[] ys = chain.getYS();
			for (int i = 0; i < 5; i++) {
				if (xs[i] == x && ys[i] == y) {
					if (chain.getStrength() == 0) {
						chain.setColor(moveColor);
						chain.setStrength(1);
					}
					else if (chain.getStrength() >= 1) {
						if (chain.getColor() == moveColor) chain.setStrength(chain.getStrength() + 1);
						else chainsToBeDeleted.add(chain);
					}
					if (chain.getStrength() == 5) {
						if (chain.getColor() == StoneColor.WHITE) this.status = Status.WHITE_WIN;
						else if (chain.getColor() == StoneColor.BLACK) this.status = Status.BLACK_WIN;
					}
				}
//				if (grid[xs[i]][ys[i]] == StoneColor.BLACK) black = true;
//				else if (grid[xs[i]][ys[i]] == StoneColor.WHITE) white = true;
			}
		}
		chains.removeAll(chainsToBeDeleted);
	}
	
	// preveri, ali je izenaceno (ni vec praznega polja), ali vrne status
	public Status status() {
		if (this.status == Status.WHITE_MOVE || this.status == Status.BLACK_MOVE) {
			if (this.possibleMoves().isEmpty()) this.status = Status.DRAW;
		}
		return this.status;
	}

	// doslednost
	// A.K.A. get in set metode
	
	public static int getSize() {
		return size;
	}
	
	public void setSize(int n) {
		size = n;
	}
	
	public List<Chain> getChains() {
		return this.chains;
	}
	
	public StoneColor[][] getGrid() {
		return this.grid;
	}
	
	public void setGrid(StoneColor[][] grid) {
		this.grid = grid;
	}
	
	public Player getOnMove() {
		return this.onMove;
	}
	
	public void setOnMove(Player player) {
		this.onMove = player;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}