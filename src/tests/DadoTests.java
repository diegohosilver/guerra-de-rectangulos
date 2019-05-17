package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import main.implementations.Dado;

public class DadoTests {
	private Dado dado;
	
	@Before
	public void setUp() {
		dado = new Dado();
	}
	
	@Test
	public void dado_CuandoSeLePasaCantidadDeTiradas_DevuelveListaDeTiradas() {
		ArrayList<Integer> tiradas = dado.tirar(2);
		
		assertTrue(tiradas.size() == 2);
	}
	
	@Test
	public void dado_CuandoSeTira_TiradasEstanEntre1y6() {
		ArrayList<Integer> tiradas = dado.tirar(5);
		
		for (int i = 0; i < tiradas.size(); i ++) {
			assertTrue(tiradas.get(i) >= 1 && tiradas.get(i) <= 6);
		}
	}
}
