package main.implementations;

import java.util.HashMap;
import java.util.Random;

import main.shared.Sector;

public class Jugador{
	private int area = 0;
	private HashMap<Integer, Rectangulo> rectangulos = new HashMap<Integer, Rectangulo>();
	private Random random = new Random();
	
	private int getKeyRandom() {
		// Obtener un valor random entre 1 y la cantidad de rectangulos
		int indice = random.nextInt(rectangulos.size()) + 1;
		return indice;
	}
	
	public Rectangulo getUltimoRectangulo() {
		return rectangulos.get(getCantidadRectangulos());
	}
	
	public void eliminarRectanguloRandom() {
		this.rectangulos.remove(getKeyRandom());
		this.area = rectangulos.size();
	}

	public int getArea() {
		return area;
	}

	public HashMap<Integer, Rectangulo> getRectangulos() {
		return rectangulos;
	}
	
	public int getCantidadRectangulos() {
		return rectangulos.size();
	}
	
	public void addRectangulo(int order, Rectangulo rectangulo) {
		this.rectangulos.put(order, rectangulo);
		this.area += rectangulo.area();
	}
	
	public boolean sectorOcupado(int fila, int columna) {
		Sector sector = new Sector(fila, columna);
		
		for (Rectangulo rectangulo : rectangulos.values()) {
			if (rectangulo.getSectores().containsValue(sector)) {
				return true;
			}
		}
		
		return false;
	}
}
