package ui.custom.Button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ResetButton extends JButton {

    public ResetButton(final ActionListener actionListener) {
        this.setText("Reiniciar Jogo");
        this.addActionListener(actionListener);
    }
}
