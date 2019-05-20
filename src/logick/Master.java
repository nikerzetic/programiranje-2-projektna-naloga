package logick;

import gui.MainWindow;
import gui.PlayingCanvas;

// to je razred, ki ga bomo kmalu izbrisali

public class Master {
	
	private Game game;
	private Player player1;
	private Player player2;
	private PlayingCanvas canvas;
	
	public Master(MainWindow mainWindow) {
		canvas = mainWindow.getCanvas();                                                    // TU SE VSE ZACNE ZAPLETATI: logick ne bi smel vsebovati kode, ki je povezana z GUI
		newGame(new HumanPlayer(StoneColor.WHITE), new ComputerPlayer(StoneColor.BLACK));
	}
	
	public void newGame(Player player1, Player player2) {
		game = new Game();
		this.player1 = player1;
		this.player2 = player2;
		game.onMove = player1;
		mainLoop();
	}

	public void mainLoop() {
		while (true) {
			game.onMove.playMove(canvas);
		}
	}
	
//	public void action() {
//		switch (game.status()) {
//		case BLACK_WIN:
//		case WHITE_WIN:
//		case DRAW:
//			break;
//		case BLACK_MOVE:
//		case WHITE_MOVE:
//			if (game.onMove == player1) player1sTurn();
//			else if (game.onMove == player2) player2sTurn();
//		}
//	}
	
	public Game getGame() {
		return game;
	}
}
