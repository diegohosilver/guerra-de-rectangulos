package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

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
		ArrayList<Sector> sectores = new ArrayList<Sector>() {
			{
				add(new Sector(1, 1));
				add(new Sector(1, 1));
				add(new Sector(1, 1));
				add(new Sector(1, 1));
			}
		};
		
		jugador.addRectangulo(new Rectangulo(sectores));
		
		assertTrue(jugador.getArea() == 4);
	}
	
	@Test
	public void rectangulosJugador_CuandoSeEliminaUno_EquivaleACero() {
		ArrayList<Sector> sectores = new ArrayList<Sector>() {
			{
				add(new Sector(1, 1));
			}
		};
		
		jugador.addRectangulo(new Rectangulo(sectores));
		jugador.eliminarRectanguloRandom();
		
		assertTrue(jugador.getCantidadRectangulos() == 0);
	}

}
