package com.k00ntz.minesweeper.board;

public enum EdgeSquare implements MSquare{
    VERTICAL{
        @Override
        public boolean isRevealed() {
            return false;
        }

        @Override
        public boolean isFlagged() {
            return false;
        }

        @Override
        public boolean isMine() {
            return false;
        }

        @Override
        public int getSurroundingMineCount() {
            return 0;
        }

        @Override
        public boolean reveal() {
            return false;
        }

        @Override
        public void setFlagged() {

        }

        public String toString(){
            return "|";
        }
    }, HORIZONTAL{
        @Override
        public boolean isRevealed() {
            return false;
        }

        @Override
        public boolean isFlagged() {
            return false;
        }

        @Override
        public boolean isMine() {
            return false;
        }

        @Override
        public int getSurroundingMineCount() {
            return 0;
        }

        @Override
        public boolean reveal() {
            return false;
        }

        @Override
        public void setFlagged() {

        }

        public String toString(){
            return "-";
        }
    }

}
