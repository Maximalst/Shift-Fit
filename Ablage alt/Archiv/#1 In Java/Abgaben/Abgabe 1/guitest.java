import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class guitest {
    private static ReagenzglasPanel selectedPanel = null;
    private static Timer timer;
    private static boolean timerStarted = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Water Sort Puzzle");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 5)); // 1 Zeile, 5 Spalten

        // Panels 1, 2, 3, 4 und 5 (leeres Glas)
        ReagenzglasPanel panel1 = new ReagenzglasPanel(new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.BLUE});
        frame.add(panel1);
        
        ReagenzglasPanel panel2 = new ReagenzglasPanel(new Color[]{Color.YELLOW, Color.RED, Color.GREEN, Color.RED});
        frame.add(panel2);

        ReagenzglasPanel panel3 = new ReagenzglasPanel(new Color[]{Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN});
        frame.add(panel3);

        ReagenzglasPanel panel4 = new ReagenzglasPanel(new Color[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.YELLOW});
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

        // Add mouse listeners to panels
        panel1.addMouseListener(new PanelMouseListener(panel1, timerLabel));
        panel2.addMouseListener(new PanelMouseListener(panel2, timerLabel));
        panel3.addMouseListener(new PanelMouseListener(panel3, timerLabel));
        panel4.addMouseListener(new PanelMouseListener(panel4, timerLabel));
        panel5.addMouseListener(new PanelMouseListener(panel5, timerLabel));

        // Zeigt das Fenster
        frame.setVisible(true);
    }

    static class PanelMouseListener extends MouseAdapter {
        private ReagenzglasPanel panel;
        private JLabel timerLabel;

        public PanelMouseListener(ReagenzglasPanel panel, JLabel timerLabel) {
            this.panel = panel;
            this.timerLabel = timerLabel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!timerStarted) {
                timer.start();
                timerStarted = true;
            }

            if (selectedPanel == null) {
                selectedPanel = panel;
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            } else {
                if (selectedPanel != panel) {
                    selectedPanel.transferColorTo(panel);
                    if (isGameFinished(panel.getParent())) {
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "Heyy, du hast alle Farben sortiert in " + timerLabel.getText());
                    }
                }
                selectedPanel.setBorder(null);
                selectedPanel = null;
            }
        }

        private boolean isGameFinished(Container parent) {
            boolean hasEmptyGlass = false;
            for (Component comp : parent.getComponents()) {
                if (comp instanceof ReagenzglasPanel) {
                    ReagenzglasPanel p = (ReagenzglasPanel) comp;
                    if (p.getColors().isEmpty()) {
                        hasEmptyGlass = true;
                    } else if (!p.isUniformColor()) {
                        return false;
                    }
                }
            }
            return hasEmptyGlass;
        }
    }
}

class ReagenzglasPanel extends JPanel {
    private List<Color> colors;

    public List<Color> getColors() {
        return colors;
    }

    public ReagenzglasPanel(Color[] colors) {
        this.colors = new ArrayList<>(java.util.Arrays.asList(colors));
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
        g2d.drawRoundRect(x, y, tubeWidth, tubeHeight, 20, 20);
        g2d.drawRoundRect(x, y - tubeWidth / 2, tubeWidth, tubeWidth / 2, 20, 20);

        // Zeichne die Flüssigkeitsschichten
        int maxSegments = 4; // Maximale Anzahl von Segmenten im Reagenzglas
        int segmentHeight = tubeHeight / maxSegments;
        for (int i = 0; i < colors.size(); i++) {
            g2d.setColor(colors.get(i));
            GradientPaint gradient = new GradientPaint(x, y + tubeHeight - (i + 1) * segmentHeight, colors.get(i), x + tubeWidth, y + tubeHeight - (i + 1) * segmentHeight + segmentHeight, colors.get(i).darker());
            g2d.setPaint(gradient);
            g2d.fillRoundRect(x, y + tubeHeight - (i + 1) * segmentHeight, tubeWidth, segmentHeight, 20, 20);
        }
    }

    public void transferColorTo(ReagenzglasPanel targetPanel) {
        if (!colors.isEmpty()) {
            Color colorToTransfer = colors.get(colors.size() - 1);
            int count = 0;
            for (int i = colors.size() - 1; i >= 0; i--) {
                if (colors.get(i).equals(colorToTransfer)) {
                    count++;
                } else {
                    break;
                }
            }
            if (targetPanel.canAcceptColor(colorToTransfer)) {
                for (int i = 0; i < count; i++) {
                    colors.remove(colors.size() - 1);
                    targetPanel.addColor(colorToTransfer);
                }
                repaint();
                targetPanel.repaint();
            }
        }
    }

    public boolean canAcceptColor(Color color) {
        return colors.isEmpty() || (colors.size() < 4 && colors.get(colors.size() - 1).equals(color));
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
