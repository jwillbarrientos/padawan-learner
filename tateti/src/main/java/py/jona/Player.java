package py.jona;

enum Player {
    X(Colors.RED),
    O(Colors.BLUE);
    public final Colors color;

    Player(Colors color) {
        this.color = color;
    }
}
