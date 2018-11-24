import java.util.*;

public class Estado implements Cloneable {
	private Punto node; // Nodo del estado
	private ArrayList<Punto> listNodes; // Lista de identificadores osmid de nodos que se desean visitar
	private double h;
	private String id; // Identificador encriptado en MD5 del estado actual

	public Estado(Punto node, ArrayList<Punto> listNodes, String id) {
		this.node = node;
		this.listNodes = listNodes;
		generateH();
		this.id = id;
	}

	public Punto GetNode() {
		return node;
	}

	public String GetId() {
		return id;
	}

	public ArrayList<Punto> getListNodes() {
		return listNodes;
	}

	// Cambia el estado actual dependiendo del nodo al cual queremos llegar
	public void ChangeState(Punto node) {
		this.node = node;
		listNodes.remove(node);
		UpdateId();
	}

	public double GetH() {
		return h;
	}

	private void generateH() {
		double LatitudDestino, LongitudDestino, LatitudActual, LongitudActual;
		LatitudDestino = listNodes.get(0).getLatitud();
		LongitudDestino = listNodes.get(0).getLongitud();
		LatitudActual = node.getLatitud();
		LongitudActual = node.getLongitud();

		double phiDest, phiAct, thetaDest, thetaAct;
		phiDest = Math.toRadians(LatitudDestino);
		phiAct = Math.toRadians(LatitudActual);
		thetaDest = Math.toRadians(LongitudDestino);
		thetaAct = Math.toRadians(LongitudActual);

		double d_phi, d_theta;
		d_phi = phiDest - phiAct;
		d_theta = thetaDest - thetaAct;

		double x, arc;
		x = Math.pow(Math.sin(d_phi / 2), 2.0) + Math.cos(phiDest) + Math.cos(phiAct)
				+ Math.pow(Math.sin(d_theta / 2), 2.0);
		x = Math.min(1.0, x);

		arc = 2 * Math.asin(Math.sqrt(x));
		
		this.h= arc*6371009;

	}

	// Actualiza el identificador 'id' del estado acutal
	public void UpdateId() {

		String Concat = node.getID();

		for (Punto node : listNodes) {
			Concat += node.getID();
		}

		id = MD5(Concat);
	}

	// Encripta el estado acutal en MD5
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

	public String toString() {
		String cadenaEstado = "Estoy en " + node.getID() + " y tengo que visitar " + listNodes.toString();
		return cadenaEstado;
	}
}
