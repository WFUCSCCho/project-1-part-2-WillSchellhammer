/************************************************************************
 * @file: Node.java
 * @description: Nodes for Binary Search Tree, used by BST class.
 * @author: Will S
 * @date: September 24, 2025
 ************************************************************************/

public class Node<T extends Comparable<T>> implements Comparable<T> {
    T data;
    Node<T> left;
    Node<T> right;

    // Implement the constructor
    //exterior node constructor
    public Node(T data) {
        this.data = data;
    }

    //interior node constructor
    public Node(T data, Node<T> left, Node<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    //copy constructor
    public Node(Node<T> n) {
        this.data = n.getData();
        this.left = n.getLeft();
        this.right = n.getRight();
    }

    // Implement the setElement method
    public void setData(T data) {
        this.data = data;
    }

    // Implement the setLeft method
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    // Implement the setRight method
    public void setRight(Node<T> right) {
        this.right = right;
    }

    // Implement the getLeft method
    public Node<T> getLeft() {
        return left;
    }

    // Implement the getRight method
    public Node<T> getRight() {
        return right;
    }

    // Implement the getElement method
    public T getData() {
        return data;
    }

    // Implement the isLeaf method
    public boolean isLeaf() {
        return left == null && right == null;
    }

    //Compare a node to a node based on their data
    public int compareTo(Node<T> n) {
        return data.compareTo(n.data);
    }

    //Compare a node to data based on the node's data
    @Override
    public int compareTo(T t) {
        return data.compareTo(t);
    }
}