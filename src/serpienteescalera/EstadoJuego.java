package serpienteescalera;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import serpienteescalera.TurnoYPosicionJugador;

public class EstadoJuego {
    private Tablero tablero;
    private List<TurnoYPosicionJugador> jugadores;
    private int turnoActual;

    public EstadoJuego(int tamaño, int numJugadores) {
        tablero = new Tablero(tamaño);
        jugadores = new ArrayList<>();
        for (int i = 0; i < numJugadores; i++) {
            jugadores.add(new TurnoYPosicionJugador("Jugador " + (i + 1)));
        }
        turnoActual = 0;
    }

    public int tirarDado() {
        return (int) (Math.random() * 6) + 1;
    }

    public void moverJugador(int resultado, JTextArea registroEventos) {
        TurnoYPosicionJugador jugadorActual = jugadores.get(turnoActual);
        int nuevaPosicion = jugadorActual.getPosicion() + resultado;

        if (nuevaPosicion > tablero.getTamaño() * tablero.getTamaño()) {
            nuevaPosicion = tablero.getTamaño() * tablero.getTamaño();
        }

        if (tablero.getSerpientes().containsKey(nuevaPosicion)) {
            int finSerpiente = tablero.verificarCasilla(nuevaPosicion);
            nuevaPosicion = finSerpiente;
            registroEventos.append(jugadorActual.getNombre() + " ha caído en una serpiente y baja a la posición " + nuevaPosicion + "\n");
        } else if (tablero.getEscaleras().containsKey(nuevaPosicion)) {
            int finEscalera = tablero.verificarCasilla(nuevaPosicion);
            nuevaPosicion = finEscalera;
            registroEventos.append(jugadorActual.getNombre() + " ha subido una escalera y avanza a la posición " + nuevaPosicion + "\n");
        }

        jugadorActual.setPosicion(nuevaPosicion);
    }

    public boolean esFinDelJuego() {
        return jugadores.stream().anyMatch(jugador -> jugador.getPosicion() == tablero.getTamaño() * tablero.getTamaño());
    }

    public void terminarJuego() {
        TurnoYPosicionJugador ganador = jugadores.stream().filter(jugador -> jugador.getPosicion() == tablero.getTamaño() * tablero.getTamaño()).findFirst().orElse(null);
        if (ganador != null) {
            JOptionPane.showMessageDialog(null, ganador.getNombre() + " ganó la partida!");
        }
    }

    public void siguienteTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public TurnoYPosicionJugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public List<TurnoYPosicionJugador> getJugadores() {
        return jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void agregarSerpiente(int inicio, int fin) {
        tablero.agregarSerpiente(inicio, fin);
    }

    public void agregarEscalera(int inicio, int fin) {
        tablero.agregarEscalera(inicio, fin);
    }

    public void reiniciarJuego() {
        for (TurnoYPosicionJugador jugador : jugadores) {
            jugador.setPosicion(0);
        }
        turnoActual = 0;
    }
}
