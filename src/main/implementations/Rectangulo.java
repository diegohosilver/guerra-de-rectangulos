package main.implementations;

import java.util.HashMap;

import main.shared.Medida;
import main.shared.Sector;

public class Rectangulo {
	private Medida<Integer, Integer> medidas;
	private HashMap<Integer, Sector> sectores;
	
	public Rectangulo(HashMap<Integer, Sector> sectores) {
		this.sectores = sectores;
	}
	
	public Rectangulo(Medida<Integer, Integer> medidas, HashMap<Integer, Sector> sectores) {
		this.medidas = medidas;
		this.sectores = sectores;
	}
	
	public HashMap<Integer, Sector> getSectores() {
		return sectores;
	}
	
	public int area() {
		return sectores.size();
	}
	
	public Sector getUltimoSector() {
		return sectores.get(sectores.size());
	}
	
	public Medida<Integer, Integer> getMedidas() {
		return medidas;
	}
}
