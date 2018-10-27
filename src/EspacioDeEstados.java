import java.util.ArrayList;

import graphsDSESIUCLM.Graph;

public class EspacioDeEstados {
	private Grafo grafo;
	
	public EspacioDeEstados(String GraphFile) {
		this.grafo = new Grafo(GraphFile);
	}
	
	public Grafo getGrafo() {
		return grafo;
	}
	
	/*
	 * Metodo que devuelve una lista con todos los sucesores (cada sucesor estara
	 * representado por un String indicando la accion a realizar, el estado nuevo al
	 * que llegariamos y el coste de dicha acción) posibles a partir de un estado
	 * actual
	 */
	public ArrayList<Sucesor> sucesores(Estado estado) {
		ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();
		//Obtenemos todos los caminos adyacentes al punto del estado acutal
		ArrayList<Arista> AristasAdyacentes=grafo.adyacentesNodo(estado.GetNode().getID());
		for (Arista a : AristasAdyacentes) { //Recorremos todas las aristas adyacentes al nodo del estado actual
			Estado nuevoEstado = (Estado) estado.clone(); //Almacenamos el estado acutal en un nuevo objeto para poder modificarlo sin perder los datos
			nuevoEstado.ChangeState((grafo.getGrafo().getVertex(a.getDestino())).getElement()); //Cambiamos el estado acutal al nuevo estado
			//Creamos un objeto sucesor con los datos necesarios
			Sucesor sucesor = new Sucesor("Estoy en " + estado.GetNode().getID() + " y voy a " + nuevoEstado.GetNode().getID(), nuevoEstado,
					a.getLongitud());
			sucesores.add(sucesor); //Insertamos un sucesor del estado acutal en la lista de sucesores
		}
		return sucesores;
	}
	
	/*
	 * Metodo que devuelve si un estado cualquiera es posible en el espacio de estados
	 * Devuelve 'true' si es posible y 'false' en caso contrario
	 */
	public boolean esta(Estado estado) {
		boolean esta = true;
		//Comprueba si un nodo de un estado pertenece al grafo
		if(!grafo.perteneceNodo(estado.GetNode().getID())) esta = false;
		//Comprueba si los nodos de la lista de nodos que hay que visitar se encuentran en el grafo
		for (String node : estado.getListNodes()) {
			if(!grafo.perteneceNodo(node)) {
				esta = false;
				break;
			}
		}
		return esta;
	}
}
