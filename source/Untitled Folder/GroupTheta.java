package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;


public class GroupTheta extends Algo
{ 
    private static String teamName = "GroupTheta";

    public static String getTeamName()
    {
        return teamName;
    }

    public static void main(String[] args)
    {
        Cram game = new Cram(false);
        game.startGame(GroupTheta.class);        
    }

    public static String algorithm(Cram game)
    {
    	//Algorithm
    	int[][] gameBoard = game.getBoard();
    	// All of the tree-generating methods are evoked simply by making a new root node.
    	CramNode rootNode = new CramNode(gameBoard);
    	// When the tree is finished building, we can just pull the best move off the root node.
    	return rootNode.BestMove();
    }
} // End Algorithm

//.....................................................................................................................................
class CramNode 
{
public static int nAIPlayer = 0;

public int childLosses = 0;
public int childWins = 0;
public int MaxChildWin;
public int MinChildLoss;
public int currentTurn;
public int nFinalScore;
public CramNode[] cramnodeChildren;
public CramNode cramnodeParent;
public CramNode cramnodeLossFavoredPath;
public CramNode cramnodeWinFavoredPath;
public String sNodeReachedBy;
public String sBestWinningMove;
public String sBestLosingMove;
public int nDepth = 0;	
public int nChildID = 0;
private int nChildCount = 0;	
public int[][] boardConfiguration;
public String[] sAvailableMoves;
//.....................................................................................................................................
// root node constructor
// Make a new root node, copy the game board
public CramNode(int[][] boardConfig) 
{
	// AI is nPlayer
	nDepth = 0;
	sAvailableMoves = new String[40];
	boardConfiguration = new int[5][5];
	copyBoard(boardConfig);
	currentTurn = boardCheck();
	nAIPlayer = currentTurn;
	MaxChildWin = -9999;
	MinChildLoss = -9999;
	cramnodeChildren = new CramNode[40];
	
	generateChildrenFromMoves();
}
//.....................................................................................................................................
public int boardCheck()
{
	for (int i = 0 ; i < boardConfiguration[0].length ; i ++)
	{
		for (int j = 0 ; j < boardConfiguration.length ; j ++)
		{
			if (boardConfiguration[i][j] == 1)
			{
				return 2;
			}
		}
	}
return 1;		
}
//.....................................................................................................................................	
// child node constructor
// Store a game board, the depth of the node in the tree, and the child number
public CramNode(int[][] boardConfig, int aDepth, int pID, int NextTurn, String sReachedBy, CramNode nodeParent) 
{
	nDepth = aDepth;
	nChildID = pID;
	sNodeReachedBy = sReachedBy;
	// Next turn means that it's now the next player's turn to make the next move.
	currentTurn = NextTurn;
	sAvailableMoves = new String[40];
	boardConfiguration = new int[5][5];
	copyBoard(boardConfig);
	MaxChildWin = -9999;
	MinChildLoss = -9999;
	cramnodeChildren = new CramNode[40];
	cramnodeParent = nodeParent;
	
	generateChildrenFromMoves();
}
//.....................................................................................................................................
public void copyBoard(int[][] origin)
{
	for (int i = 0 ; i < origin[0].length ; i++)
	{
		for (int j = 0 ; j < origin.length ; j++)
		{
			boardConfiguration[i][j] = origin[i][j];
		}
	}
	
}
//.....................................................................................................................................
public void copyMoveset(String[] origin)
{
	for (int i = 0 ; i < origin.length ; i++)
	{
		sAvailableMoves[i] = origin[i];
	}	
}
//.....................................................................................................................................
// Returns an array of valid moves as an array of strings.
public String[] getValidMoves()
{
	int nValidCount = 0;
	String[] saValidMoves = new String[40];
	
	//Check through the entire board and store all valid moves in validMoves

	// Horizontal check, the first row's length
	for(int i = 0; i < boardConfiguration[0].length; i++)
	{
		// Vertical check, the length of all rows in the array.
		for(int j = 0; j < boardConfiguration.length; j++)
		{
			/*If two consecutive values are 0, and the second value is not the rightmost entry on the board
			 * the string representation of that location that the board recognizes is stored in the string array
			 */
			if((j+1) < boardConfiguration[0].length && boardConfiguration[i][j]==0 && boardConfiguration[i][j+1] == 0)
			{
				int k = j+1;
				saValidMoves[nValidCount] = "" + i + j + i + k; //Concatenates coordinates into a string (i.e, 4041)
				nValidCount++;
			}
			if((i+1) < boardConfiguration.length && boardConfiguration[i][j] == 0 && boardConfiguration[i+1][j] == 0)
			{
				int l = i+1;
				saValidMoves[nValidCount] = "" + i + j + l + j;
				nValidCount++;
			}
		}
	}
    return saValidMoves;
}
//.....................................................................................................................................	
// Returns the game board after the move sMoveToMake has been made. The positions are updated based on whose turn it is (player 1 or player 2)
public int[][] modifyGameboard(int[][] currentBoard, String sMoveToMake, int nTurn)
{
	int[][] newBoard = new int[5][5];
	newBoard = currentBoard.clone();
	newBoard[Integer.parseInt(sMoveToMake.substring(0, 1))][Integer.parseInt(sMoveToMake.substring(1, 2))] = nTurn;
	newBoard[Integer.parseInt(sMoveToMake.substring(2, 3))][Integer.parseInt(sMoveToMake.substring(3, 4))] = nTurn;
	return newBoard;    	
}
//..................................................................................................................................... 
// Leaf node, let's tally the game board and see if we have won the game or lost the game.
public int tallyGameboard(int nAIPlayer, int nTurn)
{
	int[][] currentBoard = boardConfiguration;
	int player1Score = 0;
	int player2Score = 0;
	int sumZeros = 0;
	
	for (int x = 0; x < currentBoard[0].length ; x++)
	{
		for (int y = 0; y < currentBoard.length ; y++)
		{
			if (currentBoard[x][y] == 1)
			{
				player1Score++;    				
			}
			else if (currentBoard[x][y] == 2)
			{
				player2Score++;    				
			}
			else if (currentBoard[x][y] == 0)
			{
				sumZeros++;
			}
		}
	}
	
	// If it is the human's turn to place a piece but they cannot place a piece, we've won the game.
	if (nTurn != nAIPlayer)
	{
		// We won the game!
		if (nAIPlayer == 1)
		{
			return player1Score + sumZeros;
		}
		else
		{
			return player2Score + sumZeros;
		}
		
	}
	// If it is the AI's turn to place a piece but we cannot place a piece, we've lost the game.
	else
	{
		// We lost the game!
		if (nAIPlayer == 1)
		{
			return (player2Score+sumZeros)*-1;
		}
		else
		{
			return (player1Score+sumZeros)*-1;
		}
		
	}
}
//.....................................................................................................................................
	// Create a new child under a node
	// Increase the parent's child count.
	public void createChild(int[][] boardConfigNew, String sReachedBy)
	{
		// Next turn is either 1 or 2
		int nNextTurn = currentTurn + 1;
		if (nNextTurn == 3)
		{
			nNextTurn = 1;
		}
		cramnodeChildren[nChildCount] = new CramNode(boardConfigNew, nDepth+1, nChildCount+1, nNextTurn, sReachedBy, this);	
		nChildCount++;
	}
//....................................................................................................................................
	public void rollUp()
	{
		if (nDepth == 0)
		{
			return;
		}
		if (cramnodeParent.MinChildLoss < MinChildLoss)
		{
			cramnodeParent.MinChildLoss = MinChildLoss;
			cramnodeParent.sBestLosingMove = sNodeReachedBy;
			cramnodeParent.cramnodeLossFavoredPath = this;
			
		}
		if (cramnodeParent.MaxChildWin < MaxChildWin)
		{
			cramnodeParent.MaxChildWin = MaxChildWin;
			cramnodeParent.sBestWinningMove = sNodeReachedBy;
			cramnodeParent.cramnodeWinFavoredPath = this;
		}
		cramnodeParent.rollUp();
		
	}
//....................................................................................................................................
public void generateChildrenFromMoves()
{
	// Store all available moves into the current node.
	copyMoveset(getValidMoves());
	
	// For every move,
	for (int i = 0; i < sAvailableMoves.length ; i++)
	{
		// if there is a valid next move,
		if (sAvailableMoves[i] != null)
		{
			// Place a piece
    		// and create a child
    		createChild(modifyGameboard(boardConfiguration, sAvailableMoves[i], currentTurn), sAvailableMoves[i]);
		}
		// if there is no valid next move,
		else if (sAvailableMoves[0] == null)
		{
				// it's a leaf node, we need to compute the final score for that node
		    	nFinalScore = tallyGameboard(nAIPlayer, currentTurn);
				if (nFinalScore < 0)
				{
					// We lost the game!
					if (cramnodeParent.MinChildLoss < nFinalScore)
					{
						cramnodeParent.MinChildLoss = nFinalScore;
						cramnodeParent.sBestLosingMove = sNodeReachedBy;
						cramnodeParent.cramnodeLossFavoredPath = this;  				
					}
					cramnodeParent.childLosses++;
				}
				if (nFinalScore > 0)
				{
					// We won the game!
					if (cramnodeParent.MaxChildWin < nFinalScore)
					{
						cramnodeParent.MaxChildWin = nFinalScore;
						cramnodeParent.sBestWinningMove = sNodeReachedBy;
						cramnodeParent.cramnodeWinFavoredPath = this;
					}
					cramnodeParent.childWins++;
				}
				// and when all the leaves are done,
				if (nChildID == cramnodeParent.nChildCount)
				{
					// roll up recursively back to the root node
					rollUp();
				}
		}
	}
}
//.....................................................................................................................................
	// Tree must be completely generated before this will work.
	public String BestMove()
	{
		return sBestWinningMove;
	}   
}
