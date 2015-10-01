import java.util.Scanner;

/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * HowManyMST.java
 */

/*
 * Class for creating edges
 */
class Edge{
    int source;
    int dest;
    int weight;
    int count;

    Edge(){
        source=0;
        dest=0;
        weight=0;
        count=0;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String toString(){
        return "Source " + source + " Dest " + dest + " Weight "+ weight + " count" + count;
    }
}

/*
 * Class for finding the Boss
 */
class Subset {
    int parent;
    int rank;
    Subset(int parent, int rank){
        this.parent = parent;
        this.rank = rank;
    }
}

/*
 * Class for creating AdjacencyList for storing graph
 */
class AdjacencyList{
    AdjacencyListNode head;
    AdjacencyList(){
        head = null;
    }
}

/*
 * Class for creating AdjacencyListNode for storing graph
 */
class AdjacencyListNode{
    int weight;
    int vertex;
    AdjacencyListNode next;
    AdjacencyListNode(int weight, int vertex){
        this.weight = weight;
        this.vertex = vertex;
        this.next =null;
    }
}

/*
 * Class for creating Graph
 */
class Graph{
    int noOfVertex;
    int numberOfEdge;
    AdjacencyList[] array;

    void creatingGraph(int vertex){
        noOfVertex = vertex;
        array = new AdjacencyList[vertex];
        for ( int i = 0 ; i < vertex ; i++){
            array[i] = new AdjacencyList();
        }
    }

    void addingEdges(int source, int destination, int weight){
        // for adding source to destination
        AdjacencyListNode nodeToAdd = new AdjacencyListNode(weight, destination);
        nodeToAdd.next = array[source].head;
        array[source].head = nodeToAdd;
    }
}

/*
 * Class for finding how many MST will be there in the graph
 */
public class HowManyMST {
    static int mark=0; // used for counting the MST

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int numberOfVertices = sc.nextInt();
        int numberOfEdge = sc.nextInt();

        // creating and intilizing graph
        Graph graph = new Graph();
        graph.noOfVertex = numberOfVertices;
        graph.numberOfEdge = numberOfEdge;

        // some local variables
        int edgeInResult =0;
        int edgeInResult1 =0;
        int edgeInSorted =0;

        // for storing the boss of each vertex
        Subset[] subsets = new Subset[graph.noOfVertex];
        // for storing the first MST
        Edge[] result = new Edge[graph.noOfVertex-1];
        // for storing the rest all MST
        Edge[] result1 = new Edge[graph.noOfVertex-1];

