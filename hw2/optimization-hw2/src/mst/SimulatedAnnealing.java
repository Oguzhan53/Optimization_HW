package mst;

import java.util.*;

public class SimulatedAnnealing {

    public static void mst(int[][] graph) {

        int[][] copy = copyGraph(graph);
        ArrayList<Set<Integer>> edges = getRand(graph, copy);

        mastUtil(edges, graph, copy);

        System.out.println("Simulated Annealing \n " + " Edges : " + edges + " - Cost = " + getCost(graph, edges));
        return;
    }


    /**
     * This method finds mst with using simulated annealing algorithm
     */
    private static void mastUtil(ArrayList<Set<Integer>> edges, int[][] graph, int[][] copy) {

        double Tmax = 10;
        double coRat = 0.9;
        double Tmin = 1;
        int kT = graph.length - 1;
        int curVal = getCost(graph, edges), nextVal = -1;
        Random rand = new Random();
        int in = 0;

        int x = 0, y = 0;
        while(Tmax > Tmin) {

            for(int i = 0; i < kT; i++) {

                in = rand.nextInt(graph.length - 1);
                boolean fl = true;
                while(fl) {
                    int i3 = i;
                    for(int j = in; j < graph.length; j++) {

                        if(copy[i3][j] != 0) {

                            Set<Integer> buf = new HashSet<>();
                            buf.addAll(edges.get(i));
                            boolean fl2 = true;
                            for(int i2 : buf) {
                                if(fl2) {
                                    x = i2;
                                    fl2 = false;
                                } else {
                                    y = i2;
                                }
                            }
                            edges.get(i).clear();
                            edges.get(i).addAll(Arrays.asList(i3, j));

                            if(isTree(edges, graph)) {

                                nextVal = getCost(graph, edges);

                                double ap = Math.pow(Math.E, (curVal - nextVal) / Tmax);

                                if(nextVal < curVal || Math.random() < ap) {
                                    copy[i3][j] = 0;
                                    copy[j][i3] = 0;
                                    copy[x][y] = graph[x][y];
                                    copy[y][x] = graph[x][y];
                                    curVal = nextVal;
                                } else {
                                    copy[i3][j] = graph[i3][j];
                                    copy[j][i3] = graph[i3][j];
                                    copy[x][y] = 0;
                                    copy[y][x] = 0;
                                    edges.get(i).clear();
                                    edges.get(i).addAll(Arrays.asList(x, y));
                                }

                                fl = false;
                                break;
                            } else {
                                edges.get(i).clear();
                                edges.get(i).addAll(Arrays.asList(x, y));
                            }
                        }
                        if(j == graph.length - 1) {

                            i3++;
                            i3 = i3 % (graph.length - 1);
                            j = i3;
                            if(i3 == i) {
                                fl = false;
                                break;
                            }
                        }
                    }
                }
            }
            Tmax = Tmax * coRat;
        }
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
}
