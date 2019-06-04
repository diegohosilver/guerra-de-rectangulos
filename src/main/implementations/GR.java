package main.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

	public GR(int largo, int ancho, int cantidadJugadores) {
		_jugadorActual = 1;
		_tablero = new Tablero(largo, ancho, cantidadJugadores);
		_cantidadJugadores = cantidadJugadores;
		_cantidadTurnosSinJugar=0;
		_cantidadTurnos=0;
		_ganador = "";
	}

	public int area(int jugador) {
		return _tablero.area(jugador);
	}

	public Rectangulo ultimoRectangulo() {
		return _ultimoRectangulo;
	}

	public Coordenada ultimaCoordenada() {
		return _ultimaCoordenada;
	}

	// (No actualiza la última coordenada/rectangulo!)
	public void eliminarRect(Coordenada coordenada) {
		_tablero.eliminarRect(coordenada);
	}

	private boolean terminarJuego() {
		_jugadorActual ++;
		// Si jugadorActual excede la cantidad de jugadores, devolvemos el primero de la lista
		if (_jugadorActual > _cantidadJugadores) _jugadorActual = 1;
		// Si stackErrores tiene 2, terminamos el juego;
		return _cantidadTurnosSinJugar == 2;
	}

	private int getJugadorSiguiente() {
		int jugadorSiguiente = _jugadorActual + 1;

		// Si jugadorSiguiente excede la cantidad de jugadores, devolvemos el primero de la lista
		if (jugadorSiguiente > _cantidadJugadores) return 1;
		else return jugadorSiguiente;
	}

	private boolean puedoEscribir(Coordenada coordenada, Rectangulo rectangulo) {
		Sector primerSector = new Sector(coordenada.y, coordenada.x);
		Sector ultimoSector = new Sector(rectangulo.alto() + coordenada.y, rectangulo.ancho() + coordenada.x);
		ArrayList<Sector> sectoresRectangulo = Util.generarSectores(coordenada, new Rectangulo(rectangulo.alto(), rectangulo.ancho()));

		// Revisar si el rectángulo está dentro de los límites del tablero	
		if (!(primerSector.getFila() >= 0 && primerSector.getColumna() >= 0))
			return false;

		if (!(ultimoSector.getFila() < _tablero.largo() && ultimoSector.getColumna() < _tablero.ancho()))
			return false;

		// Revisar si los sectores están libres recorriendo los rectángulos de cada jugador
		for (Sector sector: sectoresRectangulo) {
			if (!_tablero.sectorLibre(new Coordenada(sector.getColumna(), sector.getFila()))) {
				return false;
			}
		}

		return true;
	}

	private void buscarSectorLibre(ArrayList<Integer> tiradas) {
		Sector sectorLibre = null;

		// Recorro todos los rectángulos del jugador actual
		for (Map.Entry<Coordenada, Rectangulo> rectangulo : _tablero.getRectangulos(_jugadorActual).entrySet()) {
			ArrayList<Sector> sectores = Util.generarSectores(rectangulo.getKey(), rectangulo.getValue());
			
			boolean libreArriba = true, libreAbajo = true, libreIzquierda = true, libreDerecha = true;

			for (int i = 0; i < sectores.size(); i ++) {
				Sector sector = sectores.get(i);
				
				// Arriba
				if (sector.getFila() - 1 >= 0) {
					libreArriba = libreArriba && _tablero.sectorLibre(new Coordenada(sector.getFila() - 1, sector.getColumna()));
					if (libreArriba) {
						sectorLibre = new Sector(sector.getFila() - 1, sector.getColumna(), Position.ARRIBA);
						continue;
					}
					else {
						sectorLibre = null;
					}
				}
				else {
					sectorLibre = null;
				}

				// Abajo
				if (sector.getFila() + 1 <= _tablero.largo() - 1) {
					libreAbajo = libreAbajo && _tablero.sectorLibre(new Coordenada(sector.getFila() + 1, sector.getColumna()));
					if (libreAbajo) {
						sectorLibre = new Sector(sector.getFila() + 1, sector.getColumna(), Position.ABAJO);
						continue;
					}
					else {
						sectorLibre = null;
					}
				}
				else {
					sectorLibre = null;
				}

				// Derecha
				if (sector.getColumna() + 1 <= _tablero.ancho() - 1) {
					libreDerecha = libreDerecha && _tablero.sectorLibre(new Coordenada(sector.getFila(), sector.getColumna() + 1));
					if (libreDerecha) {
						sectorLibre = new Sector(sector.getFila(), sector.getColumna() + 1, Position.DERECHA);
						continue;
					}
					else {
						sectorLibre = null;
					}
				}
				else {
					sectorLibre = null;
				}

				// Izquierda
				if (sector.getColumna() - 1 >= 0) {
					libreIzquierda = libreIzquierda && _tablero.sectorLibre(new Coordenada(sector.getFila(), sector.getColumna() - 1));
					if (libreIzquierda) {
						sectorLibre = new Sector(sector.getFila(), sector.getColumna() - 1, Position.IZQUIERDA);
						continue;
					}
					else {
						sectorLibre = null;
					}
				}
				else {
					sectorLibre = null;
				}
			}
			
			// Si luego de recorrer todos los jugadores, vemos que el sector está libre entonces intentamos jugar
			if (sectorLibre != null) {
				if (libreArriba || libreAbajo || libreIzquierda || libreDerecha) {
					if (realizarJugada(tiradas, sectorLibre)) {
						return;
					}
				}
			}
		}
		
		_cantidadTurnosSinJugar ++;
	}

	private boolean realizarJugada(ArrayList<Integer> tiradas, Sector sectorInicial) {
		Coordenada coordenada = new Coordenada(sectorInicial.getFila(), sectorInicial.getColumna());
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

		// Validar si puedo escribir, sino espejo el rectángulo
		if (!puedoEscribir) {
			switch(sectorARectangulo) {
			case ARRIBA:
			case ABAJO:
				offsetX = coordenada.x - (rectangulo.ancho() - 1);
				coordenada = new Coordenada(coordenada.x + offsetX, coordenada.y + offsetY);
				puedoEscribir = puedoEscribir(coordenada, rectangulo);
				break;
			case IZQUIERDA:
			case DERECHA:
				offsetY = coordenada.y - (rectangulo.alto() - 1);
				coordenada = new Coordenada(coordenada.x + offsetX, coordenada.y + offsetY);
				puedoEscribir = puedoEscribir(coordenada, rectangulo);
				break;
			}
		}

		if (puedoEscribir) {
			_tablero.pintar(coordenada, new Tupla<Integer, Integer>(rectangulo.ancho(), rectangulo.alto()), _jugadorActual);
			_ultimoRectangulo = rectangulo;
			_cantidadTurnosSinJugar = 0;
			return true;
		}

		return false;
	}

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

	private void realizarJugada(ArrayList<Integer> tiradas) {
		if (_tablero.area(_jugadorActual) == 0) {
			realizarJugadaInicial(tiradas);
		}
		else {
			buscarSectorLibre(tiradas);
		}

		if (terminarJuego()) {
			StringBuilder str = new StringBuilder("El ganador es el jugador número ");
			str.append(devolverGanador());
			_ganador =  str.toString();
		}
	}

	public String jugar() {
		Dado dado = new Dado();
		realizarJugada(dado.tirar(2));		
		return _ganador;
	}


	public String jugar(Integer dado1, Integer dado2) {
		realizarJugada(new ArrayList<Integer>(Arrays.asList(dado1, dado2)));
		return _ganador;
	}


	public Collection<Rectangulo> rectangulos(int jugador) {
		return _tablero.getRectangulos(jugador).values();
	}

	@Override
	public String toString() {
		return "cantTurnos:" + _cantTurnos + "\n" + " area1:" + _tablero.area(1) + "\n" + "area2:" + _tablero.area(2) + "\n" + " ganador:" + _ganador + "\n" + "tablero=" + "\n" + _tablero ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GR other = (GR) obj;
		if (_cantTurnos != other._cantTurnos)
			return false;
		if (_tablero == null) {
			if (other._tablero != null)
				return false;
		} else if (!_tablero.equals(other._tablero))
			return false;
		return true;
	}
}
