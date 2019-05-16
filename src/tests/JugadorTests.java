package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;

import main.implementations.*;
import main.shared.Sector;

public class JugadorTests {
	
	private Jugador jugador;

	@Before
	public void setUp() {
		jugador = new Jugador();
	}
	
	@Test
	public void areaJugador_CuandoSeAgreganSectores_EquivaleACuatro() {		
		HashMap<Integer, Sector> sectores = new HashMap<Integer, Sector>() {
			{
				put(1, new Sector(1, 1));
				put(2, new Sector(1, 1));
				put(3, new Sector(1, 1));
				put(4, new Sector(1, 1));
			}
		};
		
		jugador.addRectangulo(1, new Rectangulo(sectores));
		
		assertTrue(jugador.getArea() == 4);
	}
	
	@Test
	public void rectangulosJugador_CuandoSeEliminaUno_EquivaleACero() {
		HashMap<Integer, Sector> sectores = new HashMap<Integer, Sector>() {
			{
				put(1, new Sector(1, 1));
			}
		};
		
		jugador.addRectangulo(1, new Rectangulo(sectores));
		jugador.eliminarRectanguloRandom();
		
		assertTrue(jugador.getCantidadRectangulos() == 0);
	}

}
