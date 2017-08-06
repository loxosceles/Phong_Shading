import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Created by arytloc on 11.06.16.
 */

class GridPane extends JPanel {


    GridPane() {
        setLayout(new GridBagLayout());
        createGrid();
    }

    private void createGrid() {

        int count = 0;

        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                LightSource l = new LightSource(count);
                SpherePane spherePane = new SpherePane(l);
                Border border = null;
                if (row < 3) {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < 4) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                spherePane.setBorder(border);
                spherePane.setBackground(Color.black);
                add(spherePane, gbc);
                count++;
            }
        }
    }
}
