package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entities.Board;
import entities.GameStatusEnum;
import entities.Space;

public class BoardService {
    
    private final static int BOARD_LIMIT = 9;

    private Board board;

    public BoardService(final Map<String, String> gameConfig) {
        this.board = new Board(initBoard(gameConfig));
    }

    public void init(final Map<String, String> gameConfig) {
        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpaces(){
        return board.getSpaces();
    }

    public void reset(){
        board.reset();
    }

    public boolean hasErrors(){
        return board.hasErrors();
    }

    public GameStatusEnum getStatus(){
        return board.getStatus();
    }

    public boolean gameIsFinished(){
        return board.gameIsFinished();
    }

  private List<List<Space>> initBoard(final Map<String, String> gameConfig) {
    List<List<Space>> spaces = new ArrayList<>();
    for (int i = 0; i < BOARD_LIMIT; i++) {
        spaces.add(new ArrayList<>());
        for (int j = 0; j < BOARD_LIMIT; j++) {
            // Pega a configuração para a posição atual
            var positionConfig = gameConfig.get("%s,%s".formatted(i, j));

            // CRÍTICO: Verificamos se a posição foi configurada ou se é um espaço vazio
            if (positionConfig != null) {
                // Se a posição VEIO nos argumentos, criamos o Space com os valores
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            } else {
                // Se a posição NÃO VEIO nos argumentos, é um espaço em branco e editável
                // Criamos um Space padrão (valor 0 = vazio, não fixo)
                var currentSpace = new Space(0, false);
                spaces.get(i).add(currentSpace);
            }
        }
    }
        return spaces;
    }
}
