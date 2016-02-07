package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
import java.util.Random;

public class Linkuoit extends Algo{
    private static String teamName = "Linkuoit";

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Linkuoit.class);
    }

    public static String algorithm(Cram game){

	     int gameBoard[][] = game.getBoard();

       Random random = new Random();
       int decider = random.nextInt(2);


       if(decider == 1){
         for(int i = 0; i < gameBoard.length - 1; i++){
           for(int j = 0; j < gameBoard[i].length; j++){
             if(gameBoard[i][j] == 0 && gameBoard[i+1][j] == 0){
               return String.valueOf(i) + String.valueOf(j) +
                String.valueOf(i + 1) + String.valueOf(j);
             }
           }
         }

         for(int i = 0; i < gameBoard.length; i++){
           for(int j = 0; j < gameBoard[i].length - 1; j++){
             if(gameBoard[i][j] == 0 && gameBoard[i][j+1] == 0){
               return String.valueOf(i) + String.valueOf(j) +
                String.valueOf(i) + String.valueOf(j + 1);
             }
            }
          }
        }

        else{
          for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[i].length - 1; j++){
              if(gameBoard[i][j] == 0 && gameBoard[i][j+1] == 0){
                return String.valueOf(i) + String.valueOf(j) +
                  String.valueOf(i) + String.valueOf(j + 1);
              }
            }
          }

          for(int i = 0; i < gameBoard.length - 1; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
              if(gameBoard[i][j] == 0 && gameBoard[i+1][j] == 0){
                return String.valueOf(i) + String.valueOf(j) +
                  String.valueOf(i + 1) + String.valueOf(j);
              }
            }
          }
        }

        return null;
    }
}
