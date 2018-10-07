import graphsDSESIUCLM.Element;

public class Punto implements Element{
	private String osmid; //OsmId de un nodo.
	private double longitud; //Longitud de un nodo.
	private double latitud; //Latitud de un nodo.
	
	public Punto() {
		
	}
	
	/**
	 * @return the osmid
	 */
	public String getID() { //Metodo que devuelve un identificador único para representar un vértice en el grafo.
		return osmid;
	}
	
	/**
	 * @param osmid the osmid to set
	 */
	public void setOsmid(String osmid) {
		this.osmid = osmid;
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
	 * @return the latitud
	 */
	public double getLatitud() {
		return latitud;
	}
	
	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	
}
