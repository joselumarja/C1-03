import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;

/*
 * Clase FileHandler que sirve para leer el archivo .graphml y crear el grafo del mapa del pueblo con todos 
 * los puntos y aristas que lo unen.
 */
public class FileHandler extends DefaultHandler{
	private Graph<Punto,Arista> grafo = new TreeMapGraph<Punto,Arista>(); //Grafo del pueblo.
	private StringBuilder buffer = new StringBuilder(); //Buffer para leer los datos.
	private Punto punto; //Objeto de la clase Punto, el cual representa un nodo.
	private Arista arista; //Objeto de la clase Arista, la cual representa una calle.
	private String tipoData; //Cadena donde guardaremos que tipo de dato estamos leyendo.
	private String nombre_d, latitud_d, longitud_d, n_osmid_d, e_osmid_d, distancia_d, ref_d;
	
	/*
	 * Metodo get que devuelve el grafo del mapa del pueblo con los puntos y aristas que lo unen.
	 */
	public Graph<Punto,Arista> getGrafo() {
		return grafo;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	/*
	 * Metodo el cual es llamado cuando se reconoce una cadena (texto, entre una cabecera de entrada y de salida) 
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
	 * Metodo el cual es llamado cuando se reconoce una cabecera de salida mientras se lee el archivo.
	 * Clasificamos la accion a realizar dependiendo de cual sea el nombre de la cabecera (qName).
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		switch(qName) {
		case "node":
			grafo.insertVertex(punto); //Insertamos en el grafo el nodo que ya hemos leido.
			break;
		case "data":
			if(tipoData.equals(latitud_d)) {
				punto.setLatitud(Double.parseDouble(buffer.toString()));//Almacenamos en el objeto punto la latitud del nodo.
			}
			else if(tipoData.equals(longitud_d)) {
				punto.setLongitud(Double.parseDouble(buffer.toString()));//Almacenamos en el objeto punto la longitud del nodo.
			}
			else if(tipoData.equals(n_osmid_d)) {
				punto.setOsmid(buffer.toString());//Almacenamos en el objeto punto el identificador osmid del nodo.
			}
			else if(tipoData.equals(e_osmid_d)) {
				if (buffer.toString().charAt(0) == '[') { //En el caso de que haya varios cogeremos solo el primero.
					String [] parts = buffer.toString().substring(1, buffer.toString().length() - 1).split(",");
					arista.setOsmid(parts[0]);
				}
				else arista.setOsmid(buffer.toString());
			}
			else if((tipoData.equals(nombre_d)) || ((tipoData.equals(ref_d)))) {
				if (buffer.toString().charAt(0) == '[') //En el caso de que haya varios cogeremos la cadena entera
					//representado el camino a realizar.
					arista.setNombre(buffer.toString().substring(1, buffer.toString().length() - 1));
				else arista.setNombre(buffer.toString());//Almacenamos en el objeto arista el identificador osmid de la calle.
			}
			else if(tipoData.equals(distancia_d)) {
				arista.setLongitud(Double.parseDouble(buffer.toString()));//Almacenamos en el objeto arista la longitud de la calle entre los nodos origen y destino.
			}
			break;
		case "edge":
			if(arista.getNombre() == null) //Si en el archivo no se ha especificado el nombre de una arista se le pondra 'Sin Nombre'.
				arista.setNombre("Sin Nombre");
			Iterator<Vertex<Punto>> vertices = grafo.getVertices();
			Vertex<Punto> origen = null, destino = null, temp;
			while(vertices.hasNext()) { //Buscamos los nodos origen y destino de la calle.
				temp = vertices.next();
				if(temp.getElement().getID().equals(arista.getOrigen())) {
					origen = temp;
				}
				else if(temp.getElement().getID().equals(arista.getDestino())) {
					destino = temp;
				}
			}
			if(origen != null && destino != null) { //Comprobamos si existen los nodos en el grafo y si no existe una arista ya.
				grafo.insertEdge(origen, destino, arista); //Insertamos en el grafo la calle que ya hemos leido.
			}
			break;
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	/*
	 * Metodo el cual es llamado cuando se reconoce una cabecera de entrada mientras se lee el archivo.
	 * Clasificamos la acci�n a realizar dependiendo de cual sea el nombre de la cabecera (qName) y sus atributos (attributes).
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		switch(qName) {
		case "key": // A traves de la etiqueta key se obtiene los valores de las otras etiquetas
			String val_atrib = attributes.getValue("attr.name");
			String uso = attributes.getValue("for");
			
			switch(val_atrib) {
			case "name"  :
				if(uso.equals("edge")) {
					nombre_d = attributes.getValue("id");
				}
				break;
			case "x":
				longitud_d = attributes.getValue("id");
				break;
			case "y":
				latitud_d = attributes.getValue("id");
				break;
			case "osmid":
				if(uso.equals("node")) {
					n_osmid_d = attributes.getValue("id");
				}else {
					e_osmid_d = attributes.getValue("id");
				}
				break;
			case "length":
				distancia_d = attributes.getValue("id");
				break;
			case "ref":
				if(uso.equals("edge")) {
					ref_d = attributes.getValue("id");
				}
				break;
			}
			break;
		case "node":
			punto = new Punto(); //Guardamos memoria para el objeto punto.
			break;
		case "data":
			tipoData = attributes.getValue("key"); //Guardamos en el atributo 'tipoData' el tipo de dato a leer.
			buffer.delete(0, buffer.length()); //Eliminamos el contenido del buffer para que no se realicen sobreescrituras.
			break;
		case "edge":
			arista = new Arista();
			arista.setOrigen(attributes.getValue("source")); //Guardamos en el atributo 'origen' el osmId del nodo origen de una arista.
			arista.setDestino(attributes.getValue("target")); //Guardamos en el atributo 'destino' el osmId del nodo destino de una arista.
			break;
		}
	}
}

