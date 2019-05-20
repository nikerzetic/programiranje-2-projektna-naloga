package logick;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import gui.PlayingCanvas;



// Player je razred, ki zna odigrati potezo. Lahko je ComputerPlayer ali HumanPlayer

public abstract class Player implements MouseListener {
	
	private StoneColor playerColor;
	
	public Player(StoneColor color) {
		
		playerColor = color;
		
	}
	
	public void setPlayerColor(StoneColor color) {
		playerColor = color;
	}
	
	public StoneColor getPlayerColor() {
		return playerColor;
	}
	
	public abstract Move playMove(PlayingCanvas canvas);

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
//	public Player opponent() {
//		if (this == BLACK) return BLACK; else return WHITE;
//	}
//	
//	public Intersection getIntersection() {
//		if (this == BLACK) return Intersection.BLACK; else return Intersection.WHITE;
//	}
	
}
