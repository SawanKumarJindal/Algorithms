/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh 
 * MatrixChainParanthesize.java
 * References: Thomas H Cormen
 */
import java.util.Scanner;
/*
* This is the main class in which we first compute the array which stores the intermediate value,k
* and then using that array we divide it into the format of parenthesis.
*/
class MatrixChainParenthesize
{
	static int arr[];
	static int m[][],s[][];
	// This is the method which performs the task of matrix chain multiplication by dynamic programming.
	public static void matrixCalculation()
	{
		int n=arr.length - 1;
		m=new int[n+1][n+1];
		s=new int[n][n+1];
		for(int i=1;i<n+1;i++)
			m[i][i]=0;
		for( int l=2;l<=n;l++)
		{
			for(int i=1;i<= n+1-l;i++)
			{
				int j=i+l-1;
				m[i][j]=200000000;
				for(int k=i;k<=j-1;k++)
				{
					int q=m[i][k]+m[k+1][j]+arr[i-1]*arr[k]*arr[j];
					if(q<m[i][j])
					{
						m[i][j]=q;
						s[i][j]=k;
					}
				}
			}
		}	
	}
	// This is a recursive function which first goes to the k value stored at the value(i,j)
	// and then checks the k value stored at that location and when the value in location is similar to its ow value, it will 
	// print that value followed by * and then it will perform the second recursive function.
	public static void printParanthesis(int s[][],int i,int j)
	{
		if(i==j)
			System.out.print("A"+i);
		else
		{
			System.out.print("( ");
			printParanthesis(s,i,s[i][j]);
			System.out.print(" X ");
			printParanthesis(s,s[i][j]+1,j);
			System.out.print(" )");
		}
	}	
	// This is the main function which will first takes the values of the sequence and stores it in the array,
	// computes the matrix multiplication and the parenthesis function. 
	public static void main(String args[])
	{
		 Scanner sc=new Scanner(System.in);
		 int n=sc.nextInt();
		 arr=new int[n+1];
		 for(int i=0;i<n+1;i++)
		 {
			arr[i]=sc.nextInt();
		}	 
		matrixCalculation();
		printParanthesis(s,1,n);
	}
}
