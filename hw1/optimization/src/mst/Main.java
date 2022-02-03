/**
 *
 */
package mst;

import java.util.*;

/**
 * @author Oguzhan SEZGIN 
 *
 */
public class Main {

    public static void main(String[] args) {

        int[][] graph =
                {{-1, 1, -1, -1, 7}, {1, -1, 4, 3, 5}, {-1, 4, -1, 2, -1}, {-1, 3, 2, -1, 6}, {7, 5, -1, 6, -1}};
        //int[][] graph = { { -1, 17,16,-1,-1 }, {17,-1,-1,18,-1 },{16,-1,-1,10,20},{-1,18,10,-1,15},{-1,-1,20,15,-1}};
        // int[][] graph =
        //        {{-1, 15, -1, 9, 1}, {15, -1, 6, -1, 18}, {-1, 6, -1, 23, 11}, {9, -1, 23, -1, 4}, {1, 18, 11, 4, -1}};

        //   int [][]graphArray ={ { -1, 4,-1,-1,5 }, {4,-1,3,6,1 },{-1,3,-1,6,2},{-1,6,6,-1,7},{5,1,2,7,-1}};

        BranchandBound.mst(graph);

        BruteForce.mst(graph);
    }
}
