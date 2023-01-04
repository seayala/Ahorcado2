package Ahorcado2;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

//Para el cliente que ponga la palabra, actua como servidor
public class HiloCliente implements Runnable{
	private int puerto;
	private Socket s;
	private List<String> hanSalido;
	private String palabra;
	private Respuesta r;
	
	public HiloCliente(int puerto, String palabra, Respuesta r) {
		super();
		this.puerto = puerto;
		this.hanSalido=new ArrayList<String>();
		this.palabra=palabra;
		this.r=r;
	}
	@Override
	public void run() {	
		while(r.getNumFallos()<11) {
		try(BufferedReader in= new BufferedReader(new InputStreamReader(s.getInputStream()));ObjectOutputStream out= new ObjectOutputStream(s.getOutputStream());BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Introduce la letra o la palabra que consideres adecuada");
			String intento=br.readLine();
			if(hanSalido.contains(intento)) {
				System.out.println("La palabra o letra introducida ya ha sido elegida con anterioridad");
			}else {	
				if(intento.length()==1) {
					if(palabra.contains(intento)) {
						r.setEsta(true);
						for (int i = 0;i < palabra.length(); i++){
							String letra=Character.toString(palabra.charAt(i)); //te separa la palabra del master en letras
							if(letra.equals(intento)) { 
								r.setLetraPosicion(i); //añade la posicion de la letra en la palabra (empieza en 0!!)
							}
						}
					}else {
						r.setNumFallos(r.getNumFallos()+1);
					}
					
				}else {
					if(palabra.equals(intento)) {
						r.setEsta(true);
						r.setNumFallos(13); //imposible llegar a 13 fallos si no has ganando la partida
						//JUEGO ACABADO
					}else {
						r.setIntento(r.getIntento()+2);
					}
				}
				out.writeObject(r);
				out.flush();
			}
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	

}
