package solution;
import csis4463.*;

public class solution {
	public static void main(String[] args) {
		//Hash map or 2d array for storing values of state averages
		double[][] expanded = new double[6][6];
		double[][] generated = new double[6][6];
		double[][] memory = new double[6][6];
		
		for(int i = 2; i <= 12; i+=2) { //loop through the required sizes 2,4,6,8,10,12
			for (int j = 0; j < 100; j++) { //loop through all 100 puzzles for each size
				SlidingTilePuzzle puzzle = new SlidingTilePuzzle(3, 3, i); //puzzle holder

				for (int k = 0; k <= 5; k++) { //loop through for all solutions
					double[] puzzleVals;
					switch (k) {
						case 0:
							puzzleVals = ucs(puzzle); //get the solution data to average
							expanded[(i/2)-1][k] += puzzleVals[0]; //save the expanded data (pre averaging)
							generated[(i/2)-1][k] += puzzleVals[1]; //save the generated data (pre averaging)
							memory[(i/2)-1][k] += puzzleVals[2]; //save the memory data (pre averaging)
							break;
						case 1:
							puzzleVals = astarmisplaced(puzzle); //get the solution data to average
							expanded[(i/2)-1][k] += puzzleVals[0]; //save the expanded data (pre averaging)
							generated[(i/2)-1][k] += puzzleVals[1]; //save the generated data (pre averaging)
							memory[(i/2)-1][k] += puzzleVals[2]; //save the memory data (pre averaging)
							break;
						case 2:
							puzzleVals = astarmanhattan(puzzle); //get the solution data to average
							expanded[(i/2)-1][k] += puzzleVals[0]; //save the expanded data (pre averaging)
							generated[(i/2)-1][k] += puzzleVals[1]; //save the generated data (pre averaging)
							memory[(i/2)-1][k] += puzzleVals[2]; //save the memory data (pre averaging)
							break;
						case 3:
							puzzleVals = iterative(puzzle); //get the solution data to average
							expanded[(i/2)-1][k] += puzzleVals[0]; //save the expanded data (pre averaging)
							generated[(i/2)-1][k] += puzzleVals[1]; //save the generated data (pre averaging)
							memory[(i/2)-1][k] += puzzleVals[2]; //save the memory data (pre averaging)
							break;
						case 4:
							puzzleVals = idastarmisplaced(puzzle); //get the solution data to average
							expanded[(i/2)-1][k] += puzzleVals[0]; //save the expanded data (pre averaging)
							generated[(i/2)-1][k] += puzzleVals[1]; //save the generated data (pre averaging)
							memory[(i/2)-1][k] += puzzleVals[2]; //save the memory data (pre averaging)
							break;
						case 5:
							puzzleVals = idastarmanhattan(puzzle); //get the solution data to average
							expanded[(i/2)-1][k] += puzzleVals[0]; //save the expanded data (pre averaging)
							generated[(i/2)-1][k] += puzzleVals[1]; //save the generated data (pre averaging)
							memory[(i/2)-1][k] += puzzleVals[2]; //save the memory data (pre averaging)
							break;
					}
				}
			}
			
			for (int j = 0; j < 6; j++) { //loop through the whole row we just worked on and average everything
				expanded[(i/2)-1][j] /= 100;
				generated[(i/2)-1][j] /= 100;
				memory[(i/2)-1][j] /= 100;
			}
		}
		
		//Print out the tables and use printing methods for formatting
		System.out.println("Average Number of States Expanded");
		printRowHeader();
		for(int i = 2; i <= 12; i+=2) {
			printColumnHeader(i);
            printRow(expanded[(i/2)-1]);
		}
		System.out.println();
		
		System.out.println("Average Number of States Generated");
		printRowHeader();
		for(int i = 2; i <= 12; i+=2) {
			printColumnHeader(i);
            printRow(generated[(i/2)-1]);
		}
		System.out.println();
		
		System.out.println("Max States in Memory");
		printRowHeader();
		for(int i = 2; i <= 12; i+=2) {
			printColumnHeader(i);
            printRow(memory[(i/2)-1]);
		}
		System.out.println();
	}
	
	/* Methods to get the solutions and return the data we need for averages */
	private static double[] ucs(SlidingTilePuzzle thePuzzle) {
		PuzzleSolution solution = SlidingTilePuzzleSolver.uniformCostSearch(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] astarmisplaced(SlidingTilePuzzle thePuzzle) {
		PuzzleSolution solution = SlidingTilePuzzleSolver.AStarSearchMisplacedTiles(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] astarmanhattan(SlidingTilePuzzle thePuzzle) {
		PuzzleSolution solution = SlidingTilePuzzleSolver.AStarSearchManhattanDistance(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] idastarmanhattan(SlidingTilePuzzle thePuzzle) {
		PuzzleSolution solution = SlidingTilePuzzleSolver.idaStarManhattanDistance(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] idastarmisplaced(SlidingTilePuzzle thePuzzle) {
		PuzzleSolution solution = SlidingTilePuzzleSolver.idaStarMisplacedTiles(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}

	private static double[] iterative(SlidingTilePuzzle thePuzzle) {
		PuzzleSolution solution = SlidingTilePuzzleSolver.iterativeDeepening(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	
	/* Helper methods to print the tables (2d arrays) */
	public static void printRowHeader() {
		System.out.print("L\tUCS\tA*1\tA*2\tID\tIDA*1\tIDA*2");
        System.out.println();
    }
	public static void printColumnHeader(int len) {
        System.out.print(len);
        System.out.print("\t");
    }
	public static void printRow(double[] row) {
        for (double i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }
}
