package org.gamelink.game;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import org.gamelink.game.GameBoard;
import org.gamelink.game.KalahDisplay;


public class KalahGameBoard extends GameBoard{

/** The inital number of seeds in each cell at the start of the game */
private int initialSeeds;

/** The painter use to paint the game board onto the Jframe to be displayed to the user */
protected Painter painter; 

/**Object representing the JFrame used to display the gameboard*/
KalahDisplay display;

/** Represents whether the game is being run in a tournament senario or not */
boolean tournamentMode;

/********************************************************************
**********************  GAMEBOARD COORDINATES  **********************
********************************************************************/

/** The integer location of the users score on their side of the gameBoard */
private int myScoreX;

/** The integer location of the opponents score on the opposing side of the gameBoard */
private int oppScoreX = 0;

/** The integer representation of the users side of the board on the 2d integer array representation of the gameBoard */
private int mySide = 1;

/** The integer representation of the opponents side of the board on the 2d integer array representation of the gameBoard */
private int oppSide = 0;

/*******************************************************************
**************************  CONSTRUCTORS  **************************
*******************************************************************/

/**
 * Initializes the gameBoard for player 1 using the propeties defined in the Kalah.properties file or the
 * default values passed as parameters by the Kalah class. The created gameBoard is then displayed on the
 * Jframe if specified to do so by the display property.
 * @param   pNum   	   Defines the player number of the user 
 * @param   width      Defines the width of the gameBoard as specified by the Kalah.properties file
 * @param   height	   Defines the height of the gameBoard as specified by the Kalah.properties file
 * @param   display	   Defines whether or not the gameBoard is to be display in the form of a Jframe, specified in the Cram.properties file
 */
protected KalahGameBoard(int pNum, int seeds, int width, boolean beDisplayed, boolean tournamentMode, KalahDisplay kalahDisplay) {
	setPlayerNumber(pNum);
	boardWidth = width;
	boardHeight = 2;
	initialSeeds = seeds;
	myScoreX = boardWidth - 1;
	displayBoard = beDisplayed;
	gameBoard = createNewBoard();
	painter = new Painter();
	this.tournamentMode = tournamentMode;
	if (beDisplayed) {
		this.display = kalahDisplay;
		this.display.getJFrame().getContentPane().add(painter);
		this.display.getJFrame().getContentPane().invalidate();
		this.display.getJFrame().getContentPane().revalidate();
	}
}


/**
 * Creates a new 2d integer representation of the gameBoard with the initial seeds filled in and the stores set to zero.
 * @return                       The new 2d integer array representation of the gameBoard defined by the parameters.
 */
@Override
protected int[][] createNewBoard() {
	int[][] newBoard = new int[boardHeight][boardWidth];
	for (int y = 0; y < boardHeight; y++) {
		for (int x = 0; x < boardWidth; x++) {
			if ((y == oppSide && x == oppScoreX)
							|| (y == mySide && x == myScoreX))
				newBoard[y][x] = 0;
			else newBoard[y][x] = initialSeeds;
		}
	}
	return newBoard;
}

/********************************************************************
*****************************  GETTERS  *****************************
********************************************************************/

/**
* The getBoard method retrieves an altered version of the gameBoard to be used by the users algorithm.
* @return The the current configuration of the gameBoard.
*/
protected int[][] getUserBoard(){
    int[][] board = new int[boardHeight][boardWidth + 1];
    for (int y = 0; y < boardHeight; y ++){
        for (int x = 0; x < boardWidth + 1; x ++){
           if (y == 0){
                if (x == boardWidth) board[y][x] = 0;
                else board[y][x] = gameBoard[y][x];
            } else{
                if (x == 0) board[y][x] = 0;
                else board[y][x] = gameBoard[y][x - 1];
            }
        }
    }
    return board;
}

/**
 * Retreives the current score of player 1
 * @return   The current score of player 1
 */
@Override
protected int getPlayer1Score() {
	if (playerNumber == PLAYER1) return gameBoard[mySide][myScoreX];
	else return gameBoard[oppSide][oppScoreX];
}

/**
 * Retreives the current score of player 2
 * @return   The current score of player 2
 */
@Override
protected int getPlayer2Score() {
	if (playerNumber == PLAYER2) return gameBoard[mySide][myScoreX];
	else return gameBoard[oppSide][oppScoreX];
}

/********************************************************************
*****************************  UTILITY  *****************************
********************************************************************/


/**
 * Adds a score to the specified players score
 * @param player The player to which the score is to be added
 * @param score  The score to be added to the total score
 */
private void addScoreToPlayer(int player, int score){
	int playersSide;
	int playersScoreX;

	if (playerNumber == player) {
		playersSide = mySide;
		playersScoreX = myScoreX;
	} else {
		playersSide = oppSide;
		playersScoreX = oppScoreX;
	}
	gameBoard[playersSide][playersScoreX] += score;
}


/** Scans the gameBoard to determine if the game is over
 * @return   true if the game is over, false otherwise
 */
@Override
protected boolean isGameOver(){
	int seedCounter;

	for (int y = 0; y < boardHeight; y++) {
		seedCounter = 0;
		if (y == 0) for (int x = 1; x < boardWidth; x++) seedCounter += gameBoard[y][x];
		else for (int x = 0; x < boardWidth - 1; x++) seedCounter += gameBoard[y][x];
		if (seedCounter == 0) return true;
	}
	return false;
}

/**
 * Adds all remaining free cells to the specified winning player
 * @param   winningPlayer   The player number of the winning player
 */
@Override
protected void cleanUp(int winningPlayer) {
	winner = winningPlayer;
	gameOverFlag = true;
	for (int y = 0; y < boardHeight; y++) {
		int seedsOnSide = 0;
		int start = 1;
		int end = boardWidth;
		if (y == 1) {
			start = 0;
			end = boardWidth - 1;
		}
		for (int x = start; x < end; x ++) {
			seedsOnSide += gameBoard[y][x];
			gameBoard[y][x] = 0;
		}
		if (winner == PLAYER1) addScoreToPlayer(PLAYER1, seedsOnSide);
		else if (winner == PLAYER2) addScoreToPlayer(PLAYER2, seedsOnSide);
		else {
			if (y == oppSide) addScoreToPlayer(opponentPlayerNumber, seedsOnSide);
			else addScoreToPlayer(playerNumber, seedsOnSide);
		}
	}
	if (winner == VALID_GAME) {
		if (getPlayer1Score() > getPlayer2Score()) winner = PLAYER1;
		else if (getPlayer1Score() < getPlayer2Score()) winner = PLAYER2;
		else winner = TIE;
	}
	if (displayBoard) painter.repaint();
}

/**
 * Converts a move in the form entered by the user to a format usable by the gameBoard class
 * @param  move    The move entered by the user
 * @param  side    The side of the board n which the move is to be applied
 * @return         The new representation of the move which is easier to use by the gameBoard class 
 */
protected int moveToCell(int move, int side){
	if (side == mySide) return move - 1;
	else return boardWidth - move;
}

/**
 * Determines the x coordinate of the next house/store where a seed should be placed
 * @param  y    The current y position on the board
 * @param  x    The current x position on the board
 * @return      The x coordinate of the next cell where the seed should be placed
 */
protected int getNextX(int y, int x){
	if (y == oppSide && x == oppScoreX) return oppScoreX;
	else if (y == mySide && x == myScoreX) return myScoreX;
	else if (y == 0) return --x;
	else return ++x;
}

/**
 * Determines the y coordinate next house/store where a seed should be placed
 * @param  y    The current y position on the board
 * @param  x    The current x position on the board
 * @return      The y coordinate of the next cell where the seed should be placed
 */
protected int getNextY(int y, int x){
	if (y == oppSide && x == oppScoreX) return 1;
	else if (y == mySide && x == myScoreX) return 0;
	else return y;
}

/**
 * Determines the x coordinate of the cell opposite of the current house/store
 * @param      x The x coordinate of the current house/store
 * @param      y The y coordinate of the current house/store
 * @return     The x coordinate of the opposing cell
 */
protected int getOppCell(int x, int y){
	if (y == oppSide)
		return x - 1;
	else return x + 1;
}

/*********************************************************************
*****************************  PRINTERS  *****************************
*********************************************************************/

/** Prints the scores of both players 1 and 2 as well as the winner of the match */
@Override
protected void printScore(){
	System.out.println("***************");
	System.out.println("GAMEOVER");
	System.out.println("Final Score: ");
	System.out.println("Player1: " + getPlayer1Score());
	System.out.println("PLayer2: " + getPlayer2Score());
	if (winner != 3) System.out.println("Player " + winner + " wins");
	else System.out.println("TIE!");
	System.out.println("***************");
}

/*********************************************************************
*****************************  CHECKERS  *****************************
*********************************************************************/

/**
 * Validates and applies a move made by the opponent player
 * @param   opponentMove       The 1 digit String containing the x coordinate of the cell the opponent wishes to move
 * @param   oldBoardAsString   The 2d integer representation of the board prior to the move being applied by the opponent
 * @param   newBoardAsString   The 2d integer represntation of the board after the application of the move to the board
 * @param   whosTurn           The player number of the player to move next
 * @return                     True if the move is valid, false otherwise.
 */
protected boolean validateAndApplyMove(String opponentMove, String oldBoardAsString, String newBoardAsString, String whosTurn) throws NumberFormatException {
	int[][] opponentOldBoard = toArray(oldBoardAsString);
	int[][] opponentNewBoard = toArray(newBoardAsString);
	if (!areBoardsEqual(gameBoard, opponentOldBoard)) return false;
	if (opponentMove.length() != 1) return false;
	int oppMove = Character.getNumericValue(opponentMove.charAt(0));
	if (1 > oppMove || oppMove > boardWidth - 1) return false;
	if (gameBoard[oppSide][moveToCell(oppMove, oppSide)] == 0) return false;
	int[][] testNewBoard = copyBoard(gameBoard);
	int oppTurn = Integer.valueOf(whosTurn);
	boolean moveAgain = applyMove(testNewBoard, oppMove, oppSide);
	if (moveAgain) {
		if (oppTurn != opponentPlayerNumber) return false;
	} else   if (oppTurn == opponentPlayerNumber) return false;
	if (!areBoardsEqual(testNewBoard, opponentNewBoard)) return false;
	gameBoard = opponentNewBoard;
	update(oppTurn);
	if(isGameOver()) gameOverFlag = true;
	return true;
}

/**
 * validates and applies the move made by the player
 * @param  playerMove    The 1 digit String containing the x coordinate of the seeds the player wants to move
 * @return               True if the move is valid, false otherwise.
 */
protected boolean validateAndApplyMove(String playerMove) throws NumberFormatException {
	if (playerMove.length() != 1) return false;
	int move = Character.getNumericValue(playerMove.charAt(0));
	if (1 > move || move > boardWidth - 1) return false;
	if (gameBoard[mySide][moveToCell(move, mySide)] == 0) return false;
	if (!applyMove(gameBoard, move, mySide)) {
		if (whosTurn == 1) update(2);
		else update(1);
	} else update(whosTurn);
	if(isGameOver()) gameOverFlag = true;
	return true;
}

/**
 * Applies a move made by the opponent or the player to the gameBoard 
 * @param  board    The board to which the move is being applied
 * @param  move     The move to be applied to the board
 * @param  side     The side of the board on which the move is being applied
 * @return          True if the game is over as a result of the move, false otherwise.
 */
protected boolean applyMove(int[][] board, int move, int side){
	int y = side;
	int x = moveToCell(move, side);
	int seeds = board[y][x];

	board[y][x] = 0;
	do {
		int copyofY = y;
		y = getNextY(y, x);
		x = getNextX(copyofY, x);
		board[y][x]++;
		seeds--;
	} while (seeds > 0);
	int transferSeeds;
	if (y == mySide && y == side) {
		if (x == myScoreX) return true;
		if (x < myScoreX && board[oppSide][getOppCell(x, mySide)] > 0 && board[y][x] == 1) {
			transferSeeds = board[y][x] + board[oppSide][getOppCell(x, mySide)];
			board[y][x] = 0;
			board[oppSide][getOppCell(x, mySide)] = 0;
			board[mySide][myScoreX] += transferSeeds;
		}
	}
	if (y == oppSide && y == side) {
		if (x == oppScoreX) return true;
		if (oppScoreX < x && board[y][x] == 1 && board[mySide][getOppCell(x, oppSide)] > 0) {
			transferSeeds = board[y][x] + board[mySide][getOppCell(x, oppSide)];
			board[y][x] = 0;
			board[mySide][getOppCell(x, oppSide)] = 0;
			board[oppSide][oppScoreX] += transferSeeds;
		}
	}
	return false;
}

