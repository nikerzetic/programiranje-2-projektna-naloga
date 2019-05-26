package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	private JLabel status;
	
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
		
		this.setLayout(new GridBagLayout());
		
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
		
		newGame(new HumanPlayer(StoneColor.WHITE), new HumanPlayer(StoneColor.BLACK));
		
		canvas = new PlayingCanvas(this);
		GridBagConstraints canvas_layout = new GridBagConstraints();
		canvas_layout.gridx = 0;
		canvas_layout.gridy = 0;
		canvas_layout.fill = GridBagConstraints.BOTH;
		canvas_layout.weightx = 1.0;
		canvas_layout.weighty = 1.0;
		getContentPane().add(canvas, canvas_layout);
		
		//label vrstica
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
	}
	
	public void newGame(Player player1, Player player2) {
		this.game = new Game();
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.game.setOnMove(player1);
		repaint();
	}
	
	public void repaintCanvas() {
		if (game == null) {
			status.setText("The game is not in progress");
		}
		else {
			switch(game.status()) {
			case BLACK_MOVE: status.setText("Black is on the move"); break;
			case WHITE_MOVE: status.setText("White is on the move"); break;
			case BLACK_WIN: status.setText("Black is the winner!"); break;
			case WHITE_WIN: status.setText("White is the winner!"); break;
			case DRAW: status.setText("Draw!"); break;
			}
		}
		canvas.repaint();
	}
	
	public void playMove(Move move) {
		
	}
	
	public void click(int x, int y) {
		Move move = new Move(x, y);
		if (this.game.getStatus() == Status.WHITE_MOVE && player1.getHuman()) {
			// preveri, ce je poteza veljavna, in spremeni barvo polja + osvezi platno + postavi drugega igralca na vrsto
			if (this.isValidMove(move)) {
				game.play(move);
				this.repaintCanvas();
				this.game.status();
				this.game.setOnMove(player2); // TODO neuporbano v novi verziji
				if (this.game.getStatus() == Status.WHITE_MOVE) this.game.setStatus(Status.BLACK_MOVE);
			}
		}
		else if (this.game.getStatus() == Status.BLACK_MOVE && player2.getHuman()) {
			// preveri, ce je poteza veljavna, in spremeni barvo polja + osvezi platno + postavi drugega igralca na vrsto
			if (this.isValidMove(move)) {
				game.play(move);
				this.repaintCanvas();
				this.game.status();
				this.game.setOnMove(player1); // TODO neuporabno v novi verziji
				if (this.game.getStatus() == Status.BLACK_MOVE) this.game.setStatus(Status.WHITE_MOVE);
			}
		}
		System.out.println(this.game.getStatus());
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
