import javax.swing.*;  
import java.awt.*;  

public class guitest {
    public static void main(String[] args) {
     
        JFrame frame = new JFrame("Java GUI Beispiel");
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 4)); // 1 Zeile, 4 Spalten

        // Panels 1, 2, 3 und 4
        JPanel panel1 = new ReagenzglasPanel();
        frame.add(panel1);
        
        JPanel panel2 = new ReagenzglasPanel();
        frame.add(panel2);

        JPanel panel3 = new ReagenzglasPanel();
        frame.add(panel3);

        JPanel panel4 = new ReagenzglasPanel();
        frame.add(panel4);

        // Zeigt das Fenster
        frame.setVisible(true);
    }
}

class ReagenzglasPanel extends JPanel {
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
    }
}
