package main;

import main.implementations.GR;

public class Main {

	public static void main(String[] args) {
		GR gr = new GR(10, 10);
		gr.jugar(2, 2);
		gr.jugar(2, 2);
		gr.jugar(2, 2);
		System.out.println("Area: " + gr.area(1));
	}
}