	/**
	 * Updates the whosTurn variable to represnt the new player to move next and update the display to represent the new configuration of the gameBoard
	 * @param newTurn The player number of the new player to move next
	 */
	protected void update(int newTurn){
		whosTurn = newTurn;
		if (displayBoard) painter.repaint();
	}

	protected void updateDisplay() {
		if (displayBoard) painter.repaint();
		if (tournamentMode && displayBoard) display.getJFrame().getContentPane().removeAll();
	}

	// /**
	//  * The display class is used to display the gameBoard on a JFrame
	//  */
	// static class Display {

	// 	/** The width of the JFrame that will display the gameBoard */
	// 	protected static final int FRAMEWIDTH = 550;

	// 	/** The height of the JFrame that will display the gameBoard */
	// 	protected static final int FRAMEHEIGHT = 160;

	// 	/** The frame on which the gameBoard will be displayed */
	// 	protected static JFrame displayFrame;

	// 	/**
	// 	 * Displays the JFrame and adds the painter that is responsible for painting the board on it
	// 	 * @param  painter   The painter that is responsible for painting the gameBoard
	// 	 * @param  pNum      The player number of the user
	// 	 */
	// 	protected static void show(Painter painter, int pNum){
	// 		displayFrame = new JFrame("Player: " + pNum);

