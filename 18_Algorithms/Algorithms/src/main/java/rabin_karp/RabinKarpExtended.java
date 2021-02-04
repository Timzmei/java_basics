package rabin_karp;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class RabinKarpExtended
{
    private String text;
    private TreeMap<Integer, Integer> number2position;

    public RabinKarpExtended(String text)
    {
        this.text = text;
        number2position = new TreeMap<>();
    }

    public void search(String query)
    {
        createIndex(query.length());
        System.out.println("Размер алфавита: " + checkAlphabet(query));
        int queryHash = 0;
        for (int j = 0; j < query.length(); j++){
            queryHash = (128 * queryHash + query.charAt(j)) % 358;

        }

        for (int i = 0; i < number2position.size(); i++){

            if(queryHash == number2position.get(i)){

                System.out.println("Строка найдена. Позиция строки в тексте: " + (i + 1));
                return;
            }

        }
        System.out.println("Строка не найдена");
    }

    private void createIndex(int queryLength)
    {
        //TODO: implement text indexing
        for (int i = 0; i < this.text.length() - queryLength; i++) {
            int p = 0;

//            String subtext = text.substring(i, i + queryLength);

            for (int j = i; j < i + queryLength; j++){

                p = (128 * p + text.charAt(j)) % 358;
//                p = p + text.charAt(j);


            }
//            System.out.println(i + " " + p);
            number2position.put(i, p);



        }

    }

    private int checkAlphabet(String query){

        String allText = this.text + query;

        TreeSet<Integer> alphabet = new TreeSet<>();
        for (char ch:
             allText.toCharArray()) {
            alphabet.add((int) ch);

        }

        return alphabet.size();
    }
}