import java.util.*;

public class Frontera{

	private ArrayList<Nodo> frontera;
	
	public Frontera() {
		CreaFrontera();
	}
	
	public void CreaFrontera() {
		frontera=new ArrayList<Nodo>();
	}
	
	public void Insertar(Nodo nuevo) {
		frontera.add(nuevo);
		Collections.sort(frontera,new OrdenarNodof()); //Una vez a�adimos un nuevo elemento a la frontera debemos de reordenarla
	}
	
	public void InsertaLista(ArrayList<Nodo> LN) {
		while(!LN.isEmpty()) {
			frontera.add(LN.remove(0));
		}
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

}
