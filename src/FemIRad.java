import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class FemIRad {

    private Scanner scanner = new Scanner(System.in);

    private boolean gameOver = false;
    Board board = new Board();

    private String EMPTY = ".";
    public final String HUMAN = "X";
    private final String AI = "O";

    public static void main(String[] args) throws InterruptedException{
        FemIRad game = new FemIRad();
        game.startGame();
    }

    private void startGame() throws InterruptedException {
        Minimax minmax = new Minimax();

        printBoard();

        while (!gameOver) {

            if (board.isBoardFull()) {
                gameOver = true;
                return;
            }

            int[] humanMove = askForPosition();
            board.makeMove(true, humanMove);

            printBoard();
            //Thread.sleep(1000);

            if (board.isBoardFull()) {
                gameOver = true;
                return;
            }

            int[] bestMove = minmax.findCompMove();
            board.makeMove(false, bestMove);

            printBoard();
            //Thread.sleep(1000);

        }
    }

    private Point askForPosition() {
        System.out.println("Enter row:");
        int row = scanner.nextInt();
        System.out.println("Enter column:");
        int col = scanner.nextInt();

        while (!board.isPositionEmpty(new int[]{row, col} )){
            System.out.println("Illegal move! >:(");

            System.out.println("Enter row:");
            row = scanner.nextInt();
            System.out.println("Enter column:");
            col = scanner.nextInt();
        }

        return new Point(row, col);
    }

    private void printBoard() {

        System.out.print("* ");

        for (int width = 0; width < board.BOARD_SIZE; width++) {
            System.out.print(width + " ");
        }

        System.out.println();

        for (int row = 0; row < board.BOARD_SIZE; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < board.BOARD_SIZE; col++) {

                String tileStatus;

                if (board.board[row][col] == -1)
                    tileStatus = EMPTY;
                else if (board.board[row][col] == 0)
                    tileStatus = HUMAN;
                else
                    tileStatus = AI;

                System.out.print(tileStatus + " ");
            }

            System.out.println();
        }
        System.out.println();
    }

    // private void printLastMoveAndScore() {
    //     currentPlayer = isHuman ? HUMAN : AI;

    //     if (isHuman) {
    //         System.out.println("Your move: " + humanMove[0] + "," + humanMove[1] + " Score: " + calculateAllDirections(humanMove[0], humanMove[1], HUMAN));
    //         System.out.println();

    //     }

    //     if(!isHuman) {
    //         System.out.println("Computer move: " + AIMove[0] + "," + AIMove[1] + " Score: " + calculateAllDirections(AIMove[0], AIMove[1], AI));
    //         System.out.println();
    //     }

    // }   
}