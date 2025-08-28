package ui.custom.input;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entities.Space;
import service.EventEnum;
import service.EventListener;

import java.awt.Dimension;
import java.awt.Font;

import static service.EventEnum.CLEAR_SPACE;
import static java.awt.Font.PLAIN;

public class NumberText extends JTextField implements EventListener{

    private final Space space;

    public NumberText(final Space space){
        this.space = space;
        Dimension dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if(space.isFixed()){
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               changedUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changedUpdate();
            }

            private void changedUpdate(){
                if(getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }
            
        });
    }

    @Override
    public void update(EventEnum eventType) {
         if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
}
