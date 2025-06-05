package book;

public class Program {
    public static void main(String[] args) {
        BST bst = new BST(1);
        findClosestValueInBst(bst, 12);
    }

    public static int findClosestValueInBst(BST tree, int target) {
        int minor = Math.abs(tree.value - target);
        return findClosestValue(tree, target, tree.value, minor);
    }

    public static int findClosestValue(BST tree, int target, int closestNode, int minor) {
        if(tree == null)
            return closestNode;
        if((tree.value - target) < minor) {
            minor = Math.abs(tree.value - target);
            closestNode = tree.value;
        }
        findClosestValue(tree.left, target, closestNode, minor);
        findClosestValue(tree.right, target, closestNode, minor);
        return closestNode;
    }

    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }
    }
}
