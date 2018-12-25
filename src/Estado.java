import java.util.*;

/*
 * Clase Estado que representa un estado con un punto del mapa, la lista de nodos que quedan 
 * por recorrer, la heuristica de dicho estado y un identificador unico codificado en MD5
 */
public class Estado {
	private Punto node; // Nodo del estado
	private ArrayList<Punto> listNodes; // Lista de puntos osmid de nodos que se desean visitar
	private double h; // Heuristica del estado
	private String id; // Identificador encriptado en MD5 del estado actual

	/*
	 * Constructor de la clase Estado la cual crea una instancia de dicha clase con
	 * el nodo, la lista de nodos y el id como atributos que se pasan como
	 * parametros.
	 */
	public Estado(Punto node, ArrayList<Punto> listNodes, String id) {
		this.node = node;
		this.listNodes = listNodes;
		generateH();
		this.id = id;
	}

	/*
	 * Metodo get que devuelve el punto del estado
	 */
	public Punto GetNode() {
		return node;
	}

	/*
	 * Metodo get que devuelve el codigo MD5 del estado
	 */
	public String GetId() {
		return id;
	}

	/*
	 * Metodo get que devuelve la lista de puntos por visitar del estado
	 */
	public ArrayList<Punto> getListNodes() {
		return listNodes;
	}
	
	public String getListNodesToString() {
		String x="[";
		for (Punto n:listNodes) {
			x+=n.getID()+", ";
		}
		return x+="]";
	}

	/*
	 * Metodo que cambia el estado a partir del punto al cual queremos llegar
	 * (cambiando el nodo, actualizando la lista de puntos por visitar y
	 * actualizando el codigo MD5 con el nuevo estado)
	 */
	public void ChangeState(Punto node) {
		this.node = node;
		listNodes.remove(node);
		UpdateId();
	}

	/*
	 * Metodo get que devuelve la heuristica del estado
	 */
	public double GetH() {
		return h;
	}

	/*
	 * Metodo que genera el valor de la heuristica de un estado
	 */
	public void generateH() {
		double hmin = -1, haux;
		for (Punto p : listNodes) {

			haux = CalcDistance(node, p);
			if (hmin == -1 || haux < hmin) {
				hmin = haux;
			}

		}
		this.h = hmin;
	}

	/*
	 * Metodo que calcula la distancia minima entre dos puntos
	 */
	private double CalcDistance(Punto origen, Punto Destino) {
		double LatitudDestino, LongitudDestino, LatitudActual, LongitudActual;
		LatitudDestino = Destino.getLatitud();
		LongitudDestino = Destino.getLongitud();
		LatitudActual = origen.getLatitud();
		LongitudActual = origen.getLongitud();

		double phiDest, phiAct, thetaDest, thetaAct;
		phiDest = Math.toRadians(LatitudDestino);
		phiAct = Math.toRadians(LatitudActual);
		thetaDest = Math.toRadians(LongitudDestino);
		thetaAct = Math.toRadians(LongitudActual);

		double d_phi, d_theta;
		d_phi = phiDest - phiAct;
		d_theta = thetaDest - thetaAct;

		double x, arc;
		x = Math.pow(Math.sin(d_phi / 2), 2.0)
				+ Math.cos(phiAct) * Math.cos(phiDest) * Math.pow(Math.sin(d_theta / 2), 2.0);
		x = Math.min(1.0, x);

		arc = 2 * Math.asin(Math.sqrt(x));

		return arc * 6371009;

	}

	/*
	 * Actualiza el codigo MD5 'id' del estado acutal (del osmid del punto actual y
	 * los osmid de todos los puntos de la lista de nodos por recorrer)
	 */
	public void UpdateId() {

		String Concat = node.getID();

		for (Punto node : listNodes) {
			Concat += node.getID();
		}

		id = MD5(Concat);
	}

	/*
	 * Encripta el estado acutal (del osmid del punto actual y los osmid de todos
	 * los puntos de la lista de nodos por recorrer) en codigo MD5
	 */
	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	/*
	 * Metodo toString() sobreescrito el cual devuelve una cadena con el valor de
	 * los atributos
	 */
	public String toString() {
		String cadenaEstado = "Estoy en " + node.getID() + " y tengo que visitar " + listNodes.toString();
		return cadenaEstado;
	}
}
