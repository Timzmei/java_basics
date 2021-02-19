package suffix_tree;

import java.util.ArrayList;

public class Node
{
    private String fragment;
    private ArrayList<Node> nextNodes;
    private ArrayList<Integer> positions;

    public Node(String fragment, ArrayList<Integer> positions)
    {
        this.fragment = fragment;
        nextNodes = new ArrayList<>();
        this.positions = positions;
    }

    public String getFragment()
    {
        return fragment;
    }

    public ArrayList<Integer> getPosition()
    {
        return positions;
    }

    public ArrayList<Node> getNextNodes()
    {
        return nextNodes;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public void setNextNodes(ArrayList<Node> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public void setPosition(ArrayList<Integer> positions) {
        this.positions = positions;
    }

    public void addPosition(Integer position){
        positions.add(position);
    }
}