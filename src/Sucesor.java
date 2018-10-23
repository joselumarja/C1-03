
public class Sucesor {
	private String accion;
	private Estado estadoNuevo;
	private double coste;
	
	public Sucesor(String accion, Estado estadoNuevo, double coste) {
		this.accion = accion;
		this.estadoNuevo = estadoNuevo;
		this.coste = coste;
	}
}
