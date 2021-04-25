import java.io.*;

//test if change
public class arrayReader
{	 
	static OurListQueue queue = new OurListQueue();
	
	public static void main(String args[])
	{
		//read the sudoku puzzle from input text file
		//return the number of dimensions 
		//as it will also be used to solve the puzzle
		int numofDimensions = readSudoku();
		
		//convert the read sudoku puzzle into an array
		int[][] A = new int [numofDimensions][numofDimensions];		
		for(int r = 0; r < numofDimensions;r++)
		{
			for(int c = 0; c < numofDimensions; c++)
			{
				A[r][c] = queue.dequeue();
			}
		}
		
		//spacing
		System.out.println();
		
		//method to solve for sudoku and print solution
		if (solveSudoku(A, numofDimensions))
	       {
	           //if solved, print solution
	           print(A, numofDimensions);
	       }
	    else 
		{
	        System.out.println("No solution");
	    }
		
		//create a copy of the solved sudoku puzzle into an output text file
		writeSudoku(A, numofDimensions);
	}
	
	static int readSudoku()
	{
		try
		{
			//String input_file = args[0]; if using console to input file
			FileReader file_reader = new FileReader("input.txt"); //or input_file variable
			BufferedReader br = new BufferedReader(file_reader);	
			String str = "";
			int numofDimensions = 0;
				
			//first line value will be the number of lines to check
			numofDimensions = Integer.parseInt(br.readLine()); 
			System.out.println("There are " + numofDimensions + " dimensions");
		//read the sudoku puzzle 
			for(int i = 0; i < numofDimensions; i++)
			{
				//read lines as string first
				str = br.readLine();
				//traversing the string
				for (int j = 0; j < str.replace(" ","").length(); j++)
				{
					char x = str.replace(" ","").charAt(j);
					int a = Character.getNumericValue(x);
					queue.enqueue(a);
				}
			}		
			br.close();
		return numofDimensions;
		
		}catch(IOException e)
		{
			System.out.println("An exception occured");
		}
		
		return 0;
		
	}
	
	static void writeSudoku(int[][] A, int numofDimensions)
	{
		try
		{
			FileWriter file_writer = new FileWriter("output.txt");
			BufferedWriter bw = new BufferedWriter(file_writer);
			//Write solved Sudoku puzzle onto output text file
			for(int r=0;r<numofDimensions;r++)
			{
				for(int c=0;c<numofDimensions;c++)
				{
					bw.write(String.valueOf(A[r][c]) + " ");
				}
				bw.newLine();
			}
			bw.close();
			
		}catch(IOException e)
		{
			System.out.println("An exception occured");
		}
	}
	public static boolean solveSudoku(int[][] board, int n)
	{
		int row = -1;
		int col = -1;
		boolean isEmpty = true;
		
		//traverse the array/puzzle for 0
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (board[i][j] == 0)
				{
					row = i;
					col = j;
					// We still have some remaining
					// missing values in Sudoku
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) 
			{
				break;
			}
		}
		
		// No empty space left
		if (isEmpty)
		{
			return true;
		}
			
		// Else for each-row backtrack
		else
		{
			for (int num = 1; num <= n; num++)
			{
				if (isSafe(board, row, col, num))
				{
					board[row][col] = num;
					if (solveSudoku(board, n))
					{
						// print(board, n);
						return true;
					}
					else
					{
						// replace it
						board[row][col] = 0;
					}
				}
			}
		}
		return false;
	}
	
	//method to summarize check of row, col and square
	static boolean isSafe(int[][] board,int row, int col, int num)
	{
		if(rowSafe(board, row, col, num) && colSafe(board, row, col, num) && squareSafe(board, row, col, num))
		{
			return true;
		}
		return false;
	}
	
	// method to check if number to be placed already exist in row
	static boolean rowSafe(int[][] board,int row, int col, int num)
	{
		for (int d = 0; d < board.length; d++)
		{			
			if (board[row][d] == num) 
			{
				return false;
			}
		}
		return true;
	}	
	
	// method to check if number to be placed already exist in column
	static boolean colSafe(int[][] board,int row, int col, int num)
	{
		for (int r = 0; r < board.length; r++)
		{	
			if (board[r][col] == num)
			{
				return false;
			}
		}
		return true;
	}
	// method to check if number to be placed already exist in square of 3x3 if 9x9 sudoku or 3x2 if 6x6 sudoku
	static boolean squareSafe(int[][] board,int row, int col, int num)
	{
		//make sure col and row do not exceed board length
		//and go out of bounds
		//column are grouped by 3
		col = (col/3)*3;
		//row same as above but varies from (row/2)* 2 for 6x6 or (row/3)*3 for 9x9
		row = (row/(board.length/3)*(board.length/3));
		
		//for loop to adjust for 9x9 and 6x6
		//rows are variable to 3x3 and 3x2
		for(int r = 0; r < (board.length / 3); r++)
		{
			//but columns are constant 3
			for(int c = 0; c < 3; c++)
			{
				if(board[row + r][col + c] == num)
				{
					return false ;
				}
			}
		}
		return true;
	}
		
	//method to print the array of solution
	public static void print(int[][] board, int N)
	{
		// We got the answer, just print it
		for (int r = 0; r < N; r++)
		{
			for (int d = 0; d < N; d++)
			{
				System.out.print(board[r][d]);
				System.out.print(" ");
			}
			System.out.println();
			
			if ((r + 1) % (int)Math.sqrt(N) == 0)
			{
				System.out.print("");
			}
		}
	}
}