package logick;

// hrani trenutno stanje igre in nadzoruje njen potek

public class Master {
	
	private Game game;
	private Player player1;
	private Player player2;
	
	public Master() {
	}
	
	public void newGame(Player player1, Player player2) {
		game = new Game();
		this.player1 = player1;
		this.player2 = player2;
		
	}
	
	public void action() {
		switch (game.status()) {
		case BLACK_WIN:
		case WHITE_WIN:
		case DRAW:
			break;
		case BLACK_MOVE:
		case WHITE_MOVE:
			if (game.onMove == player1) player1sTurn();
			else if (game.onMove == player2) player2sTurn();
		}
	}
	
	public void player1sTurn() {
		
	}
	
	public void player2sTurn() {
		
	}
	
}
