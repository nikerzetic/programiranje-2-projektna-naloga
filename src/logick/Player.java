package logick;

// Player je razred, ki zna dolociti, ali je igralec na potezi in v primeru racunalnika odigrati potezo. Lahko je ComputerPlayer ali HumanPlayer

public abstract class Player {
	
	private StoneColor playerColor;
	
	protected boolean human;
	
	public Player(StoneColor color) {
		
		playerColor = color;
		
	}
	
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
		return "Player(" + this.playerColor +"); This is a human player: " + this.human;
	}
	
}
