package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;

public class Shadow extends Algo{ // Replace TeamName
    private static String teamName = "Shadow"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Shadow.class); // Replace TeamName
    }

    public static String algorithm(Cram game){
   		int[][] gameBoard = game.getBoard();

		if(gameBoard[0][4]==0 && gameBoard[1][4]==0){
			return "0414";
		}
		else if(gameBoard[4][2]==0 && gameBoard[4][3]==0){
			return "4243";
		}
		else if(gameBoard[1][3]==0 && gameBoard[2][3]==0){
			return "1323";
		}	
		else if(gameBoard[0][1]==0 && gameBoard[0][2]==0){
			return "0102";
		}
		else if(gameBoard[0][0]==0 && gameBoard[1][0]==0){
			return "0010";
		}		
		else if(gameBoard[2][1]==0 && gameBoard[2][2]==0){
			return "2122";
		}
		else if(gameBoard[3][0]==0 && gameBoard[4][0]==0){
			return "3040";
		}
		else if(gameBoard[0][2]==0 && gameBoard[1][2]==0){
			return "0212";
		}
		else if(gameBoard[2][0]==0 && gameBoard[3][0]==0){
			return "2030";
		}
		else if(gameBoard[3][1]==0 && gameBoard[3][2]==0){
			return "3132";
		}
		else if(gameBoard[0][3]==0 && gameBoard[0][4]==0){
			return "0304";
		}
		else if(gameBoard[4][3]==0 && gameBoard[4][4]==0){
			return "4344";
		}
		else if(gameBoard[3][4]==0 && gameBoard[4][4]==0){
			return "3444";
		}
		else if(gameBoard[4][0]==0 && gameBoard[4][1]==0){
			return "4041";
		}
		else if(gameBoard[1][1]==0 && gameBoard[1][2]==0){
			return "1112";
		}
		else if(gameBoard[1][1]==0 && gameBoard[2][1]==0){
			return "1121";
		}
		else if(gameBoard[2][3]==0 && gameBoard[3][3]==0){
			return "2333";
		}
		else if(gameBoard[3][2]==0 && gameBoard[3][3]==0){
			return "3233";
		}	
		else if(gameBoard[1][2]==0 && gameBoard[1][3]==0){
			return "1213";
		}
		else if(gameBoard[2][1]==0 && gameBoard[3][1]==0){
			return "2131";
		}
		else if(gameBoard[1][2]==0 && gameBoard[2][2]==0){
			return "1222";
		}
		else if(gameBoard[2][2]==0 && gameBoard[3][2]==0){
			return "2232";
		}
		else if(gameBoard[2][2]==0 && gameBoard[2][3]==0){
			return "2223";
		}		
		else if(gameBoard[2][0]==0 && gameBoard[2][1]==0){
			return "2021";
		}
		else if(gameBoard[4][1]==0 && gameBoard[4][2]==0){
			return "4142";
		}
		else if(gameBoard[3][2]==0 && gameBoard[4][2]==0){
			return "3242";
		}
		else if(gameBoard[1][0]==0 && gameBoard[2][0]==0){
			return "1020";
		}
		else if(gameBoard[1][4]==0 && gameBoard[2][4]==0){
			return "1424";
		}
		else if(gameBoard[2][4]==0 && gameBoard[3][4]==0){
			return "2434";
		} 
		else if(gameBoard[2][3]==0 && gameBoard[2][4]==0){
			return "2324";
		}
		else if(gameBoard[0][2]==0 && gameBoard[0][3]==0){
			return "0203";
		}
		else if(gameBoard[3][0]==0 && gameBoard[3][1]==0){
			return "3031";
		}
		else if(gameBoard[0][3]==0 && gameBoard[1][3]==0){
			return "0313";
		}
		else if(gameBoard[3][1]==0 && gameBoard[4][1]==0){
			return "3141";
		}
		else if(gameBoard[1][0]==0 && gameBoard[1][1]==0){
			return "1011";
		}
		else if(gameBoard[1][3]==0 && gameBoard[1][4]==0){
			return "1314";
		}
		else if(gameBoard[3][3]==0 && gameBoard[4][3]==0){
			return "3343";
		}
		else if(gameBoard[3][3]==0 && gameBoard[3][4]==0){
			return "3334";
		}
		return "";

    }
}
