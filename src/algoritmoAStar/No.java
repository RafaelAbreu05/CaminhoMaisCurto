package algoritmoAStar;

import java.util.ArrayList;

public class No implements Comparable<No> {

	/* Nodes that this is connected to */
	AreaMapa map;
	No north;
	No northEast;
	No east;
	No southEast;
	No south;
	No southWest;
	No west;
	No northWest;
	ArrayList<No> neighborList;
	boolean visited;
	float distanceFromStart;
	float heuristicDistanceFromGoal;
	No previousNode;
	int x;
	int y;
	boolean isObstacle;
	boolean isStart;
	boolean isGoal;

	No(int x, int y) {
		neighborList = new ArrayList<No>();
		this.x = x;
		this.y = y;
		this.visited = false;
		this.distanceFromStart = Integer.MAX_VALUE;
		this.isObstacle = false;
		this.isStart = false;
		this.isGoal = false;
	}

	No(int x, int y, boolean visited, int distanceFromStart,
			boolean isObstical, boolean isStart, boolean isGoal) {
		neighborList = new ArrayList<No>();
		this.x = x;
		this.y = y;
		this.visited = visited;
		this.distanceFromStart = distanceFromStart;
		this.isObstacle = isObstical;
		this.isStart = isStart;
		this.isGoal = isGoal;
	}

	public No getNorth() {
		return north;
	}

	public void setNorth(No north) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.north))
			neighborList.remove(this.north);
		neighborList.add(north);

		// set the new Node
		this.north = north;
	}

	public No getNorthEast() {
		return northEast;
	}

	public void setNorthEast(No northEast) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.northEast))
			neighborList.remove(this.northEast);
		neighborList.add(northEast);

		// set the new Node
		this.northEast = northEast;
	}

	public No getEast() {
		return east;
	}

	public void setEast(No east){
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.east))
			neighborList.remove(this.east);
		neighborList.add(east);

		// set the new Node
		this.east = east;
	}

	public No getSouthEast() {
		return southEast;
	}

	public void setSouthEast(No southEast) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.southEast))
			neighborList.remove(this.southEast);
		neighborList.add(southEast);

		// set the new Node
		this.southEast = southEast;
	}

	public No getSouth() {
		return south;
	}

	public void setSouth(No south) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.south))
			neighborList.remove(this.south);
		neighborList.add(south);

		// set the new Node
		this.south = south;
	}

	public No getSouthWest() {
		return southWest;
	}

	public void setSouthWest(No southWest) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.southWest))
			neighborList.remove(this.southWest);
		neighborList.add(southWest);

		// set the new Node
		this.southWest = southWest;
	}

	public No getWest() {
		return west;
	}

	public void setWest(No west) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.west))
			neighborList.remove(this.west);
		neighborList.add(west);

		// set the new Node
		this.west = west;
	}

	public No getNorthWest() {
		return northWest;
	}

	public void setNorthWest(No northWest) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.northWest))
			neighborList.remove(this.northWest);
		neighborList.add(northWest);

		// set the new Node
		this.northWest = northWest;
	}

	public ArrayList<No> getNeighborList() {
		return neighborList;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public float getDistanceFromStart() {
		return distanceFromStart;
	}

	public void setDistanceFromStart(float f) {
		this.distanceFromStart = f;
	}

	public No getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(No previousNode) {
		this.previousNode = previousNode;
	}

	public float getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isObstical() {
		return isObstacle;
	}

	public void setObstical(boolean isObstical) {
		this.isObstacle = isObstical;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public boolean equals(No node) {
		return (node.x == x) && (node.y == y);
	}

	public int compareTo(No otherNode) {
		float thisTotalDistanceFromGoal = heuristicDistanceFromGoal
				+ distanceFromStart;
		float otherTotalDistanceFromGoal = otherNode
				.getHeuristicDistanceFromGoal()
				+ otherNode.getDistanceFromStart();

		if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal) {
			return -1;
		} else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal) {
			return 1;
		} else {
			return 0;
		}
	}
}
