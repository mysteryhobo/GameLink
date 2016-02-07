//Do not be afraid to msg me about any questions. I'll explain everything i can about the code if i ended up missing or forgeting something ~Beckett

package algorithms;

import org.gamelink.game.Cram;
import org.gamelink.game.Algo;

import java.util.HashSet;
import java.util.Iterator;

public class Daniel_And_Beckett extends Algo { // Replace TeamName
    private static String teamName = "Daniel_And_Beckett"; // Replace TeamName

    public static String getTeamName() {
        return teamName;
    }

    public static void main(String[] args) {
        Cram game = new Cram(false);
        game.startGame(Daniel_And_Beckett.class); // Replace TeamName
    }

    static HashSet<String> tree = new HashSet<>();

    public static void check(Cram game) {
        String empty = new String();

        for (int row = 0; row <= 4; row++) {
            for (int column = 0; column <= 4; column++) //defines the first cell to look at and goes until each cell has been viewed
            {
                int[][] board = game.getBoard();
                if (board[row][column] == 0) //checks to see if the cell is occupied by a block or a player
                {
                    //variables to help look for connected cells that are empty
                    int below = row + 1;
                    int right = column + 1;

            /*First check if the cell will exist.
              If it does, then each if statement will test to see if that second block is occupied by a block or a player.
              If so then another if statement will run to determine whether or not the tree has included the two cell node as a child of the last move
              If it has not, then it will be inserted into the tree as a child of the last played move.*/
                    if (below <= 4) {
                        if (board[below][column]==0)
                        {
                            empty = "" + row + column + below + column;

                            tree.add(empty);
                        }
                    }

                    if (right <= 4) {
                        if (board[row][right]==0)
                        {
                            empty = "" + row + column + row + right;

                            tree.add(empty);
                        }
                    }
                }
            }
        }
    }


    public static String algorithm(Cram game)
    {
        /************************************************
         ************  PLACE ALGORITHM HERE  ************
         ************************************************/
        Iterator<String> iterator;

        tree.clear();
        check(game);
        iterator = tree.iterator();
        return iterator.next();
    }
}
