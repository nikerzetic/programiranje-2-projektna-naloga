package logick;

import gui.MainWindow;
import intelligence.AlphaBeta;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		human = false;
		
	}

	@Override
	public void playYourMove() {
		if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
			this.master.getGame().getStatus() == Status.BLACK_MOVE) {
			
			this.master.getGame().play(AlphaBeta.optimalMove(this.master.getGame(), this));
			
			this.master.getGame().status();
			this.master.getGame().setOnMove(this.master.oponent());
			
			if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
				this.master.getGame().getStatus() == Status.BLACK_MOVE) {
				this.master.getGame().setStatus(this.master.newStatus());
				this.master.getGame().getOnMove().playYourMove();
				this.master.repaintCanvas();
			}
		}
	}

}
