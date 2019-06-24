package logick;

import gui.MainWindow;
import intelligence.MoveFinder;

// Objekt, ki igra racunalnikove poteze.

public class ComputerPlayer extends Player {
	
	// SwingWorker za iskanje poteze v ozadju.
	private MoveFinder worker;
	
	// Konstruktor.
	public ComputerPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		
		// Pove, da ni clovek, ampak racunalnik.
		human = false;
		
	}
	
	// Metoda, ki osvezi platno in ustvari SwingWorkerja.
	// Ta potem izracuna in odigra racunalnikovo potezeo.
	@Override
	public void playYourMove() {
		this.master.repaintCanvas();

		this.worker = new MoveFinder(this);
		if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
				this.master.getGame().getStatus() == Status.BLACK_MOVE) this.worker.execute();
	}

	// Get in set metode.
	
	@Override
	public MoveFinder getWorker() {
		return this.worker;
	}
	
}
