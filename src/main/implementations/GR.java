package main.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import main.shared.Tupla;
import main.shared.Util;
import main.shared.Position;
import main.shared.Sector;

public class GR {
	int _cantidadJugadores;
	int _cantidadTurnosSinJugar;
	int _cantidadTurnos;
	int _jugadorActual;
	String _ganador;
	private Tablero _tablero;
	private Rectangulo _ultimoRectangulo;
	private Coordenada _ultimaCoordenada;

	/**
	 * Constructor de GR
	 * @param largo - Cantidad de filas del tablero
	 * @param ancho - Cantidad de columnas del tablero
	 * @param cantidadJugadores - Cantidad de jugadores
	 */
	public GR(int largo, int ancho, int cantidadJugadores) {
		validacionesIniciales(largo, ancho, cantidadJugadores);
		
		_jugadorActual = 1;
		_tablero = new Tablero(largo, ancho, cantidadJugadores);
		_cantidadJugadores = cantidadJugadores;
		_cantidadTurnosSinJugar=0;
		_cantidadTurnos=0;
		_ganador = "";
	}
	
	/**
	 * Validar inicialización de GR
	 * @param largo - Cantidad de filas del tablero
	 * @param ancho - Cantidad de columnas del tablero
	 * @param cantidadJugadores - Cantidad de jugadores
	 */
	private void validacionesIniciales(int largo, int ancho, int cantidadJugadores) {
		if (largo <= 0  || ancho <= 0) {
			throw new RuntimeException("El tablero debe tener largo y ancho positivo");
		}
		
		if (cantidadJugadores <= 1) {
			throw new RuntimeException("Debe haber por lo menos 2 jugadores");
		}
	}
	
	/**
	 * Primera jugada de cada jugador. Establece una posición inicial aleatoria para el primer rectángulo
	 * @param tiradas - Dados que definen el largo y el ancho del rectángulo
	 */
	private void realizarJugadaInicial(ArrayList<Integer> tiradas) {
		int intentos = 0;
		boolean jugadaRealizada = false;

		while (intentos < 10) {
			Sector sector = new Sector(Util.numeroRandom(0, _tablero.largo() - 1), Util.numeroRandom(0, _tablero.ancho() - 1), Position.ABAJO);
			jugadaRealizada = realizarJugada(tiradas, sector);

			if (jugadaRealizada) {
				break;
			}

			intentos ++;
		}

		if (!jugadaRealizada) {
			_cantidadTurnosSinJugar++;
		}
		else {
			_cantidadTurnosSinJugar = 0;
		}
	}

	/**
	 * Realizar el ciclo del juego, validando si el jugador en cuestión juega por primera vez o no.
	 * Además se encarga de contar la cantidad de turnos y finalizar el juego si corresponde.
	 * @param tiradas - Dados que determinan el largo y el ancho del rectángulo
	 */
	private void ejecutarCicloJuego(ArrayList<Integer> tiradas) {
		if (_tablero.area(_jugadorActual) == 0) {
			realizarJugadaInicial(tiradas);
		}
		else {
			buscarSectorLibreYRealizarJugada(tiradas);
		}

		_cantidadTurnos++;

		if (terminarJuego()) {
			StringBuilder str = new StringBuilder("El ganador es el jugador número ");
			str.append(devolverGanador());
			_ganador =  str.toString();
		}
	}

