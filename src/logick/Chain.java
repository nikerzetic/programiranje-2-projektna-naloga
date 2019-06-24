package logick;

// Veriga je predstavljena z dvema tabelama koordinat.
// Prazna veriga ima barvo EMPTY, veriga s kamencki iste barve pa se BLACK ali WHITE.
// Verige s kamencki razlicnih barv program sam izbrise.

public class Chain {
	
	// Barva verige.
	private StoneColor chainColor = StoneColor.EMPTY;
	
	// Moc verige.
	private int strength = 0;
	
	// Tabeli koordinat.
	private int[] xs;
	private int[] ys;	
	
	// Konstruktor verige.
	public Chain(int[] x, int[] y) {
		this.xs = x;
		this.ys = y;
	}
	
	// Konstruktor verige iz dane verige.
	public Chain(Chain chain) {
		this.xs = chain.getXS();
		this.ys = chain.getYS();
		this.strength = chain.getStrength();
		this.chainColor = chain.getColor();
	}
	
	// Preveri, ali veriga vsebuje dano potezo.
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
