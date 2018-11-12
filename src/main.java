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
				+ "5. Estrategia de busqueda en coste uniforme");
		do {
			System.out.print("Introduce el numero de una estrategia de busqueda (1-5): ");
			opcion = sc.nextInt();
		} while(opcion < 1 || opcion > 5);

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
		Nodo n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, 0);
		Prob.anadirVisitado(n_inicial);
		frontera.Insertar(n_inicial);
		switch(TBusqueda) {
		case BusquedaEnAnchura:
		case BusquedaEnProfundidadSimple:
		case BusquedaDeCostoUniforme:
			solucion=BusquedaSimple(Prob,frontera, TBusqueda);
			break;
		case BusquedaEnProfundidadAcotada:
			int Prof_Max = solicitarNumero("Introduce la profundidad maxima: ");
			solucion=BusquedaEnProfundidadAcotada(Prob,frontera,TBusqueda,Prof_Max);
			break;
		case BusquedaEnProfundidadIterativa:
			int Inc_Prof = solicitarNumero("Introduce el incremento de profundidad: ");
			solucion=BusquedaEnProfundidadIterativa(Prob,frontera,TBusqueda,Inc_Prof);
			break;
		
		}
		return solucion;

	}

	public static ArrayList<Nodo> BusquedaEnProfundidadIterativa(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda, int Inc_Prof) {
		int ProfundidadMaxima = Inc_Prof;
		boolean Salir = false;
		ArrayList<Nodo> PendientesSiguienteIteracion = new ArrayList<Nodo>();
		while (!Salir) {
			Nodo n = front.Elimina();
			if (n.GetProfundidad() <= ProfundidadMaxima) {
				Prob.anadirVisitado(n);
				if (Prob.esObjetivo(n.GetEstado())) {
					return CreaSolucion(n);
				} else {
					ArrayList<Sucesor> LS = Prob.getEspacioDeEstados().sucesores(n.GetEstado());
					ArrayList<Nodo> LN = CreaListaNodosArbol(LS, front, Prob, n, TBusqueda);

					for (Nodo Hijo : LN) {

						front.Insertar(Hijo);

					}

				}
			} else {
				PendientesSiguienteIteracion.add(n);
			}

			if (front.EsVacia()) {
				if (!PendientesSiguienteIteracion.isEmpty()) {
					front.InsertaLista(PendientesSiguienteIteracion);
					PendientesSiguienteIteracion.clear();
					ProfundidadMaxima += Inc_Prof;

				} else {
					Salir = true;
				}

			}

		}

		return null;
	}

	public static ArrayList<Nodo> BusquedaEnProfundidadAcotada(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda, int Prof_Max) {

		while (!front.EsVacia()) {
			Nodo n = front.Elimina();
			if (n.GetProfundidad() <= Prof_Max) {
				Prob.anadirVisitado(n);
				if (Prob.esObjetivo(n.GetEstado())) {
					return CreaSolucion(n);
				} else {
					ArrayList<Sucesor> LS = Prob.getEspacioDeEstados().sucesores(n.GetEstado());
					ArrayList<Nodo> LN = CreaListaNodosArbol(LS, front, Prob, n, TBusqueda);
					front.InsertaLista(LN);

				}
			}

		}

		return null;
	}
	
	/*Sirve tanto para la busqueda en Profundidad, Anchura y De Costo Uniforme, lo unico que varia es como se ordenan los nodos en la frontera*/
	public static ArrayList<Nodo> BusquedaSimple(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda)
	{
		while (!front.EsVacia()) {
			Nodo n = front.Elimina();
			Prob.anadirVisitado(n);
			if (Prob.esObjetivo(n.GetEstado())) {
				return CreaSolucion(n);
			} else {
				ArrayList<Sucesor> LS = Prob.getEspacioDeEstados().sucesores(n.GetEstado());
				ArrayList<Nodo> LN = CreaListaNodosArbol(LS, front, Prob, n, TBusqueda);
				front.InsertaLista(LN);

			}
		}

		return null;
	}

	public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Frontera front, Problema Prob, Nodo n_actual, TipoDeBusqueda TBusqueda) {
		Nodo n_sucesor;
		double f = 0;
		String id;
		ArrayList<Nodo> LN = new ArrayList<Nodo>();
		Prob.IncrementarGenerados(LS.size());
		for (Sucesor s : LS) {
			id = s.getEstadoNuevo().GetId();
			if (!front.ContieneElNodo(id) && !Prob.EstaVisitado(id)) {
				switch (TBusqueda) {
				case BusquedaEnAnchura:
					f = n_actual.GetProfundidad() + 1;
					break;
				case BusquedaDeCostoUniforme:
					f = (int) (n_actual.GetCamino() + s.getCoste());
					break;
				case BusquedaEnProfundidadSimple:
				case BusquedaEnProfundidadAcotada:
				case BusquedaEnProfundidadIterativa:
					f = -(n_actual.GetProfundidad() + 1);
					break;
				}
				n_sucesor = new Nodo(n_actual, s.getEstadoNuevo(), s.getCoste() + n_actual.GetCamino(),
						n_actual.GetProfundidad() + 1, f);
				LN.add(n_sucesor);
			}
		}

		return LN;
	}

	public static void quitarNodosVisitados(ArrayList<Nodo> LN, Problema Prob) {
		int ini = 0, fin = LN.size();
		while(ini < fin) {
			if(Prob.anadirVisitado(LN.get(ini))) {
				ini++;
			} else {
				LN.remove(ini);
				fin--;
			}
		}
	}

	/*
	 * Obtiene la solucion (el camino a recorrer para llegar al nodo objetivo)
	 * mediante el uso de una lista y obteniendo los nodos padre.
	 */
	public static ArrayList<Nodo> CreaSolucion(Nodo n_actual) {
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		Nodo nodo = n_actual;
		while (nodo != null) {
			solucion.add(0, nodo);
			nodo = nodo.getPadre();

		}
		return solucion;
	}

	public static void imprimir(ArrayList<Nodo> solucion, TipoDeBusqueda TBusqueda, Problema Prob) {
		String estrategiaCadena = "";
		Nodo nodo;
		switch(TBusqueda) {
		case BusquedaEnAnchura: 
			estrategiaCadena = "anchura";
			break;
		case BusquedaEnProfundidadSimple:
			estrategiaCadena = "profundidad simple";
			break;
		case BusquedaEnProfundidadAcotada:
			estrategiaCadena = "profundidad acotada";
			break;
		case BusquedaEnProfundidadIterativa:
			estrategiaCadena = "profundidad iterativa";
			break;
		case BusquedaDeCostoUniforme:
			estrategiaCadena = "coste uniforme";
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
