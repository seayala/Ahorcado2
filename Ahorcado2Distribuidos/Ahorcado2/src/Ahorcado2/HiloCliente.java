package Ahorcado2;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

//Para el cliente que ponga la palabra, actua como servidor
public class HiloCliente implements Runnable{
	private int puerto;
	private Socket s;
	
	public HiloCliente(int puerto) {
		super();
		this.puerto = puerto;
	}
	@Override
	public void run() {

	}
	
	

}
