package suffix_tree;

import java.util.*;

public class SuffixTree
{
    private String text;
    private ArrayList<Node> nodes;
    private Node root;

    public SuffixTree(String text)
    {
        this.text = text;
        nodes = new ArrayList<>();
//        System.out.println(nodes);
        build();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    private void build()
    {
        //TODO
        List<Word> listWords = splitText(text);
        for (Word listWord : listWords) {

            String fragment = listWord.getWord();

            if (this.nodes != null && this.nodes.size() > 0) {
                buildNode(fragment, listWord, this.nodes);
            } else {
                ArrayList<Integer> positions = new ArrayList<>();
                positions.add(listWord.getPosition());
                Node newNode = new Node(fragment, positions);
                if (this.nodes != null) {
                    this.nodes.add(newNode);
                }


            }
        }

    }

    public List<Integer> search(String query) {
        ArrayList<Integer> positions;
        //TODO
        Node node = searchNode(query, this.nodes);
        positions = node.getPosition();
        return positions;
    }

    private Node searchNode (String query, List<Node> nodes) {
        for (Node node : nodes) {
            String fragment = node.getFragment();
            if (query.regionMatches(true, 0, fragment, 0, fragment.length())) {
                if (fragment.equals(query)) {
                    return node;
                } else {
                    return searchNode(query.substring(fragment.length()), node.getNextNodes());
                }
            }

        }
        return null;

    }


    private void buildNode(String fragment, Word word, List<Node> nodeList){

            if (findFragmentNode(fragment, word, nodeList)) {
                Node newNode = buildNewNode(fragment, word.getPosition(), null, null);
                nodeList.add(newNode);
            }


    }

    private Boolean findFragmentNode(String fragment, Word word, List<Node> nodeList){
        for (int j = 0; j < nodeList.size(); j++) {
            String fragmentNode = nodeList.get(j).getFragment();

            if (fragmentNode.regionMatches(true, 0, fragment, 0, 1)) {

                String prefix = getLongestCommonPrefix(fragment, fragmentNode);

                if (prefix.length() == fragmentNode.length()) {
                    String suffixNewNode = getSuffix(prefix, fragment);
                    if (suffixNewNode != null){
                        buildNode(suffixNewNode, word, nodeList.get(j).getNextNodes());
                    } else {
                        ArrayList<Integer> newNodePositions;
                        newNodePositions = nodeList.get(j).getPosition();
                        newNodePositions.add(word.getPosition());
                        nodeList.get(j).setPosition(newNodePositions);

                    }

                } else {
                    Node newSuffixNode = nodeList.get(j);
                    String suffixTempNode = getSuffix(prefix, nodeList.get(j).getFragment());
                    newSuffixNode.setFragment(suffixTempNode);
                    Node newSuffixNode2 = buildNewNode(getSuffix(prefix, fragment), word.getPosition(), null, null);
                    Node newNode =  buildNewNode(prefix, -1, newSuffixNode, newSuffixNode2);
                    nodeList.set(j, newNode);

                }
                return false;
            }
        }
        return true;
    }

    private Node buildNewNode(String fragment, Integer position, Node nextNode1, Node nextNode2){
        ArrayList<Integer> newNodePositions = new ArrayList<>();
        newNodePositions.add(position);
        Node newNode = new Node(fragment, newNodePositions);

        ArrayList<Node> nextNewNode = new ArrayList<>();

        if(nextNode1 != null) {
            nextNewNode.add(nextNode1);
            if (nextNode2 != null) {
                nextNewNode.add(nextNode2);
            }
            newNode.setNextNodes(nextNewNode);
        }

        return  newNode;
    }


    private ArrayList<Word> splitText(String text){

        String[] words = text.split("\\s");

        ArrayList<Word> listWords = new ArrayList<>();

        int position = 0;
        for (String word : words) {
            listWords.add(new Word(word, position));

            position = position + word.length() + 1;
        }

        return listWords;
    }


    private String getLongestCommonPrefix(String str1, String str2) // Поиск максимально большого префикса
    {
        int compareLength = Math.min(str1.length(), str2.length());
        for (int i = 1; i <= compareLength; i++) {
            if (!str1.regionMatches(true, 0, str2, 0, i)) {
                return str1.substring(0, i - 1);
            }
        }
        return str1.substring(0, compareLength);
    }

    private String getSuffix(String prefix, String str2) // Поиск суффикса
    {
        int endIndex = str2.length();
        boolean b = str2.regionMatches(true, 0, prefix, 0, prefix.length());
        if(b && prefix.length() < str2.length()){
            return str2.substring(prefix.length(), endIndex);
        }

        return null;
    }







}