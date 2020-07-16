package mazeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import maze.Maze;
import maze.Wall;
import maze.Cell;

class JoinCell {

	Cell c1;
	Cell c2;

	public JoinCell(Cell cOne, Cell cTwo) {
		c1 = cOne;
		c2 = cTwo;
	}

}

public class KruskalGenerator implements MazeGenerator {

	public <T> List<T> setToList(Set<T> mySet) {
		List<T> myList = new ArrayList<>();

		for (T q : mySet)
			myList.add(q);

		return myList;
	}

	@Override
	public void generateMaze(Maze maze) {

		if (maze.type == 0) {

			// Normal maze...

			ArrayList<Set<Cell>> cellSet = new ArrayList<Set<Cell>>();

			ArrayList<Set<Cell>> tunList = new ArrayList<Set<Cell>>();

			HashMap<Wall, JoinCell> listWall = new HashMap<Wall, JoinCell>();

			ArrayList<Cell> cellOne = new ArrayList<Cell>();
			ArrayList<Cell> cellTo = new ArrayList<Cell>();

			int[] randWall = new int[4];
			randWall[0] = 0; // East
			randWall[1] = 2; // North
			randWall[2] = 3; // West
			randWall[3] = 5; // South

			for (int i = 0; i < maze.sizeR; i++) {

				for (int j = 0; j < maze.sizeC; j++) {

					LinkedHashMap<Cell, Integer> validNeigh = new LinkedHashMap<Cell, Integer>();

					boolean north = false;
					boolean east = false;
					boolean west = false;
					boolean south = false;

					Set<Cell> cellSetSingle = new HashSet<Cell>();

					Cell now = maze.map[i][j];

					if (now.exitunnel != null) {

						boolean singleTunnel = true;

						Set<Cell> store = new HashSet<Cell>();

						store.add(now);
						store.add(now.exitunnel);

						tunList.add(store);

						for (int z = 0; z < cellSet.size(); z++) {

							if (cellSet.get(z).contains(now) && cellSet.get(z).contains(now.exitunnel)) {

								singleTunnel = false;
							}

						}

						if (singleTunnel) {

							cellSetSingle.add(now);
							cellSetSingle.add(now.exitunnel);
							cellSet.add(cellSetSingle);

						}

					} else {

						cellSetSingle.add(now);
						cellSet.add(cellSetSingle);
					}

					if (now.neigh[randWall[0]] != null) { // east
						east = true;
						validNeigh.put(now.neigh[randWall[0]], 0);
					}

					if (now.neigh[randWall[1]] != null) { // north
						north = true;
						validNeigh.put(now.neigh[randWall[1]], 2);
					}

					if (now.neigh[randWall[2]] != null) { // west
						west = true;
						validNeigh.put(now.neigh[randWall[2]], 3);
					}

					if (now.neigh[randWall[3]] != null) { // south
						south = true;
						validNeigh.put(now.neigh[randWall[3]], 5);
					}

					if (north || east || west || south) {

						for (int k = 0; k < validNeigh.size(); k++) {
							listWall.put(now.wall[validNeigh.get(setToList(validNeigh.keySet()).get(k))],
									new JoinCell(now, setToList(validNeigh.keySet()).get(k)));

						}

					}

				}

			}

			while (listWall.size() >= 1) {

				// Randomizer:
				int rdWall = -1;
				Random num = new Random();
				rdWall = num.nextInt(listWall.size());

				List<Wall> keys = new ArrayList<>(listWall.keySet());

				boolean flagSetChk = false;

				for (int s = 0; s < cellSet.size(); s++) {

					if (cellSet.get(s).contains(listWall.get(keys.get(rdWall)).c1) == true
							&& cellSet.get(s).contains(listWall.get(keys.get(rdWall)).c2) == true) {

						flagSetChk = true;
						listWall.remove(keys.get(rdWall));
						break;
					} else {

					}
				}

				if (flagSetChk == false) {
					// brake the wall:

					for (int p = 0; p < cellSet.size(); p++) {

						if ((setToList(cellSet.get(p))).contains(listWall.get(keys.get(rdWall)).c1)) {

							for (int r = 0; r < cellSet.size(); r++) {

								if ((setToList(cellSet.get(r))).contains(listWall.get(keys.get(rdWall)).c2)) {
									boolean safe = false;
									for (int o = 0; o < tunList.size(); o++) {

										if (cellSet.get(r).contains(setToList(tunList.get(o)).get(1))
												&& cellSet.get(p).contains(setToList(tunList.get(o)).get(0))) {
											System.out.println("if1");
											listWall.remove(keys.get(rdWall));
											r = cellSet.size();
											p = cellSet.size();
											safe = true;
											break;

										}

										else if (cellSet.get(r).contains(setToList(tunList.get(o)).get(0))
												&& cellSet.get(p).contains(setToList(tunList.get(o)).get(1))) {
											System.out.println("if2");
											listWall.remove(keys.get(rdWall));
											r = cellSet.size();
											p = cellSet.size();
											safe = true;
											break;

										}
									}
									if (safe == true) {
										r = cellSet.size();
										p = cellSet.size();
										break;
									} else {
										cellSet.get(p).addAll(setToList(cellSet.get(r)));
										cellSet.remove(cellSet.get(r));
										r = cellSet.size();
										p = cellSet.size();
										keys.get(rdWall).present = false;
										listWall.remove(keys.get(rdWall));
										break;
									}

								}

							}

						}

					}

				}

			}
		} else if (maze.type == 1) {
			// Tunnel maze...

			ArrayList<Set<Cell>> cellSet = new ArrayList<Set<Cell>>();

			ArrayList<Set<Cell>> tunList = new ArrayList<Set<Cell>>();

			HashMap<Wall, JoinCell> listWall = new HashMap<Wall, JoinCell>();

			ArrayList<Cell> cellOne = new ArrayList<Cell>();
			ArrayList<Cell> cellTo = new ArrayList<Cell>();

			int[] randWall = new int[4];
			randWall[0] = 0; // East
			randWall[1] = 2; // North
			randWall[2] = 3; // West
			randWall[3] = 5; // South

			for (int i = 0; i < maze.sizeR; i++) {

				for (int j = 0; j < maze.sizeC; j++) {

					LinkedHashMap<Cell, Integer> validNeigh = new LinkedHashMap<Cell, Integer>();

					boolean north = false;
					boolean east = false;
					boolean west = false;
					boolean south = false;

					Set<Cell> cellSetSingle = new HashSet<Cell>();

					Cell now = maze.map[i][j];

					if (now.exitunnel != null) {

						boolean singleTunnel = true;

						Set<Cell> store = new HashSet<Cell>();

						store.add(now);
						store.add(now.exitunnel);

						tunList.add(store);

						for (int z = 0; z < cellSet.size(); z++) {

							if (cellSet.get(z).contains(now) && cellSet.get(z).contains(now.exitunnel)) {

								singleTunnel = false;
							}

						}

						if (singleTunnel) {

							cellSetSingle.add(now);
							cellSetSingle.add(now.exitunnel);
							cellSet.add(cellSetSingle);

						}

					} else {

						cellSetSingle.add(now);
						cellSet.add(cellSetSingle);
					}

					if (now.neigh[randWall[0]] != null) { // east
						east = true;
						validNeigh.put(now.neigh[randWall[0]], 0);
					}

					if (now.neigh[randWall[1]] != null) { // north
						north = true;
						validNeigh.put(now.neigh[randWall[1]], 2);
					}

					if (now.neigh[randWall[2]] != null) { // west
						west = true;
						validNeigh.put(now.neigh[randWall[2]], 3);
					}

					if (now.neigh[randWall[3]] != null) { // south
						south = true;
						validNeigh.put(now.neigh[randWall[3]], 5);
					}

					if (north || east || west || south) {

						for (int k = 0; k < validNeigh.size(); k++) {
							listWall.put(now.wall[validNeigh.get(setToList(validNeigh.keySet()).get(k))],
									new JoinCell(now, setToList(validNeigh.keySet()).get(k)));

						}

					}

				}

			}

			while (listWall.size() >= 1) {

				// Randomizer:
				int rdWall = -1;
				Random num = new Random();
				rdWall = num.nextInt(listWall.size());

				List<Wall> keys = new ArrayList<>(listWall.keySet());

				boolean flagSetChk = false;

				for (int s = 0; s < cellSet.size(); s++) {

					if (cellSet.get(s).contains(listWall.get(keys.get(rdWall)).c1) == true
							&& cellSet.get(s).contains(listWall.get(keys.get(rdWall)).c2) == true) {

						flagSetChk = true;
						listWall.remove(keys.get(rdWall));
						break;
					} else {

					}
				}

				if (flagSetChk == false) {
					// brake the wall:

					for (int p = 0; p < cellSet.size(); p++) {

						if ((setToList(cellSet.get(p))).contains(listWall.get(keys.get(rdWall)).c1)) {

							for (int r = 0; r < cellSet.size(); r++) {

								if ((setToList(cellSet.get(r))).contains(listWall.get(keys.get(rdWall)).c2)) {
									boolean safe = false;
									for (int o = 0; o < tunList.size(); o++) {

										if (cellSet.get(r).contains(setToList(tunList.get(o)).get(1))
												&& cellSet.get(p).contains(setToList(tunList.get(o)).get(0))) {
											System.out.println("if1");
											listWall.remove(keys.get(rdWall));
											r = cellSet.size();
											p = cellSet.size();
											safe = true;
											break;

										}

										else if (cellSet.get(r).contains(setToList(tunList.get(o)).get(0))
												&& cellSet.get(p).contains(setToList(tunList.get(o)).get(1))) {
											System.out.println("if2");
											listWall.remove(keys.get(rdWall));
											r = cellSet.size();
											p = cellSet.size();
											safe = true;
											break;

										}
									}
									if (safe == true) {
										r = cellSet.size();
										p = cellSet.size();
										break;
									} else {
										cellSet.get(p).addAll(setToList(cellSet.get(r)));
										cellSet.remove(cellSet.get(r));
										r = cellSet.size();
										p = cellSet.size();
										keys.get(rdWall).present = false;
										listWall.remove(keys.get(rdWall));
										break;
									}

								}

							}

						}

					}

				}

			}

		}
	} // end of generateMaze()

} // end of class KruskalGenerator

// Use Link:
// https://www.youtube.com/watch?v=SqqOB2HgGsM
// http://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm
// https://github.com/njrahman/Maze/blob/master/src/generation/MazeBuilderKruskal.java
// http://www.algostructure.com/specials/maze.php