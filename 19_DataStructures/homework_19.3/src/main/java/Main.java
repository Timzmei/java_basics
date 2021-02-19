import suffix_tree.Node;
import suffix_tree.SuffixTree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        SuffixTree suffixTree = new SuffixTree("A suffix tree also stores the position of the suffix in the leaf node");


        for( int i = 0; i < suffixTree.getNodes().size(); i++){
            ArrayList<Node> nodes = suffixTree.getNodes();
            System.out.print(nodes.get(i).getFragment() + " " + nodes.get(i).getPosition() + " " + nodes.get(i).getNextNodes());
            if (nodes.get(i).getNextNodes().size() > 0) {

                System.out.println();
                for (int j = 0; j < nodes.get(i).getNextNodes().size(); j++) {
                    System.out.println(nodes.get(i).getNextNodes().get(j).getFragment() + " " + nodes.get(i).getNextNodes().get(j).getPosition() + ", ");
                }
            }
        }
        System.out.println();


        System.out.println(suffixTree.search("stores"));
    }
}
