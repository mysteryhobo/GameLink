/**
* Group on Blackboard: Group13
* TeamName: JasonStuart
* Members: Jason Runzer 100520993, 100522058
* Date: 11/22/2015
*/

package algorithms;

import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
import java.util.ArrayList;

public class JasonStuart extends Algo { // Replace TeamName
  private static String teamName = "JasonStuart"; // Replace TeamName

  public static String getTeamName() {
    return teamName;
  }

  public static void main(String[] args) {
    Cram game = new Cram(false);
    game.startGame(JasonStuart.class); // Replace TeamName
  }

  public static String algorithm(Cram game) {

    /************************************************
     ************ PLACE ALGORITHM HERE ************
     ************************************************/

    // We store the current board each time it is our turn
    int[][] board = game.getBoard();
    // Create an arrayList to store all the move that are possible in the
    // board
    ArrayList<String> moves;

    // Initalize variable
    int count = 0;

    // Calls the function findMoves with the parameter of the current board
    // and sends the results to the moves array
    moves = findMoves(board);

    // Creates an arrayList to store the number of losses each move will get
    ArrayList<Integer> loss = new ArrayList<Integer>();

    // Checks to see if there are anymore moves left in the array
    System.out.println("\n\nNumber of moves: " + moves.size());

    /*
     * We will only run our algorithm if there are less then or equal to 21 possible
     * moves on the board, the reason for this is becasue of the computer
     * speed we would not be able to calculate where to play if the number
     * of moves is greater then 21. Also if the board size is greater than 5,
	 * a linear equation is used to see if the move can be calculated in time.
     */
    int sum = board.length + board[0].length;
    if (moves.size() > 21 || ((sum > 10) && (moves.size() >= -1 * sum + 30))) {
		
      String bestMove = moves.get(0);
      int bestMoveCount = moves.size();

      // Runs for the number of totalMoves in the board
      for (int i = 0; i < moves.size(); i++) {
        

        // The purpose of this sesction of code is the play a move and
        // then after that move has been played check to see how many
        // moves are then playable after making your move
        // So the whole point is to reduce the number of moves in the
        // board
        board[moves.get(i).charAt(0) - 48][moves.get(i)
            .charAt(1) - 48] = 1;
        board[moves.get(i).charAt(2) - 48][moves.get(i).charAt(3) - 48] = 1;
        ArrayList<String> holder = findMoves(board);
        board[moves.get(i).charAt(0) - 48][moves.get(i)
            .charAt(1) - 48] = 0;
        board[moves.get(i).charAt(2) - 48][moves.get(i).charAt(3) - 48] = 0;

        int size = holder.size();
        // If the number of moves is less then the bestMoveCount and j
        // is greater then 0 enter the if statement
        if (size < bestMoveCount && size > 0) {
          // Update the bestMoveCount to the size of the arraylist
          bestMoveCount = size;
          // Set the move to play to the index you just check from the
          // moves arrayList
          bestMove = moves.get(size);
          System.out
              .println("Thikning about this move: "
                  + bestMove);
        }

      }
      // Returns the move that reduces the number of moves in the board
      // the most
      return bestMove;
    }

    // While there are still moves to check this while loop will keep on
    // executing
    
    while (moves.size() > count) {
      System.out.println("Testing out Move: " + moves.get(count));

      // Sets one of the possible moves in the moves array equal to 1
      // (meaning the spot can no longer be played in)
      board[moves.get(count).charAt(0) - 48][moves.get(count)
          .charAt(1) - 48] = 1;
      board[moves.get(count).charAt(2) - 48][moves.get(count).charAt(3) - 48] = 1;

      // Enters the calculateLoss function and passes in the board after
      // setting the moves made above to 1 and we pass in true, which
      // repersents it is our turn
      loss.add(calculateLoss(board, true));

      // The move you previously set to 1 is not set back to 0 (meaning
      // the spot can be played on)
      board[moves.get(count).charAt(0) - 48][moves.get(count)
          .charAt(1) - 48] = 0;
      board[moves.get(count).charAt(2) - 48][moves.get(count).charAt(3) - 48] = 0;

      System.out.println("Number of Losses for this Move: "
       + loss.get(count) + "\n");

      // Increases the counter so next time in the while loop we can check
      // the next possible move in the array and pass the new board into
      // calculateLoss
      count++;
    }

    // Intializes min to the first value in the loss array
    int min = loss.get(0);
    // Sets the index to the first value in the loss array
    int indexMin = 0;

    // Runs though this for loop for the total number of moves in the move
    // array
    for (int i = 0; i < count; i++) {

      if (loss.get(i) == 0) {
        return moves.get(i);
      }

      // Checks to see if the loss value in the array is less then the min
      // loss value
      if (loss.get(i) <= min) {

        // If it is i/t sets the new minimum loss value to the min value
        min = loss.get(i);
        // Stores the index of the loss array so we can later call it to
        // play the move from the moves array
        indexMin = i;
      }
    }
    // Plays the move with the fewest losses
    return moves.get(indexMin);
  }

