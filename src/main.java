import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random; 

public class main {

	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		Problema problema = new Problema();
		EspacioDeEstados EspEst = problema.getEspacioDeEstados();
		Estado estado = problema.getEstadoIn();
		ArrayList<Sucesor> sucesores;
		Frontera frontera = new Frontera();
		Nodo nodo, nodoSuc;
		Random rand = new Random();
		
		//introducir aqui ruta al fichero del grafo
		/*System.out.println("Introduce la ruta del fichero (terminaciÃ³n con .graphml.xml)");
		String ruta = sc.nextLine();
		ruta = "data\\" + ruta;
		
		Grafo g = new Grafo(ruta);*/
		Grafo g = EspEst.getGrafo();
		String osmId;

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
		
		if(EspEst.esta(estado)) {
			System.out.println("El nodo y sus nodos por recorrer pertenecen al grafo");
 
			frontera.Insertar(new Nodo(null, estado, 0, 0, 0));//Nodo inicial
			int profundidad = 1;
			
			while(!problema.esObjetivo(estado) && !frontera.EsVacia()){
				System.out.println(frontera.toString());
				nodo = frontera.Elimina();
				estado = nodo.GetEstado();
				problema.añadirVisitado(nodo);
				sucesores = EspEst.sucesores(estado);
				for (Sucesor s : sucesores) {
					
					int f = rand.nextInt(100) + 1;
					nodoSuc = new Nodo(nodo, s.getEstadoNuevo(), s.getCoste(), profundidad, f);
					//Antes de insertar se comprueba si el nodo ha sido visitado
					boolean visitado = problema.esVisitado(nodoSuc);
					if(visitado == false) {
						System.out.println(s.getAccion());
						frontera.Insertar(nodoSuc);
					}
				}
				
				profundidad++;
			}
			System.out.println("Se han visitado todos los nodos objetivo o la frontera esta vacia");
		}
		sc.close();
	}
}
