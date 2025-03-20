import javax.swing.*;  
import java.awt.*;  

public class guitest {
    public static void main(String[] args) {
     
        JFrame frame = new JFrame("Water Sort Puzzle");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 4)); // 1 Zeile, 4 Spalten

        // Panels 1, 2, 3 und 4
        JPanel panel1 = new ReagenzglasPanel(new Color[]{Color.RED, Color.GREEN, Color.BLUE});
        frame.add(panel1);
        
        JPanel panel2 = new ReagenzglasPanel(new Color[]{Color.YELLOW, Color.PINK, Color.CYAN});
        frame.add(panel2);

        JPanel panel3 = new ReagenzglasPanel(new Color[]{Color.ORANGE, Color.MAGENTA, Color.LIGHT_GRAY});
        frame.add(panel3);

        JPanel panel4 = new ReagenzglasPanel(new Color[]{Color.WHITE, Color.DARK_GRAY, Color.BLACK});
        frame.add(panel4);

        // Zeigt das Fenster
        frame.setVisible(true);
    }
}

class ReagenzglasPanel extends JPanel {
    private Color[] colors;

    public ReagenzglasPanel(Color[] colors) {
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichne das Reagenzglas
        int width = getWidth();
        int height = getHeight();
        int tubeWidth = width / 2;
        int tubeHeight = height - 20;
        int x = (width - tubeWidth) / 2;
        int y = 10;

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, tubeWidth, tubeHeight);
        g2d.drawOval(x, y - tubeWidth / 2, tubeWidth, tubeWidth);

        // Zeichne die Flüssigkeitsschichten
        int liquidHeight = tubeHeight / colors.length;
        for (int i = 0; i < colors.length; i++) {
            g2d.setColor(colors[i]);
            g2d.fillRect(x, y + tubeHeight - (i + 1) * liquidHeight, tubeWidth, liquidHeight);
        }
    }
}
