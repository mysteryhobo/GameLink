package org.gamelink.game;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CramTest {
	
	Cram game = new Cram(1);

	@Test
	public void testSetCell() {
		int[][] testBoard = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		game.setCell(testBoard, 0, 0, 1);
		game.setCell(testBoard, 0, 4, 2);
		game.setCell(testBoard, 4, 0, 3);
		game.setCell(testBoard, 4, 4, 4);
		int[][] expectedBoard = 
			    {{1,0,0,0,2},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {3,0,0,0,4}};
		
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(testBoard[y], expectedBoard[y]);
		}
	}

	@Test
	public void testSetBoard() {
		int[][] expectedBoard = 
		    {{1,2,3,4,5},
			 {0,0,0,0,0},
			 {0,0,0,0,0},
			 {0,0,0,0,0},
			 {3,0,0,0,4}};
		game.setBoard(expectedBoard);
		
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.getBoard()[y], expectedBoard[y]);
		}
	}

	@Test
	public void testCalcScore() {
		int[][] testBoard1 = 
		    {{1,1,2,2,6},
			 {1,2,2,1,1},
			 {1,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		game.setBoard(testBoard1);
		int[] testScore1 = {10,13,2};
		assertArrayEquals(testScore1, game.calcScore());
		
		int[][] testBoard2 = 
		    {{1,0,2,2,6},
			 {1,0,0,0,0},
			 {1,1,0,0,0},
			 {0,0,0,0,0},
			 {0,0,6,0,0}};
		game.setBoard(testBoard2);
		int[] testScore2 = {21,2,1};
		assertArrayEquals(testScore2, game.calcScore());
	}

	@Test
	public void testIsGameOver() {
		int[][] testBoard1 = 
		    {{1,1,2,2,6},
			 {1,2,2,1,1},
			 {1,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		game.setBoard(testBoard1);
		assertTrue(game.isGameOver());
		int[][] testBoard2 = 
		    {{1,1,2,2,6},
			 {1,2,2,1,1},
			 {0,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		game.setBoard(testBoard2);
		assertTrue(!game.isGameOver());
	}

	@Test
	public void testAreBoardsEqual() {
		int[][] testBoard1 = 
		    {{1,1,2,2,6},
			 {1,2,2,1,1},
			 {1,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		int[][] testBoard2 = 
		    {{1,1,2,2,6},
			 {1,2,2,1,1},
			 {1,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		assertTrue(game.areBoardsEqual(testBoard1, testBoard2));
		
		testBoard2[0][0] = 7;
		assertTrue(!game.areBoardsEqual(testBoard1, testBoard2));
	}

	@Test
	public void testArrayToString() {
		int[][] testArray1 = 
		    {{1,1,2,2,6},
			 {1,2,2,1,1},
			 {1,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		String expectedString = "1|1|2|2|6|1|2|2|1|1|1|0|2|0|2|1|1|2|1|2|2|2|6|1|0";
		assertTrue(game.arrayToString(testArray1).equals(expectedString));
		
		int[][] testArray2 = 
		    {{1,1,1,1,1},
			 {2,2,2,2,2},
			 {1,1,1,1,1},
			 {1,1,1,1,1},
			 {2,2,2,2,2}};
		String expectedString2 = "1|1|1|1|1|2|2|2|2|2|1|1|1|1|1|1|1|1|1|1|2|2|2|2|2";
		assertTrue(game.arrayToString(testArray2).equals(expectedString2));
	}

	@Test
	public void testStringToArray() {
		String string1 = "1|1|1|1|1|2|2|2|2|2|1|1|1|1|1|1|1|1|1|1|2|2|2|2|2";
		int[][] expectedArray1 = 
		    {{1,1,1,1,1},
			 {2,2,2,2,2},
			 {1,1,1,1,1},
			 {1,1,1,1,1},
			 {2,2,2,2,2}};
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.stringToArray(string1)[y], expectedArray1[y]);
		}
		
		String string2 = "1|1|2|2|6|1|2|2|1|1|1|0|2|0|2|1|1|2|1|2|2|2|6|1|0";
		int[][] expectedArray2 = 
			{{1,1,2,2,6},
		     {1,2,2,1,1},
			 {1,0,2,0,2},
			 {1,1,2,1,2},
			 {2,2,6,1,0}};
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.stringToArray(string2)[y], expectedArray2[y]);
		}
	}

	@Test
	public void testCopyArray() {
		int[][] expectedArray1 = 
		    {{1,1,1,1,1},
			 {2,2,2,2,2},
			 {1,1,1,1,1},
			 {1,1,1,1,1},
			 {2,2,2,2,2}};
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.copyArray(expectedArray1)[y], expectedArray1[y]);
		}
		
		int[][] expectedArray2 = 
		    {{1,2,3,4,5},
			 {2,2,2,2,4},
			 {3,1,1,1,3},
			 {4,1,1,1,2},
			 {5,4,3,2,1}};
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.copyArray(expectedArray2)[y], expectedArray2[y]);
		}
	}

	@Test
	public void testConstructMessage() {
		String testmove = "0001";
		int[][] testOldBoard = 
		    {{1,1,2,2,1},
			 {1,1,2,2,1},
			 {2,2,1,1,2},
			 {2,2,1,1,2},
			 {1,1,0,0,0}};
		int[][] testNewBoard = 
		    {{1,1,2,2,1},
			 {1,1,2,2,1},
			 {2,2,1,1,2},
			 {2,2,1,1,2},
			 {1,1,2,2,0}};
		String expectedMessage = "0001:1|1|2|2|1|1|1|2|2|1|2|2|1|1|2|2|2|1|1|2|1|1|0|0|0:1|1|2|2|1|1|1|2|2|1|2|2|1|1|2|2|2|1|1|2|1|1|2|2|0";
		assertTrue(expectedMessage.equals(game.constructMessage(testmove, testOldBoard, testNewBoard)));
	}

	@Test
	public void testDeconstructMessage() {
		String message = "0001:1|1|2|2|1|1|1|2|2|1|2|2|1|1|2|2|2|1|1|2|1|1|0|0|0:1|1|2|2|1|1|1|2|2|1|2|2|1|1|2|2|2|1|1|2|1|1|1|1|0";
		String expectedMove = "0001";
		String expectedOldBoard = "1|1|2|2|1|1|1|2|2|1|2|2|1|1|2|2|2|1|1|2|1|1|0|0|0";
		String expectedNewBoard = "1|1|2|2|1|1|1|2|2|1|2|2|1|1|2|2|2|1|1|2|1|1|1|1|0";
		String[] messageParts = game.deconstructMessage(message);
		assertTrue(messageParts[0].equals(expectedMove));
		assertTrue(messageParts[1].equals(expectedOldBoard));
		assertTrue(messageParts[2].equals(expectedNewBoard));
	}

	@Test
	public void testConstructInitialMessage() {
		String expectedString = "0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0:2:5:5";
		int[][] testBoard = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		game.setBoard(testBoard);
		assertTrue(game.constructInitialMessage().equals(expectedString));
	}

	@Test
	public void testValidateAndApplyMoveStringIntArrayArrayIntArrayArray() {
		int[][] testBoard = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		int[][] expectedBoard = {
				 {2,2,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		game.setBoard(testBoard);
		assertTrue(game.validateAndApplyMove("0001", testBoard, expectedBoard));
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.getBoard()[y], expectedBoard[y]);
		}
	}

	@Test
	public void testValidateAndApplyMoveString() {
		int[][] testBoard = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		int[][] expectedBoard = {
				 {1,1,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		game.setBoard(testBoard);
		assertTrue(game.validateAndApplyMove("0001"));
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(game.getBoard()[y], expectedBoard[y]);
		}
	}
}
