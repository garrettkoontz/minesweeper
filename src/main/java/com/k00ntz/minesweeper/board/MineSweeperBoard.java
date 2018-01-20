package com.k00ntz.minesweeper.board;

import java.util.*;


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
        if (((board.size() - 2) * (board.size() - 2)) - mineCount == countRevealed) {
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
                } catch (IllegalArgumentException e) {
                    //do nothing, we don't want to reveal the edges
                }
            }
        }
    }

    public String printBoard() {
        StringBuilder header = new StringBuilder("   "); // one for number, one for edge
        StringBuilder rows = new StringBuilder();
        int maxSize = String.valueOf(board.size()).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxSize; i ++){
            sb.append(" ");
        }
        for (int i = 0; i < board.size(); i++) {

            header.append(i).append(sb);

            List<MineSquare> row = board.get(i);

            rows.append(i).append(sb);

            for (int j = 0; j < board.get(i).size(); j++) {
                rows.append(row.get(j).toString()).append(sb);
            }
            rows.append("x").append("\n");
        }
        header.append("\n").append(rows);
        return header.toString();
    }

}
