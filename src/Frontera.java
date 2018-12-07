import java.util.*;

/*
 * Clase Frontera la cual representa los nodos que se han generado y aun no se han 
 * expandido en el arbol de busqueda. La frontera (lista de nodos) esta ordenada de 
 * menor a mayor segun la f
 */
public class Frontera {
	// Lista de nodos que representa la frontera para la creacion del arbol de
	// busqueda
	private ArrayList<Nodo> frontera;

	/*
	 * Constructor de la clase Frontera la cual crea una instancia de dicha clase
	 * creando una frontera vacia
	 */
	public Frontera() {
		CreaFrontera();
	}

	/*
	 * Metodo que crea una frontera vacia
	 */
	public void CreaFrontera() {
		frontera = new ArrayList<Nodo>();
	}

	/*
	 * Metodo que inserta un nodo (pasado por parametro) a la frontera y la reordena
	 * de menor a mayor segun la f
	 */
	public void Insertar(Nodo nuevo) {
		frontera.add(nuevo);
		// Una vez añadimos un nuevo nodo a la frontera debemos de reordenarla
		Collections.sort(frontera, new OrdenarNodof());
	}

	/*
	 * Metodo que inserta una lista de nodos (pasado por parametro) a la frontera y
	 * la reordena de menor a mayor segun la f
	 */
	public void InsertaLista(ArrayList<Nodo> LN) {
		frontera.addAll(LN);
		// Una vez añadimos un nuevo nodo a la frontera debemos de reordenarla
		Collections.sort(frontera, new OrdenarNodof());
	}

	/*
	 * Metodo que elimina y devuelve el primer nodo de la frontera. Si la frontera
	 * esta vacia se devuelve null
	 */
	public Nodo Elimina() {
		Nodo n = null;
		if (!EsVacia())
			n = frontera.remove(0);
		return n;
	}

	/*
	 * Metodo que devuelve 'true' si la frontera esta vacia y 'false' si hay algun
	 * nodo en la frontera
	 */
	public boolean EsVacia() {
		return frontera.isEmpty();
	}

	/*
	 * Metodo que devuelve 'true' si la frontera contiene un nodo con el osmid
	 * pasado como parametro y 'false' si no lo contiene
	 */
	public boolean ContieneElNodo(String id) {
		for (Nodo x : frontera) {
			if (id.equals(x.GetEstado().GetId()))
				return true;
		}
		return false;
	}
	
	public String toString() {
		String f="";
		for(Nodo n:frontera) {
			f=n.toString()+" ";
		}
		return f;
	}

}
