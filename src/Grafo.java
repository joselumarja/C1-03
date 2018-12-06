import java.io.File;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.Vertex;

/*
 * Clase Grafo que crea y representa el mapa del pueblo con todos los nodos y todas 
 * las calles que unen esos puntos en un grafo. Además nos ofrece metodos para conocer 
 * nodos y aristas de este grafo 
 */

public class Grafo {
	// Grafo de puntos y aristas que representa el mapa del pueblo con todos los
	// nodos y calles
	private Graph<Punto, Arista> grafo;

	/*
	 * Constructor de la clase Grafo la cual crea una instancia de dicha clase
	 * inicializando el atributo grafo con el nombre del archivo .graphml pasado
	 * como parametro (el cual contiene todos los datos para crear el grafo)
	 */
	public Grafo(String GraphFile) {
		initialize(GraphFile);
	}

	/**
	 * Metodo get que devuelve el grafo del mapa del pueblo
	 */
	public Graph<Punto, Arista> getGrafo() {
		return grafo;
	}

	/*
	 * Metodo que comprueba si el identificador de un nodo pertenece al grafo creado
	 * del pueblo. Devuelve 'true' si existe y 'false' en caso contrario.
	 */
	public boolean perteneceNodo(String osmId) {
		// Obtenemos todos los vertices del grafo.
		Iterator<Vertex<Punto>> nodos = grafo.getVertices();
		// Recorremos todos los vertices del grafo buscando el solicitado
		while (nodos.hasNext()) {
			// Comprobamos si es el nodo solicitado.
			if (nodos.next().getElement().getID().equals(osmId)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Metodo que devuelve un objeto Punto con la longitud y latitud del
	 * identificador de un nodo pasado como parametro, en caso contrario devolvera
	 * un null.
	 */
	public Punto posicionNodo(String osmId) {
		// Obtenemos todos los vertices de un grafo.
		Iterator<Vertex<Punto>> nodos = grafo.getVertices();
		Punto p = null;
		// Recorremos todos los vertices del grafo
		while (nodos.hasNext()) {
			p = nodos.next().getElement();
			// Comprobamos si es el nodo solicitado.
			if (p.getID().equals(osmId)) {
				return p;
			}
		}
		return null;
	}

	/*
	 * Metodo que nos da todas las calles adyacentes de un nodo cualquiera del
	 * grafo, es decir, todas aquellas que tengan como nodo origen al que se pide.
	 * Devuelve un ArrayList de Arista con todas las calles adyacentes del nodo con
	 * el identificador que se pasa como parametro.
	 */
	public ArrayList<Arista> adyacentesNodo(String osmId) {
		ArrayList<Arista> AristasAdyacentes = new ArrayList<Arista>();
		// Obtenemos todas las aristas de un grafo.
		Iterator<Edge<Arista>> aristas = grafo.getEdges();
		Arista a = null;
		// Recorremos todas las aristas del grafo
		while (aristas.hasNext()) {
			a = aristas.next().getElement();
			// Comprobamos si la arista 'a' es adyacente al nodo con identifiador osmId y si
			// este es origen.
			if (a.IsAdyacentTo(osmId)) {
				// Añadimos dicha arista a la lista de aristas adyacentes
				AristasAdyacentes.add(a);
			}
		}
		return AristasAdyacentes;
	}

	/*
	 * Metodo que inicializa el grafo del pueblo con el archivo .graphml que se pasa
	 * como parametro.
	 */
	private void initialize(String GraphFile) {
		try {
			// Factoria para instanciar el SAXParser.
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			// Parseador para leer el archivo .graphml(XML).
			SAXParser saxParser = saxParserFactory.newSAXParser();
			// Archivo .graphml.
			File file = new File(GraphFile);
			// Manejador del archivo.
			FileHandler handler = new FileHandler();
			// Llamada al parse para leer el archivo .graphml mediante un manejador.
			saxParser.parse(file, handler);
			// Obtenemos el grafo leido en el archivo .graphml.
			grafo = handler.getGrafo();
		} catch (Exception e) {
			// Capturamos las posibles excepciones que se puedan producir al leer el archivo
			System.out.println(e.getMessage());
		}

	}

}
