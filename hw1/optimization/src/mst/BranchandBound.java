package mst;

import java.util.*;

public class BranchandBound {

    private static ArrayList<Integer> bounds = new ArrayList<>();


    public static void mst(int[][] graph) {

        Set<Integer> chosen = new HashSet<>();
        ArrayList<Integer> chosenVertices = new ArrayList<>();
        int t = BranchandBound.spanTree(graph, chosen, graph.length - 1, 0, chosenVertices);
        System.out.print("\nBranch and Bound Solution\nChosen vertices : ");
        for(int i = 0; i < chosenVertices.size() / 2; i++) {
            System.out.print("[" + chosenVertices.get(i * 2) + "," + chosenVertices.get(i * 2 + 1) + "] ,");
        }
        System.out.print(" Total cost : " + t);
    }


    /**
     * This method traverse graph with breadth first search. It chooses lower cost choice.
     */
    private static int spanTree(int[][] graph, Set<Integer> chosen, int edgeNum, int cost,
                                ArrayList<Integer> vertices) {

        if(edgeNum == 0) {
            return cost;
        }

        ArrayList<Integer> lowerBounds = new ArrayList<>();
        ArrayList<Integer> edges = new ArrayList<>();
        int minX = 0, minY = 0, minVal = 0;

        int[][] copy = new int[graph.length][graph[0].length];
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[i].length; j++) {
                copy[i][j] = graph[i][j];
            }
        }

        if(chosen.size() == 0) {
            for(int j = 0; j < graph.length; j++) {
                for(int j1 = j + 1; j1 < graph[j].length; j1++) {
                    if(graph[j][j1] == -1) {
                        continue;
                    }
                    int temp = findLowerBound(graph, j, j1, chosen, edgeNum - 1, cost);
                    if(j1 == j + 1 || minVal > temp) {
                        minX = j;
                        minY = j1;
                        minVal = temp;
                    }
                    lowerBounds.add(temp);
                    edges.add(j);
                    edges.add(j1);
                }
            }
        } else {
            for(int j = 0; j < graph.length; j++) {
                for(int j1 = j + 1; j1 < graph[j].length; j1++) {
                    if((chosen.contains(j) || chosen.contains(j1)) && graph[j][j1] != -1) {
                        int temp = findLowerBound(graph, j, j1, chosen, edgeNum - 1, cost);
                        if(j1 == j + 1 || minVal > temp) {
                            minX = j;
                            minY = j1;
                            minVal = temp;
                        }
                        lowerBounds.add(temp);
                        edges.add(j);
                        edges.add(j1);
                    }
                }
            }
        }

        quickSort(lowerBounds, 0, lowerBounds.size() - 1, edges);

        int buff, temp = 0, tCost = 0;
        boolean flag = true;
        for(int i = 0; i < lowerBounds.size(); i++) {
            if(lowerBounds.get(i) == -1) {
                continue;
            }
            buff = graph[edges.get(i * 2)][edges.get(i * 2 + 1)];
            copy[edges.get(i * 2)][edges.get(i * 2 + 1)] = -1;
            copy[edges.get(i * 2 + 1)][edges.get(i * 2)] = -1;
            chosen.add(edges.get(i * 2));
            chosen.add(edges.get(i * 2 + 1));
            tCost = spanTree(copy, chosen, edgeNum - 1, cost + buff, vertices);
            if((flag || temp > tCost) && tCost != -1) {
                minX = edges.get(i * 2);
                minY = edges.get(i * 2 + 1);
                temp = tCost;
                flag = false;
                break;
            }
            chosen.remove(edges.get(i * 2));
            chosen.remove(edges.get(i * 2 + 1));
            copy[edges.get(i * 2)][edges.get(i * 2 + 1)] = buff;
            copy[edges.get(i * 2 + 1)][edges.get(i * 2)] = buff;
        }

        if(!flag) {
            chosen.add(minX);
            chosen.add(minY);
            vertices.add(minX);
            vertices.add(minY);
            return temp;
        }
        return -1;
    }


    /**
     * This method determines the lower bound according to the selected vertex.
     */
    private static int findLowerBound(int[][] graph, int r, int c, Set<Integer> chosenVertices, int edgeNum,
                                      int currentVal) {

        int[][] copy = new int[graph.length][graph[0].length];
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[i].length; j++) {
                copy[i][j] = graph[i][j];
            }
        }

        int min = 0, minIdex = 0;
        boolean flag = true;

        ArrayList<Integer> edgeBuff = new ArrayList<>();
        ArrayList<Integer> valBuff = new ArrayList<>();
        int lowerBound = graph[r][c];
        lowerBound += currentVal;
        copy[r][c] = -1;
        copy[c][r] = -1;
        Set<Integer> copyVertices = new HashSet<>();
        Set<Integer> copyVertices2 = new HashSet<>();
        copyVertices2.addAll(chosenVertices);
        copyVertices.add(r);
        copyVertices.add(c);
        copyVertices.addAll(chosenVertices);
        copyVertices2.addAll(copyVertices);
        for(int i = 0; i < copy.length; i++) {
            if(r == i) {
                continue;
            }
            int j;
            for(j = 0; j < copy[i].length; j++) {
                if(copyVertices2.contains(i) && copyVertices2.contains(j) && copyVertices2.size() != graph.length) {
                    continue;
                }
                if(flag && copy[i][j] > 0) {
                    flag = false;
                    min = copy[i][j];
                    minIdex = j;
                } else {
                    if(copy[i][j] != -1 && min > copy[i][j]) {
                        min = copy[i][j];
                        minIdex = j;
                    }
                }
            }
            if(!flag) {

                edgeBuff.add(i);
                edgeBuff.add(minIdex);
                copy[i][minIdex] = -1;
                copy[minIdex][i] = -1;
                copyVertices2.add(i);
                copyVertices2.add(minIdex);
                valBuff.add(min);
            }
            flag = true;
        }
        flag = true;
        for(int j = 0; j < edgeNum; j++) {
            int valMin = 0;
            int buffIn = 0;
            for(int i = 0; i < valBuff.size(); i++) {

                if(valBuff.get(i) != -1 && flag) {
                    valMin = valBuff.get(i);
                    buffIn = i;
                    flag = false;
                }

                if(valBuff.get(i) != -1 && valMin > valBuff.get(i)) {
                    buffIn = i;
                    valMin = valBuff.get(i);
                }
            }
            lowerBound += valBuff.get(buffIn);
            valBuff.set(buffIn, -1);
            copyVertices.add(edgeBuff.get(buffIn * 2));
            copyVertices.add(edgeBuff.get(buffIn * 2 + 1));
            flag = true;
        }

        for(int i = 0; i < graph.length; i++) {
            if(!copyVertices.contains(i)) {
                return -1;
            }
        }

        return currentVal + lowerBound;
    }


    private static int partition(ArrayList<Integer> a, int start, int end, ArrayList<Integer> edges) {

        int pivot = a.get(end);
        int i = (start - 1);

        for(int j = start; j <= end - 1; j++) {

            if(a.get(j) < pivot) {
                i++;
                swap(a, i, j);
                swap(edges, i * 2, j * 2);
                swap(edges, i * 2 + 1, j * 2 + 1);
            }
        }

        swap(a, i + 1, end);
        swap(edges, (i + 1) * 2, end * 2);
        swap(edges, (i + 1) * 2 + 1, end * 2 + 1);
        return (i + 1);
    }


    private static void quickSort(ArrayList<Integer> a, int start, int end, ArrayList<Integer> edges) {

        if(start < end) {
            int p = partition(a, start, end, edges);
            quickSort(a, start, p - 1, edges);
            quickSort(a, p + 1, end, edges);
        }
    }


    private static void swap(ArrayList<Integer> a, int i, int j) {

        int t = a.get(i);
        a.set(i, a.get(j));
        a.set(j, t);
    }
}


