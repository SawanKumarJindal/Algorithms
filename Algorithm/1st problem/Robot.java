import java.util.*;
public class Robot {
    static int count=0;
	static int j=0,numOfStacks;
	static String moveStoreArray[];
    public static void main(String[] args) {
        String option;
        Scanner sc = new Scanner(System.in);
        numOfStacks = sc.nextInt();
        sc.nextLine();
        String[] numOfDVDS = sc.nextLine().split(" ");
        int[] num = new int[numOfStacks+1];
		moveStoreArray=new String[numOfStacks + 1];
		//System.out.println("Length of Array:"+ moveStoreArray.length);
        num[0]= Integer.MAX_VALUE;
        for ( int i=0; i<numOfStacks; i++ ){
            num[i+1] = Integer.parseInt(numOfDVDS[i]);
        }
        for ( int i=1; i<num.length; i++ ){
            //  System.out.println(num[i]);
        }
        option = possibleOrImpossible(num, numOfStacks);
        if( option =="POSSIBLE") {
            System.out.println(option);
            minNumberOfMoves(num, 1, numOfStacks,0);
           // System.out.println("count" + " " + count);
           // printStacks(num, numOfStacks);
        }
        else {
            System.out.println(option);
        }
    }

    private static boolean minNumberOfMoves(int[] num, int start, int end, int accessOrDeficiency ) {
        for ( int i=start;i <= end;i++)
		{
				System.out.println("i::" + i);
				accessOrDeficiency += num[i]-i;
				System.out.println("Decifiency:: "+accessOrDeficiency);
				if( num[i] == i)
				{
					System.out.println("Equal");
					continue;
				}	
				else if(accessOrDeficiency >= 0 && i+1 <= end )
				{
					System.out.println("More");
					num[i+1] +=accessOrDeficiency;
					count++;
					System.out.println(String.valueOf(i) +" "+ String.valueOf(i+1) +" " + String.valueOf(accessOrDeficiency));
					moveStoreArray[j]= String.valueOf(i) + String.valueOf(i+1) + String.valueOf(accessOrDeficiency);
					j++;
					return  minNumberOfMoves(num, i+1,end, 0);
				}
				else if( accessOrDeficiency < 0 && num[i+1] < i+1)
				{
					System.out.println("Less but recursion");
				    minNumberOfMoves(num, i+1,end,accessOrDeficiency );
				}
				else
				{
					if(i+1 <= end && (num[i+1] - (i+1) ==accessOrDeficiency)){
					System.out.println("Less");
					num[i] +=num[i+1]-(i+1);
					//System.out.println("Length of Array:"+ moveStoreArray.length + " j::"+j+ "Array Size manuuall::" + numOfStacks);
					count++;
					System.out.println(String.valueOf(i+1) +" "+ String.valueOf(i) +" " + String.valueOf(num[i+1] - (i+1)));
					moveStoreArray[j]= String.valueOf(i+1) + String.valueOf(i) + String.valueOf(num[i+1] - (i+1));
					j++;
					//System.out.println("Hiii");
				}
				else
				 minNumberOfMoves(num, i+1,end,accessOrDeficiency );
				}
		}	
        return true;

    }

    private static void printStacks(int[] num, int numOfStacks) {
        for ( int i=1; i< num.length;i++){
            System.out.print(num[i] + " ");
        }
    }

    private static String possibleOrImpossible(int[] num, int numOfStacks) {
        int sumOfDVDS=0;
        int sumOfAP=(numOfStacks*(numOfStacks+1))/2;
        for( int i=1; i< num.length;i++){
            sumOfDVDS = sumOfDVDS+ num[i];
        }
        if (sumOfAP==sumOfDVDS){
            return "POSSIBLE";
        }
        else {
            return "IMPOSSIBLE";
        }
    }
}