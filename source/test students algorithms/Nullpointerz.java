package algorithms;

import java.util.*;
import org.gamelink.game.Algo;
import org.gamelink.game.Cram;

public class Nullpointerz extends Algo { // Replace TeamName
	// Global variables accessible by the class members
	private static String teamName = "Nullpointerz"; // Replace TeamName

	// Initialize and construct 2d array
	static int[][] boardInit = new int[5][5];

	// Initialize and construct Manager class
	static Manager manager = new Manager();

	// Intiialize and construct Board class
	static Board board = new Board(boardInit, false, false);

	// Initialize and construct the HashMap containing all possible boards
	static HashMap<Board, LinkedList<Utility>> boards = new HashMap<Board, LinkedList<Utility>>();

	// Initialize and construct the HashMap with keys associated to board
	static HashMap<Board, Board> boardKeys = new HashMap<Board, Board>();

	// Store state of board solver
	static boolean boardSolved = false;

	public static String getTeamName() {
		return teamName;
	}

	public static void main(String[] args) {
		// Set the column and row count to 5 since its 5x5 grid
		manager.setColCount(5);
		manager.setRowCount(5);
		Cram game = new Cram(false);
		game.startGame(Nullpointerz.class); // Replace TeamName
	}

	public static String firstMove(Board board) {
		String firstMove = null;

		ArrayList<String> firstMoveArr = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (manager.isHorizontalAvailable(board, i, j)) {
					if (j == 4) {}
					else {
						firstMoveArr.add(i + "" + j + "" + i + "" + (j + 1));
					}
				}

				if (manager.isVerticalAvailable(board, i, j)) {
					if (i == 4) {}
					else {
						firstMoveArr.add(i + "" + j + "" + (i + 1) + "" + j);
					}
				}
			}
		}

		Random rand = new Random();
		int n = rand.nextInt(firstMoveArr.size());
		firstMove = firstMoveArr.get(n) + "";

		return firstMove;
	}

	/**
	 * Contains the method calls
	 * @param  game Cram game
	 * @return      4 number specifying the move
	 */
	public static String algorithm(Cram game) {
		// Holds the move to be placed
		String move = null;

		// Copy the updated board onto board class
		board.copyBoard(game.getBoard());

		// contains the counter
		int counter = 0;

		// Count the number of cells occupied
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (board.board[i][j] == 0) {
					counter++;
				}
			}
		}

		// IF the grid only contains 2 cells, meaning its the start of the game, so place random spot
		if (counter >= 23)
			return firstMove(board);

		// Check if the board has been solved
		if (!boardSolved) {
			// Get the worst case
			String worstCase = manager.computeWorstCase(board);

			// Store the result of win
			String winResult = null;

			// Execute this block if the worst case is N
			if (worstCase.equals("N")) {
				System.out.println("[DEBUG]: Worst Case: false");

				// construct and initialize linked list
				LinkedList<Utility> startBoardListing = new LinkedList<Utility>();

				// add all the boards to the linkedlist
				boardKeys.put(board, board);

				// add all the board keys to the linked list
				boards.put(board, startBoardListing);

				// Solve the board by calling solveCram method
				manager.solveCram(boards, boardKeys, board);

				// change the state of the board solved to true
				boardSolved = true;

				// Calculate move and return it to move
				move = manager.calculateMove(boards, boardKeys, board);

				// Return the move
				return move;
			} else {
				System.out.println("[DEBUG]: Worst Case: true");

				// set the worst case to move
				move = worstCase;

				// check if there is a winning outcome
				winResult = manager.conditionWin(board);

				// If it returns as true
				// then set the win condition for the board to true
				if (winResult.equals("true"))
					board.setWin(true);
				else
					board.setWin(false);
			}
		} else {
			// Initialize and construct LinkedList
			LinkedList<Utility> startBoardListing = new LinkedList<Utility>();

			// add the board to the boards keys
			boardKeys.put(board, board);

			// Add the board to the list of boards
			boards.put(board, startBoardListing);

			// Solve the board with the new move from the opponent
			manager.solveCram(boards, boardKeys, board);

			// Calculate the move needed after solving the board
			move = manager.calculateMove(boards, boardKeys, board);
		}

		return move;
	}
}

class Board {
	// Global 2D int variable
	public int board[][];

	// Boolean condition that holds the status of win
	boolean isWin;

