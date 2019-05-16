package main.interfaces;

import java.util.HashMap;

import main.shared.Medida;

public interface IGR {
	public abstract HashMap<Integer, IRectangulo> rectangulos(int numeroJugador);
	public abstract IRectangulo ultimoRectangulo();
	public abstract int area(int numeroJugador);
	public abstract void eliminarRect();
	public abstract boolean equals(IGR gr);
	public abstract void jugar();
	public abstract void jugar(Integer dado1, Integer dado2);
	public abstract Medida<Integer, Integer> getMedidas();
}
