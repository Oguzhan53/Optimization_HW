/**
 *
 */
package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Oguzhan SEZGIN
 *
 */
public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1200, 500, 50, 100, 10, 9, 15, 12));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(100, 2000, 300, 11, 12, 2312, 311));
        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(1, 2100, 12300, 50, 120, 123, 311));

        Greedy.sort(list);

        BruteForce.sort(list2);

        Backtracking.sort(list3);

        return;
    }
}
