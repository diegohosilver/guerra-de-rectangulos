package main.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import main.interfaces.*;
import main.shared.Medida;
import main.shared.Position;
import main.shared.Sector;

public class GR implements IGR {
	private int alto;
	private int ancho;
	private Rectangulo ultimoRectangulo;
	private int jugadorActual;
	private HashMap<Integer, IJugador> jugadores = new HashMap<Integer, IJugador>();
	private HashMap<Integer, Medida<Integer, Integer>> posicionesIniciales = new HashMap<Integer, Medida<Integer, Integer>>();

	public GR(int alto, int ancho) {
		this.jugadores.put(1, new Jugador());
		this.posicionesIniciales.put(1, new Medida<Integer, Integer>(0, 0));
		this.jugadores.put(2, new Jugador());
		this.posicionesIniciales.put(2, new Medida<Integer, Integer>(0, ancho - 1));
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
	
	private IRectangulo generarRectangulo(Medida<Integer, Integer> coordenadasIniciales, Medida<Integer, Integer> medidas, int offsetY, int offsetX) {
		HashMap<Integer, Sector> sectores = new HashMap<Integer, Sector>();
		int alto = coordenadasIniciales.getAlto();
		int ancho = coordenadasIniciales.getAncho();
		int contadorSectores = 1;
		
		for (int y = alto + offsetY; y < medidas.getAlto() + alto + offsetY; y ++) {
			for (int x = ancho + offsetX; x < medidas.getAncho() + ancho + offsetX; x ++) {
				sectores.put(contadorSectores, new Sector(y, x));
				contadorSectores ++;
			}
		}
		
		return new Rectangulo(medidas, sectores);
	}
	
	private boolean puedoEscribir(IRectangulo rectangulo) {
		Sector primerSector = rectangulo.getSectores().get(1);
		Sector ultimoSector = rectangulo.getUltimoSector();
		
		// Revisar si el rectángulo está dentro de los límites del tablero	
		if (!(primerSector.getFila() >= 0 && primerSector.getColumna() >= 0))
			return false;
		
		if (!(ultimoSector.getFila() < alto && ultimoSector.getColumna() < ancho))
			return false;
		
		boolean libre = true;
		
		// Revisar si los sectores están libres
		for (Entry<Integer, IJugador> jugador : jugadores.entrySet()) {
			for (Entry<Integer, Sector> sector : rectangulo.getSectores().entrySet()) {
				Sector flagSector = sector.getValue();
				libre = libre && !jugador.getValue().sectorOcupado(flagSector.getFila(), flagSector.getColumna());
			}
		}
		
		return libre;
	}

	private Sector buscarSectorLibre() {
		int flagRectangulo = getJugadorActual().getCantidadRectangulos();
		Sector sectorLibre = null;

		// Recorro todos los rectángulos del jugador actual empezando por el último
		for (int x = 0; x < getJugadorActual().getCantidadRectangulos(); x ++) {

			Rectangulo rectangulo = (Rectangulo) getJugadorActual().getRectangulos().get(flagRectangulo);				
			int flagSector = rectangulo.area();

			// Recorro todos los sectores del rectángulo empezando por el último
			for (int y = 0; y < rectangulo.area(); y ++) {

				Sector sector = rectangulo.getSectores().get(flagSector);
				int flagJugadorActual = jugadorActual;

				// Recorro todos los jugadores para validar si el sector tiene espacios libres alrededor (y no está ocupado)
				for (int i = 0; i < jugadores.size(); i++) {

					Jugador jugador = (Jugador) jugadores.get(flagJugadorActual);			

					// Arriba
					if (sector.getFila() - 1 >= 0) {
						if (!jugador.sectorOcupado(sector.getFila() - 1, sector.getColumna())) {
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
					if (sector.getFila() + 1 <= alto - 1) {
						if (!jugador.sectorOcupado(sector.getFila() + 1, sector.getColumna())) {
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
					if (sector.getColumna() + 1 <= ancho - 1) {
						if (!jugador.sectorOcupado(sector.getFila(), sector.getColumna() + 1)) {
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
						if (!jugador.sectorOcupado(sector.getFila() + 1, sector.getColumna() - 1)) {
							sectorLibre = new Sector(sector.getFila(), sector.getColumna() + 1, Position.IZQUIERDA);
							continue;
						}
						else {
							sectorLibre = null;
						}
					}
					else {
						sectorLibre = null;
					}

					flagJugadorActual ++;

					if (flagJugadorActual > jugadores.size()) flagJugadorActual = 1;
				}

				// Si luego de recorrer todos los jugadores, vemos que el sector está libre entonces lo retornamos
				if (sectorLibre != null) {
					return sectorLibre;
				}

				// Sino procedemos al sector anterior del jugador actual
				flagSector --;
			}

			flagRectangulo --;
		}

		return sectorLibre;
	}

	private void realizarJugadaInicial(ArrayList<Integer> tiradas) {
		Medida<Integer, Integer> coordenadas = posicionesIniciales.get(jugadorActual);
		Medida<Integer, Integer> tamaño = new Medida<Integer, Integer>(tiradas.get(0), tiradas.get(1));	
		Rectangulo rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, 0, 0);
		boolean puedoEscribir = puedoEscribir(rectangulo);
		
		// Validar si puedo escribir, sino muevo el rectángulo
		
		if (!puedoEscribir) {
			// Mover arriba e izquierda/derecha
			// Esquina inferior derecha
			if (coordenadas.getAlto() == alto - 1 && coordenadas.getAncho() == ancho - 1) {
				rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, -tamaño.getAlto() + 1, -tamaño.getAncho() + 1);
				puedoEscribir = puedoEscribir(rectangulo);
			}
			// Esquina inferior izquierda
			if (coordenadas.getAlto() == alto - 1 && coordenadas.getAncho() == 0) {
				rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, -tamaño.getAlto() + 1, 0);
				puedoEscribir = puedoEscribir(rectangulo);
			}
		}
		
		if (!puedoEscribir) {
			// Mover a izquierda
			// Esquina superior derecha
			if (coordenadas.getAlto() == 0 && coordenadas.getAncho() == ancho - 1) {
				rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, 0, -tamaño.getAncho() + 1);
				puedoEscribir = puedoEscribir(rectangulo);
			}
		}
		
		if (puedoEscribir) {
			getJugadorActual().addRectangulo(getJugadorActual().getCantidadRectangulos() + 1, rectangulo);
		}
	}
	
	private void realizarJugada(ArrayList<Integer> tiradas, Sector sectorInicial) {
		Medida<Integer, Integer> coordenadas = new Medida<Integer, Integer>(sectorInicial.getFila(), sectorInicial.getColumna());
		Medida<Integer, Integer> tamaño = new Medida<Integer, Integer>(tiradas.get(0), tiradas.get(1));	
		Position sectorARectangulo = sectorInicial.getPosicion();
		int offsetY = 0;
		int offsetX = 0;
		
		// Validar la posicion del sector inicial respecto del rectángulo contiguo
		switch(sectorARectangulo) {
		case ARRIBA:
			offsetY = offsetY - (tamaño.getAlto() - 1);
			break;
		case IZQUIERDA:
			offsetX = offsetX - (tamaño.getAncho() - 1);
			break;
		default:
			break;
		}
		
		Rectangulo rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, offsetY, offsetX);
		boolean puedoEscribir = puedoEscribir(rectangulo);
		
		// Validar si puedo escribir, sino espejo el rectángulo
		if (!puedoEscribir) {
			switch(sectorARectangulo) {
			case ARRIBA:
			case ABAJO:
				offsetX = coordenadas.getAncho() - (tamaño.getAncho() - 1);
				rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, offsetY, offsetX);
				puedoEscribir = puedoEscribir(rectangulo);
				break;
			case IZQUIERDA:
			case DERECHA:
				offsetY = coordenadas.getAlto() - (tamaño.getAlto() - 1);
				rectangulo = (Rectangulo) generarRectangulo(coordenadas, tamaño, offsetY, offsetX);
				puedoEscribir = puedoEscribir(rectangulo);
				break;
			}
		}
		
		if (puedoEscribir) {
			getJugadorActual().addRectangulo(getJugadorActual().getCantidadRectangulos() + 1, rectangulo);
		}
	}

