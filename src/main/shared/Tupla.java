package main.shared;

public class Tupla<T1, T2> {
	private T1 a;
	private T2 b;
	
	/**
	 * Constructor Tupla genérica
	 * @param a - Valor primer elemento.
	 * @param b - Valor segundo elemento.
	 */
	public Tupla(T1 a, T2 b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Devolver primer elemento.
	 * @return T.
	 */
	public T1 getA() {
		return this.a;
	}
	
	/**
	 * Devolver segundo elemento.
	 * @return T.
	 */
	public T2 getB() {
		return this.b;
	}
}
