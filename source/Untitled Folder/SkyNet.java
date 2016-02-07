package algorithms;

import org.gamelink.game.Algo;
import org.gamelink.game.Cram;

import java.util.*;
import java.util.stream.Collectors;

public class SkyNet extends Algo {

    public static String getTeamName(){
        return "Team SkyNet";
    }

    public static void main(String[] args) {
        Cram game = new Cram(false);
        game.startGame(SkyNet.class);
    }

    public static String algorithm(Cram game) {
        return getMove(game.getBoard());
    }

    private static Map<Long, Byte> pointsCache = new HashMap<>();

    private static byte numRows;
    private static byte rowLength;
    private static byte numSquares;
    private static byte numReservedSquares;

    private static byte countReservedSquares(int[][] board) {
        byte counter = 0;

        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 99) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static long boardToKey(int[][] board) {
        long counter = 0, result = 0;

        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) {
                    result |= ((long)1 << counter);
                }
                counter++;
            }
        }
        return result;
    }

    private static long addMoveToKey(String move, long key) {
        int row1 = Integer.parseInt(move.substring(0, 1)),
                col1 = Integer.parseInt(move.substring(1, 2)),
                row2 = Integer.parseInt(move.substring(2, 3)),
                col2 = Integer.parseInt(move.substring(3, 4));

        key |= ((long)1 << (row1 * rowLength + col1));
        key |= ((long)1 << (row2 * rowLength + col2));
        return key;
    }

    private static long flipTurnBit(long key) {
        return key ^ ((long)1 << numSquares);
    }

    private static byte getCache(long key) {
        Byte points = pointsCache.get(key);
        if (points != null)
            return points;
        return 0;
    }

    private static void putCache(long key, byte value) {
        pointsCache.put(key, value);
    }

    private static String chooseRandomMove(List<String> moves) {
        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }

    public static String getMove(int[][] board) {
        System.out.println();

        List<String> moves = getAllPossibleMoves(board);
        String bestMove;

        try {
            if ((board.length * board[0].length) > 63)
                throw new IllegalArgumentException("Board must have 63 squares or less");

            numRows = (byte)board.length;
            rowLength = (byte)board[0].length;
            numSquares = (byte)(numRows * rowLength);
            numReservedSquares = countReservedSquares(board);

            bestMove = findBestMove(boardToKey(board), moves);
        }
        catch (Throwable e) {
            // In case something horrible happens, just pick a random move.
            e.printStackTrace();
            bestMove = chooseRandomMove(moves);
            System.out.println();
            System.out.println("  ================================");
            System.out.println("  ||  THE AI IS STILL RUNNING!  ||");
            System.out.println("  ||    THIS AI DON'T QUIT!     ||");
            System.out.println("  ================================");
        }

        System.out.println();
        return bestMove;
    }

    private static List<String> getAllPossibleMoves(int[][] board) {
        List<String> possibilities = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            int[] row = board[rowIndex];
            int[] nextRow = (rowIndex < board.length-1) ? board[rowIndex+1] : null;

            for (int colIndex = 0; colIndex < row.length; colIndex++) {
                if (row[colIndex] != 0){
                    continue;
                }

                // Check to the right.
                if (colIndex < row.length-1 && row[colIndex+1] == 0) {
                    possibilities.add("" + rowIndex + colIndex + rowIndex + (colIndex + 1));
                }

                // Check downward.
                if (nextRow != null && nextRow[colIndex] == 0) {
                    possibilities.add("" + rowIndex + colIndex + (rowIndex + 1) + colIndex);
                }
            }
        }
        return possibilities;
    }

    private static String findBestMove(long boardKey, List<String> moves) {
        System.out.println(moves.size() + " possible move(s).");

        if (moves.size() > 38) {
            return chooseRandomMove(moves);
        }

        Map<String, Byte> scores = new HashMap<>();

        for (String move : moves) {
            List<String> filteredMoves = filterMovesThatOverlapWithMove(moves, move);

            // If there's no more moves, move is a winning move.
            if (filteredMoves.size() == 0) {
                return move;
            }

            // Take the move and calculate the maximum possible points that result.
            long newKey = flipTurnBit( addMoveToKey(move, boardKey) );
            byte points = calculateMaxPoints(newKey, filteredMoves);
            System.out.println("Move: " + move + "   Points: " + points);
            scores.put(move, points);
        }

        String bestMove = null;
        {
            byte bestScore = 0;

            for (Map.Entry<String, Byte> scoreEntry : scores.entrySet()) {
                if (scoreEntry.getValue() >= bestScore) {
                    bestScore = scoreEntry.getValue();
                    bestMove = scoreEntry.getKey();
                }
            }
        }
        return bestMove;
    }

    private static List<String> filterMovesThatOverlapWithMove(List<String> moves, String move) {
        return moves
                .stream()
                .filter(move2 ->
                        !checkCollision(move.toCharArray(), move2.toCharArray()))
                .collect(Collectors.toList());
    }

    private static boolean checkCollision(char[] move1, char[] move2) {
        return move1[0] == move2[0] && move1[1] == move2[1] ||
                move1[0] == move2[2] && move1[1] == move2[3] ||
                move1[2] == move2[0] && move1[3] == move2[1] ||
                move1[2] == move2[2] && move1[3] == move2[3];
    }

    private static byte calculatePointsForFinalBoard(long boardKey) {
        int emptySquares = 0;
        for (int i = 0; i < numSquares; i++) {
            long bit = (boardKey >> i) & 1;
            if (bit == 0)
                emptySquares++;
        }

        int numMoves = (numSquares - emptySquares - numReservedSquares) / 2;
        int numWinnerMoves = (numMoves / 2) + (numMoves % 2);
        int numLoserMoves = numMoves - numWinnerMoves;

        if (((boardKey >> numSquares) & 1) == 1) {
            return (byte)(numWinnerMoves * 2 + emptySquares);
        }
        else {
            return (byte)(numLoserMoves * 2);
        }
    }

    private static byte calculateMaxPoints(long boardKey, List<String> moves) {
        {
            byte cached = getCache(boardKey);
            if (cached != 0) {
                return cached;
            }
        }

        if (moves.size() == 0) {
            byte points = calculatePointsForFinalBoard(boardKey);
            putCache(boardKey, points);
            return points;
        }

        byte mostPoints = 0, leastPoints = Byte.MAX_VALUE;

        for (String move : moves) {
            List<String> filteredMoves = filterMovesThatOverlapWithMove(moves, move);
            long newKey = flipTurnBit( addMoveToKey(move, boardKey) );
            byte points = calculateMaxPoints(newKey, filteredMoves);

            if (points > mostPoints) {
                mostPoints = points;
            }
            if (points < leastPoints) {
                leastPoints = points;
            }
        }

        boolean ourMove = ((boardKey >> numSquares) & 1) == 0;
        byte returnValue = ourMove ? mostPoints : leastPoints;
        putCache(boardKey, returnValue);
        return returnValue;
    }
}