	private void realizarJugada(ArrayList<Integer> tiradas) {
		Jugador jugador = (Jugador) getJugadorActual();

		if (jugador.getCantidadRectangulos() == 0) {
			realizarJugadaInicial(tiradas);
		}
		else {
			Sector sectorContiguoInicial = buscarSectorLibre();
			
			if (sectorContiguoInicial != null) {
				realizarJugada(tiradas, sectorContiguoInicial);
			}
		}
		
		terminarTurno();
	}

	@Override
	public IRectangulo ultimoRectangulo() {
		return ultimoRectangulo;
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
		Dado dado = new Dado();
		realizarJugada(dado.tirar(2));		
	}

	@Override
	public void jugar(Integer dado1, Integer dado2) {
		realizarJugada(new ArrayList<Integer>(Arrays.asList(dado1, dado2)));
	}

	@Override
	public HashMap<Integer, IRectangulo> rectangulos(int numeroJugador) {
		return jugadores.get(numeroJugador).getRectangulos();
	}

	@Override
	public boolean equals(IGR gr) {
		if (gr.getMedidas().getAlto() == alto && gr.getMedidas().getAncho() == ancho) {
			return true;
		}

		return false;
	}

	@Override
	public Medida<Integer, Integer> getMedidas() {
		return new Medida<Integer, Integer>(alto, ancho);
	}
}
