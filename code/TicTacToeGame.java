/*
 * Assignment 5 - GUI Based Game
 * Student: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 */

// OOP Concept: Abstraction, Encapsulation, Association
public class TicTacToeGame {
    // Encapsulation: Private game state variables
    private char[][] board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private Player winner;
    private boolean gameOver;
    private int moveCount;
    private int drawCount;
    private int[][] winningCells;

    // Constructor: Initializes the game with two Player objects (Association)
    public TicTacToeGame(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new char[3][3];
        this.drawCount = 0;
        resetGame();
    }

    // Resets the board and game state for a new round
    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        this.currentPlayer = playerX;
        this.winner = null;
        this.gameOver = false;
        this.moveCount = 0;
        this.winningCells = null;
    }

    // Polymorphism: Method Overloading (makeMove by row and column)
    public boolean makeMove(int row, int col) {
        // Prevent move if out of bounds, cell already filled, or game is over
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }
        if (board[row][col] != ' ' || gameOver) {
            return false;
        }

        // Place symbol on the board
        board[row][col] = currentPlayer.getSymbol();
        moveCount++;

        // Check for winner or draw
        if (checkWinner()) {
            gameOver = true;
            winner = currentPlayer;
            winner.incrementScore();
        } else if (moveCount == 9) {
            gameOver = true;
            winner = null;
            drawCount++;
        } else {
            // Switch turn to the other player
            currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        }

        return true;
    }

    // Polymorphism: Method Overloading (makeMove by 1D button index 0-8)
    public boolean makeMove(int index) {
        int row = index / 3;
        int col = index % 3;
        return makeMove(row, col);
    }

    // Checks all rows, columns, and diagonals for a winning combination
    private boolean checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winningCells = new int[][] { {i, 0}, {i, 1}, {i, 2} };
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                winningCells = new int[][] { {0, j}, {1, j}, {2, j} };
                return true;
            }
        }

        // Check main diagonal (top-left to bottom-right)
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winningCells = new int[][] { {0, 0}, {1, 1}, {2, 2} };
            return true;
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winningCells = new int[][] { {0, 2}, {1, 1}, {2, 0} };
            return true;
        }

        return false;
    }

    // Check if the game ended in a draw
    public boolean isDraw() {
        return gameOver && winner == null;
    }

    // Getters for game state
    public boolean isGameOver() {
        return gameOver;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public char getCell(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            return board[row][col];
        }
        return ' ';
    }

    public int[][] getWinningCells() {
        return winningCells;
    }
}
