package package1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

/**********************************************************************
SuperTicTacToePanel.java implements a GUI panel for the Super Tic Tac
Toe game, complete with an array of buttons and multiple menu items.

@author Joe Gibson
@version 10.10.12
 ***********************************************************************/
@SuppressWarnings("serial")
public class SuperTicTacToePanel extends JPanel{
	
	/** Button grid */
	private JButton[][] board;
	
	/** Cell grid */
	private Cell[][] cellBoard;
	
	/** Undo move list */
	private ArrayList<Point> moveList;
	
	/** Player X icon */
	private ImageIcon xIcon;
	
	/** Player O icon */
	private ImageIcon oIcon;
	
	/** Empty cell icon */
	private ImageIcon emptyIcon;
	
	/** Number of rows */
	private int ROWS;
	
	/** Number of columns */
	private int COLS;
	
	/** Number of moves completed */
	private int moves;
	
	/** Either 128 or 64 bit image size */
	private int imageSize;
	
	/** Number of X wins */
	private int xWins;
	
	/** Number of O wins */
	private int oWins;
	
	/** Super Tic Tac Toe Game */
	private SuperTicTacToeGame game;
	
	/** Score menu */
	private JMenu scoreMenu;
	
	/** New menu item */
	private JMenuItem newMenuItem;
	
	/** Reset menu item */
	private JMenuItem resetMenuItem;
	
	/** Undo menu item */
	private JMenuItem undoMenuItem; 
	
	/** Save menu item */
	private JMenuItem saveMenuItem;
	
	/** Load menu item */
	private JMenuItem loadMenuItem;
	
	/** Quit menu item */
	private JMenuItem quitMenuItem;
	
	/** About menu item */
	private JMenuItem aboutMenuItem;
	
