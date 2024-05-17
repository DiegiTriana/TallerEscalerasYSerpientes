package serpienteescalera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TableroFrame extends JFrame {
    private EstadoJuego juego;
    private JPanel tableroPanel;
    private JPanel infoPanel;
    private JLabel turnoLabel;
    private JLabel dadoLabel;
    private JButton lanzarDadoButton;
    private JButton reiniciarButton;
    private JTextArea registroEventos;
    private int tamañoTablero;
    private int numJugadores;
    private Color[] coloresJugadores = {Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN};

    public TableroFrame(int tamañoTablero, int numJugadores) {
        this.tamañoTablero = tamañoTablero;
        this.numJugadores = numJugadores;
        juego = new EstadoJuego(tamañoTablero, numJugadores);
        inicializarComponentes();
        configurarTablero();
        agregarSerpientesYEscaleras();
    }

    private void inicializarComponentes() {
        setTitle("Serpientes y Escaleras");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableroPanel = new JPanel(new GridLayout(tamañoTablero, tamañoTablero)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarSerpientesYEscaleras(g);
            }
        };

        infoPanel = new JPanel(new GridLayout(5, 1));
        registroEventos = new JTextArea();
        registroEventos.setEditable(false);

        turnoLabel = new JLabel("Turno: " + juego.getJugadorActual().getNombre());
        dadoLabel = new JLabel("Dado: ");
        lanzarDadoButton = new JButton("Lanzar Dado");
        reiniciarButton = new JButton("Reiniciar Juego");
        reiniciarButton.setEnabled(false);

        lanzarDadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado = juego.tirarDado();
                dadoLabel.setText("Dado: " + resultado);
                juego.moverJugador(resultado, registroEventos);
                actualizarTablero();
                registrarEvento(juego.getJugadorActual().getNombre() + " lanzó un " + resultado + " y está en la posición " + juego.getJugadorActual().getPosicion());

                if (juego.esFinDelJuego()) {
                    juego.terminarJuego();
                    lanzarDadoButton.setEnabled(false);
                    reiniciarButton.setEnabled(true);
                } else {
                    juego.siguienteTurno();
                    turnoLabel.setText("Turno: " + juego.getJugadorActual().getNombre());
                }
            }
        });

        reiniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.reiniciarJuego();
                actualizarTablero();
                registroEventos.setText("");
                lanzarDadoButton.setEnabled(true);
                reiniciarButton.setEnabled(false);
                turnoLabel.setText("Turno: " + juego.getJugadorActual().getNombre());
            }
        });

        infoPanel.add(turnoLabel);
        infoPanel.add(dadoLabel);
        infoPanel.add(lanzarDadoButton);
        infoPanel.add(reiniciarButton);
        infoPanel.add(new JScrollPane(registroEventos));

        add(tableroPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
    }

    private void configurarTablero() {
        tableroPanel.setLayout(new GridLayout(tamañoTablero, tamañoTablero));

        for (int fila = tamañoTablero - 1; fila >= 0; fila--) {
            if (fila % 2 == 0) {
                for (int columna = 0; columna < tamañoTablero; columna++) {
                    int numeroCasilla = (fila * tamañoTablero) + (columna + 1);
                    JLabel casilla = new JLabel(String.valueOf(numeroCasilla), SwingConstants.CENTER);
                    casilla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPanel.add(casilla);
                }
            } else {
                for (int columna = tamañoTablero - 1; columna >= 0; columna--) {
                    int numeroCasilla = (fila * tamañoTablero) + (columna + 1);
                    JLabel casilla = new JLabel(String.valueOf(numeroCasilla), SwingConstants.CENTER);
                    casilla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPanel.add(casilla);
                }
            }
        }
    }

    private void actualizarTablero() {
        tableroPanel.removeAll();
        configurarTablero();
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            TurnoYPosicionJugador jugador = juego.getJugadores().get(i);
            int fila = (jugador.getPosicion() - 1) / tamañoTablero;
            int columna;
            if (fila % 2 == 0) {
                columna = (jugador.getPosicion() - 1) % tamañoTablero;
            } else {
                columna = tamañoTablero - 1 - ((jugador.getPosicion() - 1) % tamañoTablero);
            }
            int posicion = (tamañoTablero - fila - 1) * tamañoTablero + columna;
            if (posicion >= 0 && posicion < tamañoTablero * tamañoTablero) {
                JLabel casilla = (JLabel) tableroPanel.getComponent(posicion);
                String textoActual = casilla.getText();
                casilla.setText("<html>" + textoActual + "<br>" + "J" + (i + 1) + "</html>");
                casilla.setForeground(coloresJugadores[i]);
            }
        }
        tableroPanel.revalidate();
        tableroPanel.repaint();
    }

    private void registrarEvento(String evento) {
        registroEventos.append(evento + "\n");
    }

    private void agregarSerpientesYEscaleras() {
        switch (tamañoTablero) {
            case 10:
                agregarSerpientesYEscaleras10x10();
                break;
            case 13:
                agregarSerpientesYEscaleras13x13();
                break;
            case 15:
                agregarSerpientesYEscaleras15x15();
                break;
        }
    }

    private void agregarSerpientesYEscaleras10x10() {
        juego.agregarSerpiente(16, 6);
        juego.agregarSerpiente(47, 26);
        juego.agregarSerpiente(49, 11);
        juego.agregarSerpiente(56, 53);
        juego.agregarSerpiente(62, 19);
        juego.agregarSerpiente(64, 60);
        juego.agregarSerpiente(87, 24);
        juego.agregarSerpiente(93, 73);
        juego.agregarSerpiente(95, 75);
        juego.agregarSerpiente(98, 78);

        juego.agregarEscalera(1, 38);
        juego.agregarEscalera(4, 14);
        juego.agregarEscalera(9, 31);
        juego.agregarEscalera(21, 42);
        juego.agregarEscalera(28, 84);
        juego.agregarEscalera(36, 44);
        juego.agregarEscalera(51, 67);
        juego.agregarEscalera(71, 91);
        juego.agregarEscalera(80, 100);
    }

    private void agregarSerpientesYEscaleras13x13() {
        // Serpientes
        juego.agregarSerpiente(51, 19);
        juego.agregarSerpiente(59, 38);
        juego.agregarSerpiente(65, 47);
        juego.agregarSerpiente(82, 24);
        juego.agregarSerpiente(93, 73);
        juego.agregarSerpiente(99, 56);
        juego.agregarSerpiente(104, 87);
        juego.agregarSerpiente(110, 89);
        juego.agregarSerpiente(117, 76);
        juego.agregarSerpiente(124, 95);

        // Escaleras
        juego.agregarEscalera(4, 25);
        juego.agregarEscalera(11, 35);
        juego.agregarEscalera(23, 45);
        juego.agregarEscalera(28, 49);
        juego.agregarEscalera(36, 59);
        juego.agregarEscalera(42, 63);
        juego.agregarEscalera(48, 68);
        juego.agregarEscalera(54, 75);
        juego.agregarEscalera(62, 85);
        juego.agregarEscalera(69, 92);
        juego.agregarEscalera(78, 100);
    }

    private void agregarSerpientesYEscaleras15x15() {
        // Serpientes
        juego.agregarSerpiente(55, 13);
        juego.agregarSerpiente(62, 39);
        juego.agregarSerpiente(69, 42);
        juego.agregarSerpiente(78, 25);
        juego.agregarSerpiente(84, 48);
        juego.agregarSerpiente(93, 53);
        juego.agregarSerpiente(101, 67);
        juego.agregarSerpiente(107, 77);
        juego.agregarSerpiente(114, 90);
        juego.agregarSerpiente(119, 81);
        juego.agregarSerpiente(126, 94);
        juego.agregarSerpiente(133, 105);
        juego.agregarSerpiente(137, 111);
        juego.agregarSerpiente(142, 120);

        // Escaleras
        juego.agregarEscalera(3, 23);
        juego.agregarEscalera(8, 30);
        juego.agregarEscalera(16, 39);
        juego.agregarEscalera(22, 47);
        juego.agregarEscalera(27, 50);
        juego.agregarEscalera(35, 60);
        juego.agregarEscalera(41, 66);
        juego.agregarEscalera(46, 70);
        juego.agregarEscalera(54, 80);
        juego.agregarEscalera(61, 88);
        juego.agregarEscalera(68, 97);
        juego.agregarEscalera(74, 105);
        juego.agregarEscalera(79, 113);
        juego.agregarEscalera(86, 122);
        juego.agregarEscalera(91, 130);
    }

    private void dibujarSerpientesYEscaleras(Graphics g) {
        Map<Integer, Integer> serpientes = juego.getTablero().getSerpientes();
        Map<Integer, Integer> escaleras = juego.getTablero().getEscaleras();

        for (Map.Entry<Integer, Integer> entrada : serpientes.entrySet()) {
            dibujarLinea(g, entrada.getKey(), entrada.getValue(), Color.GREEN);
        }

        for (Map.Entry<Integer, Integer> entrada : escaleras.entrySet()) {
            dibujarLinea(g, entrada.getKey(), entrada.getValue(), Color.BLUE);
        }
    }

    private void dibujarLinea(Graphics g, int inicio, int fin, Color color) {
        int filaInicio = (inicio - 1) / tamañoTablero;
        int columnaInicio;
        if (filaInicio % 2 == 0) {
            columnaInicio = (inicio - 1) % tamañoTablero;
        } else {
            columnaInicio = tamañoTablero - 1 - ((inicio - 1) % tamañoTablero);
        }

        int filaFin = (fin - 1) / tamañoTablero;
        int columnaFin;
        if (filaFin % 2 == 0) {
            columnaFin = (fin - 1) % tamañoTablero;
        } else {
            columnaFin = tamañoTablero - 1 - ((fin - 1) % tamañoTablero);
        }

        int xInicio = columnaInicio * (tableroPanel.getWidth() / tamañoTablero) + (tableroPanel.getWidth() / tamañoTablero) / 2;
        int yInicio = (tamañoTablero - filaInicio - 1) * (tableroPanel.getHeight() / tamañoTablero) + (tableroPanel.getHeight() / tamañoTablero) / 2;
        int xFin = columnaFin * (tableroPanel.getWidth() / tamañoTablero) + (tableroPanel.getWidth() / tamañoTablero) / 2;
        int yFin = (tamañoTablero - filaFin - 1) * (tableroPanel.getHeight() / tamañoTablero) + (tableroPanel.getHeight() / tamañoTablero) / 2;

        g.setColor(color);
        g.drawLine(xInicio, yInicio, xFin, yFin);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TableroFrame frame = new TableroFrame(10, 4); 
            frame.setVisible(true);
        });
    }
}
