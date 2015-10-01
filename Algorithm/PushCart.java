import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Ravikumarsingh and Sawan Jindal on 4/28/15.
 */

// Creating Arraylist to use in removing and selecting the intervals
class MyArrayList {

    private cell[] myStore;
    private int actSize = 0;
    int size =0;

    // construtor
    public MyArrayList(int size) {
        myStore = new cell[size];
        this.size =size;
    }
    // get
    public cell get(int index) {
        if (index < actSize) {
            return myStore[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    // to add the elements
    public void add(cell obj) {
        if (myStore.length - actSize <= size/2) {
            increaseListSize();
        }
        myStore[actSize++] = obj;
    }
    // to remove the element
    public cell remove(int index) {
        if (index < actSize) {
            cell obj = myStore[index];
            myStore[index] = null;
            int tmp = index;
            while (tmp < actSize) {
                myStore[tmp] = myStore[tmp + 1];
                myStore[tmp + 1] = null;
                tmp++;
            }
            actSize--;
            return obj;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }

    }
    // for increasing the size of Arraylist
    private void increaseListSize(){
        myStore = Arrays.copyOf(myStore, myStore.length * 2);
    }

    // checking the size of Arraylist
    public int size() {
        return actSize;
    }
}

// Class cell
class cell{
    int row;
    int column;
    int altitude;
    int energyToReach=0; // energy to Reach
    char cartDirection; // which direction you are coming
    boolean flag;
    cell(int row, int column, int altitude, char cartDirection){
        this.row = row;
        this.column = column;
        this.altitude = altitude;
        this.cartDirection = cartDirection;
        this.flag = false;
    }
    public String toString(){
        return "r :" + row + "," + " c :" + column + " alti :"+ altitude + " direction " + cartDirection;
    }
}

class Cart{
    char[] direction  = new char[]{'U', 'P', 'R','L'};
}

public class PushCart {
    static Cart cart = new Cart();
    public static String minDistance(MyArrayList[][] shortestPath)
    {
        // Initialize min value
        int min = Integer.MAX_VALUE;
        String min_index = " ";

        for (int i = 0; i < shortestPath.length; i++) {
            for (int j = 0; j < shortestPath[0].length; j++) {
                for( int k =0 ; k < 4 ;i++) {
                    if(shortestPath[i][j].get(k).flag == false && shortestPath[i][j].get(k).energyToReach <= min){
                        min = shortestPath[i][j].get(k).energyToReach;
                        min_index = i + " " + j + " " + k;
                    }
                }
            }
        }
        return min_index;
    }

    public static void printing(MyArrayList[][] shortestPath){
        for (int i = 0; i < shortestPath.length; i++) {
            System.out.println("/n");
            for (int j = 0; j < shortestPath[i].length; j++) {
                System.out.print(" " + shortestPath[i][j].get(0) + " " + shortestPath[i][j].get(1) + " " + shortestPath[i][j].get(2) +" " + shortestPath[i][j].get(3));
            }
        }
    }

    // sloving by using dijkstra
    public static void dijkstra(MyArrayList[][] shortestPath, MyArrayList c){
        int totalNumberOfVertex = shortestPath.length * shortestPath[0].length;
        int[] dist = new int[totalNumberOfVertex];
        boolean[] shortestPathTree = new boolean[totalNumberOfVertex];
		
        for (int i = 0; i < shortestPath.length; i++) {
            for (int j = 0; j < shortestPath[0].length; j++) {
                for( int k =0 ; k < 4 ;i++) {
                    shortestPath[i][j].get(k).energyToReach = Integer.MAX_VALUE;
                }
            }
        }

        for( int i= 0; i < c.size() ; i++) {
            c.get(i).energyToReach = 0;
        }

        for (int i = 0; i < shortestPath.length; i++) {
            for (int j = 0; j < shortestPath[0].length; j++) {
                for (int k = 0; k < 4; i++) {
                    String u = minDistance(shortestPath);
                    String[] various = u.split(" ");
                    int toChange = Integer.parseInt(various[1]);
                    shortestPath[i][j].get(Integer.parseInt(various[1])).flag = true;
                    for (int l = 0; l < shortestPath.length; l++) {
                        for (int m = 0; m < shortestPath[0].length; m++) {
                            for (int n = 0; n < 4; n++) {
                                if (!shortestPath[l][m].get(k).flag && shortestPath[i][j].get(toChange).energyToReach!= Integer.MAX_VALUE ){
                                    try {
                                        shortestPath[i][j].get(toChange).energyToReach = shortestPath[i][j].get(k).energyToReach + shortestPath[i][j].get(toChange).energyToReach;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // printing the last cell distance
        System.out.println(shortestPath[shortestPath.length][shortestPath[0].length].get(0).energyToReach-1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfRows = sc.nextInt();
        int numOfColumns = sc.nextInt();
        MyArrayList[][] shortestPath = new MyArrayList[numOfRows][numOfColumns]; // creating 2D maze
        // Initilizing 2D array
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                MyArrayList arrayListOfCell = new MyArrayList(4);
                int altitude = sc.nextInt();
                arrayListOfCell.add(new cell(i, j, altitude, 'U'));
                arrayListOfCell.add(new cell(i, j, altitude, 'D'));
                arrayListOfCell.add(new cell(i, j, altitude, 'R'));
                arrayListOfCell.add(new cell(i, j, altitude, 'L'));
                shortestPath[i][j] = arrayListOfCell;
            }
        }
        // calling dijkstra
        dijkstra(shortestPath, shortestPath[0][0]);


    }
}
