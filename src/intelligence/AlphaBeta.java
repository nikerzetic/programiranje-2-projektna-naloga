package intelligence;

import java.util.List;

import logick.Chain;
import logick.Game;
import logick.Move;
import logick.Player;
import logick.Status;
import logick.StoneColor;

public class AlphaBeta {

	private static final int WIN = (1 << 20);
	private static final int LOSE = -WIN;
	private static final int DRAW = 0;
	
	private static final int DEPTH = 2;
	
	// vrne potezo, ki naj jo igralec odigra
	public static Move optimalMove(Game game, Player me) {
		return alphaBetaMoves(game, DEPTH, LOSE, WIN, me).getMove();
	}
	
	public static EvaluatedMove alphaBetaMoves(Game game, int depth, int alpha, int beta, Player me) { // TODO lahko bi dodali sprotno ciscenje 
		List<Move> possibleMoves = game.possibleMoves();
		EvaluatedMove optimalMove = new EvaluatedMove(possibleMoves.get(0), LOSE); 
		for (Move move : possibleMoves) {
//			System.out.println(move);
			Game temporaryGame = new Game(game);
			temporaryGame.play(move);
			System.out.println(temporaryGame);
			System.out.println("T: " + temporaryGame.getChains());
			int temporaryEvaluation = alphaBetaPosition(temporaryGame, depth-1, alpha, beta, me);
//			for (int i = 0; i < depth-1; i++) System.out.print("  ");
//			System.out.println(temporaryEvaluation);
			if (temporaryEvaluation > optimalMove.getValue()) {
				optimalMove.setMove(move);
				optimalMove.setValue(temporaryEvaluation);
//			System.out.println(optimalMove);
//			System.out.println(move);
			}
		}
//		for (int i = 0; i < depth; i++) System.out.print("  ");
//		System.out.println(optimalMove.getValue());
		System.out.println("=========");
		return optimalMove;
	}
	
	public static int alphaBetaPosition(Game game, int depth, int alpha, int beta, Player me) { // TODO ta metoda nikdar ne vrne win
		Status status = game.isWin();
		System.out.println(System.identityHashCode(status) + " " +  status);
		if (status == Status.BLACK_WIN) {
			System.out.println("O");
			if (me.getPlayerColor() == StoneColor.BLACK) return WIN;
			else return LOSE;
			}
		else if (status == Status.WHITE_WIN) {
			System.out.println("O");
			if (me.getPlayerColor() == StoneColor.WHITE) return WIN;
			else return LOSE;
			}
		else if (status == Status.DRAW) return DRAW;
		
		if (depth == 0) return evaluatePosition(game, me);
		
		List<Move> possibleMoves = game.possibleMoves(); // TODO med mozne poteze doda ze odigrane poteze!
		
		if (game.getOnMove() == me) {
			int maximumEvaluation = LOSE;
			for (Move move : possibleMoves) {
				Game temporaryGame = new Game(game);
				temporaryGame.play(move);
				int evaluation = alphaBetaPosition(temporaryGame, depth-1, alpha, beta, me);
//				for (int i = 0; i < depth-1; i++) System.out.print("  ");
//				System.out.println(evaluation);
				maximumEvaluation = Math.max(maximumEvaluation, evaluation);
				alpha = Math.max(alpha, evaluation);
				if (beta <= alpha) break;
			}
			return maximumEvaluation;
		}
		else {
			int minimumEvaluation = WIN;
			for (Move move : possibleMoves) {
				Game temporaryGame = new Game(game);
				temporaryGame.play(move);
				int evaluation = alphaBetaPosition(temporaryGame, depth-1, alpha, beta, me);
//				for (int i = 0; i < depth-1; i++) System.out.print("  ");
//				System.out.println(evaluation);
				minimumEvaluation = Math.min(minimumEvaluation, evaluation);
				alpha = Math.min(alpha, evaluation);
				if (beta <= alpha) break;
			}
			return minimumEvaluation;
		}
	}
	
	public static int evaluatePosition(Game game, Player me) {
		int evaluation = 0;
		for (Chain chain : game.getChains()) {
			if (chain.getStrength() > 0) evaluation += evaluateChain(chain, game, me);
		}
		return evaluation;
	}
	
	public static int evaluateChain(Chain chain, Game game, Player me) {
		if (chain.getColor() == me.getPlayerColor()) return chain.getStrength();// (int) Math.pow(8, chain.getStrength()-1);
		else if (chain.getColor() != StoneColor.EMPTY) return - chain.getStrength(); // (int) Math.pow(8, chain.getStrength()-1);
		return 0;
	}
		
}
