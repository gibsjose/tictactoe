package package1;

import java.awt.Point;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**********************************************************************
SuperTicTacToeGame.java implements a "super" tic tac toe game, in that
the user has the ability to expand the game board from 3x3 to 10x10,
chose which player starts the game, undo previous moves, and load and
save their game

@author Joe Gibson
@version 10.10.12
 ***********************************************************************/
public class SuperTicTacToeGame {

	/** Game board of enumerated type Cell */
	private Cell[][] board;

	/** Number of rows in the board */
	private final int ROWS;

	/** Number of columns in the board */
	private final int COLS; 

	/** Starting player */
	private final int START_PLAYER;

	/** Current player */
	private int player;

	/** Score count */
	private int fullCount;

	/** Last used file directory for load and save methods */
	private static String lastDirectory = "~/";

	/******************************************************************
	Constructor creates a Super Tic Tac Toe game given a board size and
	a starting player. Initializes all game variables and sets each 
	board space to be empty.
	@param The size of the game board
	@param The player who starts the game (X = 0, O = 1)
	 ******************************************************************/
	public SuperTicTacToeGame(int boardSize, int startPlayer) {

		//Set the rows and columns from SuperTicTacToePanel
		ROWS = boardSize;
		COLS = boardSize;

		//Set the starting player from SuperTicTacToePanel
		START_PLAYER = startPlayer;

		//Clear the cat's game counter
		fullCount = 0;

		//Initialize the board
		board = new Cell[ROWS][COLS];

		//Set each cell to be empty
		for(int row = 0; row < ROWS; row++)
			for(int col = 0; col < COLS; col++) {
				board[row][col] = Cell.EMPTY;
			}

		//Player = 0 is 'X', player = 1 is 'O'
		player = START_PLAYER;
	}


	/******************************************************************
	Selects a cell in the board using the given coordinates  (row, col)
	Places the appropriate cell type in the cell (X, O, EMPTY)
	@param Selected row
	@param Selected column
	@return none
	 ******************************************************************/
	public void select(int row, int col) {
		if(player == 0)
			if(board[row][col] == Cell.EMPTY) {

				//If it's player 0's turn, set the cell to X
				board[row][col] = Cell.X;

				//Switch players
				player = 1;
			}

		if(player == 1)
			if(board[row][col] == Cell.EMPTY) {

				//If it's player 1's turn, set the cell to O
				board[row][col] = Cell.O;

				//Switch players
				player = 0;
			}
	}


	/******************************************************************
	Unselects a cell given the cell's Point coordinates. Sets the cell
	type to EMPTY and then returns the player back to the previous
	player so they are allowed to move again.
	@param Coordinates of the cell to clear
	@return none
	 ******************************************************************/
	public void unselect(Point p) {
		if (board[p.x][p.y] != Cell.EMPTY)

			//Set the cell to be empty
			board[p.x][p.y] = Cell.EMPTY;

		//Return player back to previous player so they can move again
		player = (player + 1) % 2;
	}


	/******************************************************************
	Resets the state of the game.  Initializes an empty board and
	configures the current player as the starting player.
	@param none
	@return none
	 ******************************************************************/
	public void reset() {

		//Reset the board
		board = new Cell[ROWS][COLS];

		//Reset the cat's game counter
		fullCount = 0;

		//Set each cell to be empty
		for(int row = 0; row < ROWS; row++)
			for(int col = 0; col < COLS; col++) {
				board[row][col] = Cell.EMPTY;
			}

		//Return the player to the starting player
		player = START_PLAYER;
	}


	/******************************************************************
	Determines the status of a game (if someone has won, cat's game, or
	if the game is still in progress). To see if someone has won, it
	checks both horizontal and vertical patterns as well as north east
	and south east diagonal patterns.  To check for cat's game it sees
	if all cells are occupied with either an 'X' or 'O'.
	@param none
	@return Status of the game
	 ******************************************************************/
	public GameStatus getGameStatus() {
		//Check Horizontal (3 Columns in a row)
		for(int row = 0; row < ROWS; row++) {
			for (int col = 0; col < (COLS - 2); col++) {
				if(board[row][col] == Cell.X)
					if(board[row][col + 1] == Cell.X)
						if(board[row][col + 2] == Cell.X)
							return GameStatus.X_WON;

				if(board[row][col] == Cell.O)
					if(board[row][col + 1] == Cell.O)
						if(board[row][col + 2] == Cell.O)
							return GameStatus.O_WON;
			}
		}

		//Check Vertical (3 Rows in a row)
		for(int col = 0; col < COLS; col++) {
			for (int row = 0; row < (ROWS - 2); row++) {
				if(board[row][col] == Cell.X)
					if(board[row + 1][col] == Cell.X)
						if(board[row + 2][col] == Cell.X)
							return GameStatus.X_WON;

				if(board[row][col] == Cell.O)
					if(board[row + 1][col] == Cell.O)
						if(board[row + 2][col] == Cell.O)
							return GameStatus.O_WON;
			}
		}

		//Check North East Diagonal
		for(int row = 2; row < ROWS; row++) {
			for (int col = 0; col < (COLS - 2); col++) {
				if(board[row][col] == Cell.X)
					if(board[row - 1][col + 1] == Cell.X)
						if(board[row - 2][col + 2] == Cell.X)
							return GameStatus.X_WON;

				if(board[row][col] == Cell.O)
					if(board[row - 1][col + 1] == Cell.O)
						if(board[row - 2][col + 2] == Cell.O)
							return GameStatus.O_WON;
			}
		}

		//Check South East Diagonal
		for(int row = 0; row < (ROWS - 2); row++) {
			for (int col = 0; col < (COLS - 2); col++) {
				if(board[row][col] == Cell.X)
					if(board[row + 1][col + 1] == Cell.X)
						if(board[row + 2][col + 2] == Cell.X)
							return GameStatus.X_WON;

				if(board[row][col] == Cell.O)
					if(board[row + 1][col + 1] == Cell.O)
						if(board[row + 2][col + 2] == Cell.O)
							return GameStatus.O_WON;
			}
		}

		//Check to see how many cells are X's and O's
		for(int row = 0; row < ROWS; row++)
			for(int col = 0; col < COLS; col++) {
				if(board[row][col] == Cell.X || 
						board[row][col] == Cell.O)
					fullCount++;
			}

		//Declare cat's game if all cells are X's and O's
		if(fullCount >= (ROWS * COLS)) {
			return GameStatus.CATS;
		}

		//Otherwise the game is still in progress
		else {
			fullCount = 0;
			return GameStatus.IN_PROGRESS;
		}
	}


