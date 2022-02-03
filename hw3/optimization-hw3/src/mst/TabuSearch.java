package mst;

import java.util.*;

public class TabuSearch {

    public static void madeTabu(int[][] graph) {

        int tabuTenior = 5; /* Tabu tenior value */
        int termCond = graph.length * 5;  /* Termination condition */

        int copy[][] = copyGraph(graph);
        ArrayList<Set<Integer>> random = getRand(graph, copy);
        ArrayList<Set<Integer>> bestSol = random;
        int bestVal = getCost(graph, bestSol);
        ArrayList<ArrayList<Set<Integer>>> candidates = getCandidate(random, graph, copy);

        ArrayList<Set<Integer>> tabuList = new ArrayList<>();
        ArrayList<Integer> teniorVal = new ArrayList<>();

        int c = 0, tBest = 0;

        while(c < termCond) {
            ArrayList<Set<Integer>> curSol = bestSol;
            tBest = bestVal;

            for(int i = 0; i < candidates.size(); i++) {
                ArrayList<Set<Integer>> temp = candidates.get(i);
                int buf = getCost(graph, temp);
                Set<Integer> diff = getDiff(candidates.get(0), bestSol);

                if(buf < tBest) {
                    if(tabuList.contains(diff) && buf < bestVal) {
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

            if(tBest < bestVal) {
                bestSol.clear();
                bestSol.addAll(curSol);
                bestVal = tBest;
                tabuList.clear();
                teniorVal.clear();
            }
            candidates.clear();
            updateCopy(curSol, graph, copy);
            candidates = getCandidate(curSol, graph, copy);

            c++;
        }
        System.out.println("Tabu Search Result : \n" + bestSol + "\nObjective Function Value : " + bestVal);

        return;
    }


    /**
     * This method updates the copy (current solution) graph(Sets the value of the selected edges for the solution to 0)
     */
    private static void updateCopy(ArrayList<Set<Integer>> solution, int[][] graph, int[][] copy) {

        copy = copyGraph(graph);

        for(int i = 0; i < solution.size(); i++) {
            int x = 0, y = 0, f = 0;
            for(int iter : solution.get(i)) {
                if(f == 0) {
                    x = iter;
                    f = 1;
                } else {
                    y = iter;
                }
                copy[x][y] = 0;
                copy[y][x] = 0;
            }
        }
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
    private static Set<Integer> getDiff(ArrayList<Set<Integer>> tree1, ArrayList<Set<Integer>> tree2) {

        Set<Integer> diff = new HashSet<>();
        for(int i = 0; i < tree1.size(); i++) {
            if(!tree2.contains(tree1.get(i))) {
                diff.addAll(tree1.get(i));
                break;
            }
        }

        return diff;
    }


    /**
     * This method finds graph cost
     */
    private static int getCost(int[][] graph, ArrayList<Set<Integer>> edges) {

        int cost = 0, x = 0, y = 0;
        boolean fl = true;
        for(int i = 0; i < edges.size(); i++) {
            for(int j : edges.get(i)) {
                if(fl) {
                    x = j;
                    fl = false;
                } else {
                    y = j;
                    fl = true;
                }
            }
            cost += graph[x][y];
        }
        return cost;
    }


    /**
     * This method generates the candidate list
     */
    private static ArrayList<ArrayList<Set<Integer>>> getCandidate(ArrayList<Set<Integer>> tree, int[][] graph,
                                                                   int[][] copy) {

        ArrayList<ArrayList<Set<Integer>>> candidates = new ArrayList<>();
        for(int i1 = 0; i1 < tree.size(); i1++) {
            ArrayList<Set<Integer>> copyTree = new ArrayList<>(tree);
            int[][] copyGraph = copyGraph(copy);
            int x = 0, y = 0, f = 0;
            for(int iter : copyTree.get(i1)) {
                if(f == 0) {
                    x = iter;
                    f = 1;
                } else {
                    y = iter;
                }
            }
            copyTree.remove(i1);
            copyGraph[x][y] = 0;
            copyGraph[y][x] = 0;
            for(int i = 0; i < graph.length; i++) {
                for(int j = i + 1; j < graph.length; j++) {
                    if(copy[i][j] != 0) {
                        Set<Integer> edge = new HashSet<>();
                        edge.add(i);
                        edge.add(j);
                        copyTree.add(edge);
                        if(isTree(copyTree, graph)) {
                            ArrayList<Set<Integer>> buf = new ArrayList<>(copyTree);
                            candidates.add(buf);
                        }
                        copyTree.remove(edge);
                    }
                }
            }
        }
        return candidates;
    }


    /**
     * This method generates the random solution
     */
    private static ArrayList<Set<Integer>> getRand(int[][] graph, int[][] copy) {

        int in = 0;

        for(int i = 1; i < graph.length; i++) {
            if(graph[0][i] != 0) {
                in = i;
                break;
            }
        }

        int c = 2;
        Set<Integer> vertices = new HashSet<>();
        ArrayList<Set<Integer>> edges = new ArrayList<>();
        vertices.add(in);
        vertices.add(0);
        edges.add(new HashSet<>(Arrays.asList(0, in)));
        copy[0][in] = 0;
        copy[in][0] = 0;
        boolean fl = false;
        while(c < graph.length) {
            for(int i : vertices) {
                for(int j = 0; j < graph.length; j++) {
                    if(!vertices.contains(j) && graph[i][j] > 0) {
                        vertices.add(j);
                        edges.add(new HashSet<>(Arrays.asList(i, j)));
                        copy[i][j] = 0;
                        copy[j][i] = 0;
                        c++;
                        fl = true;
                    }
                }
                if(fl) {
                    break;
                }
            }
            fl = false;
        }

        return edges;
    }


    /**
     * This method checks the edge subset is the tree or not
     */
    private static boolean isTree(ArrayList<Set<Integer>> subset, int[][] graph) {

        int[][] copy = copyGraph(graph);
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
            copy[x][y] = -1;
            copy[y][x] = -1;
        }
        Set<Integer> gone = new HashSet<>();
        vertices.add(y);
        do {
            x = vertices.remove();
            if(gone.contains(x)) {
                continue;
            }
            for(int i = 0; i < graph[x].length; i++) {
                if(copy[x][i] == -1) {
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


    /**
     * This method copies the graph matrix
     */
    private static int[][] copyGraph(int[][] graph) {

        int[][] copy = new int[graph.length][graph.length];

        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph.length; j++) {
                copy[i][j] = graph[i][j];
            }
        }
        return copy;
    }
}
