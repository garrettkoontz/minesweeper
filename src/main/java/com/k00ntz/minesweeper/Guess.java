package com.k00ntz.minesweeper;

public class Guess{
    private boolean flag;
    private int x;
    private int y;

    public Guess(String[] guess){
        if(guess.length < 2) throw new IllegalArgumentException();
        if(guess.length > 2){
            this.flag = true;
            this.x = Integer.parseInt(guess[1]);
            this.y = Integer.parseInt(guess[2]);
        } else {
            this.x = Integer.parseInt(guess[0]);
            this.y = Integer.parseInt(guess[1]);
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isFlag(){
        return flag;
    }
}
