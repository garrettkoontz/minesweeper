package com.k00ntz.minesweeper;

import com.k00ntz.minesweeper.board.MineSweeperBoard;

import java.util.Scanner;

public class Game {

    private static final String DIRECTIONS = "Welcome to minesweeper. ";

    private Scanner scanner = new Scanner(System.in);

    private Guess nextGuess() {
        String guess = scanner.nextLine();
        return new Guess(guess.split(" "));
    }

    public void printBoard(MineSweeperBoard mineSweeperBoard) {
        System.out.println(mineSweeperBoard.printBoard());
    }

    public boolean playGame(int size) {
        MineSweeperBoard board = new MineSweeperBoard(size);
        System.out.println(DIRECTIONS);
        printBoard(board);
        boolean quitFlag = false;
        boolean blankEntry = false;
        MineSweeperBoard.State st = MineSweeperBoard.State.PLAYING;
        while (!quitFlag && st == MineSweeperBoard.State.PLAYING) {
            System.out.print("Please guess an x and y coordinate as safe, or flag a bomb: ");
            Guess g;
            try {
                g = nextGuess();
            } catch (IllegalArgumentException | NullPointerException e) {
                if (blankEntry) {
                    quitFlag = true;
                } else {
                    blankEntry = true;
                }
                continue;
            }
            int x = g.getX();
            int y = g.getY();
            st = makeGuess(board, x, y, g);
            printBoard(board);
        }
        if (st == MineSweeperBoard.State.WON) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Sorry, you lost.");
        }
        if(quitFlag){
            return false;
        }
        System.out.print("Play again? (Y/N): ");
        String playAgain = scanner.nextLine();
        return playAgain.equalsIgnoreCase("Y");
    }

    private MineSweeperBoard.State makeGuess(MineSweeperBoard board, int x, int y, Guess g){
        MineSweeperBoard.State st = MineSweeperBoard.State.PLAYING;
        if (!board.validationXY(x, y)) {
            System.out.println("Invalid x or y coordinate, please try again.");
        } else {

            if (g.isFlag()) {
                st = board.flag(x, y);
            } else if (g.isChord()){
                if(board.isRevealed(x, y) &&
                    board.getSurroundingFlagsCount(x, y) == board.get(x, y).getSurroundingMineCount()) {
                    st = board.revealSurrounding(x, y);
                }
            } else {
                st = board.guess(x, y);
            }
        }
        return st;
    }
}
