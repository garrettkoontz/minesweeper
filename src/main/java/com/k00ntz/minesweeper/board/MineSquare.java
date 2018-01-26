package com.k00ntz.minesweeper.board;

public class MineSquare implements MSquare {

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

    @Override
    public boolean isRevealed() {
        return isRevealed;
    }

    @Override
    public boolean isFlagged() {
        return isFlagged;
    }

    @Override
    public boolean isMine() {
        return isMine;
    }

    @Override
    public int getSurroundingMineCount() {
        return surroundingMineCount;
    }

    @Override
    public boolean reveal() {
        this.isRevealed = true;
        return isMine;
    }

    @Override
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
