package application;

import entities.Board;
import entities.BoardTemplate;
import entities.GameStatusEnum;
import entities.Space;
import factory.BoardFactory;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static Board board;

    public static void main(String[] args) {
        board = BoardFactory.create();
        int option;

        do {
            printBoard();
            checkGameStatus();

            printMenu();
            option = readOption();

            switch (option) {
                case 1 -> play();
                case 2 -> clear();
                case 3 -> resetGame();
                case 0 -> System.out.println("Obrigado por jogar! Saindo...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }

        } while (option != 0 && !board.gameIsFinished());

        if (board.gameIsFinished()) {
            System.out.println("\n***************************************************");
            System.out.println("* PARABÉNS! Você resolveu o Sudoku com sucesso! *");
            System.out.println("***************************************************");
            printBoard();
        }

        scanner.close();
    }

    private static void printBoard() {
        final var values = board.getSpaces().stream()
                .flatMap(List::stream)
                .map(Space::getActual)
                .map(v -> Objects.isNull(v) ? " " : String.valueOf(v))
                .collect(Collectors.toList());

        System.out.println(BoardTemplate.BOARD_TEMPLATE.formatted(values.toArray()));
    }

    private static void checkGameStatus() {
        if (board.getStatus() != GameStatusEnum.NON_STARTED) {
            if (board.hasErrors()) {
                System.out.println(">> AVISO: O tabuleiro contém erros!");
            } else if (board.getStatus() == GameStatusEnum.INCOMPLETE) {
                System.out.println(">> STATUS: O jogo está em andamento. Continue preenchendo!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nEscolha uma ação:");
        System.out.println("1 - Fazer uma jogada");
        System.out.println("2 - Limpar uma célula");
        System.out.println("3 - Reiniciar o jogo");
        System.out.println("0 - Sair do jogo");
        System.out.print("Sua opção: ");
    }

    private static int readOption() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            return -1;
        }
    }

    private static void play() {
        try {
            System.out.print("Digite a linha (0-8): ");
            int row = scanner.nextInt();
            System.out.print("Digite a coluna (0-8): ");
            int col = scanner.nextInt();
            System.out.print("Digite o valor (1-9): ");
            int value = scanner.nextInt();

            if (value < 1 || value > 9 || row < 0 || row > 8 || col < 0 || col > 8) {
                System.out.println("Valores inválidos! Linhas/colunas devem ser de 0 a 8 e o valor de 1 a 9.");
                return;
            }

            // A chamada agora é direta, sem verificação de retorno.
            board.changeValue(row, col, value);

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite apenas números.");
            scanner.next();
        }
    }

    private static void clear() {
        try {
            System.out.print("Digite a linha (0-8) para limpar: ");
            int row = scanner.nextInt();
            System.out.print("Digite a coluna (0-8) para limpar: ");
            int col = scanner.nextInt();

            if (row < 0 || row > 8 || col < 0 || col > 8) {
                System.out.println("Valores inválidos! Linhas/colunas devem ser de 0 a 8.");
                return;
            }

            // A chamada agora é direta, sem verificação de retorno.
            board.clearSpace(row, col);

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite apenas números.");
            scanner.next();
        }
    }

    private static void resetGame() {
        board.reset();
        System.out.println("O jogo foi reiniciado!");
    }
}
