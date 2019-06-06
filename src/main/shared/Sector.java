package main.shared;

public class Sector {
	private int fila;
	private int columna;
	private Position posicion;
	
	/**
	 * Constructor Sector
	 * @param fila - Valor de Y.
	 * @param columna - Valor de X.
	 * @param posicion - Posicion del sector respecto al área contígua.
	 */
	public Sector(int fila, int columna, Position posicion) {
		this.fila = fila;
		this.columna = columna;
		this.posicion = posicion;
	}

	/*
	 * Constructor Sector
	 * @param fila - Valor de Y.
	 * @param columna - Valor de X.
	 */
	public Sector(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
	
	/**
	 * Comparar Sector con otro Sector.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sector other = (Sector) obj;
		if (columna != other.columna)
			return false;
		if (fila != other.fila)
			return false;
		return true;
	}
	
	/**
	 * Devolver información básica del sector.
	 */
	@Override
	public String toString() {
		return "fila: " + Integer.toString(fila) + ", columna:" + Integer.toString(columna) + ", posicion: " + posicion;
	}
	
	/**
	 * Devolver valor del eje Y.
	 * @return Integer.
	 */
	public int getFila() {
		return fila;
	}
	
	/**
	 * Devolver valor del eje X.
	 * @return Integer.
	 */
	public int getColumna() {
		return columna;
	}
	
	/**
	 * Devolver posición respecto al área contígua.
	 * @return Position.
	 */
	public Position getPosicion() {
		return posicion;
	}

}
