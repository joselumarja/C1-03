import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;

public class Grafo{
	
	private Graph<Punto,Arista> grafo;
	
	public Grafo(String GraphFile) {
		initialize(GraphFile);
	}
	
	public boolean perteneceNodo(long osmId) {
		Iterator<Vertex<Punto>> nodos=grafo.getVertices();
		
		while(nodos.hasNext()) {
			if(nodos.next().getElement().getOsmid()==osmId) {
				
				return true;
			}
		}
		return false;
	}
	
	public Punto posicionNodo(long osmId) {
		Iterator<Vertex<Punto>> nodos=grafo.getVertices(); 
		Punto p=null;
		while(nodos.hasNext()) {
			
			p=nodos.next().getElement();
			if(p.getOsmid()==osmId) {
				return p;
			}
		}
		
		return null;
	}
	
	public ArrayList<Arista> adyacentesNodo(long osmId) {
		ArrayList<Arista> AristasAdyacentes=new ArrayList<Arista>();
		
		Iterator<Edge<Arista>> aristas=grafo.getEdges();
		Arista a=null;
		
		while(aristas.hasNext()) {
			a=aristas.next().getElement();
			
			if(a.IsAdyacentTo(osmId)) {
				AristasAdyacentes.add(a);
			}
		}
		return AristasAdyacentes;
	}
	
	private void initialize(String GraphFile){
		
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); //Factoria para instanciar el SAXParser.
			SAXParser saxParser = saxParserFactory.newSAXParser(); //Parseador para leer el archivo .graphml(XML).
			File file = new File(GraphFile); //Archivo .graphml.
			FileHandler handler = new FileHandler(); //Manejador del archivo.
			saxParser.parse(file, handler); //Llamada al parse para leer el archivo .graphml mediante un manejador.
			grafo = handler.getGrafo(); //Obtenemos el grafo leido en el archivo .graphml.
		
			file.delete();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
	

