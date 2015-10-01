import java.util.Scanner;

/**
 * Created by ravikumarsingh on 2/10/15.
 */
public class SawanRobot {
    static int count=0;
    public static void main(String[] args) {
        String option;
        Scanner sc = new Scanner(System.in);
        int numOfStacks = sc.nextInt();
        sc.nextLine();
        String[] numOfDVDS = sc.nextLine().split(" ");
        int[] num = new int[numOfStacks+1];
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
            minNumberOfMoves(num, 0, numOfStacks, 0);
            System.out.println("count" + " " + count);
            printStacks(num, numOfStacks);
        }
        else {
            System.out.println(option);
        }
    }

    private static int minNumberOfMoves(int[] num, int start, int end, int accessOrDeficiency ) {
        int extra=0;
        int deficiency=0;
        int isExtra;
		int i= start+1;
		deficiency=num[i]-i;
		if(deficiency > 0)
		   {
			num[i+1]+= deficiency;
			count++;
			System.out.println("Move from :" + (i+1) " to: "+ i +" " + deficiency
		   }
        if( start==end ){
            num[start]=num[start]-(accessOrDeficiency);
          //  System.out.println("accessOrDeficiency"+accessOrDeficiency);
            return accessOrDeficiency;
        }
        
        // for ( int i=start+1; i <= end; i++){
           /* if( num[i]> i){
                extra= num[i]-i;
                    minNumberOfMoves(num, i, end, extra);

            }
            if( num[i]< i ){
                dificency= i -num[i];
                minNumberOfMoves(num, i, end, dificency);
            }
            else{
                continue;
            } */
        extra= num[i]-i;
        isExtra = minNumberOfMoves(num, i, end, extra);
      //  System.out.println("start"+ start+"isExtra"+isExtra);
        if(num[start]!=start && isExtra!=0 && start!=0){

            num[start]+=isExtra;
            System.out.println(start +1+" "+ start +" " + isExtra );
            count++;
            System.out.println("Strat"+start+"Isextra"+isExtra+"acc::"+accessOrDeficiency);
            if( accessOrDeficiency>0){
                isExtra+=accessOrDeficiency;
                num[start]=start;
            }
            else {
                isExtra+=accessOrDeficiency;
                num[start]=start;
            }

           // System.out.println(start +1+" "+ start +" " + start );
            return isExtra;

        }

        //   }
        return -1;
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