	/**
	 * Validar si es posible escribir el rectángulo en la coordenada dada.
	 * Verifica que el rectángulo esté dentro de los límites del tablero, como así también que los sectores esten libres.
	 * @param coordenada - Coordenada inicial (superior izquierda)
	 * @param rectangulo - Largo y ancho del rectángulo
	 * @return true si puede escribir, false si no puede escribir
	 */
	private boolean puedoEscribir(Coordenada coordenada, Rectangulo rectangulo) {
		Sector primerSector = new Sector(coordenada.y, coordenada.x);
		Sector ultimoSector = new Sector(rectangulo.alto() + coordenada.y, rectangulo.ancho() + coordenada.x);
		ArrayList<Sector> sectoresRectangulo = Util.generarSectores(coordenada, new Rectangulo(rectangulo.alto(), rectangulo.ancho()));

		// Revisar si el rectángulo está dentro de los límites del tablero	
		if (!(primerSector.getFila() >= 0 && primerSector.getColumna() >= 0))
			return false;

		if (!(ultimoSector.getFila() < _tablero.largo() && ultimoSector.getColumna() < _tablero.ancho()))
			return false;

		// Revisar si los sectores están libres recorriendo el tablero
		for (Sector sector: sectoresRectangulo) {
			if (!_tablero.sectorLibre(new Coordenada(sector.getColumna(), sector.getFila()))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Busca algún sector libre contiguo al área del jugador e intenta realizar una jugada.
	 * @param tiradas - Dados que determinan el largo y el ancho del rectángulo.
	 */
	private void buscarSectorLibreYRealizarJugada(ArrayList<Integer> tiradas) {
		Sector sectorLibre = null;

		// Recorro todos los rectángulos del jugador actual
		for (Map.Entry<Coordenada, Rectangulo> rectangulo : _tablero.getRectangulos(_jugadorActual).entrySet()) {
			ArrayList<Sector> sectores = Util.generarSectores(rectangulo.getKey(), rectangulo.getValue());

			boolean libreArriba = true, libreAbajo = true, libreIzquierda = true, libreDerecha = true;

			for (int i = 0; i < sectores.size(); i ++) {
				Sector sector = sectores.get(i);

				// Arriba
				if (sector.getFila() - 1 >= 0) {
					libreArriba = _tablero.sectorLibre(new Coordenada(sector.getColumna(), sector.getFila() - 1));
					if (libreArriba) {
						sectorLibre = new Sector(sector.getFila() - 1, sector.getColumna(), Position.ARRIBA);
						if (realizarJugada(tiradas, sectorLibre)) {
							return;
						}
					}
				}

				// Abajo
				if (sector.getFila() + 1 <= _tablero.largo() - 1) {
					libreAbajo = _tablero.sectorLibre(new Coordenada(sector.getColumna(), sector.getFila() + 1));
					if (libreAbajo) {
						sectorLibre = new Sector(sector.getFila() + 1, sector.getColumna(), Position.ABAJO);
						if (realizarJugada(tiradas, sectorLibre)) {
							return;
						}
					}
				}

				// Derecha
				if (sector.getColumna() + 1 <= _tablero.ancho() - 1) {
					libreDerecha = _tablero.sectorLibre(new Coordenada(sector.getColumna() + 1, sector.getFila()));
					if (libreDerecha) {
						sectorLibre = new Sector(sector.getFila(), sector.getColumna() + 1, Position.DERECHA);
						if (realizarJugada(tiradas, sectorLibre)) {
							return;
						}
					}
				}

				// Izquierda
				if (sector.getColumna() - 1 >= 0) {
					libreIzquierda = _tablero.sectorLibre(new Coordenada(sector.getColumna() - 1, sector.getFila()));
					if (libreIzquierda) {
						sectorLibre = new Sector(sector.getFila(), sector.getColumna() - 1, Position.IZQUIERDA);
						if (realizarJugada(tiradas, sectorLibre)) {
							return;
						}
					}
				}
			}
		}

		// Si no pudo realizar jugada, incrementamos el contador de errores
		_cantidadTurnosSinJugar ++;
	}

	/**
	 * Intentar realizar jugada en el sector libre encontrado.
	 * @param tiradas - Dados que determinan el largo y el ancho del rectángulo.
	 * @param sectorInicial - Sector libre contiguo al área del jugador en turno.
	 * @return true si pudo realizar la escritura del rectángulo, false si el lugar elegido no está disponible.
	 */
	private boolean realizarJugada(ArrayList<Integer> tiradas, Sector sectorInicial) {
		Coordenada coordenada = new Coordenada(sectorInicial.getColumna(), sectorInicial.getFila());
		Rectangulo rectangulo = new Rectangulo(tiradas.get(0), tiradas.get(1));	
		Position sectorARectangulo = sectorInicial.getPosicion();
		int offsetY = 0;
		int offsetX = 0;

		// Validar la posicion del sector inicial respecto del rectángulo contiguo
		switch(sectorARectangulo) {
		case ARRIBA:
			offsetY = offsetY - (rectangulo.alto() - 1);
			break;
		case IZQUIERDA:
			offsetX = offsetX - (rectangulo.ancho() - 1);
			break;
		default:
			break;
		}

		coordenada = new Coordenada(coordenada.x + offsetX, coordenada.y + offsetY);
		boolean puedoEscribir = puedoEscribir(coordenada, rectangulo);

		// Validar si puedo escribir, sino espejo el rectángulo avanzando de a un sector
		if (!puedoEscribir) {
			switch(sectorARectangulo) {
			case ARRIBA:
			case ABAJO:
				int ejeXActual = coordenada.x;
				offsetX = coordenada.x;
				while(offsetX > ejeXActual - (rectangulo.ancho() - 1)) {
					offsetX--;
					coordenada = new Coordenada(offsetX, coordenada.y);
					puedoEscribir = puedoEscribir(coordenada, rectangulo);	
					if (puedoEscribir) {
						break;
					}
				}
				break;
			case IZQUIERDA:
			case DERECHA:
				int ejeYActual = coordenada.y;
				offsetY = coordenada.y;
				while(offsetY > ejeYActual - (rectangulo.alto() - 1)) {
					offsetY--;
					coordenada = new Coordenada(coordenada.x, offsetY);
					puedoEscribir = puedoEscribir(coordenada, rectangulo);	
					if (puedoEscribir) {
						break;
					}
				}
				break;
			}
		}

		if (puedoEscribir) {
			_tablero.pintar(coordenada, new Tupla<Integer, Integer>(rectangulo.ancho(), rectangulo.alto()), _jugadorActual);
			_ultimoRectangulo = rectangulo;
			_ultimaCoordenada = coordenada;
			_cantidadTurnosSinJugar = 0;
			return true;
		}

		return false;
	}
	
	/**
	 * Imprime el número del jugador ganador.
	 * @return número en formato String.
	 */
	private String devolverGanador() {
		int mayor = 0;
		int ganador = 0;

		for (int i = 0; i < _cantidadJugadores; i ++) {
			int area = _tablero.area(i + 1);

			if (area > mayor) {
				mayor = area;
				ganador = i + 1;
			}
		}

		return Integer.toString(ganador);		
	}
	
	/**
	 * Luego de cada turno validar si el juego debería terminar.
	 * @return true si el juego finalizó, false si todavía hay posibilidades de seguir jugando
	 */
	private boolean terminarJuego() {
		_jugadorActual ++;
		// Si jugadorActual excede la cantidad de jugadores, devolvemos el primero de la lista
		if (_jugadorActual > _cantidadJugadores) _jugadorActual = 1;
		// Si stackErrores tiene 2, terminamos el juego;
		return _cantidadTurnosSinJugar == _cantidadJugadores;
	}

	/**
	 * Jugar "tirando" los dados.
	 * @return Datos del jugador ganador si es que el juego finalizó.
	 */
	public String jugar() {
		Dado dado = new Dado();
		ejecutarCicloJuego(dado.tirar(2));		
		return _ganador;
	}

	/**
	 * Jugar con dados fijos.
	 * @param dado1 - Valor del primer dado.
	 * @param dado2 - Valor del segundo dado.
	 * @return Datos del jugador ganador si es que el juego finalizó
	 */
	public String jugar(Integer dado1, Integer dado2) {
		ejecutarCicloJuego(new ArrayList<Integer>(Arrays.asList(dado1, dado2)));
		return _ganador;
	}

	/**
	 * Devolver el área ocupada por el jugador dado.
	 * @param jugador - Número de jugador.
	 * @return Cantidad de sectores ocupados.
	 */
	public int area(int jugador) {
		return _tablero.area(jugador);
	}

	/**
	 * Devolver el último rectángulo creado.
	 * @return - Rectángulo.
	 */
	public Rectangulo ultimoRectangulo() {
		return _ultimoRectangulo;
	}

	/**
	 * Devolver última coordenada creada para generar un rectángulo.
	 * @return - Coordenada.
	 */
	public Coordenada ultimaCoordenada() {
		return _ultimaCoordenada;
	}

	/**
	 * Eliminar rectángulo del jugador dado.
	 * No actualiza el último rectángulo guardado, ni la última coordenada guardada.
	 * @param jugador - Número de jugador.
	 */
	public void eliminarRect(int jugador) {
		Coordenada coordenada = (Coordenada) _tablero.getRectangulos(jugador).keySet().toArray()[0];
		_tablero.eliminarRect(coordenada);
	}

	/**
	 * Devolver listado de rectángulos del jugador dado.
	 * @param jugador - Número de jugador
	 * @return Collection<Rectangulo>
	 */
	public Collection<Rectangulo> rectangulos(int jugador) {
		return _tablero.getRectangulos(jugador).values();
	}

	/**
	 * Devolver información general de GR.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("cantTurnos: " + _cantidadTurnos + "\n");
		
		for (int i = 0; i < _cantidadJugadores; i ++) {
			sb.append("area" + i + ": " + _tablero.area(i + 1) + "\n");
		}
		
		sb.append("ganador: " + _ganador + "\n");
		sb.append("tablero: \n" + _tablero);
		
		return sb.toString();
	}

	/**
	 * Comparar GR a otro GR.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GR other = (GR) obj;
		if (_cantidadTurnos != other._cantidadTurnos)
			return false;
		if (_tablero == null) {
			if (other._tablero != null)
				return false;
		} else if (!_tablero.equals(other._tablero))
			return false;
		return true;
	}
}
