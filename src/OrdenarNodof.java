import java.util.*;

public class OrdenarNodof implements Comparator<Nodo> {

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
