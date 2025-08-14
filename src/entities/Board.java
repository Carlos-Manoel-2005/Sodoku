package entities;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Board {
    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        boolean notStarted = this.spaces.stream()
                .flatMap(Collection::stream)
                .noneMatch(s -> !s.isFixed() && Objects.nonNull(s.getActual()));

        if (notStarted) {
            return GameStatusEnum.NON_STARTED;
        }

        boolean isMissingValues = this.spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> Objects.isNull(s.getActual()));

        return isMissingValues ? GameStatusEnum.INCOMPLETE : GameStatusEnum.COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == GameStatusEnum.NON_STARTED) {
            return false;
        }

        // Esta lógica agora vai detectar erros mesmo em células fixas alteradas
        return this.spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> Objects.nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    /**
     * Altera o valor de uma célula. A verificação de célula fixa foi removida.
     */
    public void changeValue(final int row, final int col, final int value) {
        Space space = spaces.get(row).get(col);
        space.setActual(value);
    }

    /**
     * Limpa o valor de uma célula. A verificação de célula fixa foi removida.
     */
    public void clearSpace(final int row, final int col) {
        Space space = spaces.get(row).get(col);
        space.clearSpace();
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus() == GameStatusEnum.COMPLETE;
    }
}
