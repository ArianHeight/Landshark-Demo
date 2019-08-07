package model.utility;

public class Misc {
    /*
    takes 3 Strings, the first two being subjects that are checked against the third
    Every time one of the first two Strings equals the third String

    the number of times the two Strings equals the third is returned
     */
    public static int numEquals(String one, String two, String target) {
        int count = 0;
        if (one.equals(target)) {
            count++;
        }
        if (two.equals(target)) {
            count++;
        }

        return count;
    }
}
