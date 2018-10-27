import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Problema {
	private EspacioDeEstados espacioDeEstados; //Espacio de estados del problema
	private Estado estadoInicial; //Estado inicial por el que empieza el problema
	private ArrayList<String> recorridos = new ArrayList <String>();
	
	public Problema() {
		leerProblema();
	}
	
	/*
	 * Lee el problema que se nos plantea en el archivo .json
	 * Almacena el espacio de estados leyendo el archivo .graphml 
	 * correspondiente y el estado inicial en el cual debemos empezar el problema
	 */
	public void leerProblema() {
		//Guardamos el contenido del archivo .json en un StringBuilder para su lectura
		StringBuilder content = new StringBuilder();
		try(BufferedReader reader = Files.newBufferedReader(Paths.get("./problema.json"), Charset.defaultCharset())) {
			String line = null;
			while((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		//Creamos el parseador para leer el contenido del fichero
		JsonParser parser = new JsonParser();
		//Almacenamos el JsonObjeto del problema
		JsonObject problema = parser.parse(content.toString()).getAsJsonObject();
		//Almacenamos el JsonObjeto del estado inicial
		JsonObject estadoOrigen = problema.get("IntSt").getAsJsonObject();
		
		//Guardamos el path con el nombre del archivo .graphml a leer (se obtiene del JsonObjeto del problema)
		String path = problema.get("graphlmfile").getAsString();
		//Guardamos el osmid del nodo inicial (se obtiene del JsonObjeto del estado inicial)
		String node = estadoOrigen.get("node").getAsString();
		//Guardamos el identificador del estado en MD5 del estado inicial (se obtiene del JsonObjeto del estado del estado inicial)
		String id = estadoOrigen.get("id").getAsString();		
		
		//Guardamos la lista de nodos que debemos visitar en el problema planteado (se obtiene del JsonObjeto del estado inicial)
		JsonArray lista = estadoOrigen.get("listNodes").getAsJsonArray();
		ArrayList<String> list = new ArrayList<String>();
		for (JsonElement nodo : lista) {
            list.add(nodo.getAsString());
        }
		path = path + ".xml";
		this.espacioDeEstados = new EspacioDeEstados(path); //Creamos el espacio de estados a partir del nombre del archivo .graphml a leer
		//Creamos el estado inicial a partir del nodo inicial, la lista de nodos por visitar y el identificador del estado en MD5
		this.estadoInicial = new Estado(this.espacioDeEstados.getGrafo().getGrafo().getVertex(node).getElement(), list, id);
	}
	
	public boolean esObjetivo(Estado e) {
		return e.getListNodes().isEmpty();
	}
	
	public boolean esVisitado(Nodo nodo) { //Metodo que comprueba si un nodo ya ha sido visitado
		boolean visitado;
		if(!recorridos.contains(nodo.GetEstado().GetNode().getID())) {
			visitado = false;
		}else {
			visitado = true;
		}
		return visitado;
	}
	
	public void añadirVisitado(Nodo nodo) { //Metodo que añade un nodo a la lista de nodos visitados
		recorridos.add(nodo.GetEstado().GetNode().getID());
	}
	
	public EspacioDeEstados getEspacioDeEstados() {
		return espacioDeEstados;
	}
	
	public Estado getEstadoIn() {
		return estadoInicial;
	}
}
