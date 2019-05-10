package main;

import main.implementations.GR;

public class Main {

	public static void main(String[] args) {
		GR gr = new GR(10, 10);
		gr.jugar();
		System.out.println("Area: " + gr.area(1));
	}
}