        for( int i =0 ; i < result.length ;i++){
            result[i] = new Edge();
        }
        for( int i =0 ; i < result1.length ;i++){
            result1[i] = new Edge();
        }
        // for storing all sorted edges
        Edge[] edges1 = new Edge[graph.numberOfEdge];
        sc.nextLine();
        graph.creatingGraph(numberOfVertices);
        // adding edges to graph
        for( int i = 0 ; i < numberOfEdge ;i++){
            String[] line = sc.nextLine().split(" ");
            graph.addingEdges(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
        }
        // initilizing Boss
        for( int i =0; i < subsets.length ; i++){
            subsets[i] = new Subset(i, 0);
        }

        // sorting all the edges using merge sort
        edges1= mergeSortUtil(graph);

        // assigning count to each edges based on its occurences
        for(int i =0; i < edges1.length ; i++ ){
            if( i+1 < edges1.length) {
                if (edges1[i].weight == edges1[i + 1].weight) {
                    edges1[i].count = 2;
                    edges1[i+1].count = 2;
                } else {
                    if(edges1[i].count ==2){
                        continue;
                    }
                    else {
                        edges1[i].count = 1;
                    }
                }
            }
            else {
                if(edges1[i].count ==2){

                }else {
                    edges1[i].count = 1;
                }
            }

        }
        int flag =0;
        // Checking all edges before adding to MST
        while (edgeInResult < numberOfVertices-1){
            Edge next_edge = edges1[edgeInSorted++];
            if( next_edge.weight!= result[edgeInResult].weight) {
                int x = find(subsets, next_edge.getSource());
                int y = find(subsets, next_edge.getDest());
                if (x != y) {
                    result[edgeInResult++] = next_edge;
                    unionOfTwoTree(subsets, x, y);
                }
            }
        }
        // resetting all variables
        edgeInSorted=0;
        for( int i =0; i < subsets.length ; i++){
            subsets[i] = new Subset(i, 0);
        }

        for( int i =0; i < result.length ;i++){
            Edge next_edge = result[i]; // checking the first edge in the result array
           // System.out.println("next_edge " + next_edge);
            if( next_edge.count == 1){ //if only one edge is present then  just continue
                continue;
            }
            else if( next_edge.count == 2){ // else
                if( i+1 < result.length) {
                    Edge next_to_next_edge = result[i + 1]; // checking also the next to next edge
                    if (next_edge.weight == next_to_next_edge.weight) { // if both the edges of same weight are already added then continue
                        i=i+1;
                        continue;
                    } else {
                        int index = findingTheEdge( edges1, next_edge); // else, find the index of the edge
                        if( edges1[index].weight == edges1[index+1].weight){
                            swap( edges1, index, index+1); // swaping the edges and checking to see if the new MST is possible or not
                            while (edgeInResult1 < numberOfVertices-1){
                                Edge next_edge1 = edges1[edgeInSorted++];
                                if( next_edge1.weight!= result1[edgeInResult1].weight) {
                                    int x = find(subsets, next_edge1.getSource());
                                    int y = find(subsets, next_edge1.getDest());
                                    if (x != y) {
                                        result1[edgeInResult1++] = next_edge1;
                                        unionOfTwoTree(subsets, x, y);
                                    }
                                }
                            }
                            // if possible then increment mark value
                            mark++;
                        }
                        else if(edges1[index].weight == edges1[index-1].weight){
                            swap( edges1, index, index-1);
                            // reseting the flags
                            edgeInResult1 =0;
                            edgeInSorted=0;
                            for( int k =0; k < subsets.length ; k++){
                                subsets[k] = new Subset(k, 0);
                            }
                            for( int l =0 ; l < result1.length ;l++){
                                result1[l] = new Edge();
                            }
                            while (edgeInResult1 < numberOfVertices-1){
                                Edge next_edge1 = edges1[edgeInSorted++];
                                if( next_edge1.weight!= result1[edgeInResult1].weight) {
                                    int x = find(subsets, next_edge1.getSource());
                                    int y = find(subsets, next_edge1.getDest());
                                    if (x != y) {
                                        result1[edgeInResult1++] = next_edge1;
                                        unionOfTwoTree(subsets, x, y);
                                    }
                                }
                            }
                            // if new MST is possible then incrementing the mark variable
                            mark++;
                        }
                        for( int newi =0; newi < result.length ;newi++){
                            // if the both the MST are same
                            if( result[newi].source == result1[newi].source && result[newi].dest== result1[newi].dest && result[newi].weight== result1[newi].weight && result[newi].count== result1[newi].count){
                               flag++;
                                continue;
                            }
                        }
                        //then decremnet the mark variable
                        if( flag == result.length){
                            mark--;
                        }

                    }
                }
                else { // else you are at the last edge in result array
                    int index = findingTheEdge( edges1, next_edge);
                    if( edges1[index].weight == edges1[index+1].weight){
                        swap( edges1, index, index+1);
                        edgeInResult1 =0;
                        edgeInSorted=0;
                        for( int k =0; k < subsets.length ; k++){
                            subsets[k] = new Subset(k, 0);
                        }
                        for( int l =0 ; l < result1.length ;l++){
                            result1[l] = new Edge();
                        }
                        while (edgeInResult1 < numberOfVertices-1){
                            Edge next_edge1 = edges1[edgeInSorted++];
                            if( next_edge1.weight!= result1[edgeInResult1].weight) {
                                int x = find(subsets, next_edge1.getSource());
                                int y = find(subsets, next_edge1.getDest());
                                if (x != y) {
                                    result1[edgeInResult1++] = next_edge1;
                                    unionOfTwoTree(subsets, x, y);
                                }
                            }
                        }
                       // if another MST is possible
                        mark++;
                    }
                    else if(edges1[index].weight == edges1[index-1].weight){
                        swap( edges1, index, index-1);
                        edgeInResult1 =0;
                        edgeInSorted=0;
                        for( int k =0; k < subsets.length ; k++){
                            subsets[k] = new Subset(k, 0);
                        }
                        for( int l =0 ; l < result1.length ;l++){
                            result1[l] = new Edge();
                        }
                        while (edgeInResult1 < numberOfVertices-1){
                            Edge next_edge1 = edges1[edgeInSorted++];
                            if( next_edge1.weight!= result1[edgeInResult1].weight) {
                                int x = find(subsets, next_edge1.getSource());
                                int y = find(subsets, next_edge1.getDest());
                                if (x != y) {
                                    result1[edgeInResult1++] = next_edge1;
                                    unionOfTwoTree(subsets, x, y);
                                }
                            }
                        }
                        mark++; // incrementing mark
                    }
                    for( int newi =0; newi < result.length ;newi++){
                        // if the both the MST are same
                        if( result[newi].source == result1[newi].source && result[newi].dest== result1[newi].dest && result[newi].weight== result1[newi].weight && result[newi].count== result1[newi].count){
                            flag++;
                            continue;
                        }
                    }
                    // then decrementing the mark count
                    if( flag == result.length){
                        mark--;
                    }

                }
            }
        }
        Double numberOfMST = Math.pow(2.0, (double) mark);
        System.out.println(numberOfMST.intValue());
    }

