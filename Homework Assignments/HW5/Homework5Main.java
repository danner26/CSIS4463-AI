import java.util.ArrayList;

import csis4463.*;

/**
 * See comments in Homework5.java first.
 * 
 * @author Your Name Goes here.
 */
public class Homework5Main {
	
	public static void main(String[] args) {
		// write code here to demonstrate that your 8 puzzle solver works.
		SlidingTilePuzzle puzzle = new SlidingTilePuzzle(3,3,31);
		ArrayList<SlidingTilePuzzle> solution = Homework5.solver(puzzle, new ManhattanDistance());
		
		System.out.println("Initial Puzzle");
		System.out.println(puzzle.toString());
		
		System.out.println("Iterating through the path");
		for (int i = 1; i < solution.size(); i++) {
			System.out.println("Path " + i);
			System.out.println(solution.get(i));
			System.out.println();
		}
		System.out.println(solution.get(solution.size()-1));
	}
}
class ManhattanDistance implements Heuristic {
	//implement the heuristic h() function with our puzzle
	public int h(SlidingTilePuzzle puzzle) {
		int sum = 0;
		//Use SlidingTilePuzzle's numRows() & numColumns() function from puzzle.jar
		int rows = puzzle.numRows();
		int cols = puzzle.numColumns(); 

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int pos = puzzle.getTile(i, j);
				if (pos != 0) {
					sum += (Math.abs(i - Math.floor((pos-1)/rows)) + Math.abs(j - (pos-1)%cols));
				}
			}
		}
		return sum;
	}
}