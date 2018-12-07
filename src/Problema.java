import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * Clase Problema la cual representa el problema planteado con un espacio de estados y 
 * un estado inicial los cuales son leidos a traves de un metodo que lee un archivo .json, 
 * ademas tiene una lista de visitados para controlar los nodos que se tienen que generar 
 * en la busqueda con metodos que manejan dicha lista
 */
public class Problema {
	private EspacioDeEstados espacioDeEstados; // Espacio de estados del problema
	private Estado estadoInicial; // Estado inicial por el que empieza el problema
	private ArrayList<Nodo> recorridos; // Lista de nodos que ya se han visitado
	private int NodosGenerados; // Numero de nodos que ya se han generado

	public Problema(String archivoProblema) {
		leerProblema(archivoProblema);
		recorridos = new ArrayList<Nodo>();
		NodosGenerados = 0;
	}

	/*
	 * Metodo que lee el problema que se nos plantea en el archivo .json Almacena el
	 * espacio de estados leyendo el archivo .graphml correspondiente y el estado
	 * inicial en el cual debemos empezar el problema
	 */
	public void leerProblema(String archivoProblema) {
		// Guardamos el contenido del archivo .json en un StringBuilder para su lectura
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("./" + archivoProblema),
				Charset.defaultCharset())) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Creamos el parseador para leer el contenido del fichero
		JsonParser parser = new JsonParser();
		// Almacenamos el JsonObjeto del problema
		JsonObject problema = parser.parse(content.toString()).getAsJsonObject();
		// Almacenamos el JsonObjeto del estado inicial
		JsonObject estadoOrigen = problema.get("IntSt").getAsJsonObject();

		// Guardamos el path con el nombre del archivo .graphml a leer (se obtiene del
		// JsonObjeto del problema)
		String path = problema.get("graphlmfile").getAsString();
		// Guardamos el osmid del nodo inicial (se obtiene del JsonObjeto del estado
		// inicial)
		String node = estadoOrigen.get("node").getAsString();
		// Guardamos el identificador del estado en MD5 del estado inicial (se obtiene
		// del JsonObjeto del estado del estado inicial)
		String id = estadoOrigen.get("id").getAsString();

		// Creamos el espacio de estados a partir del nombre del archivo .graphml a leer
		this.espacioDeEstados = new EspacioDeEstados(path);

		// Guardamos la lista de nodos que debemos visitar en el problema planteado (se
		// obtiene del JsonObjeto del estado inicial)
		JsonArray lista = estadoOrigen.get("listNodes").getAsJsonArray();
		ArrayList<Punto> list = new ArrayList<Punto>();
		Punto p;
		for (JsonElement nodo : lista) {
			p = espacioDeEstados.getPunto(nodo.getAsString());
			if (p != null) {
				list.add(p);
			}

		}
		// Creamos el estado inicial a partir del nodo inicial, la lista de nodos por
		// visitar y el identificador del estado en MD5
		this.estadoInicial = new Estado(this.espacioDeEstados.getGrafo().getGrafo().getVertex(node).getElement(), list,
				id);
	}

	/*
	 * Metodo que comprueba si un estado pasado como parametro es objetivo o no.
	 * Devuelve 'true' si la lista de nodos esta vacia o 'false' si hay algun
	 * elemento aun
	 */
	public boolean esObjetivo(Estado e) {
		return e.getListNodes().isEmpty();
	}

	/*
	 * Metodo que incrementa el numero de nodos generados en funcion del valor
	 * pasado como parametro
	 */
	public void IncrementarGenerados(int Incremento) {
		NodosGenerados += Incremento;
	}

	/*
	 * Metodo get que devuelve el numero de nodos generados
	 */
	public int GetGenerados() {
		return NodosGenerados;
	}

	/*
	 * Metodo que añade un nodo a la lista de nodos vistados en el caso de que no se
	 * encuentre aun o en el caso de que ya se encuentre un nodo con el mismo estado
	 * pero que sea peor que el nodo que se quiere añadir
	 */
	public boolean anadirVisitado(Nodo nodo, TipoDeBusqueda TBusqueda) {

		for (Nodo rec : recorridos) {
			if (rec.GetEstado().GetId().equals(nodo.GetEstado().GetId())) {

				switch (TBusqueda) {
				case BusquedaEnAnchura:
				case BusquedaEnProfundidad:
					return false;

				case BusquedaDeCostoUniforme:
				case BusquedaVoraz:
				case BusquedaAAsterisco:
					if (rec.GetCamino() <= nodo.GetCamino()) {
						
						return false;
					}else{
						//Descartamos el camino a dicho nodo por ser mas largo que el nuevo
						rec.PodarCamino();
					}
					break;
				}
			}
		}

		recorridos.add(nodo);

		return true;
	}

	/*
	 * Metodo get que devuelve el espacio de estados del problema leido del archivo
	 * .json
	 */
	public EspacioDeEstados getEspacioDeEstados() {
		return espacioDeEstados;
	}

	/*
	 * Metodo get que devuelve el estado inicial del problema leido del archivo
	 * .json
	 */
	public Estado getEstadoIn() {
		return estadoInicial;
	}

	/*
	 * Metodo que comprueba si un nodo se encuentra en la lista de visitados
	 * pasandole el identificador MD5 del nodo. Devuelve 'true' si se encuentra y
	 * 'false' si no se encuentra
	 */
	public boolean EstaVisitado(String id) {
		for (Nodo x : recorridos) {
			if (id.equals(x.GetEstado().GetId()))
				return true;
		}
		return false;
	}

	/*
	 * Metodo que limpia la lista de visitados inicializandola a 0
	 */
	public void LimpiarVisitados() {
		recorridos = new ArrayList<Nodo>();
	}

	/**
	 * Metodo get que devuelve la lista de nodos visitados/recorridos
	 */
	public ArrayList<Nodo> getRecorridos() {
		return recorridos;
	}

}
