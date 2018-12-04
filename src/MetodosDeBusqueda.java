import java.util.ArrayList;

/*
 * Clase MetodosDeBusqueda que contiene todos los metodos necesarios para realizar la 
 * busqueda de una solucion creando el arbol de busqueda
 */

public class MetodosDeBusqueda {
	/*
	 * public static ArrayList<Nodo> BusquedaEnProfundidadIterativa(Problema Prob,
	 * Frontera front, TipoDeBusqueda TBusqueda, int Prof_Max, int Inc_Prof) { int
	 * ProfundidadMaxima = Inc_Prof; boolean Salir = false; ArrayList<Nodo>
	 * PendientesSiguienteIteracion = new ArrayList<Nodo>(); while (!Salir) { Nodo n
	 * = front.Elimina(); if (n.GetProfundidad() <= ProfundidadMaxima) {
	 * Prob.anadirVisitado(n); if (Prob.esObjetivo(n.GetEstado())) { return
	 * CreaSolucion(n); } else { ArrayList<Sucesor> LS =
	 * Prob.getEspacioDeEstados().sucesores(n.GetEstado()); ArrayList<Nodo> LN =
	 * CreaListaNodosArbol(LS, front, Prob, n, TBusqueda, Prof_Max);
	 * 
	 * for (Nodo Hijo : LN) {
	 * 
	 * front.Insertar(Hijo);
	 * 
	 * }
	 * 
	 * } } else { PendientesSiguienteIteracion.add(n); }
	 * 
	 * if (front.EsVacia()) { if (!PendientesSiguienteIteracion.isEmpty()) {
	 * front.InsertaLista(PendientesSiguienteIteracion);
	 * PendientesSiguienteIteracion.clear(); ProfundidadMaxima += Inc_Prof;
	 * 
	 * } else { Salir = true; }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return null; }
	 */

	/*
	 * Metodo que genera el arbol de busqueda hasta una profundidad maxima pasada
	 * como parametro o hasta que se encuentra una solucion
	 */
	public static ArrayList<Nodo> BusquedaAcotada(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda,
			int Prof_Max) {
		while (!front.EsVacia()) {
			// Si la frontera no esta vacia se coge el primer nodo de la frontera
			Nodo n = front.Elimina();
			if (Prob.esObjetivo(n.GetEstado())) {
				// Si el nodo cogido de la frontera es objetivo se crea la solucion y se
				// devuelve
				return CreaSolucion(n);
			} else {
				// Genera la lista de sucesores a partir del estado de un nodo cogido de la
				// frontera
				ArrayList<Sucesor> LS = Prob.getEspacioDeEstados().sucesores(n.GetEstado());
				// Genera la lista de nodos sucesores a partir de la lista de sucesores
				ArrayList<Nodo> LN = CreaListaNodosArbol(LS, front, Prob, n, TBusqueda, Prof_Max);
				if (LN != null)
					// Insertamos la lista de nodos por expandir en la frontera
					front.InsertaLista(LN);
			}
		}
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
		Nodo n_inicial;
		// Se crea el nodo raiz
		if(TBusqueda == TipoDeBusqueda.BusquedaVoraz || TBusqueda == TipoDeBusqueda.BusquedaAAsterisco) {
			n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, Prob.getEstadoIn().GetH(), "None ");
		}else{
			n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, 0, "None ");
		}
		// Añadimos el nodo raiz a la lista de visitados
		Prob.anadirVisitado(n_inicial);
		// Insertamos el nodo raiz a la frontera
		front.Insertar(n_inicial);
		int Prof_Actual = Inc_Prof;
		while (solucion == null && Prof_Actual <= Prof_Max) {
			// Si aun no hemos encontrado una solucion y aun no se ha llegado a la
			// profundidad maxima se llama al metodo BusquedaAcotada para encontrar una
			// solucion
			solucion = BusquedaAcotada(Prob, front, TBusqueda, Prof_Actual);
			if (solucion == null) {
				// Si no se ha encontrado una solucion se limpia la lista de visitados y la
				// frontera inicializandolas vacias
				Prob.LimpiarVisitados();
				front.CreaFrontera();
				// Añadimos el nodo raiz a la lista de visitados y a la frontera
				Prob.anadirVisitado(n_inicial);
				front.Insertar(n_inicial);
			}
			Prof_Actual += Inc_Prof;
		}
		return solucion;
	}

	/*
	 * Metodo que devuelve la lista de nodos (que aun no se han visitado o que ya se
	 * han visitado pero son mejores) que se deben expandir en el arbol de busqueda
	 * a partir de la lista de sucesores
	 */
	public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Frontera front, Problema Prob,
			Nodo n_actual, TipoDeBusqueda TBusqueda, int Prof_Max) {
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
				case BusquedaEnProfundidadSimple:
				case BusquedaEnProfundidadAcotada:
				case BusquedaEnProfundidadIterativa:
					f = -(n_actual.GetProfundidad() + 1);
					break;
				case BusquedaVoraz:
					f = n_actual.GetEstado().GetH();
					break;
				case BusquedaAAsterisco:
					f = n_actual.GetEstado().GetH() + n_actual.GetCamino() + s.getCoste();
					break;
				}
				// Creamos el nuevo nodo sucesor
				n_sucesor = new Nodo(n_actual, s.getEstadoNuevo(), s.getCoste() + n_actual.GetCamino(),
						n_actual.GetProfundidad() + 1, f, s.getAccion());
				if (Prob.anadirVisitado(n_sucesor)) {
					// Si el nodo sucesor aun no se ha visitado o ya se ha visitado pero tiene una f
					// mejor se añade a la lista de nodos que se deben expandir en el arbol de
					// busqueda
					LN.add(n_sucesor);
				}
			}
		}
		return LN;
	}
	/*
	 * public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS,
	 * Frontera front, Problema Prob, Nodo n_actual, TipoDeBusqueda TBusqueda, int
	 * Prof_Max) { Nodo n_sucesor; double f = 0; String id; ArrayList<Nodo> LN =
	 * null; if (n_actual.GetProfundidad() < Prof_Max) { LN = new ArrayList<Nodo>();
	 * Prob.IncrementarGenerados(LS.size()); for (Sucesor s : LS) { id =
	 * s.getEstadoNuevo().GetId(); if (!front.ContieneElNodo(id) &&
	 * !Prob.EstaVisitado(id)) { switch (TBusqueda) { case BusquedaEnAnchura: f =
	 * n_actual.GetProfundidad() + 1; break; case BusquedaDeCostoUniforme: f =
	 * n_actual.GetCamino() + s.getCoste(); break; case BusquedaEnProfundidadSimple:
	 * case BusquedaEnProfundidadAcotada: case BusquedaEnProfundidadIterativa: f =
	 * -(n_actual.GetProfundidad() + 1); break; case BusquedaVoraz: f =
	 * n_actual.GetEstado().GetH(); break; case BusquedaAAsterisco: f =
	 * n_actual.GetEstado().GetH() + n_actual.GetCamino(); break; } n_sucesor = new
	 * Nodo(n_actual, s.getEstadoNuevo(), s.getCoste() + n_actual.GetCamino(),
	 * n_actual.GetProfundidad() + 1, f, s.getAccion()); LN.add(n_sucesor); } } }
	 * return LN; }
	 */
	/*
	 * public static void quitarNodosVisitados(ArrayList<Nodo> LN, Problema Prob) {
	 * int ini = 0, fin = LN.size(); while (ini < fin) { if
	 * (Prob.anadirVisitado(LN.get(ini))) { ini++; } else { LN.remove(ini); fin--; }
	 * } }
	 */

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

}
