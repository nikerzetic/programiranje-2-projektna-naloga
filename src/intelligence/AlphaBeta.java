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
	
	private static final int DEPTH = 8;
	
	public static Move optimalMove(Game game, Player me) {
		return null;
	}
	
	public static EvaluatedMove alhpaBetaMove(Game game, int depth, int alpha, int beta, Player me) {
		int evaluation;
		if (game.getOnMove() == me) evaluation = WIN; else evaluation = LOSE;
		List<Move> possibleMoves = game.possibleMoves();
		Move optimalMove = possibleMoves.get(0);
		for (Move move : possibleMoves) {
			Game temporaryGame = new Game(game);
			temporaryGame.play(move);
			int temporaryEvaluation = alphaBetaPosition(temporaryGame, depth-1, alpha, beta, me);
		}
		return null;
	}
	
	public static int alphaBetaPosition(Game game, int depth, int alpha, int beta, Player me) {
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
		
		
	}
	
	public static int evaluatePosition(Game game, Player me) {
		int evaluation = 0;
		List<Chain> chains = game.getChains();
		for (Chain chain : chains) {
			if (chain.getStrength() > 0) evaluation += evaluateChain(chain, game, me);
		}
		return evaluation;
	}
	
	public static int evaluateChain(Chain chain, Game game, Player me) {
		if (chain.getColor() == me.getPlayerColor()) return (int) Math.pow(2, chain.getStrength());
		else return - (int) Math.pow(2, chain.getStrength());
	}
	
	// profesorjeva koda za zgled
	
	public static Poteza alphabetaVrzi (Igra igra, Igralec jaz) {
		// Na zaÄetku alpha = ZGUBA in beta = ZMAGA
		return alphabetaPoteze(igra, GLOBINA, ZGUBA, ZMAGA, jaz).poteza;
	}
	
	public static EvaluatedMove alphaBetaMove(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Äe sem raÄunalnik, maksimiramo oceno z zaÄetno oceno ZGUBA
		// Äe sem pa Älovek, minimiziramo oceno z zaÄetno oceno ZMAGA
		if (igra.naPotezi == jaz) {ocena = ZGUBA;} else {ocena = ZMAGA;}
		List<Poteza> moznePoteze = igra.poteze();
		Poteza kandidat = moznePoteze.get(0); // MoĹžno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		for (Poteza p: moznePoteze) {
			Igra tempIgra = new Igra(igra);
			tempIgra.odigraj (p);
			int ocenap = alphabetaPozicijo (tempIgra, globina-1, alpha, beta, jaz);
			if (igra.naPotezi == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // Za alphabeta mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // Za alphabeta mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}	
			if (alpha >= beta) {return new OcenjenaPoteza (kandidat, ocena);} // Izstopimo iz "for loop", ker ostale poteze ne pomagajo
		}
		return new OcenjenaPoteza (kandidat, ocena);
	}

	public static int alphabetaPozicijo(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		Stanje stanje = igra.stanje();
		switch (stanje) {
		case ZMAGA_O: return (jaz == Igralec.O ? ZMAGA : ZGUBA);
		case ZMAGA_X: return (jaz == Igralec.X ? ZMAGA : ZGUBA);
		case NEODLOCENO: return (NEODLOC);
		default:
		// Nekdo je na potezi
		if (globina == 0) {return oceniPozicijo(igra, jaz);}
		// globina > 0
	    OcenjenaPoteza ocenjenaPoteza = alphabetaPoteze (igra, globina, alpha, beta, jaz);
		return ocenjenaPoteza.vrednost;
		}
	}

	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		for (Vrsta v : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		return ocena;	
	}
	
	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_X = 0;
		int count_O = 0;
		for (int k = 0; k < Igra.N && (count_X == 0 || count_O == 0); k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case O: count_O = 2*count_O + 1; break;
			case X: count_X = 2*count_X + 1; break;
			case PRAZNO: break;
			}
		}
		if (count_O > 0 && count_X > 0) { return 0; }
		else if (jaz == Igralec.O) { return count_O - count_X; }
		else { return count_X - count_O; }
	}
		
}
