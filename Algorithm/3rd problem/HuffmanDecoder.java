/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh 
 * HuffmanDecoder.java
 */
import java.util.Scanner;
import java.lang.String;
/*
* This is a Node class which contains the structure of the nodes which will be the basic structure.
* It contains the three components i.e two nodes(left and right) and data.
*/
class Node{

    Node left;
    Node right;
    String data;

    public Node(){
    }
    public Node(String data){
        this.data = data;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return this.data;
    }
}
/*
* This class is the structure which creates the tree of the Huffman Code(add) and then
* it checks the pattern by visiting the nodes based on the value they have been assigned. 
*/
class Huffman
{
	public Node root;
    public Huffman(){
        this.root = new Node();
    }
	// This add function will add the values in a tree by taking its sequence and then taking each value and comparing it with the 
	// edge assigned to the root node.
	public void add(String data, String sequence){
        Node temp = this.root;
        int i = 0;
        for(i=0;i<sequence.length()-1;i++){

          if(sequence.charAt(i)=='0'){
                if(temp.left == null){
                    temp.left = new Node();
                    temp = temp.left;
                }
                else{
                   temp = (Node) temp.left;
                }
            }
            else
              if(sequence.charAt(i)=='1'){
                if(temp.right == null){
                    temp.right = new Node();
                    temp = temp.right;
                }
                else{
                    temp = (Node) temp.right;
                }
         }
		}

        if(sequence.charAt(i)=='0'){
            temp.left = new Node(data); 
           }
        else{
            temp.right = new Node(data); 
        }
        }
		// This getDecodedMessage function will take the encoded sequence, takes the first character in the sequence and
		// compares it with the edge of the root node and traverses on the left or the right node.	
		public void getDecodedMessage(String encoding){

        String output = "";
        Node temp = this.root;
        for(int i = 0;i<encoding.length();i++){

            if(encoding.charAt(i) == '0'){
                temp = temp.left;

                if(temp.left == null && temp.right == null){
                    System.out.print(temp.getData());
                    temp = this.root;
                }
            }
            else
            {
                temp = temp.right;
                if(temp.left == null && temp.right == null){
                    System.out.print(temp.getData());
                    temp = this.root;  
                }

            }
        }
    }
	}
	// This is the main class which manages the upper two classes by passing the values to the functions.
	public class HuffmanDecoder{
	static int noOfFrequencies;
	// This is the main function which does all the execution.
        public static void main(String[] args)
        {
			Huffman tree= new Huffman();
       	 	 Scanner sc=new Scanner(System.in);
			noOfFrequencies=sc.nextInt();
			// It adds all the user input values in the tree.
			for(int i=1;i<=noOfFrequencies;i++)
			{
				String temp=sc.next();
				tree.add(temp,sc.next());
			}
		int lengthToDecode=sc.nextInt();
		String encodedValue=sc.next();
		// This statement decodes the encoded values.
		tree.getDecodedMessage(encodedValue);
		System.out.println();
		}
}
