import java.util.Scanner;

/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * StringConvert.java
 */

public class StringConvert {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String firstString = sc.nextLine();
        String secondString = sc.nextLine();
        int lengthOfFirstString = firstString.length(); // first string
        int lengthOfSecondString =  secondString.length(); // second string
        int[][] distance = new int[lengthOfSecondString+1][]; // creating 2D array

        for( int i = 0 ; i < lengthOfSecondString + 1 ; i++){
            distance[i] = new int[lengthOfFirstString+1];
        }
        // finding the min converstion cost
        int minCost = stringConverstion(firstString, secondString, distance, lengthOfFirstString, lengthOfSecondString);
        System.out.println(minCost);
    }

    private static int stringConverstion(String firstString, String secondString, int[][] distance, int lengthOfFirstString, int lengthOfSecondString) {
        int substitution = 0;
        int deletion = 0;
        int insertion = 0;
        // initialize the 2D array
        for (int i = 0; i < lengthOfSecondString + 1; ++i) {
            distance[i][0] = 4 * i;
        }
        // initialize the 2D array
        for (int j = 0; j < lengthOfFirstString + 1; ++j) {
            distance[0][j] = 3 * j;
        }

        for (int i = 1; i < lengthOfSecondString + 1; ++i) {
            for (int j = 1; j < lengthOfFirstString + 1; ++j) {
                // If the both the string are same at i and j
                if (firstString.charAt(j - 1) == secondString.charAt(i - 1)) {
                    distance[i][j] = distance[i - 1][j - 1];
                } else { // if not
                    deletion = distance[i][j - 1] + 3; // finding the deletion cost
                    insertion = distance[i - 1][j] + 4; // finding the insertion cost
                    if (j >= 2) {
                        substitution = distance[i - 1][j - 2] + 5; // finding the substitution cost when the chars are more than 2
                    } else {
                        substitution = distance[i][j - 1] + distance[i - 1][j]; // finding the substitution cost when the chars are less than 2
                    }
                    distance[i][j] = min(deletion, insertion, substitution); // finding the min of all the three operations
                }
            }
        }
        // return the least cost
        return distance[lengthOfSecondString][lengthOfFirstString];
    }

    // finding the min of the three numbers
    static int min(int num1, int num2, int num3)
    {
        int less = (num1 < num2) ? num1 : num2;
        return (less < num3) ? less : num3;
    }
}
