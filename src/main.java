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

public class main {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {

		System.out.print("Introduce el archivo donde esta el problema: ");
		String archivoProblema = sc.nextLine();

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

		Problema Prob = new Problema(archivoProblema);
		ArrayList<Nodo> solucion = Resolver(Prob, TBusqueda);

		if (solucion == null)
			System.out.println(
					"No se ha encontrado solucion y se han generado " + Prob.getRecorridos().size() + " nodos");
		else
			imprimir(solucion, TBusqueda, Prob);
		crearGPX(solucion, TBusqueda, Prob);
	}

	public static int solicitarEstrategia() {
		int opcion;
		System.out.println("Estrategias de busqueda:");
		System.out
				.println("1. Estrategia de busqueda en anchura\n" 
						+ "2. Estrategia de busqueda en profundidad\n"
						+ "3. Estrategia de busqueda en coste uniforme\n" 
						+ "4. Estrategia de busqueda Voraz\n"
						+ "5. Estrategia de busqueda A*\n");
		do {

			opcion = solicitarNumero("Introduce el numero de una estrategia de busqueda (1-5): ");

		} while (opcion > 5);

		return opcion;
	}

	public static int solicitarNumero(String solicitud) {
		int numero = -1;
		do {
			try 
			{
				System.out.print(solicitud);
				numero = sc.nextInt();
			} catch (Exception e) {
				
			}

		} while (numero <= 0);

		return numero;
	}

	public static ArrayList<Nodo> Resolver(Problema Prob, TipoDeBusqueda TBusqueda) {
		Frontera frontera = new Frontera();
		ArrayList<Nodo> solucion = null;
		int Prof_Max = solicitarNumero("Introduce la profundidad máxima: ");
		int Inc_Prof = solicitarNumero("Introduce el incremento de profundidad: ");
		if (Inc_Prof > Prof_Max) {
			Prof_Max = solicitarNumero(
					"La profundidad máxima debe ser mayor o igual al incremento de profundidad\nIntroduce el incremento de profundidad: ");
		}
		solucion = MetodosDeBusqueda.Busqueda(Prob, frontera, TBusqueda, Prof_Max, Inc_Prof);

		return solucion;
	}

	public static void imprimir(ArrayList<Nodo> solucion, TipoDeBusqueda TBusqueda, Problema Prob) {
		String estrategiaCadena = "";
		Nodo nodo;
		switch (TBusqueda) {
		case BusquedaEnAnchura:
			estrategiaCadena = "Anchura";
			break;
		case BusquedaEnProfundidad:
			estrategiaCadena = "Profundidad Simple";
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
			fich_solucion = new FileWriter("solucion.txt");
			pw = new PrintWriter(fich_solucion);

			BigDecimal coste = new BigDecimal(Double.toString(solucion.get(solucion.size() - 1).GetCamino()));
			coste = coste.setScale(1, RoundingMode.HALF_UP);

			pw.println("La solucion es:\r\nEstrategia:" + estrategiaCadena + "\r\nTotal Nodos Recorridos:"
					+ Prob.getRecorridos().size() + "\r\nTotal Nodos Generados:" + Prob.GetGenerados()
					+ "\r\nProfundidad:" + solucion.get(solucion.size() - 1).GetProfundidad() + "\r\nCosto:" + coste
					+ "\r\n\r\n");
			for (int i = 0; i < solucion.size(); i++) {
				nodo = solucion.get(i);
				pw.println(nodo);
				pw.println(nodo.GetEstado() + "\r\n");
			}
			fich_solucion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void crearGPX(ArrayList<Nodo> solucion, TipoDeBusqueda TBusqueda, Problema Prob) {
		GPX gpx = new GPX();
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
			out = new FileOutputStream("ruta.gpx");
			writer.writeGPX(gpx, out);
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
