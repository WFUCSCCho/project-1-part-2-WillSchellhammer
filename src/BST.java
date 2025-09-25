/************************************************************************
 * @file: BST.java
 * @description: Binary Search Tree, useable for any "Comparable" object.
 * @author: Will S
 * @date: September 24, 2025
 ************************************************************************/

public class BST<T extends Comparable<T>>{
    Node<T> root;

    // Implement the constructor

    //empty constructor
    public BST() {
        root = null;
    }

    //full constructor
    public BST(Node<T> root) {
        this.root = root;
    }

    // Implement the insert method
    //Inserts a val at in-order position in BST, or doesn't if it already exists in BST
    public void insert(T val) {
        if (isEmpty()) root = new Node<T>(val);
        else insertHelp(root, val);
    }

    //Recursive helper method for insert()
    private Node<T> insertHelp(Node<T> rt, T val) {
        if (rt == null) {
            return new Node<T>(val);
        }
        if (rt.compareTo(val) > 0) {
            rt.setLeft(insertHelp(rt.getLeft(), val));
        } else if (rt.compareTo(val) < 0) {
            rt.setRight(insertHelp(rt.getRight(), val));
        }
        return rt;
    }

    // Implement the search method
    //Searches BST pre-order for a match to val and returns it, or returns null if no match is found
    public Node<T> search(T val) {
        if (isEmpty() || val == null) return null;
        else return searchHelp(root, val);
    }

    //Recursive helper method for search()
    private Node<T> searchHelp(Node<T> rt, T val) {
        if (rt.getData().equals(val)) {
            return rt;
        }
        if (rt.getLeft() != null) {
            if (searchHelp(rt.getLeft(), val) != null) {
                return rt;
            }
        }
        if (rt.getRight() != null) {
            if (searchHelp(rt.getRight(), val) != null) {
                return rt;
            }
        }
        return null;
    }

    // Implement the remove method
    //Searches BST pre-order for a match to val and removes and returns it, or returns null if no match is found
    public Node<T> remove(T val) {
        if (isEmpty() || search(val) == null) return null;
        else {
            Node<T> prevRoot = root;
            root = removeHelp(root, val);
            return prevRoot;
        }
    }

    //Recursive helper method for remove()
    private Node<T> removeHelp(Node<T> rt, T val) {
        if (rt == null) return null;
        if (rt.compareTo(val) > 0)
            rt.setLeft(removeHelp(rt.getLeft(), val));
        else if (rt.compareTo(val) < 0)
            rt.setRight(removeHelp(rt.getRight(), val));
        else {
            if (rt.getLeft() == null) { //right child only, return right
                return rt.getRight();
            } else if (rt.getRight() == null) { //left child only, return left
                return rt.getLeft();
            } else { //both children, set rt to the lowest value in the right, then delete the lowest value from its previous position
                Node<T> temp = getLowest(rt.getRight());
                rt.setData(temp.data);
                rt.setRight(removeHelp(rt.getRight(), temp.data));
            }
        }
        return rt;
    }

    //ITERATOR

    //Prints BST in-order to command box
    public void print() {
        if (isEmpty()) return;
        printHelp(root);
        System.out.println();
    }

    //Recursive helper method for print()
    private void printHelp(Node<T> rt) {
        if (rt.getLeft() != null) printHelp(rt.getLeft());
        System.out.print(rt.getData() + "\n");
        if (rt.getRight() != null) printHelp(rt.getRight());
    }

    //*************
    //OTHER METHODS
    //*************

    //Returns true if the root of the BST is null, or false if it is any other value
    public boolean isEmpty() {
        return root == null;
    }

    // Implement the clear method
    //clears the BST
    public void clear() {
        clearHelp(root);
    }

    //recursive helper method used by clear()
    private void clearHelp(Node<T> rt) {
        if (rt.getLeft() != null) {
            clearHelp(rt.getLeft());
            rt.setLeft(null);
        }
        if (rt.getRight() != null) {
            clearHelp(rt.getRight());
            rt.setRight(null);
        }
    }

    // Implement the size method
    //returns the count of all nodes in the BST
    public int size() {
        return sizeHelp(root);
    }

    //recursive helper method used by size()
    private int sizeHelp(Node<T> rt) {
        if (rt == null) return 0;
        if (rt.isLeaf()) return 1;
        return sizeHelp(rt.getLeft()) + sizeHelp(rt.getRight()) + 1;
    }

    //Returns the lowest Node in the subtree (the first left child found)
    private Node<T> getLowest(Node<T> rt) {
        if (rt.getLeft() != null) return getLowest(rt.getLeft());
        else return rt;
    }
}