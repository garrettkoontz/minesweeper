package com.k00ntz.minesweeper.board;

public class MineSquare {

    private final boolean isMine;
    private final int surroundingMineCount;
    private boolean isRevealed;
    private boolean isFlagged;

    public MineSquare(int val) {
        this.isMine = val == -1;
        this.surroundingMineCount = val;
        this.isRevealed = false;
        this.isFlagged = false;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getSurroundingMineCount() {
        return surroundingMineCount;
    }

    public boolean reveal() {
        this.isRevealed = true;
        return isMine;
    }

    public void setFlagged() {
        this.isFlagged = !this.isFlagged;
    }

    @Override
    public String toString() {
        return isFlagged ? "F" :
                (!isRevealed ? "x" :
                        (isMine ? "*" :
                                ((surroundingMineCount > 0) ? String.valueOf(surroundingMineCount) : " ")));

    }

}
