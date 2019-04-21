import logick.*;

public class Gomoku {

	public static void main(String[] args) {
		Game game = new Game();
		
		// tests if the program properly creates all the chains
		for (Chain chain : game.CHAINS) {
			System.out.println(chain);
		}
	}

}
