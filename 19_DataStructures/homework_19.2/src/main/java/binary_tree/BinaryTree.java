package binary_tree;

import com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck;

public class BinaryTree {
    private Node root;

    public void addNode(String data) {
        //TODO implement method
        if (this.root == null){
            this.root = new Node(data);
        }
        addNodeToTree(data, this.root);

    }

    public void addNodeToTree(String data, Node node){
        Node temp = new Node(data);

        if(Integer.parseInt(node.getData().replaceAll("\\D", "")) < Integer.parseInt(data.replaceAll("\\D", ""))){

            if (node.getRight() == null){
                node.setRight(temp);
            }
            addNodeToTree(data, node.getRight());
        }
        else if(Integer.parseInt(node.getData().replaceAll("\\D", "")) > Integer.parseInt(data.replaceAll("\\D", ""))){
            if (node.getLeft() == null){
                node.setLeft(temp);
            }
            addNodeToTree(data, node.getLeft());
        }
    }

    public boolean isContains(String data) {

        boolean result = isContainsInNode(data, this.root);
        System.out.println(result);
        return result;
    }

    public Node getRoot() {
        return root;
    }

    public boolean isContainsInNode(String data, Node node){
        boolean result = false;

        if(Integer.parseInt(node.getData().replaceAll("\\D", "")) < Integer.parseInt(data.replaceAll("\\D", ""))){
            if (node.getRight() != null){
                result = isContainsInNode(data, node.getRight());
            }
        }
        else if(Integer.parseInt(node.getData().replaceAll("\\D", "")) > Integer.parseInt(data.replaceAll("\\D", ""))){

            if (node.getLeft() != null){
                result = isContainsInNode(data, node.getLeft());
            }
        }
        else if(Integer.parseInt(node.getData().replaceAll("\\D", "")) == Integer.parseInt(data.replaceAll("\\D", ""))){
            result = true;
        }
        return result;

    }
}
