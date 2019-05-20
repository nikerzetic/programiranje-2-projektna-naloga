package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import logick.Game;
import logick.StoneColor;

@SuppressWarnings("serial")
public class PlayingCanvas extends JPanel {
	
	private MainWindow master;
	
	Color lineColor = Color.DARK_GRAY;
	Color backgroundColor = Color.LIGHT_GRAY;
	Color edgeColor = Color.BLACK;
	Color blackColor = Color.BLACK;
	Color whiteColor = Color.WHITE;
	float edgeWidth = 2;
	int stoneRadius = 5;
	float lineWidth = 1;
	
	public PlayingCanvas(MainWindow master) {
		
		super();
		setBackground(Color.WHITE);
				
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
		
		setBackground(backgroundColor);

		int N = Game.getSize() + 1;
		int height = getHeight();
		int width = getWidth();
		int size = Math.min(height, width) / N;
		int pad_x = (width - size * N) /2;
		int pad_y = (height - size * N) /2;

		g.translate(pad_x, pad_y);

		g.setColor(edgeColor);
		g2.setStroke(new BasicStroke(edgeWidth));
		g.drawLine(0, 0, N * size, 0);
		g.drawLine(0, N * size, N * size, N * size);
		g.drawLine(0, 0, 0, N * size);
		g.drawLine(N * size, 0, N * size, N * size);


		g.setColor(lineColor);
		g2.setStroke(new BasicStroke(lineWidth));
		for (int i = 01; i <= 15; i++) {
			g.drawLine(i * size, 0, i * size, N * size);
			g.drawLine(0, i * size, N * size, i * size);
		}
		
		if (master != null) {
			StoneColor[][] grid = master.getGame().getGrid();

			for (int i = 0; i < N - 1; i++) {
				for (int j = 0; j < N - 1; j++) {
					StoneColor stone = grid[i][j];
					if (stone == StoneColor.EMPTY) {
						continue;
					}
					else if (stone == StoneColor.BLACK) {
						g.setColor(blackColor);
					}
					else if (stone == StoneColor.WHITE) {
						g.setColor(whiteColor);
					}
					g.fillOval((i + 1) * size - stoneRadius, (j + 1) * size - stoneRadius, 2 * stoneRadius, 2 * stoneRadius);
				}
			}
		}

	}

}
