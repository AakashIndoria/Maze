
package mazeGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import maze.Cell;
import maze.Maze;

public class HuntAndKillGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {

		// Normal Maze...
		if (maze.type == 0) {

			Set<Cell> visited = new HashSet<Cell>();

			int totalCells = maze.sizeR * maze.sizeC;

			Random num = new Random();

			Cell now = maze.map[num.nextInt(maze.map.length - 1)][num.nextInt(maze.map[0].length - 1)];

			int[] randWall = new int[4];

			randWall[0] = 0; // East
			randWall[1] = 2; // North
			randWall[2] = 3; // West
			randWall[3] = 5; // South

			while (visited.size() < totalCells) {

				boolean flag = true;

				while (flag) {

					if (!visited.contains(now)) {

						visited.add(now);

						// System.out.println(visited.size());

						if (now.exitunnel != null) {

							now = now.exitunnel;
							visited.add(now);
						}

						boolean north = false;
						boolean east = false;
						boolean west = false;
						boolean south = false;

						LinkedHashMap<Cell, Integer> validNeigh = new LinkedHashMap<Cell, Integer>();

						if (now.neigh[randWall[0]] != null) { // east
							if (!visited.contains(now.neigh[randWall[0]])) {
								east = true;
								validNeigh.put(now.neigh[randWall[0]], 0);
							}
						}

						if (now.neigh[randWall[1]] != null) { // north
							if (!visited.contains(now.neigh[randWall[1]])) {
								north = true;
								validNeigh.put(now.neigh[randWall[1]], 2);
							}
						}
						if (now.neigh[randWall[2]] != null) { // west
							if (!visited.contains(now.neigh[randWall[2]])) {
								west = true;
								validNeigh.put(now.neigh[randWall[2]], 3);
							}
						}
						if (now.neigh[randWall[3]] != null) { // south
							if (!visited.contains(now.neigh[randWall[3]])) {
								south = true;
								validNeigh.put(now.neigh[randWall[3]], 5);
							}
						}

						if (north || east || west || south) {

							Cell newRandomNeigh = setToList(validNeigh.keySet()).get(num.nextInt(validNeigh.size()));

							now.wall[validNeigh.get(newRandomNeigh)].present = false;

							now = newRandomNeigh;

						} else {
							flag = false;
						}
					}

				}

				if (visited.size() == totalCells) {

					break;

				}

				// HUnt Mode...
				for (int i = 0; i < maze.map.length - 1; i++) {
					for (int j = 0; j < maze.map[0].length - 1; j++) {

						if (visited.contains(maze.map[i][j])) {

							Cell temp = findUnvisited(maze.map[i][j], visited);

							if (temp != null) {

								// Chk for wallDown:
								now = temp;

								i = maze.map.length - 1;
								j = maze.map[0].length - 1;
								break;
							}
						}

					}

				}

			}
		} else if (maze.type == 1) {

			// Tunnel maze

			Set<Cell> visited = new HashSet<Cell>();

			int totalCells = maze.sizeR * maze.sizeC;

			Random num = new Random();

			Cell now = maze.map[num.nextInt(maze.map.length - 1)][num.nextInt(maze.map[0].length - 1)];

			int[] randWall = new int[4];

			randWall[0] = 0; // East
			randWall[1] = 2; // North
			randWall[2] = 3; // West
			randWall[3] = 5; // South

			while (visited.size() < totalCells) {

				boolean flag = true;

				while (flag) {

					if (!visited.contains(now)) {

						visited.add(now);

						// System.out.println(visited.size());

						if (now.exitunnel != null) {

							now = now.exitunnel;
							visited.add(now);
						}

						boolean north = false;
						boolean east = false;
						boolean west = false;
						boolean south = false;

						LinkedHashMap<Cell, Integer> validNeigh = new LinkedHashMap<Cell, Integer>();

						if (now.neigh[randWall[0]] != null) { // east
							if (!visited.contains(now.neigh[randWall[0]])) {
								east = true;
								validNeigh.put(now.neigh[randWall[0]], 0);
							}
						}

						if (now.neigh[randWall[1]] != null) { // north
							if (!visited.contains(now.neigh[randWall[1]])) {
								north = true;
								validNeigh.put(now.neigh[randWall[1]], 2);
							}
						}
						if (now.neigh[randWall[2]] != null) { // west
							if (!visited.contains(now.neigh[randWall[2]])) {
								west = true;
								validNeigh.put(now.neigh[randWall[2]], 3);
							}
						}
						if (now.neigh[randWall[3]] != null) { // south
							if (!visited.contains(now.neigh[randWall[3]])) {
								south = true;
								validNeigh.put(now.neigh[randWall[3]], 5);
							}
						}

						if (north || east || west || south) {

							Cell newRandomNeigh = setToList(validNeigh.keySet()).get(num.nextInt(validNeigh.size()));

							now.wall[validNeigh.get(newRandomNeigh)].present = false;

							now = newRandomNeigh;

						} else {
							flag = false;
						}
					}

				}

				if (visited.size() == totalCells) {

					break;

				}

				// HUnt Mode...
				for (int i = 0; i < maze.map.length - 1; i++) {
					for (int j = 0; j < maze.map[0].length - 1; j++) {

						if (visited.contains(maze.map[i][j])) {

							Cell temp = findUnvisited(maze.map[i][j], visited);

							if (temp != null) {

								// Chk for wallDown:
								now = temp;

								i = maze.map.length - 1;
								j = maze.map[0].length - 1;
								break;
							}
						}

					}

				}

			}
		}

	} // end of generateMaze()

	public <T> List<T> setToList(Set<T> mySet) {
		List<T> myList = new ArrayList<>();

		for (T q : mySet)
			myList.add(q);

		return myList;
	}

	public Cell findUnvisited(Cell stuck, Set<Cell> visited) {

		Cell now = stuck;

		int[] randWall = new int[4];

		randWall[0] = 0; // East
		randWall[1] = 2; // North
		randWall[2] = 3; // West
		randWall[3] = 5; // South

		boolean north = false;
		boolean east = false;
		boolean west = false;
		boolean south = false;

		LinkedHashMap<Cell, Integer> validNeigh = new LinkedHashMap<Cell, Integer>();

		if (now.neigh[randWall[0]] != null) { // east
			if (!visited.contains(now.neigh[randWall[0]])) {
				east = true;
				validNeigh.put(now.neigh[randWall[0]], 0);
			}
		}

		if (now.neigh[randWall[1]] != null) { // north
			if (!visited.contains(now.neigh[randWall[1]])) {
				north = true;
				validNeigh.put(now.neigh[randWall[1]], 2);
			}
		}
		if (now.neigh[randWall[2]] != null) { // west
			if (!visited.contains(now.neigh[randWall[2]])) {
				west = true;
				validNeigh.put(now.neigh[randWall[2]], 3);
			}
		}
		if (now.neigh[randWall[3]] != null) { // south
			if (!visited.contains(now.neigh[randWall[3]])) {
				south = true;
				validNeigh.put(now.neigh[randWall[3]], 5);
			}
		}

		if (north || east || west || south) {

			for (int i = 0; i < validNeigh.size(); i++) {

				if (!visited.contains(setToList(validNeigh.keySet()).get(i))) {

					now.wall[validNeigh.get(setToList(validNeigh.keySet()).get(i))].present = false;

					return setToList(validNeigh.keySet()).get(i);

				}

			}

		}

		return null;
	}

} // end of class HuntAndKillGenerator