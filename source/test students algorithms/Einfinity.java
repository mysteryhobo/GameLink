package algorithms;
import java.util.*;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;

public class Einfinity extends Algo
{ 	//SWITCH TOP RIGHT AND BOTTOM RIGHT CODE IN MOVES! 
	public static int i = 0, y1,x1,y2,x2, fy, fx;
	public static int [][] gb = new int [5][5];

	private static String teamName = "Einfinity"; 

	public static String getTeamName()
	{
		return teamName;
	}

	public static void main(String[] args)
	{
		Cram game = new Cram(false);
		game.startGame(Einfinity.class); 
	}

	public static String algorithm(Cram game)
	{
		gb = game.getBoard();
		if (i == 0)
		{
			i++;
			return firstMove(gb);
		}   	
		else
			return moves(gb);
	}

	public static String firstMove (int [][] game)//makes the first move in the center coloumn of the grid 
	{
		String cellOne = "", cellTwo = "", cell = "";
		if (game[2][2] == 0)//check if the specifid coloumn is empty.
		{
			y1 = 2;
			x1 = 2;
			fy = 2;
			fx = 2;
			cellTwo = secondCell(y1,x1,game);//chooses the second cell for the first move
		}
		else if (game [3][2] == 0)//check if the specifid coloumn is empty.
		{
			y1 = 3;
			x1 = 2;
			fy = 3;
			fx = 2;
			cellTwo = secondCell(y1,x1,game);//chooses the second cell for the first move
		}
		else if (game [4][2] == 0)//check if the specifid coloumn is empty.
		{
			y1 = 4;
			x1 = 2;
			fy = 4;
			fx = 2;
			cellTwo = secondCell(y1,x1,game);//chooses the second cell for the first move
		}
		else if (game [0][2] == 0)//check if the specifid coloumn is empty.
		{
			y1 = 0;
			x1 = 2;
			fy = 0;
			fx = 2;
			cellTwo = secondCell(y1,x1,game);//chooses the second cell for the first move
		}
		else if (game [1][2] == 0)//check if the specifid coloumn is empty.
		{
			y1 = 1;
			x1 = 2;
			fy = 1;
			fx = 2;
			cellTwo = secondCell(y1,x1,game);//chooses the second cell for the first move
		}

		cellOne = Integer.toString(y1) + Integer.toString(x1);//converts first cell from integers to string
		cell = cellOne + cellTwo;//combines two cell strings 
		return cell;
	} 

