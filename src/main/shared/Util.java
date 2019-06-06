package main.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.implementations.Coordenada;
import main.implementations.Rectangulo;

public class Util {
	/**
	 * Devolver un listado de sectores a partir de una coordenada inicial y las medidas de un rectángulo.
	 * @param coordenada - Coordenada inicial (superior izquierda).
	 * @param rectangulo - Largo y ancho del rectángulo.
	 * @return ArrayList<Sector>
	 */
	public static ArrayList<Sector> generarSectores(Coordenada coordenada, Rectangulo rectangulo) {
		ArrayList<Sector> sectores = new ArrayList<Sector>();
		
		for (int fila = coordenada.y; fila < coordenada.y + rectangulo.alto(); fila ++) {
			for (int columna = coordenada.x; columna < coordenada.x + rectangulo.ancho(); columna ++) {
				sectores.add(new Sector(fila, columna));
			}
		}
		
		return sectores;
	}
    
	/**
	 * Devolver un número entero aleatorio dentro de los límites establecidos.
	 * @param minimo - Valor límite mínimo.
	 * @param maximo - Valor límite máximo.
	 * @return Integer
	 */
    public static int numeroRandom(int minimo, int maximo) {
    	Random random = new Random();
    	
    	return random.nextInt(maximo) + minimo;
    }
}
