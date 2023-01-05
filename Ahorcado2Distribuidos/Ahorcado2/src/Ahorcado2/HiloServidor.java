package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Random;
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
		Random r = new Random();
		try(ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream())){
			
			this.jugadores.add(new DatosUsuario(this.s.getInetAddress().toString(), this.s.getPort()));
			
			int posicion = r.nextInt(this.jugadores.size());
			int aux = 0;
			//this.count.countDown();
			//Enviamos
			for(DatosUsuario du : this.jugadores) {
				if(aux == posicion) {
					du.setMaster(true);
				}
				oos.writeObject(du);
				oos.flush();
				aux++;
			}
			oos.writeObject(null); //Si no enviamos un objeto vacío salta una excepcion en la lectura, porque no lee nunca null
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



