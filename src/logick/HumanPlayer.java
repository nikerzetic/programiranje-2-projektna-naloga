package logick;

import gui.MainWindow;

public class HumanPlayer extends Player {
	
	public HumanPlayer(MainWindow master, StoneColor color) {
		
		super(master, color);
		this.human = true;
		
	}

	@Override
	public void playYourMove() {}
	
	

}
