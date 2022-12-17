package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
    public static void main(String[] args) {
		//LAYO
    	ExecutorService hilos=Executors.newFixedThreadPool(4);
        try(ServerSocket servidor = new ServerSocket(10000)){
            while(true) {
                Socket s = servidor.accept();
                //hilos.execute(new hiloCliente());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //LAYO
	}

}
