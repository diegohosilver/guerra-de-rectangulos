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

	public Sector(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
	
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
	
	@Override
	public String toString() {
		return "fila: " + Integer.toString(fila) + ", columna:" + Integer.toString(columna) + ", posicion: " + posicion;
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
