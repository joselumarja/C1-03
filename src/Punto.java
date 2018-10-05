import graphsDSESIUCLM.Element;

public class Punto implements Element{
	private String osmid;
	private double longitud;
	private double latitud;
	
	public Punto() {
		
	}
	
	/**
	 * @return the osmid
	 */
	public String getID() {
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
