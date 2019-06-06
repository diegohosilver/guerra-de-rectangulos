package main.implementations;

import java.util.ArrayList;
import java.util.Random;

import main.shared.Util;

public class Dado {
	
	private int tirarDado() {
		// Obtener un valor random entre 1 y 6
		int valorDado = Util.numeroRandom(1, 6);
		return valorDado;
	}
	
	/**
	 * Tirar dados la cantidad de veces solicitada. 
	 * @param cantidadDados - Cantidad de dados a tirar.
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> tirar(int cantidadDados) {
		ArrayList<Integer> tiradas = new ArrayList<Integer>();
		
		// Tirar la cantidad de dados solicitados
		for(int i = 0; i < cantidadDados; i++) {
			tiradas.add(tirarDado());
		}
		
		return tiradas;
	}
}
