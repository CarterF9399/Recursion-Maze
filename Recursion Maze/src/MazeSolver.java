import brock.*;

/**
 * The MazeSolver class is a class that uses recursion to solve any inputted maze that is in a
 * .txt file. It does this by inserting the whole file into a 2D array and traversing through it
 * from the staring point "A" to the end point "B". In the maze, the walls are represented by "*"
 * and any path that was tried and a dead end is shown as ".". This program also shows the
 * path that the program took to complete the maze, as well as the final number of steps to get
 * to the end.
 *
 * @author      Carter Fairchild
 * @course      COSC 1P03
 * @assignment  #4
 * @id          8446957
 * @version     1.0
 * @since       March 31st, 2026
 */
public class MazeSolver {
    char[][] array; // The array to be printed out and modified throughout the program

    /**
     * This constructor is used to primarily call individual methods to read from
     * the given .txt file that contains the maze, and place it in the 2D array.
     * Then, it solves the maze using the "solve" method, and prints out the result
     * into the console. This constructor also reads the first four lines of the
     * input file before calling the other methods, since the first two lines for
     * the maze are used to show the row and column count of the maze, while the
     * next two are used to show the starting position in the maze.
     */
    public MazeSolver() {
        ASCIIDataFile file = new ASCIIDataFile("maze3.txt");
        file.setDelay(0);
        int rowCount = file.readInt();
        int colCount = file.readInt();
        int startRow = file.readInt();
        int startCol = file.readInt();
        readFile(file, rowCount, colCount);
        solve(startRow, startCol, 0);
        System.out.println();   //a few extra lines for formatting to clean up the output
        System.out.println("Final maze result:");
        System.out.println();
        print(array);
    }

    /**
     * The print method simply loops through the entire 2D array and prints out
     * the entire maze, no matter which state it is currently in.
     *
     * @param data  The array to be printed out.
     */
    private void print(char[][] data){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * The insert method is used in the "solve" method whenever there needs to
     * be an arrow or a period inserted into the maze, while ensuring that the
     * start and finish points are not overwritten.
     *
     * @param row       The row index of the maze to be inserted into.
     * @param column    The column index of the maze to be inserted into.
     * @param d         The direction of the arrow to be inserted, 'none' for a period
     */
    private void insert(int row, int column, Direction d){
        char left = 0x2190 ; // ←
        char right = 0x2192 ; // →
        char up = 0x2191 ; // ↑
        char down = 0x2193 ; // ↓

        if(array[row][column] == 'A' || array[row][column] == 'B'){
            return;
        } else if (d == Direction.Left){
            array[row][column] = left;
        } else if (d == Direction.Right){
            array[row][column] = right;
        } else if (d == Direction.Up){
            array[row][column] = up;
        } else if (d == Direction.Down){
            array[row][column] = down;
        } else {        // d == Direction.None
            array[row][column] = '.';
        }
    }

    /**
     * The readFile method very simply creates a 2D array using the dimensions
     * received from the first two lines of the .txt file, which were read in the
     * constructor. Then, it fills the array with the data from the .txt file to
     * represent the maze entirely.
     *
     * @param file      The .txt file to be read and put into the array.
     * @param rows      The number of rows, or height, of the array.
     * @param columns   The number of columns, or width, of the array.
     */
    private void readFile(ASCIIDataFile file, int rows, int columns){
        array = new char[rows][columns];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = file.readChar();
            }
        }
        file.close();
    }

    /**
     * The solve method is used to fully solve the maze and provide the solved
     * maze, and the final result of the maze which is fully replaced with just
     * periods and no arrows. This is done through a series of recursive calls and
     * checks to ensure not only that the end is reached, but to make sure that
     * there are no walls passed through, and there is a clear path taken for
     * visual representation of the maze and how it is solved. Finally, before printing
     * the maze, it tells the user how many steps it took to reach the end.
     *
     * @param row       The current row index being evaluated, initially the starting position of 'A'.
     * @param col       The current column index being evaluated, initially the starting position of 'A'.
     * @param iteration Keeps track of how many steps have been performed.
     */
    private void solve(int row, int col, int iteration){
        if(array[row][col] == 'B'){
            System.out.println("Found B in " + iteration + " steps!");
            System.out.println();   //extra line for formatting, personal preference
            print(array);
        } else if(array[row][col] == '*' || array[row][col] == '.') {return;}
        else if(array[row][col] == ' ' || array[row][col] == 'A'){
            insert(row, col, Direction.Up);
            solve(row-1, col, iteration+1);
            insert(row, col, Direction.Right);
            solve(row, col+1, iteration+1);
            insert(row, col, Direction.Down);
            solve(row+1, col, iteration+1);
            insert(row, col, Direction.Left);
            solve(row, col-1, iteration+1);
            insert(row, col, Direction.None);
        } else {return;}
    }

    static void main(String[] args) {
        MazeSolver m = new MazeSolver();
    }
}
