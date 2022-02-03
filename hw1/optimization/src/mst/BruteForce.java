package mst;

import java.util.*;

public class BruteForce {

    /**
     * This method finds the all possible edge subsets and puts it to the set
     */
    private static void findSubset(int[][] graph, Set<Set<Integer>> current, Set<Set<Set<Integer>>> subset) {

        int[][] copy = new int[graph.length][graph[0].length];
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[i].length; j++) {
                copy[i][j] = graph[i][j];
            }
        }

        Set<Set<Integer>> copySet = new HashSet<>();
        copySet.addAll(current);

        if(copySet.size() == graph.length - 1) {

            subset.add(copySet);
            return;
        }

        for(int i = 0; i < graph.length; i++) {
            for(int j = i; j < graph[i].length; j++) {
                if(copy[i][j] != -1) {

                    Set<Integer> buff = new HashSet<>();
                    copy[i][j] = -1;
                    buff.add(i);
                    buff.add(j);
                    copySet.add(buff);
                    findSubset(copy, copySet, subset);
                    copySet.remove(buff);
                    copy[i][j] = graph[i][j];
                }
            }
        }
        return;
    }


    /**
     * This method first find all subsets and then choose lower cost and tree.
     */
    public static void mst(int[][] graph) {

        Set<Set<Integer>> subset = new HashSet<>();
        Set<Set<Set<Integer>>> subsets = new HashSet<>();
        findSubset(graph, subset, subsets);

        int temp = 0, buff = 0, x = 0, y = 0;
        ArrayList<Integer> edges = new ArrayList<>();
        boolean flag = true;
        Set<Set<Integer>> res = new HashSet<>();
        int cost = 0;
        for(Set<Set<Integer>> iter1 : subsets) {
            buff = 0;
            for(Set<Integer> iter2 : iter1) {
                boolean fl = true;
                for(Integer iter3 : iter2) {
                    if(fl) {
                        x = iter3;
                        fl = false;
                    } else {
                        y = iter3;
                    }
                }
                buff += graph[x][y];
            }
            if((flag || temp > buff) && isTree(iter1, graph)) {
                res.clear();
                flag = false;
                temp = buff;
                res.addAll(iter1);
            }
        }
        cost = temp;
        System.out.println("\nBrute Force Solution\nChosen vertices : " + res + " Total cost :  " + cost);
        return;
    }


    /**
     * This method checks the vertex subset is the tree or not
     */
    private static boolean isTree(Set<Set<Integer>> subset, int[][] graph) {

        int[][] copy = new int[graph.length][graph[0].length];
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[i].length; j++) {
                copy[i][j] = graph[i][j];
            }
        }
        Queue<Integer> vertices = new LinkedList<>();
        int x = 0, y = 0;
        for(Set<Integer> iter1 : subset) {
            boolean fl = true;
            for(Integer iter2 : iter1) {
                if(fl) {
                    x = iter2;
                    fl = false;
                } else {
                    y = iter2;
                }
            }
            copy[x][y] = 0;
            copy[y][x] = 0;
        }
        Set<Integer> gone = new HashSet<>();
        vertices.add(y);
        do {
            x = vertices.remove();
            if(gone.contains(x)) {
                continue;
            }
            for(int i = 0; i < graph[x].length; i++) {
                if(copy[x][i] == 0) {
                    vertices.add(i);
                }
            }
            gone.add(x);
        } while(vertices.size() > 0);

        for(int i = 0; i < graph.length; i++)
            if(!gone.contains(i)) {
                return false;
            }
        return true;
    }
}
