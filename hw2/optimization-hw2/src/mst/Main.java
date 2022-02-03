package mst;

public class Main {

    public static void main(String[] args) {


        test();


        return;
    }


    public static void test() {

        int[][] graph1 =
                {{0, 1, 0, 0, 7}, {1, 0, 4, 3, 5}, {0, 4, 0, 2, 0}, {0, 3, 2, 0, 6}, {7, 5, 0, 6, 0}};

        int[][] graph2 =
                {{0, 15, 0, 9, 1}, {15, 0, 6, 0, 18}, {0, 6, 0, 23, 11}, {9, 0, 23, 0, 4}, {1, 18, 11, 4, 0}};

        int[][] graph3 = {{0, 17, 16, 0, 0}, {17, 0, 0, 18, 0}, {16, 0, 0, 10, 20}, {0, 18, 10, 0, 15},
                {0, 0, 20, 15, 0}};

        int[][] graph4 = {{0, 4, 0, 0, 5}, {4, 0, 3, 6, 1}, {0, 3, 0, 6, 2}, {0, 6, 6, 0, 7}, {5, 1, 2, 7, 0}};
        long startTime, stopTime;
        System.out.println("-----------------------GRAPH-1---------------------------------");
        startTime = System.nanoTime();
        SimulatedAnnealing.mst(graph1);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("--------------------------------------------------------");
        startTime = System.nanoTime();
        VariableNeighborhoodSearch.mst(graph1);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("-----------------------GRAPH-2---------------------------------");
        startTime = System.nanoTime();
        SimulatedAnnealing.mst(graph2);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("--------------------------------------------------------");
        startTime = System.nanoTime();
        VariableNeighborhoodSearch.mst(graph2);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("-----------------------GRAPH-3---------------------------------");
        startTime = System.nanoTime();
        SimulatedAnnealing.mst(graph3);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("--------------------------------------------------------");
        startTime = System.nanoTime();
        VariableNeighborhoodSearch.mst(graph3);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("-----------------------GRAPH-4---------------------------------");
        startTime = System.nanoTime();
        SimulatedAnnealing.mst(graph4);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));

        System.out.println("--------------------------------------------------------");
        startTime = System.nanoTime();
        VariableNeighborhoodSearch.mst(graph4);
        stopTime = System.nanoTime();
        System.out.println("Time : " + (stopTime - startTime));
    }
}