	/******************************************************************
	Returns the game board
	@param none
	@return Game board
	 ******************************************************************/
	public Cell[][] getBoard() {
		return board;
	}

	/******************************************************************
	Saves the state of the game to a text file by saving first the
	board size, then the player, then the board, and finally the 'undo'
	button array so the undo button works even after saving/loading 
	@param none
	@return none
	 ******************************************************************/
	public void save(ArrayList<Point> moveList) {

		//Create file chooser to initialize in the last directory
		JFileChooser chooser = new JFileChooser(lastDirectory);

		//Catch an IOException error
		try {

			//Display the save dialog
			int status = chooser.showSaveDialog(null);

			//Alert that no file has been selected if necessary
			if(status != JFileChooser.APPROVE_OPTION)
				JOptionPane.showMessageDialog(null, 
						"No file selected!");
			else {

				//Initialize a PrintWriter
				PrintWriter out = null;

				//Get the selected file and store it's path as the 
				//last directory
				File file = chooser.getSelectedFile();
				lastDirectory = file.getPath();

				//Create the PrintWriter
				out = new PrintWriter(new BufferedWriter(new 
						FileWriter(file)));

				//Print the board size
				out.println("" + ROWS + " ");

				//Print the current player
				out.println("" + player + " ");

				//Print the moveList's size
				out.println("" + moveList.size() + " ");

				//Print the board
				for(int row = 0; row < ROWS; row++) {
					for(int col = 0; col < COLS; col++) {
						if(board[row][col] == Cell.X)
							out.println("0 ");
						if(board[row][col] == Cell.O)
							out.println("1 ");
						if(board[row][col] == Cell.EMPTY)
							out.println("2 ");
					}
				}

				//Print the moveList
				for(int i = 0; i < moveList.size(); i++) {
					out.println("" + moveList.get(i).x + " ");
					out.println("" + moveList.get(i).y + " ");
				}

				//Close the file
				out.close();
			}
		} catch(IOException error) {

			//Alert that the saving process was unable to succeed
			JOptionPane.showMessageDialog(null, 
					"Unable to save file!");
		}
	}


	/******************************************************************
	Loads time information from a file into the stop watch
	@param Whether or not to select a file or just select on the board
	@return none
	 ******************************************************************/
	public ArrayList<Point> load(boolean openFile) {

		//Initialize the move array list
		ArrayList<Point> loadMoveList = new ArrayList<Point>();

		//Create JFileChooser to initialize in the last directory
		JFileChooser chooser = new JFileChooser(lastDirectory);

		//Catch an IOException error
		try {

			//Initialize to JFileChoose.APPROVE_OPTION to bypass check
			int status = JFileChooser.APPROVE_OPTION;

			//Display the open dialog if necessary
			if(openFile)
				status = chooser.showOpenDialog(null);

			//Alert that no file has been selected if necessary
			if(status != JFileChooser.APPROVE_OPTION)
				JOptionPane.showMessageDialog(null, 
						"No file selected!");
			else {
				File file;

				//Get the selected file and store it's path as the 
				//last directory
				if(openFile) {
					file = chooser.getSelectedFile();
					lastDirectory = file.getPath();
				}
				else
					file = new File(lastDirectory);

				//Set up a scanner to read the file
				Scanner scan = new Scanner(file);

				//Scan in the board size and player number
				int fileBoardSize = scan.nextInt();
				int filePlayer = scan.nextInt();

				//Get the move list's size
				int loadMoveListSize = scan.nextInt();

				//Simulate player turns by populating the board 
				//from the file contents if not opening file
				if(!openFile)
					for(int row = 0; row < fileBoardSize; row++)
						for(int col = 0; col < fileBoardSize; col++) {
							int i = scan.nextInt();

							if(i == 0) {
								player = 0;
								select(row, col);
							}

							if(i == 1) {
								player = 1;
								select(row, col);
							}	
						}

				//Initialize current player to file's player
				player = filePlayer;

				//Populate the move list from the file
				for(int i = 0; i < loadMoveListSize; i++) {
					Point p = new Point();
					p.x = scan.nextInt();
					p.y = scan.nextInt();
					loadMoveList.add(p);
				}

				//Also send the board size and player back to the panel
				if(openFile)
					loadMoveList.add(new Point(fileBoardSize, filePlayer));	
			}
		} catch(IOException error) {

			//Alert that the loading process was unable to succeed
			JOptionPane.showMessageDialog(null, "Load failed!");
		}

		//Send the move list
		return loadMoveList;
	}
}
