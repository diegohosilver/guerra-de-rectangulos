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
}
