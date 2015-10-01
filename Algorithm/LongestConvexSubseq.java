import java.util.Scanner;

/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * LongestConvexSubseq.java
 */
public class LongestConvexSubseq {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfNumbers = sc.nextInt(); // which element to find
        sc.nextLine();
        // declaring the variables
        String[] array = sc.nextLine().split(" ");
        int[] inputArray = new int[numberOfNumbers+1];

        // initializing the variables
        inputArray[0] = Integer.MAX_VALUE;
        for (int i = 1; i < array.length+1; i++) {
            inputArray[i] = Integer.parseInt(array[i-1]);
        }

        // finding the longestIncreasingSubseq
        int[][] convex =  new int[numberOfNumbers+1][numberOfNumbers+1];
        // finding the LongestIncreasingConvex
        int length = longestIncreasingConvexSub(convex, inputArray);
        System.out.println(length);
    }

    private static int longestIncreasingConvexSub(int[][] convex, int[] inputArray) {
        for( int i =1; i < convex.length ;i++) {
            for (int j = 1; j < convex.length; j++) {
                if ( i == j ) {
                    convex[i][j] = 1;
                }
            }
        }
        for( int i =1; i < convex.length ;i++){
            for( int j =i+1 ; j < convex.length ;j++){
                int maxConvex =0;
                for( int k = 1 ; k <= i-1 ; k++){
                    if( 2 * inputArray[i] <= inputArray[k] + inputArray[j]){
                        if( maxConvex < convex[k][i]){
                            maxConvex = convex[k][i];
                        }
                    }
                }
                convex[i][j] = 1 + maxConvex;
            }
        }
        // finding the max in heartArray
        int maxLengthOfInceSub = 0;
        for( int i =1 ; i < convex.length ; i++){
            for( int j = 1;  j< convex.length ;j++) {
                if (convex[i][j] > maxLengthOfInceSub) {
                    maxLengthOfInceSub = convex[i][j];
                }
            }
        }
        // returning the maxLengthOfInceSub
        return  maxLengthOfInceSub+1;
    }
}