	/******************************************************************
	Constructor creates a Super Tic Tac Toe game panel given a board 
	size and a starting player. Initializes all components and game
	variables.
	@param The size of the game board
	@param The player who starts the game (X = 0, O = 1)
	******************************************************************/
	public SuperTicTacToePanel(int boardSize, int startPlayer, 
			JMenuItem newMenuItem, JMenuItem resetMenuItem, 
			JMenuItem undoMenuItem, JMenuItem saveMenuItem, 
			JMenuItem loadMenuItem, JMenuItem quitMenuItem, 
			JMenuItem aboutMenuItem, JMenu scoreMenu) {

		//Create the move list and clear it
		moveList = new ArrayList<Point>();
		moveList.clear();
		moves = 0;
		
		//Initialize the X and O wins to 0
		xWins = 0;
		oWins = 0;
		
		//Default to 128 bit and change to 64 if 6x6 or bigger
		imageSize = 128;
		if(boardSize > 6)
			imageSize = 64;
		
		//Get number of rows and columns from game
		ROWS = boardSize;
		COLS = boardSize;
		
		//Create a button listener
		ButtonListener listener = new ButtonListener();
		
		//Initialize the menus
		this.newMenuItem = newMenuItem;
		this.resetMenuItem = resetMenuItem;
		this.undoMenuItem = undoMenuItem;
		this.saveMenuItem = saveMenuItem;
		this.loadMenuItem = loadMenuItem;
		this.quitMenuItem = quitMenuItem;
		this.aboutMenuItem = aboutMenuItem;
		this.scoreMenu = scoreMenu;
		
		//Create and assign shortcuts to menus
		KeyStroke newShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke resetShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_R,
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke undoShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_Z, 
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke saveShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_S, 
				KeyEvent.CTRL_DOWN_MASK);
		KeyStroke loadShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_L, 
				KeyEvent.CTRL_DOWN_MASK);
		newMenuItem.setAccelerator(newShortcut);
		resetMenuItem.setAccelerator(resetShortcut);
		undoMenuItem.setAccelerator(undoShortcut);
		saveMenuItem.setAccelerator(saveShortcut);
		loadMenuItem.setAccelerator(loadShortcut);
		
		//Add listener to menu items
		newMenuItem.addActionListener(listener);
		resetMenuItem.addActionListener(listener);
		undoMenuItem.addActionListener(listener);
		saveMenuItem.addActionListener(listener);
		loadMenuItem.addActionListener(listener);
		quitMenuItem.addActionListener(listener);
		aboutMenuItem.addActionListener(listener);
		
		//Use a grid layout for the panel
		setLayout(new GridLayout(ROWS,COLS));
		
		//Initialze the button graphics
		xIcon = new ImageIcon("TicTacToeX" + imageSize + ".png");
		oIcon = new ImageIcon("TicTacToeO" + imageSize + ".png");
		emptyIcon = new ImageIcon("TicTacToeEmpty" + imageSize + 
				".png");
		
		//Create an appropriately sized board
		board = new JButton[ROWS][COLS];
		
		//Initialze each button, add the listener, and add it to panel
		for(int row = 0; row < ROWS; row++)
			for(int col = 0; col < COLS; col++) {
				board[row][col] = new JButton("", emptyIcon);
				board[row][col].addActionListener(listener);
				board[row][col].setBackground(Color.darkGray);
				board[row][col].setForeground(Color.darkGray);
				board[row][col].setPreferredSize(new 
						Dimension(imageSize, imageSize));
				add(board[row][col]);
			}
		
		//Configure the panel color and size
		setBackground (Color.darkGray);
		setPreferredSize(new Dimension((imageSize * boardSize), 
				(imageSize * boardSize)));
		
		//Create the game
		game = new SuperTicTacToeGame(boardSize, startPlayer);
	}
	
	
	/******************************************************************
	Displays the board by checking to see which button is occupied with
	the corresponding player.
	@param none
	@return none
	******************************************************************/
	private void displayBoard() {
		
		//Retrieve the board from the game
		cellBoard = game.getBoard();
		
		//Change the icon to correspond with the cell value
		for(int row = 0; row < ROWS; row++)
			for(int col = 0; col < COLS; col++) {
				if(cellBoard[row][col] == Cell.O)
					board[row][col].setIcon(oIcon);
				
				if(cellBoard[row][col] == Cell.X)
					board[row][col].setIcon(xIcon);
				
				if(cellBoard[row][col] == Cell.EMPTY)
					board[row][col].setIcon(emptyIcon);
			}
	}
	
	
	/******************************************************************
	Updates the score bar with the current score.
	@param none
	@return none
	******************************************************************/
	private void displayScoreBar() {
		
		//Update the menu bar text
		scoreMenu.setText("X: " + xWins + " -- O: " + oWins);
	}

	private class ButtonListener implements ActionListener {
		
		/******************************************************************
		Determines and acts upon a button press or menu selection.
		@param Which component was activated
		@return none
		******************************************************************/
		public void actionPerformed(ActionEvent event) {
			
			//Check all buttons
			for(int row = 0; row < ROWS; row++)
				for(int col = 0; col < COLS; col++) 
					if(event.getSource() == board[row][col])
					{
						//Select the appropriate cell
						game.select(row,col);
						
						//Add the selected cell to the move list
						moveList.add(new Point(row, col));
						moves++;

						//Display the board
						displayBoard();
						
						//Check for an X win, O win, cat's game, or
						//just keep playing
						if(game.getGameStatus() == GameStatus.O_WON) {
							JOptionPane.showMessageDialog(null, 
									"O won and X lost!");
							oWins++;
							displayScoreBar();
							game.reset();
							moves = 0;
							moveList.clear();
							displayBoard();
						}

						if(game.getGameStatus() == GameStatus.X_WON) {
							JOptionPane.showMessageDialog(null, 
									"X won and O lost!");
							xWins++;
							displayScoreBar();
							game.reset();
							moves = 0;
							moveList.clear();
							displayBoard();
						}

						
						if(game.getGameStatus() == GameStatus.CATS) {
							JOptionPane.showMessageDialog(null, 
									"Cat's Game!");
							displayScoreBar();
							game.reset();
							moves = 0;
							moveList.clear();
							displayBoard();
						}
						
					}
			
			if(event.getSource() == resetMenuItem) {
				
				//Reset the game and re-display the new board
				game.reset();
				moves = 0;
				moveList.clear();
				displayBoard();
			}
			
			if(event.getSource() == undoMenuItem) {
				
				//Unselect the last move until there are no more moves
				if(moves > 0) {
					game.unselect(moveList.get(moves - 1));
					moveList.remove(moves - 1);
					moves--;
				}
				
				//Re-display the board
				displayBoard();
			}
			
			if(event.getSource() == saveMenuItem)
				
				//Save the game
				game.save(moveList);

			if(event.getSource() == loadMenuItem) {
				
				//Reset the current game
				game.reset();
				moves = 0;
				moveList.clear();
				
				//Create a temporary moveList
				ArrayList<Point> tempMoveList = new ArrayList<Point>();
				
				//Call the load(true) method to select the file to load
				tempMoveList = game.load(true);
			
				//Set the number of moves to the size of the move list
				moves = tempMoveList.size();

				//Parse the board size and player number from the move
				//list's first point values
				int boardSize = tempMoveList.get(moves - 1).x;
				int player = tempMoveList.get(moves - 1).y;
				
				//Remove the erroneous extra point
				tempMoveList.remove(moves - 1);
				
				//Set the new board dimensions
				ROWS = boardSize;
				COLS = boardSize;
				
				//Remove all components from the JPanel
				removeAll();
				
				//Reset the grid layout
				setLayout(new GridLayout(ROWS, COLS));
				
				//Re-create the button listener
				ButtonListener listener = new ButtonListener();
				
				//Default to 128 bit image but shrink if necessary
				imageSize = 128;
				if(boardSize > 6)
					imageSize = 64;
				
				//Set the appropriately sized image icons
				xIcon = new ImageIcon("TicTacToeX" + imageSize + ".png");
				oIcon = new ImageIcon("TicTacToeO" + imageSize + ".png");
				emptyIcon = new ImageIcon("TicTacToeEmpty" + 
				imageSize + ".png");
				
				//Create the new board
				board = new JButton[ROWS][COLS];
				
				//Create button grid, add listeners, and add them to panel
				for (int row = 0; row < ROWS; row++ )
					for (int col = 0; col < COLS; col++) {
						board[row][col] = new JButton("", emptyIcon);
						board[row][col].addActionListener(listener);
						board[row][col].setBackground(Color.darkGray);
						board[row][col].setForeground(Color.darkGray);
						board[row][col].setPreferredSize(new 
								Dimension(imageSize, imageSize));
						add(board[row][col]);
					}
				
				//Initialize a new game
				game = new SuperTicTacToeGame(boardSize, player);
				
				//Call load(false) to select the appropriate cells
				tempMoveList = game.load(false);
				
				//Copy all elements of the tempMoveList to the moveList
				for(int i = 0; i < tempMoveList.size(); i++)
					moveList.add(tempMoveList.get(i));
				
				//Re-calculate the number of moves
				moves = moveList.size();
				
				//Configure and re-size the JPanel
				setBackground (Color.darkGray);
				setPreferredSize(new Dimension((imageSize * boardSize), 
						(imageSize * boardSize)));
				
				//Re-validate the panel
				revalidate();
				
				//Re-paint the panel
				repaint();
				
				//Re-pack the frame
				SuperTicTacToe.packFrame();
				
				//Re-display the board
				displayBoard();
			}
			
			if(event.getSource() == quitMenuItem) {
				System.exit(0);
			}
			
			if(event.getSource() == newMenuItem) {
				String[] args = {""};
				SuperTicTacToe.main(args);
			}

			if(event.getSource() == aboutMenuItem)
				JOptionPane.showMessageDialog(null, 
						"Super Tic Tac Toe v1.0 \n Designed by Joe Gibson");
		}
	}

}
