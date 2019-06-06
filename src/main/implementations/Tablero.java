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

	/**
	 * Constructor de tablero.
	 * @param largo - Largo del tablero.
	 * @param ancho - Ancho del tablero.
	 * @param cantidadJugadores - Cantidad de jugadores.
	 */
	public Tablero(int largo, int ancho, int cantidadJugadores) {
		for (int i = 0; i < cantidadJugadores; i++) {
			this.rectangulosPorJugador.put(i + 1, new HashMap<Coordenada, Rectangulo>());
		}

		this.cantidadJugadores = cantidadJugadores;

		this._tablero = new int[largo][ancho];
	}

	/**
	 * Devolver cantidad de sectores ocupados por el jugador dado.
	 * @param rectangulos - Listado de rectángulos del jugador.
	 * @return Integer.
	 */
	private int area (Collection<Rectangulo> rectangulos) {
		int area = 0;

		for (Rectangulo r: rectangulos) {
			area = area + r.area();
		}

		return area;
	}

	/**
	 * Devolver ancho del rectángulo;
	 * @return Integer.
	 */
	public int ancho() {
		return _tablero[0].length;
	}

	/**
	 * Devolver largo del rectángulo;
	 * @return Integer.
	 */
	public int largo() {
		return _tablero.length;
	}

	/**
	 * Devolver cantidad de sectores ocupados por el jugador dado.
	 * @param jugador - Número de jugador.
	 * @return Integer.
	 */
	public int area(int jugador) {
		if (rectangulosPorJugador.containsKey(jugador)) {
			Collection<Rectangulo> rectangulos = rectangulosPorJugador.get(jugador).values();
			return area(rectangulos);
		}
		else {
			throw new RuntimeException("Jugador desconocido!");
		}
	}

	/**
	 * Remover rectángulo del tablero.
	 * @param coordenada - Coordenada inicial del rectángulo.
	 * @param tamaño - Ancho y Largo del rectángulo.
	 */
	public void desPintar(Coordenada coordenada, Tupla<Integer, Integer> tamaño) {
		for (int fila = coordenada.y; fila < coordenada.y + tamaño.getB(); fila ++) {
			for (int columna = coordenada.x; columna < coordenada.x + tamaño.getA(); columna ++) {
				_tablero[fila][columna] = 0;
			}
		}
	}

	/**
	 * Agregar rectángulo al tablero y al jugador.
	 * @param coordenada - Coordenada inicial del rectángulo.
	 * @param tamaño - Ancho y Largo del rectángulo.
	 * @param jugador - Número de jugador.
	 */
	public void pintar(Coordenada coordenada, Tupla<Integer, Integer> tamaño, Integer jugador) {
		for (int fila = coordenada.y; fila < coordenada.y + tamaño.getB(); fila ++) {
			for (int columna = coordenada.x; columna < coordenada.x + tamaño.getA(); columna ++) {
				_tablero[fila][columna] = jugador;
			}
		}

		rectangulosPorJugador.get(jugador).put(coordenada, new Rectangulo(tamaño.getB(), tamaño.getA()));
	}

	/**
	 * Eliminar rectángulo en la coordenada inicial dada.
	 * @param coordenada - Coordenada inicial.
	 */
	public void eliminarRect(Coordenada coordenada) {
		for (int i = 0; i < cantidadJugadores; i++) {
			Rectangulo rectangulo = rectangulosPorJugador.get(i + 1).get(coordenada);

			if (rectangulo != null) {
				desPintar(coordenada, new Tupla<Integer, Integer>(rectangulo.ancho(), rectangulo.alto()));
				rectangulosPorJugador.get(i + 1).remove(coordenada);
				break;
			}
		}
	}

	/**
	 * Validar si la coordenada seleccionada está ocupada.
	 * @param coordenada - Coordenada a validar.
	 * @return true si está libre, false si está ocupada por otro jugador.
	 */
	public boolean sectorLibre(Coordenada coordenada) {
		return _tablero[coordenada.y][coordenada.x] == 0;
	}

	/**
	 * Devolver listado de rectángulos con sus correspondientes coordenadas inciales para el jugador dado.
	 * @param jugador - Número de jugador.
	 * @return HashMap<Coordenada, Rectangulo>
	 */
	public HashMap<Coordenada, Rectangulo> getRectangulos(Integer jugador) {
		return rectangulosPorJugador.get(jugador);
	}

	/**
	 * Mostrar tablero
	 */
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

	/**
	 * Comparar tablero a otro tablero
	 */
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
