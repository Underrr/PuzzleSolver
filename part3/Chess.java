import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * Chess.java
 */

public class Chess extends JFrame implements Observer {
    private ChessModel model;
    private JTextArea moveDisplay, message;
    private ChessButton[][] buttons;

    public Chess(ChessModel model) {
        this.model = model;
        model.addObserver(this);

        this.moveDisplay = new JTextArea("Move: " + model.getMoves() + " Select a chess piece");
        this.message = new JTextArea();
        this.message.setForeground(Color.RED);
        this.buttons = new ChessButton[model.getBoardY()][model.getBoardX()];

        JButton reset = new JButton("Reset");
        JButton next = new JButton("Next Move");
        JButton howTo = new JButton("Tutorial");
        reset.addActionListener(new ButtonListener(reset, this));
        next.addActionListener(new ButtonListener(next, this));
        howTo.addActionListener(new ButtonListener(howTo, this));

        for (int i = 0; i < model.getBoardY(); i++) {
            for (int j = 0; j < model.getBoardX(); j++) {
                ChessButton b = new ChessButton(j, i);
                b.setBorderPainted(false);
                b.setContentAreaFilled(false);
                b.setOpaque(true);
                b.setVisible(true);
                b.setBackground(new Color(205, 201, 201));
                b.addActionListener(new ButtonListener(b, this));
                buttons[i][j] = b;
            }
        }

        JPanel text = new JPanel();
        text.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 3));
        text.add(this.moveDisplay);
        text.add(this.message);
        text.setVisible(true);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(model.getBoardY(), model.getBoardX(),5,5));
        center.setVisible(true);
        for (int i = 0; i < model.getBoardY(); i++) {
            for (int j = 0; j < model.getBoardX(); j++) {
                center.add(this.buttons[i][j]);
            }
        }

        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        menu.add(reset);
        menu.add(next);
        menu.add(howTo);
        menu.setVisible(true);

        this.setLayout(new BorderLayout());
        this.add(text, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(menu, BorderLayout.SOUTH);

        this.setTitle("Solitare Chess - Chris Sleys - cas5420");
        this.setSize(model.getBoardX() * 100, model.getBoardY() * 100);
        this.setLocation(100, 100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        model.reset();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        HashMap<Point, Piece> board = model.getBoard();
                
        for (ChessButton[] row : this.buttons) {
            for(ChessButton b : row){
                b.setText(" ");
            }
        }
                
        for (Point p : board.keySet()) {
            this.buttons[p.getY()][p.getX()].setText(board.get(p).getName());
        }
                
        this.moveDisplay.setText("Move: " + model.getMoves());

        if (board.size() == 1) {
            this.message.setText("Game Over");
        }
                
        if (this.model.undoSize() == 0) {
            this.resetHighlight();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Chess initalBoard.txt");
            System.exit(0);
        }
    
        new Chess(new ChessModel(new File(args[0])));
    }

        /**
         * EventListener for all the buttons in the GUI
         * 
         * @author Chris Sleys
         * 
         */
        private class ButtonListener implements ActionListener {
            private JButton b;
            private Chess gui;

            public ButtonListener(JButton b, Chess gui) {
                this.b = b;
                this.gui = gui;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if(b instanceof ChessButton){
                    if (model.selectPiece(((ChessButton) b).getPos())) {
                        gui.resetHighlight();
                        b.setBackground(Color.LIGHT_GRAY);
                    }
                }else if(b.getText().equals("Reset")){
                    model.reset();
                    message.setText("");
                }else if(b.getText().equals("Next Move")){
                    if (!model.nextMove(model)) {
                        message.setText("There is no next move since there is no solution");
                    }
                }else{
                    JOptionPane.showMessageDialog(gui,"All the pieces move like normal chess pieces if \n" + 
                        "you would like to move one simply select the piece \n" +
                        "in question and the select where you would like it \n" +
                        "to go. If the piece does not move but instead the \n" +
                        "highlight moves to the next piece it means you can \n" +
                        "not make that move and therefore it switches focus \n" +
                        "to the next piece. If at any time you get stuck you \n" +
                        "can use the 'Next Move' button to have the next valid \n" +
                        "move played for you. If at any time you are not sure \n" +
                        "whats going on check top part of the GUI for information \n" +
                        "messages.",
                        "Tutorial",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
    protected void resetHighlight() {
        for (ChessButton[] row : this.buttons) {
            for (ChessButton b : row) {
                b.setBackground(new Color(205, 201, 201));
            }
        }
    }
}