	// Boolean condition that hold the state of pocess
	boolean isProcessed;

	// Winning ratio
	float winRatio;

	// Contains the number of children the email
	long totalChildrenCount;

	/**
	 * Board Class constructor
	 * @param  board[][]   current grid
	 * @param  condition   is there a winning outcome for this grid? [Default: TRUE]
	 * @param  isProcessed has this board been processed? [Default: FALSE]
	 * @return             null
	 */
	public Board(int[][] boardInit, boolean condition, boolean isProcessed) {
		// Assign the current grid to different 2D grid
		this.board = boardInit;

		// Set the winning outcome, fed in through parameter
		setWin(condition);

		// Set the processed state, fed in through parameter
		setProcessed(isProcessed);

		// Reset the winning ratio to 0
		setWinRatio(0);

		// Reset the children's count to 0
		setChildrenCount(0);
	}

	/**
	 * Get the win status
	 * @return boolean win status
	 */
	public boolean canWin() {
		return isWin;
	}

	/**
	 * Set the win status
	 * @param isWin null
	 */
	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	/**
	 * Get the processed state
	 * @return boolean process state
	 */
	public boolean getProcessed() {
		return isProcessed;
	}

	/**
	 * Set the processed state
	 * @param isProcessed null
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/**
	 * Get the winning ratio
	 * @return float winning ratio data
	 */
	public float getWinRatio() {
		return winRatio;
	}

	/**
	 * Set the winning ratio
	 * @param winRatio null
	 */
	public void setWinRatio(float winRatio) {
		this.winRatio = winRatio;
	}

	/**
	 * Get the total count of childrens
	 * @return [description]
	 */
	public long getChildrenCount() {
		return totalChildrenCount;
	}

	/**
	 * Set the number of children
	 * @param totalChildrenCount [description]
	 */
	public void setChildrenCount(long totalChildrenCount) {
		this.totalChildrenCount = totalChildrenCount;
	}

	public void copyBoard(int oldBoard[][]) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = oldBoard[i][j];
			}
		}
	}

	/**
	 * Returns a hash code value for the object.
	 * @return a hash code value for this object
	 *
	 * @Override to ensure the hashing efficiency by getting hash of the board
	 */
	@Override
	public int hashCode() {
		int hash = java.util.Arrays.deepHashCode(board);
		return hash;
	}

	/**
	 * Indicates whether some other object is "equal to" this one
	 * @param  b the reference object with which to compare
	 * @return   true if this object is the same as the object argument, false otherwise
	 *
	 * @override to ensure only the boards are being compared
	 */
	@Override
	public boolean equals(Object b) {
		if (!(b instanceof Board)) {
			return false;
		}

		if (board.length != ((Board) b).board.length) {
			return false;
		}

		for (int k = 0; k < board.length; k++) {
			if (board[k].length != ((Board) b).board[k].length) {
				return false;
			}
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != ((Board) b).board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * String representation of the object
	 * @return a string representation of the object
	 *
	 * @override to provide custom output
	 */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0 ; i < 5; i++) {
			result += "[";
			for (int j = 0; j < 5; j++) {
				result += board[i][j];
				if (j != 5 - 1) {
					result += ", ";
				}
			}
			if (i != 5 - 1) {
				result += "]\n";
			}
		}

		return result;
	}
}

class Utility {
	// Global variables
	private Board board;
	private int row1;
	private int row2;
	private int col1;
	private int col2;

	/**
	 * Main constructor for Utility class
	 * @param  currentBoard the current board
	 * @param  row1         row1 to insert into
	 * @param  row2         row2 to insert into
	 * @param  col1         col1 to insert into
	 * @param  col2         col2 to insert into
	 * @return              null
	 */
	public Utility(Board currentBoard, int row1, int col1, int row2, int col2) {
		// Set the board to current board at this position
		setBoard(currentBoard);

		// Set the row1
		setRow1(row1);

		// SetmoveToRow2 += (keyBoardListing.get(lowChildrenIndex).getRow2()); the row2
		setRow2(row2);

		// Set the col1
		setCol1(col1);

		// Set the row col2
		setCol2(col2);
	}

	/**
	 * Get the current board
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Set the current board
	 * @param board current board at the position
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Get the value of row1
	 * @return value of row1
	 */
	public int getRow1() {
		return row1;
	}

