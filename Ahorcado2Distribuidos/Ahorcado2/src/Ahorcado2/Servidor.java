package Ahorcado2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Servidor {
    public static void main(String[] args) {
    	List<DatosUsuario> usuarios = Collections.synchronizedList(new ArrayList<>());
    	ExecutorService pool = Executors.newFixedThreadPool(4);
    	//final CountDownLatch cont = new CountDownLatch(4);
        try(ServerSocket servidor = new ServerSocket(6666)){
            while(true) {
                Socket s = servidor.accept();
                pool.execute(new HiloServidor(s, usuarios));
            }
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
	}

}

