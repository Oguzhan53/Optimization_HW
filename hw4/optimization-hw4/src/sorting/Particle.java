package sorting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Particle {

    private ArrayList<Integer> location;

    private ArrayList<Integer> best;

    private ArrayList<Set<Integer>> velocity;

    private Random rand;

    private int bestVal;


    public Particle(ArrayList<Integer> list) {

        rand = new Random();
        location = getRandLocation(list);
        velocity = createRandVelocity();
        best = new ArrayList<>(location);
        setBestVal();
    }


    public ArrayList<Integer> getLocation() {

        return location;
    }


    public void setLocation(ArrayList<Integer> location) {

        this.location = location;
    }


    public ArrayList<Integer> getBest() {

        return best;
    }


    public void setBest(ArrayList<Integer> best) {

        this.best = new ArrayList<>(best);
    }


    public ArrayList<Set<Integer>> getVelocity() {

        return velocity;
    }


    public void setVelocity(ArrayList<Set<Integer>> velocity) {

        this.velocity = velocity;
    }


    public int getBestVal() {

        return bestVal;
    }


    public void setBestVal() {

        this.bestVal = this.findVal(location);
    }


    public int move() {

        getNewVelocity();
        addVelToLoc();
        localSearch();
        return getBestVal();
    }


    /**
     * This method search best solution between  neighbor of the given solution
     */
    private int localSearch() {

        int curVal = findVal(location), nextVal = 0;
        for(int i = 0; i < location.size(); i++) {
            ArrayList<Integer> neighbor = getNeighbor(location, i);
            nextVal = findVal(neighbor);
            if(nextVal > curVal) {
                location.clear();
                location.addAll(neighbor);
                curVal = nextVal;
            }
        }

        return curVal;
    }


    /**
     * This method returns the neighbor of the solution
     */
    private ArrayList<Integer> getNeighbor(ArrayList<Integer> list, int in) {

        ArrayList<Integer> neighbor = new ArrayList<>(list);
        int size = neighbor.size();
        swap(neighbor, in % size, (in + 1) % size);

        return neighbor;
    }


    @Override
    public String toString() {

        return "Particle Swarm method : " + best + "\n Objective function value =" + bestVal;
    }


    /**
     * This method creates randomly sorted list from given list
     *
     * @param list List which given for sorting
     */
    private ArrayList<Integer> getRandLocation(ArrayList<Integer> list) {

        location = new ArrayList<>(list);
        for(int i = 0; i < location.size(); i++) {
            swap(location, i, rand.nextInt(list.size() - 1));
        }
        return location;
    }


    /***
     *  This method creates random velocity for initialization for each particle
     *
     * @return Velocity vector
     */
    private ArrayList<Set<Integer>> createRandVelocity() {

        ArrayList<Set<Integer>> velocity = new ArrayList<>();

        for(int i = 0; i < location.size() / 3; i++) {
            Set<Integer> el = new HashSet<>();
            el.add(rand.nextInt(location.size() - 1));
            el.add(rand.nextInt(location.size() - 1));
            velocity.add(el);
        }

        return velocity;
    }


    /**
     * This method finds the solution value according to objective function
     */
    private int findVal(ArrayList<Integer> location) {

        int counter = 0;
        for(int i = 0; i < location.size(); i++) {
            for(int j = i + 1; j < location.size(); j++) {
                if(location.get(i) <= location.get(j)) {
                    counter++;
                }
            }
        }

        return counter;
    }


    /**
     * This method calculates the new velocity vector
     *
     * @return New velocity vector
     */
    private void getNewVelocity() {

        ArrayList<Set<Integer>> pBestSwapList = getVelocitySwap(location, best);
        ArrayList<Set<Integer>> gBestSwapList = getVelocitySwap(location, ParticleSwarm.gloBest.getBest());
        ArrayList<Set<Integer>> newVelocity = new ArrayList<>();
        double p1 = ParticleSwarm.c1 * ParticleSwarm.r1;
        double p2 = ParticleSwarm.c2 * ParticleSwarm.r2;
        int v1Size = (int) (velocity.size() * ParticleSwarm.w);
        int v2Size = (int) (location.size() * p1);
        int v3Size = (int) (ParticleSwarm.gloBest.getBest().size() * p2);
        if(velocity.size() <= v1Size) {
            newVelocity.addAll(velocity);
        } else {
            newVelocity.addAll(velocity.subList(0, v1Size));
        }
        if(pBestSwapList.size() <= v2Size) {
            newVelocity.addAll(pBestSwapList);
        } else {
            newVelocity.addAll(pBestSwapList.subList(0, v2Size));
        }
        if(pBestSwapList.size() <= v3Size) {
            newVelocity.addAll(pBestSwapList);
        } else {
            newVelocity.addAll(gBestSwapList.subList(0, v3Size));
        }

        setVelocity(newVelocity);
    }


    /**
     * This method create the swap indexes for reach from location value to destination value
     *
     * @param locVec  Current location value
     * @param destVec It may be the personal best value or the global destination value
     * @return Swaps index list
     */
    private ArrayList<Set<Integer>> getVelocitySwap(ArrayList<Integer> locVec, ArrayList<Integer> destVec) {

        ArrayList<Integer> copy = new ArrayList<>(locVec);
        ArrayList<Set<Integer>> swapList = new ArrayList<>();
        for(int i = 0; i < destVec.size(); i++) {

            if(destVec.get(i) != copy.get(i)) {
                for(int j = i; j < copy.size(); j++) {
                    if(copy.get(j) == destVec.get(i)) {
                        Set<Integer> buff = new HashSet<>();
                        buff.add(i);
                        buff.add(j);
                        swap(copy, i, j);
                        swapList.add(buff);
                    }
                }
            }
        }

        return swapList;
    }


    /**
     * This method swaps given index values
     *
     * @param list List
     * @param i    Firts swap element index
     * @param j    Second swap element index
     */
    private void swap(ArrayList<Integer> list, int i, int j) {

        int buff = list.get(i);
        list.set(i, list.get(j));
        list.set(j, buff);
    }


    /**
     * This method adds current location to velocity vector
     */
    private void addVelToLoc() {

        for(int i = 0; i < velocity.size(); i++) {

            int[] elemets = getSetEl(velocity.get(i));
            swap(location, elemets[0], elemets[1]);
        }
        int newVal = findVal(location);
        if(newVal > bestVal) {
            setBestVal();
            setBest(location);
        }
    }


    /**
     * This method returns the elements of the given set
     *
     * @param set Set for getting element
     * @return Elements of set array
     */
    private int[] getSetEl(Set<Integer> set) {

        int[] element = new int[2];
        boolean f1 = true;
        for(int j : set) {
            if(f1) {
                element[0] = j;
                f1 = false;
            } else {
                element[1] = j;
            }
        }

        return element;
    }
}
