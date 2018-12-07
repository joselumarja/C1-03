import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.hs.gpxparser.GPXWriter;
import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.Track;
import com.hs.gpxparser.modal.TrackSegment;
import com.hs.gpxparser.modal.Waypoint;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * Clase main la cual contiene el metodo main que inicia la ejecucion del programa. 
 * Contiene un metodo para resolver el problema, otro para imprimir la solucion y otro 
 * que crea un archivo .gpx de la solucion
 */
public class main {
	// Objeto de la clase Scanner que se utiliza para leer por teclado
	private static Scanner sc = new Scanner(System.in);

	/*
	 * Metodo main que inicia la ejecucion del programa
	 */
	public static void main(String args[]) {
		// Se pide por teclado el archivo .json donde esta el problema
		System.out.print("Introduce el archivo donde esta el problema: ");
		String archivoProblema = sc.nextLine();

		// Se pide el tipo de estrategia por teclado y se guarda en una variable de tipo
		// enum
		TipoDeBusqueda TBusqueda = null;
		switch (solicitarEstrategia()) {
		case 1:
			TBusqueda = TipoDeBusqueda.BusquedaEnAnchura;
			break;
		case 2:
			TBusqueda = TipoDeBusqueda.BusquedaEnProfundidad;
			break;
		case 3:
			TBusqueda = TipoDeBusqueda.BusquedaDeCostoUniforme;
			break;
		case 4:
			TBusqueda = TipoDeBusqueda.BusquedaVoraz;
			break;
		case 5:
			TBusqueda = TipoDeBusqueda.BusquedaAAsterisco;
			break;
		}

		// Se crea el problema
		Problema Prob = new Problema(archivoProblema);

		double TEjecucion;

		// Se cuenta el tiempo de ejecucion
		TEjecucion = System.currentTimeMillis();
		// Resolvemos el problema
		ArrayList<Nodo> solucion = Resolver(Prob, TBusqueda);
		TEjecucion = System.currentTimeMillis() - TEjecucion;

		if (solucion == null)
			// Si no se encuentra solucion se indica por pantalla
			System.out.println("\nNo se ha encontrado solucion y se han generado " + Prob.GetGenerados() + " nodos");
		else {
			// Si se encuentra solucion se indica por pantalla
			System.out.println("\nSe ha encontrado una solucion y se han generado " + Prob.GetGenerados() + " nodos");
			// Se imprime la solucion en un .txt
			imprimir(solucion, TBusqueda, Prob, TEjecucion);
			// Se crea un .gpx de la solucion
			crearGPX(solucion, TBusqueda, Prob);
		}

	}

	/*
	 * Metodo que solicita una numero que indica una estrategia
	 */
	public static int solicitarEstrategia() {
		int opcion;
		System.out.println("Estrategias de busqueda:");
		System.out.println("1. Estrategia de busqueda en anchura\n" + "2. Estrategia de busqueda en profundidad\n"
				+ "3. Estrategia de busqueda en coste uniforme\n" + "4. Estrategia de busqueda Voraz\n"
				+ "5. Estrategia de busqueda A*\n");
		do {

			opcion = solicitarNumero("Introduce el numero de una estrategia de busqueda (1-5): ");

		} while (opcion > 5);

		return opcion;
	}

	/*
	 * Metodo que solicita un numero positivo
	 */
	public static int solicitarNumero(String solicitud) {
		int numero = -1;
		do {
			try {
				System.out.print(solicitud);
				numero = sc.nextInt();
			} catch (Exception e) {

			}

		} while (numero <= 0);

		return numero;
	}

	/*
	 * Metodo que resuelve el problema dependiendo de la busqueda elegida por el
	 * usuario y devuelve una lista de nodos que sera la solucion
	 */
	public static ArrayList<Nodo> Resolver(Problema Prob, TipoDeBusqueda TBusqueda) {
		// Se crea la frontera
		Frontera frontera = new Frontera();
		ArrayList<Nodo> solucion = null;
		// Se solicita la profundidad maxima y el incremento de profundidad
		int Prof_Max = solicitarNumero("Introduce la profundidad máxima: ");
		int Inc_Prof = solicitarNumero("Introduce el incremento de profundidad: ");
		// Si la profundidad maxima es menor que el incremento se vuelve a solicitar
		if (Inc_Prof > Prof_Max) {
			int aux = Inc_Prof;
			Inc_Prof = Prof_Max;
			Prof_Max = aux;
			System.out.println("Profundidad maxima: " + Prof_Max + "\nIncremento de la profundidad: " + Inc_Prof);
		}
		System.out.println("Buscando una solucion...");
		// Se llama al metodo Busqueda para buscar una solucion
		solucion = MetodosDeBusqueda.Busqueda(Prob, frontera, TBusqueda, Prof_Max, Inc_Prof);

		return solucion;
	}

