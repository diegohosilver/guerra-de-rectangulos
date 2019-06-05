package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import main.implementations.*;

public class JugadorTests {
	
	private GR gr;

	@Before
	public void setUp() {
		gr = new GR(11,11,2);
	}
	
	@Test
	public void areaJugador_CuandoSeAgreganSectores_EquivaleACuatro() {		
		gr.jugar(1, 1);
		gr.jugar(1, 1);
		gr.jugar(1, 1);
		gr.jugar(1, 1);
		
		assertTrue(gr.area(1) == 2);
		assertTrue(gr.area(2) == 2);
	}
	
	@Test
	public void rectangulosJugador_CuandoSeEliminaUno_EquivaleACero() {
		gr.jugar(1, 1);
		gr.eliminarRect(1);
		
		assertTrue(gr.area(1) == 0);
		assertTrue(gr.rectangulos(1).size() == 0);
	}

	@Test
	public void jugador_CuandoSeObtieneUnicoRectangulo_EsIgualAlUltimoRectangulo() { 
		gr.jugar(2, 2);
		
		Rectangulo rectangulo = gr.ultimoRectangulo();
		
		assertTrue(gr.rectangulos(1).contains(rectangulo));
	}
}
