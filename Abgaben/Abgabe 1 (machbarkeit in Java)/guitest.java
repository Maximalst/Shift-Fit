import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guitest {
    public static void main(String[] args) {
     
        JFrame frame = new JFrame("Water Sort Puzzle");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 4)); // 1 Zeile, 4 Spalten

        // Panels 1, 2, 3 und 4
        JPanel panel1 = new ReagenzglasPanel(new Color[]{Color.RED, Color.GREEN, Color.BLUE});
        frame.add(panel1);
        
        JPanel panel2 = new ReagenzglasPanel(new Color[]{Color.YELLOW, Color.RED, Color.GREEN});
        frame.add(panel2);

        JPanel panel3 = new ReagenzglasPanel(new Color[]{Color.BLUE, Color.YELLOW, Color.RED});
        frame.add(panel3);

        JPanel panel4 = new ReagenzglasPanel(new Color[]{Color.GREEN, Color.BLUE, Color.YELLOW});
        frame.add(panel4);

        // Timer Label
        JLabel timerLabel = new JLabel("Time: 0 s");
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(timerLabel, BorderLayout.SOUTH);

        // Timer
        Timer timer = new Timer(1000, new ActionListener() {
            int seconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timerLabel.setText("Time: " + seconds + " s");
            }
        });
        timer.start();

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
        int tubeHeight = height - 100; 
        int x = (width - tubeWidth) / 2;
        int y = 100; // Startpunkt nach unten verschoben

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
