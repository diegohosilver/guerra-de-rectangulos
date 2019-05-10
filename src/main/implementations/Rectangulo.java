package main.implementations;

import java.util.ArrayList;

import main.interfaces.*;
import main.shared.Sector;

public class Rectangulo implements IRectangulo {
	private ArrayList<Sector> sectores;
	
	public Rectangulo(ArrayList<Sector> sectores) {
		this.sectores = sectores;
	}

	@Override
	public ArrayList<Sector> posicionesDisponibles() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Sector> getSectores() {
		return sectores;
	}
	
	@Override
	public int area() {
		return sectores.size();
	}
	
	
}
