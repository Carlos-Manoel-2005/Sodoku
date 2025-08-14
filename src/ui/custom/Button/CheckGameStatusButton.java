package ui.custom.Button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton {

    public CheckGameStatusButton(ActionListener actionListener) {
        this.setText("Verificar Jogo");
        this.addActionListener(actionListener);
    }
}
