package ontologia;

import jade.content.onto.BeanOntology;
import jade.content.onto.Ontology;

public class Ontologia_Trajetoria extends BeanOntology {
	
		public static final String NAME = "Ontologia-Trajetoria";		
		
		// Instância a Ontologia Trajetoria 
		private final static Ontology instancia = new Ontologia_Trajetoria();
		
		// Get Instância
		public final static Ontology getInstance() {
			return instancia;
		}
		
		// Construtor
		private Ontologia_Trajetoria() {
			super(NAME);
			try {
				add(getClass().getPackage().getName());
			} catch (Exception e) {e.printStackTrace();}
		}
	
	}