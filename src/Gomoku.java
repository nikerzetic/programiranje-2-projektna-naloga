import logick.*;

public class Gomoku {

	public static void main(String[] args) {
		Game game = new Game();
		
		// tests if the program properly creates all the chains
		for (Chain chain : game.getChains()) {
			System.out.println(chain);
		}
		
		System.out.println(game.getChains().size());
	}

}
