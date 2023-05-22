package pathfinding.grid.heuristics;

import pathfinding.Heuristic;
import pathfinding.NavigationNode;
import pathfinding.grid.GridCell;

public class ChebyshevDistance implements Heuristic{

	@Override
	public float calculate(NavigationNode from, NavigationNode to) {
		GridCell c1 = (GridCell)from, c2 = (GridCell) to;
		
		return calculate(Math.abs(c2.x - c1.x), Math.abs(c2.y - c1.y));
	}
	
	public float calculate(int dx, int dy) {
		return Math.max(dx, dy);
	}

}
