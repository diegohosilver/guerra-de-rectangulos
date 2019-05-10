package main.interfaces;

import java.util.ArrayList;

import main.shared.Medida;

public interface IGR {
	public abstract ArrayList<IRectangulo> rectangulos(int numeroJugador);
	public abstract IRectangulo ultimoRectangulo();
	public abstract int area(int numeroJugador);
	public abstract void eliminarRect();
	public abstract boolean equals(IGR gr);
	public abstract void jugar();
	public abstract Medida<Integer, Integer> getMedidas();
}
