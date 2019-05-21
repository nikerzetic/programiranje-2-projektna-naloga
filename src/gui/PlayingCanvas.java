package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import logick.Game;
import logick.StoneColor;

@SuppressWarnings("serial")
public class PlayingCanvas extends JPanel implements MouseListener, MouseMotionListener {
	
	private MainWindow master; // tu izvira problem
	
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
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
				
	}
	
	private int[] closestIntersection(int x, int y) {
		
		int vicinity = 10;
		
		int N = Game.getSize() + 1;
		int height = this.getHeight();
		int width = this.getWidth();
		int size = Math.min(height, width) / N;
		int padX = (width - size * N) /2;
		int padY = (height - size * N) /2;
		
		int leftLineX = (x - padX) / size - 1;
		int rightLineX = leftLineX + 1;
		int topLineY = (y - padY) / size - 1;
		int bottomLineY = topLineY + 1;
		
		int leftX = leftLineX * size + padX;
		int rightX = rightLineX * size + padX;
		int topY = topLineY * size + padY;
		int bottomY = bottomLineY * size + padY;
		
		double r1 = Math.sqrt(Math.pow(x - leftX, 2) + Math.pow(y - topY, 2));
		double r2 = Math.sqrt(Math.pow(x - rightX, 2) + Math.pow(y - topY, 2));
		double r3 = Math.sqrt(Math.pow(x - leftX, 2) + Math.pow(y - bottomY, 2));
		double r4 = Math.sqrt(Math.pow(x - rightX, 2) + Math.pow(y - bottomY, 2));
		
		if (r1 < vicinity) return new int[] {leftLineX, topLineY}; 
		else if (r2 < vicinity) return new int[] {rightLineX, topLineY};
		else if (r3 < vicinity) return new int[] {leftLineX, bottomLineY};
		else if (r4 < vicinity) return new int[] {rightLineX, bottomLineY};
		
		return null;
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
		int padX = (width - size * N) /2;
		int padY = (height - size * N) /2;

		g.translate(padX, padY);

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

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int intersection[] = closestIntersection(x, y);
		System.out.println(master.getGame()); // game je null -> null pointer exception
		if (intersection != null) master.click(intersection[0], intersection[1]);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {} // Presecisce se obarva rdece, ko mu priblizamo misko

	// Neuporabne metode
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

}