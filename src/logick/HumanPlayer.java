package logick;

import java.awt.event.MouseEvent;
import gui.PlayingCanvas;

public class HumanPlayer extends Player {

	Move move = null;
	
	public HumanPlayer(StoneColor color) {
		
		super(color);
		
	}

	@Override
	public Move playMove(PlayingCanvas canvas) {
		
		canvas.addMouseListener(this);

		while (move != null) {
			Thread.yield();
		}
		canvas.removeMouseListener(this);
		return move;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		move = new Move(e.getX(), e.getY());
	}
	
}
