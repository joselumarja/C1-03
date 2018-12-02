import java.util.ArrayList;

public class MetodosDeBusqueda {

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

	/*Sirve tanto para la busqueda en Profundidad, Anchura, De Costo Uniforme, Voraz y A*. Lo unico que varia es como se ordenan los nodos en la frontera*/
	public static ArrayList<Nodo> BusquedaSimple(Problema Prob, Frontera front, TipoDeBusqueda TBusqueda, int Prof_Max)
	{
		while (!front.EsVacia()) {
			Nodo n = front.Elimina();
			Prob.anadirVisitado(n);
			if (Prob.esObjetivo(n.GetEstado())) {
				return CreaSolucion(n);
			} else if (n.GetProfundidad() < Prof_Max){
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
				case BusquedaVoraz:
					f=n_actual.GetEstado().GetH();
					break;
				case BusquedaAAsterisco:
					f=n_actual.GetEstado().GetH()+n_actual.GetCamino();
					break;
				}
				n_sucesor = new Nodo(n_actual, s.getEstadoNuevo(), s.getCoste() + n_actual.GetCamino(),
						n_actual.GetProfundidad() + 1, f, s.getAccion());
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

}
