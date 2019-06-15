package intelligence;

import java.util.LinkedList;
import java.util.List;

import logick.*;

public class Minimax {
	
	private static final int Win = (1 << 20);
	private static final int Lose = -Win;
	private static final int Draw = 0;
	
	
	public static List<EvaluatedMove> evaluateMove (Game game, int depth, Player player) {
		List<EvaluatedMove> evaluatedMoves = new LinkedList<EvaluatedMove> ();
		List<Move> possibleMoves = game.possibleMoves();
		for (Move move: possibleMoves) {
			Game temporaryGame = new Game(game);
			temporaryGame.play(move);
			int evaluation = minimaxPosition (temporaryGame, depth - 1, player);
			evaluatedMoves.add(new EvaluatedMove(move, evaluation));
		}
		return evaluatedMoves;
	}
	
	public static int minimaxPosition (Game game, int depth, Player player) {
		Status status = game.status();
		switch(status) {
		case BLACK_WIN: return (player.getPlayerColor() == StoneColor.BLACK ? Win: Lose);
		case WHITE_WIN: return (player.getPlayerColor() == StoneColor.WHITE ? Win: Lose);
		case DRAW: return Draw;
		default:
			if (depth == 0) return evaluatePosition(game, player);
			List<EvaluatedMove> evaluatedMoves = evaluateMove(game, depth, player);
			if (game.getOnMove() == player) return maxEvaluation(evaluatedMoves);
			else return minEvaluation(evaluatedMoves);
		}	
	}
	
	public static int maxEvaluation (List<EvaluatedMove> evaluatedMoves) {
		int max = Lose;
		for (EvaluatedMove evaluatedMove: evaluatedMoves) {
			if(evaluatedMove.getValue() > max) {max = evaluatedMove.getValue();}
		}
		return max;
	}
	
	public static Move maxMove(List<EvaluatedMove> evaluatedMoves) {
		int max = Lose;
		Move move = null;
		for (EvaluatedMove evaluatedMove: evaluatedMoves) {
			if (evaluatedMove.getValue() >= max) {
				max = evaluatedMove.getValue();
				move = evaluatedMove.getMove();	
			}
		}
		return move;
	}
	
	public static int minEvaluation (List<EvaluatedMove> evaluatedMoves) {
		int min = Win;
		for (EvaluatedMove evaluatedMove: evaluatedMoves) {
			if(evaluatedMove.getValue() < min) {min = evaluatedMove.getValue();}
		}
		return min;
	}
	
	public static int evaluatePosition (Game game, Player player) {
		int evaluation = 0;
		List<Chain> chains = game.getChains();
		for (Chain chain : chains) {
			if (chain.getStrength() > 0) evaluation += evaluateChain(chain, game, player);
		}
		return evaluation;
	}
	
	public static int evaluateChain(Chain chain, Game game, Player player) {
		if (chain.getColor() == player.getPlayerColor()) return chain.getStrength();
		else return -chain.getStrength();
	}
	
}
