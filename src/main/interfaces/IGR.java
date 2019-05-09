package main.interfaces;

import java.util.ArrayList;

public interface IGR {
	public abstract IRectangulo ultimoRectangulo();
	public abstract int area(int numeroJugador);
	public abstract void eliminarRect();
	public abstract void jugar();
	public ArrayList<IRectangulo> rectangulos();
}