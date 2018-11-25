import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class main {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {

		TipoDeBusqueda TBusqueda=null;
		switch(solicitarEstrategia()) {
		case 1:
			TBusqueda=TipoDeBusqueda.BusquedaEnAnchura;
			break;
		case 2:
			TBusqueda=TipoDeBusqueda.BusquedaEnProfundidadSimple;
			break;
		case 3:
			TBusqueda=TipoDeBusqueda.BusquedaEnProfundidadAcotada;
			break;
		case 4:
			TBusqueda=TipoDeBusqueda.BusquedaEnProfundidadIterativa;
			break;
		case 5:
			TBusqueda=TipoDeBusqueda.BusquedaDeCostoUniforme;
			break;
		case 6:
			TBusqueda=TipoDeBusqueda.BusquedaVoraz;
			break;
		case 7:
			TBusqueda=TipoDeBusqueda.BusquedaAAsterisco;
			break;
		}
		
		Problema Prob = new Problema("problema.json");
		ArrayList<Nodo> solucion=Busqueda(Prob, TBusqueda);

		if(solucion==null) System.out.println("No se ha encontrado solucion y se han generado " + Prob.getRecorridos().size() + " nodos");
		else imprimir(solucion, TBusqueda, Prob);
	}

	public static int solicitarEstrategia() {
		int opcion;
		System.out.println("Estrategias de busqueda:");
		System.out.println("1. Estrategia de busqueda en anchura\n"
				+ "2. Estrategia de busqueda en profundidad simple\n"
				+ "3. Estrategia de busqueda en profundidad acotada\n"
				+ "4. Estrategia de busqueda en profundidad iterativa\n"
				+ "5. Estrategia de busqueda en coste uniforme\n"
				+ "6. Estrategia de busqueda Voraz\n" + "7. Estrategia de busqueda A*\n");
		do {

			opcion = solicitarNumero("Introduce el numero de una estrategia de busqueda (1-7): ");

		} while (opcion > 7);

		return opcion;
	}

	public static int solicitarNumero(String solicitud) {
		int numero;
		do {
			System.out.print(solicitud);
			numero = sc.nextInt();
		} while(numero <= 0);

		return numero;
	}

	public static ArrayList<Nodo> Busqueda(Problema Prob, TipoDeBusqueda TBusqueda){
		Frontera frontera = new Frontera();
		ArrayList<Nodo> solucion=null;
		Nodo n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, 0, "None");
		Prob.anadirVisitado(n_inicial);
		frontera.Insertar(n_inicial);
		switch(TBusqueda) {
		case BusquedaVoraz:
		case BusquedaAAsterisco:
		case BusquedaEnAnchura:
		case BusquedaEnProfundidadSimple:
		case BusquedaDeCostoUniforme:
			solucion=MetodosDeBusqueda.BusquedaSimple(Prob,frontera, TBusqueda);
			break;
		case BusquedaEnProfundidadAcotada:
			int Prof_Max = solicitarNumero("Introduce la profundidad maxima: ");
			solucion=MetodosDeBusqueda.BusquedaEnProfundidadAcotada(Prob,frontera,TBusqueda,Prof_Max);
			break;
		case BusquedaEnProfundidadIterativa:
			int Inc_Prof = solicitarNumero("Introduce el incremento de profundidad: ");
			solucion=MetodosDeBusqueda.BusquedaEnProfundidadIterativa(Prob,frontera,TBusqueda,Inc_Prof);
			break;
		
		}
		return solucion;

	}

	public static void imprimir(ArrayList<Nodo> solucion, TipoDeBusqueda TBusqueda, Problema Prob) {
		String estrategiaCadena = "";
		Nodo nodo;
		switch(TBusqueda) {
		case BusquedaEnAnchura: 
			estrategiaCadena = "Anchura";
			break;
		case BusquedaEnProfundidadSimple:
			estrategiaCadena = "Profundidad Simple";
			break;
		case BusquedaEnProfundidadAcotada:
			estrategiaCadena = "Profundidad Acotada";
			break;
		case BusquedaEnProfundidadIterativa:
			estrategiaCadena = "Profundidad Iterativa";
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

			pw.println("La solucion es:\r\nEstrategia:" + estrategiaCadena + "\r\nTotal Nodos Recorridoss:"
					+ Prob.getRecorridos().size() + "\r\nTotal Nodos Generados:"+Prob.GetGenerados()+"\r\nProfundidad:" + solucion.get(solucion.size()-1).GetProfundidad()
					+ "\r\nCosto:" + solucion.get(solucion.size()-1).GetCamino()+"\r\n\r\n");
			while(!solucion.isEmpty()) {
				nodo = solucion.remove(0);
				pw.println(nodo);
				pw.println(nodo.GetEstado() + "\r\n");
			}
			fich_solucion.close();
		}catch(Exception e) {
			e.printStackTrace();
		}


	}
}
