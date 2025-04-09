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
        frame.add(new TestTubePanel());

        frame.setVisible(true); // Mache das Fenster sichtbar
    }
}

class TestTubePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Setze die Farbe für die Gläser
        g2d.setStroke(new BasicStroke(3)); // Setze die Strichstärke für den Rahmen

        // Zeichne nur den Rahmen der Gläser
        int tubeWidth = 50;
        int tubeHeight = 200;
        int spacing = 30;

        int totalWidth = 5 * tubeWidth + 4 * spacing;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - tubeHeight) / 2;

        for (int i = 0; i < 5; i++) {
            int x = startX + i * (tubeWidth + spacing);
            g2d.drawRect(x, startY, tubeWidth, tubeHeight); // Zeichne nur den Rahmen
        }

        // Zeichne 5 Reagenzgläser (rechteckige Formen)
        for (int i = 0; i < 5; i++) {
            int x = startX + i * (tubeWidth + spacing);
            g2d.fillRect(x, startY, tubeWidth, tubeHeight);
        }
    }
}
