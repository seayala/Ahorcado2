package Ahorcado2;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

//Para el cliente que ponga la palabra, actua como servidor
public class HiloCliente implements Runnable {
	private Socket s;
	private List<String> hanSalido;
	private String palabra;
	private List<Character> letras = new ArrayList<>();

	public HiloCliente(Socket s, String palabra) {
		super();
		this.s = s;
		this.hanSalido = new ArrayList<String>();
		this.palabra = palabra;
		// Troceamos la palabra
		for (int i = 0; i < palabra.length(); i++) {
			this.letras.add(palabra.charAt(i));
		}
	}

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
			
			// Enviamos el tamaño de la palabra
			String tamaño = palabra.length() + "\r\n";
			bfw.write(tamaño);
			bfw.flush();

			Respuesta r;
			int numFallos = 0;
			int salida = palabra.length();
			// Juego
			do {
				r = new Respuesta();
				r.setNumFallos(numFallos);
				String intento = in.readLine();
				r.setIntento(intento);
				if (hanSalido.contains(intento)) {
					r.setEsta(false);
					r.setPosiciones(new ArrayList<>());
					// No se actualiza el número de fallos.
				} else if (intento.length() == 1) {
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
						r.setNumFallos(28); // Para en el cliente comprobarlo
						r.setPosiciones(new ArrayList<>());
					} else {
						r.setEsta(false);
						r.setNumFallos(r.getNumFallos() + 2);
						r.setPosiciones(new ArrayList<>());
					}
				}

				// Enviamos el objeto
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
