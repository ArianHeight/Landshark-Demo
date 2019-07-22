package main.Utility;

import java.util.Vector;

public class Misc {

    /*
    takes a vector<double> and returns the index of the smallest abs value stored

    returns a -1 if there are no elements in the vector
     */
    public static int findSmallestAbsIndexInVector(Vector<Double> v_d_in) {
        //various size guards
        if (v_d_in.size() == 0) {
            return -1;
        }
        else if (v_d_in.size() == 1) {
            return 0;
        }

        //saves the index and the abs value of the smallest
        double d_smallestSoFar = Math.abs(v_d_in.get(0));
        int i_currentIndex = 0;
        for (int i = 1; i < v_d_in.size(); i++) {
            if (Math.abs(v_d_in.get(i)) < d_smallestSoFar) { //current value is smaller in magnitude
                i_currentIndex = i;
                d_smallestSoFar = Math.abs(v_d_in.get(i));
            }
        }

        return i_currentIndex;
    }

    /*
    takes a vector<Double> and returns the index of the smallest positive value stored

    returns -1 if there are no elements in the vector
    returns -2 if there is no positive value
     */
    public static int findSamllestPosIndexInVector(Vector<Double> v_d_in) {
        //size guard
        if (v_d_in.size() == 0) {
            return -1;
        }

        //saves the index and the pos value of the smallest
        double d_smallestSoFar = v_d_in.get(0);
        int i_currentIndex = -1;
        for (int i = 0; i < v_d_in.size(); i++) {
            if ((i_currentIndex == -1 || v_d_in.get(i) < d_smallestSoFar) && v_d_in.get(i) >= 0) { //current value is smaller and positive
                i_currentIndex = i;
                d_smallestSoFar = v_d_in.get(i);
            }
        }

        return i_currentIndex;
    }
}
