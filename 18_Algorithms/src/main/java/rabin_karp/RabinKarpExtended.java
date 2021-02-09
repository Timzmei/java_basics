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

    public Integer search(String query)
    {


        if (query == null){
            return -1;
        }
        createIndex(query.length());
        System.out.println("Размер алфавита: " + checkAlphabet().size());

        for (char ch:
             query.toCharArray()) {
            if(!(checkAlphabet().contains((int)ch))){
                System.out.println("В искомой подстроке есть символ не из алфавита");
            }

        }

        int queryHash = 0;
        for (int j = 0; j < query.length(); j++){
            queryHash = (128 * queryHash + query.charAt(j)) % 358;

        }

        for (int i = 0; i < number2position.size(); i++){

            if(queryHash == number2position.get(i)){

                System.out.println("Строка найдена. Позиция строки в тексте: " + (i));
                return i;
            }

        }
        System.out.println("Строка не найдена");
        return -1;
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

    private TreeSet checkAlphabet(){

//        String allText = this.text + query;

        TreeSet<Integer> alphabet = new TreeSet<>();
        for (char ch:
             this.text.toCharArray()) {
            alphabet.add((int) ch);

        }

        return alphabet;
    }
}