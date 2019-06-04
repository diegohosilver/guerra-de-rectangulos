package main;

import main.implementations.GR;

public class Main {

	public static void main(String[] args) {
		// Automático
		GR gr = new GR(21,20);
		String ganador = gr.jugar();
		while (ganador == "") {
			ganador = gr.jugar();
			System.out.println("..." + gr.ultimoRectangulo().alto() +","
					+ gr.ultimoRectangulo().ancho());
		}
		System.out.println(gr);

		// Semi automático
		GR gr2 = new GR(21,22);
		String ganador2;
		ganador2 = gr2.jugar();
		ganador2 = gr2.jugar();
		ganador2 = gr2.jugar();
		gr2.eliminarRect();
		System.out.println(gr2);

	}
}