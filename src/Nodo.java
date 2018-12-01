import java.math.BigDecimal;
import java.math.RoundingMode;

public class Nodo {
	private Nodo Padre; // Nodo padre
	private Estado Est; // Estado
	private double Camino; // Camino recorrido
	private int P; // Profundidad a la que se encuentra el nodo
	private double F; // Valor en la frontera
	private String Accion; // Accion que se ha realizado para ir del nodo padre a dicho nodo

	public Nodo(Nodo Padre, Estado Est, double Camino, int P, double F, String Accion) {
		this.Padre = Padre;
		this.Est = Est;
		this.P = P;
		this.F = F;
		this.Camino = Camino;
		this.Accion = Accion;
	}

	public String getAccion() {
		return Accion;
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
		Nodo Aux = getPadre();

		while (Aux != null) {
			if (Aux.GetEstado().GetId().equals(this.GetEstado().GetId())) {
				return true;
			}

			Aux = Aux.getPadre();
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		BigDecimal f_bd = new BigDecimal(Double.toString(F));
	    f_bd = f_bd.setScale(1, RoundingMode.HALF_UP);
	    BigDecimal camino_bd = new BigDecimal(Double.toString(Camino));
	    camino_bd = camino_bd.setScale(1, RoundingMode.HALF_UP);
		
		return Accion + camino_bd + " " + String.valueOf(P) + " " + f_bd;
	}
}
