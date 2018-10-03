import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;

public class FileHandler extends DefaultHandler{
	private Graph<Punto,Arista> grafo = new TreeMapGraph<Punto,Arista>(); //Grafo del pueblo.
	private StringBuilder buffer = new StringBuilder(); //Buffer para leer los datos.
	private Punto punto; //Objeto de la clase Punto, el cual representa un nodo.
	private Arista arista; //Objeto de la clase Arista, la cual representa una calle.
	private String tipoData; //Cadena donde guardaremos que tipo de dato estamos leyendo.
	
	public Graph<Punto,Arista> getGrafo() {
		return grafo;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	/*
	 * Método el cual es llamado cuando se reconoce una cadena (texto, entre una cabecera de entrada y de salida) 
	 * mientras se lee el archivo.
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		buffer.append(ch, start, length); //Leemos el texto y lo guardamos en el buffer.
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	/*
	 * Método el cual es llamado cuando se reconoce una cabecera de salida mientras se lee el archivo.
	 * Clasificamos la acción a realizar dependiendo de cual sea el nombre de la cabecera (qName).
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		switch(qName) {
		case "node":
			grafo.insertVertex(punto); //Insertamos en el grafo el nodo que ya hemos leido.
			break;
		case "data":
			switch(tipoData) { //Dependiendo de cual sea el tipo realizamos una acción:
			case "d4": //Almacenamos en el objeto punto la latitud del nodo.
				punto.setLatitud(Double.parseDouble(buffer.toString()));
				break; 
			case "d5": //Almacenamos en el objeto punto la longitud del nodo.
				punto.setLongitud(Double.parseDouble(buffer.toString()));
				break;
			case "d6":
				punto.setOsmid(Long.parseLong(buffer.toString())); //Almacenamos en el objeto punto el identificador osmid del nodo.
				break;
			case "d7":
				arista.setOsmid(Long.parseLong(buffer.toString())); //Almacenamos en el objeto arista el identificador osmid de la calle.
				break;
			case "d8":
				arista.setNombre(buffer.toString()); //Almacenamos en el objeto arista el nombre de la calle.
				break;
			case "d11":
				arista.setLongitud(Long.parseLong(buffer.toString())); //Almacenamos en el objeto arista la longitud de la calle entre los nodos origen y destino.
				break;
			}
			break;
		case "edge":
			Iterator<Vertex<Punto>> vertices = grafo.getVertices();
			Vertex<Punto> origen = null, destino = null, temp;
			while(vertices.hasNext()) { //Buscamos los nodos origen y destino de la calle.
				temp = vertices.next();
				if(temp.getElement().getOsmid() == arista.getOrigen()) {
					origen = temp;
				}
				else if(temp.getElement().getOsmid() == arista.getDestino()) {
					destino = temp;
				}
			}
			if((origen != null && destino != null) && !grafo.areAdjacent(origen, destino)) { //Comprobamos si existen los nodos en el grafo y si no existe una arista ya.
				grafo.insertEdge(origen, destino, arista); //Insertamos en el grafo la calle que ya hemos leido.
			}
			break;
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	/*
	 * Método el cual es llamado cuando se reconoce una cabecera de entrada mientras se lee el archivo.
	 * Clasificamos la acción a realizar dependiendo de cual sea el nombre de la cabecera (qName) y sus atributos (attributes).
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		switch(qName) {
		case "node":
			punto = new Punto(); //Guardamos memoria para el objeto punto.
			break;
		case "data":
			tipoData = attributes.getValue("key"); //Guardamos en el atributo 'tipoData' el tipo de dato a leer.
			buffer.delete(0, buffer.length()); //Eliminamos el contenido del buffer para que no se realicen sobreescrituras.
			break;
		case "edge":
			arista = new Arista();
			arista.setOrigen(Long.parseLong(attributes.getValue("source")));
			arista.setDestino(Long.parseLong(attributes.getValue("target")));
			break;
		}
	}
}