	/**
	 * Set the value of row1
	 * @param row1 null
	 */
	public void setRow1(int row1) {
		this.row1 = row1;
	}

	/**
	 * Get the value of row2
	 * @return value of row2
	 */
	public int getRow2() {
		return row2;
	}

	/**
	 * Set the value of row2
	 * @param row1 null
	 */
	public void setRow2(int row2) {
		this.row2 = row2;
	}

	/**
	 * Get the value of col1
	 * @return value of col1
	 */
	public int getCol1() {
		return col1;
	}

	/**
	 * Set the value of col1
	 * @param row1 null
	 */
	public void setCol1(int col1) {
		this.col1 = col1;
	}

	/**
	 * Get the value of col2
	 * @return value of col2
	 */
	public int getCol2() {
		return col2;
	}

	/**
	 * Set the value of col2
	 * @param row1 null
	 */
	public void setCol2(int col2) {
		this.col2 = col2;
	}
}

class Manager {
	// Global Variables
	private int numRows = 5;
	private int numCols = 5;
	private int prevRow1;
	private int prevRow2;
	private int prevCol1;
	private int prevCol2;
	private int combinationCount = 0;
	private int finishedCount = 0;
	private int boardLength = 1;

	// Initialize and Construct Linked List data structure with the elements of the collection Board
	// Contains boards for the worst case
	private LinkedList<Board> worstCase = new LinkedList<Board>();

	// Initialize and Construct Linked List data structure with the elements of the collection String
	// Contains the moves for the worst cases
	private LinkedList<String> worstCaseMoves = new LinkedList<String>();

	/**
	 * Return the worst case if the current state of the board is found in worstCase list
	 * @param  boardArg Current state of the board
	 * @return          N, if the board does not exist in list; Board if the current board state is in the list
	 */
	public String computeWorstCase(Board boardArg) {
		// Will contain the data to be returned
		String result = "N";

		// Loop through the list of worst cases
		for (int i = 0; i < worstCase.size(); i++) {
			// If the board parameter contains the board in the list, then return the list
			if (boardArg.equals(worstCase.get(i))) {
				System.out.println("[BEBUG]: Worst Case Found!");
				// Get the board from the worst case using the index
				result = worstCaseMoves.get(i);

				// Terminate the loop
				break;
			}
		}

		// Return the result
		return result;
	}

	/**
	 * Return true or false based on board's status of winning
	 * @param  boardArg current board state
	 * @return          true if the current board exists in worst case list and return if the board has chance of winning
	 */
	public String conditionWin(Board boardArg) {
		// Will contain the data to be returned
		String result = "true";

		// Loop through the list of worst cases
		for (int i = 0; i < worstCase.size(); i++) {
			// If the board parameter contains the board in the list
			if (boardArg.equals(worstCase.get(i))) {
				// Get the win status of the board found
				if (worstCase.get(i).canWin() == false)
					result = "false";

				// Terminate the loop
				break;
			}
		}

		// Return the result
		return result;
	}

	/**
	 * construct hashtable to implement Map interface
	 * @param boards    List of boards
	 * @param boardKeys List of board keys
	 * @param board     current board
	 */
	public void solveCram(HashMap<Board, LinkedList<Utility>> boards, HashMap<Board, Board> boardKeys, Board board) {
		// Boolean to store the state of the construction, DEFAULT: false
		boolean isFinished = false;

		// Call the constructHash function
		constructHash(boards, boardKeys, board);

		// change the state to true to indicate, hash has been processed
		isFinished = true;

		if (isFinished)
			System.out.println("[DEBUG]: Finished Constructing HashTable.");
	}

	/**
	 * Construct the hash table based on every single possible move
	 * @param boards    List of boards
	 * @param boardKeys List of board keys
	 * @param board     Current board
	 */
	public void constructHash(HashMap<Board, LinkedList<Utility>> boards, HashMap<Board, Board> boardKeys, Board board) {
		// Create a nested loop to iterate through each and every possible move
		// Loop through each cell in a row
		for (int row = 0; row <= this.getRowCount() - 1; row++) {
			// Loop through each cell in a column
			for (int column = 0; column <= this.getColcount() - 1; column++) {
				// Check if there is a place for horizontal positioning
				if (this.isHorizontalAvailable(board, row, column)) {
					// Column doesn't hit grid boundary
					if (column != this.getColcount() - 1)
						// set the horizontal move and add it to the list of boards
						this.setMove(board, row, column, row, column + 1, boards, boardKeys);
				}

				// Check if there is a place for vertical positioning
				if (this.isVerticalAvailable(board, row, column)) {
					// Check if row doesn't hit grid boundary
					if (row != this.getRowCount() - 1)
						// Set the vertical move and add it to list of boards
						this.setMove(board, row, column, row + 1, column, boards, boardKeys);
				}
			}
		}

		// Once all the possible horizontal and vertical moves have been recorded
		// call populateHash to fill all boards to the hash map
		populateHash(boards, boardKeys);
	}

