import graphsDSESIUCLM.Element;

public class Arista implements Element{
	private String osmid; //OsmId de una calle.
	private String nombre; //Nombre de una calle.
	private double longitud; //Longitud de una calle.
	private String origen; //Identificador del nodo origen de una calle.
	private String destino; //Identificador del nodo destino de una calle.
	
	public Arista() {
		
	}

	/**
	 * @return the osmid + origen + destino
	 */
	public String getID() { //Metodo que devuelve un identificador único para representar una union entre dos nodos.
		return osmid + origen + destino;
	}

	/**
	 * @param osmid the osmid to set
	 */
	public void setOsmid(String osmid) {
		this.osmid = osmid;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the longitud
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public boolean IsAdyacentTo(String osmId) { //Comprueba si el osmId de un nodo es origen de una arista. 
		
		return osmId.equals(this.origen);
		
	}
	
	
}
