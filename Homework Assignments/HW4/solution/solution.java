package solution;
import java.util.HashMap;
import java.util.ArrayList;
import csis4463.*;

public class solution {
	public static void main(String[] args) {
		//Hash map or 2d array for storing values of state averages
		double[][] expanded = new double[6][6];
		double[][] generated = new double[6][6];
		double[][] memory = new double[6][6];
		
		for(int i = 2; i <= 12; i+=2) { //loop through the required sizes 2,4,6,8,10,12
			
			for (int j = 0; j < 100; j++) {
				SlidingTilePuzzle puzzle = new SlidingTilePuzzle(3, 3, i);

				for (int k = 0; k <= 5; k++) {
					double[] puzzleVals;
					switch (k) {
						case 0:
							puzzleVals = ucs(puzzle);
							expanded[(i/2)-1][k] += puzzleVals[0];
							generated[(i/2)-1][k] += puzzleVals[1];
							memory[(i/2)-1][k] += puzzleVals[2];
							break;
						case 1:
							puzzleVals = astarmisplaced(puzzle);
							expanded[(i/2)-1][k] += puzzleVals[0];
							generated[(i/2)-1][k] += puzzleVals[1];
							memory[(i/2)-1][k] += puzzleVals[2];
							break;
						case 2:
							puzzleVals = astarmanhattan(puzzle);
							expanded[(i/2)-1][k] += puzzleVals[0];
							generated[(i/2)-1][k] += puzzleVals[1];
							memory[(i/2)-1][k] += puzzleVals[2];
							break;
						case 3:
							puzzleVals = iterative(puzzle);
							expanded[(i/2)-1][k] += puzzleVals[0];
							generated[(i/2)-1][k] += puzzleVals[1];
							memory[(i/2)-1][k] += puzzleVals[2];
							break;
						case 4:
							puzzleVals = idastarmisplaced(puzzle);
							expanded[(i/2)-1][k] += puzzleVals[0];
							generated[(i/2)-1][k] += puzzleVals[1];
							memory[(i/2)-1][k] += puzzleVals[2];
							break;
						case 5:
							puzzleVals = idastarmanhattan(puzzle);
							expanded[(i/2)-1][k] += puzzleVals[0];
							generated[(i/2)-1][k] += puzzleVals[1];
							memory[(i/2)-1][k] += puzzleVals[2];
							break;
					}
				}
			}
			
			for (int j = 0; j < 6; j++) {
				expanded[(i/2)-1][j] /= 100;
				generated[(i/2)-1][j] /= 100;
				memory[(i/2)-1][j] /= 100;
			}
		}
		
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
	
	private static double[] ucs(SlidingTilePuzzle thePuzzle) {
		//System.out.println("ucs");
		PuzzleSolution solution = SlidingTilePuzzleSolver.uniformCostSearch(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] astarmisplaced(SlidingTilePuzzle thePuzzle) {
		//System.out.println("astarmis");
		PuzzleSolution solution = SlidingTilePuzzleSolver.AStarSearchMisplacedTiles(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] astarmanhattan(SlidingTilePuzzle thePuzzle) {
		//System.out.println("astarman");
		PuzzleSolution solution = SlidingTilePuzzleSolver.AStarSearchManhattanDistance(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] idastarmanhattan(SlidingTilePuzzle thePuzzle) {
		//System.out.println("idastarman");
		PuzzleSolution solution = SlidingTilePuzzleSolver.idaStarManhattanDistance(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	private static double[] idastarmisplaced(SlidingTilePuzzle thePuzzle) {
		//System.out.println("isastarmis");
		PuzzleSolution solution = SlidingTilePuzzleSolver.idaStarMisplacedTiles(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}

	private static double[] iterative(SlidingTilePuzzle thePuzzle) {
		//System.out.println("iter");
		PuzzleSolution solution = SlidingTilePuzzleSolver.iterativeDeepening(thePuzzle);
		
		return new double[] {solution.getNumberOfStatesExpanded(), solution.getNumGenerated(), solution.getNumberOfStatesInMemory()};
	}
	public static void printRow(double[] row) {
        for (double i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }
	public static void printRowHeader() {
		System.out.print("L\tUCS\tA*1\tA*2\tID\tIDA*1\tIDA*2");
        System.out.println();
    }
	public static void printColumnHeader(int len) {
        System.out.print(len);
        System.out.print("\t");
    }
}
