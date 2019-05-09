package main.shared;

import java.util.ArrayList;

import main.interfaces.IRectangulo;

public class Jugador {
	private int numero;
	private int area = 0;
	private ArrayList<IRectangulo> rectangulos = new ArrayList<IRectangulo>();
	
	public Jugador(int numero) {
		this.numero = numero;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area += area;
	}

	public ArrayList<IRectangulo> getRectangulos() {
		return rectangulos;
	}

	public void addRectangulo(IRectangulo rectangulo) {
		this.rectangulos.add(rectangulo);
	}

	public int getNumero() {
		return numero;
	}
}
