package Ahorcado2;

import java.io.Serializable;
import java.util.*;

public class Respuesta implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 27L;
	private boolean esta;
	private String intento;
	private List<Integer> posiciones;
	private int numFallos;
	public Respuesta(){
		super();
	        this.esta = false;
	        this.intento = "";
	        this.posiciones = new ArrayList<>();
	        this.numFallos = 0;
	}
	public boolean isEsta() {
		return esta;
	}
	public void setEsta(boolean esta) {
		this.esta = esta;
	}
	public String getIntento() {
		return intento;
	}
	public void setIntento(String intento) {
		this.intento = intento;
	}
	public List<Integer> getPosiciones() {
		return posiciones;
	}
	public void setPosiciones(List<Integer> posiciones) {
		this.posiciones = posiciones;
	}
	public int getNumFallos() {
		return numFallos;
	}
	public void setNumFallos(int numFallos) {
		this.numFallos = numFallos;
	}
	
	

}
