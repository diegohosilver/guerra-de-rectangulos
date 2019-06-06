package main.implementations;

public class Rectangulo {
	private int alto;
	private int ancho;
	
	/**
	 * Constructor de Rectangulo.
	 * @param alto - Alto del rectángulo.
	 * @param ancho - Ancho del rectángulo.
	 */
	public Rectangulo(int alto, int ancho) {
		this.alto = alto;
		this.ancho = ancho;
	}
	
	/**
	 * Devolver alto del rectángulo.
	 * @return Integer.
	 */
	public int alto() {
		return this.alto;
	}
	
	/**
	 * Devolver ancho del rectángulo.
	 * @return Integer.
	 */
	public int ancho() {
		return this.ancho;
	}
	
	/**
	 * Devolver cantidad de sectores ocupados por el rectángulo.
	 * @return Integer.
	 */
	public int area() {
		return alto * ancho;
	}

	/**
	 * Comparar rectángulo a otro rectángulo.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rectangulo other = (Rectangulo) obj;
		if (alto != other.alto)
			return false;
		if (ancho != other.ancho)
			return false;
		return true;
	}
}
