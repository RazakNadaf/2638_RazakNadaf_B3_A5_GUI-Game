/*
 * Assignment 5 - GUI Based Game
 * Student: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 */

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Run GUI construction on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new TicTacToeGUI();
        });
    }
}
