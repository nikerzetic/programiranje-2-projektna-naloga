package Intelligence;

import java.util.LinkedList;
import java.util.List;

import logick.*;

public class Minimax {
	
	private static final int Win = (1 << 20);
	private static final int Lose = -Win;
	private static final int Draw = 0;
	
	
	public static List<EvaluatedMove> EvaluateMove (Game game, int depth, Player me) {
		List<EvaluatedMove> EvaluatedMoves = new LinkedList<EvaluatedMove> ();
		List<Move> PossibleMoves = game.possibleMoves();
		for (Move move: PossibleMoves) {
			Game temporaryGame = new Game();
			temporaryGame.play(move);
			int evaluation = minimaxPosition (temporaryGame, depth - 1, me);
			EvaluatedMoves.add(new EvaluatedMove(move, evaluation));
		}
		return EvaluatedMoves;
	}
	
	public static int minimaxPosition (Game game, int depth, Player me) {
		Status status = game.status();
		switch(status) {
		case BLACK_WIN: return (me == Player.BLACK ? Win: Lose);
		case WHITE_WIN: return (me == Player.WHITE ? Win: Lose);
		case DRAW: return Draw;
		default:
			if (depth == 0) {return evaluatePosition(game, me);}
			List<EvaluatedMove> EvaluatedMoves = EvaluateMove(game, depth, me);
			if (game.onMove == me) {return maxEvaluation(EvaluatedMoves);}
			else {return minEvaluation(EvaluatedMoves);}
		}	
	}
	
	public static int maxEvaluation (List<EvaluatedMove> EvaluatedMoves) {
		int max = Lose;
		for (EvaluatedMove evaluatedMove: EvaluatedMoves) {
			if(evaluatedMove.value > max) {max = evaluatedMove.value;}
		}
		return max;
	}
	
	public static Move maxMove(List<EvaluatedMove> EvaluatedMoves) {
		int max = Lose;
		Move move = null;
		for (EvaluatedMove evaluatedMove: EvaluatedMoves) {
			if (evaluatedMove.value >= max) {
				max = evaluatedMove.value;
				move = evaluatedMove.move;	
			}
		}
		return move;
	}
	
	public static int minEvaluation (List<EvaluatedMove> EvaluatedMoves) {
		int min = Win;
		for (EvaluatedMove evaluatedMove: EvaluatedMoves) {
			if(evaluatedMove.value < min) {min = evaluatedMove.value;}
		}
		return min;
	}
	
	public static int evaluatePosition (Game game, Player me) {
		int evaluation = 0;
		List<Chain> chains = game.getChains();
		for (Chain chain : chains) {
			evaluation = evaluation + evaluateChain(chain, game, me);
		}
		return evaluation;
	}
	
	public static int evaluateChain(Chain chain, Game game, Player me) {
		Intersection[][] grid = game.getGrid();
		int points = 0;
		int[] xs = chain.getXS();
		int[] ys = chain.getYS();
		for (int i = 0; i < 5; i++) {
			//ker imava list vrst vsebuje samo vrste s crnimi ali belimi kamencki
			if (grid[xs[i]][ys[i]] != Intersection.EMPTY) points++;
		}
		return points;
		
	}
	
}
