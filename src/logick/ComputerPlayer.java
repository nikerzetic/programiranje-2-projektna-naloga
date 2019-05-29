package logick;

import java.util.List;

import gui.MainWindow;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		human = false;
		
	}

	@Override
	public void playYourMove() {
		if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
			this.master.getGame().getStatus() == Status.BLACK_MOVE) {
			
			System.out.println("0:" + this.master.getGame().getStatus() + " " + this.master.getGame().getOnMove());
			
			List<Move> randomMoves = this.master.getGame().possibleMoves();
			int randomNumber = (int)(Math.random() * randomMoves.size());
			this.master.getGame().play(randomMoves.get(randomNumber));
			this.master.getGame().status();
			System.out.println("1:" + this.master.getGame().getStatus() + " " + this.master.getGame().getOnMove());
			this.master.getGame().setOnMove(this.master.oponent());
			
			if (this.master.getGame().getStatus() == Status.WHITE_MOVE || 
				this.master.getGame().getStatus() == Status.BLACK_MOVE) {
				System.out.println("2:" + this.master.getGame().getStatus() + " " + this.master.getGame().getOnMove());
				this.master.getGame().setStatus(this.master.newStatus());
				System.out.println("3:" + this.master.getGame().getStatus() + " " + this.master.getGame().getOnMove());
				this.master.getGame().getOnMove().playYourMove();
				this.master.repaintCanvas();
			}
			
			System.out.println("4:" + this.master.getGame().getStatus() + " " + this.master.getGame().getOnMove());
		}
		System.out.println("5:" + this.master.getGame().getStatus() + " " + this.master.getGame().getOnMove());
	}
	
}
