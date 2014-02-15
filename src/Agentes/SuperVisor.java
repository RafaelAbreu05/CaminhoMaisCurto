package agentes;

import javax.swing.JOptionPane;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ontologia.Caminho;
import ontologia.CaminhoRealizado;
import ontologia.Ontologia_Trajetoria;

public class SuperVisor extends Agent {

	// private int velocidade; // Velocidade ao desenhar
	private Caminho caminhoRealizadoAlg1 = new Caminho(); // Caminho realizado
															// pelo caminhante
															// utilizando o 1º
															// algoritmo

	// private int alg; // Identificação do 2º algoritmo a comparar
	// private Caminho caminhoRealizadoAlg2 = new Caminho(); // Caminho
	// realizado pelo caminhante utilizando o 1º algoritmo

	protected void setup() {

		getContentManager().registerLanguage(new SLCodec());
		getContentManager()
				.registerOntology(Ontologia_Trajetoria.getInstance());

		/*
		 * Object[] args = getArguments(); if(args != null){ velocidade =
		 * Integer.parseInt(args[0].toString()); alg =
		 * Integer.parseInt(args[2].toString()); }
		 */

		// Adicionar um CyclicBehaviour()
		addBehaviour(new CyclicBehaviour(this) {

			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					try {
						CaminhoRealizado caminhoRealizadoAlg1 = (CaminhoRealizado) myAgent
								.getContentManager().extractContent(msg);

						System.out.printf("Número de passos efetuados: "
								+ caminhoRealizadoAlg1.getCaminhoRealizado()
										.getTrajetoria().size());
						JOptionPane.showMessageDialog(null,
								"Número de passos efetuados: "
										+ caminhoRealizadoAlg1
												.getCaminhoRealizado()
												.getTrajetoria().size());

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else
					block();
			}
		});
	}

	// To String
	public String toString() {
		return "Supervisor [Caminho Realizado " + caminhoRealizadoAlg1 + "]\n";
	}

	// Fim do SuperVisor
	protected void takeDown() {
		System.out.println("Supervisor acabou!");
	}

}