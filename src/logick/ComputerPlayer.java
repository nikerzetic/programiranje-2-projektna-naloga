package logick;

import gui.MainWindow;
import intelligence.MoveFinder;

public class ComputerPlayer extends Player {
	
	private MoveFinder worker;
	
	public ComputerPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		human = false;
		this.worker = new MoveFinder(this);
		
	}

	@Override
	public void playYourMove() {
		
		this.master.repaintCanvas();

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
