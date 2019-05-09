package main.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.interfaces.IGR;
import main.interfaces.IRectangulo;
import main.interfaces.IJugador;

public class GR implements IGR {
	private int alto;
	private int ancho;
	private int jugadorActual;
	private Map<Integer, IJugador> jugadores = new HashMap<Integer, IJugador>();
	
	public GR(int alto, int ancho) {
		this.jugadores.put(1, new Jugador());
		this.jugadores.put(2, new Jugador());
		this.alto = alto;
		this.ancho = ancho;
	}
	
	private IJugador getJugadorSiguiente() {
		int jugadorSiguiente = jugadorActual + 1;
		
		// Si jugadorSiguiente excede la cantidad de jugadores, devolvemos el primero de la lista
		if (jugadorSiguiente > jugadores.size()) return jugadores.get(1);
		else return jugadores.get(jugadorSiguiente);
	}

	@Override
	public IRectangulo ultimoRectangulo() {
		return jugadores.get(jugadorActual).getUltimoRectangulo();
	}

	@Override
	public int area(int numeroJugador) {
		return jugadores.get(numeroJugador).getArea();
	}

	@Override
	public void eliminarRect() {
		getJugadorSiguiente().eliminarRectanguloRandom();		
	}

	@Override
	public void jugar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<IRectangulo> rectangulos(int numeroJugador) {
		return jugadores.get(numeroJugador).getRectangulos();
	}
	
	
}
