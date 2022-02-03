package sorting;

import java.util.*;

public class TabuSearch {

    public static void sort(ArrayList<Integer> list) {

        int tabuTenior = 5; /* Tabu tenior value */
        int termCond = list.size() * list.size() / 2;  /* Termination condition */

        ArrayList<Integer> list1 = new ArrayList<>(list);
        getRand(list1);
        ArrayList<Integer> bestSol = new ArrayList<>(list1);    /* Best solution */
        int bestVal = findVal(bestSol);   /* Best value according to objective function */
        ArrayList<ArrayList<Integer>> candidates = getCandidateList(list1); /* Candidate list */
        ArrayList<Set<Integer>> tabuList = new ArrayList<>();
        ArrayList<Integer> teniorVal = new ArrayList<>();
        int c = 0, tBest = 0;

        while(c < termCond) {
            ArrayList<Integer> curSol = bestSol;

            tBest = bestVal;

            for(int i = 0; i < candidates.size(); i++) {
                ArrayList<Integer> temp = candidates.get(i);
                int buf = findVal(temp);
                if(buf > tBest) {

                    Set<Integer> diff = getDiff(temp, curSol);
                    if(tabuList.contains(diff) && buf >
                            bestVal) {  /* If objective function value grather than the best value than program accepts the tabu active value*/
                        int in = tabuList.indexOf(diff);
                        teniorVal.set(in, tabuTenior);

                        tBest = buf;
                        curSol = temp;
                    } else if(!tabuList.contains(diff)) {
                        tabuList.add(diff);
                        teniorVal.add(tabuTenior);

                        tBest = buf;
                        curSol = temp;
                    }
                }
                updateTabuList(tabuList, teniorVal);
            }

            if(tBest > bestVal) {
                copyList(curSol, bestSol);
                bestVal = tBest;
                tabuList.clear();
                teniorVal.clear();
            }
            candidates.clear();
            candidates = getCandidateList(curSol);
            c++;
        }

        System.out.println("Tabu Search Result : \n" + bestSol);
        return;
    }


    /**
     * This method updates the tabu list (decreases the tabuTenior value in each iteration)
     */
    private static void updateTabuList(ArrayList<Set<Integer>> tabuList, ArrayList<Integer> teniorVal) {

        for(int i = 0; i < tabuList.size(); i++) {
            if(teniorVal.get(i) == 0) {
                teniorVal.remove(i);
                tabuList.remove(i);
            } else {
                teniorVal.set(i, teniorVal.get(i) - 1);
            }
        }
    }


    /**
     * This method returns the difference between current solution and candidate solution
     */
    private static Set<Integer> getDiff(ArrayList<Integer> list1, ArrayList<Integer> list2) {

        if(list1.size() != list2.size()) {
            return null;
        }

        Set<Integer> swapEl = new HashSet<>();

        for(int i = 0; i < list1.size(); i++) {
            if(list1.get(i) != list2.get(i)) {
                swapEl.add(list1.get(i));
            }
        }

        return swapEl;
    }


    /**
     * This method copy source list to destination list
     */
    private static void copyList(ArrayList<Integer> source, ArrayList<Integer> dest) {

        dest.clear();
        for(int i = 0; i < source.size(); i++) {
            dest.add(source.get(i));
        }
    }


    /**
     * This method generates the candidate list
     */
    private static ArrayList<ArrayList<Integer>> getCandidateList(ArrayList<Integer> list) {

        ArrayList<ArrayList<Integer>> candidates = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            ArrayList<Integer> NewCandidate = new ArrayList<>(list);
            swap(NewCandidate, i, (i + 1) % NewCandidate.size());
            candidates.add(NewCandidate);
        }

        return candidates;
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


    private static void swap(ArrayList<Integer> array, int i, int j) {

        int buff = array.get(i);
        array.set(i, array.get(j));
        array.set(j, buff);
    }
}
