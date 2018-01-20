package com.k00ntz.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Creates a minesweeper board, only considers mine density (if provided) in whole percentages
 */
public class MineSweeperBoard {

    private final List<List<MineSquare>> board;

    private final int mineCount;

    private int countRevealed;

    public enum State {
        PLAYING, WON, LOST
    }

    private State state = State.PLAYING;

    public MineSweeperBoard(int size) {
        MineSweeperBoardFactory factory = new MineSweeperBoardFactory(size);
        this.board = factory.build();
        this.mineCount = factory.getMineCount();

    }

    public boolean validationXY(int x, int y) {
        return x > 0 && y > 0 && x < board.size() -1 && y < board.size() -1;
    }

    public State flag(int x, int y){
        if(!validationXY(x,y)) {
            throw new IllegalArgumentException(
                    String.format("Invalid x y combo, x %s and/or y %s out of range %s", x, y, board.size()));
        }
        board.get(y).get(x).setFlagged();
        return state;
    }

    public State guess(int x, int y) {
        if (!validationXY(x, y)) {
            throw new IllegalArgumentException(
                    String.format("Invalid x y combo, x %s and/or y %s out of range %s", x, y, board.size()));
        }
        MineSquare guessSquare = board.get(y).get(x);
        if (!guessSquare.isRevealed() && !guessSquare.isFlagged()) {
            if (guessSquare.reveal()) {
                state = State.LOST;
                return state;
            } else {
                countRevealed++;
                if (board.get(y).get(x).getSurroundingMineCount() == 0) {
                    revealSurrounding(x, y);
                }
            }
        }
        if (((board.size()-2) * (board.size()-2)) - mineCount == countRevealed) {
            state = State.WON;
            return state;
        } else {
            return state;
        }
    }

    private void revealSurrounding(int x, int y) {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                try {
                    guess(x + k, y + l);
                } catch (IllegalArgumentException e){
                    //do nothing, we don't want to reveal the edges
                }
            }
        }
    }

    public String printBoard() {
        StringBuilder header = new StringBuilder("  "); // one for number, one for edge
        StringBuilder rows = new StringBuilder();
        for(int i = 0; i < board.size(); i++){
            header.append(i).append(" ");
            List<MineSquare> row = board.get(i);
            rows.append(i).append(" ");
            for(int j = 0; j < board.get(i).size(); j++){
                rows.append(row.get(j).toString()).append(" ");
            }
            rows.append("x").append("\n");
        }
        header.append("\n").append(rows);
        return header.toString();
    }


    class MineSweeperBoardFactory {

        //could be short... only need up to 8
        private final int[][] board;

        private int mineCount;

        private static final double DEFAULT_PERCENT_MINES = .1;

        public MineSweeperBoardFactory(int size, double mineDensityPercent, long seed) {
            this.board =
                    createBoard(size, seed, mineDensityPercent);
        }

        public List<List<MineSquare>> build() {
            List<List<MineSquare>> mineBoard = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                List<MineSquare> row = new ArrayList<>();
                for (int j = 0; j < board[i].length; j++) {
                    row.add(new MineSquare(board[i][j]));
                }
                mineBoard.add(row);
            }
            return mineBoard;
        }

        public int getMineCount() {
            return mineCount;
        }

        public MineSweeperBoardFactory(int size, double mineDensityPercent) {
            this.board =
                    createBoard(size, System.currentTimeMillis(), DEFAULT_PERCENT_MINES);
        }

        public MineSweeperBoardFactory(int size) {
            this.board =
                    createBoard(size, System.currentTimeMillis(), DEFAULT_PERCENT_MINES);
        }

        int[][] createBoard(int size, long seed, double mineDensityPercent) {
            int[][] mineBoard = initializeBoard(size, seed, mineDensityPercent);
            fillInBoard(mineBoard);
            return mineBoard;
        }

        // I don't like methods like this...
        void fillInBoard(int[][] mineBoard) {
            for (int i = 1; i < mineBoard.length - 1; i++) {
                for (int j = 1; j < mineBoard[i].length - 1; j++) {
                    if (mineBoard[i][j] != -1) {
                        int sum = 0;
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                if (mineBoard[i + k][j + l] == -1) sum++;
                            }
                        }
                        mineBoard[i][j] = sum;
                    }
                }
            }
        }

        /**
         * Initializes board, at first they are 0, then we update mines with -1. Surrounds with a row and column of 0s for
         * easier computations later.
         *
         * @param size
         * @param seed
         * @param mineDensity
         * @return
         */
        int[][] initializeBoard(int size, long seed, double mineDensity) {
            Random r = new Random(seed);
            int[][] retBoard = new int[size + 1][size + 1];
            int bound = (int) (mineDensity * 100);
            for (int i = 1; i < retBoard.length - 1; i++) {
                int[] row = retBoard[i];
                for (int j = 1; j < row.length -1; j++) {
                    int rand = r.nextInt(100);
                    if (rand < bound) {
                        row[j] = -1;
                        mineCount++;
                    }
                }
            }
            return retBoard;
        }

    }

}
