package delivery;

import java.util.ArrayList;
import java.util.List;

public class BST {
    //node class that defines BST node
    class Node {

        private Auto auto;
        private Node left, right;

        public Node(Auto auto) {
            this.auto = auto;
            left = right = null;
        }

        public Auto getAuto() {
            return auto;
        }
    }

    // BST root node
    private Node root;

    public Node getRoot() {
        return root;
    }

    // Constructor for BST =>initial empty tree
    BST() {
        root = null;
    }

    //delete a node from BST
    void deleteKey(Auto auto) {
        root = delete_Recursive(root, auto);
    }

    //recursive delete function
    Node delete_Recursive(Node root, Auto otherAuto) {
        //tree is empty
        if (root == null) return root;

        //traverse the tree
        int comparison = root.auto.compareTo(otherAuto);
        if (comparison < 0)     //traverse left subtree
            root.left = delete_Recursive(root.left, otherAuto);
        else if (comparison > 0)  //traverse right subtree
            root.right = delete_Recursive(root.right, otherAuto);
        else {
            // node contains only one child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node has two children;
            //get inorder successor (min value in the right subtree)
            root.auto = minValue(root.right);

            // Delete the inorder successor
            root.right = delete_Recursive(root.right, root.auto);
        }
        return root;
    }

    Auto minValue(Node root) {
        //initially minval = root
        Auto minval = root.auto;
        //find minval
        while (root.left != null) {
            minval = root.left.auto;
            root = root.left;
        }
        return minval;
    }

    Auto maxValue(Node root) {
        //initially maxval = root
        Auto maxval = root.auto;
        //find maxval
        while (root.right != null) {
            maxval = root.right.auto;
            root = root.right;
        }
        return maxval;
    }

    // insert a node in BST
    void insert(Auto auto) {
        root = insert_Recursive(root, auto);
    }

    //recursive insert function
    Node insert_Recursive(Node root, Auto otherAuto) {
        //tree is empty
        if (root == null) {
            root = new Node(otherAuto);
            return root;
        }
        //traverse the tree
        int comparison = root.auto.compareTo(otherAuto);
        if (comparison < 0)     //insert in the left subtree
            root.left = insert_Recursive(root.left, otherAuto);
        else if (comparison > 0)    //insert in the right subtree
            root.right = insert_Recursive(root.right, otherAuto);
        // return pointer
        return root;
    }

    // method for inorder traversal of BST
    void inorder() {
        inorder_Recursive(root);
    }

    // find nodes with value greater
    public List<Node> nodesGreaterThanXOrEqual(Node node, int weight) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<Node> left = nodesGreaterThanXOrEqual(node.left, weight);
        List<Node> right = nodesGreaterThanXOrEqual(node.right, weight);

        List<Node> current = new ArrayList<>();
        if (node.auto.getMaxWeight() >= weight) {
            current.add(node);
        }
        current.addAll(left);
        current.addAll(right);

        return current;

        //return (node.auto.getMaxWeight() > weight ? 1 : 0) + countLeft + countRight;
    }

    // recursively traverse the BST
    void inorder_Recursive(Node root) {
        if (root != null) {
            inorder_Recursive(root.left);
            System.out.print(root.auto + " ");
            inorder_Recursive(root.right);
        }
    }

    boolean search(Auto auto) {
        root = search_Recursive(root, auto);
        if (root != null)
            return true;
        else
            return false;
    }

    //recursive insert function
    Node search_Recursive(Node root, Auto auto) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.auto.equals(auto))
            return root;
        // val is greater than root's key
        int comparison = root.auto.compareTo(auto);
        if (comparison > 0)
            return search_Recursive(root.left, auto);
        // val is less than root's key
        return search_Recursive(root.right, auto);
    }

    public static void main(String[] args) {
        //create a BST object
        BST bst = new BST();
        /* BST tree example - only max weight (max load) is important
              45
           /     \
          10      90
         /  \    /
        7   12  50   */
        //insert data into BST
        bst.insert(new Auto(45, "KAMAZ"));
        bst.insert(new Auto(10, "Trabant"));
        bst.insert(new Auto(7, "Bicycle"));
        bst.insert(new Auto(12, "Upgraded bicycle"));
        bst.insert(new Auto(90, "Tesla"));
        bst.insert(new Auto(50, "ZAZ"));
        //print the BST
        System.out.println("The BST Created with input data(Left-root-right):");
        bst.inorder();
        System.out.println();

        Auto minAuto = bst.minValue(bst.root);
        if (minAuto != null) {
            System.out.println("minimal load " + minAuto.getMaxWeight());
        }

        Auto maxAuto = bst.maxValue(bst.root);
        if (maxAuto != null) {
            System.out.println("maximal load " + maxAuto.getMaxWeight());
        }
        System.out.println();
        System.out.println("----------------------");
        List<BST.Node> greaterThan44 = bst.nodesGreaterThanXOrEqual(bst.root, 44);
        greaterThan44.stream().forEach(node -> {
            System.out.println(node.auto);
        });
        System.out.println("----------------------");

        //delete leaf node
        System.out.println("\nThe BST after Delete 12(leaf node):");
        bst.deleteKey(new Auto(12, "any"));
        bst.inorder();
        //delete the node with one child
        System.out.println("\nThe BST after Delete 90 (node with 1 child):");
        bst.deleteKey(new Auto(90, "any"));
        bst.inorder();

        //delete node with two children
        System.out.println("\nThe BST after Delete 45 (Node with two children):");
        bst.deleteKey(new Auto(45, "any"));
        bst.inorder();
        //search a key in the BST
        boolean ret_val = bst.search(new Auto(50, "any"));
        System.out.println("\nKey 50 found in BST: " + ret_val);
        ret_val = bst.search(new Auto(12, "any"));
        System.out.println("\nKey 12 found in BST: " + ret_val);

        /* expected output
The BST Created with input data(Left-root-right):
Auto{maxWeight=7, make='Bicycle'} Auto{maxWeight=10, make='Trabant'} Auto{maxWeight=12, make='Upgraded bicycle'} Auto{maxWeight=45, make='KAMAZ'} Auto{maxWeight=50, make='ZAZ'} Auto{maxWeight=90, make='Tesla'}
minimal load 7
maximal load 90

----------------------
Auto{maxWeight=45, make='KAMAZ'}
Auto{maxWeight=90, make='Tesla'}
Auto{maxWeight=50, make='ZAZ'}
----------------------

The BST after Delete 12(leaf node):
Auto{maxWeight=7, make='Bicycle'} Auto{maxWeight=10, make='Trabant'} Auto{maxWeight=45, make='KAMAZ'} Auto{maxWeight=50, make='ZAZ'} Auto{maxWeight=90, make='Tesla'}
The BST after Delete 90 (node with 1 child):
Auto{maxWeight=7, make='Bicycle'} Auto{maxWeight=10, make='Trabant'} Auto{maxWeight=45, make='KAMAZ'} Auto{maxWeight=50, make='ZAZ'}
The BST after Delete 45 (Node with two children):
Auto{maxWeight=7, make='Bicycle'} Auto{maxWeight=10, make='Trabant'} Auto{maxWeight=50, make='ZAZ'}
Key 50 found in BST: true

Key 12 found in BST: false

         */
    }
}
