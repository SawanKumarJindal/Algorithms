import java.util.Scanner;
public class LongestConvexSubseq
{
	public static void main(String args[])
	{
		 Scanner sc=new Scanner(System.in);
		 int n=sc.nextInt();
		 
		int LCS[N];
LCS[0] = 1; LCS[1] =2 ;

for(i=2;i<N;i++)
{
   LCS[i] = 2;
   for(j=1;j<i;j++)
   {
       for(k=0;k<j;k++)
       {
           if(LCS[j]-1 == LCS[k] && A[j] < (A[k] + A[i])/2 )
               LCS[i] = max( LCS[i], 1+LCS[j]);
       }
   }
}

