package main.interfaces;

import java.util.ArrayList;

import main.shared.Sector;

public interface IRectangulo {
	public abstract ArrayList<Sector> posicionesDisponibles();
	public abstract ArrayList<Sector> getSectores();
	public abstract int area();
}
