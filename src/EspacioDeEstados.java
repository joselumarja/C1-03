import java.util.ArrayList;

/*
 * Clase EspacioDeEstados que representa todos los posibles estados a traves de un 
 * grafo y ademas posee un metodo para comprobar si un estado es posible en este 
 * espacio de estados.
 */

public class EspacioDeEstados {
	private Grafo grafo; // Grafo que representa el mapa del pueblo (puntos y aristas)

	/*
	 * Constructor de la clase EspacioDeEstados la cual crea una instancia de dicha
	 * clase. El atributo grafo pasa a ser un objeto de la clase Grafo creandose el
	 * grafo que representa el mapa del pueblos.
	 */
	public EspacioDeEstados(String GraphFile) {
		this.grafo = new Grafo(GraphFile);
	}

	/*
	 * Metodo get que devuelve el grafo del espacio de estados que representa el
	 * mapa del pueblo.
	 */
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
		// Obtenemos todos los caminos adyacentes por los que podemos ir a partir del
		// estado acutal
		ArrayList<Arista> AristasAdyacentes = grafo.adyacentesNodo(estado.GetNode().getID());
		Estado nuevoEstado;
		// Recorremos todas las aristas adyacentes al estado actual
		for (Arista a : AristasAdyacentes) {
			// Creamos un objeto de Estado que representa el nuevo estado al cual llegaremos
			// si elegimos la arista 'a'
			nuevoEstado = new Estado(estado.GetNode(), new ArrayList<Punto>(estado.getListNodes()), estado.GetId());
			// Actualizamos el nuevo estado con el punto destino, la nueva lista de nodos
			// (si se ha llegado a un nodo por visitar se debe eliminar de dicha lista) y el
			// nuevo codigo MD5.
			nuevoEstado.ChangeState((grafo.getGrafo().getVertex(a.getDestino())).getElement());
			// Creamos un objeto de la clase Sucesor con los datos necesarios.
			Sucesor sucesor = new Sucesor(
					estado.GetNode().getID() + " -> " + nuevoEstado.GetNode().getID() + " (" + a.getNombre() + ") ",
					nuevoEstado, a.getLongitud());
			// Insertamos un sucesor del estado acutal en la lista de sucesores.
			sucesores.add(sucesor);
		}
		return sucesores;
	}

	/*
	 * Metodo que devuelve si un estado cualquiera es posible en el espacio de
	 * estados. Devuelve 'true' si es posible y 'false' en caso contrario
	 */
	public boolean esta(Estado estado) {
		boolean esta = true;
		// Comprueba si un nodo de un estado pertenece al grafo (Si no pertenece el
		// estado no es posible y por lo tanto devolvera 'false').
		if (!grafo.perteneceNodo(estado.GetNode().getID()))
			esta = false;
		// Comprueba si los nodos de la lista de nodos que hay que visitar se encuentran
		// en el grafo (si alguno de ellos no pertenece al grafo, el estado no es
		// posible y se devolvera 'false').
		for (Punto node : estado.getListNodes()) {
			if (!grafo.perteneceNodo(node.getID())) {
				esta = false;
				break;
			}
		}
		return esta;
	}

	/*
	 * Metodo que devuelve una instancia de la clase Punto el cual representa el
	 * punto con el osmid pasado como parametro. Devolvera null si no existe en el
	 * grafo.
	 */
	public Punto getPunto(String osmid) {
		return grafo.posicionNodo(osmid);
	}
}