  /*
   * calculateLoss - Parameters: newBoard, and boolean value. Returns an
   * integer value The purpose of this function is it plays though every
   * permutation of the move we passed into the board, it does this by
   * checking to see how many losses the move we played will have and then we
   * return number of losses into the loss array at the index that corresponds
   * to the move we just played from the moves array
   */
  public static int calculateLoss(int[][] newBoard, boolean turn) {

    // The variable for the number of losses
    int loss = 0;

    // Stores all the possible moves into the movoes array
    ArrayList<String> moves = findMoves(newBoard);

    // If there are no moves in the array enter the if statement
    if (moves.size() == 0) {
      // If we are not the last people to play then return 1 (which in
      // this case repersents a loss)
      if (!turn) {
        return 1;
      }
      // If we were the last one to play then return 0
      else {
        return 0;
      }
    }

    // Switching turns
    if (turn == false) {
      turn = true;
    } else
      turn = false;

    int index = 0;

    // Same while loop as before, we start recursion, by passing in the next
    // moved to be played on the board we passed into the calculateLoss
    // function
    while (moves.size() > index) {
      // System.out.println("got in the while loop of calculate moves");
      newBoard[moves.get(index).charAt(0) - 48][moves.get(index)
          .charAt(1) - 48] = 1;
      newBoard[moves.get(index).charAt(2) - 48][moves.get(index)
          .charAt(3) - 48] = 1;
      loss += calculateLoss(newBoard, turn); //add to the losses for that move
      newBoard[moves.get(index).charAt(0) - 48][moves.get(index)
          .charAt(1) - 48] = 0;
      newBoard[moves.get(index).charAt(2) - 48][moves.get(index)
          .charAt(3) - 48] = 0;
      index++;
    }
    // After you play through the whole game with the first move you sent
    // in, it will return the amount of times you could lose with the move
    return loss;
  }

  /* 
   * Finds all possible moves for the board.
   * It finds all the possible moves by going vertically through the board,
   * and if it finds two adjacent open blocks, it then appends those locations together
   * and adds them to the list to be returned.
   * newBoard - the board to find all the moves on
   * returns - a String array list of all the possible moves
   */
  public static ArrayList<String> findMoves(int[][] newBoard) {

    // Declare Variables
    String previous = null;
    String current = null;
    int previousJV = 99;
    int previousJH = 99;
    int counterIndex = 0;

    // Create an ArrayList of total moves in the board
    ArrayList<String> totalMoves = new ArrayList<String>();

    // First for loop runs for the width of the board
    for (int i = 0; i < (newBoard[0].length); i++) {
      // Second loop runs for the height of the board
      for (int j = 0; j < (newBoard.length); j++) {
        // Checks to see if the spot on the board has not been played on
        if (newBoard[j][i] == 0) {
          // Converts the string of the board postion into a number
          // and stores it into current variable
          current = Integer.toString(j) + Integer.toString(i);
          // Checks to see if the last position on the board was able
          // to be played on
          if ((previous != null) && (j - previousJV == 1)) {
            // stores the move into the arrayList totalMoves
            totalMoves.add(previous + current);
          }
        }
        // If the spot on the board is not playable
        else {
          current = null;
        }
        // Sets previous to the current value whether it be null on the
        // position on the board it just checked
        previous = current;
        // Sets the previousJV value to j
        previousJV = j;
      }
    }

    // First for loop runs for the height of the board
    for (int i = 0; i < (newBoard.length); i++) {
      // Second for loop runs for the width of the board
      for (int j = 0; j < (newBoard[0].length); j++) {
        // Checks to see if the spot on the board has not been played
        if (newBoard[i][j] == 0) {
          // Converts the string of the board postion into a number
          // and stores it into current variable
          current = Integer.toString(i) + Integer.toString(j);
          // Checks to see if the last position on the board was able
          // to be played on
          if ((previous != null) && (j - previousJH == 1)) {
            // Stores the move into the arrayList totalMoves  
            totalMoves.add(previous + current);
          }
        }
        // If the spot on the board is not playable
        else {
          current = null;
        }
        // Sets previous to the current value whether it be null on the
        // position on the board it just checked
        previous = current;
        // Sets the previousJH value to j
        previousJH = j;
      }
    }
    // Returns the totalMoves arrayList
    return totalMoves;
  }

}
