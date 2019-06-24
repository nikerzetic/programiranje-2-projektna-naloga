package intelligence;

import javax.swing.SwingWorker;
import logick.Move;
import logick.Player;

// SwingWorker za iskanje racunalnikove poteze v drugem threadu.

@SuppressWarnings("rawtypes")
public class MoveFinder extends SwingWorker {
	
	private Player player;
	private Move toPlay;
	
	// Konstruktor.
	public MoveFinder(Player player) {
		this.player = player;
	}

	// Proces v ozadju.
	@Override
	protected Move doInBackground() throws Exception {
		Move toPlay = AlphaBeta.optimalMove(this.player.getMaster().getGame(), this.player);
		this.toPlay = toPlay;
		return toPlay;
	}
	
	// Metoda, ki se izvede, ko MoveFinder preneha z racunanjem.
	@Override
	public void done() {
		if (this.toPlay != null) {
			this.player.getMaster().getGame().play(this.toPlay);
			this.player.getMaster().getGame().getOnMove().playYourMove();
		}
	}

}
