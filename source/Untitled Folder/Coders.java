package algorithms;
import org.gamelink.game.Cram;

import java.util.ArrayList;

import org.gamelink.game.Algo;

@SuppressWarnings("unchecked")
/*
 * By: James Morrison and Asmy Asmy Sarasan
 * 
 * 
 */
public class Coders extends Algo{ // Replace TeamName
    private static String teamName = "Coders"; // Replace TeamName
    private static ArrayList<ArrayList<String>> wStates = new ArrayList<ArrayList<String>>();
	  private static String currentMove = "";

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Coders.class); // Replace TeamName
    }

    public static String algorithm(Cram game){
    	int[][] gameBoard = game.getBoard();

    	codeTimer ct = new codeTimer(gameBoard);
    	ct.start();
    	try {
    	    Thread.sleep(25000);                 //1000 milliseconds is one second.
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
    	ArrayList<String> ms = ct.returnMoves();
    	if (ct.t.isAlive() || !ms.contains(currentMove)){
    			currentMove = ms.get(ms.size()-1);
    	}
    	return currentMove;
    }

    public static ArrayList<String> getMoves(int[][] board){
		ArrayList<String> moves = new ArrayList<String>();
		int xoffset;
		int yoffset = 1;
		String base;
		for (int y = 0; y < board.length; y++ ){
			xoffset = 1;
			int x;
			for (x = 0; x < board[0].length;x++){
				if (board[y][x] == 0){
					base = Integer.toString(y)+Integer.toString(x);
					if (xoffset < board[0].length){
						if (board[y][xoffset] == 0){
							moves.add(base+Integer.toString(y)+Integer.toString(xoffset));
						}
					}
					if (yoffset < board.length){
						if (board[yoffset][x] == 0){
							moves.add(base+Integer.toString(yoffset)+Integer.toString(x));
						}
					}
				}
				xoffset++;
			}
			yoffset++;
		}
		return moves;
	}

	public static ArrayList<String> popMove(ArrayList<String> moves,String move){
		ArrayList<String> newMoves = new ArrayList(moves);

		//System.out.print(move.substring(0,2)+" ");System.out.println(move.substring(2,4));
		for (String n:moves){
			if (n.startsWith(move.substring(0,2)) || n.startsWith(move.substring(2,4))){
				newMoves.remove(n);
			}
			if (n.endsWith(move.substring(0,2)) || n.endsWith(move.substring(2,4))){
				newMoves.remove(n);
			}
		}
		//System.out.println(newMoves);
		return newMoves;
	}

	public static int State(ArrayList<String> moves){
		int winning = 0;

		if (wStates.contains(moves)){
			return 1;
		}
		if (moves.size() == 1){
			//wStates.add(moves);
			return 1;
		}
		if (moves.isEmpty()){
			return 0;
		}
		if (moves.size() == 2){
			if (moves.get(0).startsWith(moves.get(1).substring(0,2)) || moves.get(0).startsWith(moves.get(1).substring(2,4))){
				wStates.add(moves);
				currentMove = moves.get(0);
				return 1;
			}
			if (moves.get(0).endsWith(moves.get(1).substring(0,2)) || moves.get(0).endsWith(moves.get(1).substring(2,4))){
				wStates.add(moves);
				currentMove = moves.get(0);
				return 1;
			}
		}
		ArrayList<String> check;

		for (String n : moves){
			
			check = popMove(moves,n);
			int w = State(check);
			if (w==0){
				currentMove=n;
				wStates.add(moves);
				return 1;
			}
			winning += w;
		}
		//System.out.println(winning);
		if (winning == moves.size()){
			return 0;
		}
		return 1;
	}

	public static class codeTimer implements Runnable{
		private Thread t;
		ArrayList<String> moves;
		int[][] board;
		codeTimer(int[][] b){
			this.board = b;
		}
		public void run(){
			this.moves = getMoves(this.board);
		    State(moves);

		}
		
		public ArrayList<String> returnMoves(){
			return moves;
		}
		public void start(){
			if (t==null){
				t = new Thread(this);
				t.start();
			}
		}
	}
}
