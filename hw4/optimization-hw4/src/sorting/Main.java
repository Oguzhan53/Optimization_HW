package sorting;

import java.util.ArrayList;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        test(50, 5);
    }


    public static void test(int listSize, int testRepeat) {

        long startTime, stopTime;
        Random rand = new Random();
        for(int i = 0; i < testRepeat; i++) {
            ArrayList<Integer> testArr = new ArrayList<>();
            for(int j = 0; j < listSize; j++) {
                testArr.add(rand.nextInt(1000));
            }
            ArrayList<Integer> testArr2 = new ArrayList<>(testArr);
            System.out.println("--------------------------------------------------------");
            startTime = System.currentTimeMillis();
            ParticleSwarm.sort(testArr);
            stopTime = System.currentTimeMillis();
            System.out.println("Time(ms) : " + (stopTime - startTime));
        }
    }
}
