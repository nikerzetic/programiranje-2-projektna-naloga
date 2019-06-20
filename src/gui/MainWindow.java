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
	// JPanel, na katerem igramo igro.
	private PlayingCanvas canvas;
	
	// Statusna vrstica.
	private JLabel status_label;
	
	// Opcije iger.
	private JMenuItem gameComputerHuman;
	private JMenuItem gameHumanComputer;
	private JMenuItem gameComputerComputer;
	private JMenuItem gameHumanHuman;
	
	// Konstuktor
	public MainWindow() {
		
		// Nastavitev naslova igre.
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Glavni meni.
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		JMenu gameMenu = new JMenu("Nova igra");
		mainMenu.add(gameMenu);
		
		// Nastavi razporeditev okna.
		this.setLayout(new GridBagLayout());
		
		// Moznosti v meniju.
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
		
		// Dodamo igralno polje na okno.
		canvas = new PlayingCanvas(this);
		GridBagConstraints canvas_layout = new GridBagConstraints();
		canvas_layout.gridx = 0;
		canvas_layout.gridy = 0;
		canvas_layout.fill = GridBagConstraints.BOTH;
		canvas_layout.weightx = 1.0;
		canvas_layout.weighty = 1.0;
		getContentPane().add(canvas, canvas_layout);
		
		// Dodamo statusno vrstico, ki opisuje stanje igre.
		status_label = new JLabel();
		status_label.setFont(new Font(status_label.getFont().getName(), status_label.getFont().getStyle(), 20));
		GridBagConstraints status_label_layout = new GridBagConstraints();
		status_label_layout.gridx = 0;
		status_label_layout.gridy = 1;
		status_label_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status_label, status_label_layout);
		
		// Zacnemo novo igro.
		newGame(new HumanPlayer(this, StoneColor.BLACK), new ComputerPlayer(this, StoneColor.WHITE));
		
	}
	
	// Metoda za nastavitev nove igre.
	public void newGame(Player player1, Player player2) {
		this.stopInProgress();
		
		this.game = new Game(player1, player2);
		
		this.game.setOnMove(player1);
		this.game.setStatus(Status.BLACK_MOVE);
		this.repaintCanvas();
		
		this.game.getOnMove().playYourMove();
	}
	
	// Metoda, ki znova izrise elemente v oknu.
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
	
	// Metoda, ki jo platno poklice ob kliku
	public void click(int x, int y) {
		Move move = new Move(x, y);
		if (this.game.getOnMove().getHuman()) {
			// Preveri, ce je poteza veljavna. Ce je potem spremeni barvo polja, osvezi platno in postavi drugega igralca na vrsto.
			if (this.isValidMove(move)) {
				game.play(move);
				this.repaintCanvas();
				this.game.getOnMove().playYourMove();
			}
		}
	}
	
	// Metoda, ki preveri, ce je poteza veljavna.
	private boolean isValidMove(Move move) {
		for (Move possibleMove : this.game.possibleMoves()) {
			if (move.getX() == possibleMove.getX() && move.getY() == possibleMove.getY()) return true;
		}
		return false;
	}
	
//	private void mainLoop() {
//		while(true) {
//			if (this.game.getOnMove().getHuman()) {
//				Thread.yield();
//			}
//			else {
//				this.game.play(this.game.getOnMove().playYourMove());
//			}
//		}
//	}
	
	// Metoda, ki nastavi igro na tisto, ki smo jo izbrali iz Menija.
	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();
		if (source == gameComputerHuman) {
			newGame(new ComputerPlayer(this, StoneColor.BLACK), new HumanPlayer(this, StoneColor.WHITE));
		}
		
		if (source == gameHumanComputer) {
			newGame(new HumanPlayer(this, StoneColor.BLACK), new ComputerPlayer(this, StoneColor.WHITE));
		}
		
		if (source == gameComputerComputer) {
			newGame(new ComputerPlayer(this, StoneColor.BLACK), new ComputerPlayer(this, StoneColor.WHITE));
		}
		
		if (source == gameHumanHuman) {
			newGame(new HumanPlayer(this, StoneColor.BLACK), new HumanPlayer(this, StoneColor.WHITE));
		}
		
	}
	

	private void stopInProgress() {
		if (this.game != null) {
			if (!this.game.getPlayer1().getHuman()) this.game.getPlayer1().getWorker().cancel(true);
			if (!this.game.getPlayer2().getHuman()) this.game.getPlayer2().getWorker().cancel(true);
		}
	
	// Get in set metode.
	public Game getGame() {
		return game;
	}

}
