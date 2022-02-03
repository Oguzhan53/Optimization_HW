package sorting;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class GeneticAlgorithm {

    public static final int populationSize = 10;

    private static Random rand = new Random();


    public static void sort(ArrayList<Integer> list) {

        int max = findMax(list);
        ArrayList<ArrayList<Integer>> population = getRandomPopulation(populationSize, list);
        Hashtable<Integer, Integer> numTable = createHashTable(list);
        while(true) {
            quickSort(population, 0, population.size() - 1);
            for(int i = 0; i < percent(populationSize, 50); i++) {
                population.set(i, insertMutation(population.get(i)));
            }

            if(findVal(population.get(0)) == max) {
                break;
            }

            ArrayList<ArrayList<Integer>> newGeneration = new ArrayList<>();
            for(int i = 0; i < percent(populationSize, 10); i++) {
                ArrayList<Integer> buf = new ArrayList<>(population.get(i));
                newGeneration.add(buf);
            }
            for(int i = 0; i < percent(populationSize, 90); i++) {
                int c1, c2;
                while(true) {
                    c1 = rand.nextInt(percent(populationSize, 50));
                    c2 = rand.nextInt(percent(populationSize, 50));
                    if(c1 != c2) {
                        break;
                    }
                }
                ArrayList<Integer> child = order1Crossover(population.get(c1), population.get(c2), numTable);
                newGeneration.add(child);
            }
            population.clear();
            population.addAll(newGeneration);
        }

        System.out.println("Genetic Algorithm Result : \n" + population.get(0));

        return;
    }


    /**
     * This method calculates percent value according to the given numbers
     */
    private static int percent(int num, int percent) {

        return (num * percent) / 100;
    }


    /**
     * This method applies order-1 Crossover operation
     */
    private static ArrayList<Integer> order1Crossover(ArrayList<Integer> list1, ArrayList<Integer> list2,
                                                      Hashtable<Integer, Integer> numTable) {

        Hashtable<Integer, Integer> copyTable = new Hashtable<>(numTable);
        int in1, in2;
        while(true) {
            in1 = rand.nextInt(list1.size() - 1);
            in2 = rand.nextInt(list1.size() - 1);
            if(in1 != in2) {
                break;
            }
        }
        if(in1 > in2) {
            int buf = in1;
            in1 = in2;
            in2 = buf;
        }
        ArrayList<Integer> child = new ArrayList<>();
        child.addAll(list1.subList(in1, in2));
        for(int i = 0; i < child.size(); i++) {
            decreaseVal(copyTable, child.get(i));
        }
        int c = 0;
        int i = 0;
        int index = in2;
        while(i < list1.size() - (in2 - in1)) {
            if(index == list1.size()) {
                index = 0;
            }
            if(copyTable.containsKey(list2.get((in2 + c) % list1.size()))) {
                if(index >= child.size()) {
                    child.add(list2.get((in2 + c) % list1.size()));
                } else {
                    child.add(index, list2.get((in2 + c) % list1.size()));
                }
                decreaseVal(copyTable, list2.get((in2 + c) % list1.size()));

                i++;
                index++;
            }
            c++;
        }

        return child;
    }


    /**
     * This method decreases the hash table value
     */
    private static void decreaseVal(Hashtable<Integer, Integer> numTable, int key) {

        if(numTable.containsKey(key)) {
            if(numTable.get(key) == 1) {
                numTable.remove(key);
            } else {
                numTable.replace(key, numTable.get(key) - 1);
            }
        }
    }


    /**
     * This method increases the hash table value
     */
    private static void increaseVal(Hashtable<Integer, Integer> numTable, int key) {

        if(numTable.containsKey(key)) {
            numTable.replace(key, numTable.get(key) + 1);
        } else {
            numTable.put(key, 1);
        }
    }


    /**
     * This method creates hash table for elements number
     */
    private static Hashtable<Integer, Integer> createHashTable(ArrayList<Integer> list) {

        Hashtable<Integer, Integer> numTable = new Hashtable<>();
        for(int i = 0; i < list.size(); i++) {
            increaseVal(numTable, list.get(i));
        }
        return numTable;
    }


    /**
     * This method applies insert mutation operation
     */
    private static ArrayList<Integer> insertMutation(ArrayList<Integer> list) {

        ArrayList<Integer> copy = new ArrayList<>(list);
        int first = findVal(copy);
        int in1 = rand.nextInt(copy.size()), in2 = rand.nextInt(copy.size());
        if(in1 < in2) {
            int buf = copy.get(in2);
            copy.remove(in2);
            copy.add((in1 + 1) % list.size(), buf);
        } else {
            int buf = copy.get(in1);
            copy.remove(in1);
            copy.add((in2 + 1) % list.size(), buf);
        }

        int second = findVal(copy);
        if(second > first) {
            return copy;
        } else {
            return list;
        }
    }


    /***
     *  This method swaps population elements for quick sort
     */
    private static void swapQuick(ArrayList<ArrayList<Integer>> list, int i, int j) {

        ArrayList<Integer> temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }


    /**
     * Partition operation for quick sort
     */
    static int partition(ArrayList<ArrayList<Integer>> list, int low, int high) {

        ArrayList<Integer> pivot = list.get(high);

        int i = (low - 1);

        for(int j = low; j <= high - 1; j++) {

            if(findVal(list.get(j)) > findVal(pivot)) {

                i++;
                swapQuick(list, i, j);
            }
        }
        swapQuick(list, i + 1, high);
        return (i + 1);
    }


    /**
     * This method sorts(quick sort) the population elements in order from largest to smallest
     */
    static void quickSort(ArrayList<ArrayList<Integer>> list, int low, int high) {

        if(low < high) {

            int pi = partition(list, low, high);

            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }


    /**
     * This function calculates the best objective function value
     */
    private static int findMax(ArrayList<Integer> list) {

        int size = list.size();
        return size * (size - 1) / 2;
    }


    /**
     * This method finds the solution value according to objective function
     */
    public static int findVal(ArrayList<Integer> list) {

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
     * This method generates random population initially
     */
    public static ArrayList<ArrayList<Integer>> getRandomPopulation(int pSize, ArrayList<Integer> list) {

        ArrayList<ArrayList<Integer>> population = new ArrayList<>();

        for(int i = 0; i < pSize; i++) {
            ArrayList<Integer> tPop = new ArrayList<>(list);
            getRand(tPop);
            population.add(tPop);
        }
        return population;
    }


    /**
     * This method generates random solution
     */
    private static void getRand(ArrayList<Integer> list) {

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
