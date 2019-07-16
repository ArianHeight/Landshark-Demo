package Utility;

import Data.Structure.GameComponent;
import Data.Structure.VisualTextureComponent;

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
    REQUIRES:A list of VisualTextureComponents stored as GameComponents
    EFFECT:Sorts all of them into ascending order by comparing their layer values
     */
    public static void quicksortForVTC(List<GameComponent> li_gc_in, int pivot) {
        if (li_gc_in.size() <= 1) {
            return;
        }

        int pivotVal = ((VisualTextureComponent)li_gc_in.get(pivot)).getLayer();
        swap(li_gc_in, pivot, li_gc_in.size() - 1);
        int i_left = 0;
        int i_right = li_gc_in.size() - 2;
        int i_found = 0;

        while (i_left <= i_right) {
            i_found = 0;
            if (((VisualTextureComponent)li_gc_in.get(i_left)).getLayer() < pivotVal) {
                i_left++;
            }
            else {
                i_found++;
            }
            if (((VisualTextureComponent)li_gc_in.get(i_right)).getLayer() > pivotVal) {
                i_right--;
            }
            else {
                i_found++;
            }

            if (i_found == 2) {
                swap(li_gc_in, i_left, i_right);
                i_left++;
                i_right--;
            }
        }

        swap(li_gc_in, i_left, li_gc_in.size() - 1);
        pivot = i_left;

        if (li_gc_in.size() <= 3) {
            return;
        }

        quicksortForVTC(li_gc_in.subList(0, pivot),  pivot / 2);
        quicksortForVTC(li_gc_in.subList(pivot + 1, li_gc_in.size()), (li_gc_in.size() - pivot) / 2);
    }
}
