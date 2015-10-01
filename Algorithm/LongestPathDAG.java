/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh 
 * LongestPathDAG.java
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
 * computing the Topological Sorting. Then we use the finishing time to evaluate the presence of edge using dynamic
 * programming by checking the presence of edge at the finished time.
 */
public class LongestPathDAG
{
	static Vertex[] adjLists;
	static Boolean seen[];
	static int fin[];
	static int time=0;
	// Constructor which will initialise the size of the list and also stores the values of the vertices.
	public LongestPathDAG(int file){
         
        adjLists = new Vertex[file + 1];
        for (int v=1; v < adjLists.length; v++) {
            adjLists[v] = new Vertex(v, null);
        }
    }
	// This DFS method works on the graph itself and the result it produces, is the topological ordering of the graph.
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
	// This is the Topological method which will call DFS method to compute its order by measuring the time on which it is
	// finished.
	public void TopologicalSort()
	{
		seen=new Boolean[adjLists.length + 1];
		fin=new int[adjLists.length+ 1];
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
	}
	// This is the method which will add nodes to the neighbour as adjacency list of the graph. 
	 public void addNeighbors(int v1,int v2)
	 {
            adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
	 }
	 // This is the main method which will first inputs the edges, tokenizes them, adds them in the adjacency list, 
	 //computes the Topological Sorting. After this we get the finishing time and using that we compute the presence 
	 // of edges on 2-D Array as we have to compute the presence of edge and maximize the path. 	
	 public static void main(String[] args)
	{
		String x[]=new String[101];
        Scanner sc = new Scanner(System.in);
        int noOfVertices = sc.nextInt();
		sc.nextLine();
		LongestPathDAG graph = new LongestPathDAG(noOfVertices);
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
		graph.TopologicalSort();
		int twoDArray[][]=new int[noOfVertices+1][noOfVertices+1];
		for(int k=1;k<=noOfVertices;k++)
		{
			for(int j=1;j<=noOfVertices;j++)
			{
				if(k == j)
					twoDArray[k][j] = 0;
			}
		}
		for (int v=adjLists.length -1 ; v >= 1 ; v--) 
		{
			int addingNode=adjLists[v].name;
			for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next)
			{
				int vertexNode=adjLists[nbr.vertexNum].name;
				twoDArray[addingNode][vertexNode] = 1;
				twoDArray[vertexNode][addingNode] = 1;
			}
		}
		int s[]=new int[noOfVertices + 1];
		int max=0;
		for(int j=1;j<=noOfVertices;j++)
		{
			s[j]=0;
			for(int k=1;k<j;k++)
			{
				if(twoDArray[fin[j]][fin[k]] == 1 && s[j] < s[k]+1)
					s[j]=s[k]+1;
			}
			if(max< s[j])
				max=s[j];
				
		}
		System.out.println(max);
    }
}