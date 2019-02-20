package solution;
import java.util.HashMap;
import java.util.ArrayList;
import csis4463.*;

public class solution {
	public static void main(String[] args) {
		//Hash map or 2d array for storing values of state averages
		
		
		for(int i = 2; i <= 12; i+=2) { //loop through the required sizes 2,4,6,8,10,12
			SlidingTilePuzzle[] puzzels = new SlidingTilePuzzle[100];
			int expanded = 0,
				generated = 0,
				memory = 0;
			for (int j = 0; j < 100; j++) {
				puzzels[j] = new SlidingTilePuzzle(3, 3, i);
			}
		}
	}
}
