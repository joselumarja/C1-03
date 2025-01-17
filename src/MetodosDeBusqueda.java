import java.util.ArrayList;

/*
 * Clase MetodosDeBusqueda que contiene todos los metodos necesarios para realizar la 
 * busqueda de una solucion creando el arbol de busqueda
 */

public class MetodosDeBusqueda {

	/*
	 * Metodo que genera el arbol de busqueda hasta una profundidad maxima pasada
	 * como parametro o hasta que se encuentra una solucion
	 */
	public static ArrayList<Nodo> BusquedaAcotada(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda, int Prof_Max,
			int Inc_Prof) {
		boolean salir = false;
		int Prof_Act = Inc_Prof;
		ArrayList<Nodo> PendientesSiguienteIteracion = new ArrayList<Nodo>();
		do {
			while (!front.EsVacia()) {
				// Si la frontera no esta vacia se coge el primer nodo de la frontera
				Nodo n = front.Elimina();

				// comprobamos si en tiempo de ejecucion se ha encontrado un camino que pase por
				// el mismo nodo pero sea mas corto
				if (!n.CaminoPodado()) {
					if (Prob.esObjetivo(n.GetEstado())) {
						// Si el nodo cogido de la frontera es objetivo se crea la solucion y se
						// devuelve
						return CreaSolucion(n);
					} else {
						// Genera la lista de sucesores a partir del estado de un nodo cogido de la
						// frontera
						ArrayList<Sucesor> LS = Prob.getEspacioDeEstados().sucesores(n.GetEstado());
						// Genera la lista de nodos sucesores a partir de la lista de sucesores
						ArrayList<Nodo> LN = CreaListaNodosArbol(LS, front, Prob, n, TBusqueda, Prof_Act,
								PendientesSiguienteIteracion);
						if (LN != null)
							// Insertamos la lista de nodos por expandir en la frontera
							front.InsertaLista(LN);
					}
				}
			}

			if (PendientesSiguienteIteracion.size() > 0 && Prof_Act < Prof_Max) {

				if (Prof_Act + Inc_Prof > Prof_Max) {
					Prof_Act = Prof_Max;
				}

				else {
					Prof_Act += Inc_Prof;
				}

				front.InsertaLista(PendientesSiguienteIteracion);
				PendientesSiguienteIteracion = new ArrayList<Nodo>();
			} else {
				salir = true;
			}

		} while (!salir);

		return null;
	}

	/*
	 * Metodo que realiza el control de las iteraciones de la busqueda (en el caso
	 * de no encontrar solucion en la primera iteracion) e inicializa la frontera y
	 * la lista de visitados
	 */
	public static ArrayList<Nodo> Busqueda(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda, int Prof_Max,
			int Inc_Prof) {
		ArrayList<Nodo> solucion = null;
		Nodo n_inicial = null;

		// Se crea el nodo raiz
		switch (TBusqueda) {
		case BusquedaEnAnchura:
		case BusquedaEnProfundidad:
		case BusquedaDeCostoUniforme:
			n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, 0, "None ");
			break;
		case BusquedaVoraz:
		case BusquedaAAsterisco:
			n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, Prob.getEstadoIn().GetH(), "None ");
			break;
		}

		// Añadimos el nodo raiz a la lista de visitados
		Prob.anadirVisitado(n_inicial, TBusqueda);
		// Insertamos el nodo raiz a la frontera
		front.Insertar(n_inicial);

		// se llama al metodo BusquedaAcotada para encontrar una solucion

		solucion = BusquedaAcotada(Prob, front, TBusqueda, Prof_Max, Inc_Prof);

		return solucion;

	}

	/*
	 * Metodo que devuelve la lista de nodos (que aun no se han visitado o que ya se
	 * han visitado pero son mejores) que se deben expandir en el arbol de busqueda
	 * a partir de la lista de sucesores
	 */
	public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Frontera front, Problema Prob,
			Nodo n_actual, TipoDeBusqueda TBusqueda, int Prof_Max, ArrayList<Nodo> PendientesSiguienteIteracion) {
		Nodo n_sucesor;
		double f = 0;
		ArrayList<Nodo> LN = null;
		Prob.IncrementarGenerados(LS.size());
		if (n_actual.GetProfundidad() < Prof_Max) {
			// Si los nodos que se vayan a generar se encuentran dentro de la cota creamos
			// la lista de nodos vacia
			LN = new ArrayList<Nodo>();
			for (Sucesor s : LS) {
				// Por cada sucesor de la lista de sucesores calculamos la f dependiendo del
				// tipo de busqueda que estemos realizando
				switch (TBusqueda) {
				case BusquedaEnAnchura:
					f = n_actual.GetProfundidad() + 1;
					break;
				case BusquedaDeCostoUniforme:
					f = n_actual.GetCamino() + s.getCoste();
					break;
				case BusquedaEnProfundidad:
					f = -(n_actual.GetProfundidad() + 1);
					break;
				case BusquedaVoraz:
					f = s.getEstadoNuevo().GetH();
					break;
				case BusquedaAAsterisco:
					f = s.getEstadoNuevo().GetH() + n_actual.GetCamino();
					break;
				}
				// Creamos el nuevo nodo sucesor
				n_sucesor = new Nodo(n_actual, s.getEstadoNuevo(), s.getCoste() + n_actual.GetCamino(),
						n_actual.GetProfundidad() + 1, f, s.getAccion());
				if (Prob.anadirVisitado(n_sucesor, TBusqueda)) {
					// Si el nodo sucesor aun no se ha visitado o ya se ha visitado pero tiene una f
					// mejor se añade a la lista de nodos que se deben expandir en el arbol de
					// busqueda
					LN.add(n_sucesor);
				}
			}
		} else {
			PendientesSiguienteIteracion.add(n_actual);
		}
		return LN;
	}

	/*
	 * Metodo que obtiene y devuelve la solucion (el camino a recorrer para llegar
	 * al nodo objetivo) mediante el uso de una lista y obteniendo los nodos padre.
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
	
	public static void OrdenObjetivos(ArrayList<Punto> objetivos, ArrayList<Nodo> solucion) {
		int pares=0;
		double aux=0,suma=0;
		boolean sumar=false;
		String osmid;
		for(Nodo n:solucion) {
			
			if(sumar) {
				suma+=n.GetCamino()-aux;
				sumar=false;
				aux=0;
			}
			osmid=n.GetEstado().GetNode().getID();
			if(osmid.charAt(osmid.length()-1)=='0'||osmid.charAt(osmid.length()-1)=='2'||osmid.charAt(osmid.length()-1)=='4'||osmid.charAt(osmid.length()-1)=='6'||osmid.charAt(osmid.length()-1)=='8') {
				pares++;
				aux=n.GetCamino();
				sumar=true;
			}
		}
		System.out.println("N Pares: "+pares);
		System.out.println("Suma: "+suma);
	}

}
