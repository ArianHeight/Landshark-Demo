package Utility;

/*
this class will contain all rng or prng-related methods
 */
public class RandomNumberGenerator {
    public static boolean randomBool() {
        if (Math.random() > 0.5) {
            return true;
        }

        return false;
    }

    public static int randomBetween(int i_start, int i_end) {
        return (int) (i_start + Math.random() * (i_end - i_start));
    }
}