	/*
	 * Metodo que crea el archivo 'solucion.txt' que contiene todas las acciones,
	 * estados y datos relevantes del recorrido de la solucion
	 */
	public static void imprimir(ArrayList<Nodo> solucion, TipoDeBusqueda TBusqueda, Problema Prob, double TEjecucion) {
		String estrategiaCadena = "";
		Nodo nodo;
		switch (TBusqueda) {
		case BusquedaEnAnchura:
			estrategiaCadena = "Anchura";
			break;
		case BusquedaEnProfundidad:
			estrategiaCadena = "Profundidad";
			break;
		case BusquedaDeCostoUniforme:
			estrategiaCadena = "Coste Uniforme";
			break;
		case BusquedaVoraz:
			estrategiaCadena = "Voraz";
			break;
		case BusquedaAAsterisco:
			estrategiaCadena = "A*";
			break;
		}
		FileWriter fich_solucion;
		PrintWriter pw;

		try {
			// Creacion del archivo
			fich_solucion = new FileWriter("solucion.txt");
			pw = new PrintWriter(fich_solucion);

			// Los numeros con decimal tendran un solo decimal
			BigDecimal coste = new BigDecimal(Double.toString(solucion.get(solucion.size() - 1).GetCamino()));
			coste = coste.setScale(1, RoundingMode.HALF_UP);

			// Datos relevantes de la solucion
			pw.println("La solucion es:\r\nEstrategia:" + estrategiaCadena + "\r\nTotal Nodos Recorridos:"
					+ Prob.getRecorridos().size() + "\r\nTotal Nodos Generados:" + Prob.GetGenerados()
					+ "\r\nTiempo de ejecucion:" + TEjecucion / 1000 + "\r\nProfundidad:"
					+ solucion.get(solucion.size() - 1).GetProfundidad() + "\r\nCosto:" + coste + "\r\n\r\n");
			// Acciones y estados de todos los pasos del recorrido de la solucion
			for (int i = 0; i < solucion.size(); i++) {
				nodo = solucion.get(i);
				pw.println(nodo);
				pw.println(nodo.GetEstado() + "\r\n");
			}
			// Se cierra el archivo
			fich_solucion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Metodo que crea el archivo 'ruta.gpx' que contiene el recorrido de la
	 * solucion para que pueda ser visualizado de forma grafica por un visualizador
	 * de archivos .gpx
	 */
	public static void crearGPX(ArrayList<Nodo> solucion, TipoDeBusqueda TBusqueda, Problema Prob) {
		GPX gpx = new GPX();
		// Creacion e insercion de los waypoints
		Waypoint point = new Waypoint(Prob.getEstadoIn().GetNode().getLatitud(),
				Prob.getEstadoIn().GetNode().getLongitud());
		point.setName("[I]" + Prob.getEstadoIn().GetNode().getID());
		gpx.addWaypoint(point);
		for (int i = 0; i < Prob.getEstadoIn().getListNodes().size(); i++) {
			point = new Waypoint(Prob.getEstadoIn().getListNodes().get(i).getLatitud(),
					Prob.getEstadoIn().getListNodes().get(i).getLongitud());
			point.setName("[V]" + Prob.getEstadoIn().getListNodes().get(i).getID());
			gpx.addWaypoint(point);
		}
		// Creacion e insercion de los track segments
		TrackSegment trackSegment = new TrackSegment();
		for (int i = 0; i < solucion.size(); i++) {
			point = new Waypoint(solucion.get(i).GetEstado().GetNode().getLatitud(),
					solucion.get(i).GetEstado().GetNode().getLongitud());
			trackSegment.addWaypoint(point);
		}
		Track track = new Track();
		track.addTrackSegment(trackSegment);
		gpx.addTrack(track);
		GPXWriter writer = new GPXWriter();
		FileOutputStream out;
		try {
			// Creacion del archivo
			out = new FileOutputStream("ruta.gpx");
			// Escritura del archivo
			writer.writeGPX(gpx, out);
			// Se cierra el archivo
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
