package com.k00ntz.minesweeper;

import com.k00ntz.minesweeper.board.MineSweeperBoard;

import java.util.Scanner;

public class Game {

    private static final String DIRECTIONS = "Welcome to minesweeper. ";

    private Scanner scanner = new Scanner(System.in);

    private MineSweeperBoard mineSweeperBoard;

    private Guess nextGuess(){
        String guess = scanner.nextLine();
        return new Guess(guess.split(" "));
    }

    public void printBoard(MineSweeperBoard mineSweeperBoard){
        System.out.println(mineSweeperBoard.printBoard());
    }

    public boolean playGame(int size, long seed, double mineDensity){
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
            if(!board.validationXY(g.getX(), g.getY())){
                System.out.println("Invalid x or y coordinate, please try again.");
            } else {
                if (g.isFlag()) {
                    st = board.flag(g.getX(), g.getY());
                } else {
                    st = board.guess(g.getX(), g.getY());
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
}
