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
	
	private static final int DEPTH = 4;
	
	// vrne potezo, ki naj jo igralec odigra
	public static Move optimalMove(Game game, Player me) {
		return alphaBetaMoves(game, DEPTH, LOSE, WIN, me).getMove();
	}
	
	public static EvaluatedMove alphaBetaMoves(Game game, int depth, int alpha, int beta, Player me) {
		List<Move> possibleMoves = game.possibleMoves();
		EvaluatedMove optimalMove = new EvaluatedMove(possibleMoves.get(0), LOSE); 
		for (Move move : possibleMoves) {
//			System.out.println(move);
			Game temporaryGame = new Game(game);
			temporaryGame.play(move);
//			int temporaryEvaluation = evaluatePosition(temporaryGame, me);
//			System.out.println(temporaryEvaluation);
			int temporaryEvaluation = alphaBetaPosition(temporaryGame, depth-1, alpha, beta, me);
			if (temporaryEvaluation > optimalMove.getValue()) {
				optimalMove.setMove(move);
				optimalMove.setValue(temporaryEvaluation);
//			System.out.println(optimalMove);
//			System.out.println(move);
			}
		}
		System.out.println("=========");
		return optimalMove;
	}
	
	public static int alphaBetaPosition(Game game, int depth, int alpha, int beta, Player me) {
//		System.out.println(".");
		Status status = game.getStatus();
		if (status == Status.BLACK_WIN) {
			if (me.getPlayerColor() == StoneColor.BLACK) return WIN;
			else return LOSE;
			}
		else if (status == Status.WHITE_WIN) {
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
				maximumEvaluation = Math.max(maximumEvaluation, evaluation);
				alpha = Math.max(alpha, evaluation);
				if (alpha <= beta) break;
			}
			return maximumEvaluation;
		}
		else {
			int minimumEvaluation = WIN;
			for (Move move : possibleMoves) {
				Game temporaryGame = new Game(game);
				temporaryGame.play(move);
				int evaluation = alphaBetaPosition(temporaryGame, depth-1, alpha, beta, me);
				minimumEvaluation = Math.min(minimumEvaluation, evaluation);
				alpha = Math.min(alpha, evaluation);
				if (alpha >= beta) break;
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
		if (chain.getColor() == me.getPlayerColor()) return (int) Math.pow(4, chain.getStrength());
		else if (chain.getColor() != StoneColor.EMPTY) return - (int) Math.pow(4, chain.getStrength());
		return 0;
	}
		
}
