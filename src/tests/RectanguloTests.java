package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;

import main.implementations.*;
import main.shared.Sector;

public class RectanguloTests {
	
	private Rectangulo rectangulo;

	@Test
	public void rectangulo_CuandoSeAgreganSectores_UltimoSectorConCoordenadas() {
		HashMap<Integer, Sector> sectores = new HashMap<Integer, Sector>() {
			{
				put(1, new Sector(0, 0));
				put(2, new Sector(0, 1));
				put(3, new Sector(1, 0));
				put(4, new Sector(1, 1));
			}
		};
		
		
		rectangulo = new Rectangulo(sectores);
		
		assertEquals(rectangulo.getUltimoSector().getColumna(), 1);
		assertEquals(rectangulo.getUltimoSector().getFila(), 1);
	}
}
