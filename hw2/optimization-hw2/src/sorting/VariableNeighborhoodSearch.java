package sorting;

import java.util.ArrayList;
import java.util.Random;

public class VariableNeighborhoodSearch {

    private final static int shakeSize = 4;


    /**
     * This method sorts given list with using variable neighborhood search algorithm
     */

    public static ArrayList<Integer> sort(ArrayList<Integer> list) {

        ArrayList<Integer> list1 = new ArrayList<>(list);
        getRand(list1);

        int fixedTime = 50, c = 0;
        int kMax = list.size();
        int curVal = findVal(list1), nextVal = 0;
        while(c < fixedTime) {
            c++;
            for(int k = 0; k < kMax; k++) {
                ArrayList<Integer> neighbor = shake(list1);
                nextVal = localSearch(neighbor);
                if(nextVal > curVal) {
                    k = 0;
                    curVal = nextVal;
                    list1.clear();
                    list1.addAll(neighbor);
                    c = 0;
                }
            }
        }

        System.out.println("Variable Neighborhood Search Result : \n" + list1);
        return list1;
    }


    /**
     * This method search best solution between  neighbor of the given solution
     */
    private static int localSearch(ArrayList<Integer> list) {

        int curVal = findVal(list), nextVal = 0;
        for(int i = 0; i < list.size(); i++) {
            ArrayList<Integer> neighbor = getNeighbor(list, i);
            nextVal = findVal(neighbor);
            if(nextVal > curVal) {
                list.clear();
                list.addAll(neighbor);
                curVal = nextVal;
            }
        }

        return curVal;
    }


    /**
     * This method returns the neighbor of the solution
     */
    private static ArrayList<Integer> getNeighbor(ArrayList<Integer> list, int in) {

        ArrayList<Integer> neighbor = new ArrayList<>(list);
        int size = neighbor.size();
        swap(neighbor, in % size, (in + 1) % size);

        return neighbor;
    }


    /**
     * This method finds the solution value according to objective function
     */
    private static int findVal(ArrayList<Integer> list) {

        int counter = 0;
        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                if(list.get(i) <= list.get(j)) {
                    counter++;
                }
            }
        }

        return counter;
    }


    /**
     * This method shakes the given solution according to shakeSize
     */
    private static ArrayList<Integer> shake(ArrayList<Integer> list) {

        Random rand = new Random();
        ArrayList<Integer> shaked = new ArrayList<>(list);
        for(int i = 0; i < shakeSize; i++) {
            swap(shaked, rand.nextInt(list.size() - 1), rand.nextInt(list.size() - 1));
        }
        return shaked;
    }


    /**
     * This method generates the random solution
     */
    private static void getRand(ArrayList<Integer> list) {

        Random rand = new Random();
        for(int i = 0; i < list.size(); i++) {
            swap(list, i, rand.nextInt(list.size() - 1));
        }
    }


    private static void swap(ArrayList<Integer> array, int i, int j) {

        int buff = array.get(i);
        array.set(i, array.get(j));
        array.set(j, buff);
    }
}