	//////////////////////////////////////////////ALL OTHER MOVES//////////////////////////////////////////////
	public static String moves(int [][] game)
	{
		int y = fy, x = fx; 
		String cellTwo = "", dirCheck = "";
		if (y+1 < 5 && x-1 > -1)//check if bottom left is a valid cell
		{
			if (game[y+1][x-1] == 0)//check if bottom left cell is empty
			{
				fy = y+1;
				fx = x-1;
				cellTwo = secondCell (fy, fx, game);//choose the second cell starting from bottom left
				if (cellTwo.equals("-1-1"))//if there are no cells to go with bottom left cell
				{
					fy = y-1;
					fx = x+1;
					dirCheck = checkBottomRight(fy, fx, game);//check bottom right
					if (dirCheck.equals("-1-1"))//if bottom right is not empty or not valid
					{
						dirCheck = checkTopRight(fy, fx, game);//check top right
						if (dirCheck.equals("-1-1"))
						{
							dirCheck = checkTopLeft (fy,fx,game);//check top left
							if (dirCheck.equals("-1-1"))
							{
								dirCheck = outOfDiagonals (game);//means no diagonals are available 
							}
						}
					}
				}
				else
					dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//makes a string with bottom left cell
			}
			else if (y+1 < 5 && x+1 < 5)//check bottom right is a valid cell
			{
				if (game[y+1][x+1] == 0)//check if bottom right cell is empty
				{
					fy = y+1;
					fx = x+1;
					cellTwo = secondCell(fy, fx, game);//find second cell for bottom right cell
					if (cellTwo.equals("-1-1"))//if there are no other cells 
					{
						fy = y-1;
						fx = x-1;
						dirCheck = checkTopRight(fy, fx, game);//check the top right cell
						if (dirCheck.equals("-1-1"))
						{
							dirCheck = checkTopLeft(fy, fx, game);//check the top left cell
							if (dirCheck.equals("-1-1"))
							{
								dirCheck = outOfDiagonals (game);//no diagonals available
							}
						}
					}
					else dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make a move with bottom right
				}
				else if (y-1 > -1 && x+1 < 5)//check top right is a valid cell
				{
					if (game[y-1][x+1] == 0)//check if bottom right cell is empty
					{
						fy = y-1;
						fx = x+1;
						cellTwo = secondCell (fy, fx, game);//get the second cell
						if (cellTwo.equals("-1-1"))
						{
							dirCheck = checkTopLeft(fy, fx, game);//check top left
							if (dirCheck.equals("-1-1"))
							{
								dirCheck = outOfDiagonals (game);//no diagonals left
							}
						}
						else 
							dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
					}
					else if (y-1 > -1 && x-1 > -1)//check top left is a valid cell
					{
						if (game[y-1][x-1] == 0)//check 
						{
							fy = y-1;
							fx = x-1;
							cellTwo = secondCell(fy, fx, game);//check for a second cell
							if (cellTwo.equals("-1-1"))
							{
								dirCheck = outOfDiagonals (game); 
							}
							else 
								dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
						}
						else 
							dirCheck = outOfDiagonals (game);
					}
					else 
					{
						//OUT OF DIAGONALS 
						dirCheck = outOfDiagonals (game);
					}
				}
				else if (y-1 > -1 && x-1 > -1)//check top left is a valid cell
				{
					if (game[y-1][x-1] == 0)
					{
						fy = y-1;
						fx = x-1;
						cellTwo = secondCell(fy, fx, game);//check for second cell
						if (cellTwo.equals("-1-1"))
						{
							//OUT OF DIAGONALS 
							dirCheck = outOfDiagonals (game);
						}
						else 
							dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
					}
				}
				else 
				{
					//OUT OF DIAGONALS 
					dirCheck = outOfDiagonals (game);
				}
			}
			else if (y-1 > -1 && x+1 < 5)//check top right
			{
				if (game[y-1][x+1] == 0)//check top right empty
				{
					fy = y-1;
					fx = x+1;
					cellTwo = secondCell (fy, fx, game);//check for second cell
					if (cellTwo.equals("-1-1"))
					{
						fy = y+1;
						fx = x-1;
						dirCheck = checkTopLeft (fy, fx, game);
						if (dirCheck.equals("-1-1"))
						{
							dirCheck = outOfDiagonals (game);
						}
						else 
							dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
					}
					else 
						dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;				
				}
				else if (y-1 > -1 && x-1 > -1)//check top left
				{
					if (game[y-1][x-1] == 0)//check if top left empty
					{
						fy = y-1;
						fx = x-1;
						cellTwo = secondCell(fy,fx,game);//choose second cell
						if (cellTwo.equals("-1-1"))
						{
							dirCheck = outOfDiagonals(game);
						}
						else 
							dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
					}
					else 
					{
						dirCheck = outOfDiagonals (game);
					}
				}
			}
			else if (y-1 > -1 && x-1 > -1)//check top left 
			{
				if (game[y-1][x-1] == 0)//check if top left is empty
				{
					fy = y-1;
					fx = x-1;
					cellTwo = secondCell(fy,fx,game);//choose second cell
					if (cellTwo.equals("-1-1"))//if there is no second cell
					{
						dirCheck = outOfDiagonals(game);//no diagonals left
					}
					else 
						dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top left the next move 
				}
				else 
				{
					dirCheck = outOfDiagonals (game);//no diagonals left
				}
			}
			else 
			{
				dirCheck = outOfDiagonals(game);//no diagonals left
			}
		}
		else if (y+1 < 5 && x+1 < 5)//check if bottom right is a valid cell
		{
			if (game[y+1][x+1] == 0)//cehck if bottom right is empty
			{
				fy = y+1;
				fx = x+1;
				cellTwo = secondCell (fy, fx, game);//choose second cell 
				if (cellTwo.equals("-1-1"))//check if second cell is valid 
				{
					fy = y-1;
					fx = x-1;
					dirCheck = checkTopRight (fy, fx, game);//check top right 
					if (dirCheck.equals("-1-1"))//check if top right is a valid move
					{
						dirCheck = checkTopLeft (fy, fx, game);//check if top left is a valid move
						if (dirCheck.equals("-1-1"))
						{
							//OUT OF DIAGONALS
							dirCheck = outOfDiagonals (game);
						}
					}
				}
				else
					dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
			}
			else if (y-1 > -1 && x+1 < 5)//check if top right is valid
			{
				if (game[y-1][x+1] == 0)//top right empty
				{
					fy = y-1;
					fx = x+1;
					cellTwo = secondCell (fy, fx, game);
					if (cellTwo.equals("-1-1"))//check if second cell is valid
					{
						fy = y+1;
						fx = x-1;
						dirCheck = checkTopLeft (fy, fx, game);//check top left
						if (dirCheck.equals("-1-1"))
						{
							//OUT OF DIAGOANLS
							dirCheck = outOfDiagonals (game);
						}
					}
					else 
						dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top right the next move
				}
				else if (y-1 > -1 && x-1 > -1)//check if top left is a valid move 
				{
					if (game[y-1][x-1] == 0)//check if top left is empty
					{
						fy = y-1;
						fx = x-1;
						cellTwo = secondCell(fy, fx, game);//choose a second cell
						if (cellTwo.equals("-1-1"))//check is second cell is valid 
						{
							//OUT OF DIAGONALS 
							dirCheck = outOfDiagonals (game);
						}
						else
							dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top left the next move
					}
					else 
					{
						//OUT OF DIAGONALS 
						dirCheck = outOfDiagonals (game);
					}
				}
				else 
				{
					dirCheck = outOfDiagonals (game);//out of diagonals 
				}

			}
			else if (y-1 > -1 && x-1 > -1)//check if top left is a valid move 
			{
				if (game[y-1][x-1] ==  0)//check if top left is empty
				{
					fy = y-1;
					fx = x-1; 
					cellTwo = secondCell (fy, fx, game);//choose a second cell
					if (cellTwo.equals("-1-1"))//check if second cell is valid 
					{
						//OUT OF DIAGONALS 
						dirCheck = outOfDiagonals (game);
					}
					else 
						dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top left the next move
				}
			}
			else 
			{
				//OUT OF DIAGONALS 
				dirCheck = outOfDiagonals (game);
			}
		}
		else if (y-1 > -1 && x+1 < 5)//check top right is a valid cell
		{
			if (game[y-1][x+1] == 0)//check if top right is empty 
			{
				fy = y-1;
				fx = x+1;
				cellTwo = secondCell (fy, fx, game);//choose second cell
				if (cellTwo.equals("-1-1"))//check if second cell is valid 
				{
					dirCheck = checkTopLeft(fy, fx, game);//check top left for a move
					if (dirCheck.equals("-1-1"))//check if top left is a valid move
					{
						//OUT OF DIAGONALS
						dirCheck = outOfDiagonals (game);
					}
				}
				else 
					dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//top left is the next move
			}
			else if (y-1 > -1 && x-1 > -1)//check if top left is a valid move 
			{
				if (game[y-1][x-1] == 0)//check if top left is empty
				{
					fy = y-1;
					fx = x-1;
					cellTwo = secondCell(fy, fx, game);//choose second cell
					if (cellTwo.equals("-1-1"))//check if second cell is empty
					{
						//OUT OF DIAGONALS 
						dirCheck = outOfDiagonals (game);

					}
					else 
						dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;
				}
				else 
				{
					//out of diagonals 
					dirCheck = outOfDiagonals (game);
				}
			}
			else 
			{
				//OUT OF DIAGONALS 
				dirCheck = outOfDiagonals (game);
			}
		}
		else if (y-1 > -1 && x-1 > -1)//check if top left is a valid cell
		{
			if (game[y-1][x-1] == 0)//check if top left cell is a empty 
			{
				fy = y-1;
				fx = x-1;
				cellTwo = secondCell(fy, fx, game);//choose second cell
				if (cellTwo.equals("-1-1"))//check if second cell is valid 
				{
					//OUT OF DIAGONALS 
					dirCheck = outOfDiagonals (game);
				}
				else 
					dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top left the next move
			}
			else
			{
				//OUT OF DIAGONALS 
				dirCheck = outOfDiagonals (game);
			}
		}
		else 
		{
			//OUT OF DIAGONALS 
			dirCheck = outOfDiagonals (game);
		}

		return dirCheck; //return the next move
	}


