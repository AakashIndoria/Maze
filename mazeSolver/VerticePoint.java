package mazeSolver;

import maze.Cell;

class VerticeP {
	private String vertPoint;
	private Cell vert;
	private VerticeP[] vertNeigh = new VerticeP[7];
	private VerticeP vertPrev;

	public VerticeP(Cell vert) {
		String b1 = "[";
		String b2 = "]";
		this.vert = vert;
		this.vertPoint = b1 + vert.c + "," + vert.r + b2;
	}

	public String getVertPoint() {
		return vertPoint;
	}

	public Cell getCell() {
		return vert;
	}

	public void AdjSet(VerticeP node, int way) {
		vertNeigh[way] = node;
	}

	public VerticeP vertGet(int dir) {
		return vertNeigh[dir];
	}

	public void setPrevious(VerticeP node) {
		this.vertPrev = node;
	}

	public VerticeP getPrevious() {
		return this.vertPrev;
	}

	public void countPath(int pathLength) {
		if (vertPrev != null) {

			pathLength++;
			vertPrev.countPath(pathLength);

		} else {

			RecursiveBacktrackerSolver.wayCount = pathLength;

		}
	}
}
