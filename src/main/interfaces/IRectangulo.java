package main.interfaces;

import java.util.HashMap;

import main.shared.Medida;
import main.shared.Sector;

public interface IRectangulo {
	public abstract HashMap<Integer, Sector> getSectores();
	public abstract int area();
	public abstract Sector getUltimoSector();
	public abstract Medida<Integer, Integer> getMedidas();
}
