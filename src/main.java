import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Random; 

public class main {

	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
		
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
		String estrategia = "anchura";
		int Prof_Max = 50;
		Problema Prob = new Problema("problema.json");
		Busqueda_Acotada(Prob, estrategia, Prof_Max);
	}


	
	public static ArrayList<Nodo> Busqueda_Acotada(Problema Prob, String estrategia, int Prof_Max) {
		//Inicialización
		Frontera frontera = new Frontera();
		Nodo n_inicial = new Nodo(null, Prob.getEstadoIn(), 0, 0, 0);
		frontera.Insertar(n_inicial);
		boolean solucion = false;
		Nodo n_actual = null;
		
		//Bucle de busqueda
		while(!solucion && !frontera.EsVacia()) {
			n_actual = frontera.Elimina();
			if(Prob.esObjetivo(n_actual.GetEstado())) {
				solucion = true;
			} else {
				ArrayList<Sucesor> LS = Prob.getEspacioDeEstados().sucesores(n_actual.GetEstado());
				ArrayList<Nodo> LN = CreaListaNodosArbol(LS, n_actual, Prof_Max, estrategia);
				quitarNodosVisitados(LN, Prob);
				frontera.InsertaLista(LN);
			}
		}
		
		//Resultado
		if(solucion) {
			return CreaSolucion(n_actual);
		} else {
			return null;
		}
		//fin_Busqueda_Acotada
	}
	
	public static ArrayList<Nodo> CreaListaNodosArbol(ArrayList<Sucesor> LS, Nodo n_actual, int Prof_Max, String estrategia) {
		Nodo n_sucesor;
		int f = 0;
		ArrayList<Nodo> LN = new ArrayList<Nodo>();
		if (n_actual.GetProfundidad() < Prof_Max) {
			for (Sucesor s : LS) {
				switch (estrategia) {
				case "anchura":
					f = n_actual.GetProfundidad() + 1;
					break;
				case "costo uniforme":
					f = (int) (n_actual.GetCamino() + s.getCoste());
					break;
				case "profundidad simple":
				case "profundidad acotada":
				case "profundidad iterativa":
					f = -(n_actual.GetProfundidad() + 1);
					break;
				}
				n_sucesor = new Nodo(n_actual, s.getEstadoNuevo(), s.getCoste() + n_actual.GetCamino(),
						n_actual.GetProfundidad() + 1, f);
				LN.add(n_sucesor);
			}
		} else LN = null;
		return LN;
	}
	
	public static void quitarNodosVisitados(ArrayList<Nodo> LN, Problema Prob) {
		int ini = 0, fin = LN.size();
		while(ini < fin) {
			if(Prob.esVisitado(LN.get(ini))) {
				LN.remove(ini);
				fin--;
			} else {
				ini++;
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
