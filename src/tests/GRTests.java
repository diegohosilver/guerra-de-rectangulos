package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import main.implementations.GR;
import main.implementations.Rectangulo;

import org.junit.Before;

public class GRTests {
	private GR gr1, gr2, gr3;
	
	@Before
	public void setUp() {
		gr1 = new GR(11,10,2);
		gr2 = new GR(11,11,2);
		gr3 = new GR(11,10,2);
	}
	
	@Test
	public void testEquals1() {
		//gr1 tiene que ser igual que gr3 y distinto que gr2
		assertTrue(gr1.equals(gr3));
		assertTrue(!gr1.equals(gr2));
	}
	
	@Test
	public void testEquals2() {
		gr1.jugar(2,3);
		gr3.jugar(2,3);
		gr2.jugar(3,2);
		
		//gr1 tiene que ser igual que gr3 y distinto que gr2
		// DISCLAIMER: Este test jamás va a dar verdadero, salvo que jugar de gr1 y jugar de gr3 tengan exactamente las mismas posiciones
		assertTrue(gr1.equals(gr3));
		assertTrue(!gr1.equals(gr2));
	}
	
	@Test
	public void testArea() {
		gr1.jugar();
		Rectangulo h1 = gr1.ultimoRectangulo();
		gr1.jugar();
		Rectangulo h2 = gr1.ultimoRectangulo();
		
		assertEquals(gr1.area(1) + gr1.area(2), h1.area() + h2.area());
	}
	
	@Test
	public void testEliminar() {
		int areaini = gr1.area(1) + gr1.area(2);
		
		assertEquals(0, areaini); //área inicial 0
		
		gr1.jugar(); //turno Jug.1 agrega un rectángulo
		int area1 = gr1.area(1);
		int area = gr1.area(1) + gr1.area(2);
		
		gr1.jugar(); //turno Jug.2 agrega un rectángulo
		gr1.eliminarRect(2); //turno Jug.1 – elimina el único rectángulo del Jug.2
		
		int area2 = gr1.area(2);
		assertTrue(area2 <= area1);
		
		// queda el área total anterior al turno del jugador 2
		// se elimina uno al azar pero hay uno solo para eliminar.
		assertEquals(gr1.area(1) + gr1.area(2), area);
	}

}
