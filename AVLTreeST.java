//Authors: Will Peters & Erin Corcoran

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;



public class AVLTreeST<Key extends Comparable, Value> {

    /**
     * The root node.
     */
    private Node root;

    /**
     * This class represents an inner node of the AVL tree.
     */
    private class Node {
        private final Key key;   // the key
        private Value val;       // the associated value
        private int height;      // height of the subtree
        private int size;        // number of nodes in subtree
        private Node left;       // left subtree
        private Node right;      // right subtree

        public Node(Key key, Value val, int height, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.height = height;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public AVLTreeST() {
      root = null;
    }

    /**
     * Checks if the symbol table is empty.
     *
     * @return {@code true} if the symbol table is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number key-value pairs in the symbol table.
     *
     * @return the number key-value pairs in the symbol table
     */
    public int size() {
        return size(root);
    }

    /**
     * Returns the number of nodes in the subtree.
     *
     * @param x the subtree
     *
     * @return the number of nodes in the subtree
     */
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    /**
     * Returns the height of the internal AVL tree. It is assumed that the
     * height of an empty tree is -1 and the height of a tree with just one node
     * is 0.
     *
     * @return the height of the internal AVL tree
     */
    public int height() {
        return height(root);
    }

    /**
     * Returns the height of the subtree.
     *
     * @param x the subtree
     *
     * @return the height of the subtree.
     */
    private int height(Node x) {
        if (x == null) return 0;
        return x.height;
    }


    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key);
        if (x == null) return null;
        return x.val;
    }


    private Node get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x;
    }


    public boolean contains(Key key) {
        return get(key) != null;
    }


    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        root = put(root, key, val);

    }

    public void printLevelOrder()
    {
      printLevelOrder(root);
    }
    private void printLevelOrder(Node root)
      {
          // Base Case
          if(root == null)
              return;

          // Create an empty queue for level order tarversal
          Queue<Node> q =new LinkedList<Node>();

          // Enqueue Root and initialize height
          q.add(root);


          while(true)
          {

              // nodeCount (queue size) indicates number of nodes
              // at current level.
              int nodeCount = q.size();
              if(nodeCount == 0)
                  break;

              // Dequeue all nodes of current level and Enqueue all
              // nodes of next level
              while(nodeCount > 0)
              {
                  Node node = q.peek();
                  System.out.print(node.key + " ");
                  q.remove();
                  if(node.left != null)
                      q.add(node.left);
                  if(node.right != null)
                      q.add(node.right);
                  nodeCount--;
              }
              System.out.println();
          }
      }


    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1, 1); // singleton tree's height is 1.

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        }
        else if (cmp > 0) {
            x.right = put(x.right, key, val);
        }
        else {
            x.val = val;
            return x;
        }
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }


    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        }
        else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }


    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    /**
     * Rotates the given subtree to the right.
     *
     * @param x the subtree
     * @return the right rotated subtree
     */
     private Node rotateRight(Node x) {
       Node l = x.left;
       Node c = x.right;
       Node a = l.left;
       Node b = l.right;

     l.right = x;
     x.left = b;
     x.right =c;

//why dont we have to re assign the child nodes to parent of X?
       x.size = 1 + size(x.left) + size(x.right);
       l.size = 1 + size(l.left) + size(l.right);
       x.height = 1 + Math.max(height(x.left), height(x.right));
       l.height = 1 + Math.max(height(l.left), height(l.right));

       return l;
     }

     /**
      * Rotates the given subtree to the left.
      *
      * @param x the subtree
      * @return the left rotated subtree
      */
     private Node rotateLeft(Node x) {
      Node r = x.right;
       Node b = r.left;
       Node a = x.left;
       Node c = r.right;


       r.left = x;
       r.right = c;
       x.left = a;
       x.right = b;

       x.size = 1 + size(x.left) + size(x.right);
       r.size = 1 + size(r.left) + size(r.right);
       x.height = 1 + Math.max(height(x.left), height(x.right));
       r.height = 1 + Math.max(height(r.left), height(r.right));

       return r;
     }





    public static void main(String[] args) {
        AVLTreeST<Integer, Integer> st = new AVLTreeST<Integer, Integer>();

        //your test code
        st.put(15, 15);
        st.put(36, 36);
        st.put(45, 45);
        st.put(11,11);
        st.put(14, 14);
        st.put(1,1);
        st.put(100,100);
        st.put(2, 2);
        st.put(3,3);
        st.put(4,4);
        st.put(5,5);
        st.put(6,6);
        st.put(7, 1);
        st.put(8,1);
        st.put(9,1);
        st.put(10,1);
        st.put(12,1);
        st.printLevelOrder();
        System.out.println(st.size());
    }
}
