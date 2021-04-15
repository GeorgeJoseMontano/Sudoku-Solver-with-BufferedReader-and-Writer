import java.io.*;


public class arrayReader{
	 public static boolean isSafe(int[][] board,
             int row, int col,
             int num)
		{
		// Row has the unique (row-clash)
		for (int d = 0; d < board.length; d++)
		{
		
		// Check if the number we are trying to
		// place is already present in
		// that row, return false;
		if (board[row][d] == num) {
		return false;
		}
		}
		
		// Column has the unique numbers (column-clash)
		for (int r = 0; r < board.length; r++)
		{
		
		// Check if the number
		// we are trying to
		// place is already present in
		// that column, return false;
		if (board[r][col] == num)
		{
		return false;
		}
		}
		
		// Corresponding square has
		// unique number (box-clash)
		int sqrt = (int)Math.sqrt(board.length);
		int boxRowStart = row - row % sqrt;
		int boxColStart = col - col % sqrt;
		
		for (int r = boxRowStart;
		r < boxRowStart + sqrt; r++)
		{
		for (int d = boxColStart;
		d < boxColStart + sqrt; d++)
		{
		if (board[r][d] == num)
		{
		return false;
		}
		}
		}
		
		// if there is no clash, it's safe
		return true;
		}
		
		public static boolean solveSudoku(
		int[][] board, int n)
		{
		int row = -1;
		int col = -1;
		boolean isEmpty = true;
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
		if (!isEmpty) {
		break;
		}
		}
		
		// No empty space left
		if (isEmpty)
		{
		return true;
		}
		
		// Else for each-row backtrack
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
		return false;
		}
		
		public static void print(
		int[][] board, int N)
		{
		
		// We got the answer, just print it
		for (int r = 0; r < N; r++)
		{
		for (int d = 0; d < N; d++)
		{
		System.out.print(board[r][d]);
		System.out.print(" ");
		}
		System.out.print("\n");
		
		if ((r + 1) % (int)Math.sqrt(N) == 0)
		{
		System.out.print("");
		}
		}
		}
	
	public static void main(String args[])
	{
		OurListQueue queue = new OurListQueue(); 
		//String input_file = args[0]; if using console to input file
		
		try
		{
			FileReader file_reader = new FileReader("input.txt"); //or input_file variable 
			BufferedReader br = new BufferedReader(file_reader);
			
			String str = "";
			int numofDimensions = 0;
			
			numofDimensions = Integer.parseInt(br.readLine()); //first line value will be the number of lines to check
			System.out.println("There are " + numofDimensions + " dimensions");
			int[][] A = new int [numofDimensions][numofDimensions];

			for(int i=0;i<numofDimensions;i++)
			{
				str = br.readLine();
				for (int j = 0; j < str.replace(" ","").length(); j++)//traversing the string
				{
					char x = str.replace(" ","").charAt(j);
					int a = Character.getNumericValue(x);
					queue.enqueue(a);
				}
			//	System.out.println();
			}		
			br.close();
			for(int r=0;r<numofDimensions;r++){
					for(int c=0;c<numofDimensions;c++){
						A[r][c]=queue.dequeue();
					}
			}
			System.out.println();
			
			if (solveSudoku(A, numofDimensions))
	        {
	            // print solution
	            print(A, numofDimensions);
	        }
	        else {
	            System.out.println("No solution");
	        }
			
		}catch(IOException e)
		{
			System.out.println("An exception occured");
		}
		
		
		
	}
}