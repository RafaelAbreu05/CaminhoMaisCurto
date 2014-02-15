package algoritmoAStar;

import interacao.JFramePrincipal;

import java.util.ArrayList;
import java.util.Collections;
import ontologia.Caminho;
import ontologia.Ponto2D;

import terreno.Terreno;

public class AStar {
	private AreaMapa map;
	private AStarHeuristicas heuristic;
	// private int startX;
	// private int startY;
	// private int goalX;
	// private int goalY;
	/**
	 * closedList The list of Nodes not searched yet, sorted by their distance
	 * to the goal as guessed by our heuristic.
	 */
	private ArrayList<No> closedList;
	private SortedNodeList openList;
	private CaminhoNos shortestPath;
	Registador log = new Registador();

	public AStar(AreaMapa map, AStarHeuristicas heuristic) {
		this.map = map;
		this.heuristic = heuristic;

		closedList = new ArrayList<No>();
		openList = new SortedNodeList();
	}


	public CaminhoNos calcShortestPath(int inicioX, int inicioY, int fimX,
			int fimY) {
		// this.startX = startX;
		// this.startY = startY;
		// this.goalX = goalX;
		// this.goalY = goalY;

		// mark start and goal node
		map.setStartLocation(inicioX, inicioY);
		map.setGoalLocation(fimX, fimY);

		// Check if the goal node is blocked (if it is, it is impossible to find
		// a path there)
		if (map.getNo(fimX, fimY).isObstacle) {
			return null;
		}

		map.getStartNo().setDistanceFromStart(0);
		closedList.clear();
		openList.clear();
		openList.add(map.getStartNo());

		// while we haven't reached the goal yet
		while (openList.size() != 0) {

			// get the first Node from non-searched Node list, sorted by lowest
			// distance from our goal as guessed by our heuristic
			No current = openList.getFirst();

			// check if our current Node location is the goal Node. If it is, we
			// are done.
			if (current.getX() == map.getGoalLocationX()
					&& current.getY() == map.getGoalLocationY()) {
				return reconstructPath(current);
			}

			// move current Node to the closed (already searched) list
			openList.remove(current);
			closedList.add(current);

			// go through all the current Nodes neighbors and calculate if one
			// should be our next step
			for (No neighbor : current.getNeighborList()) {
				boolean neighborIsBetter;

				// if we have already searched this Node, don't bother and
				// continue to the next one
				if (closedList.contains(neighbor))
					continue;

				// also just continue if the neighbor is an obstacle
				if (!neighbor.isObstacle) {

					// calculate how long the path is if we choose this neighbor
					// as the next step in the path
					float neighborDistanceFromStart = (current
							.getDistanceFromStart() + map.getDistanceBetween(
							current, neighbor));

					// add neighbor to the open list if it is not there
					if (!openList.contains(neighbor)) {
						openList.add(neighbor);
						neighborIsBetter = true;
						// if neighbor is closer to start it could also be
						// better
					} else if (neighborDistanceFromStart < current
							.getDistanceFromStart()) {
						neighborIsBetter = true;
					} else {
						neighborIsBetter = false;
					}
					// set neighbors parameters if it is better
					if (neighborIsBetter) {
						neighbor.setPreviousNode(current);
						neighbor.setDistanceFromStart(neighborDistanceFromStart);
						neighbor.setHeuristicDistanceFromGoal(heuristic
								.getEstimatedDistanceToGoal(neighbor.getX(),
										neighbor.getY(),
										map.getGoalLocationX(),
										map.getGoalLocationY()));
					}
				}

			}
		}
		return null;
	}

	public Caminho getCaminhoMaisCurto(long velocidade) {
		Caminho caminho = new Caminho();
		for (int x = 0; x < Terreno.linhas; x++) {
			for (int y = 0; y < Terreno.colunas; y++) {
				No no = map.getNo(x, y);
				if (shortestPath.contains(no.getX(), no.getY())) {
					Ponto2D ponto = new Ponto2D(x, y);
					caminho.mover(ponto);
					if (x != Terreno.getFim().getX()
							&& y != Terreno.getFim().getY()) {
						JFramePrincipal.terreno.setPosicao(x, y, "b");
					}
					try {
						Thread.sleep(velocidade);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return caminho;
	}

	private CaminhoNos reconstructPath(No node) {
		CaminhoNos path = new CaminhoNos();
		while (!(node.getPreviousNode() == null)) {
			path.prependWayPoint(node);
			node = node.getPreviousNode();
		}
		this.shortestPath = path;
		return path;
	}

	private class SortedNodeList {

		private ArrayList<No> list = new ArrayList<No>();

		public No getFirst() {
			return list.get(0);
		}

		public void clear() {
			list.clear();
		}

		public void add(No no) {
			list.add(no);
			Collections.sort(list);
		}

		public void remove(No n) {
			list.remove(n);
		}

		public int size() {
			return list.size();
		}

		public boolean contains(No n) {
			return list.contains(n);
		}
	}

}
