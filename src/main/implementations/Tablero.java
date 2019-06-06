package main.implementations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import main.shared.Tupla;

public class Tablero {
	// Rect�ngulos de cada jugador
	private HashMap<Integer, HashMap<Coordenada, Rectangulo>> rectangulosPorJugador = new HashMap<Integer, HashMap<Coordenada, Rectangulo>>();
	// Rect�ngulos de todos. 0: vac�o, n: jugador n
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
	 * @param rectangulos - Listado de rect�ngulos del jugador.
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
	 * Devolver ancho del rect�ngulo;
	 * @return Integer.
	 */
	public int ancho() {
		return _tablero[0].length;
	}

	/**
	 * Devolver largo del rect�ngulo;
	 * @return Integer.
	 */
	public int largo() {
		return _tablero.length;
	}

	/**
	 * Devolver cantidad de sectores ocupados por el jugador dado.
	 * @param jugador - N�mero de jugador.
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
	 * Remover rect�ngulo del tablero.
	 * @param coordenada - Coordenada inicial del rect�ngulo.
	 * @param tama�o - Ancho y Largo del rect�ngulo.
	 */
	public void desPintar(Coordenada coordenada, Tupla<Integer, Integer> tama�o) {
		for (int fila = coordenada.y; fila < coordenada.y + tama�o.getB(); fila ++) {
			for (int columna = coordenada.x; columna < coordenada.x + tama�o.getA(); columna ++) {
				_tablero[fila][columna] = 0;
			}
		}
	}

	/**
	 * Agregar rect�ngulo al tablero y al jugador.
	 * @param coordenada - Coordenada inicial del rect�ngulo.
	 * @param tama�o - Ancho y Largo del rect�ngulo.
	 * @param jugador - N�mero de jugador.
	 */
	public void pintar(Coordenada coordenada, Tupla<Integer, Integer> tama�o, Integer jugador) {
		for (int fila = coordenada.y; fila < coordenada.y + tama�o.getB(); fila ++) {
			for (int columna = coordenada.x; columna < coordenada.x + tama�o.getA(); columna ++) {
				_tablero[fila][columna] = jugador;
			}
		}

		rectangulosPorJugador.get(jugador).put(coordenada, new Rectangulo(tama�o.getB(), tama�o.getA()));
	}

	/**
	 * Eliminar rect�ngulo en la coordenada inicial dada.
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
	 * Validar si la coordenada seleccionada est� ocupada.
	 * @param coordenada - Coordenada a validar.
	 * @return true si est� libre, false si est� ocupada por otro jugador.
	 */
	public boolean sectorLibre(Coordenada coordenada) {
		return _tablero[coordenada.y][coordenada.x] == 0;
	}

	/**
	 * Devolver listado de rect�ngulos con sus correspondientes coordenadas inciales para el jugador dado.
	 * @param jugador - N�mero de jugador.
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
