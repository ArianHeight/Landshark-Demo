package main.GameLogic;

/*

this class represents a single game score with a name/score

 */
public class GameScore {
    private String str_name;
    private int i_score;

    //cstr
    public GameScore(String str_name, int i_score) {
        this.str_name = str_name;
        this.i_score = i_score;
    }

    //accessors
    public String getName() { return this.str_name; }
    public int getScore() { return this.i_score; }

    //for printing
    public String toString() {
        return this.str_name + " " + this.i_score;
    }
}
