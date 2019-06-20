package logick;

import gui.MainWindow;
import intelligence.MoveFinder;

// Player je abstrakten razred, ki zna dolociti, ali je igralec na potezi in v primeru racunalnika odigrati potezo. 
// Lahko je ComputerPlayer ali HumanPlayer.

public abstract class Player {
	
	private StoneColor playerColor;
	
	protected boolean human;
	
	protected MainWindow master;
	
	// Konstruktor.
	public Player(MainWindow master, StoneColor color) {
		
		this.master = master;
		this.playerColor = color;
		
	}
	
	public abstract void playYourMove();
	
	public void setPlayerColor(StoneColor color) {
		this.playerColor = color;
	}
	
	public StoneColor getPlayerColor() {
		return this.playerColor;
	}

	public boolean getHuman() {
		return this.human;
	}
	
	public String toString() {
		return "Player(" + this.playerColor +")";
	}
	
	public Status getStatus() {
		if (this.playerColor == StoneColor.WHITE) return Status.WHITE_MOVE;
		else if (this.playerColor == StoneColor.BLACK) return Status.BLACK_MOVE;
		return Status.DRAW; // se ne more zgoditi
	}
	
	public MainWindow getMaster() {
		return this.master;
	}

	public abstract MoveFinder getWorker();
	
}
