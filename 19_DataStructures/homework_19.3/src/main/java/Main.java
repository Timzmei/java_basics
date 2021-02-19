import suffix_tree.Node;
import suffix_tree.SuffixTree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        SuffixTree suffixTree = new SuffixTree("Thirdly, let is have a method to carve out a child node from a given parent. In this process, the parent node is text value will get truncated, and the right-truncated string becomes the text value of the child node. Additionally, the children of the parent will get transferred to the child node");

        printTree(suffixTree.getNodes(), "");


//        for( int i = 0; i < suffixTree.getNodes().size(); i++){
//            ArrayList<Node> nodes = suffixTree.getNodes();
//            System.out.print(nodes.get(i).getFragment() + " " + nodes.get(i).getPosition() + " " + nodes.get(i).getNextNodes());
//            if (nodes.get(i).getNextNodes().size() > 0) {
//
//                System.out.println();
//                for (int j = 0; j < nodes.get(i).getNextNodes().size(); j++) {
//                    System.out.println("\t" + nodes.get(i).getNextNodes().get(j).getFragment() + " " + nodes.get(i).getNextNodes().get(j).getPosition() + ", ");
//                }
//            }
//        }


        System.out.println();


        System.out.println(suffixTree.search("nodes"));
    }

    public static void printTree (ArrayList<Node> nodes, String tab){
        tab = "" + tab;

        for( int i = 0; i < nodes.size(); i++){
            System.out.println(tab + nodes.get(i).getFragment() + " " + nodes.get(i).getPosition() + ", ");
            if (nodes.get(i).getNextNodes().size() > 0) {

                printTree(nodes.get(i).getNextNodes(), tab + "\t");

            }
        }
    }
}
