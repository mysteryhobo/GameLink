package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;

public class Group extends Algo{ // Replace TeamName
    private static String teamName = "Group"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Group.class); // Replace TeamName
    }

    public static String algorithm(Cram game){
 		int[][] gameBoard = game.getBoard();
		ArrayList<ArrayList<Integer>> allMoves = moves(gameBoard);
		String move = 	allMoves.get(0).get(1).toString() +
						allMoves.get(0).get(0).toString() + 
						allMoves.get(0).get(3).toString() +
						allMoves.get(0).get(2).toString();
		//System.out.print(move);
		return move;
	}
	public static ArrayList<ArrayList<Integer>> moves(int[][] gameBoard){
		ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();

		for(int y = 0; y <= 4; y++){
			//int possibleMoves[][]= new Integer[4][];
			ArrayList<Integer> currentMove = new ArrayList<Integer>();
			int moveCounter = 0;
			for(int x = 0; x <= 4; x++){
				//Check col moves
				if(gameBoard[y][x]==0&&x<4){
					if(gameBoard[y][x+1]==0){
						currentMove.add(x);
						currentMove.add(y);
						currentMove.add(x+1);
						currentMove.add(y);
						ArrayList<Integer> tempMove = new ArrayList<Integer>(currentMove);
						possibleMoves.add(tempMove);
						currentMove.clear();
					}
				}
				//Check row moves
				if(gameBoard[y][x]==0&&y<4){
					if(gameBoard[y+1][x]==0){
						currentMove.add(x);
						currentMove.add(y);
						currentMove.add(x);
						currentMove.add(y+1);
						ArrayList<Integer> tempMove = new ArrayList<Integer>(currentMove);
						possibleMoves.add(tempMove);
						currentMove.clear();
						//System.out.print("");
					}
				}
				
			}

		}
		return possibleMoves;
	}
}
