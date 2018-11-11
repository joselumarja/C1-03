import java.util.ArrayList;
import java.util.Scanner; 

public class main {
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		//introducir aqui ruta al fichero del grafo
		/*System.out.println("Introduce la ruta del fichero (terminación con .graphml.xml)");
		String ruta = sc.nextLine();
		ruta = "data\\" + ruta;
		
		Grafo g = new Grafo(ruta);*/
		
		/*
		if (g != null) {
			//Funcionalidad 1
			System.out.println("Introduce un id para comprobar que esta en el grafo");
			osmId = sc.next();
			System.out.println(g.perteneceNodo(osmId));
			
			//Funcionalidad 2
			System.out.println("Introduce un id para obtener su longitud y latitud en el grafo");
			osmId = sc.next();
			Punto p = g.posicionNodo(osmId);
			if (p != null) {
				System.out.println("Longitud: " + p.getLongitud() + " Latitud: " + p.getLatitud());
			} else {
				System.out.println("El nodo no existe en el grafo");
			}
			
			//Funcionalidad 3
			System.out.println("Introduce un nodo para ver sus aristas");
			osmId = sc.next();
			ArrayList<Arista> aristas = g.adyacentesNodo(osmId);
			if (!aristas.isEmpty()) {
				for (Arista a : aristas) {
					System.out.println("Nodo origen: " + a.getOrigen() + " Nodo destino: " + a.getDestino()
							+ " Longitud: " + a.getLongitud() + " Nombre: " + a.getNombre());
				}
			} else {
				System.out.println("Error, el nodo introducido no tiene aristas o no existe");
			}
		}
		*/
		/*
		if(EspEst.esta(estado)) { //Priemro comprobamos que el estado inicial sea posible en el espacio de estados
			System.out.println("El nodo y sus nodos por recorrer pertenecen al grafo");
 
			frontera.Insertar(new Nodo(null, estado, 0, 0, 0));//Nodo inicial
			int profundidad = 1;
			
			while(!problema.esObjetivo(estado) && !frontera.EsVacia()){ //Mientras que queden nodos por recorrer o nodos en la frontera se ejecutara el programa
				System.out.println("\n" + frontera.toString());
				nodo = frontera.Elimina(); //Extraemos de la frontera el nodo con menor f
				estado = nodo.GetEstado(); //Extraemos el estado del nodo
				System.out.println("*****Nodo Seleccionado de la frontera: "+estado.GetNode().getID() + "*****");
				System.out.println(estado.toString());
				sucesores = EspEst.sucesores(estado); //Calculamos los nodos sucesores del nodo elegido
				
				for (Sucesor s : sucesores) { //Recorremos la lista de nodos sucesores y los introducimos en la frontera
					int f = rand.nextInt(100) + 1;
					nodoSuc = new Nodo(nodo, s.getEstadoNuevo(), s.getCoste(), profundidad, f); //Crea el nodo sucesor
					//Antes de insertar se comprueba si el nodo ha sido visitado
					boolean visitado = problema.esVisitado(nodoSuc); //Comprobamos si el nodo sucesor ya ha sido visitado, de ser asi no se introduce en la frontera
					if(visitado == false) {
						problema.anadirVisitado(nodoSuc); //A�adimos el nodo seleccionado a la lista de nodos visitados
						System.out.println(s.getAccion()); //Muestra la accion posible a realizar
						frontera.Insertar(nodoSuc); //Inserta el nodo sucesor a expandir
					} 
				}
				profundidad++;
			}
			if(problema.esObjetivo(estado)) { //Si encuentra una solucion la muestra por pantalla (el camino a recorrer)
				System.out.println("Se ha llegado a un nodo objetivo, camino:");
				Stack<Nodo> camino = obtenerSolucion(nodo);
				while (!camino.isEmpty()) {
					System.out.print(camino.pop().GetEstado().GetNode().getID());
				}
			} else System.out.println("La frontera esta vacia y no se ha podido encontrar una solución");
			
		}*/
		//int estrategia=solicitarEstrategia();
		
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
		//
		Problema Prob = new Problema("problema.json");
		//ArrayList<Nodo> solucion = Busqueda(Prob, estrategia, Prof_Max, Inc_Prof);
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
			solucion=BusquedaEnAnchura(Prob,frontera, TBusqueda);
			break;
		case BusquedaEnProfundidadSimple:
			solucion=BusquedaEnProfundidad(Prob,frontera, TBusqueda);
			break;
		case BusquedaEnProfundidadAcotada:
			int Prof_Max = solicitarNumero("Introduce la profundidad maxima: ");
			solucion=BusquedaEnProfundidadAcotada(Prob,frontera,TBusqueda,Prof_Max);
			break;
		case BusquedaEnProfundidadIterativa:
			int Inc_Prof = solicitarNumero("Introduce el incremento de profundidad: ");
			BusquedaEnProfundidadIterativa(Prob,frontera,TBusqueda,Inc_Prof);
			break;
		case BusquedaDeCostoUniforme:
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
				System.out.println(n.GetProfundidad());
			}

			if (front.EsVacia()) {
				if (!PendientesSiguienteIteracion.isEmpty()) {
					System.out.println(ProfundidadMaxima+" "+PendientesSiguienteIteracion.toString());
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

					for (Nodo Hijo : LN) {

						front.Insertar(Hijo);

					}

				}
			}

		}

		return null;
	}
	
	public static ArrayList<Nodo> BusquedaEnProfundidad(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda) {
		
		while (!front.EsVacia()) {
			Nodo n = front.Elimina();
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
		}

		return null;

	}
	
public static ArrayList<Nodo> BusquedaEnAnchura(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda) {
		
		while (!front.EsVacia()) {
			Nodo n = front.Elimina();
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
		}

		return null;

	}
	
	
	public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Frontera front, Problema Prob, Nodo n_actual, TipoDeBusqueda TBusqueda) {
		Nodo n_sucesor;
		double f = 0;
		String id;
		ArrayList<Nodo> LN = new ArrayList<Nodo>();
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
		System.out.println("La solucion es:\nEstrategia:" + estrategiaCadena + "\nTotal Nodos Generados:"
				+ Prob.getRecorridos().size() + "\nProfundidad:" + solucion.get(solucion.size()-1).GetProfundidad()
				+ "\nCosto:" + solucion.get(solucion.size()-1).GetCamino());
		while(!solucion.isEmpty()) {
			nodo = solucion.remove(0);
			System.out.println(nodo);
			System.out.println(nodo.GetEstado() + "\n");
		}
	}
}
