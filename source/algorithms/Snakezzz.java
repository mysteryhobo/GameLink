/**
 * Design and Analysis of Algorithms
 * Proff: Masoud Makrehchi
 * 
 * Daljit Sohi		100520358
 * Neil Ramdath		100519195
 * 
 * Cram Game Algorithm
 */

package algorithms;
import org.gamelink.game.Cram;
import java.util.*;
import java.lang.*;
import org.gamelink.game.Algo;

public class Snakezzz extends Algo{ // Replace TeamName

	private static String teamName = "Snakezzz"; // Replace TeamName
	//return moves
	private static String move = null;

	//Creating a 5 X 5 Array
	private static int[][] myArray = new int[5][5];

	public static String getTeamName(){
		return teamName;
	}

	public static void main(String[] args){
		Cram game = new Cram(false);
		game.startGame(Snakezzz.class); // Replace TeamName
	}

	public static String algorithm(Cram game){

		//Copying my array over to the gameBoard() created by the Cram class
		System.arraycopy(game.getBoard(), 0, myArray, 0, game.getBoard().length);

		// creating an array to store possible moves
		ArrayList<String> movesArray = new ArrayList<>();


		//loop through the array
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				//Check Horizontal Cell
				if(checkForX(myArray, i, j))
				{
					//if the postion has not been played, then place it into the 'movesArray' array
					if(j != 4)
						movesArray.add(i + "" + j + "" + i + "" + (j + 1));
				}
				//Check Vertical Cells
				if(checkForY(myArray, i, j))
				{
					//if the postion has not been played, then place it into the 'movesArray' array
					if(i != 4) 
						movesArray.add(i + "" + j + "" + (i + 1) + "" + j);
				}
			}
		}

		//Randomly search 'movesArray', and select a position from there, and send it to the Cram Class
		Random ran = new Random();
		int random = ran.nextInt(movesArray.size());
		return movesArray.get(random)+"";

	}// END of algorith() method

	//Making sure  X is in bound, and has not occupied
	public static boolean checkForX(int board[][], int x, int y)
	{
		if(x < 4 || y <= 4)
		{
			if(board[x][y] == 0 && board[x][y + 1] == 0)
				return true; // If position is playable, return true.
		}
		return false; // else return false
	}

	//Making sure Y is in bound, and has not been occupied
	public static boolean checkForY(int board[][], int x, int y){

		if(x <= 4|| y < 4){

			if(board[x][y] == 0 && board[x + 1][y] == 0){
				return true; // If position is playable, return true.
			}
		}
		return false; // else return false
	}//End of checkFor

}//END OF TheTeam Class
