package sorting;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {

    /**
     * This method sorts the given list with using simulated annealing algorithm
     */
    public static ArrayList<Integer> sort(ArrayList<Integer> list) {

        ArrayList<Integer> list1 = new ArrayList<>(list);

        double Tmax = list.size() * 3;
        double coRat = 0.9;
        double Tmin = 1;
        int kT = list1.size();

        getRand(list1);

        int curVal = findVal(list1), nextVal = -1;
        while(Tmax > Tmin) {

            for(int i = 0; i < kT; i++) {

                ArrayList<Integer> neighbor = getNeighbor(list1, i);
                nextVal = findVal(neighbor);
                double pos = Math.pow(Math.E, (nextVal - curVal) / Tmax);
                if(nextVal > curVal || Math.random() < pos) {
                    list1.clear();
                    list1 = new ArrayList<>(neighbor);
                    curVal = nextVal;
                }
            }
            Tmax = Tmax * coRat;
        }
        System.out.println("Simulated Annealing Result : \n" + list1);
        return list1;
    }


    /**
     * This method generates random solution
     */
    private static void getRand(ArrayList<Integer> list) {

        Random rand = new Random();
        for(int i = 0; i < list.size(); i++) {
            swap(list, i, rand.nextInt(list.size() - 1));
        }
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


    private static void swap(ArrayList<Integer> array, int i, int j) {

        int buff = array.get(i);
        array.set(i, array.get(j));
        array.set(j, buff);
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
}

