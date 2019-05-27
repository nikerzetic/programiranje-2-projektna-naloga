package logick;

public class HumanPlayer extends Player {
	
	public HumanPlayer(StoneColor color) {
		
		super(color);
		this.human = true;
		
	}

	@Override
	public Move playYourMove() {
		return null;
	}
	
	

}
