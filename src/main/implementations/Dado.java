package main.implementations;

import java.util.ArrayList;
import java.util.Random;

public class Dado {
	
	private Random random = new Random();
	
	private int tirarDado() {
		// Obtener un valor random entre 1 y 6
		int valorDado = random.nextInt(6) + 1;
		return valorDado;
	}

	public ArrayList<Integer> tirar(int cantidadDados) {
		ArrayList<Integer> tiradas = new ArrayList<Integer>();
		
		// Tirar la cantidad de dados solicitados
		for(int i = 0; i < cantidadDados; i++) {
			tiradas.add(tirarDado());
		}
		
		return tiradas;
	}
}
