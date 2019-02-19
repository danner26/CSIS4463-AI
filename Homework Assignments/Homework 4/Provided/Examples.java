import csis4463.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Examples {
	
	public static void main(String[] args) {
		
		
		// To construct an 8-puzzle first two params should be 3 (a 3 by 3 puzzle). 
		// The 3rd parameter is the length of the shortest path from this state to goal state.
		// E.g., 4 as the 3rd param will generate a random puzzle such that it is possible to
		// find a solution with length 4.
		SlidingTilePuzzle puzzle = new SlidingTilePuzzle(3, 3, 4);
		
		// The SlidingTilePuzzle class overrides the toString method, so you can output
		// a puzzle state with a call to System.out.println as follows.
		System.out.println(puzzle);
		
		
		// You can solve a puzzle using the implementations of search algorithms provided in the
		// class SlidingTilePuzzleSolver.  See the documentation of that class for the search algorithms
		// included.  You call them all the same way.  Here is an example with A* and Manhattan distance.
		PuzzleSolution solution = SlidingTilePuzzleSolver.AStarSearchManhattanDistance(puzzle);
		
		// After the search is done, you can find the solution as well as statistics about the search
		// via the PuzzleSolution object.  See the documentation for names of the methods.  Here
		// are a couple examples.
		
		// The getSolution method gives you the path as an ArrayList.
		ArrayList<SlidingTilePuzzle> path = solution.getSolution();
		
		System.out.println("Solution path");
		for (SlidingTilePuzzle s : path) {
			System.out.println();
			System.out.println(s);
		}
		
		System.out.println();
		System.out.println("Num states expanded: " + solution.getNumberOfStatesExpanded());
		System.out.println("Num states generated: " + solution.getNumGenerated());
		System.out.println("Num states in memory: " + solution.getNumberOfStatesInMemory());
		System.out.println("Path length: " + solution.getPathLength());
		
		
		// You can get a list of the successors (states that are one step away) with:
		ArrayList<SlidingTilePuzzle> successors = puzzle.getSuccessors();
		
		// You can check if the state is the goal state with
		if (puzzle.isGoalState()) {
			// do something
		}
		
		// You can get the tile in a specific location of the puzzle with the following
		// (example specifically shows row 0 and column 2).
		int tileNumber = puzzle.getTile(0, 2);
		
		// Your search algorithms will need to know the dimensions of the puzzle.
		// You can get the number of rows and columns with
		int rows = puzzle.numRows();
		int columns = puzzle.numColumns();
		
		
		// I've provided you with a priority queue implementation that supports changing priorities.
		// You'll need this.  The Priority Queue implementation in the Java API doesn't support that operation.
		// This works a bit differently than the one in the Java API.  Here are a few examples.
		
		// If you want an empty priority queue of SlidingTilePuzzles
		MinHeapPQ<SlidingTilePuzzle> pq = new MinHeapPQ<SlidingTilePuzzle>();
		
		// Use the offer method to add an element, value pair.  This is different than the Java API'f offer method
		// which relies on a Comparator.  What value you use will depend on the search algorithm you are implementing.
		pq.offer(puzzle, 7);
		
		// If you need to change the priority of an element already in the priority queue, simply call offer again.
		// If the priority is lower than the current one, it will decrease it.  Otherwise, it will leave it alone.
		// This implementation doesn't support increasing priority.
		pq.offer(puzzle, 4);
		
		// peek returns (but does not remove) the element with lowest priority.
		// peekPriority returns the priority of that element.
		SlidingTilePuzzle puzzle2 = pq.peek();
		int value = pq.peekPriority();
		
		// poll removes and returns the element with lowest priority (just the element... if you need its priority and don't already have it,
		// then use peekPriority first).
		SlidingTilePuzzle puzzle3 = pq.poll();
		
		
		// For both Uniform Cost Search and A*, you will need a MinHeapPQ as your frontier (the states awaiting expansion).
		// However, for every state we've ever generated, we need to keep track of the best f value (for A*) or the best g value (for uniform cost search)
		// that we have previously seen.  Additionally, we need to keep track of the backpointer that corresponds with that f or g value.
		// Backpointer is just the state we were at previously along the relevant path.
		// I recommend using Java's HashMap class for this.  You can either maintain one HashMap for the f values and one for the backpointers.
		// Or you can declare a cimple class that contains those two values and then just have a single HashMap.
		
		// Here's an example of how you might declare a HashMap to keep track of previous f values.
		// i.e., mapping puzzles to integers.
		HashMap<SlidingTilePuzzle, Integer> generatedSet = new HashMap<SlidingTilePuzzle, Integer>();
		
		// You can then add to it with put:
		generatedSet.put(puzzle, 4);
		
		// You can check if you've seen a puzzle state previously with something like:
		if (generatedSet.containsKey(puzzle)) {
			// do something here.
		}
		
		// you can find a state's f value with get 
		int v = generatedSet.get(puzzle);
		
		
		// Likewise, you can use a HashMap to store backpointers:
		HashMap<SlidingTilePuzzle, SlidingTilePuzzle> back = new HashMap<SlidingTilePuzzle, SlidingTilePuzzle>();
		
		for (SlidingTilePuzzle s : successors) {
			back.put(s, puzzle);
		}
		
		
		
	}
}