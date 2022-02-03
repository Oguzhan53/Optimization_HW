package sorting;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.*;

public class ParticleSwarm {

    protected static double c1 = 1, c2 = 1;

    protected static double r1 = 0.5, r2 = 0.5;

    protected static double w = 0.5;

    protected static Particle gloBest;


    /**
     * This method sorts the given list with using particle swarm method
     *
     * @param list List given for sorting
     */
    public static void sort(ArrayList<Integer> list) {

        int pSize = 20;
        ArrayList<Particle> population = createPopulation(pSize, list);
        int termCond = pSize * 80, c = 0;
        gloBest = findGlobalBest(population);
        int bestVal = gloBest.getBestVal();
        while(c < termCond) {
            for(int i = 0; i < population.size(); i++) {
                int t = population.get(i).move();
                if(t > bestVal) {
                    bestVal = t;
                    gloBest = population.get(i);
                    c = 0;
                }
                c++;
            }
        }
        System.out.println(gloBest);
    }


    /**
     * This method finds the global best particle in the given popilation
     *
     * @param population Population of particles
     * @return Global best particle
     */
    private static Particle findGlobalBest(ArrayList<Particle> population) {

        Particle best = population.get(0);
        int val = best.getBestVal();
        for(int i = 1; i < population.size(); i++) {
            if(population.get(i).getBestVal() > val) {
                best = population.get(i);
                val = population.get(i).getBestVal();
            }
        }
        return best;
    }


    /**
     * This method creates particles add the population list
     *
     * @param pSize Population size
     * @param list  List which given for sorting
     * @return Population
     */
    private static ArrayList<Particle> createPopulation(int pSize, ArrayList<Integer> list) {

        ArrayList<Particle> population = new ArrayList<>();
        for(int i = 0; i < pSize; i++) {
            population.add(new Particle(list));
        }

        return population;
    }
}
