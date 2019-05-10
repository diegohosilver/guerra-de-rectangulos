package main.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.interfaces.*;
import main.shared.Sector;

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
		this.jugadorActual = 1;
	}
	
	private int terminarTurno() {
		jugadorActual ++;
		// Si jugadorActual excede la cantidad de jugadores, devolvemos el primero de la lista
		if (jugadorActual > jugadores.size()) jugadorActual = 1;
		return jugadorActual;
	}
	
	private IJugador getJugadorSiguiente() {
		int jugadorSiguiente = jugadorActual + 1;
		
		// Si jugadorSiguiente excede la cantidad de jugadores, devolvemos el primero de la lista
		if (jugadorSiguiente > jugadores.size()) return jugadores.get(1);
		else return jugadores.get(jugadorSiguiente);
	}
	
	private IJugador getJugadorActual() {
		return jugadores.get(jugadorActual);
	}

	@Override
	public IRectangulo ultimoRectangulo() {
		return jugadores.get(jugadorActual).getUltimoRectangulo();
	}

	@Override
	public int area(int numeroJugador) {
		return ((Jugador) jugadores.get(numeroJugador)).getArea();
	}

	@Override
	public void eliminarRect() {
		getJugadorSiguiente().eliminarRectanguloRandom();		
	}

	@Override
	public void jugar() {
		Dado dado = new Dado();
		ArrayList<Integer> tiradas = dado.tirar(2);
		ArrayList<Sector> sectores = new ArrayList<Sector>();
		int cantidadSectores = tiradas.get(0) * tiradas.get(1);
		
		for (int i = 0; i < cantidadSectores; i++) {
			sectores.add(new Sector(1, 1));
		}
		
		Jugador jugador = (Jugador) getJugadorActual();
		Rectangulo rectangulo = new Rectangulo(sectores);
		jugador.addRectangulo(rectangulo);
		
		terminarTurno();
	}

	@Override
	public ArrayList<IRectangulo> rectangulos(int numeroJugador) {
		return ((Jugador) jugadores.get(numeroJugador)).getRectangulos();
	}
	
	@Override
	public boolean equals(IGR gr) {
		if (((GR) gr).getAlto() == alto && ((GR) gr).getAncho() == ancho) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int getAlto() {
		return alto;
	}
	
	@Override
	public int getAncho() {
		return ancho;
	}
}
