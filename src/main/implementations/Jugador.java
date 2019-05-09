package main.implementations;

import java.util.ArrayList;
import java.util.Random;

import main.interfaces.IRectangulo;
import main.interfaces.IJugador;

public class Jugador implements IJugador {
	private int area = 0;
	private ArrayList<IRectangulo> rectangulos = new ArrayList<IRectangulo>();
	private Random random = new Random();
	
	private int getIndiceRandom() {
		// Obtener un valor random entre 0 y la cantidad de rectangulos
		int indice = random.nextInt(rectangulos.size() - 1);
		return indice;
	}
	
	@Override
	public int getCantidadRectangulos() {
		return rectangulos.size();
	}
	
	@Override
	public IRectangulo getUltimoRectangulo() {
		return rectangulos.get(getCantidadRectangulos() - 1);
	}

	@Override
	public void addRectangulo(IRectangulo rectangulo) {
		this.rectangulos.add(rectangulo);
		this.area += rectangulo.area();
	}
	
	@Override
	public void eliminarRectanguloRandom() {
		this.rectangulos.remove(getIndiceRandom());
	}

	@Override
	public int getArea() {
		return area;
	}

	@Override
	public ArrayList<IRectangulo> getRectangulos() {
		return rectangulos;
	}
}
