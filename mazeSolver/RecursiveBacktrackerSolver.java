package mazeSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import maze.Maze;

public class RecursiveBacktrackerSolver implements MazeSolver {

	static int wayCount = 0;
	HashMap<String, VerticeP> vertChk = new HashMap<String, VerticeP>();

	@Override
	public void solveMaze(Maze maze) {

		String b1 = "[";
		String b2 = "]";

		HashMap<String, VerticeP> verts = new HashMap<String, VerticeP>();

		setupMaze(maze, verts);

		setupVerts(verts, maze);

		VerticeP thisVertice = verts.get(b1 + maze.entrance.c + "," + maze.entrance.r + b2);
		VerticeP lastVertice = verts.get(b1 + maze.exit.c + "," + maze.exit.r + b2);

		Random random = new Random();

		while (thisVertice != lastVertice) {
			maze.drawFtPrt(thisVertice.getCell());

			if (thisVertice.vertGet(6) != null && !vertChk.containsKey(thisVertice.vertGet(6).getVertPoint())) {
				thisVertice.vertGet(6).setPrevious(thisVertice);
				vertChk.put(thisVertice.getVertPoint(), thisVertice);

				thisVertice = thisVertice.vertGet(6);
			} else {

				boolean allVisited = true;

				{
					int i = 0;
					while (i <= 6) {

						if (thisVertice.vertGet(i) != null) {
							if (!vertChk.containsKey(thisVertice.vertGet(i).getVertPoint())) {
								allVisited = false;
							}
						}

						i++;
					}
				}

				if (allVisited == true) {
					vertChk.put(thisVertice.getVertPoint(), thisVertice);
					thisVertice = thisVertice.getPrevious();
				}

				else {
					boolean flagChk = false;

					while (!flagChk) {

						ArrayList<Integer> vertArray = new ArrayList<Integer>();
						for (int i = 0; i < maze.NUM_DIR; i++) {
							if (thisVertice.vertGet(i) != null) {
								if (!vertChk.containsKey(thisVertice.vertGet(i).getVertPoint())) {
									vertArray.add(i);
								}
							}
						}
						random = new Random();
						int randomDir;
						randomDir = random.nextInt(vertArray.size());
						int face = vertArray.get(randomDir);

						if (thisVertice.vertGet(face) != null
								&& !vertChk.containsKey(thisVertice.vertGet(face).getVertPoint())) {
							flagChk = true;
							VerticeP vertForth = verts.get(thisVertice.vertGet(face).getVertPoint());
							vertForth.setPrevious(thisVertice);
							vertChk.put(thisVertice.getVertPoint(), thisVertice);
							thisVertice = vertForth;

						}
					}
				}
			}
		}

		maze.drawFtPrt(thisVertice.getCell());
		vertChk.put(thisVertice.getVertPoint(), thisVertice);

		thisVertice.countPath(wayCount);
		System.out.println("length of the path created = " + wayCount);
	}

	public void setupMaze(Maze maze, HashMap<String, VerticeP> verts) {

		{
			int i = 0;
			while (i < maze.sizeR) {
				int j = 0;
				while (j < maze.sizeC) {

					VerticeP vertNew = new VerticeP(maze.map[i][j]);
					verts.put(vertNew.getVertPoint(), vertNew);

					j++;
				}

				i++;
			}
		}
	}

	public void setupVerts(HashMap<String, VerticeP> verts, Maze maze) {

		String b1 = "[";
		String b2 = "]";

		for (VerticeP vertV : verts.values()) {

			for (int h = 0; h < maze.NUM_DIR; h++) {
				if (vertV.getCell().neigh[h] != null) {
					if (vertV.getCell().wall[h].present == false && vertV.getCell().wall[h].drawn == false) {
						VerticeP vertDest = verts
								.get(b1 + vertV.getCell().neigh[h].c + "," + vertV.getCell().neigh[h].r + b2);
						if (vertDest != null)
							vertV.AdjSet(vertDest, h);
					}
				}
			}
			if (maze.type == 1) {
				if (vertV.getCell().exitunnel != null) {
					VerticeP exitTunnel = verts
							.get(b1 + vertV.getCell().exitunnel.c + "," + vertV.getCell().exitunnel.r + b2);
					vertV.AdjSet(exitTunnel, 6);
				}
			}
		}

	}

	@Override
	public boolean isSolved() {

		return true;
	}

	@Override
	public int cellsExplored() {

		return vertChk.size();
	}

}