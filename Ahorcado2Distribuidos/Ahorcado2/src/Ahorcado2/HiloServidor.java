package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//Para que el servidor conecte a los clientes
public class HiloServidor extends Thread{
	private Socket s;
	private List<DatosUsuario> jugadores;
	//private CountDownLatch count;
	
	public HiloServidor(Socket s, List<DatosUsuario> jugadores) {
		super();
		this.s = s;
		this.jugadores = jugadores;
		//this.count = count;
	}
	
	public void run() {
		try(DataOutputStream dos = new DataOutputStream(s.getOutputStream())){
			
			this.jugadores.add(new DatosUsuario(this.s.getInetAddress().toString(), this.s.getPort()));
			//this.count.countDown();
			//Enviamos
			for(DatosUsuario du : this.jugadores) {
				dos.writeBytes(du.getHost() + " ? " + du.getPort() + "\r\n");
				dos.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				this.s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}

