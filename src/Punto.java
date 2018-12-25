import graphsDSESIUCLM.Element;

/*
 * Clase Punto que representa un punto del mapa del pueblo con un identificador unico 
 * y los valores de las coordenadas longitud y latitud
 */

public class Punto implements Element {
	private String osmid; // Osmid de un punto del mapa del pueblo.
	private double longitud; // Valor de la longitud de un punto del mapa del pueblo.
	private double latitud; // Valor de la latitud de un punto del mapa del pueblo.

	/*
	 * Constructor vacio el cual crea una instancia de dicha clase
	 */
	public Punto() {

	}
	
	public Punto(String osmid) {
		this.osmid=osmid;
	}

	/**
	 * Metodo get que devuelve un identificador unico para representar un punto del
	 * mapa del pueblo
	 */
	public String getID() { //
		return osmid;
	}

	/**
	 * Metodo set que modifica el identificador unico para representar un punto del
	 * mapa del pueblo con el valor pasado como parametro
	 */
	public void setOsmid(String osmid) {
		this.osmid = osmid;
	}

	/**
	 * Metodo get que devuelve el valor de la longitud de un punto del mapa del
	 * pueblo
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * Metodo set que modifica el valor de la longitud de un punto del mapa del
	 * pueblo por el valor pasado como parametro
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	/**
	 * Metodo get que devuelve el valor de la latitud de un punto del mapa del
	 * pueblo
	 */
	public double getLatitud() {
		return latitud;
	}

	/**
	 * Metodo set que modifica el valor de la latitud de un punto del mapa del
	 * pueblo por el valor pasado como parametro
	 */
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	@Override
	public String toString() {
		return osmid;
	}
}
