import java.util.ArrayList;

/**
 * GameTree class.
 *
 * @author abe elias
 * @version 1.0
 */
public class GameTree {
    /**
     * inner node class.
     *
     * @author abe elias
     * @version 1.0
     */
    public class Node {
        int value;
        ArrayList<Node> nodes = new ArrayList<>();
        State.Move m;
        Node bestChild;

        /**
         * default constructor.
         *
         * @param m game state
         */
        public Node(State.Move m) {
            this.m = m;
        }

        /**
         * return minimax value of node.
         *
         * @return minimax value
         */
        public int getValue() {
            return value;
        }

        /**
         * move accessor.
         *
         * @return move
         */
        public State.Move getMove() {
            return m;
        }

        /**
         * accessor of best child node based on values.
         *
         * @return best child
         */
        public Node getBestChild() {
            return bestChild;
        }

        /**
         * find child of current move state.
         *
         * @param m move state
         * @return child
         */
        public Node findChild(State.Move m) {
            for (Node hold : nodes) {
                if (hold.m.equals(m)) {
                    return hold;
                }
            }
            return null;
        }

        /**
         * gets prediction on who will win the game.
         *
         * @return computer, no one, or player
         */
        public String getPrediction() {
            if (getValue() == 1) {
                return "computer";
            } else if (getValue() == 0) {
                return "no one";
            } else {
                return "player";
            }
        }
    }

    Node root;

    /**
     * default constructor to begin tree building.
     *
     * @param start beginning of tree
     */
    public GameTree(State start) {
        root = buildTree(start, new NimState.Move(1, 0));
    }

    /**
     * accessor of root node.
     *
     * @return root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * builds game tree based on minimax algorithm.
     *
     * @param state game state
     * @param move move state
     * @return tree
     */
    public Node buildTree(State state, State.Move move) {
        Node ret = new Node(move);
        if (state.gameOver()) {
            ret.value = state.getValue();
        } else {
            for (int i = 0; i < state.findAllMoves().size(); i++) {
                State.Move hold1 = state.findAllMoves().get(i);
                state.doMove(hold1);
                ret.nodes.add(buildTree(state, hold1));
                state.undoMove(hold1);
                if (state.isPlayerTurn()) {
                    ret.value = 1;
                    for (int j = 0; j < ret.nodes.size(); j++) {
                        Node hold2 = ret.nodes.get(j);
                        if (hold2.value < ret.value) {
                            ret.value = hold2.value;
                            ret.bestChild = hold2;
                            break;
                        }
                        ret.bestChild = hold2;
                    }
                } else {
                    ret.value = -1;
                    for (int j = 0; j < ret.nodes.size(); j++) {
                        Node hold2 = ret.nodes.get(j);
                        if (hold2.value > ret.value) {
                            ret.value = hold2.value;
                            ret.bestChild = hold2;
                            break;
                        }
                        ret.bestChild = hold2;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * gets size of game tree.
     *
     * @return number of nodes
     */
    public int getSize() {
        return getSize(root) + 1;
    }

    /**
     * recursively finds size of gametree.
     *
     * @param n start of tree
     * @return number of nodes
     */
    public int getSize(Node n) {
        int ret = 0;
        for (int i = 0; i < n.nodes.size(); i++) {
            ret = ret + 1 + getSize(n.nodes.get(i));
        }
        return ret;
    }
}
