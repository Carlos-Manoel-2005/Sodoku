package application;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import ui.custom.screen.MainScreen;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0 || args[0].isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dados de configuração do tabuleiro não foram encontrados!\nVerifique as configurações do launch.json.", "Erro de Inicialização", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] individualConfigs = args[0].split(" ");

        final var gameConfig = Arrays.stream(individualConfigs)
            .collect(toMap(
                k -> k.split(";")[0],
                v -> v.split(";")[1]
            ));

        new MainScreen(gameConfig).buildMainScreen();
    }
}