import java.util.Arrays;

/**
 * @author  Ruha Javed <a href="mailto:ruha.javed1@ucalgary.ca">ruha.javed1@ucalgary.ca</a>
 * @version 1.2
 * @since   1.0
 */

 /**
  * Class which implements 8 queens programming challenge.
  */
 public class EightQueens implements Cloneable
 {
    private char chessBoard[][] = new char[8][8];
    private static int numOfQueens = 0;

    /**
     * Class constructor; fills board with char 'o'.
     * Note, could use emptySquare function here. 
     * Most likely, this is more efficient though.
     */
    public EightQueens()
    {
        for(int i = 0; i < chessBoard.length; i++)
        {
            Arrays.fill(chessBoard[i], 'o');
        }
    }

    /**
     * Utility function; prints board.
     */
    public void printChessBoard()
    {
        for(int i = 0; i < chessBoard.length; i++)
        {
            for(int j = 0; j < chessBoard[i].length; j++)
            {
                System.out.print(chessBoard[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Gets current state of chess board. 
     * @return returns the chess board.
     */
    public char[][] getBoard()
    {
        return chessBoard;
    }

    /**
     * Setter for numOfQueens. 
     */
    private void setNumOfQueens(int numOfQueens)
    {
        this.numOfQueens = numOfQueens;
    }

    /**
     * Getter for numOfQueens. 
     */
    private int getNumOfQueens()
    {
        return numOfQueens;
    }

    /**
     * Sets the value at chessBoard[row][column] as 'Q'.
     * Note that this function expects zero-indexing.
     * @param row starts as zero.
     * @param column starts as zero.
     */
    public void setQueen(int row, int column)
    {
        chessBoard[row][column] = 'Q';
    }

    /**
     * Sets a given chess position as empty.
     * @param row    row to set empty.
     * @param column column to set empty.
     */
    public void emptySquare(int row, int column)
    {
        chessBoard[row][column] = 'o';
    }

    /**
     * clones the EightQueens instance obj using
     * the Cloneable interface.
     */
    public Object clone() throws CloneNotSupportedException
    {
        EightQueens copy = (EightQueens)super.clone();  // create clone - board is still a shallow copy

        char[][] originalBoard = this.getBoard();       // create a new array to store same board contents of this
        char[][] copyBoard = new char[8][8];
        for(int i = 0; i < originalBoard.length; i++)
        {
            for(int j = 0; j < originalBoard[i].length; j++)
            {
                copyBoard[i][j] = originalBoard[i][j];
            }
        }
        copy.chessBoard = copyBoard;                    // set clone's board reference to copy
        return copy;
    }

    /**
     * checks if placing a qeen at the given row
     * and column is valid - i.e. not threatened
     * by any other queens previously placed on board.
     * @param row row wanted to place queen at.
     * @param column column wanted to place queen at.
     * @return returns false if queen will be threatened at
     * given position, otherwise true.
     */
    public boolean isValid(int row, int column)
    {
        // check for any queens vertically
        for(int j = 0; j < chessBoard[row].length; j++)
        {
            if (chessBoard[row][j] == 'Q')
            {
                return false;
            }
        }

        // check for any queens horizontally
        for(int i = 0; i < chessBoard.length; i++)
        {
            if (chessBoard[i][column] == 'Q')
            {
                return false;
            }
        }    

        // check for any queens in upper LHS to lower RHS diagonal
        for(int i = 0; i < chessBoard.length; i++)
        {
            if ((row - i) >= 0 && (column - i) >= 0 && chessBoard[row - i][column - i] == 'Q')
            {
                return false;
            }
            if ((row + i) < chessBoard.length && (column + i) < chessBoard.length && chessBoard[row + i][column + i] == 'Q')
            {
                return false;
            }
        } 

        // check for any queens in lower LHS to upper RHS diagonal
        for(int i = 0; i < chessBoard.length; i++)
        {
            if ((row - i) >= 0 && (column + i) < chessBoard.length && chessBoard[row - i][column + i] == 'Q')
            {
                return false;
            }
            if ((row + i) < chessBoard.length && (column - i) >= 0 && chessBoard[row + i][column - i] == 'Q')
            {
                return false;
            }
        } 
        return true; // return true if no conflicts found
    } 

    /**
     * This function places a maximum of 8 queens on a chess board, 
     * without exposing each to attacks.
     * @param queensRemaining # of queens to place on board without vulnerability
     * @return boolean to indicate success or failure of placement
     */
    public boolean setQueens(int queensRemaining)
    {
        if ((queensRemaining < 0) || (queensRemaining > 8)) // if given an out of range #
        {
            return false;
        }
        else if (queensRemaining == 0) // base case - no more to place
        {
            return true;
        }

        //otherwise - recursion!
        for(int i = 0; i < chessBoard.length; i++)
        {
            for(int j = 0; j < chessBoard[i].length; j++)   // go through each place on the board, trying to place
            {
                if(isValid(i, j))                           // check if a given space is safe to place at - i.e a queen is not threatened there
                {
                    this.setQueen(i, j);                    // if so, tentatively place queen there
                    int numOfQueens = this.getNumOfQueens() + 1;    // increment num of queens placed
                    this.setNumOfQueens(numOfQueens);

                    if(setQueens(queensRemaining-1))        // do a recursive call and see if this choice results in a solution
                    {
                        return true;                        // if this choice results in a success, return true
                    }
                    else
                    {
                        this.emptySquare(i, j);             // unsuccessful choice, backtrack and remove queen previously placed
                        numOfQueens = this.getNumOfQueens() - 1; // decrement num of queens placed
                        this.setNumOfQueens(numOfQueens);
                    }
                }
            }
        }
        return false;                                       // return false - no solution found
    }

    public static void main(String args[]) throws CloneNotSupportedException
    {

    // uncomment up to next test to test actual algorithm by placing different queens
    // at different times
        //  boolean result;

        //  EightQueens testObj1 = new EightQueens();
        // testObj1.setQueen(0, 0);
        // testObj1.setQueen(1, 4);
        // testObj1.setQueen(7,7);
        // testObj1.setQueen(6,3);
        // result = testObj1.setQueens(4);
        // System.out.println(result);

        //  testObj1 = new EightQueens();
        //  result = testObj1.setQueens(9);
        //  System.out.println(result);

        // testObj1 = new EightQueens();
        // testObj1.setQueen(6, 6);
        // testObj1.setQueen(1, 5);
        // testObj1.setQueen(4,7);
        // result = testObj1.setQueens(5);
        // System.out.println(result);

        // testObj1 = new EightQueens();
        // testObj1.setQueen(0, 6);
        // testObj1.setQueen(4,4);
        // result = testObj1.setQueens(6);
        // System.out.println(result);

        // testObj1 = new EightQueens();
        // testObj1.setQueen(1, 2);
        // result = testObj1.setQueens(7);
        // System.out.println(result);

        // testObj1 = new EightQueens();
        // result = testObj1.setQueens(8);
        // System.out.println(result);

    // uncomment below to test deep copying
        // EightQueens originalObj = new EightQueens();
        // originalObj.setQueen(7, 2);

        // EightQueens clonedObj = (EightQueens) originalObj.clone();
        // clonedObj.setQueen(0, 0);
        // clonedObj.setQueen(7, 7);
        // clonedObj.emptySquare(7, 2);

        // originalObj.printChessBoard();
        // System.out.println();
        // clonedObj.printChessBoard();
    }
 }