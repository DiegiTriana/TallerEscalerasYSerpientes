package serpienteescalera;

import java.util.HashMap;
import java.util.Map;

public class Tablero {
    private int tamaño;
    private Map<Integer, Integer> serpientes;
    private Map<Integer, Integer> escaleras;

    public Tablero(int tamaño) {
        this.tamaño = tamaño;
        serpientes = new HashMap<>();
        escaleras = new HashMap<>();
    }

    public void agregarSerpiente(int inicio, int fin) {
        serpientes.put(inicio, fin);
    }

    public void agregarEscalera(int inicio, int fin) {
        escaleras.put(inicio, fin);
    }

    public int getTamaño() {
        return tamaño;
    }

    public int verificarCasilla(int posicion) {
        if (serpientes.containsKey(posicion)) {
            return serpientes.get(posicion);
        } else if (escaleras.containsKey(posicion)) {
            return escaleras.get(posicion);
        }
        return posicion;
    }

    public Map<Integer, Integer> getSerpientes() {
        return serpientes;
    }

    public Map<Integer, Integer> getEscaleras() {
        return escaleras;
    }
}
