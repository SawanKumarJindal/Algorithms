import java.util.*;
/*
 * Created by : Sawan Kumar Jindal and Ravi Kumar Singh
 * PathInMaze.java
 */

/*
 * Class QueueArray for create queue
 */
class QueueArray<cell> {
    private class Node {
        public cell data;
        public Node next;
        public Node(cell data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;
    // enqueue in the queue
    public void enqueue(cell item) {
        Node newNode = new Node(item, null);
        if (isEmpty()) {head = newNode;} else {tail.next = newNode;}
        tail = newNode;
    }
    // dequeue from the queue
    public cell dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        cell item = head.data;
        if (tail == head) {
            tail = null;
        }
        head = head.next;
        return item;
    }
    // getting from front of the queue
    public cell peek() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.data;
    }
    // checking if the queue is empty
    public boolean isEmpty() {
        return head == null;
    }
    // if queue is empty
    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.next) {
            count++;
        }
        return count;
    }
}

/*
 * class cell which acts as every cell in 2D maze
 */
class cell{

    int value; // the value 0,1,2,3
    String cellChar=" "; // if its Source, Destination or wall or free area
    int mark = 0; // marking the every cell
    int row;
    int column;
    boolean visitedOrNot = false; // if the cell is visited or not

    public String toString(){
        return "r :" + row + "," + " c :" + column + " m :"+ mark + "cc" + cellChar;
    }
}

public class PathInMaze {

    static QueueArray<cell> queueToCheck = new QueueArray<cell>(); // queue
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        int numOfRows = sc.nextInt();
        int numOfColumns = sc.nextInt();
        cell[][] shortestPath = new cell[numOfRows][numOfColumns]; // creating 2D maze
        // Initilizing 2D array
        for( int i = 0 ; i < numOfRows ; i++){
            for ( int j = 0; j < numOfColumns ; j++){
                cell c = new cell();
                c.row = i;
                c.column = j;
                c.value = sc.nextInt();
                if(c.value == 1){
                    c.cellChar = "O";
                }
                if(c.value ==2){
                    c.cellChar = "S";
                }
                if(c.value==3){
                    c.cellChar = "D";
                }
                if(c.value ==0){
                    c.cellChar ="F";
                }
                shortestPath[i][j] = c;
            }
        }
        // finding the shortest path in 2D maze
        shortestPathInMaze( shortestPath, numOfRows, numOfColumns);
    }

    // finding the neighbours of the current cell
    private static void findingNeighbors(cell[][] shortestPath, int row, int column) {
        if( column >=1 && column <= shortestPath[row].length-1) {
            cell left = shortestPath[row][column - 1];
            if( !left.cellChar.equalsIgnoreCase("O")  && left.visitedOrNot == false){ // if the cell is not already visited
                left.visitedOrNot = true; // making visited
                left.mark = shortestPath[row][column].mark +1; // incrementing mark
                queueToCheck.enqueue(left); // adding to queue
            }
        }
        if( column >=0 && column <= shortestPath[row].length-2) {
            cell right = shortestPath[row][column + 1];
            if( !right.cellChar.equalsIgnoreCase("O") && right.visitedOrNot == false){// if the cell is not already visited
                right.visitedOrNot = true;// making visited
                right.mark = shortestPath[row][column].mark + 1;// incrementing mark
                queueToCheck.enqueue(right);// adding to queue
            }
        }
        if( row >=1 && row <= shortestPath.length-1) {
            cell top = shortestPath[row - 1][column];
            if( !top.cellChar.equalsIgnoreCase("O")  && top.visitedOrNot == false){// if the cell is not already visited
                top.visitedOrNot = true;// making visited
                top.mark = shortestPath[row][column].mark + 1;// incrementing mark
                queueToCheck.enqueue(top);// adding to queue
            }
        }
        if( row >=0 && row <= shortestPath.length-2) {
            cell bottom = shortestPath[row + 1][column];
            if( !bottom.cellChar.equalsIgnoreCase("O") && bottom.visitedOrNot == false){// if the cell is not already visited
                bottom.visitedOrNot = true;// making visited
                bottom.mark = shortestPath[row][column].mark + 1;// incrementing mark
                queueToCheck.enqueue(bottom);// adding to queue
            }
        }
    }
    //finding the shortest path between source and destination
    private static void shortestPathInMaze(cell[][] shortestPath, int numOfRows, int numOfColumns) {
        for( int i = 0 ;  i < numOfRows ; i++){
            for( int j = 0; j < numOfColumns ; j++){
                if( shortestPath[i][j].cellChar.equalsIgnoreCase("S")){
                    BFS(shortestPath, i , j);
                }
            }
        }
    }
    // doing Breadth first search on source
    private static void BFS(cell[][] shortestPath, int i, int j) {
        int maxMark=0;
        shortestPath[i][j].visitedOrNot = true; // making to visited
        shortestPath[i][j].mark =0;
        cell source = new cell();
        queueToCheck.enqueue(shortestPath[i][j]); // adding the source to queue
        while (!queueToCheck.isEmpty() && !source.cellChar.equalsIgnoreCase("D") ){ // while if queue is empty and that cell is not the Destination
            source = queueToCheck.peek(); // taking the first cell from queue
           // System.out.println(source);
            queueToCheck.dequeue(); // removing from the queue
            findingNeighbors(shortestPath, source.row, source.column); // finding the neighbours
        }
        // finding the max mark
        for( int k = 0 ; k < shortestPath.length ; k++){
            for ( int l = 0; l < shortestPath[k].length ; l++){
                if(shortestPath[k][l].cellChar.equalsIgnoreCase("D")){
                    // Destination has reached
                    maxMark = shortestPath[k][l].mark;
                    break;
                }
            }
        }
        // Destination has not reached
        if( maxMark==0){
            maxMark =-1;
        }
        // printing the shortest length
        System.out.println(maxMark);
    }
}
