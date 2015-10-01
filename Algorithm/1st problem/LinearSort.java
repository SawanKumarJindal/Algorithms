/*
 * File: SumSquares.java
 * Author: Ravi Kumar Singh and Sawan Jindal
 */

import java.util.Scanner;

/*
 * Linearsort Class contains main method which sorts
 * the elements of an array in O(n) using radix sort
 */

public class LinearSort {

    // Counting sort
    public static void counting_sort(int arr[], int n, int exp)
    {

        int[] output= new int[n]; // output array
        int i;
        int[]count= new int[n];
        for (int j=0; j < n; j++)
            count[j] = 0;

        // keep count of occurrences in count array
        for (i = 0; i < n; i++)
            count[ (arr[i]/exp)%n ]++;

        // Change count[i] so that count[i] now contains actual
        // position of this digit in output[]
        for (i = 1; i < n; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--)
        {
            output[count[ (arr[i]/exp)%n] - 1] = arr[i];
            count[(arr[i]/exp)%n]--;
        }

        // Copy the output array to arr[]
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    public static void radix_sort(int[] array, int sizeOfArray){
        // Doing counting sort on unit digit to max digit in base n.
        for( int i=1; i<=sizeOfArray*sizeOfArray; i=i*sizeOfArray) {
            counting_sort(array, sizeOfArray, i);
        }
    }

    public static void print(int[] num) {
        for( int i=0; i< num.length; i++){
            System.out.print(num[i] + " ");
        }
    }

    public static void main(String[] args) {
        // taking the inputs
        Scanner sc = new Scanner(System.in);
        int sizeOfArray = sc.nextInt();
        sc.nextLine();
        String[] numbers = sc.nextLine().split(" ");
        int[] num = new int[sizeOfArray];
        for ( int i=0; i<sizeOfArray; i++ ){
            num[i] = Integer.parseInt(numbers[i]);
        }
        //applying radix sort
        radix_sort(num, sizeOfArray);
        //printing the sorted array
        print(num);
    }
}
