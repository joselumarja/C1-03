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

		frontera.addAll(LN);
		Collections.sort(frontera,new OrdenarNodof());
	}
	
	public Nodo Elimina() {
		Nodo n=null;
		if(!EsVacia()) n=frontera.remove(0);
		return n;
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
	
	public boolean ContieneElNodo(Nodo n) {
		for(Nodo x:frontera) {
			if(n.GetEstado().GetId().equals(x.GetEstado().GetId())) return true;
		}
		
		return false;
	}
	
	public boolean ContieneElNodo(String id) {
		for(Nodo x:frontera) {
			if(id.equals(x.GetEstado().GetId())) return true;
		}
		
		return false;
	}

}