	/**
	 * Populate all the possible boards to the HashMap
	 * @param boards    List of boards containing the moves
	 * @param boardKeys List of keys associated to the board
	 */
	public void populateHash(HashMap<Board, LinkedList<Utility>> boards, HashMap<Board, Board> boardKeys) {
		// Keep looping untill we exceed the count of boards
		while (this.finishedCount < this.boardLength) {
			System.out.println("[DEBUG]: Finished Count: " + this.finishedCount + ";\t Boards Count: " + this.boardLength);

			// Initialize and Construct Object array
			// Get the set view of the keys contained in the board map
			Object[] boardArr = boards.keySet().toArray();

			// Get the size of the map specific to board
			this.boardLength = boardArr.length;

			// reset the finished count to 0
			this.finishedCount = 0;

			// Loop through the size of the map specific to board
			for (int i = 0; i < this.boardLength; i++) {
				// Get each board as an index and peek at the first element
				// execute the block is the first element is non null
				if (boards.get(boardArr[i]).peekFirst() == null) {
					// Save the state of isPlaced, default to false
					boolean isPlaced = false;

					// Nested loop to loop through row and column
					// Loop through the row of the grid
					for (int row = 0; row <= this.getRowCount() - 1; row++) {
						// Loop through the column of the grid
						for (int column = 0; column <= this.getColcount() - 1; column++) {
							// Check if the horizontal position is available for placement
							if (this.isHorizontalAvailable((Board) boardArr[i], row, column)) {
								// Check for boundaries of the grid
								if (column != this.getColcount() - 1) {
									// Change the state of is Placed
									isPlaced = true;

									// Set the move and add it to the hashmap
									this.setMove((Board) boardArr[i], row, column, row, column + 1, boards, boardKeys);
								}
							}

							// Check if the vertical position is available for placement
							if (this.isVerticalAvailable((Board) boardArr[i], row, column)) {
								// Check for boundaries of the grid
								if (row != this.getRowCount() - 1) {
									// Change the state of isPlace to true
									isPlaced = true;

									// Set the move and add it to the hashmap
									this.setMove((Board) boardArr[i], row, column, row + 1, column, boards, boardKeys);
								}
							}
						}
					}

					// If isPlace is false, then execute the code
					if (!isPlaced) {
						// Initialize and construct Utility class with boards key
						Utility utility = new Utility((Board) boardArr[i], 0, 0, 0, 0);

						// Get the board from hash map and add it to the utility class
						boards.get(boardArr[i]).add(utility);

						// Set the win state of the board to false since no move was placed
						((Board) boardArr[i]).setWin(false);

						// Set the state of processed board to true, meaning this board has already been processed
						((Board) boardArr[i]).setProcessed(true);
					}
				} else {
					// Increment the count of tasks finished
					this.finishedCount++;
				}
			}
		}

		// Once finished with populating hash map, then process this hash map
		processHash(boards, boardKeys);
	}

