import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        System.out.println("Starte Game");

        // J-Frame Starten
        System.out.println("Starte JFrame");
        JFrame frame = new JFrame("SHIFT-FIT");
        frame.setSize(800, 700); // Fenstergröße setzen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fenster nur durch manuellen Schließen schließen

        // Custom Panel mit Reagenzgläsern hinzufügen
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 5)); // GridLayout with 1 row and 5 columns
        container.add(new Glas1());
        container.add(new Glas2());
        container.add(new Glas3());
        container.add(new Glas4());
        container.add(new Glas5());
        frame.add(container);

        frame.setVisible(true); // Mache das Fenster sichtbar
    }
}

class Glas1 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(10, 10, 50, 200); // Beispiel: Rechteck als Glas
    }
}

class Glas2 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(70, 10, 50, 200); // Beispiel: Rechteck als Glas
    }
}


class Glas3 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(200, 100, 50, 200); // Beispiel: Rechteck als Glas
    }
}


class Glas4 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(200, 100, 50, 200); // Beispiel: Rechteck als Glas
    }
}


class Glas5 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(200, 100, 50, 200); // Beispiel: Rechteck als Glas
    }
}
