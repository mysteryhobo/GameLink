package org.gamelink.game;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import org.gamelink.game.GameBoard;
import org.gamelink.game.CramDisplay;


class CramGameBoard extends GameBoard {

/** The integer representation of the cells filled in prior to the start of the game */
private final int INITIAL_FILLED_CELL = 99;

/** The number of cells to be filled prior to the start of the game */
private int initialFilledCells;

/** The painter use to paint the game board onto the Jframe to be displayed to the user */
protected Painter painter;

/**Object representing the JFrame used to display the gameboard*/
CramDisplay display;

/** Represents whether the game is being run in a tournament senario or not */
boolean tournamentMode;

/*******************************************************************
**************************  CONSTRUCTORS  **************************
*******************************************************************/

/**
 * Initializes the gameBoard for player 1 using the propeties defined in the Cram.properties file or the
 * default values passed as parameters by the Cram class. The created gameBoard is then displayed on the
 * Jframe if specified to do so by the display property.
 * @param   initialFilledCells   Defines the number of cells to be filled prior to the start of the game as specified by the Cram.properties file
 * @param   width                Defines the width of the gameBoard as specified by the Cram.properties file
 * @param   height						   Defines the height of the gameBoard as specified by the Cram.properties file
 * @param   display						   Defines whether or not the gameBoard is to be display in the form of a Jframe, specified in the Cram.properties file
 */
protected CramGameBoard(int initCells, int width, int height, boolean displayBoard, boolean tournamentMode, CramDisplay cramDisplay) {
	setPlayerNumber(PLAYER1);
	boardHeight = height;
	boardWidth = width;
	initialFilledCells = initCells;
	this.displayBoard = displayBoard;
	gameBoard = createNewBoard();
	painter = new Painter();
	this.tournamentMode = tournamentMode;
	if (displayBoard) {
		this.display = cramDisplay;
		this.display.getJFrame().getContentPane().add(painter);
		this.display.getJFrame().getContentPane().invalidate();
		this.display.getJFrame().getContentPane().revalidate();
	}
}

/**
 * Initializes the gameBoard for player 2 using the 2d integer array created by player 1. The properties of this gameBoard are compared to the
 * properties defined in the Cram.properties file (or the defaults) to ensure equality. The created gameBoard is then displayed on the Jframe
 * if specified to do so by the display property.
 * @param   boardAsString	  Defines the board created by player 1 and then sent to player 2 to ensure board synchronisity between the two boards
 * @param   width					  Defines the width of the gameBoard create by player 1
 * @param   height				  Defines the height of the gameBoard created by player 1
 * @param   display				  Defines whether or not the gameBoard is to be display in the form of a Jframe, specified in the Cram.properties file
 */
protected CramGameBoard(String boardAsString, int width, int height, boolean beDisplayed, boolean tournamentMode, CramDisplay cramDisplay) {
	setPlayerNumber(PLAYER2);
	boardHeight = height;
	boardWidth = width;
	this.displayBoard = beDisplayed;
	gameBoard = toArray(boardAsString);
	painter = new Painter();
	this.tournamentMode = tournamentMode;
	if (tournamentMode) displayBoard = true;
	if (displayBoard) {
		this.display = cramDisplay;
		this.display.getJFrame().getContentPane().add(painter);
		this.display.getJFrame().getContentPane().invalidate();
		this.display.getJFrame().getContentPane().revalidate();
	}
}

/**
 * Creates a new 2d integer representation of the gameBoard with the initial random cells filled with the INITIAL_FILLED_CELL value (99)
 * @param   initialFilledCells   Defines the number of cells to be filled prior to the start of the game as specified by the Cram.properties file
 * @param   width                Defines the width of the gameBoard as specified by the Cram.properties file
 * @param   height						   Defines the height of the gameBoard as specified by the Cram.properties file
 * @return                       The new 2d integer array representation of the gameBoard defined by the parameters
 */
@Override
protected int[][] createNewBoard() {
	int[][] newBoard = new int[boardHeight][boardWidth];
	for (int i = 0; i < initialFilledCells; i++) {
		int y = (int) (Math.random() * boardHeight);
		int x = (int) (Math.random() * boardWidth);
		setCell(newBoard, y, x, INITIAL_FILLED_CELL);
	}
	return newBoard;
}


/********************************************************************
*****************************  SETTERS  *****************************
********************************************************************/



/** Sets an individual cell of the gameBoard to a value
 * @param   board   The board with the cell to be changed
 * @param   y       The y oordinate of the cell to be changed
 * @param   x       The x coordinate of the cell to be changed
 * @param   value   The new value to be placed in the cell
 */
private void setCell(int[][] board, int y, int x, int value){
	board[y][x] = value;
}



/*********************************************************************
*****************************  PRINTERS  *****************************
*********************************************************************/


/*********************************************************************
*****************************  CHECKERS  *****************************
*********************************************************************/

/** Scans the gameBoard to determine if the game is over
 * @return   true if the game is over, false otherwise
 */
@Override
protected boolean isGameOver(){
	for (int x = 0; x < boardWidth; x++) {
		for (int y = 0; y < boardHeight; y++) {
			if (x - 1 >= 0 && gameBoard[y][x] == 0 && gameBoard[y][x - 1] == 0) return false;
			if (x + 1 < boardWidth && gameBoard[y][x] == 0 && gameBoard[y][x + 1] == 0) return false;
			if (y - 1 >= 0 && gameBoard[y][x] == 0 && gameBoard[y - 1][x] == 0) return false;
			if (y + 1 < boardHeight && gameBoard[y][x] == 0 && gameBoard[y + 1][x] == 0) return false;
		}
	}
	return true;
}

/**
 * Adds all remaining free cells to the specified winning player
 * @param   winningPlayer   The player number of the winning player
 */
@Override
protected void cleanUp(int winningPlayer) {
	winner = winningPlayer;
	gameOverFlag = true;
	if (winner != 3){
		for (int y = 0; y < boardHeight; y ++){
			for (int x = 0; x < boardWidth; x ++){
				if (gameBoard[y][x] == 0) {
					if (winner == PLAYER1) player1Score ++;
					else player2Score ++;
				}
			}
		}
	}
	if (displayBoard) painter.repaint();
}

/**
 * Adds two points to a players score
 * @param   pNum   The player number of the player whose score will have the points added to
 */
private void addMoveToScore(int pNum){
  if (pNum == PLAYER1) player1Score += 2;
  else player2Score += 2;
}

/**
 * Validates and applies a move made by the opponent player
 * @param   opponentMove       The 4 digit String containing the 4 coordinates of the move in the form y1, x1, y2, x2
 * @param   oldBoardAsString   The 2d integer representation of the board prior to the move being applied by the opponent
 * @param   newBoardAsString   The 2d integer represntation of the board after the application of the move to the board
 * @return                     True if the move is valid, false otherwise.
 */
protected boolean validateAndApplyMove(String opponentMove, String oldBoardAsString, String newBoardAsString) throws NumberFormatException {
	int[][] opponentOldBoard = toArray(oldBoardAsString);
	int[][] opponentNewBoard = toArray(newBoardAsString);
	if (!areBoardsEqual(gameBoard, opponentOldBoard)) return false;
	if (opponentMove.length() != 4) return false;
	int y1 = Character.getNumericValue(opponentMove.charAt(0));
	int x1 = Character.getNumericValue(opponentMove.charAt(1));
	int y2 = Character.getNumericValue(opponentMove.charAt(2));
	int x2 = Character.getNumericValue(opponentMove.charAt(3));
	if (y1 == y2 && x1 == x2) return false;
	if ((Math.abs(y1 - y2) + Math.abs(x1 - x2)) >= 2) return false;
	if (gameBoard[y1][x1] != 0 || gameBoard[y2][x2] != 0) return false;
	int[][] verificationBoard = copyBoard(gameBoard);
	setCell(verificationBoard, y1, x1, opponentPlayerNumber);
	setCell(verificationBoard, y2, x2, opponentPlayerNumber);
	if (!areBoardsEqual(verificationBoard, opponentNewBoard)) return false;
	setBoard(opponentNewBoard);
    addMoveToScore(opponentPlayerNumber);
	switchTurn();
	if (displayBoard) painter.repaint();
	return true;
}

/**
 * validates and applies the move made by the player
 * @param  playerMove The 4 digit String containing the 4 coordinates of the move in the form y1, x1, y2, x2
 * @return            True if the move is valid, false otherwise.
 */
protected boolean validateAndApplyMove(String playerMove) throws NumberFormatException {
	if (playerMove.length() != 4) return false;
	int y1 = Character.getNumericValue(playerMove.charAt(0));
	int x1 = Character.getNumericValue(playerMove.charAt(1));
	int y2 = Character.getNumericValue(playerMove.charAt(2));
	int x2 = Character.getNumericValue(playerMove.charAt(3));
	if (y1 == y2 && x1 == x2) return false;
	if (Math.abs(y1 - y2) + Math.abs(x1 - x2) >= 2) return false;
	if (gameBoard[y1][x1] != 0 || gameBoard[y2][x2] != 0) return false;
	setCell(gameBoard, y1, x1, playerNumber);
	setCell(gameBoard, y2, x2, playerNumber);
  	addMoveToScore(playerNumber);
	switchTurn();
	if (displayBoard) painter.repaint();
	return true;
}

// /**
//  * The display class is used to display the gameBoard on a JFrame
//  */
//  static class Display {

// 	/** The width of the JFrame that will display the gameBoard */
// 	protected static final int FRAMEWIDTH = 500;

// 	/** The height of the JFrame that will display the gameBoard */
// 	protected static final int FRAMEHEIGHT = 320;

// 	/**
// 	 * Displays the JFrame and adds the painter that is responsible for painting the board on it
// 	 * @param  painter   The painter that is responsible for painting the gameBoard
// 	 * @param  pNum      The player number of the user
// 	 */
// 	protected static void show(Painter painter, int pNum){
// 		JFrame displayFrame = new JFrame("Player: " + pNum);
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