	/**
	 * Parse the hash maps in order to find winning boards
	 * @param boards    List of boards
	 * @param boardKeys List of boards keys
	 */
	public void processHash(HashMap<Board, LinkedList<Utility>> boards, HashMap<Board, Board> boardKeys) {
		System.out.println("[DEBUG]: Processing Hashes");

		// Initialize Object array and set key sets of boards
		Object[] boardArr = boards.keySet().toArray();

		// Get the length of the object array
		this.boardLength = boardArr.length;

		// store the number of total processed hashmaps
		int totalProcessed = 0;

		// Keep on looping until we have processed each and every boards
		while (totalProcessed < boardArr.length) {
			// Reset the count of totalprocessed
			totalProcessed = 0;

			// Loop through each of the board from boardArr object array
			for (int i = 0; i < this.boardLength; i++) {
				// Print the processing stats every 20000 keys
				if (i % 15000 == 0)
					System.out.println("[DEBUG]: Processing " + (i + 1) + " out of " + boardArr.length + " boards");

				// Get all the boards that were not processed
				if (!boardKeys.get(((Board) boardArr[i])).getProcessed()) {
					// Initialize LinkedList for the boards not processed
					LinkedList<Utility> boardKeyList = boards.get(boardArr[i]);

					// Stores the count of wins each move can result in
					int winCount = 0;

					// Stores the total boards processed count
					int processedCount = 0;

					// Stores the number of childrens
					long childrenCount = 0;

					// Loop through the size of linkedlist
					for (int j = 0; j < boardKeyList.size(); j++) {
						// Get all the boards that have already been processed
						if (boardKeys.get(boardKeyList.get(j).getBoard()).getProcessed()) {
							// Set the status of processed to true
							boardKeyList.get(j).getBoard().setProcessed(true);

							// Increment the number of boards processed
							processedCount++;

							// Check if the board in linkedlist does not contain win state as others in board keys hashmap
							if (boardKeyList.get(j).getBoard().canWin() != boardKeys.get(boardKeyList.get(j).getBoard()).canWin())
								// Set it to true if it does
								boardKeyList.get(j).getBoard().setWin(boardKeys.get(boardKeyList.get(j).getBoard()).canWin());

							// Check if the board in the linkedlist does not contain the same amount of win ratio
							if (boardKeyList.get(j).getBoard().getWinRatio() != boardKeys.get(boardKeyList.get(j).getBoard()).getWinRatio())
								// Set the winratio of the board into the linkedlist
								boardKeyList.get(j).getBoard().setWinRatio(boardKeys.get(boardKeyList.get(j).getBoard()).getWinRatio());

							// Check if the board in the linkedlist have the same amount of children as the hash map
							if (boardKeyList.get(j).getBoard().getChildrenCount() != boardKeys.get(boardKeyList.get(j).getBoard()).getChildrenCount())
								// set the children count if it's not
								boardKeyList.get(j).getBoard().setChildrenCount(boardKeys.get(boardKeyList.get(j).getBoard()).getChildrenCount());

							// Check if the boards in linked list contains win scenarios
							if (boardKeyList.get(j).getBoard().canWin())
								// Increment the win counter if it does
								winCount++;

							// Set the children count by incrementing the childrens found in the linked list
							childrenCount += boardKeys.get(boardKeyList.get(j).getBoard()).getChildrenCount() + 1;
						}
					}

					// Check if the we have finished processing each board
					if (processedCount == boardKeyList.size()) {
						// If the win count contains value same as linked list size
						if (winCount == boardKeyList.size())
							// Then set the win to false
							boardKeys.get(((Board) boardArr[i])).setWin(false);
						else
							// else set it to true
							boardKeys.get(((Board) boardArr[i])).setWin(true);

						// Set the state of the board to processed
						boardKeys.get(((Board) boardArr[i])).setProcessed(true);

						// Calculate the win ratio by finding the ratio of win to number of boards
						boardKeys.get(((Board) boardArr[i])).setWinRatio((float) winCount / (float) boardKeyList.size());

						// Set the children counts of the board
						boardKeys.get(((Board) boardArr[i])).setChildrenCount(childrenCount);
					} else
						// Change the processed state to false indicating the board hasn't been processed
						boardKeys.get(((Board) boardArr[i])).setProcessed(false);
				} else {
					// Increment the processed count
					totalProcessed++;
				}
			}

			// Display some stats for processed
			System.out.println("[DEBUG]: Total processed: " + totalProcessed + "; \t Size: " + boardArr.length);
		}
	}

	/**
	 * Boolean check whether the row and column is empty in a board, iterating through horizontal positions
	 * @param  board  current state of the board
	 * @param  row    row to start the search
	 * @param  column column to start the search
	 * @return        true, if the place is available horizontally, false otherwise
	 */
	public boolean isHorizontalAvailable(Board board, int row, int column) {
		// Check to make sure the row and column doesn't exceed the boundaries of the grid
		if (row > 4 || column >= 4) { }
		else {
			// Check the first cell and the next horizontal cell to validate the availability
			if (board.board[row][column] == 0 && board.board[row][column + 1] == 0)
				return true;
		}
		return false;
	}

