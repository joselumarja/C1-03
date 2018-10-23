import java.util.ArrayList;

import graphsDSESIUCLM.Graph;

public class EspacioDeEstados {
	private Grafo grafo;
	
	public EspacioDeEstados(String GraphFile) {
		this.grafo = new Grafo(GraphFile);
	}
	
	public ArrayList<Sucesor> sucesores(Estado estado) {
		ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();
		ArrayList<Arista> AristasAdyacentes=grafo.adyacentesNodo(estado.GetNode().getID());
		if(!AristasAdyacentes.isEmpty()) {
			for (Arista a : AristasAdyacentes) {
				Estado nuevoEstado = (Estado)estado.clone();
				nuevoEstado.ChangeState((grafo.getGrafo().getVertex(a.getDestino())).getElement());
				Sucesor sucesor = new Sucesor("Estoy en " + estado.GetNode().getID() + " y voy a "
						+ nuevoEstado.GetNode().getID(), nuevoEstado, a.getLongitud());
				sucesores.add(sucesor);
			}
		}
		return sucesores;
	}
	
	public boolean esta(Estado estado) {
		boolean esta = true;
		if(!grafo.perteneceNodo(estado.GetNode().getID())) esta = false;
		for (String node : estado.getListNodes()) {
			if(!grafo.perteneceNodo(node)) {
				esta = false;
				break;
			}
		}
		return esta;
	}
}

