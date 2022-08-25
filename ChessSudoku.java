package finalproject;

import java.util.*;
import java.io.*;


public class ChessSudoku
{
	/* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
	 * a standard Sudoku puzzle, SIZE is 3 and N is 9. 
	 */
	public int SIZE, N;

	/* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
	 * not yet been revealed are stored as 0. 
	 */
	public int grid[][];

	/* Booleans indicating whether of not one or more of the chess rules should be 
	 * applied to this Sudoku. 
	 */
	public boolean knightRule;
	public boolean kingRule;
	public boolean queenRule;

	
	// Field that stores the same Sudoku puzzle solved in all possible ways
	public HashSet<ChessSudoku> solutions = new HashSet<ChessSudoku>();
	
	
	//methods to solve Sudoku
	
	//check if the number is already in the row
	private boolean rowCheck(int num, int row) {
		for (int i =0; i<this.N; i++) {
			if (grid[row][i]== num) {
				return false;
			}
		}
		return true;
	}
	
	//check if number is already in the column
	private boolean columnCheck(int num, int column) {
		for (int i=0; i<this.N; i++) {
			if (grid[i][column]== num) {
				return false;
			}
		}
		return true;
	}
	
	//check if number is already in the box
	private boolean boxCheck(int row, int column, int num) {
		
		int startR= row -(row % this.SIZE);
		int startC= column-(column %this.SIZE);
		
		for (int i=0; i<SIZE;i++) {
			for (int k=0; k<SIZE;k++) {
				if (grid[i+startR][k+startC]==num) {
					return false;
				}
			}
		}
		return true;
		
	}
	//checks the knight rule
	private boolean knightCheck(int row, int column, int num) {
		
		int indexRange=this.N-1;
		int r1=row+2;
		int r2=row-2;
		
		int c1=column+2;
		int c2=column-2;
		
		if (r1>=0 && r1<=indexRange) {
			if (column+1>=0 && column+1<=indexRange) {
				if (grid[r1][column+1]==num) {
					return false;
				}
			}
			if (column-1>=0 && column-1<=indexRange) {
				if (grid[r1][column-1]==num) {
					return false;
				}
			}
			
		}
		if (r2>=0 && r2<=indexRange) {
			if (column+1>=0 && column+1<=indexRange) {
				if (grid[r2][column+1]==num) {
					return false;
				}
			}
			if (column-1>=0 && column-1<=indexRange) {
				if (grid[r2][column-1]==num) {
					return false;
				}
			}
			
		}
		
		if (c1>=0 && c1<=indexRange) {
			if (row+1>=0 && row+1<=indexRange) {
				if (grid[row+1][c1]==num) {
					return false;
				}
			}
			if (row-1>=0 && row-1<=indexRange) {
				if (grid[row-1][c1]==num) {
					return false;
				}
			}
			
		}
		if (c2>=0 && c2<=indexRange) {
			if (row+1>=0 && row+1<=indexRange) {
				if (grid[row+1][c2]==num) {
					return false;
				}
			}
			if (row-1>=0 && row-1<=indexRange) {
				if (grid[row-1][c2]==num) {
					return false;
				}
			}
			
		}
		return true;
	}
	
	//checks the king rule
	private boolean kingCheck(int row, int column,int num) {
		
		int indexRange=this.N-1;
		int r1=row+1;
		int r2=row-1;
		
		if (r1>=0 && r1<=indexRange) {
			if (column+1>=0 && column+1<=indexRange) {
				if (grid[r1][column+1]==num) {
					return false;
				}
			}
			if (column-1>=0 && column-1<=indexRange) {
				if (grid[r1][column-1]==num) {
					return false;
				}
			}
			
		}
		if (r2>=0 && r2<=indexRange) {
			if (column+1>=0 && column+1<=indexRange) {
				if (grid[r2][column+1]==num) {
					return false;
				}
			}
			if (column-1>=0 && column-1<=indexRange) {
				if (grid[r2][column-1]==num) {
					return false;
				}
			}
			
		}
		return true;
	}
	
	//checks the queen rule
	private boolean queenCheck(int row, int column, int num) {
		
		if (num==this.N) {
			for(int i =row, j=column; i<this.N && j<this.N; i++, j++) {
				if (this.grid[i][j]==this.N) {
					return false;
				}
				
			}
			for(int i =row, j=column; i>=0 && j>=0; i--, j--) {
				if (this.grid[i][j]==this.N) {
					return false;
				}
				
			}
			for(int i =row, j=column; i<this.N && j>=0; i++, j--) {
				if (this.grid[i][j]==this.N) {
					return false;
				}
				
			}
			for(int i =row, j=column; i>0 && j<this.N; i--, j++) {
				if (this.grid[i][j]==this.N) {
					return false;
				}
				
			}
		}
		
		return true;
	}
	
