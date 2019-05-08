package gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import logick.Master;

@SuppressWarnings("serial")
public class PlayingCanvas extends JPanel {
	
	Master master;
	
	// tu manjkajo se kozmeticni popravki
	
	public PlayingCanvas(Master master) {
		
		super();
		this.master = master;
		setBackground(Color.WHITE);
				
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

}
