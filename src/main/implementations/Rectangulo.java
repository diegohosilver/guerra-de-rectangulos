package main.implementations;

public class Rectangulo {
	private int alto;
	private int ancho;
	
	public Rectangulo(int alto, int ancho) {
		this.alto = alto;
		this.ancho = ancho;
	}
	
	public int alto() {
		return this.alto;
	}
	
	public int ancho() {
		return this.ancho;
	}
	
	public int area() {
		return alto * ancho;
	}

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
