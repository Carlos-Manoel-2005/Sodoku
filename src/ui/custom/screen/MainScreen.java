package ui.custom.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static service.EventEnum.CLEAR_SPACE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.JPanel;

import entities.GameStatusEnum;
import entities.Space;
import service.BoardService;
import service.NotifierService;
import ui.custom.Button.CheckGameStatusButton;
import ui.custom.Button.FinishGameButton;
import ui.custom.Button.ResetButton;
import ui.custom.Frame.MainFrame;
import ui.custom.Panel.MainPanel;
import ui.custom.Panel.SudokuSector;
import ui.custom.input.NumberText;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    
    private JButton finishGameButton;
    private JButton resetButton;
    private JButton checkGameStatusButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    
    public void buildMainScreen() {
        
        JPanel mainPanel = new MainPanel(dimension);
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        
        for (int r = 0; r < 9; r += 3) {
            int endRow = r + 2;
            for (int c = 0; c < 9; c += 3) {
                int endCol = c + 2;
                List<Space> spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                gridPanel.add(generateSection(spaces));
            }
        }

        
        JPanel buttonPanel = new JPanel();
        addResetButton(buttonPanel);
        addCheckGameStatusButton(buttonPanel);
        addFinishGameButton(buttonPanel);

        
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * CORREÇÃO DO BUG 1: A ordem de acesso à lista de listas foi corrigida.
     */
    private List<Space> getSpacesFromSector(List<List<Space>> spaces,
                                            final int initCol, final int endCol,
                                            final int initRow, final int endRow) {

        List<Space> spaceSector = new ArrayList<>();
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                
                spaceSector.add(spaces.get(r).get(c));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces) {
        List<NumberText> fields = new ArrayList<>(spaces.stream()
            .map(NumberText::new).toList());

        fields.forEach(t -> notifierService.subscribe(CLEAR_SPACE, t));

        return new SudokuSector(fields);
    }

   
    private void addFinishGameButton(final JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()) {
                JOptionPane.showMessageDialog(null, "Parabéns, você concluiu o jogo!");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else if (boardService.hasErrors()) {
                JOptionPane.showMessageDialog(null, "O jogo está completo, mas contém erros. Verifique os números.");
            } else { 
                JOptionPane.showMessageDialog(null, "O jogo ainda não foi totalmente preenchido.");
            }
        });

        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            boolean hasErrors = boardService.hasErrors();
            GameStatusEnum gamestatus = boardService.getStatus();

            String message = switch (gamestatus) {
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? " e contém erros." : " e não contém erros.";
            JOptionPane.showMessageDialog(null, message);
        });

        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(final JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(
                null,
                "Deseja realmente reiniciar o jogo?",
                "Limpar o Jogo",
                YES_NO_OPTION,
                QUESTION_MESSAGE
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });

        mainPanel.add(resetButton);
    }
}