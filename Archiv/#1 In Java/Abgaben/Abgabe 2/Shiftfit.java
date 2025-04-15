package Dev;
import javax.swing.*;

public class Shiftfit {
    public static void main(String args[]){
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(); // Panel für Layout
        JLabel label = new JLabel("Enter your name:");
        JTextField textField = new JTextField(15);
        JButton button = new JButton("Press");

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        frame.getContentPane().add(panel); // Fügt das Panel zum Frame hinzu
        frame.setVisible(true);
    }
}
