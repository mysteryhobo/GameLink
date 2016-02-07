package org.gamelink.game;

abstract class GameBoard {
	/** The 2d integer array representing the board on which the game is being played */
	protected int[][] gameBoard;

	/** The player number of the user opperating this side of the game */
	protected int playerNumber;

	/** The player number of the user opperating the other side of the game */
	protected int opponentPlayerNumber;

	/** The integer representation of player 1s player number */
	protected final int PLAYER1 = 1;

	/** The integer representation of player 2s player number */
	protected final int PLAYER2 = 2;

	/** The integer representation of a completed game. */
	protected final int VALID_GAME = 3;

	/** The integer representation Tie game. */
	protected final int TIE = 3;

	/** The integer representation of an invalid game. */
	protected final int INVALID_GAME = 4;

	/** The integer representation of an invalid cell on the gameBoard */
	protected final int INVALID_CELL = 99;

	/** The score of player 1 aquired over the course of the game */
	protected int player1Score = 0;

	/** The score of player 2 aquired over the course of the game */
	protected int player2Score = 0;

	/** The player number of the winner of the match */
	protected int winner = 0;

	/** The player number of the player to move next */
	protected int whosTurn = 1;

	/** A flag used to indicate to the painter class that the game has ended */
	protected boolean gameOverFlag = false;

	/** The height of the gameBoard */
	protected int boardHeight;

	/** The width of the gameBoard */
	protected int boardWidth;

	/** Defines whether the gameBoard is displayed to the user or not */
	protected boolean displayBoard;

	protected abstract int[][] createNewBoard();

	/**
	 * Sets the playerNumber of the player and sets the opponentPlayerNumber to the opposite
	 * @param   pNum   The playerNumber of the user
	 */
	protected void setPlayerNumber(int pNum){
		playerNumber = pNum;
		if (playerNumber == PLAYER1) opponentPlayerNumber = PLAYER2;
		else opponentPlayerNumber = PLAYER1;
	}

	/**
	 * Retreives the current 2d integer representation of the gameBoard
	 * @return   The current 2d integer representation of the gameBoard
	 */
	protected int[][] getBoard(){
		return gameBoard;
	}

	/**
	 * Converts to the String representation of the gameBoard to a 2d integer representation
	 * @param   boardAsString   The string representation of the game board
	 * @return                  The 2d integer array representation of the game board
	 */
	protected int[][] toArray(String boardAsString) {
	  int rowCounter = 0;
	  int[][] boardAsArray = new int[boardHeight][boardWidth];
	  String[] rowsAsStrings = boardAsString.split("\\|");
	  for (String row : rowsAsStrings) {
	    String[] cellsAsStrings = row.replace("[", "").replace("]", "").split(", ");
	    int[] rowAsArray = new int[cellsAsStrings.length];
	    for (int i = 0; i < rowAsArray.length; i++) {
	      rowAsArray[i] = Integer.parseInt(cellsAsStrings[i]);
	    }
	    boardAsArray[rowCounter] = rowAsArray;
	    rowCounter ++;
	  }
		return boardAsArray;
	}

	/**
	 * Retreives the player number of the player to move next
	 * @return   The player number of the player to move next
	 */
	protected int getTurn() {
		return whosTurn;
	}

	/**
	 * Retreives the current score of player 1
	 * @return   The current score of player 1
	 */
	protected int getPlayer1Score(){
		return player1Score;
	}

	/**
	 * Retreives the current score of player 2
	 * @return   The current score of player 2
	 */
	protected int getPlayer2Score(){
		return player2Score;
	}

	/**
	 * Retreives the winner of the match
	 * @return   The winner of the match
	 */
	protected int getWinner() {
		return winner;
	}

	/**
	 * Retrieves if the game is over
	 * @return true if the game is over false otherwise
	 */
	protected boolean getGameOverFlag() {
		return gameOverFlag;
	}

	/** Switches the player to move next to the opposing player */
	protected void switchTurn() {
		if (whosTurn == PLAYER1) whosTurn = PLAYER2;
		else whosTurn = PLAYER1;
	}

	/** sets the 2d integer array of the gameBoard to an new 2d integer array */
	protected void setBoard(int[][] board){
		gameBoard = board;
	}

	/** Prints the 2d integer representation of the gameBoard to the standard output*/
	protected void printBoard(){
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				if (gameBoard[y][x] == INVALID_CELL) System.out.print("|X");
				else System.out.print("|" + gameBoard[y][x]);
			}
			System.out.print("|\n");
		}
		System.out.println("--------------------");
	}

	/** Prints the 2d integer representation of a board to the standard output
	 * @param   board   The board to be printed to the standard output
	 */
	protected void printBoard(int[][] board){
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				if (board[y][x] == INVALID_CELL) System.out.print("|X");
				else System.out.print("|" + board[y][x]);
			}
			System.out.print("|\n");
		}
		System.out.println("--------------------");
	}

	/** Prints the scores of both players 1 and 2 as well as the winner of the match */
	protected void printScore(){
			System.out.println("********************");
			System.out.println("GAMEOVER");
			System.out.println("Final Score: ");
			System.out.println("Player1: " + player1Score);
			System.out.println("PLayer2: " + player2Score);
			System.out.println("Player " + winner + " wins");
			System.out.println("********************");
	}

	protected abstract boolean isGameOver();

	protected abstract void cleanUp(int winningPlayer);


	/**
	 * Determine if two 2d integer representation of the board have the same configuration
	 * @param   board1   The first board to be compared in the form of a 2d integer array
	 * @param   board2   The second board to be compared in the form of a 2d integer array
	 * @return           True if the boards are the same, false otherwise
	 */
	protected boolean areBoardsEqual(int[][] board1, int[][] board2){
		for (int x = 0; x < boardWidth; x++)
			for (int y = 0; y < boardHeight; y++)
				if (board1[y][x] != board2[y][x]) return false;
		return true;
	}

	/**
	 * Creates a copy of the 2d integer array board
	 * @param   board   The 2d integer array board to be copied
	 * @return          The copy of the original board
	 */
	protected int[][] copyBoard(int[][] board) {
		int[][] copyBoard = new int[board.length][board[0].length];
		int rowCounter = 0;
		for (int[] row : board)
			System.arraycopy(row, 0, copyBoard[rowCounter++], 0, row.length);
		return copyBoard;
	}
}