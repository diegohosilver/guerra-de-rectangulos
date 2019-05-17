package main.implementations;

import java.util.HashMap;

import main.interfaces.*;
import main.shared.Medida;
import main.shared.Sector;

public class Rectangulo implements IRectangulo {
	private Medida<Integer, Integer> medidas;
	private HashMap<Integer, Sector> sectores;
	
	public Rectangulo(HashMap<Integer, Sector> sectores) {
		this.sectores = sectores;
	}
	
	public Rectangulo(Medida<Integer, Integer> medidas, HashMap<Integer, Sector> sectores) {
		this.medidas = medidas;
		this.sectores = sectores;
	}
	
	@Override
	public HashMap<Integer, Sector> getSectores() {
		return sectores;
	}
	
	@Override
	public int area() {
		return sectores.size();
	}
	
	@Override
	public Sector getUltimoSector() {
		return sectores.get(sectores.size());
	}
	
	@Override
	public Medida<Integer, Integer> getMedidas() {
		return medidas;
	}
	
	
}
