/*
Algorithm developed by Mohannad Abdo 100523158 and Mirna Zohiry 100535658
The program gets the current x and y positions according to the order specified, then checks the free upper,lower and side cells to occupy. Once an empty cell is found,it's y and x coordinates are retrieved to be occupied.




*/

package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;


	public class groupone extends Algo{ // Replace TeamName
    	private static String teamName = "groupone"; // Replace TeamName

	//Order followed in each turn
	public static int[] indexIDs ={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	//Board table dimension
	public static int dimension = 5;
	//Stating position
	public static int currentPosition = 0;
	public static int[][] gameBoard ;

	//check coordinates correctness 
	public static boolean checkCell(int x , int y)
	{
		if(x == -1 || y == -1 ) return false;
		if (x >= dimension || y >= dimension) return false;

		if(gameBoard[y][x] == 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	//formula used to get y-axis 
	public static int getYFromIndex(int indexID)
	{
		return ((indexID / dimension));
	}
	
	//formula used to get x-axis 
	public static int getXFromIndex (int indexID)
	{
		return ((indexID % dimension));
	}

	public static int getNorthY (int y)
	{ return y-1;}
	public static int getNorthX (int x)
	{ return x;}

	public static int getSouthY (int y)
	{ return y+1;}
	public static int getSouthX (int x)
	{ return x;}

	public static int getEastY (int y)
	{ return y;}
	public static int getEastX (int x)
	{ return x+1;}

	public static int getWestY (int y)
	{ return y;}
	public static int getWestX (int x)
	{ return x-1;}


	public static String getTeamName()
	{
        return teamName;
	}

	public static void main(String[] args)
	{
        Cram game = new Cram(false);
        game.startGame(groupone.class); // Replace TeamName
	}

	public static String algorithm(Cram game){

 		gameBoard = game.getBoard();
  		boolean secondRun = false;
		String result = "";
		
		while((!secondRun ) || (currentPosition < 25)){
			
			int x = getXFromIndex(indexIDs[currentPosition]);
			int y = getYFromIndex(indexIDs[currentPosition]);
			
			if(checkCell(x,y)){
				//checkNorth
				if(checkCell(getNorthX(x),getNorthY(y))){
					currentPosition++;
					result =  ""+ y +""+x + ""+getNorthY(y) + ""+getNorthX(x);
					
					return result;
				}
				//checkSouth
				if(checkCell(getSouthX(x),getSouthY(y))){
					currentPosition++;
					result = ""+ y +""+x + ""+getSouthY(y) +""+ getSouthX(x);
					
					return result;
				}
				//checkWest

				if(checkCell(getWestX(x),getWestY(y))){
					currentPosition++;
					result = ""+ y + ""+x +""+ getWestY(y) + ""+getWestX(x);
					
					return result;
				}
				//checkEast
				if(checkCell(getEastX(x),getEastY(y))){
					currentPosition++;
					result = ""+ y +""+ x +""+ getEastY(y) +""+ getEastX(x);
					
					return result;
				}

			}

			
			if(currentPosition >= 25){ 
				currentPosition = 0;
				secondRun = true;
			}else{
				currentPosition++;
			}

		}
	return "";
    	}
}


