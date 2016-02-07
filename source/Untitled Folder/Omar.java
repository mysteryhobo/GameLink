package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
import java.util.Scanner;

public class Omar extends Algo{ // Replace TeamName
    private static String teamName = "Omar"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Omar.class); // Replace TeamName
    }

    public static String algorithm(Cram game){

  		int gameBoard[][] = game.getBoard();

  		/*
  		 * Fill up middle cross
  		 */
  		if (gameBoard[2][2] == 0 && gameBoard[2][3] == 0)
  			return "2223";
  		if (gameBoard[2][2] == 0 && gameBoard[2][1] == 0)
  			return "2221";
  		if (gameBoard[2][2] == 0 && gameBoard[1][2] == 0)
  			return "2212";
  		if (gameBoard[2][2] == 0 && gameBoard[3][2] == 0)
  			return "2232";

  		/*
  		 * Fill up cross around 23
  		 */
  		if (gameBoard [2][3] == 0 && gameBoard[1][3] == 0)
  			 return "2313";
  		if (gameBoard [2][3] == 0 && gameBoard[3][3] == 0)
  			 return "2333";
  		if (gameBoard [2][3] == 0 && gameBoard[2][4] == 0)
  			 return "2324";


  		/*
  		 * Fill up cross around 12
  		 */
  		if (gameBoard [1][2] == 0 && gameBoard[0][2] == 0)
  			 return "1202";
  		if (gameBoard [1][2] == 0 && gameBoard[1][1] == 0)
  			 return "1211";
  		if (gameBoard [1][2] == 0 && gameBoard[1][3] == 0)
  			 return "1213";

  		/*
  		 * Fill up cross around 21
  		 */
  		if (gameBoard [2][1] == 0 && gameBoard[1][1] == 0)
  			 return "2111";
  		if (gameBoard [2][1] == 0 && gameBoard[2][0] == 0)
  			 return "2120";
  		if (gameBoard [2][1] == 0 && gameBoard[3][1] == 0)
  			 return "2131";

  		/*
  		 * Fill up cross around 32
  		 */
  		if (gameBoard [3][2] == 0 && gameBoard[3][1] == 0)
  			 return "3231";
  		if (gameBoard [3][2] == 0 && gameBoard[4][2] == 0)
  			 return "3242";
  		if (gameBoard [3][2] == 0 && gameBoard[3][3] == 0)
  			 return "3233";

  		/*
  		 * Fill up cross around 13
  		 */
  		if (gameBoard [1][3] == 0 && gameBoard[0][3] == 0)
  			 return "1303";
  		if (gameBoard [1][3] == 0 && gameBoard[1][4] == 0)
  			 return "1314";

  		/*
  		 * Fill up cross around 11
  		 */
  		if (gameBoard [1][1] == 0 && gameBoard[0][1] == 0)
  			 return "1101";
  		if (gameBoard [1][1] == 0 && gameBoard[1][0] == 0)
  			 return "1110";

  		/*
  		 * Fill up cross around 31
  		 */
  		if (gameBoard [3][1] == 0 && gameBoard[3][0] == 0)
  			 return "3130";
  		if (gameBoard [3][1] == 0 && gameBoard[4][1] == 0)
  			 return "3141";

  		/*
  		 * Fill up cross around 33
  		 */
  		if (gameBoard [3][3] == 0 && gameBoard[4][3] == 0)
  			 return "3343";
  		if (gameBoard [3][3] == 0 && gameBoard[3][4] == 0)
  			 return "3334";

  		/*
  		 * Fill up rest around 02
  		 */
  		if (gameBoard [0][2] == 0 && gameBoard[0][1] == 0)
  			 return "0201";
  		if (gameBoard [0][2] == 0 && gameBoard[0][3] == 0)
  			 return "0203";

  		/*
  		 * Fill up rest around 20
  		 */
  		if (gameBoard [2][0] == 0 && gameBoard[1][0] == 0)
  			 return "2010";
  		if (gameBoard [2][0] == 0 && gameBoard[3][0] == 0)
  			 return "2030";

  		/*
  		 * Fill up rest around 42
  		 */
  		if (gameBoard [4][2] == 0 && gameBoard[4][1] == 0)
  			 return "4241";
  		if (gameBoard [4][2] == 0 && gameBoard[4][3] == 0)
  			 return "4243";

  		/*
  		 * Fill up rest around 24
  		 */
  		if (gameBoard [2][4] == 0 && gameBoard[1][4] == 0)
  			 return "2414";
  		if (gameBoard [2][4] == 0 && gameBoard[3][4] == 0)
  			 return "2434";



  		/*
  		 * Fill up corners
  		 */
  		//Check top left corner (Horizontal)
  		if (gameBoard[0][0] == 0 && gameBoard[0][1] == 0)
  			return "0001";
  		//Check top left corner (Vertical)
  		if (gameBoard[0][0] == 0 && gameBoard[1][0] == 0)
  			return "0010";
  		//Check bottom left corner (Horizontal)
  		if (gameBoard[4][0] == 0 && gameBoard[4][1] == 0)
  			return "4041";
  		//Check bottom left corner (Vertical)
  		if (gameBoard[4][0] == 0 && gameBoard[3][0] == 0)
  			return "4030";
  		//Check top right corner (Horizontal)
  		if (gameBoard[0][4] == 0 && gameBoard[0][3] == 0)
  			return "0403";
  		//Check top right corner (Vertical)
  		if (gameBoard[0][4] == 0 && gameBoard[1][4] == 0)
  			return "0414";
  		//Check bottom right corner (Horizontal)
  		if (gameBoard[4][4] == 0 && gameBoard[4][3] == 0)
  			return "4443";
  		//Check bottom right corner (Vertical)
  		if (gameBoard[4][4] == 0 && gameBoard[3][4] == 0)
  			return "4434";
  		else {
  			return "0001";
  		}
    }
}
