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
		try(ServerSocket servidor = new ServerSocket(puerto);BufferedReader br= new BufferedReader(new InputStreamReader(System.in))){
			while(true) {
				try {
					String palabra=br.readLine();
					Socket s = servidor.accept();
					Respuesta r=new Respuesta();
					pool.submit(new HiloCliente(puerto,palabra,r));
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
			ObjectInputStream dis = new ObjectInputStream(s.getInputStream())){
			
			try {
				DatosUsuario du = (DatosUsuario) dis.readObject();
				while(du != null) {
					if(!s.getLocalAddress().toString().equals(du.getHost())) {
						jugadores.add(du);
					}
					else {
						if(du.getMaster()) {
							System.out.println("Eres maestro");
						}
					}
					du = (DatosUsuario) dis.readObject();
				}
				//Mostrar la lista
				int indice = 1;
				for(DatosUsuario user : jugadores) {
					System.out.println(indice + "- " + user.toString());
					indice++;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
