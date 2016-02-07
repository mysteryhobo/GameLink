package org.gamelink.game;

import org.gamelink.connect.Link;
import java.util.Scanner;
import java.util.Arrays;
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
 * The Kalah class is responsible for cintrolling all aspects of the game including: The Creating and updating the gameBoard, Sending, recieving and validating moves, and controlling thr flow of the game.
 */
public class Kalah{

    /** The player number of the user opperating this side of the game. */
    private int playerNumber;

    /** The player number of the user opperating the other side of the game. */
    private int opponentPlayerNumber;

    /** The integer representation of player 1s player number. */
    private final int PLAYER1 = 1;

    /** The integer representation of player 2s player number. */
    private final int PLAYER2 = 2;

    /** The integer representation of a completed completed game. */
    private final int VALID_GAME = 3;

    /** The integer representation of an invalid game. */
    private final int INVALID_GAME = 4;

    /** The object representation of this players connection to the opponent player. */
    private Link connection = new Link();

    /** The object representation of the game board in which the game is being player. */
    private KalahGameBoard gameBoard;

    /** The object representing the JFrame used to display the gameboard */
    private KalahDisplay display;


    /********************************************************************
    *******************  PROPERTIES FROM CONFIG FILE  *******************
    ********************************************************************/

    /** The dynamic file path of the configuration folder where the game properties are store. */
    private final String CONFIG_FILE = "./Kalah.properties";

    /** The property stored in the config file that represents the maximum time it may take for a player to make a move (default: 30). */
    private int timeLimitProperty = 30;

    /** The value that the time limit property is set to to represent an infinite amount of time. */
    private final int MAX_TIME_LIMIT = 9999999;

    /** The property stored in the config file that represents whether or not the gameBoard is displayed in the form of a jFrame (default: true).*/
    private boolean displayBoardProperty = true;

    /** The property that represents whether or not messages regarding moves are printed to the standard output. */
    private boolean tournamentMode = false;

    /** The property stored in the config file that represents the delay (in seconds) added between moves to make games more watchable (default: 3 seconds). */
    private int moveDelayProperty = 3;

    /** The property stored in the config file that represents the initial number of seeds in each house (default: 3). */
    private int seedsPerCellProperty = 3;

    /** The property that represents the width of the gameBoard, deived from the config file houses property (default: 7). */
    private int widthProperty = 7;

    /** The property that represents the height of the gameBoard (always 2). */
    private int heightProperty = 2;

    /*******************************************************************
    **************************  CONSTRUCTORS  **************************
    *******************************************************************/

    /**
     * Initializes the Kalah class by reading the properties from the config file, prompting the user to enter their playerNumber, and connects to the opponent player.
     * @param  useOwnIpAddress Defines whether the user wishes to play against an opponent run on the same computer (Same ip address).
     */
    public Kalah(boolean useOwnIpAddress){
        this.readConfigFile();
        getUserPlayerNumber();
        connection.connect(useOwnIpAddress);
        if (displayBoardProperty) display = new KalahDisplay(playerNumber);
    }

    /**
     * Initializes the Kalah class by reading the properties from the config file, set the playerNumber of the user, and connects to the opponent player.
     * @param  pNum            The playerNumber of the player (player 1 or player 2).
     * @param  useOwnIpAddress A boolean variable representing if the user wishes to play against an opponent on the same system. Used by tournament automator.
     */
    public Kalah(int pNum, boolean useOwnIpAddress, KalahDisplay display){
        tournamentMode = true;
        this.readConfigFile();
        if (displayBoardProperty) this.display = display;
        setPlayerNumber(pNum);
        connection.connect(useOwnIpAddress);
    }

