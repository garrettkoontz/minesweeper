package com.k00ntz.minesweeper;

public class Guess{
    private boolean flag;
    private boolean chord;
    private int x;
    private int y;

    public Guess(String[] guess){
        if(guess.length < 2) throw new IllegalArgumentException();
        if(guess.length > 2){
            this.flag = guess[0].equalsIgnoreCase("F");
            this.chord = guess[0].equalsIgnoreCase("C");
            this.x = Integer.parseInt(guess[1]);
            this.y = Integer.parseInt(guess[2]);
        } else {
            this.flag = guess[0].startsWith("F");
            this.chord = guess[0].startsWith("C");
            this.x = Integer.parseInt(guess[0].replaceAll("[A-z]", ""));
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

    public boolean isChord(){
        return chord;
    }
}
