import java.util.*;

/*
 * Clase que se encarga de la ordenacion de la frontera en funcion del valor f de los nodos (de menor a mayor)
 */
public class OrdenarNodof implements Comparator<Nodo> {
	@Override
	/*
	 * Metodo compare() sobreescrito que compara dos nodos de la frontera pasados
	 * como parametros en funcion del valor de sus f y los ordena de menor a mayor
	 */
	public int compare(Nodo n1, Nodo n2) {
		if (n1.GetF() < n2.GetF()) {

			return -1;

		} else if (n1.GetF() > n2.GetF()) {

			return 1;
		}
		return 0;
	}

}
