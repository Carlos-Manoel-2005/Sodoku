package entities;

public class Space {

    private Integer actual;
    private final Integer expected;
    private final boolean fixed;

    public Space(Integer expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;

        if (fixed) {
            this.actual = expected;
        }
    }

    public Integer getActual() {
        return actual;
    }
    
    public void setActual(final Integer actual) {
        this.actual = actual;
    }

    /**
     * Limpa o valor da célula, definindo-o como nulo.
     */
    public void clearSpace() {
        this.setActual(null);
    }

    public Integer getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
}
