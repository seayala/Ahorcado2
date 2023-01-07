package Ahorcado2;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

//Para el cliente que ponga la palabra, actua como servidor
public class HiloCliente implements Runnable {
	private Socket s;
	private List<String> hanSalido; //Lista que contiene las palabras o caracteres que el usuario ya ha introducido
	private String palabra;
	private List<Character> letras = new ArrayList<>();

	public HiloCliente(Socket s, String palabra) {
		super();
		this.s = s;
		this.hanSalido = new ArrayList<String>();
		this.palabra = palabra;
		// Troceamos la palabra en una lista de caracteres
		for (int i = 0; i < palabra.length(); i++) {
			this.letras.add(palabra.charAt(i));
		}
	}

	//funcion que te devuelve las posiciones de una letra en uan determinada palabra
	private static List<Integer> contieneLetra(List<Character> letras, Character letra) {
		List<Integer> posiciones = new ArrayList<>();
		for (int i = 0; i < letras.size(); i++) {
			if (letras.get(i).equals(letra)) {
				posiciones.add(i);
			}
		}
		return posiciones;
	}

	@Override
	public void run() {
		Respuesta resp;
		try (BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
			ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream())) {
			
			// Enviamos el tamaÃ±o de la palabra
			String tamano = palabra.length() + "\r\n";
			bfw.write(tamano);
			bfw.flush();

			Respuesta r;
			int numFallos = 0;
			int salida = palabra.length();
			// Aqui comienza el juego del ahorcado
			do {
				r = new Respuesta();
				r.setNumFallos(numFallos);
				String intento = in.readLine();
				r.setIntento(intento);
				if (hanSalido.contains(intento)) {
					r.setEsta(false);
					r.setPosiciones(new ArrayList<>());
					// No se actualiza el numero de fallos puesto que el intento ya ha ha sido introducido anteriormente.
				} else if (intento.length() == 1) { //el intento es un letra
					Character letra = intento.charAt(0);
					List<Integer> posiciones = contieneLetra(letras, letra);
					if (posiciones.size() == 0) {
						r.setEsta(false);
						r.setNumFallos(r.getNumFallos() + 1);
						r.setPosiciones(new ArrayList<>());
					} else {
						r.setEsta(true);
						r.setPosiciones(posiciones);
						salida -= r.getPosiciones().size();
					}
				} else { // El intento es una palabra
					if (palabra.equals(intento)) {
						r.setEsta(true);
						r.setNumFallos(28); //se acierta la palabra el juego se ha acabado por lo que ponemos en respuesta un número de fallos imposible de alcanzar de manera naturayl en el juego
						r.setPosiciones(new ArrayList<>());
					} else {
						r.setEsta(false);
						r.setNumFallos(r.getNumFallos() + 2);
						r.setPosiciones(new ArrayList<>());
					}
				}
				//El jugador pierde el juego contra el master
				if(r.getNumFallos() >= 11 && r.getNumFallos() != 28) {
					r.setIntento(palabra);
				}
				// Enviamos el objeto respuesta con los datos de la parrtida
				out.writeObject(r);
				out.flush();
				hanSalido.add(intento);
				numFallos = r.getNumFallos();
			} while (r.getNumFallos() < 11 && salida != 0); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
