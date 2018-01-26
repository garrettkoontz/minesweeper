package com.k00ntz.minesweeper;

import java.util.Scanner;

public class Main {

    public static void main (String[] args){
        System.out.print("Welcome to minesweeper. Please specify what size board you want to play:");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        Game game = new Game();
        if(game.playGame(size)){
            Main.main(args);
        }
    }

}
