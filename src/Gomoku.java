import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Gomoku {

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    private String EMPTY = ".";
    private final String HUMAN = "X";
    private final String AI = "O";
    String currentPlayer;

    private final int BOARD_SIZE = 8;

    private boolean isHuman = true;
    private boolean gameOver;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];


    public static void main(String[] args) throws InterruptedException{
        Gomoku game = new Gomoku();
        game.startGame();
    }

    public Gomoku() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = -1;
            }
        }
    }

    private void startGame() throws InterruptedException {
        System.out.println("Available moves: ");
        for (Point p : getAvailableTiles()) {
            System.out.print(p.x + "," + p.y + "; ");
        }
        System.out.println();
        printBoard();

        while (!gameOver) {

            if (isBoardFull()) {
                gameOver = true;
                return;
            }

            printBoard();
            Thread.sleep(1000);

            if (isBoardFull()) {
                gameOver = true;
                return;
            }

            printBoard();
            Thread.sleep(1000);

            /*System.out.println("Available moves: ");
            for (Point p : getAvailableTiles()) {
                System.out.print(p.x + "," + p.y + "; ");
            }
            System.out.println();*/
        }
    }

    private int[] askForPosition() {
        System.out.println("Enter row:");
        int row = scanner.nextInt();
        System.out.println("Enter column:");
        int col = scanner.nextInt();

        while (!isPositionEmpty(new int[]{row, col} )){
            System.out.println("Illegal move! >:(");

            System.out.println("Enter row:");
            row = scanner.nextInt();
            System.out.println("Enter column:");
            col = scanner.nextInt();
        }

        return new int[]{row, col};
    }

    private void printBoard() {

        System.out.print("* ");

        for (int width = 0; width < BOARD_SIZE; width++) {
            System.out.print(width + " ");
        }

        System.out.println();

        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < BOARD_SIZE; col++) {

                String tileStatus;

                if (board[row][col] == -1)
                    tileStatus = EMPTY;
                else if (board[row][col] == 0)
                    tileStatus = HUMAN;
                else
                    tileStatus = AI;

                System.out.print(tileStatus + " ");
            }

            System.out.println();
        }
        System.out.println();
    }

    private int[] makePlayerMove() {
        currentPlayer = isHuman ? HUMAN : AI;

        if (isHuman) {
            //int[] humanMove = askForPosition();
            int[] humanMove = findCompMove();
            board[humanMove[0]][humanMove[1]] = 0;
            isHuman = false;
            return humanMove;
        }

        if (!isHuman) {
            int[] AIMove = findCompMove();
            board[AIMove[0]][AIMove[1]] = 1;
            isHuman = true;
            return AIMove;
        }

    }

    private void printLastMoveAndScore() {
        currentPlayer = isHuman ? HUMAN : AI;

        if (isHuman) {
            System.out.println("Your move: " + humanMove[0] + "," + humanMove[1] + " Score: " + calculateAllDirections(humanMove[0], humanMove[1], HUMAN));
            System.out.println();

        }

        if(!isHuman) {
            System.out.println("Computer move: " + AIMove[0] + "," + AIMove[1] + " Score: " + calculateAllDirections(AIMove[0], AIMove[1], AI));
            System.out.println();
        }

    }

    private int calculateChainScore(int[][] board, int x, int y, String player) {
        if (board[x][y] == -1)
            return 0;
        if (player.equals(HUMAN) && board[x][y] == 0) {
            // TODO: räkna ut om vi har flera i rad
            // Om vi har flera i rad ska vi returnera antalet brickor vi har i rad

            return calculateAllDirections(x, y, player);
        }
        return 0;
    }

    private int calculateAllDirections(int x, int y, String player) {
        int score = 0;

        // x-led
        for (int ii = -1; ii <= 1; ii++) {
            // y-led
            for (int jj = -1; jj <= 1; jj++) {
                if (!(ii == 0 && jj == 0)) {
                    score += checkDirection(x, y, ii, jj, player);
                }
            }
        }
        return score;
    }

    private int checkDirection(int x, int y, int xIncStep, int yIncStep, String player) {

        int score = 0;
        int xOffset = 0;
        int yOffset = 0;
        boolean noWallHit = true;

        while (noWallHit) {
            xOffset += xIncStep;
            yOffset += yIncStep;
            int _x = x + xOffset;
            int _y = y + yOffset;

            if ((_x > BOARD_SIZE - 1) || (_x <= 0) || (_y > BOARD_SIZE - 1) || (_y <= 0)) {
                // kört in i väggen -> sluta
                return score;
            }
            if (player.equals(HUMAN) && board[x][y] == 0) {
                score++;
            }
            if (player.equals(AI) && board[x][y] == 1) {
                score++;
            }
            if (board[x][y] == -1) {
                return score;
            }
        }
        return score;
    }

    private int calculateScore(int[][] board, String player) {
        int score = 0;
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                score += calculateChainScore(board, x, y, player);
            }
        }
        return score;
    }

    private int scoreBoard() {
        String currentPlayer = isHuman ? HUMAN : AI;


        return 0;
    }

    //public int[] findHumanMove() {}

    private int[] findCompMove(/*int depth, int left, int right*/) {
        int[] possibleMove = {
                random.nextInt(BOARD_SIZE),
                random.nextInt(BOARD_SIZE)
        };

        while (!isPositionEmpty(possibleMove)) {
            possibleMove[0] = random.nextInt(BOARD_SIZE);
            possibleMove[1] = random.nextInt(BOARD_SIZE);
        }

        //if (board[0][1] == )

        return possibleMove;
    }

    /*private int[] minValue (int depth, int alpha, int beta) {
        int [] min;
        return min;
    }

    private int[] maxValue () {
        int [] max;
        return max;
    }*/

    private List<Point> getAvailableTiles () {
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

    private boolean isPositionEmpty(int[] position) {
        return board[position[0]][position[1]] == -1; // Om platsen är tom är den true
    }

    private boolean isBoardFull() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (board[x][y] == -1)
                    return false;
            }
        }
        return true;
    }import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

    public class Gomoku {

        private Scanner scanner = new Scanner(System.in);
        private Random random = new Random();

        private String EMPTY = ".";
        private final String HUMAN = "X";
        private final String AI = "O";
        String currentPlayer;

        private final int BOARD_SIZE = 8;

        private boolean isHuman = true;
        private boolean gameOver;

        private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];


        public static void main(String[] args) throws InterruptedException{
            Gomoku game = new Gomoku();
            game.startGame();
        }

        public Gomoku() {
            for (int x = 0; x < BOARD_SIZE; x++) {
                for (int y = 0; y < BOARD_SIZE; y++) {
                    board[x][y] = -1;
                }
            }
        }

        private void startGame() throws InterruptedException {
            System.out.println("Available moves: ");
            for (Point p : getAvailableTiles()) {
                System.out.print(p.x + "," + p.y + "; ");
            }
            System.out.println();
            printBoard();

            while (!gameOver) {

                if (isBoardFull()) {
                    gameOver = true;
                    return;
                }

                printBoard();
                Thread.sleep(1000);

                if (isBoardFull()) {
                    gameOver = true;
                    return;
                }

                printBoard();
                Thread.sleep(1000);

            /*System.out.println("Available moves: ");
            for (Point p : getAvailableTiles()) {
                System.out.print(p.x + "," + p.y + "; ");
            }
            System.out.println();*/
            }
        }

        private int[] askForPosition() {
            System.out.println("Enter row:");
            int row = scanner.nextInt();
            System.out.println("Enter column:");
            int col = scanner.nextInt();

            while (!isPositionEmpty(new int[]{row, col} )){
                System.out.println("Illegal move! >:(");

                System.out.println("Enter row:");
                row = scanner.nextInt();
                System.out.println("Enter column:");
                col = scanner.nextInt();
            }

            return new int[]{row, col};
        }

        private void printBoard() {

            System.out.print("* ");

            for (int width = 0; width < BOARD_SIZE; width++) {
                System.out.print(width + " ");
            }

            System.out.println();

            for (int row = 0; row < BOARD_SIZE; row++) {
                System.out.print(row + " ");
                for (int col = 0; col < BOARD_SIZE; col++) {

                    String tileStatus;

                    if (board[row][col] == -1)
                        tileStatus = EMPTY;
                    else if (board[row][col] == 0)
                        tileStatus = HUMAN;
                    else
                        tileStatus = AI;

                    System.out.print(tileStatus + " ");
                }

                System.out.println();
            }
            System.out.println();
        }

        private int[] makePlayerMove() {
            currentPlayer = isHuman ? HUMAN : AI;

            if (isHuman) {
                //int[] humanMove = askForPosition();
                int[] humanMove = findCompMove();
                board[humanMove[0]][humanMove[1]] = 0;
                isHuman = false;
                return humanMove;
            }

            if (!isHuman) {
                int[] AIMove = findCompMove();
                board[AIMove[0]][AIMove[1]] = 1;
                isHuman = true;
                return AIMove;
            }

        }

        private void printLastMoveAndScore() {
            currentPlayer = isHuman ? HUMAN : AI;

            if (isHuman) {
                System.out.println("Your move: " + humanMove[0] + "," + humanMove[1] + " Score: " + calculateAllDirections(humanMove[0], humanMove[1], HUMAN));
                System.out.println();

            }

            if(!isHuman) {
                System.out.println("Computer move: " + AIMove[0] + "," + AIMove[1] + " Score: " + calculateAllDirections(AIMove[0], AIMove[1], AI));
                System.out.println();
            }

        }

        private int calculateChainScore(int[][] board, int x, int y, String player) {
            if (board[x][y] == -1)
                return 0;
            if (player.equals(HUMAN) && board[x][y] == 0) {
                // TODO: räkna ut om vi har flera i rad
                // Om vi har flera i rad ska vi returnera antalet brickor vi har i rad

                return calculateAllDirections(x, y, player);
            }
            return 0;
        }

        private int calculateAllDirections(int x, int y, String player) {
            int score = 0;

            // x-led
            for (int ii = -1; ii <= 1; ii++) {
                // y-led
                for (int jj = -1; jj <= 1; jj++) {
                    if (!(ii == 0 && jj == 0)) {
                        score += checkDirection(x, y, ii, jj, player);
                    }
                }
            }
            return score;
        }

        private int checkDirection(int x, int y, int xIncStep, int yIncStep, String player) {

            int score = 0;
            int xOffset = 0;
            int yOffset = 0;
            boolean noWallHit = true;

            while (noWallHit) {
                xOffset += xIncStep;
                yOffset += yIncStep;
                int _x = x + xOffset;
                int _y = y + yOffset;

                if ((_x > BOARD_SIZE - 1) || (_x <= 0) || (_y > BOARD_SIZE - 1) || (_y <= 0)) {
                    // kört in i väggen -> sluta
                    return score;
                }
                if (player.equals(HUMAN) && board[x][y] == 0) {
                    score++;
                }
                if (player.equals(AI) && board[x][y] == 1) {
                    score++;
                }
                if (board[x][y] == -1) {
                    return score;
                }
            }
            return score;
        }

        private int calculateScore(int[][] board, String player) {
            int score = 0;
            for (int x = 0; x < BOARD_SIZE; x++) {
                for (int y = 0; y < BOARD_SIZE; y++) {
                    score += calculateChainScore(board, x, y, player);
                }
            }
            return score;
        }

        private int scoreBoard() {
            String currentPlayer = isHuman ? HUMAN : AI;


            return 0;
        }

        //public int[] findHumanMove() {}

        private int[] findCompMove(/*int depth, int left, int right*/) {
            int[] possibleMove = {
                    random.nextInt(BOARD_SIZE),
                    random.nextInt(BOARD_SIZE)
            };

            while (!isPositionEmpty(possibleMove)) {
                possibleMove[0] = random.nextInt(BOARD_SIZE);
                possibleMove[1] = random.nextInt(BOARD_SIZE);
            }

            //if (board[0][1] == )

            return possibleMove;
        }

    /*private int[] minValue (int depth, int alpha, int beta) {
        int [] min;
        return min;
    }

    private int[] maxValue () {
        int [] max;
        return max;
    }*/

        private List<Point> getAvailableTiles () {
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

        private boolean isPositionEmpty(int[] position) {
            return board[position[0]][position[1]] == -1; // Om platsen är tom är den true
        }

        private boolean isBoardFull() {
            for (int x = 0; x < BOARD_SIZE; x++) {
                for (int y = 0; y < BOARD_SIZE; y++) {
                    if (board[x][y] == -1)
                        return false;
                }
            }
            return true;
        }

    }


}
