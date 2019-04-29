package logick;

// hrani trenutno stanje igre in nadzoruje njen potek

public class Master {
	
	private Game game;
	private Player player1;
	private Player player2;
	
	public Master() {
	}
	
	public void newGame() {
		game = new Game();
		// ...
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
