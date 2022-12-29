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
	
	public HiloCliente(int puerto, String palabra) {
		super();
		this.puerto = puerto;
		this.hanSalido=new ArrayList<String>();
		this.palabra=palabra;
	}
	@Override
	public void run() {
		Respuesta r=new Respuesta();
		while(r.getNumFallos()<11) {
		try(BufferedReader in= new BufferedReader(new InputStreamReader(s.getInputStream()));ObjectOutputStream out= new ObjectOutputStream(s.getOutputStream());BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Introduce la letra o la palabra que consideres adecuada");
			String intento=br.readLine();
			if(hanSalido.contains(intento)) {
				System.out.println("La palabra o letra introducida ya ha sido elegida con anterioridad");
			}else {	
				if(intento.length()==1) {
					if(palabra.contains(intento)) {
						r.setEsta(true); // FALTA AÑADIR LAS POSICIONES
					}else {
						r.setNumFallos(r.getNumFallos()+1);
					}
					
				}else {
					if(palabra.equals(intento)) {
						r.setEsta(true); //JUEGO ACABADO
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
