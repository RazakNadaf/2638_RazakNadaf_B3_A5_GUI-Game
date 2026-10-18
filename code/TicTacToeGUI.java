/*
 * Assignment 5 - GUI Based Game
 * Student: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// OOP Concept: Inheritance (TicTacToeGUI extends JFrame), Event Handling (implements ActionListener)
public class TicTacToeGUI extends JFrame implements ActionListener {
    // Encapsulation: Private GUI components and game reference
    private TicTacToeGame game;
    private JButton[][] boardButtons;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JButton newGameButton;

    // Custom colors for student GUI styling
    private final Color COLOR_X = new Color(30, 85, 185);       // Blue for Player X
    private final Color COLOR_O = new Color(210, 45, 40);       // Red for Player O
    private final Color COLOR_WIN = new Color(195, 235, 195);   // Soft green highlight for winning cells
    private final Color COLOR_DEFAULT_BTN = new Color(245, 245, 248);

    // Constructor: Sets up the GUI window and initializes components
    public TicTacToeGUI() {
        // Association: Create Player objects and initialize TicTacToeGame
        Player playerX = new Player("Razak", 'X');
        Player playerO = new Player("Friend", 'O');
        this.game = new TicTacToeGame(playerX, playerO);

        // JFrame Window Setup
        setTitle("Tic-Tac-Toe Game - Razak Nadaf (2638)");
        setSize(480, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Initialize UI panels
        setupHeaderPanel();
        setupBoardPanel();
        setupBottomPanel();

        // Update initial status display
        updateTurnDisplay();
        updateScoreDisplay();

        setVisible(true);
    }

    // Creates the top title, score, and turn status panel
    private void setupHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 10, 5, 10));

        JLabel titleLabel = new JLabel("TIC-TAC-TOE GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        scoreLabel.setForeground(new Color(60, 60, 60));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(COLOR_X);

        headerPanel.add(titleLabel);
        headerPanel.add(scoreLabel);
        headerPanel.add(statusLabel);

        add(headerPanel, BorderLayout.NORTH);
    }

    // Creates the 3x3 grid of clickable buttons
    private void setupBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boardButtons = new JButton[3][3];

        Font btnFont = new Font("Arial", Font.BOLD, 42);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(btnFont);
                button.setFocusPainted(false);
                button.setBackground(COLOR_DEFAULT_BTN);
                button.addActionListener(this); // Event handling
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    // Creates the bottom panel with New Game button
    private void setupBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));

        newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        newGameButton.setPreferredSize(new Dimension(140, 38));
        newGameButton.setFocusPainted(false);
        newGameButton.addActionListener(this); // Event handling

        bottomPanel.add(newGameButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Event Handling: Responds to button clicks on the board and New Game button
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Handle "NEW GAME" button click
        if (source == newGameButton) {
            handleReset();
            return;
        }

        // Handle 3x3 board cell clicks
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (source == boardButtons[row][col]) {
                    handleCellClick(row, col);
                    return;
                }
            }
        }
    }

    // Processes a move on the clicked cell
    private void handleCellClick(int row, int col) {
        char symbol = game.getCurrentPlayer().getSymbol();

        // Message passing: communicate with TicTacToeGame logic
        boolean moveSuccessful = game.makeMove(row, col);
        if (!moveSuccessful) {
            return; // Ignore clicks on already filled cells or if game is over
        }

        // Update button appearance
        JButton clickedBtn = boardButtons[row][col];
        clickedBtn.setText(String.valueOf(symbol));
        clickedBtn.setForeground(symbol == 'X' ? COLOR_X : COLOR_O);

        // Check if move concluded the game
        if (game.isGameOver()) {
            if (game.getWinner() != null) {
                Player winner = game.getWinner();
                statusLabel.setText(winner.getName() + " (" + winner.getSymbol() + ") Wins!");
                statusLabel.setForeground(new Color(25, 135, 60)); // Green text for win

                // Highlight winning row/col/diag
                int[][] winningCells = game.getWinningCells();
                if (winningCells != null) {
                    for (int[] cell : winningCells) {
                        boardButtons[cell[0]][cell[1]].setBackground(COLOR_WIN);
                    }
                }
            } else if (game.isDraw()) {
                statusLabel.setText("Game Draw!");
                statusLabel.setForeground(new Color(180, 80, 20)); // Orange text for draw
            }

            // Disable board buttons after game ends
            setBoardEnabled(false);
            updateScoreDisplay();
        } else {
            // Game continues, update turn label
            updateTurnDisplay();
        }
    }

    // Handles resetting the board for a new game
    private void handleReset() {
        game.resetGame();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardButtons[row][col].setText("");
                boardButtons[row][col].setBackground(COLOR_DEFAULT_BTN);
                boardButtons[row][col].setEnabled(true);
            }
        }

        updateTurnDisplay();
        updateScoreDisplay();
    }

    // Enables or disables all 3x3 board buttons
    private void setBoardEnabled(boolean enabled) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardButtons[row][col].setEnabled(enabled);
            }
        }
    }

    // Updates the turn label text and color
    private void updateTurnDisplay() {
        Player current = game.getCurrentPlayer();
        statusLabel.setText(current.getName() + "'s Turn (" + current.getSymbol() + ")");
        statusLabel.setForeground(current.getSymbol() == 'X' ? COLOR_X : COLOR_O);
    }

    // Updates the scoreboard label text
    private void updateScoreDisplay() {
        scoreLabel.setText("Player X: " + game.getPlayerX().getName() + " (" + game.getPlayerX().getScore() + ")    |    " +
                           "Player O: " + game.getPlayerO().getName() + " (" + game.getPlayerO().getScore() + ")");
    }

    // Getters for programmatic testing and screenshot capture
    public TicTacToeGame getGame() {
        return game;
    }

    public JButton[][] getBoardButtons() {
        return boardButtons;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }
}
