import java.util.ArrayList;

/**
 * NimState class.
 *
 * @author abe elias
 * @version 1.0
 */
public class NimState implements State {
    /**
     * inner move class.
     *
     * @author abe elias
     * @version 1.0
     */
    public static class Move implements State.Move {
        int stack;
        int num;

        /**
         * default constructor.
         *
         * @param stack stack number
         * @param num number of game pieces in stack
         */
        public Move(int stack, int num) {
            this.stack = stack;
            this.num = num;
        }

        /**
         * checks if object is equal to current move.
         *
         * @param o object being checked
         * @return true if o is equal
         */
        public boolean equals(Object o) {
            if (o instanceof Move) {
                return ((Move) o).stack == this.stack && ((Move) o).num == this.num;
            }
            return false;
        }

        /**
         * returns string equiv. of current num and stack.
         *
         * @return string
         */
        public String toString() {
            return "Taking " + num + " from stack " + stack;
        }
    }

    public int[] stacks;
    public boolean turn;

    /**
     * default constructor.
     *
     * @param n number of stacks
     * @param pt current turn
     */
    public NimState(int n, boolean pt) {
        turn = pt;
        stacks = new int[n];
        for (int i = 0; i < n; i++) {
            stacks[i] = i + 1;
        }
    }

    /**
     * copy constructor.
     *
     * @param stacks stack array
     * @param pt current turn
     */
    public NimState(int[] stacks, boolean pt) {
        this.stacks = new int[stacks.length];
        for (int i = 0; i < stacks.length; i++) {
            this.stacks[i] = stacks[i];
        }
        turn = pt;
    }

    /**
     * accessor of stack array.
     *
     * @return stacks
     */
    public int[] getStacks() {
        return stacks;
    }

    /**
     * checks whose turn it is.
     *
     * @return true if it is player turn
     */
    public boolean isPlayerTurn() {
        return turn;
    }

    /**
     * returns string equivalent of stack state.
     * @return string stack
     */
    public String toString() {
        if (stacks == null) {
            return null;
        }
        String ret = "";
        int hold;
        for (int i = 0; i < stacks.length; i++) {
            ret = ret + (i + 1) + ": ";
            for (hold = 0; hold < stacks[i]; hold++) {
                ret = ret + "X";
            }
            ret = ret + "\n";
        }
        return ret;
    }

    /**
     * checks if all stacks are empty.
     *
     * @return true if stacks are empty
     */
    public boolean gameOver() {
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * gets minimax value of leaf node.
     *
     * @return -1 if player turn, 1 if computer turn
     */
    public int getValue() {
        if (!gameOver()) {
            throw new IllegalStateException();
        } else if (turn) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * performs move parameter on the stack.
     *
     * @param move Move The move to be done.
     *
     * @return true if move is done
     */
    public boolean doMove(State.Move move) {
        NimState.Move nMove = (NimState.Move) move;
        if (nMove.stack <= stacks.length && nMove.num <= stacks[nMove.stack - 1]) {
            stacks[nMove.stack - 1] = stacks[nMove.stack - 1] - nMove.num;
            turn = !turn;
            return true;
        }
        return false;
    }

    /**
     * undoes move parameter on the stack.
     *
     * @param move Move The move to be undone.
     */
    public void undoMove(State.Move move) {
        NimState.Move nMove = (NimState.Move) move;
        stacks[nMove.stack - 1] = stacks[nMove.stack - 1] + nMove.num;
        turn = !turn;
    }

    /**
     * finds all possible move in current stack.
     *
     * @return all moves
     */
    public ArrayList<State.Move> findAllMoves() {
        ArrayList<State.Move> ret = new ArrayList<>();
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 1; j <= 3 && j <= stacks[i]; j++) {
                ret.add(new NimState.Move(i + 1, j));
            }
        }
        return ret;
    }
}
