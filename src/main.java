import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Random; 

public class main {

	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		Problema problema = new Problema();
		EspacioDeEstados EspEst = problema.getEspacioDeEstados();
		Estado estado = problema.getEstadoIn();
		ArrayList<Sucesor> sucesores;
		Frontera frontera = new Frontera();
		Nodo nodo = null, nodoSuc;
		Random rand = new Random();
		
		//introducir aqui ruta al fichero del grafo
		/*System.out.println("Introduce la ruta del fichero (terminación con .graphml.xml)");
		String ruta = sc.nextLine();
		ruta = "data\\" + ruta;
		
		Grafo g = new Grafo(ruta);*/
		Grafo g = EspEst.getGrafo();
		String osmId;
		
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
					nodoSuc = new Nodo(nodo, s.getEstadoNuevo(), s.getCoste(), profundidad, f);
					//Antes de insertar se comprueba si el nodo ha sido visitado
					boolean visitado = problema.esVisitado(nodoSuc); //Comprobamos si el nodo sucesor ya ha sido visitado, de ser asi no se introduce en la frontera
					if(visitado == false) {
						problema.añadirVisitado(nodoSuc); //A�adimos el nodo seleccionado a la lista de nodos visitados
						System.out.println(s.getAccion());
						frontera.Insertar(nodoSuc);
					} else {
						if (problema.comprobarMejor(nodoSuc)) {
							frontera.Insertar(nodoSuc);
						}
					}
				}
				profundidad++;
			}
			if(problema.esObjetivo(estado)) {
				System.out.println("Se ha llegado a un nodo objetivo, camino:");
				Stack<Nodo> camino = obtenerSolucion(nodo);
				while (!camino.isEmpty()) {
					System.out.print(camino.pop().GetEstado().GetNode().getID());
				}
			} else System.out.println("La frontera esta vacia y no se ha podido encontrar una solución");
			
		}
		sc.close();
	}

	public static Stack<Nodo> obtenerSolucion(Nodo nodo) {
		Stack<Nodo> camino = new Stack<Nodo>();
		while (nodo != null) {
			camino.push(nodo);
			nodo = nodo.getPadre();
		}
		return camino;
	}
}
