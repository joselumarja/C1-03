import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		
		//introducir aqui ruta al fichero del grafo
		System.out.println("Introduce la ruta del fichero (terminación con .graphml.xml)");
		String ruta = sc.nextLine();
		ruta = "data\\" + ruta;
		
		Grafo g = new Grafo(ruta);
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

	}
}
