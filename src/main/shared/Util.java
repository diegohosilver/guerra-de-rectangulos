package main.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.implementations.Coordenada;
import main.implementations.Rectangulo;

public class Util {
	/**
	 * Devolver un listado de sectores a partir de una coordenada inicial y las medidas de un rect�ngulo.
	 * @param coordenada - Coordenada inicial (superior izquierda).
	 * @param rectangulo - Largo y ancho del rect�ngulo.
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
	 * Devolver un n�mero entero aleatorio dentro de los l�mites establecidos.
	 * @param minimo - Valor l�mite m�nimo.
	 * @param maximo - Valor l�mite m�ximo.
	 * @return Integer
	 */
    public static int numeroRandom(int minimo, int maximo) {
    	Random random = new Random();
    	
    	return random.nextInt(maximo) + minimo;
    }
}
