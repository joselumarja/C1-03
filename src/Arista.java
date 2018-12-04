import graphsDSESIUCLM.Element;

/*
 * Clase Arista que representa una calle la cual une dos puntos (origen y destino)
 */

public class Arista implements Element {
	private String osmid; // OsmId de una calle.
	private String nombre; // Nombre de una calle.
	private double longitud; // Longitud de una calle.
	private String origen; // Identificador del nodo origen de una calle.
	private String destino; // Identificador del nodo destino de una calle.

	public Arista() {

	}

	/**
	 * @return the osmid + origen + destino Metodo get que devuelve un identificador
	 *         unico para representar una union entre dos nodos.
	 */
	public String getID() {
		return osmid + origen + destino;
	}

	/**
	 * @param osmid the osmid to set Metodo set que modifica el valor osmid de una
	 *              arista que une dos nodos.
	 */
	public void setOsmid(String osmid) {
		this.osmid = osmid;
	}

	/**
	 * @return the nombre Metodo get que devuelve el nombre de la calle que
	 *         representa la arista
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set Metodo set que modifica el nombre de la calle
	 *               que representa la arista
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the longitud Metodo get que devuelve la longitud de la calle que
	 *         representa la arista
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set Metodo set que modifica la longitud de la
	 *                 calle que representa la arista
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the origen Metodo get que devuelve el osmid del punto origen de dicha
	 *         arista
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set Metodo set que modifica el osmid del punto
	 *               origen de dicha arista
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the destino Metodo get que devuelve el osmid del punto destino de
	 *         dicha arista
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set Metodo set que modifica el osmid del punto
	 *                destino de dicha arista
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/*
	 * Comprueba si el osmId de un nodo es origen de una arista
	 */
	public boolean IsAdyacentTo(String osmId) {
		return osmId.equals(this.origen);

	}

}
