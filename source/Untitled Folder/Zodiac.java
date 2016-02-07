package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;

public class Zodiac extends Algo
{ // Replace TeamName
    private static String teamName = "Zodiac"; // Replace TeamName

    public static String getTeamName()
    {
        return teamName;
    }

    public static void main(String[] args)
    {
        Cram game = new Cram(false);
        game.startGame(Zodiac.class); // Replace TeamName
    }
    
    // blackboard group - "Group A"
    public static String algorithm(Cram game)
    {
        // variables for board height/width for easy testing
        int boardHeight = 5;
        int boardWidth = 5;

        // get pieces on the board
        int[][] gameBoard = game.getBoard();
        
        // go through every spot on the board(top left -> bottom right)
        // seems to be the best method for finding a free spot
        // executes (boardheight * boardwidth * 2) + 1 times max
        for(int y = 0; y < boardHeight; y++)
        {
            for(int x = 0; x < boardWidth; x++)
            {
                // place pieces without restriction
                if((x+1) < boardWidth && gameBoard[y][x] == 0 && gameBoard[y][x+1] == 0)
                    return y + "" + x + "" + y + "" + (x+1);
                if((y+1) < boardHeight && gameBoard[y][x] == 0 && gameBoard[y+1][x] == 0)
                    return y + "" + x + "" + (y+1) + "" + x;
            }
        }
                
        // no free spots
        return "no result";
    }
}