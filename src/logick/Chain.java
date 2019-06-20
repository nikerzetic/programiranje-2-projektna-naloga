package logick;

public class Chain {
	// Veriga je predstavljena z dvema tabelama koordinat.
	// Ce so vsi njeni kamencki iste barve, ima se barvo (EMPTY, BLACK, WHITE).
	
	// Barva verige.
	private StoneColor chainColor = StoneColor.EMPTY;
	
	// Moc verige.
	private int strength = 0;
	
	// Tabeli koordinat.
	private int[] xs;
	private int[] ys;	
	
	// Konstruktor verig.
	public Chain(int[] x, int[] y) {
		this.xs = x;
		this.ys = y;
	}
	
	// Preveri, èe tabela vsebuje potezo.
	public boolean containsMove(Move move) {
		boolean value = false;
		for (int x : this.getXS()) {
			if (move.getX() == x) {
				value = true;
				break;
			}
		}
		if (value) for (int y : this.getYS()) {
			if (move.getY() == y) return true;
		}
		return false;
	}
	
	public String toString() {
		String chainString = "{";
		for (int i = 0; i < 4; i++) {
			chainString += "{" + xs[i] + ", " + ys[i] + "}, ";
		}
		chainString += "{" + xs[4] + ", " + ys[4] + "}}";
		return chainString;
	}
	
	// Get in set metode.
	public int[] getXS() {
		return this.xs;
	}

	public int[] getYS() {
		return this.ys;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public StoneColor getColor() {
		return this.chainColor;
	}
	
	public void setColor(StoneColor color) {
		this.chainColor = color;
	}
}
