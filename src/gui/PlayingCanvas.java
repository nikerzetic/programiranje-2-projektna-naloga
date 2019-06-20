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
	// Igralno polje, na katerem se poteka igra.
	
	private MainWindow master;
	
	// Barva crt.
	Color lineColor = Color.DARK_GRAY;
	
	// Barva ozadja.
	Color backgroundColor = new Color(85, 91, 105);
	
	// Barva igralnega polja.
	Color boardColor = new Color(240, 218, 127);
	
	// Barva robov okna.
	Color edgeColor = Color.BLACK;
	
	// Barvi kamenckov.
	Color blackColor = Color.BLACK;
	Color whiteColor = Color.WHITE;
	
	// Sirina robov.
	float edgeWidth = 2;
	
	// Sirina crt.
	float lineWidth = 1;
	
	// Konstruktor
	public PlayingCanvas(MainWindow master) {
		
		super();
		this.master = master;
		setBackground(Color.WHITE);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
				
	}
	
	// Metoda, ki vrne najblizje presecisce tocke (x, y).
	private int[] closestIntersection(int x, int y) {
		
		// Stevilo kvadratkov v eni vrstici.
		int N = Game.getSize() + 1;
		
		// Visina okna.
		int height = this.getHeight();
		
		// Sirina okna.
		int width = this.getWidth();
		
		// sirina in visina enega kvadratka.
		int size = Math.min(height, width) / N;
		
		int padX = (width - size * N) /2;
		int padY = (height - size * N) /2;
		
		// Stevilka, ki doloci kako blizu lahko kliknes, da se doloci najblizje presecisce.
		int vicinity = size / 2;
		
		// Leva, desna, zgornja in spodnja crta, ki so najblizje (x, y).
		int leftLineX = (x - padX) / size - 1;
		int rightLineX = leftLineX + 1;
		int topLineY = (y - padY) / size - 1;
		int bottomLineY = topLineY + 1;
		
		int leftX = (leftLineX + 1) * size + padX;
		int rightX = (rightLineX + 1) * size + padX;
		int topY = (topLineY + 1) * size + padY;
		int bottomY = (bottomLineY + 1) * size + padY;
		
		// Izracun razdalj od tocke (x, y), do vseh presecisc ki jih doloca kvadratek, v katerem je tocka (x, y).
		double r1 = Math.sqrt(Math.pow(x - leftX, 2) + Math.pow(y - topY, 2));
		double r2 = Math.sqrt(Math.pow(x - rightX, 2) + Math.pow(y - topY, 2));
		double r3 = Math.sqrt(Math.pow(x - leftX, 2) + Math.pow(y - bottomY, 2));
		double r4 = Math.sqrt(Math.pow(x - rightX, 2) + Math.pow(y - bottomY, 2));
		
		// Dolocimo najblizje presecisce tocke (x, y).
		if (r1 < vicinity) return new int[] {leftLineX, topLineY}; 
		else if (r2 < vicinity) return new int[] {rightLineX, topLineY};
		else if (r3 < vicinity) return new int[] {leftLineX, bottomLineY};
		else if (r4 < vicinity) return new int[] {rightLineX, bottomLineY};
		
		return null;
	}
	
	// Zacetna velikost okna.
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	// Metoda, ki izrise platno.
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
		
		// Nastavitev barve ozadja.
		setBackground(backgroundColor);
		
		int N = Game.getSize() + 1;
		int height = getHeight();
		int width = getWidth();
		int size = Math.min(height, width) / N;
		int padX = (width - size * N) /2;
		int padY = (height - size * N) /2;
		
		// Radij kamenckov.
		int stoneRadius = size / 3;

		g.translate(padX, padY);
		
		// Nastavitev barve igralnega polja.
		g.setColor(boardColor);
		
		// Izris polja, ki je v obliki kvadrata.
		g.fillRect(0, 0, N * size, N * size);
		
		// Nastavitev barve in izris robov.
		g.setColor(edgeColor);
		g2.setStroke(new BasicStroke(edgeWidth));
		g.drawLine(0, 0, N * size, 0);
		g.drawLine(0, N * size, N * size, N * size);
		g.drawLine(0, 0, 0, N * size);
		g.drawLine(N * size, 0, N * size, N * size);

		// Nastavitev barve in izris crt na igralnem polju.
		g.setColor(lineColor);
		g2.setStroke(new BasicStroke(lineWidth));
		for (int i = 01; i <= 15; i++) {
			g.drawLine(i * size, 0, i * size, N * size);
			g.drawLine(0, i * size, N * size, i * size);
		}
		
		// Izris kamenckov ob kliku.
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
	
	// Metoda, ki si ob kliku zapomni najblitje presecisce.
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int intersection[] = closestIntersection(x, y);
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
