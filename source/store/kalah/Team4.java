package algorithms;

import org.gamelink.game.Kalah;
import org.gamelink.game.Algo;
import java.util.Random;
import java.util.Scanner;

public class Team4 extends Algo{
    private static String teamName = "Team4";


    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Kalah game = new Kalah(false);
        game.startGame(Team4.class);
    }

    // public static String algorithm(Kalah game){
    //     int[][] gameBoard = game.getBoard();
    //     Scanner userInputScanner = new Scanner(System.in);
    //     System.out.print("Please enter your move: ");
    //     String input = userInputScanner.nextLine();
    //     return input;
    // }

    public static String algorithm(Kalah game){
        int[][] gameBoard = game.getBoard();
        int range = gameBoard[1].length - 2;

        Random rand = new Random();

        while(true){
            int move = rand.nextInt(range) + 1;
            if (gameBoard[1][move] > 0) return String.valueOf(move);
        }
    }
}