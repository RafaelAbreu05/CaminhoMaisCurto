package agentes;

import interacao.JFramePrincipal;
import jade.content.ContentException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import ontologia.Caminho;
import ontologia.CaminhoRealizado;
import ontologia.Ontologia_Trajetoria;
import ontologia.Ponto2D;
import terreno.Terreno;
import algoritmoAStar.AStar;
import algoritmoAStar.AStarHeuristicas;
import algoritmoAStar.AreaMapa;
import algoritmoAStar.HeuristicaProxima;
import algoritmoAStar.Registador;

public class Comportamento_Caminhante extends TickerBehaviour {

	// Variáveis de Instância
	private Caminhante caminhante;
	private int algoritmo;
	private boolean fim = false;
	private long velocidade;
	private int tipo;

	// Contrutor por parâmetros
	public Comportamento_Caminhante(Agent a, long velocidade, int algoritmo,
			int tipo) {
		super(a, velocidade);
		caminhante = (Caminhante) a;
		this.algoritmo = algoritmo;
		this.velocidade = velocidade;
		this.tipo = tipo;
	}

	// Comportamento periodico consoante um periodo de tempo (Ticker Behavior)
	protected void onTick() {

		JFramePrincipal.end = false;

		// Se atinguiu o fim
		if (fim) {
			CaminhoRealizado caminhoRealizado = new CaminhoRealizado();
			// recupera o caminho realizado do caminhante
			caminhoRealizado.setCaminhoRealizado(caminhante.getCaminho());

			// Cria uma mensagem ACL
			ACLMessage cfp = new ACLMessage(ACLMessage.INFORM);
			// Utilizando a Ontologia_Trajetoria
			cfp.setOntology(Ontologia_Trajetoria.getInstance().getName());
			cfp.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
			try {
				// O caminhante coloca na mensagem ACL com caminho realizado
				myAgent.getContentManager().fillContent(cfp, caminhoRealizado);
				// Coloca o SuperVisor como destinatário da mensagem
				cfp.addReceiver(new AID("SuperVisor", AID.ISLOCALNAME));
				// Envia a mensagem
				myAgent.send(cfp);
			} catch (ContentException ce) {
				ce.printStackTrace();
			}
			JFramePrincipal.end = true;
			stop();
		} else {
			switch (algoritmo) {
			// Algoritmo A-Star
			case 1: {
				Caminho c = aStar();
				caminhante.setCaminho(c);
				fim = true;
				break;
			}
			// Algoritmo Dijkstra
			case 2:
				break;
			default:
				break;
			}
		}
	}

	public Caminho aStar() {
		Caminho caminhoMaisCurto = new Caminho();
		for (int i = 0; i <= (Terreno.listaPontos.size() - 2); i++) {

			Registador log = new Registador();

			log.addToLog("Inicializar o Mapa...");
			AreaMapa mapa = new AreaMapa(Terreno.linhas, Terreno.colunas,
					Terreno.matriz, tipo);

			log.addToLog("Inicializar Heuristicas...");
			AStarHeuristicas heuristica = new HeuristicaProxima();

			log.addToLog("Inicializar a pesquisa...");
			AStar pathFinder = new AStar(mapa, heuristica);

			log.addToLog("Inicializar calculo do caminho mais curto...");
			int inicioX = Terreno.listaPontos.get(i).getX();
			int inicioY = Terreno.listaPontos.get(i).getY();

			int fimX = Terreno.listaPontos.get(i + 1).getX();
			int fimY = Terreno.listaPontos.get(i + 1).getY();

			pathFinder.calcShortestPath(inicioX, inicioY, fimX, fimY);

			log.addToLog("Desenhar no terreno o caminho mais curto...");
			Caminho caminho = pathFinder.getCaminhoMaisCurto(velocidade);

			for (int j = 0; j < caminho.getTrajetoria().size(); j++) {
				Ponto2D ponto = new Ponto2D();
				ponto = caminho.getTrajetoria().get(j);
				caminhoMaisCurto.getTrajetoria().add(ponto);
			}
		}
		return caminhoMaisCurto;
	}
}
