package Ahorcado2;

import java.net.*;
import java.util.List;

//Para que el servidor conecte a los clientes
public class HiloServidor extends Thread{
	private Socket s;
	private List<String> jugadores;
	
	public HiloServidor(Socket s, List<String> jugadores) {
		super();
		this.s = s;
		this.jugadores = jugadores;
	}
	
	public void run() {
		
	}
	
	
}
