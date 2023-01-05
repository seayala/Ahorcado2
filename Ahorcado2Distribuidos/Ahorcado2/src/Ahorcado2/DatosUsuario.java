package Ahorcado2;

import java.io.Serializable;

public class DatosUsuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 28L;
	private String host;
	private int port;
	private boolean master;
	
	public boolean getMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public DatosUsuario(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		this.master = false;
	}

	@Override
	public String toString() {
		if(this.master) {
			return "Direccion IP: " + host + ", puerto: " + port + " (Maestro)";
		}
		return "Direccion IP: " + host + ", puerto: " + port;
	}
	
	
}


