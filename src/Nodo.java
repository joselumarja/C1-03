
public class Nodo{
	private Nodo Padre; //Nodo padre
	private Estado Est;	//Estado
	private double Camino; //Camino recorrido
	private int P; //Profundidad a la que se encuentra el nodo
	private double F;	//Valor en la frontera

	public Nodo(Nodo Padre, Estado Est, double Camino, int P, double F) {
		this.Padre = Padre;
		this.Est = Est;
		this.P = P;
		this.F = F;
		this.Camino = Camino;
	}

	public Nodo getPadre() {
		return Padre;
	}
	public double GetF() {
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
	
	public boolean EstaRepetido() {
		Nodo Aux=getPadre();
		
		while(Aux!=null) {
			if(Aux.GetEstado().GetId().equals(this.GetEstado().GetId())) 
			{
				return true;
			}
			
			Aux=Aux.getPadre();
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String cadenaNodo;
		if(this.Padre == null) {
			cadenaNodo = "None";
		} else {
			cadenaNodo = Padre.GetEstado().GetNode().getID() + " -> " + Est.GetNode().getID();
		}
		cadenaNodo += " " + String.valueOf(Camino) + " " + String.valueOf(P) + " " + String.valueOf(F); 
		return cadenaNodo;
	}
}
