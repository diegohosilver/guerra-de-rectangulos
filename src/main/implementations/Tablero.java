package main.implementations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import main.shared.Tupla;

public class Tablero {
	// Rectángulos de cada jugador
	private HashMap<Integer, HashMap<Coordenada, Rectangulo>> rectangulosPorJugador = new HashMap<Integer, HashMap<Coordenada, Rectangulo>>();
	// Rectángulos de todos. 0: vacío, n: jugador n
	private int _tablero[][];
	// Cantidad de jugadores
	private int cantidadJugadores;

	public Tablero(int largo, int ancho, int cantidadJugadores) {
		for (int i = 0; i < cantidadJugadores; i++) {
			this.rectangulosPorJugador.put(i, new HashMap<Coordenada, Rectangulo>());
		}

		this.cantidadJugadores = cantidadJugadores;

		this._tablero = new int[largo][ancho];
	}

	private int area (Collection<Rectangulo> rectangulos) {
		int area = 0;

		for (Rectangulo r: rectangulos) {
			area = area + r.area();
		}

		return area;
	}

	public int ancho() {
		return _tablero[0].length;
	}

	public int largo() {
		return _tablero.length;
	}

	public int area(int jugador) {
		if (rectangulosPorJugador.containsKey(jugador)) {
			Collection<Rectangulo> rectangulos = rectangulosPorJugador.get(jugador).values();
			return area(rectangulos);
		}
		else {
			throw new RuntimeException("Jugador desconocido!");
		}
	}

	public void desPintar(Coordenada coordenada, Tupla<Integer, Integer> tamaño) {
		for (int columna = coordenada.x; columna < coordenada.x + tamaño.getA(); columna ++) {
			for (int fila = coordenada.y; fila < coordenada.y + tamaño.getB(); fila ++) {
				_tablero[columna][fila] = 0;
			}
		}
	}

	public void pintar(Coordenada coordenada, Tupla<Integer, Integer> tamaño, Integer jugador) {
		for (int columna = coordenada.x; columna < coordenada.x + tamaño.getA(); columna ++) {
			for (int fila = coordenada.y; fila < coordenada.y + tamaño.getB(); fila ++) {
				_tablero[columna][fila] = jugador;
			}
		}
	}

	public void eliminarRect(Coordenada coordenada) {
		for (int i = 0; i < cantidadJugadores; i++) {
			Rectangulo rectangulo = rectangulosPorJugador.get(i + 1).get(coordenada);

			if (rectangulo != null) {
				desPintar(coordenada, new Tupla<Integer, Integer>(rectangulo.alto(), rectangulo.ancho()));
				rectangulosPorJugador.get(i + 1).remove(coordenada);
				break;
			}
		}
	}
	
	public boolean sectorLibre(Coordenada coordenada) {
		return _tablero[coordenada.x][coordenada.y] == 0;
	}
	
	public HashMap<Coordenada, Rectangulo> getRectangulos(Integer jugador) {
		return rectangulosPorJugador.get(jugador);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<largo(); i++) {
			for (int j=0; j<ancho(); j++) {
				sb.append(_tablero[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		if (!Arrays.deepEquals(_tablero, other._tablero))
			return false;
		return true;
	}

}
