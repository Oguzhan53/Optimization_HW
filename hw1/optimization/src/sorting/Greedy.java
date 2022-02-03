package sorting;

import java.util.ArrayList;

public class Greedy {

    /**
     * This method sorts the list with using greedy algorithm
     */
    public static ArrayList<Integer> sort(ArrayList<Integer> list) {

        ArrayList<Integer> sorted = new ArrayList<>();

        int min = list.get(0), minInd = 0;
        for(int i = 0; i < list.size(); i++) {
            if(min > list.get(i)) {
                min = list.get(i);
                minInd = i;
            }
        }
        sorted.add(min);
        list.remove(minInd);
        int max = -1, maxInd = 0;
        while(list.size() != 1) {

            for(int j = 0; j < list.size(); j++) {
                sorted.add(list.get(j));
                int buff = findVal(sorted);
                if(buff > max || (buff == max && list.get(maxInd) > list.get(j))) {
                    max = buff;
                    maxInd = j;
                }
                sorted.remove(list.get(j));
            }

            sorted.add(list.get(maxInd));
            list.remove(maxInd);

            max = -1;
            maxInd = 0;
        }
        sorted.add(list.get(0));

        System.out.println("Greedy algorithm result \n"+sorted);

        return sorted;
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




