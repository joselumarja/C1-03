
public class Nodo{
	private Nodo Padre; //Nodo padre
	private Estado Est;	//Estado
	private double Camino; //Camino recorrido
	private int P; //Profundidad a la que se encuentra el nodo
	private int F;	//Valor en la frontera

	public Nodo(Nodo Padre, Estado Est, double Camino, int P, int F) {
		this.Padre = Padre;
		this.Est = Est;
		this.P = P;
		this.F = F;
		if (Padre == null) this.Camino = Camino;
		else this.Camino = Padre.Camino + Camino;
	}

	public Nodo getPadre() {
		return Padre;
	}
	public int GetF() {
		return F;
	}
	
	public Estado GetEstado() {
		return Est;
	}
	
	public double GetCamino() {
		return Camino;
	}
	
	public int GetProfundidad() {
		return P;
	}

}
