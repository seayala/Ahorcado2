package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Cliente {
	public void soyServidor(int puerto) {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		try(ServerSocket servidor = new ServerSocket(puerto)){
			while(true) {
				try {
					Socket s = servidor.accept();
					pool.submit(new HiloCliente(puerto));
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void soyJugador(String host, int puerto) {
		try(Socket jugador = new Socket(host, puerto)){
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