	/** The color used to represent player 1 cells on the gameBoard */
	Color player1Color = Color.decode("#e82320");

	/** The color used to represent player 2 cells on the gameBoard */
	Color player2Color = Color.decode("#117cbb");

	/** The color used to represent an empty cell on the gameBoard */
	Color freeCellColor = Color.decode("#f4f5f8");

	/** The color used to represent an initialy filled cell */
	Color randomCellColor = Color.decode("#3A3A3C");

	/** The size of the padding between each cell and around the edge of the display */
	protected final int PADDING = 10;

	/** The Font size of the text used to display the messages on the display */
	protected final int FONTSIZE = 16;

	/**
	 * Paints the gameBoard and the corresponding messages on the JFrame
	 * @param g The graphics used to paint the grapics on the JFrame
	 */
	protected void paintComponent(Graphics g) {
		int cellHeight = ((this.getHeight() - (PADDING * (boardHeight + 1))) - 20) / boardHeight;
		int cellWidth = (this.getWidth() - (PADDING * (boardWidth + 1))) / boardWidth;
		paintBoard(g, cellWidth, cellHeight);
		paintMessage(g, cellHeight);
	}

	/**
	 * Paints the current configuration of the gameBoard on the JFrame
	 * @param  The graphics used to paint the graphics on the display
	 * @param  The width of each cell on the gameBoard on the display
	 * @param  The height of each cell on the gameBoard on the display
	 */
	private void paintBoard(Graphics g, int cellWidth, int cellHeight) {
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int row = 0; row < boardHeight; row++) {
			for (int col = 0; col < boardWidth; col++) {
				if (gameBoard[row][col] == 1) g.setColor(player1Color);
				else if (gameBoard[row][col] == 2) g.setColor(player2Color);
				else if (gameBoard[row][col] == 0) g.setColor(freeCellColor);
				else g.setColor(randomCellColor);
				int x = ((col + 1) * PADDING) + (col * cellWidth);
				int y = ((row + 1) * PADDING) + (row * cellHeight);
				g.fillRect(x, y, cellWidth, cellHeight);
			}
		}
	}

	/**
	 * Paints a message on the bottom of the JFrame
	 * @param  The graphics used to paint the message
	 * @param  The height of the cells painted on the gameBoard
	 */
	private void paintMessage(Graphics g, int cellHeight) {
		String displayMessage;
		int x = PADDING;
		int y = (PADDING * (boardHeight + 1)) + (cellHeight * boardHeight) + PADDING;
		g.setFont(new Font("Arial Black", Font.PLAIN, FONTSIZE));
		if (!gameOverFlag) {
			setMessageColor(whosTurn, g);
			g.drawString("Player " + whosTurn + "'s turn", x, y);
		} else {
			if (winner == playerNumber) displayMessage = "Gameover: You win!";
			else displayMessage = "Gameover: You lose :(";
			setMessageColor(playerNumber, g);
			g.drawString(displayMessage, x, y);
			FontMetrics metrics = g.getFontMetrics();
			String message1 = "Player 1: " + player1Score;
			String message2 = "Player 2: " + player2Score;
			g.setColor(player1Color);
			g.drawString(message1, this.getWidth() - (metrics.stringWidth(message2) * 2) - ((PADDING * 2) + 5), y);
			g.setColor(player2Color);
			g.drawString(message2, this.getWidth() - metrics.stringWidth(message2) - PADDING, y);
		}
	}

	/**
	 * sets the current color to match the corresponding players color
	 * @param  The player number of the player
	 * @param  The grapics used to change the color.
	 */
	private void setMessageColor(int pNum, Graphics g){
		if (pNum == 1) g.setColor(player1Color);
		else g.setColor(player2Color);
	}
}
}
