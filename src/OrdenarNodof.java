import java.util.*;

public class OrdenarNodof implements Comparator<Nodo> { //Clase que se encarga de la ordenacion de la frontera en funcion de f

	@Override
	public int compare(Nodo n1, Nodo n2) {
		if (n1.GetF() < n2.GetF()) {
		
		return -1;
		
	} else if (n1.GetF() > n2.GetF()) {
		
		return 1;
	}

	return 0;
	}

}
