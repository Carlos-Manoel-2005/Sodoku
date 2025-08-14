package application;


import java.awt.Dimension;

import ui.custom.Frame.MainFrame;
import ui.custom.Panel.MainPanel;

public class Main {

    public static void main(String[] args) {
        Dimension dimension = new Dimension(545, 515);

        MainPanel mainPanel = new MainPanel(dimension);

        MainFrame mainFrame = new MainFrame(dimension, mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
