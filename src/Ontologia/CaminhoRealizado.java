	package ontologia;

import jade.content.Predicate;


public class CaminhoRealizado implements Predicate{

	// Vari�veis de inst�ncia
	private Caminho caminhoRealizado;
	private int algoritmo;

	// Get
	public Caminho getCaminhoRealizado() {
		return caminhoRealizado;
	}
	
	public int getAlgoritmo() {
		return algoritmo;
	}
	
	// Set
	public void setCaminhoRealizado(Caminho caminho) {
		this.caminhoRealizado = caminho;
	}

	public void setAlgotimo(int algoritmo) {
		this.algoritmo = algoritmo;
	}
}
