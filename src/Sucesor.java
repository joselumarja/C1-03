
public class Sucesor {
	private String accion; //String que representa la acción para llegar a otro estado
	private Estado estadoNuevo; //Estado nuevo a partir del cual llegamos realizando la acción anterior
	private double coste; //Coste para llegar al estado nuevo realizando la acción anterior
	
	public Sucesor(String accion, Estado estadoNuevo, double coste) {
		this.accion = accion;
		this.estadoNuevo = estadoNuevo;
		this.coste = coste;
	}
	
	public String getAccion() {
		return accion;
	}
	
	public Estado getEstadoNuevo() {
		return estadoNuevo;
	}
	
	public double getCoste() {
		return coste;
	}
}
