import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * Clase Nodo que representa un nodo en el arbol de busqueda con todos los datos necesarios 
 * para llevar a cabo una busqueda eficiente
 */

public class Nodo {
	private Nodo Padre; // Nodo padre
	private Estado Est; // Estado del nodo
	private double Camino; // Coste del camino realizado hasta el momento
	private int P; // Profundidad a la que se encuentra el nodo
	private double F; // Valor en la frontera
	private String Accion; // Accion que se ha realizado para ir del nodo padre a dicho nodo

	/*
	 * Constructor de la clase Nodo la cual crea una instancia de dicha clase con un
	 * Nodo como padre, un estado, un coste de camino, una profundidad, un valor de
	 * la frontera y la accion para llegar a este nodo pasados como parametros
	 */
	public Nodo(Nodo Padre, Estado Est, double Camino, int P, double F, String Accion) {
		this.Padre = Padre;
		this.Est = Est;
		this.P = P;
		this.F = F;
		this.Camino = Camino;
		this.Accion = Accion;
	}

	/*
	 * Metodo get que devuelve una cadena con la accion que realiza el nodo padre
	 * para llegar a dicho nodo con ese estado
	 */
	public String getAccion() {
		return Accion;
	}

	/*
	 * Metodo get que devuelve el nodo padre de este nodo
	 */
	public Nodo getPadre() {
		return Padre;
	}

	/*
	 * Metodo get que devuelve el valor del nodo en la frontera
	 */
	public double GetF() {
		return F;
	}

	/*
	 * Metodo get que devuelve el estado del nodo
	 */
	public Estado GetEstado() {
		return Est;
	}

	/*
	 * Metodo get que devuelve el coste del camino desde el nodo raiz hasta este nodo
	 */
	public double GetCamino() {
		return Camino;
	}

	/*
	 * Metodo get que devuelve la profundidad del nodo
	 */
	public int GetProfundidad() {
		return P;
	}

	/*
	 * Metodo toString() sobreescrito el cual devuelve una cadena con el valor de
	 * los atributos
	 */
	@Override
	public String toString() {
		// Redondearemos los valores decimales a un solo decimal
		BigDecimal f_bd = new BigDecimal(Double.toString(F));
		f_bd = f_bd.setScale(1, RoundingMode.HALF_UP);
		BigDecimal camino_bd = new BigDecimal(Double.toString(Camino));
		camino_bd = camino_bd.setScale(1, RoundingMode.HALF_UP);

		return Accion + camino_bd + " " + String.valueOf(P) + " " + f_bd;
	}
}
