/*
* Created by Sawan Kumar Jindal and Ravi Singh 
*/

import java.util.Scanner;
/*
* This is the main class in which we will find the maximum no of aligned points. We first will calculate the perpendicular equations of every
* two points given to us and then we will compare those equations with each other and equation with the maximum no of occurrence's count will be the solution.
*/
public class AlignPoints
{
    private static Node root;
    static int arr[][];
    static double maxcount=1.0;
    static int noOfPoints;
	// This is the class which will act as an object in which the value of the equation will be stored and then compared.
    private static class Node
    {
        Node left;
        Node right;
        double slope,bcoordinate,count;
		// default constructor which will assign the values.
        Node(double a,double b)
        {
            left = null;
            right = null;
            slope = a;
            bcoordinate=b;
            count=1.0;
        }
    }
    public void AlignPoints()
    {
        root = null;
    }
	// public lookup function which will return the search value.
    public static boolean lookup(double slope,double bcoordinate)
    {
        return(lookup(root, slope,bcoordinate));
    }
	// main functioning lookup function which will look at the nodes and will determine whether the entries are present or not.
    private static boolean lookup(Node node, double slope,double bcoordinate)
    {
        if (node == null)
        {
            return(false);
        }
		//This statement will check for the values present in the heap or not.
        if (slope == node.slope && bcoordinate == node.bcoordinate)
        {
            ++node.count;
            if(node.count > maxcount){
                maxcount=node.count;}
            return(true);
        }
		// This statement will check if the value is lesser or not. If yes, then it will recursively call the left part.
        else if (slope < node.slope || (slope == node.slope && bcoordinate < node.bcoordinate))
        {
            return(lookup(node.left, slope,bcoordinate));
        }
		// Otherwise it will call the right part.
        else
        {
            return(lookup(node.right, slope,bcoordinate));
        }
    }
    public static void insert(double slope,double bcoordinate)
    {
        root = insert(root, slope,bcoordinate);
    }
	// This is the insert function which will insert the values in the heap.
    private static Node insert(Node node, double slope,double bcoordinate)
    {
	    // This condition which will check whether the node is null or not.
        if (node==null)
        {
            node = new Node(slope,bcoordinate);
        }
        else
        {
		    // If the value of node is lesser then it will insert in the left portion.
            if (slope < node.slope || (slope == node.slope && bcoordinate < node.bcoordinate))
            {
                node.left = insert(node.left, slope,bcoordinate);
            }
			// Otherwise it will insert in the right portion.
            else
            {
                node.right = insert(node.right, slope,bcoordinate);
            }
        }
        return(node); 
    }
	// The main function which will first take all the values, store them in the array,does the calculation and will print the output.
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        noOfPoints=sc.nextInt();
        arr = new int[noOfPoints + 1][3];
        for(int i=1;i<=noOfPoints;i++)
        {
            arr[i][1]=sc.nextInt();
            arr[i][2]=sc.nextInt();
        }
        calculation();
        System.out.println((int)maxcount);
    }
	// This is the calculation function which will calculate the midpoint perpendicular equations of every two points and then will check whether
	// they are present or not. If not, then it will insert the value in the data structure.
    public static void calculation()
    {
        for(int i=1;i<noOfPoints;i++)
        {
            for( int j=i+1;j<=noOfPoints;j++)
            {
                int x1=arr[i][1];
                int y1=arr[i][2];
                int x2=arr[j][1];
                int y2=arr[j][2];
                double xm=((x1+x2)/2.0);
                double ym=(y1+y2)/2.0;
                double slopeNewLine=0.0;
                if((y2-y1)==0)
                {
                    double slopeGivenLine=(y2-y1)/(x2-x1);
                    slopeNewLine= -1/slopeGivenLine;
                }
                else
                {
                    slopeNewLine = -1 * (double) (x2 - x1) / (y2 - y1);
                }
                double b= ym - (slopeNewLine * xm);
                if(!lookup(root,slopeNewLine,b))
                {
                    root=insert(root,slopeNewLine,b);
                }
            }
        }
    }
}