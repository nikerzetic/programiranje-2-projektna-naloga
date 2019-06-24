package intelligence;

import logick.Move;

// Razred, ki hrani potezo in njeno vrednost.

public class EvaluatedMove {
		
	private Move move;
	private int value;
		
	// Konstruktor.
	public EvaluatedMove (Move move, int value) {
		this.move = move;
		this.value = value;
	}

	// Oblika izpisa v print stavku.
	public String toString() {
		return "EvaluatedMove(" + move + ", " + value + ")";
	}
	
	// Get in set metode.
	
	public Move getMove() {
		return this.move;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setMove(Move move) {
		this.move = move;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