	/**
	 * Boolean check whether the row and column is empty in a board, iterating through vertical positions
	 * @param  board  current state of the boolean
	 * @param  row    row to start the search
	 * @param  column column to start the search
	 * @return        true, if the place is available vertically, false otherwise
	 */
	public boolean isVerticalAvailable(Board board, int row, int column) {
		// Check to make sure the row and column doesn't exeed the boundaries of the grid
		if (row >= 4 || column > 4) { }
		else {
			// Check the first cell and the next vertical cell to validate the availability
			if (board.board[row][column] == 0 && board.board[row + 1][column] == 0)
				return true;
		}
		return false;
	}

	/**
	 * Fill the positions on the board with the values provided in the parameter
	 * @param board     current state of the board
	 * @param row1      row of the first cell
	 * @param column1   column of the first cell
	 * @param row2      row of the next cell, vertical or horizontal
	 * @param column2   column of the next cell, vertical or horizontal
	 * @param boards    List of Boards collection
	 * @param boardKeys List of Boards collection containing the keys
	 */
	public void setMove(Board board, int row1, int column1, int row2, int column2, HashMap<Board, LinkedList<Utility>> boards, HashMap<Board, Board> boardKeys) {
		// Initialize and construct a 5x5 2d matrix
		int[][] boardInit = new int[5][5];

		// Initialize and construct Board class with 5x5 empty grid
		Board thisBoard = new Board(boardInit, true, false);

		// Loop through the board and clone it onto thisBoard
		for (int i = 0; i < 5; i++)
			thisBoard.board[i] = board.board[i].clone();

		// Set the row and column of the grid with 1 to fill the 2 cell
		thisBoard.board[row1][column1] = 1;
		thisBoard.board[row2][column2] = 1;

		// Construct a Utility class with thisBoard and the values of the cells
		Utility utility = new Utility(thisBoard, row1, column1, row2, column2);

		// Check if the list of boards contain this specific related board
		// Check if they are not returned as null
		if (boards.get(thisBoard) != null)
			// If found, add the board to the utiltiy
			boards.get(board).add(utility);
		else {
			// If not found, add the other boards into utility
			boards.get(board).add(utility);

			// Initialize and Construct a new board of collection Utility
			LinkedList<Utility> newBoard = new LinkedList<Utility>();

			// Add the current board to the list of boards with keys
			boardKeys.put(thisBoard, thisBoard);

			// Add the current board to the list of boards
			boards.put(thisBoard, newBoard);
		}

		// Set the row and column for the 2 cells horizontally or vertically
		setPrevRow1(row1);
		setPrevCol1(column1);
		setPrevRow2(row2);
		setPrevCol2(column2);
		this.combinationCount++;
	}

