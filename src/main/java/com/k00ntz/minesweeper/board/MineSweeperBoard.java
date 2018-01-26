package com.k00ntz.minesweeper.board;

import java.util.*;


/**
 * Creates a minesweeper board, only considers mine density (if provided) in whole percentages
 */
public class MineSweeperBoard {

    private final List<List<MSquare>> board;

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

    public boolean isRevealed(int x, int y) {
        return board.get(y).get(x).isRevealed();
    }

    public boolean validationXY(int x, int y) {
        return x > 0 && y > 0 && x < board.size() - 1 && y < board.size() - 1;
    }

    public State flag(int x, int y) {
        if (!validationXY(x, y)) {
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
        MSquare guessSquare = board.get(y).get(x);
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
        if (((board.size() - 2) * (board.size() - 2)) - mineCount == countRevealed) {
            state = State.WON;
            return state;
        } else {
            return state;
        }
    }

    public State revealSurrounding(int x, int y) {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                try {
                    State newState = guess(x + k, y + l);
                    if (State.PLAYING != newState) return newState;
                } catch (IllegalArgumentException e) {
                    //do nothing, we don't want to reveal the edges
                }
            }
        }
        return State.PLAYING;
    }

    public MSquare get(int x, int y){
        return board.get(y).get(x);
    }

    public int getSurroundingFlagsCount(int x, int y){
        int cnt = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (board.get(y + k).get(x + l).isFlagged())
                    cnt++;
            }
        }
        return cnt;
    }

    public static <E> String padRight(E s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public String printBoard() {
        StringBuilder header = new StringBuilder("   "); // one for number, one for edge
        StringBuilder rows = new StringBuilder();
        int padSize = String.valueOf(board.size()).length() + 1;
        for (int i = 0; i < board.size(); i++) {
            if (i > 0 && i < board.size() - 1) {
                header.append(padRight(i, padSize));
                rows.append(padRight(i, padSize));
            } else {
                header.append(padRight("", padSize));
                rows.append(padRight("", padSize));
            }

            List<MSquare> row = board.get(i);

            for (int j = 0; j < board.get(i).size(); j++) {
                rows.append(padRight(row.get(j), padSize));
            }
            if (i > 0 && i < board.size() - 1) {
                rows.append(padRight(i, padSize));
            }
            rows.append("\n");
        }
        StringBuilder finalOutput = new StringBuilder(header);
        finalOutput.append("\n").append(rows).append(header);
        return finalOutput.toString();
    }

}