	//checks if a number should go in a cell based on the rules needed to apply
	private boolean validSolution(int row, int column, int num) {
		
		if (this.knightRule==false && this.kingRule==false && this.queenRule==false) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num);
		}
		if (this.knightRule==true && this.kingRule==false && this.queenRule==false) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && knightCheck(row,column,num);
		}
		if (this.knightRule==true && this.kingRule==true && this.queenRule==false) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && knightCheck(row,column,num) && kingCheck(row,column,num);
		}
		if (this.knightRule==false && this.kingRule==true && this.queenRule==false) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && kingCheck(row,column,num);
		}
		if (this.knightRule==false && this.kingRule==false && this.queenRule==true) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && queenCheck(row,column,num);
		}
		if (this.knightRule==false && this.kingRule==true && this.queenRule==true) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && queenCheck(row,column,num) && kingCheck(row,column,num);
		}
		if (this.knightRule==true && this.kingRule==true && this.queenRule==true) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && knightCheck(row,column,num) && queenCheck(row,column,num) && kingCheck(row,column,num);
		}
		if (this.knightRule==true && this.kingRule==false && this.queenRule==true) {
			return rowCheck(num, row) && columnCheck( num, column) && boxCheck( row, column, num) && knightCheck(row,column,num) && queenCheck(row,column,num);
		}
		return false;
		
	}
	
	//check what range of values can be at a specific cell
	private ArrayList<Integer> rangeOfValues(int row, int column) {
		
		ArrayList<Integer> mylist=new ArrayList<Integer>();
		for (int num=1; num<=this.N;num++) {
			if (validSolution(row,column,num)==true) {
				mylist.add(num);
			}
			
		}
		return mylist;
		
	}

	//Back Track method for multiple solutions
	private void multiBackTrack(int grid2[][], int row, int col) {
		this.grid=grid2;
		for (row=0; row <this.N;row++) {
			for (col=0; col<this.N;col++) {
				if (this.grid[row][col]==0) {
					for (int number=1; number<=this.N;number++) {
						if (validSolution(row,col,number)) {
							this.grid[row][col]=number;
							multiBackTrack(grid, row, col);
							this.grid[row][col]=0;
						}
					}
					return;
				}
			}
		}
		
		ChessSudoku s = new ChessSudoku( this.SIZE );
		for (int i=0; i<this.N;i++) {
			for (int j=0; j<this.N;j++) {
				s.grid[i][j]=this.grid[i][j];
			}
		}
		if (this.solutions.contains(s)==false) {
			this.solutions.add(s);
		}
		
	}

	//Backtrack method for one solution that takes into account the size of the sudoku
	private boolean oneBackTrack(int row, int column) {
		
		if (this.SIZE==3){
			boolean empty=false;
			for (int i =0; i<this.N;i++) {
				if (empty==false) {
					for (int j=0; j<this.N;j++) {
						if (empty==false) {
							if(this.grid[i][j]==0) {
								row=i;
								column=j;
								empty=true;
							}
						}
					}
				}
			}
			
			if (empty!=true) {
				return true;
			}
			for (int number=1; number <= this.N; number++) {
				if (validSolution(row,column,number)) {
					this.grid[row][column]=number;
					if (oneBackTrack(row,column)==true) {
						return true;
					}
					else {
						this.grid[row][column]=0;
					}
				}
				
				
			}
			return false;
		
		}
		
		else {
			boolean empty=false;
			for (int i =0; i<this.N;i++) {
				if (empty==false) {
					for (int j=0; j<this.N;j++) {
						if (empty==false) {
							if(this.grid[i][j]==0) {
								row=i;
								column=j;
								empty=true;
							}
						}
					}
				}
			}
			
			if (empty!=true) {
				return true;
			}
			ArrayList<Integer> mylist= rangeOfValues(row,column);
			for (Integer num: mylist) {
					this.grid[row][column]=num;
					
					if (oneBackTrack(row,column)==true) {
						return true;
					}
					else {
						this.grid[row][column]=0;
					}
				
				
			}
			return false;
		}
		
		
	}
	
	
	/* The solve() method should remove all the unknown characters ('x') in the grid
	 * and replace them with the numbers in the correct range that satisfy the constraints
	 * of the Sudoku puzzle. If true is provided as input, the method should find finds ALL 
	 * possible solutions and store them in the field named solutions. */
	public void solve(boolean allSolutions) {
		
		if (allSolutions==false) {
			
			this.oneBackTrack(0,0);
			
		}
		
		if (allSolutions==true) {
	
			this.multiBackTrack(this.grid,0,0);
			
			for(ChessSudoku solution: this.solutions) {
				
				this.grid=solution.grid;
			}
			
		}
		
	}

	

	/*****************************************************************************/
	/* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE METHODS BELOW THIS LINE. */
	/*****************************************************************************/

	/* Default constructor.  This will initialize all positions to the default 0
	 * value.  Use the read() function to load the Sudoku puzzle from a file or
	 * the standard input. */
	public ChessSudoku( int size ) {
		SIZE = size;
		N = size*size;

		grid = new int[N][N];
		for( int i = 0; i < N; i++ ) 
			for( int j = 0; j < N; j++ ) 
				grid[i][j] = 0;
	}


	/* readInteger is a helper function for the reading of the input file.  It reads
	 * words until it finds one that represents an integer. For convenience, it will also
	 * recognize the string "x" as equivalent to "0". */
	static int readInteger( InputStream in ) throws Exception {
		int result = 0;
		boolean success = false;

		while( !success ) {
			String word = readWord( in );

			try {
				result = Integer.parseInt( word );
				success = true;
			} catch( Exception e ) {
				// Convert 'x' words into 0's
				if( word.compareTo("x") == 0 ) {
					result = 0;
					success = true;
				}
				// Ignore all other words that are not integers
			}
		}

		return result;
	}


	/* readWord is a helper function that reads a word separated by white space. */
	static String readWord( InputStream in ) throws Exception {
		StringBuffer result = new StringBuffer();
		int currentChar = in.read();
		String whiteSpace = " \t\r\n";
		// Ignore any leading white space
		while( whiteSpace.indexOf(currentChar) > -1 ) {
			currentChar = in.read();
		}

		// Read all characters until you reach white space
		while( whiteSpace.indexOf(currentChar) == -1 ) {
			result.append( (char) currentChar );
			currentChar = in.read();
		}
		return result.toString();
	}


	/* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
	 * grid is filled in one row at at time, from left to right.  All non-valid
	 * characters are ignored by this function and may be used in the Sudoku file
	 * to increase its legibility. */
	public void read( InputStream in ) throws Exception {
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				grid[i][j] = readInteger( in );
			}
		}
	}


	/* Helper function for the printing of Sudoku puzzle.  This function will print
	 * out text, preceded by enough ' ' characters to make sure that the printint out
	 * takes at least width characters.  */
	void printFixedWidth( String text, int width ) {
		for( int i = 0; i < width - text.length(); i++ )
			System.out.print( " " );
		System.out.print( text );
	}


	/* The print() function outputs the Sudoku grid to the standard output, using
	 * a bit of extra formatting to make the result clearly readable. */
	public void print() {
		// Compute the number of digits necessary to print out each number in the Sudoku puzzle
		int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

		// Create a dashed line to separate the boxes 
		int lineLength = (digits + 1) * N + 2 * SIZE - 3;
		StringBuffer line = new StringBuffer();
		for( int lineInit = 0; lineInit < lineLength; lineInit++ )
			line.append('-');

		// Go through the grid, printing out its values separated by spaces
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				printFixedWidth( String.valueOf( grid[i][j] ), digits );
				// Print the vertical lines between boxes 
				if( (j < N-1) && ((j+1) % SIZE == 0) )
					System.out.print( " |" );
				System.out.print( " " );
			}
			System.out.println();

			// Print the horizontal line between boxes
			if( (i < N-1) && ((i+1) % SIZE == 0) )
				System.out.println( line.toString() );
		}
	}


	/* The main function reads in a Sudoku puzzle from the standard input, 
	 * unless a file name is provided as a run-time argument, in which case the
	 * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
	 * outputs the completed puzzle to the standard output. */
	public static void main( String args[] ) throws Exception {
		InputStream in = new FileInputStream("veryEasy3x3.txt");

		// The first number in all Sudoku files must represent the size of the puzzle.  See
		// the example files for the file format.
		int puzzleSize = readInteger( in );
		if( puzzleSize > 100 || puzzleSize < 1 ) {
			System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
			System.exit(-1);
		}

		ChessSudoku s = new ChessSudoku( puzzleSize );
		
		// You can modify these to add rules to your sudoku
		s.knightRule = false;
		s.kingRule = false;
		s.queenRule = false;
		
		// read the rest of the Sudoku puzzle
		s.read( in );

		System.out.println("Before the solve:");
		s.print();
		System.out.println();

		// Solve the puzzle by finding one solution.
		s.solve(false);

		// Print out the (hopefully completed!) puzzle
		System.out.println("After the solve:");
		s.print();
	}
}

