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

        int pivotVal = ((VisualComponent)input.get(pivot)).getLayer();
        swap(input, pivot, input.size() - 1);
        int left = 0;
        int right = input.size() - 2;
        int found = 0;

        while (left <= right) {
            found = 0;
            if (((VisualComponent)input.get(left)).getLayer() < pivotVal) {
                left++;
            }
            else {
                found++;
            }
            if (((VisualComponent)input.get(right)).getLayer() > pivotVal) {
                right--;
            }
            else {
                found++;
            }

            if (found == 2) {
                swap(input, left, right);
                left++;
                right--;
            }
        }

        swap(input, left, input.size() - 1);
        pivot = left;

        if (input.size() <= 3) {
            return;
        }

        quicksortForVC(input.subList(0, pivot),  pivot / 2);
        quicksortForVC(input.subList(pivot + 1, input.size()), (input.size() - pivot) / 2);
    }
}
