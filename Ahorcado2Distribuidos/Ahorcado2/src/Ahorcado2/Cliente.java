package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Cliente {
	private static boolean soyMaster = false;
	private static int puertoMaster;
	private static String hostMaster;

	public static void soyServidor(int puerto) {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		List<String> hanSalido = new ArrayList<String>();
		try (ServerSocket servidor = new ServerSocket(puerto)) {
			while (true) {
				try {
					// Lectura de la palabra desde la interfaz gráfica (aquí está por teclado)
					Scanner sc = new Scanner(System.in);
					System.out.println("Introduce la palabra:");
					String palabra = sc.nextLine();

					Socket s = servidor.accept();

					pool.submit(new HiloCliente(s, palabra));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void soyJugador(String host, int puerto) {
		Respuesta resp;
		try (Socket jugador = new Socket(hostMaster, puertoMaster);
				ObjectInputStream ois = new ObjectInputStream(jugador.getInputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(jugador.getInputStream()));
				BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(jugador.getOutputStream()))) {
			// Leemos el tamaño
			String t = in.readLine();
			int tam = Integer.parseInt(t);

			String aux = "";
			for (int i = 0; i < tam; i++) {
				aux += "_ ";
			}
			System.out.println("Palabra: " + aux);
			Scanner sc = new Scanner(System.in);
			int salida = tam;
			do {
				System.out.println("Introduzca el intento");
				String intento = sc.nextLine();
				intento = intento.toUpperCase() + "\r\n";

				// Enviamos el intento al servidor
				bfw.write(intento);
				bfw.flush();

				// Obtenemos el objeto respuesta
				resp = (Respuesta) ois.readObject();

				// Actualiamos la interfaz gráfica (aquí esta hecho todo para consola).
				if (resp.getNumFallos() == 28) {
					System.out.println("Palabra: " + resp.getIntento().toUpperCase());
					System.out.println("¡¡¡GANADOR!!!");
				} else if (resp.isEsta()) {
					List<Integer> p = resp.getPosiciones();
					String aux2 = aux;
					salida -= p.size();
					for (int i = 0; i < p.size(); i++) {
						for (int j = 0; j < tam; j++) {
							if (j == p.get(i)) {
								if (j == 0) {
									aux2 = resp.getIntento() + aux2.substring(2 * j + 1, 2 * tam);
								} else if (j == tam - 1) {
									aux2 = aux2.substring(0, 2 * j) + resp.getIntento();
								} else {
									aux2 = aux2.substring(0, 2 * j) + resp.getIntento()
											+ aux2.substring(2 * j + 1, 2 * tam);
								}

							}
						}
					}
					aux = aux2 + " ";
					muestraFallo(resp.getNumFallos());
					System.out.println("Palabra: " + aux);
				} else {
					System.out.println("La letra no está");
					muestraFallo(resp.getNumFallos());
					System.out.println("Palabra: " + aux);
				}
			} while (resp.getNumFallos() < 11 && salida != 0);
			if (salida == 0) {
				System.out.println("Palabra: " + aux);
				System.out.println("¡¡¡GANADOR!!!");
			} else if (resp.getNumFallos() != 28) {
				System.out.println("GAME OVER");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		List<DatosUsuario> jugadores = conexionInicial();
		if (soyMaster) {
			soyServidor(puertoMaster);
		} else {
			soyJugador(hostMaster, puertoMaster);
		}
	}

	public static List<DatosUsuario> conexionInicial() {
		List<DatosUsuario> jugadores = new ArrayList<>();
		try (Socket s = new Socket("localhost", 6666);
				ObjectInputStream dis = new ObjectInputStream(s.getInputStream())) {

			try {
				DatosUsuario du = (DatosUsuario) dis.readObject();
				while (du != null) {
					if (!s.getLocalAddress().toString().equals(du.getHost())) {
						if (du.getMaster()) {
							hostMaster = du.getHost();
							puertoMaster = du.getPort();
						}
						jugadores.add(du);
					} else if (du.getMaster()) {
						soyMaster = true;
						puertoMaster = du.getPort();
					}
					du = (DatosUsuario) dis.readObject();
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
		return jugadores;
	}

	public static void muestraFallo(int numFallos) {
		String cadena;
		switch (numFallos) {
		case 1:
			cadena = "__|_________" + "\r\n" + " |        | ";
			break;
		case 2:
			cadena = "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "__|_________"
					+ "\r\n" + " |        | ";
			break;
		case 3:
			cadena = "  _______" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |"
					+ "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 4:
			cadena = "  _______" + "\r\n" + "  |/" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |"
					+ "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 5:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n" + "  |" + "\r\n"
					+ "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 6:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |     o" + "\r\n" + "  |" + "\r\n" + "  |"
					+ "\r\n" + "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 7:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |     o" + "\r\n" + "  |     |" + "\r\n" + "  |"
					+ "\r\n" + "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 8:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |     o" + "\r\n" + "  |    /|" + "\r\n" + "  |"
					+ "\r\n" + "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 9:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |     o" + "\r\n" + "  |    /|\\" + "\r\n" + "  |"
					+ "\r\n" + "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 10:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |     o" + "\r\n" + "  |    /|\\" + "\r\n"
					+ "  |    / " + "\r\n" + "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		case 11:
			cadena = "  _______" + "\r\n" + "  |/    |" + "\r\n" + "  |     o" + "\r\n" + "  |    /|\\" + "\r\n"
					+ "  |    / \\" + "\r\n" + "  |" + "\r\n" + "__|_________" + "\r\n" + " |        | ";
			break;
		default:
			cadena = "";
		}
		System.out.println(cadena);
	}
}
