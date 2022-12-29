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
		usuarios.add(new DatosUsuario("192.168.1.1", 25));
		usuarios.add(new DatosUsuario("192.168.1.2", 258));
		usuarios.add(new DatosUsuario("192.168.1.3", 25896));
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

