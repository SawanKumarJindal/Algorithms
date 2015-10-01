import java.util.Scanner;

/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * SortCards.java
 */

public class SortCards {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfNumbers = sc.nextInt(); // which element to find
        sc.nextLine();
        // declaring the variables
        String[] array = sc.nextLine().split(" ");
        int[] inputArray = new int[numberOfNumbers];
        int[] heartArray = new int[numberOfNumbers];

        // initializing the variables
        for (int i = 0; i < array.length; i++) {
            inputArray[i] = Integer.parseInt(array[i]);
        }
        // finding the longestIncreasingSubseq
        int length = longestIncreasingSubsequense(inputArray, heartArray, numberOfNumbers );
        // printing the least number of move
        System.out.println(numberOfNumbers - length);

        }
    private static int longestIncreasingSubsequense(int[] inputArray, int[] heartArray, int numberOfNumbers) {
        for ( int j =0 ; j < inputArray.length ; j++){
            // initializing every heartArray to 1
            heartArray[j] =1;
            // checking to all elements previous or equal to j
            for ( int k = 0 ; k <= j-1 ;k++){
                // if the element in k is less than element in j and heartArray[k] + 1 is more than heartArray[j], means we can increase length of
                // longestIncreasingSubsequense
                if ( inputArray[k] < inputArray[j]  && heartArray[j] < heartArray[k] +1){
                    heartArray[j] = heartArray [k] +1;
                }
            }
        }
        // finding the max in heartArray
        int maxLengthOfInceSub = heartArray[0];
        for( int i =1 ; i < heartArray.length ; i++){
            if (heartArray[i]> maxLengthOfInceSub){
                maxLengthOfInceSub = heartArray[i];
            }
        }
        // returning the maxLengthOfInceSub
        return  maxLengthOfInceSub;
    }

}
