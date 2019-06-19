package logick;

import gui.MainWindow;
import intelligence.AlphaBeta;

// Objekt, ki igra racunalnikove poteze.

public class ComputerPlayer extends Player {
	
	
	public ComputerPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		
		// Pove, da ni clovek ampak racunalnik.
		human = false;
		
	}
	
	// Metoda, ki odigra racunalnikovo potezo in na potezo nastavi nasprotnika.
	@Override
	public void playYourMove() {
		if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
			this.master.getGame().getStatus() == Status.BLACK_MOVE) {
			
			Move toPlay = AlphaBeta.optimalMove(this.master.getGame(), this);
			this.master.getGame().play(toPlay);
			
			this.master.getGame().status();
			this.master.getGame().setOnMove(this.master.getGame().oponent());
			
			if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
				this.master.getGame().getStatus() == Status.BLACK_MOVE) {
				this.master.getGame().setStatus(this.master.newStatus());
				this.master.getGame().getOnMove().playYourMove();
				this.master.repaintCanvas();
			}
		}
	}

}
