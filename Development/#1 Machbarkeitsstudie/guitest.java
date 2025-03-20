import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class guitest {
    private static ReagenzglasPanel selectedPanel = null;
    private static Timer timer;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Water Sort Puzzle");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 5)); // 1 Zeile, 5 Spalten

        // Panels 1, 2, 3, 4 und 5 (leeres Glas)
        ReagenzglasPanel panel1 = new ReagenzglasPanel(new Color[]{Color.RED, Color.GREEN, Color.BLUE});
        frame.add(panel1);
        
        ReagenzglasPanel panel2 = new ReagenzglasPanel(new Color[]{Color.YELLOW, Color.RED, Color.GREEN});
        frame.add(panel2);

        ReagenzglasPanel panel3 = new ReagenzglasPanel(new Color[]{Color.BLUE, Color.YELLOW, Color.RED});
        frame.add(panel3);

        ReagenzglasPanel panel4 = new ReagenzglasPanel(new Color[]{Color.GREEN, Color.BLUE, Color.YELLOW});
        frame.add(panel4);

        ReagenzglasPanel panel5 = new ReagenzglasPanel(new Color[]{}); // leeres Glas
        frame.add(panel5);

        // Timer Label
        JLabel timerLabel = new JLabel("Time: 0 s");
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(timerLabel, BorderLayout.SOUTH);

        // Timer
        timer = new Timer(1000, new ActionListener() {
            int seconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timerLabel.setText("Time: " + seconds + " s");
            }
        });
        timer.start();

        // Add mouse listeners to panels
        panel1.addMouseListener(new PanelMouseListener(panel1));
        panel2.addMouseListener(new PanelMouseListener(panel2));
        panel3.addMouseListener(new PanelMouseListener(panel3));
        panel4.addMouseListener(new PanelMouseListener(panel4));
        panel5.addMouseListener(new PanelMouseListener(panel5));

        // Zeigt das Fenster
        frame.setVisible(true);
    }

    static class PanelMouseListener extends MouseAdapter {
        private ReagenzglasPanel panel;

        public PanelMouseListener(ReagenzglasPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedPanel == null) {
                selectedPanel = panel;
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            } else {
                if (selectedPanel != panel) {
                    selectedPanel.transferColorTo(panel);
                    if (isGameFinished(panel.getParent())) {
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "Heyy, du hast alle Farben Sortiert");
                    }
                }
                selectedPanel.setBorder(null);
                selectedPanel = null;
            }
        }

        private boolean isGameFinished(Container parent) {
            for (Component comp : parent.getComponents()) {
                if (comp instanceof ReagenzglasPanel) {
                    ReagenzglasPanel p = (ReagenzglasPanel) comp;
                    if (!p.isUniformColor()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}

class ReagenzglasPanel extends JPanel {
    private java.util.List<Color> colors;

    public ReagenzglasPanel(Color[] colors) {
        this.colors = new java.util.ArrayList<>(java.util.Arrays.asList(colors));
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
        int liquidHeight = tubeHeight / (colors.size() + 1); // +1 to leave space at the top
        for (int i = 0; i < colors.size(); i++) {
            g2d.setColor(colors.get(i));
            g2d.fillRect(x, y + tubeHeight - (i + 1) * liquidHeight, tubeWidth, liquidHeight);
        }
    }

    public void transferColorTo(ReagenzglasPanel targetPanel) {
        if (!colors.isEmpty()) {
            Color colorToTransfer = colors.get(colors.size() - 1);
            if (targetPanel.canAcceptColor(colorToTransfer)) {
                colors.remove(colors.size() - 1);
                targetPanel.addColor(colorToTransfer);
                repaint();
                targetPanel.repaint();
            }
        }
    }

    public boolean canAcceptColor(Color color) {
        return colors.isEmpty() || colors.get(colors.size() - 1).equals(color);
    }

    public void addColor(Color color) {
        colors.add(color);
    }

    public boolean isUniformColor() {
        if (colors.isEmpty()) return false;
        Color firstColor = colors.get(0);
        for (Color color : colors) {
            if (!color.equals(firstColor)) {
                return false;
            }
        }
        return true;
    }
}
