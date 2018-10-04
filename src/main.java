import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		
		//introducir aqui ruta al fichero del grafo
		String ruta = "Pueblo.graphml";
		
		Grafo g = new Grafo(ruta);
		int osmId;

		if (g != null) {
			System.out.println("Introduce un id para comprobar que esta en el grafo");
			osmId = sc.nextInt();
			System.out.println(g.perteneceNodo(osmId));

			System.out.println("Introduce un id para obtener su longitud y latitud en el grafo");
			osmId = sc.nextInt();
			Punto p = g.posicionNodo(osmId);
			if (p != null) {
				System.out.println("Longitud: " + p.getLongitud() + " Latitud: " + p.getLatitud());
			} else {
				System.out.println("El nodo no existe en el grafo");
			}

			System.out.println("Introduce un nodo para ver sus aristas");
			osmId = sc.nextInt();
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
