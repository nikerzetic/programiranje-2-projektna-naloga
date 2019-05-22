package intelligence;

import java.util.LinkedList;
import java.util.List;

import logick.*;

public class Minimax {
	
	private static final int Win = (1 << 20);
	private static final int Lose = -Win;
	private static final int Draw = 0;
	
	
	public static List<EvaluatedMove> EvaluateMove (Game game, int depth, Player me) {
		List<EvaluatedMove> evaluatedMoves = new LinkedList<EvaluatedMove> ();
		List<Move> PossibleMoves = game.possibleMoves();
		for (Move move: PossibleMoves) {
			Game temporaryGame = new Game();
			temporaryGame.play(move);
			int evaluation = minimaxPosition (temporaryGame, depth - 1, me);
			evaluatedMoves.add(new EvaluatedMove(move, evaluation));
		}
		return evaluatedMoves;
	}
	
	public static int minimaxPosition (Game game, int depth, Player me) {
		Status status = game.status();
		switch(status) {
		case BLACK_WIN: return (me.getPlayerColor() == StoneColor.BLACK ? Win: Lose);
		case WHITE_WIN: return (me.getPlayerColor() == StoneColor.WHITE ? Win: Lose);
		case DRAW: return Draw;
		default:
			if (depth == 0) {return evaluatePosition(game, me);}
			List<EvaluatedMove> evaluatedMoves = EvaluateMove(game, depth, me);
			if (game.getOnMove() == me) {return maxEvaluation(evaluatedMoves);}
			else {return minEvaluation(evaluatedMoves);}
		}	
	}
	
	public static int maxEvaluation (List<EvaluatedMove> evaluatedMoves) {
		int max = Lose;
		for (EvaluatedMove evaluatedMove: evaluatedMoves) {
			if(evaluatedMove.value > max) {max = evaluatedMove.value;}
		}
		return max;
	}
	
	public static Move maxMove(List<EvaluatedMove> evaluatedMoves) {
		int max = Lose;
		Move move = null;
		for (EvaluatedMove evaluatedMove: evaluatedMoves) {
			if (evaluatedMove.value >= max) {
				max = evaluatedMove.value;
				move = evaluatedMove.move;	
			}
		}
		return move;
	}
	
	public static int minEvaluation (List<EvaluatedMove> evaluatedMoves) {
		int min = Win;
		for (EvaluatedMove evaluatedMove: evaluatedMoves) {
			if(evaluatedMove.value < min) {min = evaluatedMove.value;}
		}
		return min;
	}
	
	public static int evaluatePosition (Game game, Player me) {
		int evaluation = 0;
		List<Chain> chains = Game.getChains();
		for (Chain chain : chains) {
			evaluation = evaluation + evaluateChain(chain, game, me);
		}
		return evaluation;
	}
	
	public static int evaluateChain(Chain chain, Game game, Player me) {
		StoneColor[][] grid = game.getGrid();
		int points = 0;
		int[] xs = chain.getXS();
		int[] ys = chain.getYS();
		for (int i = 0; i < 5; i++) {
			//ker imava list vrst vsebuje samo vrste s crnimi ali belimi kamencki
			if (grid[xs[i]][ys[i]] != StoneColor.EMPTY) points++;
		}
		return points;
		
	}
	
}
