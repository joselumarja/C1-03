import java.util.*;

public class Frontera{

	private ArrayList<Nodo> frontera;
	private Comparator<Nodo> n;
	
	public Frontera() {
		CreaFrontera();
	}
	
	public void CreaFrontera() {
		frontera=new ArrayList<Nodo>();
	}
	
	public void Insertar(Nodo nuevo) {
		frontera.add(nuevo);
		Collections.sort(frontera,new OrdenarNodof());
	}
	
	public Nodo Elimina() {
		return frontera.remove(0);
	}
	
	public boolean EsVacia() {
		return frontera.isEmpty();
	}
	
	public String toString() {
		String nodosFron = "+++++Frontera: ";
		for(Nodo n : frontera) {
			nodosFron += n.GetEstado().GetNode().getID().toString() + " ";
		}
		return nodosFron;
	}
//	@Override
	/*Comparador de ordenacion de la frontera*/
//	public int compare(Nodo n1, Nodo n2) {
//		if (n1.GetF() < n2.GetF()) {
//			
//			return -1;
//			
//		} else if (n1.GetF() > n2.GetF()) {
//			
//			return 1;
//		}
//	
//		return 0;
//	}

}