	////////////////////////////SECOND CELL still needs work update time 1:24 am///////////////////////////////////////////////////////////////////
	public static String secondCell (int y, int x, int [][] game)
	{
		String cellTwo = "";
		if (y-1 > -1)//check out of bounds (upper)
		{
			if (game[y-1][x] == 0)//TOP
			{
				y2 = y-1;
				x2 = x;
			}
			else if (x-1 > -1)//left is valid
			{
				if (game[y][x-1] == 0)//LEFT
				{
					y2 = y;
					x2 = x-1;
				}
				else if (x+1 < 5)
				{ 
					if (game[y][x+1] == 0)//RIGHT
					{
						y2 = y;
						x2 = x+1;
					}
					else if (y+1 < 5)//if top is valid 
					{
						if (game[y+1][x] == 0)//check if top is empty
						{
							y2 = y+1;
							x2 = x;
						}
						else 
						{
							y2 = -1;
							x2 = -1;
						}
					}
					else 
					{
						y2 = -1;
						x2 = -1;
					}
				}
				else if (y+1 < 5)//check if bottom is valid 
				{
					if (game[y+1][x] == 0)//BOTTOM
					{
						y2 = y+1;
						x2 = x;
					}
					else 
					{
						y2 = -1;
						x2 = -1;
					}
				}
			}
			else if (x+1 < 5)//check if right is valid 
			{
				if (game [y][x+1] == 0)//if right is empty
				{
					y2 = y;
					x2 = x+1;
				}
				else if (y+1 < 5)//if bottom is valid 
				{
					if (game[y+1][x] == 0)//if bottom is empty
					{
						y2 = y+1;
						x2 = x;
					}
					else 
					{
						y2 = -1;
						x2 = -1;
					}
				}
				else 
				{
					y2 = -1;
					x2 = -1;
				}
			}
			else
			{
				y2 = -1;
				x2 = -1;
			}
		}
		//////////////////////////////////////////////////check left
		else if (x-1 > -1)//check out of bounds (left)
		{
			if (game[y][x-1] == 0)//LEFT
			{
				y2 = y;
				x2 = x-1;
			}
			else if (x+1 < 5)
			{ 
				if (game[y][x+1] == 0)//RIGHT
				{
					y2 = y;
					x2 = x+1;
				}
				else if (y+1 < 5)
				{
					if (game[y+1][x] == 0)
					{
						y2 = y+1;
						x2 = x;
					}
					else 
					{
						y2 = -1;
						x2 = -1;
					}
				}
				else 
				{
					y2 = -1;
					x2 = -1;
				}
			}
			else if (y+1 < 5)
			{
				if (game[y+1][x] == 0)//BOTTOM
				{
					y2 = y+1;
					x2 = x;
				}
				else 
				{
					y2 = -1;
					x2 = -1;
				}
			}
			else 
			{
				y2 = -1;
				x2 = -1;
			}
		}
		///////////////////////////////////////////////////////////////////////check right
		else if (x+1 < 5)//check out of bounds (right)
		{
			if (game[y][x+1] == 0)//RIGHT 
			{
				y2 = y;
				x2 = x+1;
			}
			else if (y+1 < 5)//if bottom is valid 
			{
				if (game[y+1][x] == 0)//BOTTOM
				{
					y2 = y+1;
					x2 = x;
				}
				else 
				{
					y2 = -1;
					x2 = -1;
				}		
			}
			else 
			{
				y2 = -1;
				x2 = -1;
			}
		}
		//convert two integers to strings 
		cellTwo = Integer.toString(y2) + Integer.toString(x2);
		return cellTwo;
	}

