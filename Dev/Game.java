import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Glas extends JPanel {
    private int secondsElapsed = 0; // Zeit in Sekunden
    private JLabel timerLabel;

    public Glas() {
        setLayout(null); // Absolute Positionierung

        // Timer-Label
        timerLabel = new JLabel("Zeit: 0s");
        timerLabel.setBounds(350, 20, 100, 30); // Position und Größe
        add(timerLabel);

        // Timer starten
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                timerLabel.setText("Zeit: " + secondsElapsed + "s");
            }
        });
        timer.start();

        // "Neues Spiel"-Knopf
        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.setBounds(350, 60, 120, 30); // Position und Größe
        add(newGameButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int rectWidth = 300;
        int rectHeight = 800;
        int x = (width - rectWidth) / 2;
        int y = (height - rectHeight) / 2;

        // Zeichne das Glas (Rechteck)
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3)); // Dickere Striche
        g2d.drawRect(x, y, rectWidth, rectHeight); // Rechteck als Glas mit nur Strichen

        // Unterteile das Glas in 5 Bereiche
        int numSections = 5;
        int sectionHeight = rectHeight / numSections;
        for (int i = 1; i < numSections; i++) {
            int lineY = y + i * sectionHeight;
            g2d.drawLine(x, lineY, x + rectWidth, lineY); // Horizontale Linie zeichnen
        }
    }
}
