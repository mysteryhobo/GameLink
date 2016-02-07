package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;

import java.util.Random;

/**
 * @author 100514374
 *
 */
public class nickgregorio extends Algo{ // Replace TeamName
	
	//The following are global variables in order to maintain their values within multiple methods. 
	static int blockX1 = 0, blockX2 = 0, blockY1 = 0, blockY2 = 0;
	static boolean enableBlock = false;
	static boolean init =true;
    private static String teamName = "nickgregorio"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(nickgregorio.class); // Replace TeamName
    }

    public static String algorithm(Cram game){
 
   /************************************************
    ************  PLACE ALGORITHM HERE  ************
    ************************************************/
    	//Initialize a copy of the gameboard, as well as parameters used in the algorithm.
    	int availableBlocks = 0;
    	String complete = "";
    	int[][] gameBoard = game.getBoard();
    	
		// Get the number of rows and columns in the game board
		int rows = gameBoard.length;
		int columns = gameBoard[0].length;
		
		//The amount of available blocks will play a role in what decisions the algorithm makes. 
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (gameBoard[i][j] == 0) {
					availableBlocks++;	
					if (isBlocked(i, j, gameBoard)) {
						availableBlocks--;
					}
				}
			}
		}
		
		//If there is an even number of blocks, and the result of that division is not divisible by 2, then the algorithm should not block, as it is currently set to win
		if (availableBlocks % 2 == 0 && (availableBlocks/2) %2 != 0) {
			enableBlock = false;
		} else {
			enableBlock = true;
		}
		
		if (availableBlocks <= (rows*columns) / 5) {
			enableBlock = true;
		}
		
		//In the event that blocking is enabled, and possible, we want to do so.
		if (blockingAvailable(gameBoard) && enableBlock) {
			complete = concat(blockY1, blockX1, blockY2, blockX2);
			
		} 
		else {
			//If blocking is not enabled or not possible, the following algorithm will determine a place for a block
			//Iterate through rows
			for (int i = 0; i < rows; i++) {
				boolean added = false;
				//Iterate through columns
				for (int j = 0; j < columns; j++) {
					//Every time we find an available coordinate, we need to confirm that there is an adjacent available coordinate so that we can actua;ly place our block
					if (isAvailable(i, j, gameBoard)) {
						// Get second coordinate
						if (isAvailable(i + 1, j, gameBoard)) {
							// Concatenate the string
							complete = concat(i, j, i + 1, j);
							// Set added to true
							added = true;
							

						} else if (isAvailable(i - 1, j, gameBoard)) {
							complete = concat(i, j, i - 1, j);
							added = true;
							

						} else if (isAvailable(i, j - 1, gameBoard)) {
							complete = concat(i, j, i, j - 1);
							added = true;
							

						} else if (isAvailable(i, j + 1, gameBoard)) {
							complete = concat(i, j, i, j + 1);
							added = true;
							

						} else {
							
							added = false;
						}

					}

				}
				//If we've added a block, we do not need to continue searching, and can break.
				if (added) {
					break;

				}
			}

		}

		return complete;
		

    
    }
    
    /**
     * Checks if an available position in the board is free
     * @param row amount of rows 
     * @param column amount of columns
     * @param board copy of the game board
     * @return true if the position is available, otherwise false.
     */
	public static boolean isAvailable(int row, int column, int[][] board) {
		if (row >= 0 && row < board.length) {

			if (column >= 0 && column < board[0].length) {
				if (board[row][column] == 0) {
					return true;
				}

			}
		} else {
			return false;
		}

		return false;
	}

	/**
	 * Blocking can be an effective counter-play, forcing the opponent into moves that forces them into a losing scenario
	 * @param board The current gameboard
	 * @return
	 */
	public static boolean blockingAvailable(int[][] board) {
		
		int[][] gameBoard = board;
		boolean available[] = { false, false, false, false };
		int availableCounter = 0;
		boolean breaker = false;
		int tempX1 = 0, tempY1 = 0;
		for (int i = 0; i <= board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				breaker = false;
				available[0] = false;
				available[1] = false;
				available[2] = false;
				available[3] = false;
				// Find available square
				if (isAvailable(i, j, board)) {

					/*
					 * For all availalable squares,determine which adjacent
					 * squares are available. For an effective block, there can
					 * only be one available adjacent square. The available
					 * array and availableCounter array will be responsible for
					 * this Within each check, we will also check if there is an
					 * adjacent square other than the square we intend to block
					 * That way, we know there is a position to place the 2x1
					 * block while still effectively blocking the opponent
					 */

					// Check position to the right of the available position
					if (isAvailable(i, j + 1, gameBoard)) {
						tempY1 = i;
						tempX1 = j + 1;

						available[0] = true;
					}
					// Check position to the left of the available position
					if (isAvailable(i, j - 1, gameBoard)) {
						tempY1 = i;
						tempX1 = j - 1;
						available[1] = true;
					}
					// Check position below available position
					if (isAvailable(i + 1, j, gameBoard)) {
						tempY1 = i + 1;
						tempX1 = j;
						available[2] = true;
					}
					// Check position above available position
					if (isAvailable(i - 1, j, gameBoard)) {
						tempY1 = i - 1;
						tempX1 = j;
						available[3] = true;
					}
					breaker = true;
				
				}
				
				if (breaker) {

					// Ensure that only one available space exists outside of the space we
					// intend to block
					availableCounter = 0;
					for (int count = 0; count < 4; count++)
						if (available[count]) {
							availableCounter++;
						}
					
					

					if (availableCounter == 1) {
						blockX1 = tempX1;
						blockY1 = tempY1;
						
						// Depending on which available index was set to true, set the
						// coordinates of the first two coordinates for concatenation later
						if (available[0]) {
							// Available position is to the right of the possible block;
							// ensure another opening around it.
							// Check the position above block in question, where tempY1 = i,
							// and tempX1 = j+1
							if (isAvailable(tempY1 + 1, tempX1, gameBoard)) {
								blockY2 = tempY1 + 1;
								blockX2 = tempX1;
								return true;
								// Check position to the right of block
							} else if (isAvailable(tempY1, tempX1 + 1, gameBoard)) {
								blockY2 = tempY1;
								blockX2 = tempX1 + 1;
								return true;

								// Check Position below block
							} else if (isAvailable(tempY1 - 1, tempX1, gameBoard)) {
								blockY1 = tempY1 - 1;
								blockX1 = tempX1;
								return true;
							} else {
							
							}

						} else if (available[1]) {
							// Available position is to the left of the possible block;
							// ensure another opening around it
							// Check the positions to the left, above, and below of this
							// position (TempY1 = i; TempX1 = j-1)
							// Check position to the left
							if (isAvailable(tempY1, tempX1 - 1, gameBoard)) {
								blockY2 = tempY1;
								blockX2 = tempX1 - 1;
								return true;
								// Check position below
							} else if (isAvailable(tempY1 + 1, tempX1, gameBoard)) {
								blockY2 = tempY1 + 1;
								blockX2 = tempX1;
								return true;

								// Check position above
							} else if (isAvailable(tempY1 - 1, tempX1, gameBoard)) {
								blockY2 = tempY1 - 1;
								blockX2 = tempX1;
								return true;
							} else {
								
							}

						} else if (available[2]) {
							// Available position is below the possible block location;
							// ensure another space around it.
							// Check the positions to the left, below, and right of this
							// position (TempY1 = i+1; TempX1 = j)
							// Check position to the left
							if (isAvailable(tempY1, tempX1 - 1, gameBoard)) {
								blockY2 = tempY1;
								blockX2 = tempX1 - 1;
								return true;
								// Check position below
							} else if (isAvailable(tempY1 + 1, tempX1, gameBoard)) {
								blockY2 = tempY1 + 1;
								blockX2 = tempX1;
								return true;

								// Check position to the right
							} else if (isAvailable(tempY1, tempX1 + 1, gameBoard)) {
								blockY2 = tempY1;
								blockX2 = tempX1 + 1;
								return true;
							} else {
								
							}
						} else { // available[3] is true
							// Available position is above the possible block locationl;
							// ensure another space around it.
							// Check the positions to the left, right, and above this
							// position (TempY1 = i-1; TempX1 = j);
							// Check position to the left
							if (isAvailable(tempY1, tempX1 - 1, gameBoard)) {
								blockY2 = tempY1;
								blockX2 = tempX1 - 1;
								return true;
								// Check position to the right
							} else if (isAvailable(tempY1, tempX1 + 1, gameBoard)) {
								blockY2 = tempY1;
								blockX2 = tempX1 + 1;
								return true;
								// Check position above
							} else if (isAvailable(tempY1 - 1, tempX1, gameBoard)) {
								blockY2 = tempY1 - 1;
								blockX2 = tempX1;
								return true;
							} else {
							}
						}
					}
					
				}
			}

		}


		return false;
	}

	/**
	 * String must be concatenated with the integers to be compatible with the Cram game system.
	 * @param y1 first y coordinate
	 * @param x1 first x coordinate
	 * @param y2 second y coordinate
	 * @param x2 second x coordinate
	 * @return
	 */
	public static String concat(int y1, int x1, int y2, int x2) {
		String newString = "";

		newString = newString.concat(String.valueOf(y1));
		newString = newString.concat(String.valueOf(x1));
		newString = newString.concat(String.valueOf(y2));
		newString = newString.concat(String.valueOf(x2));

		return newString;
	}
	
	/**
	 * Method for determining if there are any available spaces around a given block
	 * @param board the gameboard
	 * @return true if there is no available spaces around the given block
	 */
	public static boolean isBlocked(int rows, int columns, int[][] board){

				if (rows == 0 && columns == 0) {
					if (board[rows+1][columns] != 0 && board[rows][columns+1] !=0) {
						return true;
					}
				}
				else if (rows == board.length - 1 && columns == 0) {
					if (board[rows-1][columns] != 0 && board[rows][columns+1] != 0) {
						return true;
					}
					
				} else if (rows == 0 && columns == board[0].length - 1) {
					if (board[rows+1][columns] != 0 && board[rows][columns - 1] !=0) {
						return true;
					}
					
				} else if (rows == board.length - 1 && columns == board[0].length - 1){
					if (board[rows-1][columns] != 0 && board[rows][columns-1] !=0) {
						return true;
					}
					
				} else if (rows == 0) {
					if (board[rows+1][columns] != 0 && board[rows][columns-1] != 0 && board[rows][columns+1] != 0) {
						return true;
					}
					
				} else if (rows == board.length - 1) {
					if (board[rows - 1][columns] != 0  && board[rows][columns-1] != 0 && board[rows][columns+1] != 0) {
						return true;
					}
				} else if (columns == 0) {
					if (board[rows][columns+1] != 0 && board[rows-1][columns] != 0 && board[rows+1][columns]!= 0) {
						return true;
					}
				} else if (columns == board[0].length - 1) {
					if (board[rows][columns-1] != 0 && board[rows-1][columns] != 0 && board[rows+1][columns]!= 0) {
						return true;
					}
				} else {
					if (board[rows][columns+1] != 0 && board[rows][columns-1] != 0 && board[rows-1][columns] != 0 && board[rows+1][columns]!= 0) {
						return true;
					}
				}
		return false;
	}
	
	
}
