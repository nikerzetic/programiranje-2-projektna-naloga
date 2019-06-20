package intelligence;

import javax.swing.SwingWorker;

import logick.Move;
import logick.Player;

@SuppressWarnings("rawtypes")
public class MoveFinder extends SwingWorker {
	
	private Player player;
	private Move toPlay;
	
	public MoveFinder(Player player) {
		this.player = player;
	}

	@Override
	protected Move doInBackground() throws Exception {
		Move toPlay = AlphaBeta.optimalMove(this.player.getMaster().getGame(), this.player);
		this.toPlay = toPlay;
		return toPlay;
	}
	
	@Override
	public void done() {
		this.player.getMaster().getGame().play(this.toPlay);
		this.player.getMaster().getGame().getOnMove().playYourMove();
	}

}
