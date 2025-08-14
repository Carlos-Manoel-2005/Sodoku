package ui.custom.Panel;

import javax.swing.border.LineBorder;
import javax.swing.JPanel;
import java.awt.Dimension;

import static java.awt.Color.black;

public class SudokuSector extends JPanel {

    public SudokuSector() {
        Dimension dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
    }
}