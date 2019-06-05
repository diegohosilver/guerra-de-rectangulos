package main.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.implementations.Coordenada;
import main.implementations.Rectangulo;

public class Util {
	public static ArrayList<Sector> generarSectores(Coordenada coordenada, Rectangulo rectangulo) {
		ArrayList<Sector> sectores = new ArrayList<Sector>();
		
		for (int fila = coordenada.y; fila < coordenada.y + rectangulo.alto(); fila ++) {
			for (int columna = coordenada.x; columna < coordenada.x + rectangulo.ancho(); columna ++) {
				sectores.add(new Sector(fila, columna));
			}
		}
		
		return sectores;
	}
	
    public static <T> List<T> intersectar(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public static int numeroRandom(int minimo, int maximo) {
    	Random random = new Random();
    	
    	return random.nextInt(maximo) + minimo;
    }
}
