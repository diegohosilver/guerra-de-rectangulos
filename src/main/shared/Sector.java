package main.shared;

public class Sector {
	private int fila;
	private int columna;
	private Position posicion;
	
	public Sector(int fila, int columna, Position posicion) {
		this.fila = fila;
		this.columna = columna;
		this.posicion = posicion;
	}
	
	public int getFila() {
		return fila;
	}
	
	public int getColumna() {
		return columna;
	}
	
	public Position getPosicion() {
		return posicion;
	}

}
