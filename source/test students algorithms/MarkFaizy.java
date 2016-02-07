package algorithms;
import java.util.*;

import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
/**
 * 
 * @author Faizan Contractor
 * @author Mark Lewis
 *
 */
public class MarkFaizy extends Algo{
    private static String teamName = "MarkFaizy"; 

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(MarkFaizy.class);
    }

    public static String algorithm(Cram game){
    	int[][] gameBoard = game.getBoard(); // current gameboard from cram
    	int[][] origameBoard = new int[5][5]; // gameboard to modify and determine moves
    	int[][] origameBoard2 = new int[5][5];
    	for(int i=0;i<5;i++){
    		for(int j=0;j<5;j++){
    			origameBoard[i][j] = gameBoard[i][j];
    			origameBoard2[i][j] = gameBoard[i][j];
        	}
    	}
    	int p1MoveCounter=0;
    	int p2MoveCounter=0;
    	int movecounter=0;
    	ArrayList<String> allMoves = new ArrayList<String>(); // arraylist to store all possible moves
    	allMoves = allPossibleMoves(gameBoard);
    	return solve(allMoves, origameBoard, p1MoveCounter, p2MoveCounter, origameBoard2, movecounter);
    }
    /**
     * allPossibleMoves method to store all moves in an arraylist
     * @param gameBoard
     * @return allmoves
     */
    public static ArrayList<String> allPossibleMoves(int[][] gameBoard){
    	String y1="",x1="",y2="",x2="";
    	ArrayList<String> allmoves = new ArrayList<String>();
    	for (int num1=0;num1<5;num1++){
    		for (int num2=0;num2<5;num2++){
    	    	for (int num3=0;num3<=num1;num3++){
    	    		for (int num4=0;num4<=num2;num4++){
    	    			if (gameBoard[num1][num2]==0 && gameBoard[num3][num4]==0 && (num1-num3)>=-1 && (num1-num3)<=1 && (num2-num4)>=-1 && (num2-num4)<=1 && Math.abs(num1-num3)!=Math.abs(num2-num4)){
    	    				y1=Integer.toString(num3);
    	    				x1=Integer.toString(num4);
    	    				y2=Integer.toString(num1);
    	    				x2=Integer.toString(num2);
    	    				allmoves.add(y1+x1+y2+x2);
    	    			}
    	    		}
    			}
    		}
    	}
    	return allmoves;
    }
    /**
     * 
     * @param allmoves
     * @param gameBoard
     * @param p1
     * @param p2
     * @param OGboard
     * @param moves
     * @return 1 move as a string
     */
    public static String solve(ArrayList<String> allmoves, int[][] OGboard, int p1, int p2, int[][] OGboard2, int moves){
    	String move="";
    	int my1,my2,my3,my4;
    	if (allmoves.size()==1){ //base case.. if 1 move remain then take it and win the game
    		if (p1<=p2){
    			move=allmoves.get(0);
    			return move;
    		}
	    }
    	else if (allmoves.size()==2){ // base case..if 2 possible moves remain but by taking 1 move, eliminates the other move then take it and win the game
    		if ((allmoves.get(0).substring(0,2)).equals(allmoves.get(1).substring(0,2)) || (allmoves.get(0).substring(0,2)).equals(allmoves.get(1).substring(2)) || (allmoves.get(0).substring(2)).equals(allmoves.get(1).substring(0,2)) || (allmoves.get(0).substring(2)).equals(allmoves.get(1).substring(2))){
    			if (p1<=p2){
    				move=allmoves.get(0);
        			return move;
    			}
	    	}
	   	}
    	else{
    		if (p1==0){
    			move = allmoves.get(moves);
    			my1=Integer.parseInt(move.substring(0, 1));
    			my2=Integer.parseInt(move.substring(1, 2));
    			my3=Integer.parseInt(move.substring(2, 3));
    			my4=Integer.parseInt(move.substring(3, 4));
    			OGboard[my1][my2]=1;
    			OGboard[my3][my4]=1;
    			allmoves = allPossibleMoves(OGboard);
    			p1++;
    			if (allmoves.size()==0){
	   				return move;
	   			}
    		}
    		else{
    			if (allmoves.size()!=0){
    				move = allmoves.get(allmoves.size()-1);
    			}
    			else{
    				return solve(allmoves, OGboard2, p1, p2, OGboard2, moves++);
    			}
    			my1=Integer.parseInt(move.substring(0, 1));
    			my2=Integer.parseInt(move.substring(1, 2));
    			my3=Integer.parseInt(move.substring(2, 3));
    			my4=Integer.parseInt(move.substring(3, 4));
    			OGboard[my1][my2]=1;
    			OGboard[my3][my4]=1;
    			allmoves = allPossibleMoves(OGboard);
    			if (p1<=p2){
    				p1++;
    				if (allmoves.size()==0){
	   					return move;
	   				}
    			}
    			else{
    				p2++;
    				if (allmoves.size()==1){
	   					return move;
	   				}
    				else if (allmoves.size()==2){
    					if ((allmoves.get(0).substring(0,2)).equals(allmoves.get(1).substring(0,2)) || (allmoves.get(0).substring(0,2)).equals(allmoves.get(1).substring(2)) || (allmoves.get(0).substring(2)).equals(allmoves.get(1).substring(0,2)) || (allmoves.get(0).substring(2)).equals(allmoves.get(1).substring(2))){
    		        		return move;
    			    	}
    				}
    			}
    		}
    		return solve(allmoves, OGboard, p1, p2, OGboard2, moves);
    	}
    	moves++;
    	allmoves = allPossibleMoves(OGboard2);
    	if (moves<allmoves.size()){
    		return solve(allmoves, OGboard2, 0, 0, OGboard2, moves);	
    	}
    	return allmoves.get(0);
    }
}
