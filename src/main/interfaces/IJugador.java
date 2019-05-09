package main.interfaces;

import java.util.ArrayList;

public interface IJugador {
	public abstract int getCantidadRectangulos();
	public abstract IRectangulo getUltimoRectangulo();
	public abstract void addRectangulo(IRectangulo rectangulo);
	public abstract void eliminarRectanguloRandom();
	public abstract int getArea();
	public abstract ArrayList<IRectangulo> getRectangulos();
}