package serpienteescalera;

public class TurnoYPosicionJugador {
    private String nombre;
    private int posicion;

    public TurnoYPosicionJugador(String nombre) {
        this.nombre = nombre;
        this.posicion = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void mover(int espacios) {
        posicion += espacios;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return nombre + " está en la posición " + posicion;
    }
}
