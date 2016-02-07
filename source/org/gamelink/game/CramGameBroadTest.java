package org.gamelink.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class CramGameBoardTest {

	@Test
	public void testCramGameBoardIntIntIntBoolean() {
		CramGameBoard testBoard1 = new CramGameBoard(2, 5, 5, false);
		testBoard1.printGameBoard();
	}

	@Test
	public void testCramGameBoardIntArrayArrayBoolean() {
		CramGameBoard testGameBoard1 = new CramGameBoard("[1, 1, 0, 2, 99]|[0, 2, 2, 2, 0]|[1, 1, 99, 2, 2]|[0, 0, 0, 1, 1]|[0, 0, 0, 0, 0]", 5, 5, false);
		int[][] testBoard1 = testGameBoard1.getBoard();
		int[][] expectedBoard1 =
		    {{1,1,0,2,99},
			 {0,2,2,2,0},
			 {1,1,99,2,2},
			 {0,0,0,1,1},
			 {0,0,0,0,0}};
		for (int y = 0; y < testBoard1.length; y ++){
			assertArrayEquals(testBoard1[y], expectedBoard1[y]);
		}

		CramGameBoard testGameBoard2 = new CramGameBoard("[0, 0, 0, 0, 99]|[0, 0, 0, 0, 0]|[0, 0, 99, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]", 5, 5, false);
		int[][] testBoard2 = testGameBoard2.getBoard();
		int[][] expectedBoard2 =
		    {{0,0,0,0,99},
			 {0,0,0,0,0},
			 {0,0,99,0,0},
			 {0,0,0,0,0},
			 {0,0,0,0,0}};
		for (int y = 0; y < testBoard2.length; y ++){
			assertArrayEquals(testBoard2[y], expectedBoard2[y]);
		}

		CramGameBoard testGameBoard3 = new CramGameBoard("[1, 1, 1, 1, 99]|[2, 2, 99, 2, 2]|[1, 2, 99, 1, 2]|[99, 99, 99, 99, 99]|[1, 1, 1, 1, 1]", 5, 5, false);
		int[][] testBoard3 = testGameBoard3.getBoard();
		int[][] expectedBoard3 =
		    {{1,1,1,1,99},
			 {2,2,99,2,2},
			 {1,2,99,1,2},
			 {99,99,99,99,99},
			 {1,1,1,1,1}};
		for (int y = 0; y < testBoard3.length; y ++){
			assertArrayEquals(testBoard3[y], expectedBoard3[y]);
		}
	}

	@Test
	public void testIsGameOver() {
		CramGameBoard testGameBoard1 = new CramGameBoard ("[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]", 5, 5, false);
		assertTrue(!testGameBoard1.isGameOver());
		CramGameBoard testGameBoard2 = new CramGameBoard ("[1, 1, 1, 1, 1]|[1, 1, 1, 1, 1]|[1, 1, 1, 1, 1]|[1, 1, 1, 1, 1]|[1, 1, 1, 1, 1]", 5, 5, false);
		assertTrue(testGameBoard2.isGameOver());
		CramGameBoard testGameBoard3 = new CramGameBoard ("[1, 1, 2, 2, 99]|[1, 2, 2, 1, 1]|[1, 0, 2, 0, 2]|[1, 1, 2, 1, 2]|[2, 2, 99, 1, 0]", 5, 5, false);
		assertTrue(testGameBoard3.isGameOver());
		CramGameBoard testGameBoard4 = new CramGameBoard ("[1, 1, 2, 2, 99]|[1, 2, 2, 1, 1]|[0, 0, 2, 0, 2]|[1, 1, 2, 1, 2]|[2, 2, 99, 1, 0]", 5, 5, false);
		assertTrue(!testGameBoard4.isGameOver());
	}

	@Test
	public void testCleanUp() {
		CramGameBoard testGameBoard1 = new CramGameBoard ("[1, 1, 2, 2, 99]|[0, 2, 2, 1, 1]|[1, 2, 2, 1, 1]|[1, 2, 1, 1, 2]|[0, 2, 99, 0, 2]", 5, 5, false);
		testGameBoard1.cleanUp(1);
		assertEquals(testGameBoard1.getWinner(), 1);
		assertEquals(testGameBoard1.getPlayer1Score(), 3);
		assertEquals(testGameBoard1.getPlayer2Score(), 0);

		CramGameBoard testGameBoard2 = new CramGameBoard ("[1, 1, 2, 2, 99]|[0, 2, 2, 1, 1]|[1, 2, 2, 1, 1]|[1, 2, 1, 1, 2]|[0, 2, 99, 0, 2]", 5, 5, false);
		testGameBoard2.cleanUp(2);
		assertEquals(testGameBoard2.getWinner(), 2);
		assertEquals(testGameBoard2.getPlayer1Score(), 0);
		assertEquals(testGameBoard2.getPlayer2Score(), 3);

		CramGameBoard testGameBoard3 = new CramGameBoard ("[1, 1, 2, 2, 99]|[0, 2, 2, 1, 1]|[1, 2, 2, 1, 1]|[1, 2, 1, 1, 2]|[0, 2, 99, 0, 2]", 5, 5, false);
		testGameBoard3.cleanUp(3);
		assertEquals(testGameBoard3.getWinner(), 3);
		assertEquals(testGameBoard3.getPlayer1Score(), 0);
		assertEquals(testGameBoard3.getPlayer2Score(), 0);
	}

	@Test
	public void testValidateAndApplyMoveStringIntArrayArrayIntArrayArray() {
		CramGameBoard testGameBoard1 = new CramGameBoard ("[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]", 5, 5, false);
		int[][] oldBoard1 = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		int[][] newBoard1 = {
				 {1,1,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		assertTrue(testGameBoard1.validateAndApplyMove("0001", oldBoard1, newBoard1));
		assertEquals(testGameBoard1.getPlayer1Score(), 2);
		assertEquals(testGameBoard1.getPlayer2Score(), 0);

		for (int y = 0; y < 5; y ++){
			assertArrayEquals(testGameBoard1.getBoard()[y], newBoard1[y]);
		}

		CramGameBoard testGameBoard2 = new CramGameBoard ("[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]", 5, 5, false);
		int[][] oldBoard2 = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		int[][] newBoard2 = {
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		assertTrue(!testGameBoard2.validateAndApplyMove("0000", oldBoard2, newBoard2));
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(testGameBoard2.getBoard()[y], newBoard2[y]);
		}
	}


	@Test
	public void testValidateAndApplyMoveString() {
		CramGameBoard testGameBoard1 = new CramGameBoard ("[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]", 5, 5, false);
		int[][] expectedBoard1 = {
				 {2,2,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		assertTrue(testGameBoard1.validateAndApplyMove("0001"));
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(testGameBoard1.getBoard()[y], expectedBoard1[y]);
		}

		CramGameBoard testGameBoard2 = new CramGameBoard ("[1, 1, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]|[0, 0, 0, 0, 0]", 5, 5, false);
		int[][] expectedBoard2 = {
				 {1,1,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0},
				 {0,0,0,0,0}};
		assertTrue(!testGameBoard2.validateAndApplyMove("0001"));
		for (int y = 0; y < 5; y ++){
			assertArrayEquals(testGameBoard2.getBoard()[y], expectedBoard2[y]);
		}
	}

}
