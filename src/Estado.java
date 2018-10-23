import java.util.*;

public class Estado implements Cloneable{
	private Punto node;
	private ArrayList<String> listNodes;
	private String id;

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

	public void ChangeState(Punto node) {
		this.node = node;
		listNodes.remove(node.getID());
		UpdateId();
	}

	public void UpdateId() {

		String Concat = node.getID();

		for (String node : listNodes) {
			Concat += node;
		}

		id = MD5(Concat);
	}

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

	protected Object clone() {
		// TODO Auto-generated method stub
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println("No se puede duplicar");
		}
		return obj;
	}
}
