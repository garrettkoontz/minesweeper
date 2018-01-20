package com.k00ntz.minesweeper;

import java.util.Scanner;

public class Main {

    private static final String DIRECTIONS = "Welcome to minesweeper. ";

    private static class Guess{
        boolean flag;
        int x;
        int y;

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
    }

    private Scanner scanner = new Scanner(System.in);

    private MineSweeperBoard mineSweeperBoard;

    private Guess nextGuess(){
        String guess = scanner.nextLine();
        return new Guess(guess.split(" "));
    }

    private boolean playGame(int size, long seed, double mineDensity){
        MineSweeperBoard board = new MineSweeperBoard(size);
        System.out.println(DIRECTIONS);
        printBoard(board);
        boolean quitFlag = false;
        boolean blankEntry = false;
        MineSweeperBoard.State st = MineSweeperBoard.State.PLAYING;
        while(!quitFlag && st == MineSweeperBoard.State.PLAYING){
            System.out.print("Please guess an x and y coordinate as safe, or flag a bomb: ");
            Guess g = null;
            try {
                g = nextGuess();
            } catch(IllegalArgumentException e){
                if(blankEntry){
                    quitFlag = true;
                } else {
                    blankEntry = true;
                }
            }
            if(!board.validationXY(g.x, g.y)){
                System.out.println("Invalid x or y coordinate, please try again.");
            } else {
                if (g.flag) {
                    st = board.flag(g.x, g.y);
                } else {
                    st = board.guess(g.x, g.y);
                }
            }
            printBoard(board);
        }
        if(st == MineSweeperBoard.State.WON){
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Sorry, you lost.");
        }
        return false;
    }

    public static void main (String[] args){
        System.out.print("Welcome to minesweeper. Please specify what size board you want to play:");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        Main main = new Main();
        if(main.playGame(size, System.currentTimeMillis(), .1)){
            Main.main(args);
        }
    }

    public void printBoard(MineSweeperBoard mineSweeperBoard){
        System.out.println(mineSweeperBoard.printBoard());
    }
}
