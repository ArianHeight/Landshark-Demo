package main.utility;

import main.data.structure.GameComponent;
import main.data.structure.VisualComponent;

import java.util.List;

/*
all kinds of sorting
 */
public class Sorter {
    //dangerous no out of bounds checking!
    private static void swap(List<GameComponent> in, int one, int two) {
        GameComponent temp = in.get(one);
        in.set(one, in.get(two));
        in.set(two, temp);
    }

    /*
    REQUIRES:A list of VisualComponents stored as GameComponents
    EFFECT:Sorts all of them into ascending order by comparing their layer values
     */
    public static void quicksortForVC(List<GameComponent> input, int pivot) {
        if (input.size() <= 1) {
            return;
        }

        pivot = actualQuickSortVC(input, pivot, 0, input.size() - 2,
                ((VisualComponent)input.get(pivot)).getLayer(), false);

        if (input.size() <= 3) {
            return;
        }

        quicksortForVC(input.subList(0, pivot),  pivot / 2);
        quicksortForVC(input.subList(pivot + 1, input.size()), (input.size() - pivot) / 2);
    }

    //private method that sorts the input list so that smaller elements are to the left of the pivot
    //returns the pivot value
    private static int actualQuickSortVC(List<GameComponent> input, int pivot, int left, int right,
                                         int pivotVal, boolean found) {
        swap(input, pivot, input.size() - 1);

        while (left <= right) {
            found = false;
            if (((VisualComponent)input.get(left)).getLayer() < pivotVal) {
                left++;
            } else {
                found = true;
            }
            if (((VisualComponent)input.get(right)).getLayer() > pivotVal) {
                right--;
            } else if (found) {
                swap(input, left, right);
                left++;
                right--;
            }
        }

        swap(input, left, input.size() - 1);
        return left; //left index is the new pivot
    }
}
