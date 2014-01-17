/**
 * ChessModel.java
 */

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Observable;

public class ChessModel extends Observable implements
                Puzzle<HashMap<Point, Piece>> {
        private HashMap<Point, Piece> board;
        private HashMap<Character, Piece> pieceAbrev;
        private int X, Y, moveCount;
        private ArrayList<Point> undoStack;
        private boolean debug = false;
        private Solver<HashMap<Point, Piece>> solver = new Solver<HashMap<Point, Piece>>();
        private File startBoard;

        /**
         * Initilizes model
         * 
         * @param init
         *            Inital board setup.
         */
        public ChessModel(File init) {
                this.debugger("Initalizing model");
                this.board = new HashMap<Point, Piece>();
                this.undoStack = new ArrayList<Point>();
                this.startBoard = init;
                this.reset();
        }

        /**
         * If a piece exists at given point then add it to the stack. If there are
         * two pieces in the stack see if its a kill, if its not a kill change focus
         * to the piece at point p.
         * 
         * @param p
         *            Point selected by user.
         * @return True if a piece at p and false if there is not one there or a
         *         kill has occured. Used to determine background color.
         */
        public boolean selectPiece(Point p) {
                boolean ret = false;
                this.debugger("Selecting point " + p);
                if (this.board.containsKey(p)) {
                        ret = true;
                        this.debugger(p + " exists, adding to undo stack");
                        this.undoStack.add(p);

                        if (this.undoStack.size() == 2) {
                                this.debugger("Undo stack size == 2");
                                Point first = this.undoStack.get(0);
                                Point second = this.undoStack.get(1);

                                // get the piece at point first and get its moves based on point
                                // first. If the set contains the second point we have a valid
                                // kill move.
                                if (this.board.get(first).getMoves(first).contains(second)) {
                                        this.debugger("Piece at " + first + " can kill piece at "
                                                        + second);
                                        this.board.remove(second);
                                        // copy the piece at first into second
                                        this.board.put(second, this.board.get(first));
                                        this.board.remove(first); // remove first from the board
                                        this.moveCount++;
                                        this.undoStack.clear();
                                        ret = false;
                                } else {
                                        this.debugger("No kill, changing focus to second");
                                        // if this is not a kill the second becomes the first
                                        this.undoStack.remove(0);
                                }
                        }
                }

                this.setChanged();
                this.notifyObservers(this.board);
                return ret;
        }
        
        /**
         * Returns current move counter.
         * @return Number of moves made.
         */
        public int getMoves() {
                return moveCount;
        }

        /**
         * Returns the lenght of the board on the x-axis.
         * @return Lenght of board on x-axist.
         */
        public int getBoardX() {
                return this.X;
        }

        /**
         * Returns the lenght of the board on the y-axis.
         * @return Lenght of board on y-axist.
         */
        public int getBoardY() {
                return this.Y;
        }

        /**
         * Returns the size of the undo stack.
         * @return Size of undo stack.
         */
        public int undoSize() {
                return this.undoStack.size();
        }

        /**
         * Returns the current board setup.
         * @return Locations of all the pieces on the board.
         */
        public HashMap<Point, Piece> getBoard() {
                return this.board;
        }

        /**
         * Uses the solver to generate the best next move for the game and updates the board to match.
         * @param cm Instaceated version of the model.
         * @return Returns true if there is a solution and false if non exists.
         */
        public boolean nextMove(ChessModel cm) {
                // run the solver and set board to the second step since the first is
                // the current board.
                try {
                        this.board = this.solver.solve(cm).get(1);
                        this.moveCount++;
                        this.setChanged();
                        this.notifyObservers(this.board);
                        return true;
                } catch (NullPointerException e) {
                        return false;
                }
        }

        /**
         * If there is only one piece on the board then this config is the solution.
         */
        @Override
        public boolean isGoal(HashMap<Point, Piece> config) {
                this.debugger("Checking if " + config + " is solution");
                if (config.size() == 1) {
                        this.debugger("Found solution");
                        return true;
                } else {
                        return false;
                }
        }

        @Override
        public ArrayList<HashMap<Point, Piece>> getNeighbors(
                        HashMap<Point, Piece> config) {
                this.debugger("Getting neighbors for " + config);
                ArrayList<HashMap<Point, Piece>> ret = new ArrayList<HashMap<Point, Piece>>();
                for (Point first : config.keySet()) {
                        HashSet<Point> moves = config.get(first).getMoves(first);
                        this.debugger("Moves for piece at " + first + " are " + moves);
                        // check for kills
                        for (Point second : config.keySet()) {
                                this.debugger("Checking for kills at " + second);
                                if (moves.contains(second)) {
                                        this.debugger("Found kill");
                                        HashMap<Point, Piece> neighbor = new HashMap<Point, Piece>();
                                        // make neighbor copy of config
                                        neighbor.putAll(config);
                                        // remove the dead piece
                                        neighbor.remove(second);
                                        // move first to the location of second
                                        neighbor.put(second, config.get(first));
                                        // remove first
                                        neighbor.remove(first);

                                        ret.add(neighbor);
                                }
                        }
                }
                return ret;
        }

        @Override
        public HashMap<Point, Piece> getStart() {
                this.debugger("Giving solver current board " + this.board);
                return this.board;
        }

        /**
         * Pareses input file to generate board.
         * @param init File input from the command line.
         */
        private void readFile(File init) {
                try {
                        BufferedReader in = new BufferedReader(new FileReader(init));
                        String[] nextLine = in.readLine().split(" ");
                        char current;

                        this.Y = Integer.parseInt(nextLine[0]);
                        this.X = Integer.parseInt(nextLine[1]);

                        this.pieceAbrev = this.pieceAbreveations();

                        for (int i = 0; i < this.Y; i++) {
                                this.debugger("Parsing line " + i);
                                nextLine = in.readLine().split(" ");
                                for (int j = 0; j < this.X; j++) {
                                        current = nextLine[j].charAt(0);
                                        if (this.pieceAbrev.containsKey(current)) {
                                                this.debugger("Found piece");
                                                this.board.put(new Point(j, i),
                                                                this.pieceAbrev.get(current));
                                        }
                                }
                        }

                        in.close();

                } catch (FileNotFoundException e) {
                        System.out.println("File Not Found Exception");
                        for (StackTraceElement s : e.getStackTrace()) {
                                System.err.println(s.toString());
                        }
                        System.exit(1);
                } catch (IOException e) {
                        System.out.println("I/O Exception");
                        for (StackTraceElement s : e.getStackTrace()) {
                                System.err.println(s.toString());
                        }
                        System.exit(2);
                }
        }

        /**
         * Assignes abreveations to each piece.
         * @return Set of characters maped to each piece.
         */
        private HashMap<Character, Piece> pieceAbreveations() {
                this.debugger("Generating piece abrevations");
                HashMap<Character, Piece> ret = new HashMap<Character, Piece>();

                ret.put(new Character('B'), new Bishop("Bishop", this.X, this.Y));
                ret.put(new Character('K'), new King("King", this.X, this.Y));
                ret.put(new Character('N'), new Knight("Knight", this.X, this.Y));
                ret.put(new Character('P'), new Pawn("Pawn", this.X, this.Y));
                ret.put(new Character('R'), new Rook("Rook", this.X, this.Y));
                ret.put(new Character('Q'), new Queen("Queen", this.X, this.Y));

                return ret;
        }

        /**
         * Returns everything to starting positions.
         */
        public void reset() {
                this.debugger("Reseting board");
                this.moveCount = 0;
                this.board.clear();
                this.readFile(this.startBoard);
                this.undoStack.clear();
                this.setChanged();
                this.notifyObservers(this.board);
        }

        /**
         * Simple debugger for testing.
         * @param message String to be written to log.
         */
        private void debugger(String message) {
                if (this.debug) {
                        System.out.println(message);
                }
        }

        /**
         * Turn debuggin on and off.
         */
        public void toggleDebug() {
                if (this.debug) {
                        this.debug = false;
                } else {
                        this.debug = true;
                }
        }
}