package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logick.*;

// Hrani trenutno stanje igre in nadzoruje njen potek

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener{
	
	private Game game;
	private PlayingCanvas canvas;
	private Player player1;
	private Player player2;
	
	private JMenuItem gameComputerHuman;
	private JMenuItem gameHumanComputer;
	private JMenuItem gameComputerComputer;
	private JMenuItem gameHumanHuman;
	
	public MainWindow() {
		
		setTitle("Gomoku");

		// glavni meni
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		JMenu gameMenu = new JMenu("Nova igra");
		mainMenu.add(gameMenu);
		
		gameComputerHuman = new JMenuItem("Raèunalnik proti èloveku");
		gameMenu.add(gameComputerHuman);
		gameComputerHuman.addActionListener(this);
		
		gameHumanComputer = new JMenuItem("Èlovek proti raèunalniku");
		gameMenu.add(gameHumanComputer);
		gameHumanComputer.addActionListener(this);
		
		gameComputerComputer = new JMenuItem("Raèunalnik proti raèunalniku");
		gameMenu.add(gameComputerComputer);
		gameComputerComputer.addActionListener(this);

		gameHumanHuman = new JMenuItem("Èlovek proti èloveku");
		gameMenu.add(gameHumanHuman);
		gameHumanHuman.addActionListener(this);
		
		newGame(new HumanPlayer(StoneColor.WHITE), new ComputerPlayer(StoneColor.BLACK));
		
		canvas = new PlayingCanvas(this);
		add(canvas);
		
	}
	
	public void newGame(Player player1, Player player2) {
		this.game = new Game();
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.game.setOnMove(player1);
	}
	
	public void repaintCanvas() {
		canvas.repaint();
	}
	
	public void playMove(Move move) {
		
	}
	
	public void click(int x, int y) {
		Move move = new Move(x, y);
		if (player1 == this.game.getOnMove() && player1.getHuman()) {
			// preveri, ce je poteza veljavna, in spremeni barvo polja + osvezi platno + postavi drugega igralca na vrsto
			if (this.isValidMove(move)) {
				System.out.println(this.game.getOnMove() + " " + x + " " + y);
				game.play(move);
				this.repaintCanvas();
				this.game.status();
				this.game.setOnMove(player2);
				this.game.setStatus(player2.getStatus());
			}
		}
		else if (player2 == this.game.getOnMove() && player2.getHuman()) {
			// preveri, ce je poteza veljavna, in spremeni barvo polja + osvezi platno + postavi drugega igralca na vrsto
			if (this.isValidMove(move)) {
				System.out.println(this.game.getOnMove() + " " + x + " " + y);
				game.play(move);
				this.repaintCanvas();
				this.game.status();
				this.game.setOnMove(player1);
			}
		}
	}
	
	private boolean isValidMove(Move move) {
		for (Move possibleMove : this.game.possibleMoves()) {
			if (move.getX() == possibleMove.getX() && move.getY() == possibleMove.getY()) return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();
		if (source == gameComputerHuman) {
			newGame(new ComputerPlayer(StoneColor.WHITE), new HumanPlayer(StoneColor.BLACK));
		}
		
		if (source == gameHumanComputer) {
			newGame(new HumanPlayer(StoneColor.WHITE), new ComputerPlayer(StoneColor.BLACK));
		}
		
		if (source == gameComputerComputer) {
			newGame(new ComputerPlayer(StoneColor.WHITE), new ComputerPlayer(StoneColor.BLACK));
		}
		
		if (source == gameHumanHuman) {
			newGame(new HumanPlayer(StoneColor.WHITE), new HumanPlayer(StoneColor.BLACK));
		}
		
	}
	
	public Game getGame() {
		return game;
	}

}
