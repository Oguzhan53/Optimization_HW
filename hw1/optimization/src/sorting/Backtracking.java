/**
 *
 */
package sorting;

import java.util.ArrayList;
import java.util.Set;

public class Backtracking {

    public static int max = 0;


    public static void sort(ArrayList<Integer> list) {

        ArrayList<Integer> sorted = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();

        Backtracking.sortUtil(list, temp, sorted);
        System.out.println("Backtacking algorithm result \n"+sorted);
    }

    /**
     *  This method sorts list with using backtracking algorithm
     * */
    private static void sortUtil(ArrayList<Integer> list, ArrayList<Integer> tempArray, ArrayList<Integer> sorted) {

        if(list.size() == 0) {
            int val = findVal(tempArray);
            if(val > max) {
                sorted.clear();
                for(int i = 0; i < tempArray.size(); i++)
                    sorted.add(tempArray.get(i));
                max = val;
            }
            return;
        }

        ArrayList<Integer> copy = new ArrayList<>(list);

        for(int i = 0; i < list.size(); i++) {
            int buff = list.get(i);

            tempArray.add(buff);
            copy.remove(copy.indexOf(buff));

            sortUtil(copy, tempArray, sorted);

            tempArray.remove(tempArray.size() - 1);
            copy.add(buff);
        }


    }

    /**
     * This method finds the solution value according to objective function
     */
    private static int findVal(ArrayList<Integer> list) {

        int counter = 0;
        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                if(list.get(i) < list.get(j)) {
                    counter++;
                }
            }
        }

        return counter;
    }
}



