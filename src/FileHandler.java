import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;

public class FileHandler extends DefaultHandler{
	private Graph grafo = new TreeMapGraph(); //Grafo del pueblo
	private StringBuilder buffer = new StringBuilder(); //Buffer para leer los datos
	private Punto punto; //Objeto de la clase Punto, el cual representa un nodo
	private Arista arista; //Objeto de la clase Arista, la cual representa una calle
	private String tipoData; //Cadena donde guardaremos que tipo de dato estamos leyendo
	
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
		case "graphml": 
			break;
		case "graph":
			break;
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
			}
			break;
		case "edge":
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
		case "graphml": 
			break;
		case "graph":
			break;
		case "node":
			punto = new Punto(); //Guardamos memoria para el objeto punto.
			punto.setOsmid(Long.parseLong(attributes.getValue("id"))); //Almacenamos en el objeto punto el identificador osmid del nodo.
			break;
		case "data":
			tipoData = attributes.getValue("key"); //Guardamos en el atributo 'tipoData' el tipo de dato a leer.
			buffer.delete(0, buffer.length()); //Eliminamos el contenido del buffer para que no se realicen sobreescrituras.
			break;
		case "edge":
			
			break;
		}
	}
}