    /** Variant of the constuctor used when tournament is distribute between two systems p1Address is the ip address of the first system */
    public Kalah (String p1Address, KalahDisplay display){
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
     * Retrieves an altered version of the gameBoard for use by the users algorithm.
     * @return An altered 2d integer array representation of the gameBoard with added buffers opposite of each score.
     */
    public int[][] getBoard(){
      return gameBoard.getUserBoard();
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

    /** Prints the properties retrieved from the config file, or the default values if no config file was found */
    private void printConfig(){
        System.out.println("*******************************************************");
        System.out.println("CONFIG: Seeds = " + seedsPerCellProperty);
        System.out.println("CONFIG: houses = " + (widthProperty - 1));
        System.out.println("CONFIG: TimeLimit = " + timeLimitProperty);
        System.out.println("CONFIG: Display Board = " + displayBoardProperty);
        System.out.println("CONFIG: Time Delay = " + moveDelayProperty);
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
        if (playerNumber == PLAYER1) opponentPlayerNumber = PLAYER2;
        else opponentPlayerNumber = PLAYER1;
    }

    /** Responsible for reading the config file and retrieving the contained propeties, for use as the settings for the game */
    private void readConfigFile(){
        Properties properties = new Properties();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(CONFIG_FILE);
            properties.load(inputStream);
            seedsPerCellProperty = Integer.parseInt(properties.getProperty("Seeds"));
            int housesPerPlayerProperty = Integer.parseInt(properties.getProperty("Houses"));
            timeLimitProperty = Integer.parseInt(properties.getProperty("MoveTimeLimit"));
            displayBoardProperty = Boolean.parseBoolean(properties.getProperty("DisplayBoard"));
            if (tournamentMode) displayBoardProperty = true;
            moveDelayProperty = Integer.parseInt(properties.getProperty("MoveDelay"));
            widthProperty = housesPerPlayerProperty + 1;
            if (!tournamentMode) printConfig();
            if (timeLimitProperty == 0) timeLimitProperty = MAX_TIME_LIMIT;
        } catch (IOException e){
            System.out.println("Error: Unable to read config file");
            if (!tournamentMode) printConfig();
        } finally {
            if (inputStream != null){
                try{
                    inputStream.close();
                } catch (IOException ex){
                    System.out.println("Error: Unable to close file Reader");
                }
            }
        }
    }

    /** Initializes the gameBoard and synchromizes the two games together with a message hand shake to verify game properties. */
    private void initializeGame() {
      if (playerNumber == PLAYER1) {
        gameBoard = new KalahGameBoard(playerNumber, seedsPerCellProperty, widthProperty, displayBoardProperty, tournamentMode, display);
        StringBuilder synchronizationMessage = new StringBuilder();
        synchronizationMessage.append(seedsPerCellProperty + ":"
                                      + widthProperty + ":"
                                      + heightProperty);

        connection.sendMsg(synchronizationMessage.toString());
        if (!connection.getMsg().equals("valid")) {
          System.out.println("Error: Properties do not match with opponent. Fix them and try again");
          System.exit(1);
        }
      } else {
        boolean validProperties = true;
        String[] synchronizationMessageParts = connection.getMsg().split(":");
        if (!synchronizationMessageParts[0].equals(String.valueOf(seedsPerCellProperty)))
                validProperties = false;
            if (!synchronizationMessageParts[1].equals(String.valueOf(widthProperty)))
                validProperties = false;
            if (!synchronizationMessageParts[2].equals(String.valueOf(heightProperty)))
                validProperties = false;
          if (validProperties) {
                gameBoard = new KalahGameBoard(playerNumber, seedsPerCellProperty, widthProperty, displayBoardProperty, tournamentMode, display);
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
     * determines if it is the player's turn
     * @return true if it is their turn, false otherwise
     */
    private boolean isMyTurn(){
        if (gameBoard.getTurn() == playerNumber) return true;
        else return false;
    }

    /**
     * rotates the board 180 degrees to orient it for the opposite player
     * @param  board    The board to be rotated.
     * @return          The rotated board
     */
    private int[][] flipBoard(int[][] board){
            int[][] array = new int[heightProperty][widthProperty];
            for (int y = 0; y < heightProperty; y ++){
                    for (int x = 0; x < widthProperty; x ++){
                            array[y][x] = board[(heightProperty - 1) - y][(widthProperty - 1) - x];
                    }
            }
            return array;
    }

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
    public void startGame(Class<?> algo){
      if (!tournamentMode) System.out.println("Starting Game");
      initializeGame();
      boolean endGame = false;
      try{
          Method algorithm = algo.getMethod("algorithm", Kalah.class);
          while (true){
              while (isMyTurn()){
                  if(makeMove(runAlgo(algorithm, this))){
                      endGame = true;
                      break;
                  }
              }
              if (endGame) break;
              while (!isMyTurn()){
                  if(getMove()){
                      endGame = true;
                      break;
                  }
              }
              if (endGame) break;
          }
      } catch (NoSuchMethodException me){
          System.out.println("Error: Method does not exist");
      }
        if (!tournamentMode) gameBoard.printScore();
        connection.disconnect();
    }

    /**
     * Sends the players last move to the opponent and clears the board placing remaining seeds in the opponent players store. Used when an invalid move is entered by the player
     * @param  move           The invalid move entered by the player or the type of error returned by calling the users algorithm
     * @param  winningPnum    The player number of the winning player.
     */
    private void endGame(String move, int winningPnum) {
      connection.sendMsg(move);
      gameBoard.cleanUp(winningPnum);
      gameBoard.updateDisplay();
    }

    /**********************************************************************
    *****************************  GAME MOVE  *****************************
    **********************************************************************/

    /**
     * Responsible for validating, applying, and sending the users move to the opponent.
     * @param  move     The move made by the player in the form of a string.
     * @return          True if the game is over, false otherwise.
     */
    private boolean makeMove(String move) {
      if (move.equals("timeout")) {
        if (!tournamentMode) System.out.println("GameOver: Your algorithm exceeded the maximum time to make a move.");
    		endGame(move, opponentPlayerNumber);
            return true;
      } else if (move.equals("error")) {
    		if (!tournamentMode) System.out.println("GameOver: Your algorithm encounted an error.");
    		endGame(move, opponentPlayerNumber);
            return true;
      } else {
  		  int[][] oldBoard = copyArray(gameBoard.getBoard());
  		  if (!gameBoard.validateAndApplyMove(move)) {
  			if (!tournamentMode) System.out.println("GameOver: Your algorithm entered invalid move: " + move + ".");
        move = "invalid:" + move;
  			endGame(move, opponentPlayerNumber);
            return true;
  		} else {
  			if (!tournamentMode) System.out.println("You moved: " + move);
  			StringBuilder message = new StringBuilder();
  			message.append(move + ":"
  			   + arrayToString(flipBoard(oldBoard)) + ":"
  			   + arrayToString(flipBoard(gameBoard.getBoard())) + ":"
               + gameBoard.getTurn() + ":"
               + gameBoard.getGameOverFlag());
            connection.sendMsg(message.toString());
            if (connection.getMsg().equals("invalid")) {
  				System.out.println("Error: illegal tampering of code. Both Players lose");
  				gameBoard.cleanUp(INVALID_GAME);
  				System.exit(1);
  			}
  			if (gameBoard.getGameOverFlag()) gameBoard.cleanUp(VALID_GAME);
  			return gameBoard.getGameOverFlag();
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
      } else if (opponentMove.startsWith("invalid")) {
          if (!tournamentMode) System.out.println("GameOver: Opponent entered an invalid move");
          gameBoard.cleanUp(playerNumber);
          return true;
      } else {
          String[] opponentMoveParts = opponentMove.split(":");
              if (!tournamentMode) System.out.println("Opponent moved: " + opponentMoveParts[0]);
          if (gameBoard.validateAndApplyMove(opponentMoveParts[0], opponentMoveParts[1], opponentMoveParts[2], opponentMoveParts[3])) {
              boolean gameOver = gameBoard.getGameOverFlag();
              if (gameOver == Boolean.parseBoolean(opponentMoveParts[4])) {
                  connection.sendMsg("valid");
              } else {
                  connection.sendMsg("invalid");
              }
              if (gameOver) gameBoard.cleanUp(VALID_GAME);
              return gameOver;
          } else {
              connection.sendMsg("invalid");
              System.out.println("Error: illegal tampering of code: Both players Lose");
              gameBoard.cleanUp(INVALID_GAME);
              System.exit(1);
              return true;
          }
      }
  }

    /**********************************************************************
    *************************  CALLING ALGORITHM  *************************
    **********************************************************************/


    /**
    * The runAlgo method utalizes the KalahAlgoInvoker Class to call the user-created algorithm. This algorithm returns the players move in the form of a String.
    * @param The method representation of the user-created algorithm
    * @param The instance of the game of Kalah currently being played
    * @return The move returned by the users algorithm in the form of a String
    * @see KalahAlgoInvoker
    */
    private static String runAlgo(Method method, Kalah game){
        String move = "";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        SimpleTimeLimiter timeLimiter = new SimpleTimeLimiter(executor);
        try {
            Thread.sleep(game.moveDelayProperty * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        try{
            move = timeLimiter.callWithTimeout(new KalahAlgoInvoker(method, game), game.timeLimitProperty, TimeUnit.SECONDS, true);
        } catch (UncheckedTimeoutException ute){
            System.out.println("Algorithm timed out");
            move = "timeout";
        } catch (Exception e){
            System.out.println("Error: Exception thrown in algorithm");
            move = "error";
        }
        executor.shutdownNow();
        if (move == null) return "timeout";
        return move;
    }
}

/**
* The KalahAlgoInvoker class is used to invoke the players algorithm with a specified time limit
*/
class KalahAlgoInvoker implements Callable<String> {
    protected Method algorithm;
    protected Kalah game;

    /**
    * The KalahAlgoInvoker constructor creates an instance of the algorithm invoker to correspond with the current state of Kalah game
    * @param The players algorithm retrieved from the user-made algorithm class
    * @param The current instance of the Kalah game.
    */
    public KalahAlgoInvoker(Method method, Kalah kalah){
        algorithm = method;
        game = kalah;
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
            move = (String) algorithm.invoke(new Object[] {null}, game);
        } catch (IllegalAccessException iae){
            System.out.println("Error: Denied access to method");
        } catch (InvocationTargetException ite){
            System.out.println("Error: Invalid target method");
            System.out.println("*********************************************************");
            ite.printStackTrace();
            System.out.println("**********************************************************");
        }
        return move;
    }
}