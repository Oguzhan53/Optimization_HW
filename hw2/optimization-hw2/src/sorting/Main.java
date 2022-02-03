package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {



        test(10,5);

    }


    public static void test(int listSize, int testRepeat) {

        long startTime, stopTime;
        Random rand = new Random();
        for(int i = 0; i < testRepeat; i++) {
            ArrayList<Integer> testArr = new ArrayList<>();
            for(int j = 0; j < listSize; j++) {
                testArr.add(rand.nextInt(1000));
            }

            System.out.println("--------------------------------------------------------");
            startTime = System.nanoTime();
            SimulatedAnnealing.sort(testArr);
            stopTime = System.nanoTime();
            System.out.println("Time : " + (stopTime - startTime));
            System.out.println("--------------------------------------------------------");
            startTime = System.nanoTime();
            VariableNeighborhoodSearch.sort(testArr);
            stopTime = System.nanoTime();
            System.out.println("Time : " + (stopTime - startTime));
        }
    }
}
