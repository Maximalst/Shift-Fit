import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

class Glas extends JPanel {
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
