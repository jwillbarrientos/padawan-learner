package py.jona;

public enum Colors {
    RED("\033[91m"),
    BLUE("\033[94m"),
    GREEN("\033[92m"),
    SKY_BLUE("\033[96m"),
    DEFAULT("\033[39m");

    public final String escapeSequence;

    Colors(String escapeSequence) {
        this.escapeSequence = escapeSequence;
    }
}
