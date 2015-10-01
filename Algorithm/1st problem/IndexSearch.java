/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh 
 * IndexSearch.java
 */
import java.util.*;
/*
 * This class searches the index of an input by firstly storing in the array
 * and since the array is already sorted, it first calculates its middle value
 * and then checks whether the middle value is less or greater than the value stored 
 * in array. If less then it will check the upper half array and vice versa. 
 */
public class IndexSearch
{
	public static int arr[];
	int middleValue=0;
	static int inputNumber;
    static boolean checkNumber(int arr[],int lower,int upper)
	{
		int middleValue = (lower + upper)/2;
		// This condition will occur when the value is not present in array.
		if(middleValue <lower || middleValue > upper)
		{
			return false;
		}
		// This condition will occur when the value is present in array.
		if (arr[middleValue] == middleValue)
		{
			return true;
		}
		// This will change the region of checking to half ie. lower part of array.
		else if (arr[middleValue] > middleValue)
		{	
			return checkNumber(arr , lower , middleValue-1 );
		}
		// This will change the region of checking to half ie. upper part of array.
		else
		{
			return checkNumber(arr , middleValue+1 , upper );
		}
	}
	public static void main(String args[])
	{
		Scanner sc  = new Scanner(System.in);
		inputNumber=sc.nextInt();
		arr=new int[inputNumber + 1];
		for (int i=1;i<=inputNumber;i++)
			arr[i]=sc.nextInt();
		if(checkNumber(arr,1,inputNumber))
			System.out.println("TRUE");
		else
			System.out.println("FALSE");
	}
}