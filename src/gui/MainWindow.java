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
	
	private JLabel status_label;
	
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
		
		gameComputerHuman = new JMenuItem("Racunalnik proti cloveku");
		gameMenu.add(gameComputerHuman);
		gameComputerHuman.addActionListener(this);
		
		gameHumanComputer = new JMenuItem("Clovek proti racunalniku");
		gameMenu.add(gameHumanComputer);
		gameHumanComputer.addActionListener(this);
		
		gameComputerComputer = new JMenuItem("Racunalnik proti racunalniku");
		gameMenu.add(gameComputerComputer);
		gameComputerComputer.addActionListener(this);

		gameHumanHuman = new JMenuItem("Clovek proti cloveku");
		gameMenu.add(gameHumanHuman);
		gameHumanHuman.addActionListener(this);
		
		canvas = new PlayingCanvas(this);
		GridBagConstraints canvas_layout = new GridBagConstraints();
		canvas_layout.gridx = 0;
		canvas_layout.gridy = 0;
		canvas_layout.fill = GridBagConstraints.BOTH;
		canvas_layout.weightx = 1.0;
		canvas_layout.weighty = 1.0;
		getContentPane().add(canvas, canvas_layout);
		
		//label vrstica
		status_label = new JLabel();
		status_label.setFont(new Font(status_label.getFont().getName(), status_label.getFont().getStyle(), 20));
		GridBagConstraints status_label_layout = new GridBagConstraints();
		status_label_layout.gridx = 0;
		status_label_layout.gridy = 1;
		status_label_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status_label, status_label_layout);
		
		newGame(new HumanPlayer(StoneColor.WHITE), new HumanPlayer(StoneColor.BLACK));

		this.repaintCanvas();
		
	}
	
	public void newGame(Player player1, Player player2) {
		this.game = new Game();
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.game.setStatus(Status.BLACK_MOVE);
		this.repaintCanvas();
	}
	
	public void repaintCanvas() {
		if (game == null) {
			this.status_label.setText("Igra ni v teku.");
		}
		else {
			if (this.game.getStatus() == Status.DRAW) this.status_label.setText("Izenacenje.");
			else if (this.game.getStatus() == Status.WHITE_MOVE) this.status_label.setText("Beli igralec je na potezi.");
			else if (this.game.getStatus() == Status.BLACK_MOVE) this.status_label.setText("Crni igralec je na potezi.");
			else if (this.game.getStatus() == Status.WHITE_WIN) this.status_label.setText("Beli igralec je zmagal.");
			else if (this.game.getStatus() == Status.BLACK_WIN) this.status_label.setText("Crni igralec je zmagal.");
		}
		this.repaint();
	}
	
	public void playMove(Move move) {
		
	}
	
	public void click(int x, int y) {
		Move move = new Move(x, y);
		if (this.game.getStatus() == Status.WHITE_MOVE && player1.getHuman()) {
			// preveri, ce je poteza veljavna, in spremeni barvo polja + osvezi platno + postavi drugega igralca na vrsto
			if (this.isValidMove(move)) {
				game.play(move);
				this.game.status();
				this.game.setOnMove(player2); // TODO neuporbano v novi verziji
				if (this.game.getStatus() == Status.WHITE_MOVE) this.game.setStatus(Status.BLACK_MOVE);
				this.repaintCanvas();
			}
		}
		else if (this.game.getStatus() == Status.BLACK_MOVE && player2.getHuman()) {
			// preveri, ce je poteza veljavna, in spremeni barvo polja + osvezi platno + postavi drugega igralca na vrsto
			if (this.isValidMove(move)) {
				game.play(move);
				this.game.status();
				this.game.setOnMove(player1); // TODO neuporabno v novi verziji
				if (this.game.getStatus() == Status.BLACK_MOVE) this.game.setStatus(Status.WHITE_MOVE);
				this.repaintCanvas();
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
