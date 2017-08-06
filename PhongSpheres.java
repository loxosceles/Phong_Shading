import javax.swing.*;
import java.awt.*;

public class PhongSpheres {

    private static void createAndShowGUI() {

        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Spheres");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new GridPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(1100, 1100);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
