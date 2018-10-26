import java.util.Comparator;

public class Nodo{
	private Nodo Padre; //Nodo padre
	private Estado Est;	//Estado
	private int Camino; //Camino recorrido
	private int P; //Profundidad a la que se encuentra el nodo
	private int F;	//Valor en la frontera

	public Nodo(Nodo Padre, Estado Est, int Camino, int P, int F) {
		this.Padre = Padre;
		this.Est = Est;
		this.Camino = Camino;
		this.P = P;
		this.F = F;
	}

	public int GetF() {
		return F;
	}
	
	public Estado GetEstado() {
		return Est;
	}
	
	public int GetCamino() {
		return Camino;
	}
	
	public int GetProfundidad() {
		return P;
	}

}
