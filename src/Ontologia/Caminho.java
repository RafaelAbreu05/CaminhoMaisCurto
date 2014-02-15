package ontologia;


import java.util.*;
import jade.content.Concept;


public class Caminho implements Concept{
	
	// Caminho é um conjunto (ArrayList) de pontos 2D
	private ArrayList<Ponto2D> trajetoria = new ArrayList<Ponto2D>();

	// Gets
	public ArrayList<Ponto2D> getTrajetoria() { 
		return trajetoria; 
		}
	
	// Sets
	public void setTrajetoria(ArrayList<Ponto2D> trajetoria) { 
		this.trajetoria = trajetoria; 
	}

	public void mover(Ponto2D ponto) { 
		trajetoria.add(ponto.clone()); 
		
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\nCAMINHO:\n");
		for(Ponto2D ponto : trajetoria) 
			s.append("POSICAO - " + ponto.toString() + "\n");
		s.append("FIM\n");
		return s.toString();
	}

}