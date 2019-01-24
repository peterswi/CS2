//Name : Will Peters
//Partner : Erin Corcorran

import java.util.LinkedList;
import java.util.Queue;

public class MaxPQ<Key extends Comparable>{

  private class Node{
    private Key key;
    private Node parent, left, right;

    private Node(Key key){
      this.key = key;
      parent = null;
      left = null;
      right = null;
    }
  }

  private Node root;
  private int size;

  public MaxPQ(){
    root = null;
    size = 0;
  }
  public int size(){
    return this.size;
  }

  public void insert(Key x){
    if (x == null){
      throw new IllegalArgumentException("Entered Key is null.");
    }

    if (root == null){
      root = new Node(x);
      size ++;
    }
    else if(size == 1){
       Node newNode= new Node(x);
       newNode.parent = root;
       root.left = newNode;
       size++;
       swim(newNode);
    }
    else{
      size ++;
      double s = (double) size;
      int[] binary = decToBinary(s);
      Node parent = root;
      int direction;
      Node newNode = new Node(x);
      for (int i =1; i< (binary.length); i++){//starting at 1 because we ignore first number
        direction = binary[i];
        if(direction ==0){
          if(parent.left==null){
            parent.left = newNode;
            newNode.parent = parent;
            swim(newNode);
          }
          else{
            parent = parent.left;
          }
        }
        else if(direction ==1){
          if (parent.right ==null){
            parent.right = newNode;
            newNode.parent = parent;
            swim(newNode);
          }
          else{
            parent = parent.right;
          }
        }
      }
//      System.out.println("inserted");
    }
  }


  private int[] decToBinary(double x){
    double s = ((Math.log(x))/(Math.log(2.0)));

    int size = (int) s;
    int container[] = new int[size+1];
    int y = (int) x;

    int i=0;
    while(y > 0){
      container[i] = y%2;
      i++;
      y = y/2;
    }
    int binaryContainer[] = new int[size+1];
    for(int j=0; j< container.length; j++){
      i--;
      binaryContainer[j]= container[i];
    }
    return binaryContainer;
  }

  private void swim(Node z) {
   if (z != null && z.parent != null) {
    Key tempHolder; // temporary holder to swap values
    while (z.parent != null && (z.key.compareTo(z.parent.key) > 0)) {
      Node zParent = z.parent;
      tempHolder = z.key;
      z.key = zParent.key;
      zParent.key = tempHolder;
      z = zParent;
//      System.out.println("swapped");
    }
  }
  }


//insert printLevelOrder function for testing
public void printLevelOrder(){
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

  public static void main(String[] args){

    MaxPQ<Integer> newPQ= new MaxPQ<Integer>();

    newPQ.insert(10);
    newPQ.insert(100);
    newPQ.insert(5);
    newPQ.insert(450);
    newPQ.insert(1);
    newPQ.insert(7);
    newPQ.insert(18);
    newPQ.insert(11);
    newPQ.insert(70);
    newPQ.insert(1);
    newPQ.insert(38);
    newPQ.insert(73);
    newPQ.insert(800);
    newPQ.insert(540);
    newPQ.insert(74);
    newPQ.insert(1000);
    newPQ.insert(750);
    newPQ.insert(15);
    newPQ.insert(301);
    newPQ.insert(801);
    newPQ.insert(27);
    newPQ.insert(0);
    newPQ.insert(1);
    newPQ.insert(550);


    newPQ.printLevelOrder();
    System.out.println("This max priority queue has size "+newPQ.size()+".");
  }
}
