package main.implementations;

import java.util.HashMap;
import java.util.Random;

import main.interfaces.*;
import main.shared.Sector;

public class Jugador implements IJugador {
	private int area = 0;
	private HashMap<Integer, IRectangulo> rectangulos = new HashMap<Integer, IRectangulo>();
	private Random random = new Random();
	
	private int getKeyRandom() {
		// Obtener un valor random entre 1 y la cantidad de rectangulos
		int indice = random.nextInt(rectangulos.size()) + 1;
		return indice;
	}
	
	@Override
	public IRectangulo getUltimoRectangulo() {
		return rectangulos.get(getCantidadRectangulos());
	}
	
	@Override
	public void eliminarRectanguloRandom() {
		this.rectangulos.remove(getKeyRandom());
		this.area = rectangulos.size();
	}

	@Override
	public int getArea() {
		return area;
	}

	@Override
	public HashMap<Integer, IRectangulo> getRectangulos() {
		return rectangulos;
	}
	
	@Override
	public int getCantidadRectangulos() {
		return rectangulos.size();
	}
	
	@Override
	public void addRectangulo(int order, IRectangulo rectangulo) {
		this.rectangulos.put(order, rectangulo);
		this.area += rectangulo.area();
	}
	
	@Override
	public boolean sectorOcupado(int fila, int columna) {
		Sector sector = new Sector(fila, columna);
		
		for (IRectangulo rectangulo : rectangulos.values()) {
			if (rectangulo.getSectores().containsValue(sector)) {
				return true;
			}
		}
		
		return false;
	}
}
