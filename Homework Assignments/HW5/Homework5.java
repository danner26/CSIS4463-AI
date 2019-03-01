import csis4463.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * For this assignment, you will need the puzzle.jar file and its documentation (docs.zip) 
 * from homework 4, and the Examples.java will be useful as well.
 *
 * In Java, implement any search algorithm that we saw in class to solve sliding tile
 * puzzles, such as the 8 puzzle, 15 puzzle, etc.
 *
 * You must not change the name, parameters, or return type of the solver method.
 * You are free to add as many helper methods as you find useful.
 *
 * You are free to use the MinHeapPQ class that is in the puzzle.jar (see its documentation).
 * It supports changing the priority of elements that are in the PQ, whereas the PQ implementation
 * in the Java API does not support that operation.
 *
 * Grading:
 * Your grade will be in the interval [0, 100] if you implement an algorithm that is guaranteed
 * to find the optimal path.  Otherwise, if you implement an algorithm that is not
 * guaranteed to find the optimal path (e.g., DFS), then your grade will be in the interval [0, 85]
 * (i.e., you lose 15 points for the non-optimal algorithms).
 *
 * If your code doesn't compile, then your grade will be in the interval [0, 60] depending upon the severity
 * of the syntax errors.  i.e., make sure your code compiles (you lose at least 40 points if it doesn't.
 *
 * After completing the solver method, write code to demonstrate that it works in the Homework5Main class.
 *
 * @author Sam Isidoro and Daniel Anner
 *
 */
public class Homework5 {
	
	/**
	 * Solves sliding tile puzzles with the algorithm of your choice.
	 *
	 * @return A path from the start state to the goal state.
	 */
	public static ArrayList<SlidingTilePuzzle> solver(SlidingTilePuzzle puzzle, Heuristic h) {
		//init the list/map/heaps for our algorithm
		MinHeapPQ<SlidingTilePuzzle> priorityQueue = new MinHeapPQ<SlidingTilePuzzle>(); //states to still evaluate
		HashMap<SlidingTilePuzzle, Integer> exploredStates = new HashMap<SlidingTilePuzzle, Integer>(); //already evaluated states
		HashMap<SlidingTilePuzzle, SlidingTilePuzzle> bps = new HashMap<SlidingTilePuzzle, SlidingTilePuzzle>(); //back pointers
		ArrayList<SlidingTilePuzzle> solutionPath = new ArrayList<SlidingTilePuzzle>(); //hopefully our shortest path to the solution
		
		//@Sam, I keep thinking to use add, but in the docs you'll see we need to use offer
		priorityQueue.offer(puzzle, h.h(puzzle));
		
		while (!priorityQueue.isEmpty()) {
			int peekPriority = priorityQueue.peekPriority(); //according to the docs, "returns the priority value of the top of the MinHeapPQ"

			SlidingTilePuzzle evalState = priorityQueue.poll(); //get the first state to eval, and remove it from the queue
			if(evalState.isGoalState()) { //if we are at the goal, then setup data to be returned in the arraylist
				SlidingTilePuzzle solution = evalState;
				
				while (bps.get(solution) != null) { //check if the state is in the back pointer map and iterate
					solutionPath.add(0, solution); //add solution to the beginning
					solution = bps.get(solution); //get the back pointer
				}
				return solutionPath; //return the array list
			} else { //not at the goal, put data in the exploredStates map
				exploredStates.put(evalState, peekPriority);
				peekPriority = (peekPriority - h.h(evalState) ); //eval and set peekPriority to new state (not sure if the +1 is needed)
				
				for (SlidingTilePuzzle puzzle2 : evalState.getSuccessors()) { //iterate through all successors (use a puzzle holder, puzzle2)
					int cost = peekPriority + h.h(puzzle2); //cost is peekPriority and heuristic cost
					
					/*
					 * check for the following condition to be true:
					 * make sure the cost is less (this ensures we have the lowest cost solution)
					 * make sure the explored states DO NOT contain the puzzle2 instance
					 */
					if(!exploredStates.containsKey(puzzle2) || cost < exploredStates.get(puzzle2)) { //why cant I order this in the reverse direction??
						priorityQueue.offer(puzzle2, cost); //again its offer not add.. 
						bps.put(puzzle2, evalState); //set the back pointer 
					}
				}
			}
		}
		return null; //returns null if the puzzle is not solvable
	}
}