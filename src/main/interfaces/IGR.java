package main.interfaces;

import java.util.HashMap;

import main.implementations.Rectangulo;
import main.shared.Medida;

public interface IGR {
	public abstract HashMap<Integer, IRectangulo> rectangulos(int numeroJugador);
	public abstract Rectangulo ultimoRectangulo();
	public abstract int area(int numeroJugador);
	public abstract void eliminarRect();
	public abstract boolean equals(IGR gr);
	public abstract String jugar();
	public abstract String jugar(Integer dado1, Integer dado2);
	public abstract Medida<Integer, Integer> getMedidas();
	public abstract String toString();
}
