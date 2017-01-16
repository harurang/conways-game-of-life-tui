import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ConwaysGame {
	public static void main(String []args) throws IOException {
		// if at least one value is not passed in the command line, display error
		if(args.length != 1)
			System.out.print("Please provide a command line argument.");
		else {
			// command line argument is retreived from element [0,0] of array args and assigned to variable inFile
			File inFile = new File (args[0]);

			Scanner input = null;

			try {
				// streams in text file
				input = new Scanner(inFile);
			} catch (FileNotFoundException e) {
				// print error if file is not found
				System.out.print("\n\n\nThat file does not exist.\n\n\n");
			}

			// assign the first two lines of text file to variables
			int rowMatrix = input.nextInt();
			int columnMatrix = input.nextInt();

			int generation1[][] = new int[rowMatrix][columnMatrix];
			int generation2[][] = new int[rowMatrix][columnMatrix];

			// while there are lines to stream in
			while (input.hasNextInt())
			{
				// loop through each element in 2d array
				for(int row = 0; row < generation1.length; row++) {
					for(int column = 0; column < generation1[row].length; column++) {
						//retain value for each integer streamed in
						int value = input.nextInt();
						//add same integer to each element in each array
						generation1[row][column] = value;
						generation2[row][column] = value;
					}
				}
			}
			// close strem used to read in text file
			input.close();
			// execute program
			execute(generation1, generation2);
		}
	}

  /*
  @requires two 2D arrays that contain contents of file
  @ensures game is executed based on number of supplied generations
  */
	public static void execute(int[][]generation1, int[][]generation2) {
		Scanner input = new Scanner(System.in);

		System.out.print("\nHow many pairs of generations would you like to see?\n");
		// retain input
		int answer = input.nextInt();
		System.out.println();

		// while x is less than answer
		for(int x = 0; x < answer; x++) {
			// display generation array
			displayBoard(generation1);
			// update generation2 array based on neighbors of generation array
			updateBoard(generation1, generation2);
			System.out.println();
			// display generation 2
			displayBoard(generation2);
			// update generation based on generation2 neighbors
			updateBoard(generation2, generation1);
			System.out.println();
		}
	}

  /*
  @requires coordinates of a cell and board that cell belongs to
  @ensures number of live neighbors are returned
  */
  public static int getLiveNeighbors(int x, int y, int[][] generation) {
    int liveCount = 0;
    // if no edge case
    if(x != 0 && y != 0 && x != generation.length-1 && y != generation[0].length-1) {
      // loop through each row surrounding cell
      for(int i = (-1+x); i < 2+x; i++) {
    			//loop through column surrounding cell
           for(int j = (-1+y); j < 2+y; j++) {
    				//if index does not equal coordinate (x,y)
            if(i != x || j != y)
    					if(generation[i][j] == 1)
    						liveCount++;
        		}
      }
    // if edge case
    } else {
      int top = x-1;
      int bottom = x+1;
      int left = y-1;
      int right = y+1;

      // top case
      if (x == 0) {
          top = generation.length-1;
      // bottom case
    } else if (x == generation.length-1) {
          bottom = 0;
      }
      // left case
      if (y == 0) {
          left = generation[0].length-1;
      // right edge case
    } else if (y == generation[0].length-1) {
          right = 0;
      }

      // neighbors to consider based on edge cases
      int[][] neighbors =
      { {top, left}, {top, y}, {top, right}, {x, left}, {x, right}, {bottom, left}, {bottom, y}, {bottom, right} };

      // assess each neighbor stored in neighbors
      for (int i = 0; i < neighbors.length; i++) {
        if (generation[neighbors[i][0]][neighbors[i][1]] == 1)
            liveCount++;
      }
    }
    return liveCount;
  }

  /*
  @requires a 2D array with last state of game and a second 2D array for new state of game
  @ensures new state is saved to generation2[][] based on live neighbors of each cell in generation1[][]
  */
  public static void updateBoard(int[][]generation1, int[][]generation2) {
    int liveNeighbors = 0;

    for(int i = 1; i < generation1.length; i++) {
      for(int j = 1; j < generation1[i].length; j++) {
        liveNeighbors = getLiveNeighbors(i, j, generation1);
        // any live cell that does not have 2 or 3 neighbors dies
        if(generation1[i][j] == 1 && (liveNeighbors != 2 && liveNeighbors != 3))
          generation2[i][j] = 0;
        // any dead cell with three neighbors comes to life
        else if(generation1[i][j] == 0 && (liveNeighbors == 3))
          generation2[i][j] = 1;
      }
    }
  }

	/*
  @requires a 2D array that represents current state of game
  */
	public static void displayBoard(int[][]currentGeneration) {
		// loop through rows
		for(int x = 0; x < currentGeneration.length; x++) {
			// loop through columns
			for(int y = 0; y < currentGeneration[x].length; y++) {
				// print out the each element
				System.out.print(currentGeneration[x][y]);
				// start a new line once one row is completed
				if(y == currentGeneration[x].length-1)
					System.out.print("\n");
			}
		}
	}
}
