import java.io.File;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.Vertex;

public class Grafo{
	
	private Graph<Punto,Arista> grafo;
	
	/*
	 * Constructor que tiene como parametro el nombre del archivo que se desea leer.
	 * El constructor inicializa el grafo.
	 */
	public Grafo(String GraphFile) {
		initialize(GraphFile);
	}
	
	/**
	 * @return the grafo
	 */
	public Graph<Punto, Arista> getGrafo() {
		return grafo;
	}

	/*
	 * Funcionalidad 1: Comprueba si el identificador de un nodo pertenece al grafo creado del pueblo.
	 * Devuelve true si existe y false en caso contrario.
	 */
	public boolean perteneceNodo(String osmId) {
		Iterator<Vertex<Punto>> nodos=grafo.getVertices(); //Obtenemos todos los vertices del grafo.
		
		while(nodos.hasNext()) {
			if(nodos.next().getElement().getID().equals(osmId)) { //Comprobamos si es el nodo solicitado.
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Funcionalidad 2: Nos da la longitud y latitud de un nodo del grafo.
	 * Devuelve un objeto de la clase Punto en el caso de que el identificador exista en el
	 * grafo del pueblo, en caso contrario devolvera un null.
	 */
	public Punto posicionNodo(String osmId) {
		Iterator<Vertex<Punto>> nodos=grafo.getVertices(); //Obtenemos todos los vertices de un grafo.
		Punto p=null;
		while(nodos.hasNext()) {
			
			p=nodos.next().getElement();
			if(p.getID().equals(osmId)) { //Comprobamos si es el nodo solicitado.
				return p;
			}
		}
		
		return null;
	}
	
	/*
	 * Funcionalidad 3: Nos da todas las calles adyacentes de un nodo cualquiera del grafo,
	 * es decir, todas aquellas que tengan como nodo origen al que se pide.
	 * Devuelve un ArrayList de Arista con todas las calles adyacentes del nodo pedido.
	 */
	public ArrayList<Arista> adyacentesNodo(String osmId) {
		ArrayList<Arista> AristasAdyacentes=new ArrayList<Arista>();
		
		Iterator<Edge<Arista>> aristas=grafo.getEdges(); //Obtenemos todas las aristas de un grafo.
		Arista a=null;
		
		while(aristas.hasNext()) {
			a=aristas.next().getElement();
			
			if(a.IsAdyacentTo(osmId)) { //Comprobamos si la arista 'a' es adyacente al nodo con identifiador osmId y si este es origen.
				AristasAdyacentes.add(a);
			}
		}
		return AristasAdyacentes;
	}
	
	/*
	 * Inicializa el grafo con el archivo que se pasa como parametro.
	 */
	private void initialize(String GraphFile){
		
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); //Factoria para instanciar el SAXParser.
			SAXParser saxParser = saxParserFactory.newSAXParser(); //Parseador para leer el archivo .graphml(XML).
			File file = new File(GraphFile); //Archivo .graphml.
			FileHandler handler = new FileHandler(); //Manejador del archivo.
			saxParser.parse(file, handler); //Llamada al parse para leer el archivo .graphml mediante un manejador.
			grafo = handler.getGrafo(); //Obtenemos el grafo leido en el archivo .graphml.
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
	

