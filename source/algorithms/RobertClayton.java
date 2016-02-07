package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Character;

public class RobertClayton extends Algo { // Replace TeamName
    private static String teamName = "RobertClayton"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(RobertClayton.class); // Replace TeamName
    }

    public static String algorithm (Cram game) {
        /***************************************************
        *   Designed by:                                   *
        *   Clayton Cheung - 100539921                     *
        *   and                                            *
        *   Robert Kocovski - 100536625                    *
        *   for SOFE3770U, Design & Analysis of Algorithms *
        ****************************************************/

        int[][] gameBoard = game.getBoard();
        String playerMove = "";
        int player = whichPlayer(gameBoard);
        playerMove = findMove (gameBoard, player);
        if (isValid(gameBoard, playerMove))
            return playerMove;
        else
            return backup(gameBoard);
    }

    public static String findMove (int[][] board, int player) {
        //  Find the best move. This will be called from the algorithm function.
        String bestMove = "";
        ArrayList<String> moveList = new ArrayList<String>();
        ArrayList<String> equalMoves = new ArrayList<String>();
        Random rand = new Random();
        int[][] localBoard = copyArray(board);
        moveList = findPossible(localBoard);
        int[][] childBoard = copyArray(localBoard);
        int winMax = 0;    //Highest number of win conditions found
        int winCount[] = new int[moveList.size()];  //Win counter

        //Iterates through the first layer, uses recursion on findWins
        //to iterate through the rest of the move branches in the match
        for (int j = 0; j < moveList.size(); j++) {
            winCount[j] = 0;
            int x1, y1, x2, y2;
            //Above for loop performs recursion for each possible move
            String tempMove = moveList.get(j);
            x1 = Character.getNumericValue(tempMove.charAt(0));
            y1 = Character.getNumericValue(tempMove.charAt(1));
            x2 = Character.getNumericValue(tempMove.charAt(2));
            y2 = Character.getNumericValue(tempMove.charAt(3));
            childBoard[x1][y1] = 1;
            childBoard[x2][y2] = 1;

            //Recursion checks all move branches
            winCount[j] += findWins(childBoard, player);
            //Reset the board for the next move
            childBoard[x1][y1] = 0;
            childBoard[x2][y2] = 0;
            //If the current branch is the best, record it.
        }

        for (int i = 0; i < moveList.size(); i++) {
            if (winCount[i] > winMax) {
                winMax = winCount[i];
            }
        }
        for (int i = 0; i < moveList.size(); i++){
            if (winCount[i] == winMax) {
                equalMoves.add(moveList.get(i));
            }
        }
        if (equalMoves.size() >1) {
            int n = rand.nextInt(equalMoves.size());
            bestMove = equalMoves.get(n);
        }
        if (equalMoves.size() == 1)
            bestMove = equalMoves.get(0);
        return bestMove;
    }

    public static int findWins (int[][] board, int player){
        /*  Iterates through each potential move's subsequent moves
        *   Returns the number of winning scenarios that this branch can provide
        */

        int winMax = 0;
        int[][] childBoard = copyArray(board);
        ArrayList<String> moveList = new ArrayList<String>();
        moveList = findPossible(board);
        int winCount[] = new int[moveList.size()];  //Win counter
            //Keeps going until findPossible doesn't return any moves

            //Setting up board for current branch
            // for loop performs recursion for each possible move
            if (("".equals(moveList.get(0))) == false && whichPlayer(childBoard) == player) {
                for (int j = 0; j < moveList.size(); j++) {
                winCount[j] = 0;
                int x1, y1, x2, y2;
                //Above for loop performs recursion for each possible move
                String tempMove = moveList.get(j);
                x1 = Character.getNumericValue(tempMove.charAt(0));
                y1 = Character.getNumericValue(tempMove.charAt(1));
                x2 = Character.getNumericValue(tempMove.charAt(2));
                y2 = Character.getNumericValue(tempMove.charAt(3));
                childBoard[x1][y1] = 1;
                childBoard[x2][y2] = 1;

                //Recursion checks all move branches
                winCount[j] += findWins(childBoard, player);
                //Reset the board for the next move
                //If it is the last move

                //If the current branch is the best, record it.
                if (winCount[j] > winMax) {
                    winMax = winCount[j];
                }

                childBoard[x1][y1] = 0;
                childBoard[x2][y2] = 0;
            }
        }
        if (("".equals(moveList.get(0))) == true && whichPlayer(childBoard) != player)
            winMax += 1;
        //  returns the number of winning scenarios
        return winMax;
    }

    public static ArrayList<String> findPossible (int[][] board) {
        /*  For the current board, find all the POSSIBLE moves we can make
        *   put them into a string (we can just separate all the possibilities
        *   using spaces or something)
        *   Basically it checks how many consecutive empty cells there are
        *   and appends them to the string.
        *   While iterating, keep count of how many moves we find.
        *   If count % 2 == 0, then we cannot possibly win so backtrack.
        *   Can use string length to determine how many moves there are?
        */

        StringBuffer move = new StringBuffer("");
        ArrayList<String> moveList = new ArrayList<String>();
        int moveCount = 0;
        int[][] localBoard = copyArray(board);
        int i, j;

        //Iterates through the board checking for 0's
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++){
                if (localBoard[i][j] == 0) {
                    /*  We do not need to consider cells above or to the left
                    *   because they must've been considered already because
                    *   the algorithm iterates top-left to bottom-right.     */

                    if (j<4 && localBoard[i][j+1] == 0) {
                        //If the current cell [i][j] is 0, check for 0 below
                        move.append(i);
                        move.append(j);
                        move.append(i);
                        move.append(j+1);
                        moveList.add(move.toString());
                        move.delete(0, 4);
                        moveCount++;
                        localBoard[i][j+1] = 1;
                    }
                    if (i<4 && localBoard[i+1][j] == 0) {
                        //If current cell [i+1][j] is 0, check for 0 on right
                        move.append(i);
                        move.append(j);
                        move.append(i+1);
                        move.append(j);
                        moveList.add(move.toString());
                        move.delete(0, 4);
                        moveCount++;
                        localBoard[i+1][j] = 1;
                    }
                }
            }
        }
        //If there are no moves, set moveList to "" to avoid null
        if (moveCount == 0)
            moveList.add(move.toString());
        return moveList;
    }

    public static int[][] copyArray (int[][] arr) {
        //Performs deep copy of arr
        int[][] newArray = new int [arr.length][];
        for (int i = 0; i < arr.length; i ++)
            newArray[i] = Arrays.copyOf(arr[i], arr[i].length);
        return newArray;
    }

    public static int whichPlayer (int[][] board) {
        //Determine which player's move it is for a given board
        //Return 1 if it is now P1's move, 2 if it is P2's move.
        int p2count = 0;
        int p1count = 0;
        int playerNumber = 0;
        for (int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j ++) {
                if(board[i][j] == 1)
                    p1count++;
                if(board[i][j] == 2)
                    p2count++;
            }
        }
        if (p1count > p2count)
            playerNumber = 2;
        if (p2count == p1count)
            playerNumber = 1;
        return playerNumber;
    }

    public static boolean isValid (int[][] board, String move) {
        /*  takes a move to be considered and checks if it is valid
        *   i.e. does it overlap, the two blocks are adjacent,
        *   correct string formatting. I think those are the only requirements
        */
        char y1 = move.charAt(0);
        char x1 = move.charAt(1);
        char y2 = move.charAt(2);
        char x2 = move.charAt(3);

        if (move.length() != 4) return false;

        //check each coordinate has proper values (0 - 4)
        if (y1 != '0' && y1 != '1' && y1 != '2' && y1 != '3' && y1 != '4')
    	   return false;

        if (x1 != '0' && x1 != '1' && x1 != '2' && x1 != '3' && x1 != '4')
    	   return false;

        if (y2 != '0' && y2 != '1' && y2 != '2' && y2 != '3' && y2 != '4')
    	   return false;

        if (x2 != '0' && x2 != '1' && x2 != '2' && x2 != '3' && x2 != '4')
    	   return false;

        //check if move overlaps
        if (board[y1 - '0'][x1 - '0'] != 0 || board[y2 - '0'][x2 - '0'] != 0)
            return false;

        //check if the two blocks are adjacent
        if (((x2 - x1) == 1 && (y2-y1) == 0) || ((x2-x1) == 0 && (y2-y1) == 1))
    	    return true;
        else
    	    return false;
    }

    public static String backup (int[][] board) {
        /*  if the primary algorithm cannot find a win scenario
        *   game must continue; play any move that is valid
        */
        String move = "";

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0 && board[i][j+1] == 0 ) {
					move = move + i + j + i + (j + 1);
					return move;
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				if (board[i][j] == 0 && board[i+1][j] == 0) {
					move = move + i + j + (i + 1) + j;
					return move;
				}
			}
		}

		return move;
    }

}
