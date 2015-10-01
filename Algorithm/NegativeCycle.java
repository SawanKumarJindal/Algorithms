/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * NegativeCycle.java
 */
import java.util.Scanner;

/*
 * Class for for checking the Negative Cycle in the graph
 */
public class NegativeCycle {

    public static void main(String[] args) {
        // taking the inputs
        Scanner sc = new Scanner(System.in);
        int numberOfVertices = sc.nextInt();
        int numberOfEdge = sc.nextInt();
        // creating the graph
        int[][] graph = new int[numberOfVertices+1][numberOfVertices+1];
        sc.nextLine();
        int negativeSource = 0;
        int[] sourceofNegiveEdge = new int[numberOfVertices+1];

        for( int i = 0 ; i < numberOfEdge ;i++){
                String[] line = sc.nextLine().split(" ");
                int source = Integer.parseInt(line[0]);
                int destination = Integer.parseInt(line[1]);
                int weight = Integer.parseInt(line[2]);
            if( weight <0){
                negativeSource = source;
            }
            graph[source][destination] = weight;
        }

        for( int i =1 ; i < graph.length;i++){
            if( graph[i][negativeSource]!=0){
                sourceofNegiveEdge[i] = i;
            }
        }
        // runing the Dijkstral algorithm
        dijkstra(graph, negativeSource, sourceofNegiveEdge);

    }

    // Dijkstral algorithm
    private static void dijkstra(int[][] graph, int source, int[] sourceofNegiveEdge) {

        int vertexDistance[] = new int[graph.length];
        boolean ifTheVetexIsAllReadyInc[] = new boolean[graph.length];

        for( int i =1 ; i < graph.length ; i++){
            vertexDistance[i] = Integer.MAX_VALUE;
            ifTheVetexIsAllReadyInc[i] = false;
        }

        vertexDistance[source]= 0;

        for( int i =1 ; i < graph.length ;i++){
            int vertex = min(graph, vertexDistance, ifTheVetexIsAllReadyInc);
            ifTheVetexIsAllReadyInc[vertex] = true;
            for( int v =1 ; v < graph.length ; v++){
                if (!ifTheVetexIsAllReadyInc[v] && graph[vertex][v]!=0  && vertexDistance[vertex]+graph[vertex][v] < vertexDistance[v]) {
                    vertexDistance[v] = vertexDistance[vertex] + graph[vertex][v];
                }
            }
        }
        // finding the the distance back to source, if its negative then YES, then the graph has negative cycle else doesn't contains negative cycle
        boolean chekhing = checkingIfNegtiveCycle(graph, sourceofNegiveEdge, vertexDistance, source);
        if(chekhing){
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

    }

    // adding the last edge and checking if the distance are negative or positive
    private static boolean checkingIfNegtiveCycle(int[][] graph, int[] sourceofNegiveEdge, int[] vertexDistance, int source) {
        for( int i = 1; i < graph.length ;i++){
            if( sourceofNegiveEdge[i]!=0){
                if( vertexDistance[sourceofNegiveEdge[i]] + graph[sourceofNegiveEdge[i]][source]<0 ){
                    int sum = vertexDistance[sourceofNegiveEdge[i]] + graph[sourceofNegiveEdge[i]][source];
                    return true;
                }
            }
        }
        return false;
    }

    // finding the min of all the vertex
    private static int min(int[][] graph, int[] vertexDistance, boolean[] ifTheVetexIsAllReadyInc) {
        int min = Integer.MAX_VALUE;
        int min_index = 0;
        for (int v = 1; v < graph.length; v++)
            if (ifTheVetexIsAllReadyInc[v] == false && vertexDistance[v] <= min) {
                min = vertexDistance[v];
                min_index = v;
            }
        return min_index;
    }
}
