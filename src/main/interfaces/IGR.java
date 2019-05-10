package main.interfaces;

import java.util.ArrayList;

public interface IGR {
	public abstract ArrayList<IRectangulo> rectangulos(int numeroJugador);
	public abstract IRectangulo ultimoRectangulo();
	public abstract int area(int numeroJugador);
	public abstract void eliminarRect();
	public abstract boolean equals(IGR gr);
	public abstract void jugar();
	public abstract int getAlto();
	public abstract int getAncho();
}
