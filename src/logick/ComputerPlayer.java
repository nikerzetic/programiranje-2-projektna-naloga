package logick;

import gui.MainWindow;
import intelligence.MoveFinder;

// Objekt, ki igra racunalnikove poteze.

public class ComputerPlayer extends Player {
	
	private MoveFinder worker;

	
	public ComputerPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		
		// Pove, da ni clovek ampak racunalnik.
		human = false;
		
	}
	
	// Metoda, ki odigra racunalnikovo potezo in na potezo nastavi nasprotnika.
	@Override
	public void playYourMove() {
		
		this.master.repaintCanvas();

		this.worker = new MoveFinder(this);

		if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
				this.master.getGame().getStatus() == Status.BLACK_MOVE) {

			this.worker.execute(); // TODO to lahko povzroca tezave, ker je definiran zunaj in se ne vsakic na novo ustvari
			
		}
	}


	@Override
	public MoveFinder getWorker() {
		return this.worker;
	}
	
}
