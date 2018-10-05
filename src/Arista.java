import graphsDSESIUCLM.Element;

public class Arista implements Element{
	private String osmid;
	private String nombre;
	private double longitud;
	private String origen;
	private String destino;
	
	public Arista() {
		
	}

	/**
	 * @return the osmid
	 */
	public String getID() {
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
	
	public boolean IsAdyacentTo(String osmId) {
		
		return osmId.equals(this.origen);
		
	}
	
	
}
