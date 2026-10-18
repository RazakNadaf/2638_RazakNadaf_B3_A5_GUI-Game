/*
 * Assignment 5 - GUI Based Game
 * Student: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 */

// OOP Concept: Classes and Objects, Encapsulation
public class Player {
    // Encapsulation: Private instance variables
    private String name;
    private char symbol;
    private int score;

    // Constructor: Parameterized constructor to initialize player object
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.score = 0;
    }

    // Constructor Overloading: Default constructor
    public Player() {
        this("Unknown", ' ');
    }

    // Encapsulation: Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void resetScore() {
        this.score = 0;
    }

    // Polymorphism: Method Overriding of toString()
    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}
