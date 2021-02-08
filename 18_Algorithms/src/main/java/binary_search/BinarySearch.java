package binary_search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

public class BinarySearch
{
    private ArrayList<String> list;

    public BinarySearch(ArrayList<String> list)
    {
        this.list = list;
    }

    public int search(String query)
    {
        return search(query, 0, list.size() - 1);
    }

    private int search(String query, int from, int to)
    {
        //TODO: write code here

        if(list.isEmpty() || from >= to){
            return -1;
        }

        TreeMap<String, Integer> mapList = new TreeMap<>();

        for (int i = 0; i < list.size(); i++) {
            mapList.put(list.get(i), i);
        }
        Collections.sort(list);



        int middle = (from + to) / 2;
        int comparison = query.compareTo(list.get(middle));
        if (comparison == 0){
            return mapList.get(list.get(middle));
        }
        if(comparison > 0){

            return search(query, middle, to);
        }

        if(comparison < 0){
            return search(query, from, middle);
        }

        return -1;
    }
}
