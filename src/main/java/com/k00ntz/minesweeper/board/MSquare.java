package com.k00ntz.minesweeper.board;

public interface MSquare {
    boolean isRevealed();

    boolean isFlagged();

    boolean isMine();

    int getSurroundingMineCount();

    boolean reveal();

    void setFlagged();

    @Override
    String toString();
}
