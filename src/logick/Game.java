package logick;

import java.util.*;

public class Game {

	// velikost plosce 
	public static int size = 15;
	
	// seznam vseh moznih verig na plosci
	public static final List<Chain> CHAINS = new LinkedList<Chain>();
	
	// matrika vseh presecisc
	private Intersection[][] grid;
	
	static {
		
		// sestavi seznam vseh moznih peteric na igralnem polju
		
		int[][] vectors = {{0, 1}, {1, 0}, {1, 1}, {-1, -1}};
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int[] v : vectors) {
					int vx = v[0];
					int vy = v[1];
					if (0 <= x + 5 * vx && x + 5 * vx <= size &&
							0 <= y + 5 * vy && y + 5 * vy <= size
							) {
						int[] xs = new int[5];						
						int[] ys = new int[5];
						for (int i = 0; i < 5; i++) {
							xs[i] = x + i * vx;
							ys[i] = y + i* vy;
						}
						CHAINS.add(new Chain(xs, ys));
					}
				}
			}
		}
	}
	
}