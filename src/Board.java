import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

    String currentPlayer;

    public final static int BOARD_SIZE = 8;

    //private boolean isHuman = true;

    public int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = -1;
            }
        }
    }

    public Point makeMove(int[][] board, boolean isHuman, Point move) {

        if (isHuman) {
            board[move.x][move.y] = 0;
            isHuman = false;
        } else {
            board[move.x][move.y] = 1;
            isHuman = true;
        }
        return move;
    }

    public List<Point> getAvailableTiles() {
        List<Point> availableTiles = new ArrayList<>();

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (board[x][y] == -1) {
                    availableTiles.add(new Point(x,y));
                }
            }
        }

        return availableTiles;
    }

    public boolean isPositionEmpty(Point position) {
        return board[position.x][position.y] == -1; // Om platsen är tom är den true
    }

    public boolean isBoardFull() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (board[x][y] == -1)
                    return false;
            }
        }
        return true;
    }

}
    
    