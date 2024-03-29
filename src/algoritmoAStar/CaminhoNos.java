package algoritmoAStar;

import java.util.ArrayList;

public class CaminhoNos {
	// The waypoints in the path (list of coordiantes making up the path)
	private ArrayList<No> waypoints = new ArrayList<No>();

	public CaminhoNos() {
	}

	public int getLength() {
		return waypoints.size();
	}

	public No getWayPoint(int index) {
		return waypoints.get(index);
	}


	/**
	 * Get the x-coordinate for the waypoiny at the given index.
	 *
	 * @param index
	 *            The index of the waypoint to get the x-coordinate of.
	 * @return The x coordinate at the waypoint.
	 */
	public int getX(int index) {
		return getWayPoint(index).getX();
	}

	/**
	 * Get the y-coordinate for the waypoint at the given index.
	 *
	 * @param index
	 *            The index of the waypoint to get the y-coordinate of.
	 * @return The y coordinate at the waypoint.
	 */
	public int getY(int index) {
		return getWayPoint(index).getY();
	}

	/**
	 * Append a waypoint to the path.
	 *
	 * @param x
	 *            The x coordinate of the waypoint.
	 * @param y
	 *            The y coordinate of the waypoint.
	 */
	public void appendWayPoint(No n) {
		waypoints.add(n);
	}

	/**
	 * Add a waypoint to the beginning of the path.
	 *
	 * @param x
	 *            The x coordinate of the waypoint.
	 * @param y
	 *            The y coordinate of the waypoint.
	 */
	public void prependWayPoint(No n) {
		waypoints.add(0, n);
	}

	/**
	 * Check if this path contains the WayPoint
	 *
	 * @param x
	 *            The x coordinate of the waypoint.
	 * @param y
	 *            The y coordinate of the waypoint.
	 * @return True if the path contains the waypoint.
	 */
	public boolean contains(int x, int y) {
		for (No no : waypoints) {
			if (no.getX() == x && no.getY() == y)
				return true;
		}
		return false;
	}
}
