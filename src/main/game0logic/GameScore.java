package main.game0logic;

/*

this class represents a single game score with a name/score

 */
public class GameScore {
    private String name;
    private int score;

    //cstr
    public GameScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    //accessors
    //returns the name associated w/ this score
    public String getName() {
        return this.name;
    }

    //returns the value of the score
    public int getScore() {
        return this.score;
    }

    //for printing
    public String toString() {
        return this.name + " " + this.score;
    }
}
