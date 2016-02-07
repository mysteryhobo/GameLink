package org.gamelink.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class KalahTest {
	
	Kalah game = new Kalah(1);

	@Test
	public void testCalcScore() {
		int[][] testBoard1 = 
		    {{18,0,0,0,0,0,0},
			 {0,0,0,0,0,0,18}};
		int[] expectedScore1 = {18,18,3};
		int[][] testBoard2 = 
		    {{21,0,0,0,0,0,0},
			 {0,0,0,0,0,0,15}};
		int[] expectedScore2 = {15,21,2};
		int[][] testBoard3 = 
		    {{15,0,0,0,0,0,0},
			 {0,0,0,0,0,0,21}};
		int[] expectedScore3 = {21,15,1};
		int[][] testBoard4 = 
		    {{15,1,0,0,3,0,0},
			 {0,0,2,0,0,0,15}};
		int[] expectedScore4 = {21,15,1};
		game.setBoard(testBoard1);
		game.setWinner(0);
		assertArrayEquals(expectedScore1, game.calcScore());
		game.setBoard(testBoard2);
		game.setWinner(0);
		assertArrayEquals(expectedScore2, game.calcScore());
		game.setBoard(testBoard3);
		game.setWinner(0);
		assertArrayEquals(expectedScore3, game.calcScore());
		game.setBoard(testBoard4);
		assertArrayEquals(expectedScore4, game.calcScore());

	}

	@Test
	public void testIsGameOver() {
		int[][] testBoard1 = 
		    {{18,0,0,0,0,0,0},
			 {0,0,0,0,0,0,18}};
		int[][] testBoard2 = 
		    {{15,0,1,0,2,0,0},
			 {0,0,0,0,0,0,18}};
		int[][] testBoard3 = 
		    {{18,0,0,0,0,0,0},
			 {0,2,0,0,1,0,15}};
		int[][] testBoard4 = 
		    {{18,0,1,0,4,0,0},
			 {0,2,0,0,1,0,15}};
		game.setBoard(testBoard1);
		assertTrue(game.isGameOver());
		game.setBoard(testBoard2);
		assertTrue(game.isGameOver());
		game.setBoard(testBoard3);
		assertTrue(game.isGameOver());
		game.setBoard(testBoard4);
		assertTrue(!game.isGameOver());
	}

	@Test
	public void testAreBoardsEqual() {
		int[][] testBoard1 = 
		    {{5,4,3,2,2,1,0},
			 {0,0,6,7,0,0,18}};
		int[][] testBoard2 = 
		    {{5,4,3,2,2,1,0},
			 {0,0,6,7,0,0,18}};
		assertTrue(game.areBoardsEqual(testBoard1, testBoard2));
		
		testBoard2[0][0] = 7;
		assertTrue(!game.areBoardsEqual(testBoard1, testBoard2));
	}

	@Test
	public void testArrayToString() {
		int[][] testArray1 = 
		    {{5,4,3,2,2,1,0},
			 {0,0,6,7,0,0,18}};
		String expectedString1 = "5|4|3|2|2|1|0|0|0|6|7|0|0|18";
		assertTrue(game.arrayToString(testArray1).equals(expectedString1));
		
		int[][] testArray2 = 
		    {{1,1,2,2,3,3,0},
			 {1,2,3,4,5,6,7}};
		String expectedString2 = "1|1|2|2|3|3|0|1|2|3|4|5|6|7";
		assertTrue(game.arrayToString(testArray2).equals(expectedString2));
	}

	@Test
	public void testStringToArray() {
		int[][] expectedArray1 = 
		    {{5,4,3,2,2,1,0},
			 {0,0,6,7,0,0,18}};
		String testString1 = "5|4|3|2|2|1|0|0|0|6|7|0|0|18";
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(expectedArray1[y], game.stringToArray(testString1)[y]);
		}
		
		int[][] expectedArray2 = 
		    {{1,1,2,2,3,3,0},
			 {1,2,3,4,5,6,7}};
		String testString2 = "1|1|2|2|3|3|0|1|2|3|4|5|6|7";
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(expectedArray2[y], game.stringToArray(testString2)[y]);
		}
	}

	@Test
	public void testFlipBoard() {
		int[][] testArray1 = 
		    {{14,13,12,11,10,9,8},
			 {1,2,3,4,5,6,7}};
		int[][] expectedArray1 = 
		    {{7,6,5,4,3,2,1},
			 {8,9,10,11,12,13,14}};
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(expectedArray1[y], game.flipBoard(testArray1)[y]);
		}
	}

	@Test
	public void testCopyArray() {
		int[][] expectedArray1 = 
		    {{7,6,5,4,3,2,1},
			 {8,9,10,11,12,13,14}};
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.copyArray(expectedArray1)[y], expectedArray1[y]);
		}
		
		int[][] expectedArray2 = 
		    {{5,4,6,3,7,2,8},
			 {8,4,7,3,2,3,4}};
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.copyArray(expectedArray2)[y], expectedArray2[y]);
		}
	}

	@Test
	public void testMoveToCell() {
		assertEquals(game.moveToCell(1, 1), 0);
		assertEquals(game.moveToCell(1, 0), 6);
		assertEquals(game.moveToCell(2, 1), 1);
		assertEquals(game.moveToCell(2, 0), 5);
		assertEquals(game.moveToCell(3, 1), 2);
		assertEquals(game.moveToCell(3, 0), 4);
		assertEquals(game.moveToCell(4, 1), 3);
		assertEquals(game.moveToCell(4, 0), 3);
		assertEquals(game.moveToCell(5, 1), 4);
		assertEquals(game.moveToCell(5, 0), 2);
		assertEquals(game.moveToCell(6, 1), 5);
		assertEquals(game.moveToCell(6, 0), 1);
	}

	@Test
	public void testConstructMessage() {
		String testmove = "1";
		int[][] testOldBoard = 
		    {{7,6,5,4,3,2,1},
			 {8,9,10,11,12,13,14}};
		int[][] testNewBoard = 
		    {{7,6,5,4,3,2,1},
			 {8,9,10,12,12,12,14}};
		int testTurn = 1;
		String expectedMessage = "1:7|6|5|4|3|2|1|8|9|10|11|12|13|14:7|6|5|4|3|2|1|8|9|10|12|12|12|14:1";
		assertTrue(expectedMessage.equals(game.constructMessage(testmove, testOldBoard, testNewBoard, testTurn)));
		
	}

	@Test
	public void testConstructInitialMessage() {
		int[][] testOldBoard = 
		    {{7,6,5,4,3,2,1},
			 {8,9,10,11,12,13,14}};
		game.setBoard(testOldBoard);
		String expectedMessage = "7|6|5|4|3|2|1|8|9|10|11|12|13|14:3:7";
		assertTrue(game.constructInitialMessage().equals(expectedMessage));
	}

	@Test
	public void testDeconstructMessage() {
		String testMove = "1";
		String testTurn = "1";
		String testOldBoard = "7|6|5|4|3|2|1|8|9|10|11|12|13|14";
		String testNewBoard = "7|6|5|4|3|2|1|8|9|10|12|12|12|14";
		String[] messageParts = game.deconstructMessage("1:7|6|5|4|3|2|1|8|9|10|11|12|13|14:7|6|5|4|3|2|1|8|9|10|12|12|12|14:1");
		assertTrue(messageParts[0].equals(testMove));
		assertTrue(messageParts[1].equals(testOldBoard));
		assertTrue(messageParts[2].equals(testNewBoard));
		assertTrue(messageParts[3].equals(testTurn));
	}

	@Test
	public void testValidateAndApplyMoveStringIntArrayArrayIntArrayArrayString() {
		int[][] testOldBoard = 
		    {{16,0,0,1,0,0,1},
			    {0,1,0,0,1,0,16}};
		int[][] testNewBoard = 
		    {{18,0,0,1,0,0,0},
			    {0,1,0,0,0,0,16}};
		game.setBoard(testOldBoard);
		assertTrue(game.validateAndApplyMove("1", game.flipBoard(testOldBoard), game.flipBoard(testNewBoard), "1"));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], testNewBoard[y]);
		}
	}

	@Test
	public void testValidateAndApplyMoveString() {
		int[][] testOldBoard = 
		    {{16,0,0,1,0,0,1},
			    {0,1,0,0,1,0,16}};
		int[][] testNewBoard = 
		    {{16,0,0,0,0,0,1},
			    {0,0,0,0,1,0,18}};
		game.setBoard(testOldBoard);
		assertTrue(game.validateAndApplyMove("2"));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], testNewBoard[y]);
		}
	}

	@Test
	public void testApplyMove() {
		int[][] testBoard1 = 
		    {{16,0,0,1,0,0,1},
			    {0,4,0,0,1,0,16}};
		int[][] expectedBoard1 = 
		    {{16,0,0,1,0,0,0},
			    {0,0,1,1,2,0,18}};
		game.setBoard(testBoard1);
		assertTrue(!game.applyMove(testBoard1, 2, 1));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard1[y]);
		}
		
		int[][] testBoard2 = 
		    {{16,0,0,1,0,0,1},
			    {0,5,0,0,1,0,16}};
		int[][] expectedBoard2 = 
		    {{16,0,0,1,0,0,1},
			    {0,0,1,1,2,1,17}};
		game.setBoard(testBoard2);
		assertTrue(game.applyMove(testBoard2, 2, 1));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard2[y]);
		}
		
		int[][] testBoard3 = 
		    {{16,0,0,1,0,0,1},
			    {0,4,0,0,1,0,16}};
		int[][] expectedBoard3 = 
		    {{21,0,0,0,0,0,1},
			    {0,0,0,0,1,0,16}};
		game.setBoard(testBoard3);
		assertTrue(!game.applyMove(testBoard3, 4, 0));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard3[y]);
		}
		
		int[][] testBoard4 = 
		    {{16,1,0,1,0,0,1},
			    {0,4,0,0,1,0,16}};
		int[][] expectedBoard4 = 
		    {{17,0,0,1,0,0,1},
			    {0,4,0,0,1,0,16}};
		game.setBoard(testBoard4);
		assertTrue(game.applyMove(testBoard4, 6, 0));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard4[y]);
		}
		
		int[][] testBoard5 = 
		    {{16,0,0,1,0,0,0},
			    {0,4,0,0,3,0,16}};
		int[][] expectedBoard5 = 
		    {{16,0,0,1,0,0,1},
			    {0,4,0,0,0,1,17}};
		game.setBoard(testBoard5);
		assertTrue(!game.applyMove(testBoard5, 5, 1));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard5[y]);
		}
		
		int[][] testBoard6 = 
		    {{16,0,0,5,0,0,0},
			    {0,0,0,0,3,0,16}};
		int[][] expectedBoard6 = 
		    {{17,1,1,0,0,0,0},
			    {1,1,0,0,3,0,16}};
		game.setBoard(testBoard6);
		assertTrue(!game.applyMove(testBoard6, 4, 0));
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard6[y]);
		}
	}

	@Test
	public void testWipeBoard() {
		int[][] testBoard1 = 
		    {{16,0,4,1,3,0,1},
			    {0,0,0,0,0,0,16}};
		int[][] expectedBoard1 = 
		    {{25,0,0,0,0,0,0},
			    {0,0,0,0,0,0,16}};
		game.setBoard(testBoard1);
		game.wipeBoard();
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard1[y]);
		}
		
		int[][] testBoard2 = 
		    {{16,0,0,0,0,0,0},
			    {0,5,0,1,1,0,15}};
		int[][] expectedBoard2 = 
		    {{16,0,0,0,0,0,0},
			    {0,0,0,0,0,0,22}};
		game.setBoard(testBoard2);
		game.wipeBoard();
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getGameBoard()[y], expectedBoard2[y]);
		}
	}

	@Test
	public void testGetNextX() {
		assertEquals(game.getNextX(1, 0), 1);
		assertEquals(game.getNextX(1, 1), 2);
		assertEquals(game.getNextX(1, 2), 3);
		assertEquals(game.getNextX(1, 3), 4);
		assertEquals(game.getNextX(1, 4), 5);
		assertEquals(game.getNextX(1, 5), 6);
		assertEquals(game.getNextX(1, 6), 6);
		assertEquals(game.getNextX(0, 0), 0);
		assertEquals(game.getNextX(0, 1), 0);
		assertEquals(game.getNextX(0, 2), 1);
		assertEquals(game.getNextX(0, 3), 2);
		assertEquals(game.getNextX(0, 4), 3);
		assertEquals(game.getNextX(0, 5), 4);
		assertEquals(game.getNextX(0, 6), 5);
	}

	@Test
	public void testGetNextY() {
		assertEquals(game.getNextY(1, 0), 1);
		assertEquals(game.getNextY(1, 1), 1);
		assertEquals(game.getNextY(1, 2), 1);
		assertEquals(game.getNextY(1, 3), 1);
		assertEquals(game.getNextY(1, 4), 1);
		assertEquals(game.getNextY(1, 5), 1);
		assertEquals(game.getNextY(1, 6), 0);
		assertEquals(game.getNextY(0, 0), 1);
		assertEquals(game.getNextY(0, 1), 0);
		assertEquals(game.getNextY(0, 2), 0);
		assertEquals(game.getNextY(0, 3), 0);
		assertEquals(game.getNextY(0, 4), 0);
		assertEquals(game.getNextY(0, 5), 0);
		assertEquals(game.getNextY(0, 6), 0);
	}

	@Test
	public void testGetOppCell() {
		assertEquals(game.getOppCell(0, 1), 1);
		assertEquals(game.getOppCell(1, 1), 2);
		assertEquals(game.getOppCell(2, 1), 3);
		assertEquals(game.getOppCell(3, 1), 4);
		assertEquals(game.getOppCell(4, 1), 5);
		assertEquals(game.getOppCell(5, 1), 6);
		assertEquals(game.getOppCell(1, 0), 0);
		assertEquals(game.getOppCell(2, 0), 1);
		assertEquals(game.getOppCell(3, 0), 2);
		assertEquals(game.getOppCell(4, 0), 3);
		assertEquals(game.getOppCell(5, 0), 4);
		assertEquals(game.getOppCell(6, 0), 5);
	}
	
	@Test
	public void testgetBoard() {
		int[][] testBoard1 = 
			{{1,2,3,4,5,6,7},
			 {1,2,3,4,5,6,7}};
		int[][] expectedBoard1 = 
			{{1,2,3,4,5,6,7,0},
			 {0,1,2,3,4,5,6,7}};
		game.setBoard(testBoard1);
		for (int y = 0; y < 2; y ++){
			assertArrayEquals(game.getBoard()[y], expectedBoard1[y]);
		}
	}
}
