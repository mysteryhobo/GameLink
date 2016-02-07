package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
// Packages imported for algorithm implementation
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GamersWanted extends Algo{ // Replace TeamName
    private static String teamName = "GamersWanted"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(GamersWanted.class); // Replace TeamName
    }

    public static String algorithm(Cram game){
 
   /************************************************
    ************  PLACE ALGORITHM HERE  ************
	************************************************/
	// Note: This algorithm will run when Gamelink calls it, signalling it is my turn to move.
	int[][] gameBoard = game.getBoard();
	// Store in a linked list the coordinates of all cells that contain zeroes.
		// Create a 2-D array list with the number of entries corresponding to the total number of cells.
		ArrayList<Integer[]> cellsWithZeroes = new ArrayList<Integer[]>();
		// Iterate through the game board cells vertically...
		for (int y = 0; y < 4; y++) {
			// and horizontally...
			for (int x = 0; x < 4; x++) {
			// If the value in the cell is equal to zero...
				if  (gameBoard[y][x] == 0) {
				// Store its location in the the list in the location of the pointer and advance it.
					Integer[] zeroArray = new Integer[2];
					zeroArray[0] = y;
					zeroArray[1] = x;
					cellsWithZeroes.add(zeroArray);
				}
			}
		}
	// Produce a list of all possible locations to place a domino.
		// Create a list for horizontal placements.
		ArrayList<Integer[]> horizontalLocations = new ArrayList<Integer[]>();
			// Iterate through the list of zeros.
			for (int h = 0; h < cellsWithZeroes.size(); h++) {
				// Compare the first digits (y) of the value in the zero pointer and the next.
				// If the first digits are not out of bounds...
				if (h != cellsWithZeroes.size() - 1) {
					// and if they are equal...
					if (cellsWithZeroes.get(h)[0].equals(cellsWithZeroes.get(h + 1)[0])) {
						// Compare the second digits (x).
						// If the second digits differ by one...
							if (Math.abs(cellsWithZeroes.get(h)[1] - cellsWithZeroes.get(h + 1)[1]) == 1) {
							// Store the two locations in the horizontal placement list.
								Integer[] horizArray = new Integer[2];
								horizArray[0] = h;
								horizArray[1] = h + 1;
								horizontalLocations.add(horizArray);
							}
						}
					}
				}
		// Create a list for vertical placements.
		ArrayList<Integer[]> verticalLocations = new ArrayList<Integer[]>();
			// Iterate through the list of zeros.
			for (int v = 0; v < cellsWithZeroes.size(); v++) {
				// Compare the second digits (x) of the value in the zero pointer and the next.
				// If the second digits are not out of bounds...
				if (v != cellsWithZeroes.size() - 1) {
					// and if they are equal...
					if (cellsWithZeroes.get(v)[1].equals(cellsWithZeroes.get(v + 1)[1])) {
						// Compare the first digits (y).
						// If the first digits differ by one...
							if (Math.abs(cellsWithZeroes.get(v)[0] - cellsWithZeroes.get(v + 1)[0]) == 1) {
							// Store the two locations in the vertical placement list.
								Integer[] vertArray = new Integer[2];
								vertArray[0] = v;
								vertArray[1] = v + 1;
								verticalLocations.add(vertArray);
							}
						}
					}
				}
	// Randomly select and return a valid location to place a domino.
		// Generate a random integer (0 or 1) to pick either a horizontal or vertical placement.
		Random placement = new Random();
		int order = placement.nextInt(1);
		// If the integer in question is 0 or there is no possible vertical move, generate a random integer to pick a horizontal move.
		if (order == 0 || verticalLocations.size() == 0) {
			Random query = new Random();
			int z = query.nextInt(horizontalLocations.size() + 1);
			// Convert the coordinates found into a string and return it.
		String move = Integer.toString(cellsWithZeroes.get(horizontalLocations.get(z)[0])[0]) +
           	                  		Integer.toString(cellsWithZeroes.get(horizontalLocations.get(z)[0])[1]) +
                               		Integer.toString(cellsWithZeroes.get(horizontalLocations.get(z)[1])[0]) +
                               		Integer.toString(cellsWithZeroes.get(horizontalLocations.get(z)[1])[1]);
		return move;
		}
		// If the integer in question is 1 or there is no possible horizontal move, generate a random integer to pick a vertical move.
		if (order == 1 || horizontalLocations.size() == 0) {
			Random query = new Random();
			int z = query.nextInt(verticalLocations.size() + 1);
			// Convert the coordinates found into a string and return it.
		String move = Integer.toString(cellsWithZeroes.get(verticalLocations.get(z)[0])[0]) +
           	                  		Integer.toString(cellsWithZeroes.get(verticalLocations.get(z)[0])[1]) +
                               		Integer.toString(cellsWithZeroes.get(verticalLocations.get(z)[1])[0]) +
                               		Integer.toString(cellsWithZeroes.get(verticalLocations.get(z)[1])[1]);
		return move;
		}
		// return null if otherwise
		else return null;
	// Note: the program will endlessly search for a valid move if no valid move can be made.
    }
}
