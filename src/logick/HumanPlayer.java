package logick;

import gui.MainWindow;
import intelligence.MoveFinder;

// Objekt, ki igra clovekove poteze.

public class HumanPlayer extends Player {
	
	// Konstruktor.
	public HumanPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		this.human = true;
		
	}

	// Metoda, ki v praksi ne pocne nic.
	@Override
	public void playYourMove() {
		this.master.repaintCanvas();
	}

	// Neimplementirana metoda.
	@Override
	public MoveFinder getWorker() {
		return null;
	}
	
}
