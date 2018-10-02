import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;

public class Grafo {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); //Factoria para instanciar el SAXParser.
		SAXParser saxParser = saxParserFactory.newSAXParser(); //Parseador para leer el archivo .graphml(XML).
		File file = new File("Villar del Pozo.graphml"); //Archivo .graphml.
		FileHandler handler = new FileHandler(); //Manejador del archivo.
		saxParser.parse(file, handler); //Llamada al parse para leer el archivo .graphml mediante un manejador.
		
		Graph grafo = handler.getGrafo(); //Obtenemos el grafo leido en el archivo .graphml.
	}
}
