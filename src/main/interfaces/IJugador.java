package main.interfaces;

import java.util.HashMap;

public interface IJugador {
	public abstract void addRectangulo(int order, IRectangulo rectangulo);
	public abstract void eliminarRectanguloRandom();
	public abstract int getArea();
	public abstract int getCantidadRectangulos();
	public abstract IRectangulo getUltimoRectangulo();
	public abstract HashMap<Integer, IRectangulo> getRectangulos();
	public abstract boolean sectorOcupado(int fila, int columna);
}
