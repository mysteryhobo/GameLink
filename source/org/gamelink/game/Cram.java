package org.gamelink.game;

import org.gamelink.connect.Link;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import java.util.concurrent.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * The Cram class is responsible for cintrolling all aspects of the game including: The Creating and updating the gameBoard, Sending, recieving and validating moves, and controlling thr flow of the game.
 */
public class Cram {

/** The player number of the user opperating this side of the game. */
private int playerNumber;

/** The player number of the user opperating the other side of the game. */
private int opponentPlayerNumber;

/** The integer representation of player 1s player number. */
private final int PLAYER1 = 1;

/** The integer representation of player 2s player number. */
private final int PLAYER2 = 2;

/** The integer representation of an invalid game. */
private final int ILLEGAL_WINNER = 3;

/** The object representation of this players connection to the opponent player. */
private Link connection = new Link();

/** The object representation of the game board in which the game is being player. */
private CramGameBoard gameBoard;

/** The object representing the JFrame used to display the gameboard */
private CramDisplay display;

/********************************************************************
*******************  PROPERTIES FROM CONFIG FILE  *******************
********************************************************************/

/** The object representation of the game board in which the game is being player. */
private final String CONFIG_FILE = "./Cram.properties";

/** The property stored in the config file that represents the maximum time it may take for a player to make a move (default: 30). */
private int timeLimitProperty = 30;

/** The property stored in the config file that represents the maximum time it may take for a player to make a move (default: 30). */
private final int MAX_TIME_LIMIT = 9999999;

/** The property stored in the config file that represents whether or not the gameBoard is displayed in the form of a jFrame (default: true).*/
private boolean displayBoardProperty = true;

/** The property stored in the config file that represents whether or not the gameBoard is displayed in the form of a jFrame (default: true).*/
private boolean tournamentMode = false;

/** The property stored in the config file that represents the delay (in seconds) added between moves to make games more watchable (default: 3 seconds). */
private int moveDelayProperty = 3;

/** The property stored in the config file that represents the nimber of cell that are initially filled in prior to the start of the game */
private int initialFilledCellsProperty = 2;

/** The property that represents the width of the gameBoard (default: 5). */
private int widthProperty = 5;

/** The property that represents the height of the gameBoard (default: 2). */
private int heightProperty = 5;

/*******************************************************************
**************************  CONSTRUCTORS  **************************
*******************************************************************/

/**
 * Initializes the Cram class by reading the properties from the config file, prompting the user to enter their playerNumber, and connects to the opponent player.
 * @param  useOwnIpAddress Defines whether the user wishes to play against an opponent run on the same computer (Same ip address).
 */
public Cram (boolean useOwnIpAddress){
	this.readConfigFile();
	getUserPlayerNumber();
	connection.connect(useOwnIpAddress);
    if (displayBoardProperty) display = new CramDisplay(playerNumber);
}
/**
 * Initializes the Cram class by reading the properties from the config file, set the playerNumber of the user, and connects to the opponent player.
 * @param  pNum            The playerNumber of the player (player 1 or player 2).
 * @param  useOwnIpAddress A boolean variable representing if the user wishes to play against an opponent on the same system. Used by tournament automator.
 */
public Cram (int pNum, boolean useOwnIpAddress, CramDisplay display){
	tournamentMode = true;
	this.readConfigFile();
	if (displayBoardProperty) this.display = display;
	setPlayerNumber(pNum);
	connection.connect(useOwnIpAddress);
}

public Cram (String p1Address, CramDisplay display){
	tournamentMode = true;
	this.readConfigFile();
	if (displayBoardProperty) this.display = display;
	setPlayerNumber(PLAYER2);
	connection.connectAsPlayer2(p1Address);
}

/********************************************************************
*****************************  GETTERS  *****************************
********************************************************************/

/**
 * Retrieves an 2d integer representation of the  gameBoard for use by the users algorithm.
 * @return An altered 2d integer array representation of the gameBoard with added buffers opposite of each score.
 */
public int[][] getBoard(){
	return gameBoard.getBoard();
}

/**
 * Retrieves the current score of player1 from the gameBoard.
 * @return The current score of player 1.
 */
public int getPlayer1Score() {
	return gameBoard.getPlayer1Score();
}

/**
 * Retrieves the current score of player1 from the gameBoard.
 * @return The current score of player 1.
 */
public int getPlayer2Score() {
	return gameBoard.getPlayer2Score();
}

/**
 * Retrieves the winner of the match, if the match is not finished yet it will return 0
 * @return 0 if game is not finished, 1 if player 1 is the winner, 2 if player 2 is the winner, 3 if the game resulted in a tie, and 4 if the game was invalid (illegal tampering of code).
 */
public int getWinner() {
	return gameBoard.getWinner();
}

/*********************************************************************
*****************************  PRINTERS  *****************************
*********************************************************************/

private void printConfig(){
		System.out.println("*******************************************************");
		System.out.println("CONFIG: Initial Cells Filled = " + initialFilledCellsProperty);
		System.out.println("CONFIG: Board Height = " + heightProperty);
		System.out.println("CONFIG: Board Width = " + widthProperty);
		System.out.println("CONFIG: Time Limit = " + timeLimitProperty);
		System.out.println("CONFIG: Time Delay = " + moveDelayProperty);
		System.out.println("CONFIG: Display Board = " + displayBoardProperty);
		System.out.println("*******************************************************");
}

/*********************************************************************
************************  GAME INITILIZATION  ************************
*********************************************************************/

/** Prompts the user to enter their player number and sets the player Number, opponent player number and the player number of the connection to the entered player Number */
private void getUserPlayerNumber(){
	Scanner pNumInputScanner = new Scanner(System.in);
	int inputNum = 0;

	do {
		System.out.print("Please enter your player number (1 or 2): ");
		inputNum = pNumInputScanner.nextInt();
	} while (inputNum != 1 && inputNum != 2);
	setPlayerNumber(inputNum);
}

/**
 * sets the playerNumber, opponent player number, and connection player number
 * @param pNum      The player number to be set.
 */
private void setPlayerNumber(int pNum){
	playerNumber = pNum;
	connection.setPlayerNumber(pNum);
	if (pNum == PLAYER1)
		opponentPlayerNumber = PLAYER2;
	else
		opponentPlayerNumber = PLAYER1;
}

/** Responsible for reading the config file and retrieving the contained propeties, for use as the settings for the game */
private void readConfigFile(){
	Properties properties = new Properties();
	InputStream inputStream = null;
	try{
		inputStream = new FileInputStream(CONFIG_FILE);
		properties.load(inputStream);
		initialFilledCellsProperty = Integer.parseInt(properties.getProperty("InitialCells"));
		heightProperty = Integer.parseInt(properties.getProperty("BoardHeight"));
		widthProperty = Integer.parseInt(properties.getProperty("BoardWidth"));
		timeLimitProperty = Integer.parseInt(properties.getProperty("MoveTimeLimit"));
		displayBoardProperty = Boolean.parseBoolean(properties.getProperty("DisplayBoard"));
		if (tournamentMode) displayBoardProperty = true;
		moveDelayProperty = Integer.parseInt(properties.getProperty("MoveDelay"));
		if (!tournamentMode) printConfig();
		if (timeLimitProperty == 0)
			timeLimitProperty = MAX_TIME_LIMIT;
	} catch (IOException e) {
		System.out.println("Error: Unable to read config file");
		if (!tournamentMode) printConfig();
	} finally {
		if (inputStream != null) {
			try{
				inputStream.close();
			} catch (IOException ex) {
				System.out.println("Error: Unable to close file Reader");
			}
		}
	}
}

/** Initializes the gameBoard and synchromizes the two games together with a message hand shake to verify game properties. */
private void initializeGame(){
	if (playerNumber == PLAYER1) {
		gameBoard = new CramGameBoard(initialFilledCellsProperty, widthProperty, heightProperty, displayBoardProperty, tournamentMode, display);
		StringBuilder synchronizationMessage = new StringBuilder();
		synchronizationMessage.append(arrayToString(gameBoard.getBoard()) + ":" +
															  initialFilledCellsProperty + ":" +
																						heightProperty + ":" +
																						 widthProperty);
		connection.sendMsg(synchronizationMessage.toString());
		if (!connection.getMsg().equals("valid")) {
			System.out.println("Error: Properties do not match with opponent. Fix them and try again");
			System.exit(1);
		}
	} else {
		boolean validProperties = true;
		String[] synchronizationMessageParts = connection.getMsg().split(":");
		if (!synchronizationMessageParts[1].equals(String.valueOf(initialFilledCellsProperty)))
			validProperties = false;
		if (!synchronizationMessageParts[2].equals(String.valueOf(heightProperty)))
			validProperties = false;
		if (!synchronizationMessageParts[3].equals(String.valueOf(widthProperty)))
			validProperties = false;
		if (validProperties) {
			gameBoard = new CramGameBoard(synchronizationMessageParts[0], widthProperty, heightProperty, displayBoardProperty, tournamentMode, display);
			connection.sendMsg("valid");
		} else {
			connection.sendMsg("invalid");
			System.out.println("Error: Properties do not match with opponent");
			System.exit(1);
		}
	}
}

/**********************************************************************
******************************  UTILITY  *****************8************
**********************************************************************/


/**
 * Converts the 2d integer array representaton of a board to a string to be sent to the opposing player
 * @param  boardAsArray     The 2d integer representation of the board
 * @return                  The string representation of the board
 */ 
private String arrayToString(int[][] boardAsArray) {
	StringBuilder boardAsString = new StringBuilder();
	int rowCounter = 1;
	for (int[] rowAsArray : boardAsArray) {
		boardAsString.append(Arrays.toString(rowAsArray));
		if (rowCounter < heightProperty) boardAsString.append("|");
		rowCounter ++;
	}
	return boardAsString.toString();
}

/**
 * Creates a copy of a 2d integer representation of a board
 * @param  array    The 2d integer array to be copied
 * @return          The 2d integer copy of the array
 */
private int[][] copyArray(int[][] array){
		int[][] copy = new int[heightProperty][widthProperty];
		for (int y = 0; y < heightProperty; y ++){
				for (int x = 0; x < widthProperty; x ++){
						copy[y][x] = array[y][x];
				}
		}
		return copy;
}


/**********************************************************************
*****************************  GAME PLAY  *****************************
**********************************************************************/

/**
 * Resopisble for controlling the flow of the game by calling the players algorithm when needed.
 * @param algo      The class containing the algorithm created by the user.
 */
public void startGame(Class< ? > algo){
	if (!tournamentMode) System.out.println("Starting Game");
	initializeGame();
	boolean start = true;

	try{
		if (playerNumber == 2) {
			boolean gameover = getMove();
			start = !gameover;
		}
		Method algorithm = algo.getMethod("algorithm", Cram.class );
		while (start) {
			String move = runAlgo(algorithm, this);
			if (makeMove(move) == true) break;
			if (getMove() == true) break;
		}
	} catch (NoSuchMethodException me) {
		if (!tournamentMode) System.out.println("Error: Algorithm method does not meet requirements");
		String move = "invalid algo";
		makeMove(move);
	}
	if (!tournamentMode) gameBoard.printScore();
	connection.disconnect();
}

/**********************************************************************
*****************************  GAME MOVE  *****************************
**********************************************************************/

/**
 * Responsible for validating, applying, and sending the users move to the opponent.
 * @param  move     The move made by the player in the form of a string.
 * @return          True if the game is over, false otherwise.
 */
private boolean makeMove(String move){
	if (move.equals("timeout")) {
		if (!tournamentMode) System.out.println("GameOver: Your algorithm exceeded the maximum time to make a move.");
		connection.sendMsg(move);
		gameBoard.cleanUp(opponentPlayerNumber);
		return true;
	} else if (move.equals("error")) {
		if (!tournamentMode) System.out.println("GameOver: Your algorithm encounted an error.");
		connection.sendMsg(move);
		gameBoard.cleanUp(opponentPlayerNumber);
		return true;
	} else if (move.equals("invalid algo")) {
		if (!tournamentMode) System.out.println("GameOver: Your algorithm does meet the required specifications");
		connection.sendMsg(move);
		gameBoard.cleanUp(opponentPlayerNumber);
		return true;
	} else {
		int[][] oldBoard = copyArray(gameBoard.getBoard());
		if (!gameBoard.validateAndApplyMove(move)) {
			if (!tournamentMode) System.out.println("GameOver: Your algorithm entered invalid move: " + move + ".");
			move = "invalid:" + move;
			connection.sendMsg(move);
			gameBoard.cleanUp(opponentPlayerNumber);
			return true;
		} else {
			boolean endgame = gameBoard.isGameOver();
			if (!tournamentMode) System.out.println("You moved: " + move);
			StringBuilder message = new StringBuilder();
			message.append(move + ":"
			+ arrayToString(oldBoard) + ":"
			+ arrayToString(gameBoard.getBoard()) + ":"
			+ endgame);
			connection.sendMsg(message.toString());
			String response = connection.getMsg();
			if (response.equals("invalid")) {
				System.out.println("Error: illegal tampering of code. Both Players lose");
				gameBoard.cleanUp(ILLEGAL_WINNER);
				System.exit(1);
			}
			if (endgame) gameBoard.cleanUp(playerNumber);
			return endgame;
		}
	}
}

/**
 * retrieves the opponent players move, determines if it was a valid move and if the game is over
 * @return True if the game is over, false otherwise.
 */
private boolean getMove(){
	String opponentMove = connection.getMsg();
	if (opponentMove.equals("timeout")) {
		if (!tournamentMode) System.out.println("GameOver: Opponent algorithm exceeded maximum time to make move.");
		gameBoard.cleanUp(playerNumber);
		return true;
	} else if (opponentMove.equals("error")) {
		if (!tournamentMode) System.out.println("GameOver: Opponent algorithm encountered an error.");
		gameBoard.cleanUp(playerNumber);
		return true;
	} else if (opponentMove.equals("invalid algo")) {
		if (!tournamentMode) System.out.println("GameOver: Opponent algorithm does not meet required specifications");
		gameBoard.cleanUp(playerNumber);
		return true;
	} else if (opponentMove.startsWith("invalid")) {
		if (opponentMove.equals("invalid:")){
			if (!tournamentMode) System.out.println("GameOver: Opponent algorithm entered invalid move.");
			gameBoard.cleanUp(playerNumber);
			return true;
		}
		String[] opponentMoveParts = opponentMove.split(":");
		if (!tournamentMode) System.out.println("GameOver: Opponent entered invalid move: " + opponentMoveParts[1]);
		gameBoard.cleanUp(playerNumber);
		return true;
	} else {
		if (opponentMove.length() == 0){
			if (!tournamentMode) System.out.println("GameOver: Opponent algorithm entered invalid move.");
			gameBoard.cleanUp(playerNumber);
			return true;
		}
		String[] opponentMoveParts = opponentMove.split(":");
		if (gameBoard.validateAndApplyMove(opponentMoveParts[0], opponentMoveParts[1], opponentMoveParts[2])) {
			boolean gameOver = gameBoard.isGameOver();
			if (gameOver == Boolean.parseBoolean(opponentMoveParts[3])) connection.sendMsg("valid");
			else connection.sendMsg("invalid");
			if (gameOver) gameBoard.cleanUp(opponentPlayerNumber);
			return gameOver;
		} else {
			connection.sendMsg("invalid");
			System.out.println("Error: illegal tampering of code: Both players Lose");
			gameBoard.cleanUp(ILLEGAL_WINNER);
			System.exit(1);
			return true;
		}
	}
}

/**
* The runAlgo method utalizes the CramAlgoInvoker Class to call the user-created algorithm. This algorithm returns the players move in the form of a String.
* @param The method representation of the user-created algorithm
* @param The instance of the game of Cram currently being played
* @return The move returned by the users algorithm in the form of a String
* @see CramAlgoInvoker
*/
private static String runAlgo(Method method, Cram game){
	String move = "";
	ExecutorService executor = Executors.newSingleThreadExecutor();
	SimpleTimeLimiter timeLimiter = new SimpleTimeLimiter(executor);

	try {
		Thread.sleep(game.moveDelayProperty * 1000);
	} catch (InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	try{
		move = timeLimiter.callWithTimeout(new CramAlgoInvoker(method, game), game.timeLimitProperty, TimeUnit.SECONDS, true);
	} catch (UncheckedTimeoutException ute) {
		System.out.println("Algorithm timed out");
		move = "timeout";
	} catch (Exception e) {
		System.out.println("Error: Exception thrown in algorithm");
		move = "error";
	}
	executor.shutdownNow();
	if (move == null) return "timeout";
	return move;
}
}

/**
* The CramAlgoInvoker class is used to invoke the players algorithm with a specified time limit
*/
class CramAlgoInvoker implements Callable<String> {
protected Method algorithm;
protected Cram game;

/**
 * The CramAlgoInvoker constructor creates an instance of the algorithm invoker to correspond with the
 * current state of Cram game
 * @param The players algorithm retrieved from the user-made algorithm class
 * @param The current instance of the Cram game.
 */
public CramAlgoInvoker(Method method, Cram cram){
	algorithm = method;
	game = cram;
}
/**
 * The call method invokes the user-create algorithm to retrieve the players move.
 * @return The move returned by the users algorithm in the form of a String.
 * @throws Exception if the user-made algorithm encounters an error.
 */
@Override
public String call() throws Exception {
	String move = "";

	try{
		move = (String) algorithm.invoke(new Object[] { null }, game);
	} catch (IllegalAccessException iae) {
		System.out.println("Error: Denied access to method");
		move = "error";
	} catch (InvocationTargetException ite) {
		System.out.println("Error: Exception thrown in algorithm");
		System.out.println("*********************************************************");
		ite.printStackTrace();
		System.out.println("**********************************************************");
		move = "error";
	}
	return move;
}
}
