import java.util.*;

public class Estado implements Cloneable{
	private Punto node; //Nodo del estado
	private ArrayList<String> listNodes; //Lista de identificadores osmid de nodos que se desean visitar
	private String id; //Identificador encriptado en MD5 del estado actual

	public Estado(Punto node, ArrayList<String> listNodes, String id) {
		this.node = node;
		this.listNodes = listNodes;
		this.id = id;
	}

	public Punto GetNode() {
		return node;
	}

	public String GetId() {
		return id;
	}

	public ArrayList<String> getListNodes() {
		return listNodes;
	}

	//Cambia el estado actual dependiendo del nodo al cual queremos llegar
	public void ChangeState(Punto node) {
		this.node = node;
		listNodes.remove(node.getID());
		UpdateId();
	}

	//Actualiza el identificador 'id' del estado acutal
	public void UpdateId() {

		String Concat = node.getID();

		for (String node : listNodes) {
			Concat += node;
		}

		id = MD5(Concat);
	}

	//Encripta el estado acutal en MD5
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
		String listaRec = "-----Nodos por recorrer: ";
		for(String s : listNodes) {
			listaRec += s.toString() + " ";
		}
		return listaRec;
	}
}
