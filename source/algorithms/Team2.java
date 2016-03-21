package algorithms;

import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
import java.util.Scanner;

public class Team2 extends Algo{
    private static String teamName = "Team2";

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Team2.class);
    }

    // public static String algorithm(Cram game){
    //     int[][] gameBoard = game.getBoard();
    //     Scanner userInputScanner = new Scanner(System.in);
    //     System.out.print("Please enter your move: ");
    //     String input = userInputScanner.nextLine();
    //     return input;
    // }

    public static String algorithm(Cram game){
        int[][] gameBoard = game.getBoard();
        game.getBoard();
        int width = gameBoard[0].length;
        int height = gameBoard.length;

        for (int x = 0; x < width; x ++){
            for (int y = 0; y < height; y ++){
                if (gameBoard[y][x] == 0 && y - 1 > 0 && gameBoard[y - 1][x] == 0) { 
                    return Integer.toString(y) + Integer.toString(x) + Integer.toString(y - 1) + Integer.toString(x);
                }
                if (gameBoard[y][x] == 0 && y + 1 < height && gameBoard[y + 1][x] == 0){
                    return Integer.toString(y) + Integer.toString(x) + Integer.toString(y + 1) + Integer.toString(x); 
                } 
                if (gameBoard[y][x] == 0 && x - 1 > 0 && gameBoard[y][x - 1] == 0){
                    return Integer.toString(y) + Integer.toString(x) + Integer.toString(y) + Integer.toString(x - 1); 
                } 
                if (gameBoard[y][x] == 0 && x + 1 < width && gameBoard[y][x + 1] == 0){
                    return Integer.toString(y) + Integer.toString(x) + Integer.toString(y) + Integer.toString(x + 1);
                }      
            }
        }
        return null;
    }  
}