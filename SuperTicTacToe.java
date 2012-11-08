package package1;

import javax.swing.*;

/**********************************************************************
SuperTicTacToe.java implements a main method to instantiate a Super Tic
Tac Toe Panel and run the game.

@author Joe Gibson
@version 10.10.12
***********************************************************************/
public class SuperTicTacToe {
	
	/** The main window frame */
	private static JFrame frame;

	/******************************************************************
	Main method prompts user for board size and start player input and
	creates the appropriate SuperTicTacToePanel.
	@param Array of arguments
	@return none
	******************************************************************/
	public static void main (String[] args)
	{  
		
		//Default to a board size of 3x3, starting with X
		int boardSize = 3;
		int startPlayer = 0;
		
		//Default to incorrect input
		boolean goodInput = false;
		
		//Use the OS X menu bar in place of the default Java menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		//Set the application name to Super Tic Tac Toe
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", 
				"Super Tic Tac Toe");
		
		//Continuously prompt the user for a board size until proper
		//input is given or user selects cancel
		do {
			String boardSizeString = JOptionPane.showInputDialog(null, 
					"Enter the board size (3-10):");
			
			goodInput = false;
			
			if(boardSizeString != null)
				try {
					boardSize = Integer.parseInt(boardSizeString);
					
					if(boardSize >= 3 && boardSize <= 10)
						goodInput = true;
					else
						goodInput = false;
					
				} catch(NumberFormatException nfe) {
					goodInput = false;
				}
			else
				//Exit on cancel
				System.exit(0);

		} while(!goodInput); //Repeat if bad input was given
		
		//Continuously prompt the user for a start player until proper
		//input is given or user selects cancel
		do {
			String startPlayerString = JOptionPane.showInputDialog(null, 
					"Starting Player (X or O):");
			
			goodInput = false;
			
			if(startPlayerString != null)
				try {
					if(startPlayerString.equals("X") || 
							startPlayerString.equals("x") || 
							startPlayerString.equals("0")) {
						startPlayer = 0;
						goodInput = true;
					}
					else if(startPlayerString.equals("O") || 
							startPlayerString.equals("o") || 
							startPlayerString.equals("1")) {
						startPlayer = 1;
						goodInput = true;
					}
					else {
						goodInput = false;
					}
				} catch(NumberFormatException nfe){
					goodInput = false;
				}
			else
				//Exit on cancel
				System.exit(0);
				
		} while(!goodInput); //Repeat if bad input

		//Create and initialize the menu system
		JMenuBar menuBar;
		JMenu fileMenu, helpMenu, scoreMenu;
		JMenuItem newMenuItem, resetMenuItem, undoMenuItem, saveMenuItem; 
		JMenuItem loadMenuItem, quitMenuItem, aboutMenuItem;
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		newMenuItem = new JMenuItem("New");
		resetMenuItem = new JMenuItem("Reset");
		undoMenuItem = new JMenuItem("Undo");
		saveMenuItem = new JMenuItem("Save");
		loadMenuItem = new JMenuItem("Load");
		quitMenuItem = new JMenuItem("Quit");
		helpMenu = new JMenu("Help");
		aboutMenuItem = new JMenuItem("About");
		scoreMenu = new JMenu("X: 0 -- O: 0");
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		menuBar.add(scoreMenu);
		fileMenu.add(newMenuItem);
		fileMenu.add(resetMenuItem);
		fileMenu.add(undoMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu.add(quitMenuItem);
		helpMenu.add(aboutMenuItem); 
		
		//Create the JFrame
		frame = new JFrame ("Super Tic Tac Toe");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		//Add the menu bar
		frame.setJMenuBar(menuBar);

		//Create a new SuperTicTacToePanel and pass it the user specified
		//board size, start player, and all menu items
		frame.getContentPane().add(new SuperTicTacToePanel(boardSize, 
				startPlayer, newMenuItem, resetMenuItem, undoMenuItem, 
				saveMenuItem, loadMenuItem, quitMenuItem, aboutMenuItem, 
				scoreMenu));

		//Pack the frame, make it visible, and make it unresizable
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	
	/******************************************************************
	Re-packs the frame so it can be re-sized appropriately.
	@param none
	@return none
	******************************************************************/
	public static void packFrame() {
		frame.pack();
	}
}
