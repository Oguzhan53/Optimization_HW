package sorting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BruteForce {



    public static void sort(ArrayList<Integer> list) {

        Set<ArrayList<Integer>> permutations = new HashSet<ArrayList<Integer>>();
        BruteForce.findPermutations(list, 0, permutations);
        ArrayList<Integer> sorted2 = BruteForce.findSorted(permutations);
        System.out.println("Brute force algorithm result\n"+sorted2);
    }

    /**
     *  This method find all possible permutations of the list and adds its to permutations set
     * */
    private static void findPermutations(ArrayList<Integer> array, int curInd, Set<ArrayList<Integer>> permutations) {

        if(curInd == array.size() - 1) {
            ArrayList<Integer> copy = new ArrayList<>(array);
            permutations.add(copy);
            return;
        }

        for(int i = 0; i < array.size(); i++) {
            swap(array, i, curInd);
            findPermutations(array, curInd + 1, permutations);
            swap(array, i, curInd);
        }
    }


    private static void swap(ArrayList<Integer> array, int i, int j) {

        int buff = array.get(i);
        array.set(i, array.get(j));
        array.set(j, buff);
    }

    /**
     * This method finds the solution value according to objective function
     */
    private static ArrayList<Integer> findSorted(Set<ArrayList<Integer>> permutations) {

        ArrayList<Integer> sorted = null;
        int max = 0, counter = 0;
        for(ArrayList<Integer> iter : permutations) {

            for(int i = 0; i < iter.size(); i++) {
                for(int j = i + 1; j < iter.size(); j++) {
                    if(iter.get(i) < iter.get(j)) {
                        counter++;
                    }
                }
            }

            if(counter > max) {
                sorted = new ArrayList<>(iter);
                max = counter;
            }
            counter = 0;
        }

        return sorted;
    }
}
