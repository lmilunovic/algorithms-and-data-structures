package com.ladislav;

/**
 * Created by Ladislav on 4/17/2017.
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node<K, V> root;

    public void put(K key, V value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node<K, V> put(Node<K, V> h, K key, V value) {

        if (h == null) {
            return new Node<>(key, value, RED);
        }

        int comparison = key.compareTo(h.key);

        if (comparison > 0) {
            h.right = put(h.right, key, value);
        } else if (comparison < 0) {
            h.left = put(h.left, key, value);
        } else {
            h.value = value;
        }

        if (isRed(h.right) && (!isRed(h.left))) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && (isRed(h.left.left))) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        return h;
    }

    public V get(K key) {

        Node<K, V> x = root;

        while (x != null) {

            int comparison = key.compareTo(x.key);

            if (comparison < 0) {
                x = x.left;
            } else if (comparison > 0) {
                x = x.right;
            } else {
                return x.value;
            }
        }

        return null;
    }

    private Node<K, V> rotateLeft(Node<K, V> h) {
        Node<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node<K, V> rotateRight(Node<K, V> h) {
        Node<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node<K, V> h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private boolean isRed(Node<K, V> x) {
        return x != null && x.color == RED;
    }

    public boolean checkPaths() {
        return compareBlackCount(root, -10, 0) != -1;  // Magic Constant -10, put psfi SENTINEL?
    }

    private int compareBlackCount(Node<K, V> node, int endCount, int current) {

        if (node == null) {
            current++;
            if (endCount == -10) {
                endCount = current;
                return endCount;
            }
            if (current == endCount) {
                return endCount;
            } else {
                return -1;
            }
        }

        if (!isRed(node)) {
            current++;
        }
        endCount = compareBlackCount(node.left, endCount, current);
        endCount = compareBlackCount(node.right, endCount, current);

        return endCount;
    }


    private class Node<K, V> {

        K key;
        V value;
        boolean color;

        Node<K, V> left;
        Node<K, V> right;

        private Node(K key, V value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }
}