	///////////////////////////DIAGONAL CHECKING///////////////////////////////////////////////////////////////////
	public static String checkBottomLeft (int bly, int blx, int [][] game)
	{
		int x = blx, y = bly;
		String cellTwo;
		if (y+1 < 5 && x-1 < 5)//check bottom right is a valid cell
		{
			if (game[y+1][x+1] == 0)//check if bottom right is empty
			{
				fy = y+1;
				fx = x-1; 
				cellTwo = secondCell (fy, fx, game);//choose a second cell
				if (cellTwo.equals("-1-1"))//check if second cell is valid
				{
					fy = y;
					fx = x;
					return "-1-1";
				}
				else 
					return Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make bottom left the next move
			} 
			else
				return "-1-1";
		}
		else 
			return "-1-1";
	}
	public static String checkBottomRight (int bry, int brx, int [][] game)//BOTTOM RIGHT
	{
		int x = brx, y = bry;
		String cellTwo;
		if (y+1 < 5 && x+1 < 5)//check bottom right is a valid cell
		{
			if (game[y+1][x+1] == 0)//check if bottom right is empty
			{
				fy = y+1;
				fx = x+1; 
				cellTwo = secondCell (fy, fx, game);//choose second cell
				if (cellTwo.equals("-1-1"))//check if second cell is empty
				{
					fy = y;
					fx = x;
					return "-1-1";
				}
				else 
					return Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make bottom right the next move
			} 
			else
				return "-1-1";
		}
		else 
			return "-1-1";
	}

