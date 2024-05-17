package serpienteescalera;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] opcionesTamaño = {"10x10", "13x13", "15x15"};
            String tamañoSeleccionado = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona el tamaño del tablero",
                    "Configuración",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesTamaño,
                    opcionesTamaño[0]);
            int tamañoTablero = Integer.parseInt(tamañoSeleccionado.split("x")[0]);

            int numJugadores;
            while (true) {
                String numJugadoresStr = JOptionPane.showInputDialog(
                        null,
                        "Ingrese el número de jugadores (2-4):");
                try {
                    numJugadores = Integer.parseInt(numJugadoresStr);
                    if (numJugadores >= 2 && numJugadores <= 4) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entre 2 y 4.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                }
            }

            TableroFrame frame = new TableroFrame(tamañoTablero, numJugadores);
            frame.setVisible(true);
        });
    }
}
