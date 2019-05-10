package main.shared;

public class Medida<T1, T2> {
	private T1 alto;
	private T2 ancho;
	
	public Medida(T1 alto, T2 ancho) {
		this.alto = alto;
		this.ancho = ancho;
	}
	
	public T1 getAlto() {
		return this.alto;
	}
	
	public T2 getAncho() {
		return this.ancho;
	}
}
