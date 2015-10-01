/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh 
 * OneWay.java
 */
import java.util.Scanner;
/*
 * This is a sub-class which works as a neighbour to the list of vertices. It stores the data as integer.
 */
class Neighbor {
    public int vertexNum;
    public Neighbor next;
    public Neighbor(int vnum, Neighbor nbr) {
            this.vertexNum = vnum;
            next = nbr;
    }
}
/*
 * This is a class which will act as an array that will store the vertices.
 */ 
class Vertex {
    int name;
    Neighbor adjList;
    Vertex(int name, Neighbor neighbors) {
            this.name = name;
            this.adjList = neighbors;
    }
}
/*
 * This is the main class in which we first tokenize the input values, stores them as edges in the adjacent list,
 * computing the strongly connected components. After this, the last component will be connected to the first one,
 * as a possible way of making the whole graph connected and again, the strongly connected component are checked 
 * on the new graph. If they traverse in one single DFS then it is connected.
 */
public class OneWay {
    Vertex[] adjLists;
    Vertex[] adjListsTanspose;
	static int time=0,loop=1,c=0;;
	static Boolean seen[];
	static int fin[];
	static int arr[];
	static int x[];
	// Constructor which will initialise the size of the list and also stores the values of the vertices.
    public OneWay(int file){
         
        adjLists = new Vertex[file + 1];
        for (int v=1; v < adjLists.length; v++) {
            adjLists[v] = new Vertex(v, null);
        }
		adjListsTanspose = new Vertex[file + 1];
        for (int v=1; v < adjListsTanspose.length; v++) {
            adjListsTanspose[v] = new Vertex(v, null);
        }
    }
	// This DFS version works on the graph itself and the result it produces, is the topological ordering of the graph.
	public void DFS(int s)
	{
		seen[s]=true;
		 for (Neighbor nbr=adjLists[s].adjList; nbr != null;nbr=nbr.next)
			{
			   if(seen[adjLists[nbr.vertexNum].name]== false )
					DFS(adjLists[nbr.vertexNum].name);	
            }
		time++;
		fin[time]= s;		
	}
	// This version of DFS works on the transpose of the graph and will eventually give us the strongly connected components.
	public void DFSNEW(int s)
	{
		int i=1;
		x[i] = s;
		seen[s]=true;
		i++;
		 for (Neighbor nbr=adjListsTanspose[s].adjList; nbr != null;nbr=nbr.next)
			{
			   if(seen[adjListsTanspose[nbr.vertexNum].name]== false)
					DFSNEW(adjListsTanspose[nbr.vertexNum].name);		
            }
	}
	// This method will compute the transpose of the original graph by reversing the edges.
	public void computeTranspose()
	{
		for (int v=adjLists.length -1 ; v >= 1 ; v--) 
		{
			int addingNode=adjLists[v].name;
			for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next)
			{
				int vertexNode=adjLists[nbr.vertexNum].name;
				adjListsTanspose[vertexNode].adjList = new Neighbor(addingNode, adjListsTanspose[vertexNode].adjList);
			}
		}		
	}
	// This method computes the strongly connected components of the graph by computing the topological ordering of it,
	// reversing the graph, computing the DFS on the reversed graph by starting from the last finished node.
     public void strongConnectedComponents()
	{
		c++;
		seen=new Boolean[adjLists.length + 1];
		fin=new int[adjLists.length+ 1];
		arr=new int[adjLists.length + 1];
		x=new int[adjLists.length+ 1];
		for(int i=1;i<adjLists.length; i++)
		{
			seen[i]=false;
			fin[i]=1;
		}
		for(int v=1;v<adjLists.length; v++)
		{
			if(seen[v]==false)
			{
				DFS(v);
			}	
		}
		if(c != 2)
			computeTranspose();
		for(int i=1;i<seen.length; i++)
		{
			seen[i]=false;
		}
		for (int v=adjListsTanspose.length -1 ; v >= 1 ; v--) 
		{
			if(seen[fin[v]] ==  false)
			{
				arr[loop]=fin[v];
				loop++;	
				DFSNEW(fin[v]);
			}	
		}	
	}
	// This is the method which will add nodes to the neighbour as adjacency list of the graph. 
	 public void addNeighbors(int v1,int v2)
	 {
            adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
	 }
	 // This is the method which will add nodes to the neighbour as adjacency list of the transposed graph.
	  public void addTransposeNeighbors(int v1,int v2)
	 {
            adjListsTanspose[v1].adjList = new Neighbor(v2, adjListsTanspose[v1].adjList);
	 }
	 // This is the main method which will first inputs the edges, tokenizes them, adds them in the adjacency list, 
	 //computes the strongly connected components. We will add the last component with the first component, add the reversal in the 
	 // transpose, computes the strongly connected components on the new graph.	
    public static void main(String[] args)
	{
		String x[]=new String[101];
        Scanner sc = new Scanner(System.in);
        int noOfVertices = sc.nextInt();
		sc.nextLine();
		OneWay graph = new OneWay(noOfVertices);
		int i=1;
		while(i<=noOfVertices && sc.hasNext())
		{
			String line=sc.nextLine();
			int k=1;
			for(String token : line.split(" "))
			{
				
				x[k]=token;
				k++;
			}
			if(k == 2)
				{}
			else if(k == 3)
					graph.addNeighbors(i,Integer.parseInt(x[1]));
			else if(Integer.parseInt(x[1]) > Integer.parseInt(x[k-2]))
				{
					for(int p=1;p<=k-2;p++)
					graph.addNeighbors(i,Integer.parseInt(x[p]));
				}
			else
			{		for(int p=k-2;p>=1;p--)
					graph.addNeighbors(i,Integer.parseInt(x[p]));
			}
			i++;
		}
		graph.strongConnectedComponents();
		int u=arr[loop-1];
		int v=arr[1];
		graph.addNeighbors(u,v);	
		graph.addTransposeNeighbors(v,u);
		time=0;
		loop=1;
		graph.strongConnectedComponents();
		if(loop == 2)
		{
			System.out.println("YES");
			System.out.println(u+" "+v);
		}
		else
			System.out.println("NO");
    }
}