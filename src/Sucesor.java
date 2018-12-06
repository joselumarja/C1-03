/*
 * Clase Sucesor la cual representa un sucesor de un estado con el estado nuevo, una especificación de la accion 
 * a realizar y el coste de realizar dicha accion
 */
public class Sucesor {
	private String accion; // String que representa la acción para llegar a un estado nuevo de otro estado
	private Estado estadoNuevo; // Estado nuevo a partir del cual llegamos realizando la acción anterior
	private double coste; // Coste para llegar al estado nuevo realizando la acción anterior

	/*
	 * Constructor de la clase Sucesor la cual crea una instancia de dicha clase con
	 * la accion para cambiar de estado, el nuevo estado al cual llegaremos y el
	 * coste de realizar dicha accion pasados como parametros
	 */
	public Sucesor(String accion, Estado estadoNuevo, double coste) {
		this.accion = accion;
		this.estadoNuevo = estadoNuevo;
		this.coste = coste;
	}

	/*
	 * Metodo get que devuelve una cadena con la accion que se realiza para cambiar
	 * de estado (con el estado nuevo, el estado padre y la calle por donde se lleva
	 * a cabo la accion)
	 */
	public String getAccion() {
		return accion;
	}

	/*
	 * Metodo get que devuelve el nuevo estado al cual llegaremos realizando la
	 * accion del sucesor
	 */
	public Estado getEstadoNuevo() {
		return estadoNuevo;
	}

	/*
	 * Metodo get que devuelve el coste de la accion
	 */
	public double getCoste() {
		return coste;
	}
}