   /*
    * Function to swap the edges
    */
    public static void swap(Edge[] edges1, int i, int j) {
        Edge tmp = edges1[i];
        edges1[i] = edges1[j];
        edges1[j] = tmp;
    }

   /*
    * Function to find the edge in the sorted array
    */
    private static int findingTheEdge(Edge[] edges, Edge edge) {
        int low = 0;
        int high = edges.length - 1;
        while(high >= low) {
            int middle = (low + high) / 2;
            if(edges[middle].weight == edge.weight) {
                return middle;
            }
            if(edges[middle].weight < edge.weight) {
                low = middle + 1;
            }
            if(edges[middle].weight > edge.weight) {
                high = middle - 1;
            }
        }
        return -1;
    }

   /*
    * Union of the two trees into a forest
    */
    private static void unionOfTwoTree(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as root and increment
            // its rank by one
        else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

   /*
    * finding the boss of each edges before adding
    */
    private static int find(Subset[] subsets, int source) {
        if (subsets[source].parent != source) {
            subsets[source].parent = find(subsets, subsets[source].parent);
        }
        return subsets[source].parent;

    }

   /*
    * Method for doing merge sort Util
    */
    private static Edge[] mergeSortUtil(Graph graph) {
        Edge[] edges = new Edge[graph.numberOfEdge];
        int edge=0;
        for (int v = 0; v < graph.noOfVertex; ++v) {
            AdjacencyListNode pCrawl = graph.array[v].head;
            while (pCrawl!=null)
            {
                edges[edge] = new Edge();
                edges[edge].setSource(v);
                edges[edge].setDest(pCrawl.vertex);
                edges[edge].setWeight(pCrawl.weight);
                pCrawl = pCrawl.next;
                edge++;
            }
        }
        return mergeSort(edges);

    }

   /*
    * Method for doing merge sort
    */
    public static Edge[] mergeSort(Edge[] a) {
        if (a.length <= 1)
            return a;
        int mid = a.length / 2;
        Edge[] left = new Edge[mid];
        Edge[] right = new Edge[a.length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = a[i];
        }
        int k = 0;
        for (int j = mid; j < a.length; j++) {
            right[k++] = a[j];
        }

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);

    }

   /*
    * Method for doing merge sort
    */
    public static Edge[] merge(Edge[] p, Edge[] q) {
        Edge[] result = new Edge[p.length + q.length];
        int k = 0;
        int i = 0;
        int j = 0;
        for (; i < p.length && j < q.length;) {
            if (p[i].weight <= q[j].weight)
                result[k++] = p[i++];
            else
                result[k++] = q[j++];
        }

        while (i < p.length)
            result[k++] = p[i++];
        while (j < q.length)
            result[k++] = q[j++];

        return result;
    }

}

