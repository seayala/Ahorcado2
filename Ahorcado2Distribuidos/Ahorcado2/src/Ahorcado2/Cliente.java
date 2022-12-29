package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Cliente {
	public void soyServidor(int puerto) {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		List<String> hanSalido=new ArrayList<String>();
		try(ServerSocket servidor = new ServerSocket(puerto)){
			while(true) {
				try {
					String palabra=br.readLine();
					Socket s = servidor.accept();
					pool.submit(new HiloCliente(puerto,palabra));
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
	
	public static void main(String[] args) {
		List<DatosUsuario> jugadores = new ArrayList<>();
		try(Socket s = new Socket("localhost", 6666);
			DataInputStream dis = new DataInputStream(s.getInputStream())){
			
			String jugador = dis.readLine();
			while(jugador != null) {
				String split[] = jugador.split(" ? ");
				if(!s.getLocalAddress().toString().equals(split[0])) {
					jugadores.add(new DatosUsuario(split[0], Integer.parseInt(split[2])));
				}
				jugador = dis.readLine();
			}
			
			//Mostrar la lista
			int indice = 1;
			for(DatosUsuario user : jugadores) {
				System.out.println(indice + "- " + user.toString());
				indice++;
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


