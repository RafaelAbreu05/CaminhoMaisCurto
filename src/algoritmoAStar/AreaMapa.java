package algoritmoAStar;

import java.util.ArrayList;
import algoritmoAStar.Registador;

;

public class AreaMapa {

	private int mapWith;
	private int mapHeight;
	private ArrayList<ArrayList<No>> map;
	private int startLocationX = 0;
	private int startLocationY = 0;
	private int goalLocationX = 0;
	private int goalLocationY = 0;
	private String[][] obstacleMap;
	private int tipoCaminhante;

	private Registador log = new Registador();

	public AreaMapa(int mapWith, int mapHeight, String[][] obstacleMap,
			int tipoCaminhante) {
		this.mapWith = mapWith;
		this.mapHeight = mapHeight;
		this.obstacleMap = obstacleMap;
		this.tipoCaminhante = tipoCaminhante;

		createMap();
		log.addToLog("\tMapa criado");
		registerEdges();
	}

	private void createMap() {
		No no;
		map = new ArrayList<ArrayList<No>>();
		for (int x = 0; x < mapWith; x++) {
			map.add(new ArrayList<No>());
			for (int y = 0; y < mapHeight; y++) {
				no = new No(x, y);
				switch (tipoCaminhante) {
				case 1: // Pessoa sem Acesso Restito
				{
					// Se for parede ("p"), água ("a"), zona verde ("v") ou
					// acesso restrito ("x") é um obstáculo
					if (obstacleMap[x][y].equals("p")
							|| obstacleMap[x][y].equals("a")
							|| obstacleMap[x][y].equals("v")
							|| obstacleMap[x][y].equals("x"))
						no.setObstical(true);
					break;
				}
				case 2: // Pessoa com Acesso Restrito
				{
					// Se for parede ("p"), água ("a") ou zona verde ("v") é
					// obstáculo
					if (obstacleMap[x][y].equals("p")
							|| obstacleMap[x][y].equals("a")
							|| obstacleMap[x][y].equals("v"))
						no.setObstical(true);
					break;
				}
				case 3: // Defeciente Motor sem Acesso Restrito
				{
						// Se for parede ("p"), água("a"), acesso restrito ("x"),
						// zona verde ("v") ou escadas ("s") é um obstáculo
					if (obstacleMap[x][y].equals("p")
							|| obstacleMap[x][y].equals("a")
							|| obstacleMap[x][y].equals("x")
							|| obstacleMap[x][y].equals("v")
							|| obstacleMap[x][y].equals("s"))
						no.setObstical(true);
					break;
				}
				case 4: // Defeciente Motor com Acesso Restrito
				{
					// Se for parede ("p"), água ("a"), escadas ("s") ou zona
					// verde ("v") é obstáculo
					if (obstacleMap[x][y].equals("p")
							|| obstacleMap[x][y].equals("a")
							|| obstacleMap[x][y].equals("s")
							|| obstacleMap[x][y].equals("v"))
						no.setObstical(true);
					break;
				}
				default:
					break;
				}
				map.get(x).add(no);
			}
		}
	}

	/**
	 * Registers the Nos edges (connections to its neighbors).
	 */
	private void registerEdges() {
		for (int x = 0; x < mapWith - 1; x++) {
			for (int y = 0; y < mapHeight - 1; y++) {
				No No = map.get(x).get(y);
				if (!(y == 0))
					No.setNorth(map.get(x).get(y - 1));
				if (!(y == 0) && !(x == mapWith))
					No.setNorthEast(map.get(x + 1).get(y - 1));
				if (!(x == mapWith))
					No.setEast(map.get(x + 1).get(y));
				if (!(x == mapWith) && !(y == mapHeight))
					No.setSouthEast(map.get(x + 1).get(y + 1));
				if (!(y == mapHeight))
					No.setSouth(map.get(x).get(y + 1));
				if (!(x == 0) && !(y == mapHeight))
					No.setSouthWest(map.get(x - 1).get(y + 1));
				if (!(x == 0))
					No.setWest(map.get(x - 1).get(y));
				if (!(x == 0) && !(y == 0))
					No.setNorthWest(map.get(x - 1).get(y - 1));
			}
		}
	}

	public ArrayList<ArrayList<No>> getNos() {
		return map;
	}

	public void setObstical(int x, int y, boolean isObstical) {
		map.get(x).get(y).setObstical(isObstical);
	}

	public No getNo(int x, int y) {
		return map.get(x).get(y);
	}

	public void setStartLocation(int x, int y) {
		map.get(startLocationX).get(startLocationY).setStart(false);
		map.get(x).get(y).setStart(true);
		startLocationX = x;
		startLocationY = y;
	}

	public void setGoalLocation(int x, int y) {
		map.get(goalLocationX).get(goalLocationY).setGoal(false);
		map.get(x).get(y).setGoal(true);
		goalLocationX = x;
		goalLocationY = y;
	}

	public int getStartLocationX() {
		return startLocationX;
	}

	public int getStartLocationY() {
		return startLocationY;
	}

	public No getStartNo() {
		return map.get(startLocationX).get(startLocationY);
	}

	public int getGoalLocationX() {
		return goalLocationX;
	}

	public int getGoalLocationY() {
		return goalLocationY;
	}

	public No getGoalLocation() {
		return map.get(goalLocationX).get(goalLocationY);
	}

	public float getDistanceBetween(No No1, No No2) {
		// if the Nos are on top or next to each other, return 1
		if (No1.getX() == No2.getX() || No1.getY() == No2.getY()) {
			return 1 * (mapHeight + mapWith);
		} else { // if they are diagonal to each other return diagonal distance:
					// sqrt(1^2+1^2)
			return (float) 1.7 * (mapHeight + mapWith);
		}
	}

	public int getMapWith() {
		return mapWith;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void clear() {
		startLocationX = 0;
		startLocationY = 0;
		goalLocationX = 0;
		goalLocationY = 0;
		createMap();
		registerEdges();
	}
}