	/**
	 * Calculate the best move given the current state of the board
	 * @param  boards    List of boards with possibile possabilities
	 * @param  boardKeys List of keys associated to the board
	 * @param  board     board class containg the current state of the board
	 * @return           [description]
	 */
	public String calculateMove(HashMap<Board, LinkedList<Utility>> boards, HashMap<Board, Board> boardKeys, Board board) {
		String move = "";

		// contains the 4 positions
		int moveToRow1 = 1, moveToRow2 = 1, moveToCol1 = 1, moveToCol2 = 1;

		// Get the boolean value of the board win status
		boolean canWin = boardKeys.get(board).canWin();

		// Execute the block if the board has a win status
		if (canWin) {
			// Initialize linked list of current board
			LinkedList<Utility> keyGridList = boards.get(board);

			// Store leastChildren value
			// 9223372036854775807
			long leastChildren = Long.MAX_VALUE;

			// Store the index of least children
			int leastChildrenIndex = 0;

			// Loop through each element in linked list
			for (int i = 0; i < keyGridList.size(); i++) {
				// Get the boards that have a loss status
				if (!keyGridList.get(i).getBoard().canWin()) {
					// Check if the board's children count is less than the max value
					if (keyGridList.get(i).getBoard().getChildrenCount() < leastChildren) {
						// Set the children count to leastChildren
						leastChildren = keyGridList.get(i).getBoard().getChildrenCount();

						// Set the children index
						leastChildrenIndex = i;
					}
				}
			}

			// Increment the 4 positions of the least child
			moveToRow1 += (keyGridList.get(leastChildrenIndex).getRow1());
			moveToRow2 += (keyGridList.get(leastChildrenIndex).getRow2());
			moveToCol1 += (keyGridList.get(leastChildrenIndex).getCol1());
			moveToCol2 += (keyGridList.get(leastChildrenIndex).getCol2());
		} else {
			// Intiialize linked list with the current board
			LinkedList<Utility> keyGridList = boards.get(board);

			// Store the highest ratio
			float highestRatio = 0;

			// store the number of children
			long mostChildren = 0;

			// store the index of the child
			int mostChildrenIndex = 0;

			// Loop through the linked list
			for (int i = 0; i < keyGridList.size(); i++) {
				// Check if the win ratio is higher than the current ratio
				if (keyGridList.get(i).getBoard().getWinRatio() > highestRatio) {
					// set the value of highest ratio of the current board
					highestRatio = keyGridList.get(i).getBoard().getWinRatio();

					// Set the count of max children of the current board
					mostChildren = keyGridList.get(i).getBoard().getChildrenCount();

					// set the index of most children
					mostChildrenIndex = i;
				}
				// Check if the win ratio is the highest
				else if (keyGridList.get(i).getBoard().getWinRatio() == highestRatio) {
					// check if the current board has more children
					if (keyGridList.get(i).getBoard().getChildrenCount() > mostChildren) {
						// Set the highest ratio of the current board
						highestRatio = keyGridList.get(i).getBoard().getWinRatio();

						// Set the number of children of the current board
						mostChildren = keyGridList.get(i).getBoard().getChildrenCount();

						// change the index of the max child board
						mostChildrenIndex = i;
					}
				}
			}

			// Increment the 4 positions of the least child
			moveToRow1 += (keyGridList.get(mostChildrenIndex).getRow1());
			moveToRow2 += (keyGridList.get(mostChildrenIndex).getRow2());
			moveToCol1 += (keyGridList.get(mostChildrenIndex).getCol1());
			moveToCol2 += (keyGridList.get(mostChildrenIndex).getCol2());
		}

		// Append the positions to the move variable, thus creating the next move
		move += moveToRow1 - 1;
		move += moveToCol1 - 1;
		move += moveToRow2 - 1;
		move += moveToCol2 - 1;

		return move;
	}

	/**
	 * Get the number of rows
	 * @return number of columns
	 */
	public int getRowCount() {
		return numRows;
	}

	/**
	 * Set the number of rows
	 * @param num number of rows
	 */
	public void setRowCount(int num) {
		this.numRows = num;
	}

	/**
	 * Get the number of columns
	 * @return number of columns
	 */
	public int getColcount() {
		return numCols;
	}

	/**
	 * Set the number of columns
	 * @param num number of columns
	 */
	public void setColCount(int num) {
		this.numCols = num;
	}

	/**
	 * Get the index of row for first cell
	 * @return index of row
	 */
	public int getPrevRow1() {
		return prevRow1;
	}

	/**
	 * Set the index of row for first cell
	 * @param num index
	 */
	public void setPrevRow1(int num) {
		this.prevRow1 = num;
	}

	/**
	 * Get the index of row for the second cell
	 * @return index
	 */
	public int getPrevRow2() {
		return prevRow2;
	}

	/**
	 * Set the index of row for the second cell
	 * @param num index
	 */
	public void setPrevRow2(int num) {
		this.prevRow2 = num;
	}

	/**
	 * Get the index of column for the first cell
	 * @return index
	 */
	public int getPrevCol1() {
		return prevCol1;
	}

	/**
	 * Set the index of column for the first cell
	 * @param num index
	 */
	public void setPrevCol1(int num) {
		this.prevCol1 = num;
	}

	/**
	 * Get the index of column for the second cell
	 * @return index
	 */
	public int getPrevCol2() {
		return prevCol2;
	}

	/**
	 * Get the index of column for the second cell
	 * @param num index
	 */
	public void setPrevCol2(int num) {
		this.prevCol2 = num;
	}

	/**
	 * Print the values of board on the console
	 * @param board current board state
	 */
	public void printBoard(int[][] board) {
		System.out.println("Printing Board\n");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(" " + board[i][j] + " ");
			}
			System.out.println();
		}
	}
}