	public static String checkTopRight (int ty, int tx, int [][] game)//TOP RIGHT
	{
		int x = tx, y = ty; 
		String cellTwo = "";
		if (y-1 > -1 && x+1 < 5)//check if top right is a valid move
		{
			if (game[y-1][x+1]==0)//top right is empty
			{
				fy = y-1;
				fx = x+1;
				cellTwo = secondCell(fy,fx,game);//choose a second cell
				if (cellTwo.equals("-1-1"))//check if second cell is empty
				{
					fy = y;
					fx = x;
					return "-1-1";
				}
				else
					return Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top right the next move
			}
			else 
				return "-1-1";
		}
		else return "-1-1";
	}

	public static String checkTopLeft (int tlx, int tly, int[][] game)//TOP LEFT
	{
		int x = tlx, y = tly; 
		String cellTwo = "";
		if (y-1 > -1 && x-1 < 5)//check if top left is a valid cell
		{
			if (game[y-1][x-1]==0)//check if top left cell is empty
			{
				fy = y-1;
				fx = x-1;
				cellTwo = secondCell(fy,fx,game);//choose a second cell 
				if (cellTwo.equals("-1-1"))//check if second cell is empty
				{
					fy = y+1;
					fx = x+1;
					return "-1-1";
				}
				else
					return Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make top left the next move
			}
			else 
				return "-1-1";
		}
		else return "-1-1";
	}

	///////////////////////////////////////////////OUT OF DIAGONALS ////////////////////////////////////////
	public static String outOfDiagonals (int [][] game)
	{
		
		boolean found = false;
		String cellTwo = "", dirCheck = "";
		for (int a = 0; a <= 4; a++)//go through the coloumns 
		{
			for (int b = 0; b <= 4; b++)//go through the rows 
			{
				if (game[a][b] == 0)//check if cell on the grid is empty
				{
					fy = a;
					fx = b;
					cellTwo = secondCell (fy, fx, game);//choose a second cell for the empty cell
					if (!cellTwo.equals("-1-1"))//check if second cell is valid 
					{
						a = 5;
						b = 5;
						dirCheck = Integer.toString(fy) + Integer.toString(fx) + cellTwo;//make the cell the next move
						found = true;
					}
				}
			}
		}
		if (found == false)//if no cells are found on the grid GAME OVER RETURN AN INVALID MOVE (WILL NEVER HAPPEN BECAUSE GAME ENDS BEFORE THIS). 
		{
			return "-2-2";
		}
		return dirCheck;
	}
}
