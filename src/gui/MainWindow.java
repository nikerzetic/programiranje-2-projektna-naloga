package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logick.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener{
	
	private Master master;

	private PlayingCanvas canvas;
	private JMenuItem gameComputerHuman;
	private JMenuItem gameHumanComputer;
	private JMenuItem gameComputerComputer;
	private JMenuItem gameHumanHuman;
	
	public MainWindow() {
		
		setTitle("Gomoku");

		master = new Master(this);
		canvas = new PlayingCanvas(master);
		
		// glavni meni
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		JMenu gameMenu = new JMenu("Nova igra");
		mainMenu.add(gameMenu);
		
		gameComputerHuman = new JMenuItem("Računalnik proti človeku");
		gameMenu.add(gameComputerHuman);
		gameComputerHuman.addActionListener(this);
		
		gameHumanComputer = new JMenuItem("Človek proti računalniku");
		gameMenu.add(gameHumanComputer);
		gameHumanComputer.addActionListener(this);
		
		gameComputerComputer = new JMenuItem("Računalnik proti računalniku");
		gameMenu.add(gameComputerComputer);
		gameComputerComputer.addActionListener(this);

		gameHumanHuman = new JMenuItem("Človek proti človeku");
		gameMenu.add(gameHumanHuman);
		gameHumanHuman.addActionListener(this);
		
		add(canvas);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();
		if (source == gameComputerHuman) {
			master.newGame(new ComputerPlayer(StoneColor.WHITE), new HumanPlayer(StoneColor.BLACK));
		}
		
		if (source == gameHumanComputer) {
			master.newGame(new HumanPlayer(StoneColor.WHITE), new ComputerPlayer(StoneColor.BLACK));
		}
		
		if (source == gameComputerComputer) {
			master.newGame(new ComputerPlayer(StoneColor.WHITE), new ComputerPlayer(StoneColor.BLACK));
		}
		
		if (source == gameHumanHuman) {
			master.newGame(new HumanPlayer(StoneColor.WHITE), new HumanPlayer(StoneColor.BLACK));
		}
		
	}

	public PlayingCanvas getCanvas() {
		return canvas;
	}
	
}
