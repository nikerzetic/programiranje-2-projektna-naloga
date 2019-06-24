package logick;

import java.util.LinkedList;
import java.util.List;

// Osnovni razred za igranje igre.

public class Game {

	// Velikost plosce.
	private static int SIZE = 15;
	
	// Seznam vseh moznih verig na plosci.
	private List<Chain> chains = new LinkedList<Chain>();
	
	// Matrika vseh presecisc.
	private StoneColor[][] grid;
	
	// Igralca, ki bosta igrala igro.
	private Player player1;
	private Player player2;
	
	// Igralec na potezi.
	private Player onMove;
	
	// Igro vedno zacne crni igralec.
	private Status status = Status.BLACK_MOVE;

	// Konstruktor.
	public Game(Player player1, Player player2) {

		this.grid = new StoneColor[SIZE][SIZE];

		this.player1 = player1;
		this.player2 = player2;
		
		// Ko se poklice igra, najprej sestavi seznam vseh moznih peteric na igralnem polju in
		// doda vsa presecisce na mrezo, ter vse mozne poteze v seznam potez.
		
		int[][] vectors = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				
				// Doda prazno presecisce na mrezo.
				grid[x][y] = StoneColor.EMPTY;
				
				// Doda vse mozne vrste z zacetkom v (x, y).
				for (int[] v : vectors) {
					int vx = v[0];
					int vy = v[1];
					if (0 <= (x + 4 * vx) && (x + 4 * vx) < SIZE && 0 <= (y + 4 * vy) && (y + 4 * vy) < SIZE) {
						int[] xs = new int[5];
						int[] ys = new int[5];
						for (int i = 0; i < 5; i++) {
							xs[i] = x + i * vx;
							ys[i] = y + i * vy;
						}
						chains.add(new Chain(xs, ys));
					}
				}
			}
		}

	}
	
	// Konstruktor igre iz dane igre.
	public Game(Game game) {
		for (Chain chain : game.chains) {
			this.chains.add(new Chain(chain));
		}
		this.grid = new StoneColor[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.grid[i][j] = game.grid[i][j];
			}
		}
		this.onMove = game.onMove;
		this.player1 = game.player1;
		this.player2 = game.player2;
		this.status = game.status;
  }
	
	// Metoda, ki vrne seznam vseh moznih potez.
	public List<Move> possibleMoves() {
		List<Move> moves = new LinkedList<Move>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (this.grid[i][j] == StoneColor.EMPTY)
					moves.add(new Move(i, j));
			}
		}
		return moves;
	}
	
	// Metoda, ki izbrise vse verigo iz seznama verig.
	public void deleteChain(Chain chain) {
		chains.remove(chain);
	}
	
	// Metoda, ki odigra potezo in izbrise vse "mrtve" verige.
	public void play(Move move) {
		int x = move.getX();
		int y = move.getY();
		StoneColor moveColor = StoneColor.EMPTY;
		
		if (this.getStatus() == Status.WHITE_MOVE)
			moveColor = StoneColor.WHITE;
		else if (this.getStatus() == Status.BLACK_MOVE)
			moveColor = StoneColor.BLACK;

		this.grid[x][y] = moveColor;

		this.sieveChains(move, moveColor);
		this.setOnMove(this.oponent());
		this.newStatus();
	}
	
	// Metoda, ki vsaki verigi doloci moc in izbrise mrtve.
	private void sieveChains(Move move, StoneColor moveColor) {
		int x = move.getX();
		int y = move.getY();

		List<Chain> chainsToBeDeleted = new LinkedList<Chain>();
		for (Chain chain : this.chains) {
			int[] xs = chain.getXS();
			int[] ys = chain.getYS();
			for (int i = 0; i < 5; i++) {
				if (xs[i] == x && ys[i] == y) {
					if (chain.getStrength() == 0) {
						chain.setColor(moveColor);
						chain.setStrength(1);
					} else if (chain.getStrength() >= 1) {
						if (chain.getColor() == moveColor)
							chain.setStrength(chain.getStrength() + 1);
						else
							chainsToBeDeleted.add(chain);
					}
					if (chain.getStrength() == 5) {
						if (chain.getColor() == StoneColor.WHITE)
							this.status = Status.WHITE_WIN;
						else if (chain.getColor() == StoneColor.BLACK)
							this.status = Status.BLACK_WIN;
					}
				}
			}
		}
		this.chains.removeAll(chainsToBeDeleted);
	}
	
	// Preveri, ali je izenaceno (ni vec praznega polja), ali pa vrne status.
	public Status status() {
		if (this.status == Status.WHITE_MOVE || this.status == Status.BLACK_MOVE) {
			if (this.possibleMoves().isEmpty())
				this.status = Status.DRAW;
		}
		return this.status;
	}

	// Poisce nasprotnika igralca na potezi.
	public Player oponent() {
		if (this.onMove == player1)
			return player2;
		else
			return player1;
	}

	// Nastavi drugega igralca na potezo, ce je mogoce.
	public void newStatus() {
		this.status();
		if (this.status == Status.BLACK_MOVE) status = Status.WHITE_MOVE;
		else if (this.status == Status.WHITE_MOVE) status = Status.BLACK_MOVE;
	}
	
	public String toString() {
		String string = "";
		String newLine = System.getProperty("line.separator");
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				string += " " + grid[j][i];
			}
			string += newLine;
		}
		return string;
	}

	// Get in set metode.
	
	public static int getSize() {
		return SIZE;
	}

	public void setSize(int n) {
		SIZE = n;
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
	
	public Player getPlayer1 () {
		return this.player1;
	}
	
	public Player getPlayer2 () {
		return this.player2;
	}
}