	// 		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// 		displayFrame.add(painter);
	// 		displayFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
	// 		displayFrame.setVisible(true);
	// 	}
	// }

	/**
	 * Paints the gameBoard and any other information on the JFram to be dispayed for the user
	 */
	class Painter extends JPanel {

		/** The color used to represent the cells on row 0 of the gameBoard */
		private Color row0Color;

		/** The color used to represent the cells on row 1 of the gameBoard */
		private Color row1Color;

		/** The height of the stores when displayed on the jFrame */
		private int storeHeight;

		/** The height of the houses when displayed on the jFrame */
		private int cellHeight;

		/** The width of all cells when displayed on the JFrame */
		private int width;

		/** The size of the padding between each cell and around the edge of the display */
		private final int PADDING = 10;

		/** The Font size of the text used to display the messages on the display */
		private final int FONTSIZE = 16;

		/** The size of the font used to display the number of seeds in a cell */
		private final int SEEDFONT = 36;

		/** Inintializes the painter that will be responsible for painting the gameBoard on the JFrame by setting the values of the colors used in the painting process */
		private Painter(){
			if (playerNumber == PLAYER1) {
				row1Color = Color.decode("#117cbb");
				row0Color = Color.decode("#e82320");
			} else {
				row1Color = Color.decode("#e82320");
				row0Color = Color.decode("#117cbb");
			}
		}

