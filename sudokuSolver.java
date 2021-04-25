import java.io.*;

//test if change
public class sudokuSolver
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
		boolean isFilled = true;
		
		//traverse the array/puzzle for 0 or empty slots
		for (int i = 0; i < n && isFilled; i++)
		{
			for (int j = 0; j < n && isFilled; j++)
			{
				//if there is empty slot it will flag false and 
				//end loop at current position
				if (board[i][j] == 0)
				{
					row = i;
					col = j;	
					isFilled = false;
				}
			}
		}
		
		// if no 0 or empty slots then end method and return true
		if (isFilled)
		{
			return true;
		}
			
		// else for each-row backtrack
		else
		{
			for (int num = 1; num <= n; num++)
			{
				if (isSafe(board, row, col, num))
				{
					//fill slot with num
					board[row][col] = num;
					//if all the subsequent slots have been successfully
					//filled with a num with no clashes found then it
					//will successfully return true
					if (solveSudoku(board, n))
					{
						return true;
					}
					//otherwise slot will be returned to empty and will backtrack
					//and will be ready to receive a new num
					else
					{
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
		//make sure col and row do not exceed respective box
		//and go out of bounds
		//column are grouped by 3 and does not exceed 3
		int boxCol = col - col % 3;
		//row same as above but varies from row-row%2 for 6x6 or row-row%3 for 9x9
		int boxRow = row - row % (board.length/3);
		
		//for loop to traverse box
		//rows are variable to 3x3 and 3x2
		for(int r = 0; r < (board.length / 3); r++)
		{
			//but columns are constant 3
			for(int c = 0; c < 3; c++)
			{
				//if number exist then return false
				if(board[boxRow + r][boxCol + c] == num)
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
		}
	}
}