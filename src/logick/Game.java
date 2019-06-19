package logick;

import java.util.*;

public class Game {

	// Velikost plosce.
	private static int size = 15;
	
	// Seznam vseh moznih verig na plosci.
	private List<Chain> chains = new LinkedList<Chain>();
	
	// Matrika vseh presecisc.
	private StoneColor[][] grid;
	
	// Igralca, ki bosta igrala igro.
	private Player player1;
	private Player player2;
	
	// Igralec na potezi.
	private Player onMove; // Ta spremenljivka je najbrz neuporabna, ker lahko, kdo je na potezi, dolocimo iz statusa.
	
	// Nastavitev statusa na "Crni na potezi".
	private Status status = Status.BLACK_MOVE;
	
	public Game(Player player1, Player player2) {
		
		this.grid = new StoneColor[size][size];
		
		this.player1 = player1;
		this.player2 = player2;
		
		// Ko se poklice igra, najprej sestavi seznam vseh moznih peteric na igralnem polju in
		// doda vsa presecisce na mrezo, ter vse mozne poteze v seznam potez.
		
		int[][] vectors = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				
				// Doda prazno presecisce na mrezo.
				grid[x][y] = StoneColor.EMPTY;
				
				// Doda vse mozne vrste z zacetkom v (x, y).
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
	
	// Konstruktor igre.
	public Game(Game game) {
		for (Chain chain : game.chains) {
			this.chains.add(new Chain(chain.getXS(), chain.getYS()));
		}
		this.grid = new StoneColor[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				this.grid[x][y] = game.grid[x][y];
			}
		}
		this.onMove = game.onMove;
		this.player1 = game.player1;
		this.player2 = game.player2;
		this.setStatus(game.getStatus());
	}
	
	// Metoda, ki vrne seznam vseh moznih potez.
	public List<Move> possibleMoves() {
		List<Move> moves = new LinkedList<Move>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (grid[i][j] == StoneColor.EMPTY) moves.add(new Move(i, j));
			}
		}
		return moves;
	}
	
	// Metoda, ki izbrise vse verigo iz seznama verig.
	public void deleteChain(Chain chain) {
		chains.remove(chain);
	}
	
	// Metoda, ki preveri, ce ima veriga en bel in en crn kamencek (oziroma, ce je "mrtva")
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
	
	// Preveri, ali je veriga zmagovalna.
	// Ne preveri, ali so vsi kamencki iste barve.
	// Neuporabna metoda.
	private boolean isWinning(Chain chain) {
		int counter = 0;
		int[] xs = chain.getXS();
		int[] ys = chain.getYS();
		for (int i = 0; i < 5; i++) {
			if (grid[xs[i]][ys[i]] != StoneColor.EMPTY) counter++;
		}
		return (counter == 5);
	}
	
	// Metoda, ki preveri, ce so vsi kamencki v verigi iste barve,
	// ali pa ce ima veriga vsaj 2 kamencka razlicne barve, jo doda v seznam "mrtvih" verig.
	public StoneColor findStatus() {
		List<Chain> chainsToBeDeleted = new LinkedList<Chain>();
		for (Chain chain : chains) {
			if (chain.getStrength() == 5) return chain.getColor();
			else if (isDead(chain)) chainsToBeDeleted.add(chain);
		}
		chains.removeAll(chainsToBeDeleted);
		return StoneColor.EMPTY;
	}

	// To metodo bi lahko razbili na vec pomoznih funkcij.
	// Metoda, ki odigra potezo in izbrise vse "mrtve" verige.
	public void play(Move move) {
		int x = move.getX();
		int y = move.getY();
		StoneColor moveColor = StoneColor.EMPTY;
		
		if (this.getStatus() == Status.WHITE_MOVE) moveColor = StoneColor.WHITE;
		else if (this.getStatus() == Status.BLACK_MOVE) moveColor = StoneColor.BLACK;
		
		this.grid[move.getX()][move.getY()] = moveColor;
		
		// Za vsako verigo preveri, ali je mrtva in ji doloci moc.
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
	
	// Preveri, ali je izenaceno (ni vec praznega polja), ali pa vrne status.
	public Status status() {
		if (this.status == Status.WHITE_MOVE || this.status == Status.BLACK_MOVE) {
			if (this.possibleMoves().isEmpty()) this.status = Status.DRAW;
		}
		return this.status;
	}
	
	public Player oponent() {
		if (this.onMove == player1) return player2;
		else return player1;
	}

	// Doslednost,
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