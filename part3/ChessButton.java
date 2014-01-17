import javax.swing.JButton;

public class ChessButton extends JButton {
    Point pos;

    public ChessButton(int x, int y) {
        this.pos = new Point(x, y);
    }

    public Point getPos() {
        return pos;
    }
}