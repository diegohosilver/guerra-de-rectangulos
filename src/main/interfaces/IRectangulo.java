package main.interfaces;

import java.util.ArrayList;

import main.implementations.Sector;

public interface IRectangulo {
	public abstract int area();
	public abstract ArrayList<Sector> posicionesDisponibles();
}
