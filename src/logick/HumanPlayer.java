package logick;

import gui.MainWindow;
import intelligence.MoveFinder;

// Objekt, ki igra clovekove poteze.
public class HumanPlayer extends Player {
	
	public HumanPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		this.human = true;
		
	}

	@Override
	public void playYourMove() {
		this.master.repaintCanvas();
	}

	@Override
	public MoveFinder getWorker() {
		return null;
	}
	
}
