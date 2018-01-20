package com.k00ntz.minesweeper;

import com.k00ntz.minesweeper.board.MineSquare;
import org.junit.Test;

import static org.junit.Assert.*;

public class MineSquareTest {

    @Test
    public void setFlagged() {
        MineSquare ms = new MineSquare(-1);
        assertEquals("x",ms.toString());
        ms.setFlagged();
        assertEquals("F", ms.toString());
        ms = new MineSquare(0);
        assertEquals("x",ms.toString());
        ms.setFlagged();
        assertEquals("F", ms.toString());
    }

    @Test
    public void toStringTest() {
        MineSquare ms = new MineSquare(-1);
        assertEquals("Mines before reveal are x","x",ms.toString());
        ms.reveal();
        assertEquals("Mines after reveal are blank"," ",ms.toString());
        MineSquare ms2 = new MineSquare(0);
        assertEquals("Zeros before reveal are x","x",ms2.toString());
        ms2.reveal();
        assertEquals("Zeros after reveal are blank"," ",ms2.toString());
        MineSquare ms3 = new MineSquare(1);
        assertEquals("Nums before reveal are x","x",ms3.toString());
        ms3.reveal();
        assertEquals("Nums after reveal are num","1",ms3.toString());
    }
}