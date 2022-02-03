package mst;

import java.util.*;

public class VariableNeighborhoodSearch {

    public final static int shakeSize = 2;


    /**
     * This method finds mst with using variable neighborhood search algorithm
     */
    public static void mst(int[][] graph) {

        int[][] copy = copyGraph(graph);
        ArrayList<Set<Integer>> edges = getRand(graph, copy);
        int fixedTime = 10, c = 0;
        int kMax = graph.length;
        int curVal = getCost(graph, edges), nextVal = 0;

        while(c < fixedTime) {
            c++;
            for(int k = 0; k < kMax; k++) {
                int[][] copy2 = copyGraph(copy);
                ArrayList<Set<Integer>> neighborEdges = shake(edges, graph, copy2);
                nextVal = localSearch(neighborEdges, graph, copy2);
                if(nextVal < curVal) {
                    k = 0;
                    curVal = nextVal;
                    edges.clear();
                    edges.addAll(neighborEdges);
                    copy = copy2.clone();
                    c = 0;
                }
            }
        }

        System.out.println(
                "Variable Neighborhood Search \n " + " Edges : " + edges + " - Cost = " + getCost(graph, edges));
    }


    /**
     * This method shakes the given solution according to shakeSize
     */
    private static ArrayList<Set<Integer>> shake(ArrayList<Set<Integer>> edges, int[][] graph,
                                                 int[][] copy2) {

        ArrayList<Set<Integer>> neighborEdges = new ArrayList<>();
        for(int i = 0; i < edges.size(); i++) {
            neighborEdges.add(new HashSet<>(edges.get(i)));
        }

        Random rand = new Random();
        int in = 0;
        int x = 0, y = 0;
        for(int i = 0; i < shakeSize; i++) {

            in = rand.nextInt(graph.length - 1);
            int in2 = rand.nextInt(edges.size());
            boolean fl = true;
            while(fl) {
                int i3 = i;
                for(int j = in; j < graph.length; j++) {

                    if(copy2[i3][j] != 0) {

                        Set<Integer> buf = new HashSet<>();
                        buf.addAll(neighborEdges.get(in));
                        boolean fl2 = true;
                        for(int i2 : buf) {
                            if(fl2) {
                                x = i2;
                                fl2 = false;
                            } else {
                                y = i2;
                            }
                        }
                        neighborEdges.get(in).clear();
                        neighborEdges.get(in).addAll(Arrays.asList(i3, j));

                        if(isTree(neighborEdges, graph)) {
                            copy2[i3][j] = 0;
                            copy2[j][i3] = 0;
                            copy2[x][y] = graph[x][y];
                            copy2[y][x] = graph[x][y];

                            fl = false;
                            break;
                        } else {
                            neighborEdges.get(in).clear();
                            neighborEdges.get(in).addAll(Arrays.asList(x, y));
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

        return neighborEdges;
    }


    /**
     * This method search best solution between  neighbor of the given solution
     */
    private static int localSearch(ArrayList<Set<Integer>> edges, int[][] graph, int[][] copy) {

        int curVal = getCost(graph, edges), nextVal = 0;

        int in = 0;

        int x = 0, y = 0;

        for(int i = 0; i < graph.length - 1; i++) {
            boolean fl = true;
            while(fl) {
                int i3 = i;
                for(int j = i; j < graph.length; j++) {
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

                            if(nextVal < curVal) {
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

        return curVal;
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
