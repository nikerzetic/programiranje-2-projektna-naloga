package intelligence;

import logick.Move;
import logick.StoneColor;

public class EvaluatedMove {
		
	private Move move;
	private int value;
		
	public EvaluatedMove (Move move, int value) {
		this.move = move;
		this.value = value;
	}
	
	public Move getMove() {
		return this.move;
	}
	
	public int getValue() {
		return this.value;
	}
	
}