		/**
		 * Paints the gameBoard and the corresponding status messages on the JFrame
		 * @param g The graphics used to paint the grapics on the JFrame
		 */
		protected void paintComponent(Graphics g) {
			storeHeight = this.getHeight() - (PADDING * 4);
			cellHeight = ((this.getHeight() - (PADDING * (boardHeight + 1))) - (PADDING * 2)) / boardHeight;
			width = (this.getWidth() - (PADDING * (boardWidth + 2))) / (boardWidth + 1);
			paintBoard(g);
			paintMessage(g);
		}

		/**
		 * Paints the current configuration of the gameBoard on the JFrame
		 * @param g The grapics object used to paint the gameBoard
		 */
		protected void paintBoard(Graphics g){
			g.setColor(Color.black);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setFont(new Font("Arial Black", Font.PLAIN, SEEDFONT));
			FontMetrics metric = g.getFontMetrics();
			for (int row = 0; row < boardHeight; row++) {
				for (int col = 0; col < boardWidth; col++) {
					int x = PADDING;
                	int y = PADDING;
					int height = cellHeight;
					g.setColor(row1Color);

					if (row == 0 && col == 0)
						height = storeHeight;
					else if (row == 1 && col == (boardWidth - 1)) {
						height = storeHeight;
						x = ((col + 2) * PADDING) + ((col + 1) * width);
						g.setColor(row0Color);
					} else if (row == 0)
						x = ((col + 1) * PADDING) + (col * width);
					else {
						x = ((col + 2) * PADDING) + ((col + 1) * width);
						y = ((row + 1) * PADDING) + (row * cellHeight);
						g.setColor(row0Color);
						}
				g.fillRect(x, y, width, height);
				String seeds = String.valueOf(gameBoard[row][col]);
                g.setColor(Color.black);
                g.drawString(seeds, x + ((width / 2) - (metric.stringWidth(seeds)) / 2), y + (height / 2) + (metric.getHeight() / 4));
				}
			}
		}

		/**
		 * Paints the current status message on the JFrame
		 * @param g The grapics object used to paint the status message
		 */
		protected void paintMessage(Graphics g){
			int x = PADDING;
			int y = (PADDING * (boardHeight + 1)) + storeHeight;

			g.setFont(new Font("Arial Black", Font.PLAIN, FONTSIZE));
			FontMetrics metric = g.getFontMetrics();
	        if (playerNumber == PLAYER1){
	            if (whosTurn == 1) g.setColor(row0Color);
	            else g.setColor(row1Color);    
	        } else {
	            if (whosTurn == PLAYER1) g.setColor(row1Color);
	            else g.setColor(row0Color);  
	        }

			if (!gameOverFlag) g.drawString("Player" + whosTurn  + "'s turn", x, y);
			else {
				String message;
				if (winner == playerNumber) message = "Gameover: You win!";
				else if (winner == opponentPlayerNumber) message = "Gameover: You lose :(";
				else if (winner == TIE) message = "GameOver: Tie!";
				else message = "GameOver: Illegal code tampering";
				if (playerNumber == PLAYER1) g.setColor(Color.decode("#e82320"));
				else g.setColor(Color.decode("#117cbb"));
				g.drawString(message, x, y);
			}
		}
	}
}
