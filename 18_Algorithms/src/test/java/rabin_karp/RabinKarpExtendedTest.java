package rabin_karp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RabinKarpExtendedTest {

    @Test
    @DisplayName("Искомая подстрока есть в тексте")
    public void foundTest(){
        String text = "ASDFGHJKLASDDSADFAAFASFASFASSDASASDASDASFASFASFASFDASFAFGDSAGFADAFDSFA";
        String query = "AFASFASFAS";
        RabinKarpExtended rabin = new RabinKarpExtended(text);
        Integer actual = rabin.search(query);
        Integer index = text.indexOf(query);
//        List<Integer> expected = new ArrayList<>();
//        for (int i = index; i < index + query.length(); i++) {
//            expected.add(i);
//        }
        assertEquals(index.toString(), actual.toString());
    }

    @Test
    @DisplayName("Искомой подстроки в тексте нет")
    public void notFoundTest(){
        String text = "ASDF";
        String query = "SDFA";
        RabinKarpExtended rabin = new RabinKarpExtended(text);
        Integer act = rabin.search(query);
        assertEquals(act.toString(), "-1");
    }

//    @Test
//    @DisplayName("Алфавит больше 9 символов")
//    public void largeAlphabetTest(){
//        String text = "ASDFGGHJKL:ASDEEFE WEFWEFBWEIFWETWT";
//        assertThrows(Exception.class, () -> new RabinKarpExtended(text));
//    }

    @Test
    @DisplayName("В искомой подстроке есть символ не из алфавита")
    public void withAnotherCharSetTest(){
        String text = "asdfgadasfgsdgsadgasgsadgasdgasdgsdgasdgasdgasdfasg";
        String query = "asdfga7";
        RabinKarpExtended rabin = new RabinKarpExtended(text);
        Integer act = rabin.search(query);
        assertEquals(act.toString(), "-1");
    }

    @Test
    @DisplayName("Пустая строка")
    public void emptyString(){
        String text = "";
        String query = "";
        RabinKarpExtended rabin = new RabinKarpExtended(text);
        Integer act = rabin.search(query);
        assertEquals(act.toString(), "-1");
    }

}