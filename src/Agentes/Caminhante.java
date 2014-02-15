package agentes;

import interacao.JFramePrincipal;
import jade.core.Agent;
import ontologia.*;
import jade.content.lang.sl.SLCodec;


public class Caminhante extends Agent {

	private Caminho caminhoAgente = new Caminho();
	private int velocidade = 5;
	/*
	 * Algoritmos caminho mais curto entre dois pontos
	 * 1 - A* (A-Star)
	 * 2 - Dijkstra
	 *
	 * */
	private int algoritmo = 1; // por defeito fica o Algoritmo A*


	/*
	 * Tipo de Caminhante
	 * 1 - Pessoa sem Acesso Restrito
	 * 2 - Pessoa com Acesso Restrito
	 * 3 - Defeciente Motor sem Acesso Restrito
	 * 4 - Defeciente Motor com Acesso Restrito
	*/
	private int tipo = 1;


	protected void setup(){

		JFramePrincipal.end = false;

		// registar ontologia
		getContentManager().registerLanguage(new SLCodec());
		getContentManager().registerOntology(Ontologia_Trajetoria.getInstance());

		// leitura dos argumentos
		Object[] args = getArguments();
		if(args != null){
			velocidade = Integer.parseInt(args[0].toString());
			algoritmo = Integer.parseInt(args[1].toString());
			tipo = Integer.parseInt(args[2].toString());
		}

		addBehaviour(new Comportamento_Caminhante(this, velocidade, algoritmo, tipo));
	}

	// Gets
	public Caminho getCaminho() {
		return caminhoAgente;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public int getAlgoritmo() {
		return algoritmo;
	}

	public int getTipo() {
		return tipo;
	}

	// Sets
	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public void setCaminho(Caminho caminho) {
		caminhoAgente = caminho;
	}

	public void setAlgoritmo(int algoritmo) {
		this.algoritmo = algoritmo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	// Fim do agente
	protected void takeDown(){
		System.out.println("Xau, já descobri o caminho mais curto!");
	}

}
