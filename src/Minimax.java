import java.awt.*;
import java.util.List;
import java.util.Random;

public class Minimax {

    private Random random = new Random();
    private Board game;

    public Minimax(Board _game) {
        game = _game;
    }

    public int minimax(int[][] tempBoard, int maxDepth, int h, boolean isMaximizingPlayer) {

        int bestScore;

        // current board state is a terminal state (någon vunnit, maxdjup...)
        if (h > maxDepth) {
            return evaluateBoard(tempBoard);
        }

        if (isMaximizingPlayer) {
            int maxVal = -INFINITY;
            List<Point> availableMoves = game.getAvailableTiles();
            for (currentMove : availableMoves) {
                game.makeMove(tempBoard, false, currentMove);
                int score = minimax(tempBoard, depth, h+1, false);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        } else {
            int minVal = +INFINITY 
            List<Point> availableMoves = game.getAvailableTiles();
            for (currentMove : availableMoves) {
                game.makeMove(tempBoard, true, currentMove);
                int score = minimax(tempBoard, depth, h+1, true);
                if (score < bestScore) {
                    bestMove = score;
                }
            }
        }

        return bestVal;
    }

    public Point findCompMove(int[][] tempBoard, int maxDepth) {
        int[] bestMove = null;
        List<Point> availableMoves = game.getAvailableTiles();
        int bestScore = -1;

        for (currentMove : availableMoves) {
            game.makeMove(tempBoard, false, currentMove);
            int score = minimax(tempBoard, depth, 0, true);
            if (score > bestScore) {
                bestMove = currentMove;
            }
        }

        return bestMove;
    }

    private int calculateChainScore(int[][] tempBoard, int x, int y, String player) {
        if (tempBoard[x][y] == -1)
            return 0;
        if (player.equals(HUMAN) && tempBoard[x][y] == 0) {
            // TODO: räkna ut om vi har flera i rad
            // Om vi har flera i rad ska vi returnera antalet brickor vi har i rad

            return calculateAllDirections(tempBoard, x, y, player);
        }
        return 0;
    }

    private int calculateAllDirections(int[][] tempBoard, int x, int y, String player) {
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

    private int checkDirection(int[][] tempBoard, int x, int y, int xIncStep, int yIncStep, String player) {

        int score = 0;
        int xOffset = 0;
        int yOffset = 0;
        boolean noWallHit = true;

        while (noWallHit) {
            xOffset += xIncStep;
            yOffset += yIncStep;
            int _x = x + xOffset;
            int _y = y + yOffset;

            if ((_x > Board.BOARD_SIZE - 1) || (_x <= 0) || (_y > BOARD_SIZE - 1) || (_y <= 0)) {
                // kört in i väggen -> sluta
                return score;
            }
            if (player.equals(FemIRad.HUMAN) && board[x][y] == 0) {
                score++;
            }
            if (player.equals(AI) && board[x][y] == 1) {
                score++;
            }
            if (tempBoard[x][y] == -1) {
                return score;
            }
        }
        return score;
    }

    private int evaluateBoard(int[][] tempBoard, String player) {
        int score = 0;
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                score += calculateChainScore(tempBoard, x, y, player);
            }
        }
        return score;
    }

}
