import double_linked_list.DoubleLinkedList;
import double_linked_list.ListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тестирование двусвязного списка")
public class DoubleLinkedListTest {
    private DoubleLinkedList<String> doubleLinkedList;

    @BeforeEach
    void setUp() {
        doubleLinkedList = new DoubleLinkedList<>();
    }

    @Test
    @DisplayName("Проверка добавления одного элемента в конец списка")
    void addOneItemToHead() {
        ListItem<String> item = new ListItem<>("new");
        doubleLinkedList.addToHead("new");

        assertEquals(item, doubleLinkedList.getTailElement());
        assertEquals(item, doubleLinkedList.getHeadElement());
        assertEquals(1, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Проверка добавления одного элемента в конец списка")
    void addOneItemToTail() {
        ListItem<String> item = new ListItem<>("new");
        doubleLinkedList.addToTail("new");

        assertEquals(item, doubleLinkedList.getTailElement());
        assertEquals(item, doubleLinkedList.getHeadElement());
        assertEquals(1, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Проверка добавления двух элементов в конец списка")
    void addTwoItemsToTail() {
        doubleLinkedList.addToTail("first");
        doubleLinkedList.addToTail("second");

        assertEquals("second", doubleLinkedList.getTailElement().getData());
        assertEquals("first", doubleLinkedList.getHeadElement().getData());
        assertEquals(2, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Проверка добавления двух элементов в начало списка")
    void addTwoItemsToHead() {
        doubleLinkedList.addToHead("first");
        doubleLinkedList.addToHead("second");

        assertEquals("first", doubleLinkedList.getTailElement().getData());
        assertEquals("second", doubleLinkedList.getHeadElement().getData());
        assertEquals(2, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Удаление элемента head из списка длиной 1 элемент")
    void removeHeadFromListSizeOneElement() {
        doubleLinkedList.addToHead("first");
        doubleLinkedList.removeHeadElement();

        assertNull(doubleLinkedList.getHeadElement());
        assertEquals(0, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Удаление элемента head из списка длиной 3 элемента")
    void removeHeadFromListSizeThreeElements() {
        doubleLinkedList.addToHead("first");
        doubleLinkedList.addToHead("second");
        doubleLinkedList.addToHead("third");
        doubleLinkedList.removeHeadElement();

        assertEquals("second", doubleLinkedList.getHeadElement().getData());
        assertEquals(2, doubleLinkedList.getSize());
    }


    @Test
    @DisplayName("Удаление элемента tail из списка длиной 1 элемент")
    void removeTailFromListSizeOneElement() {
        doubleLinkedList.addToHead("first");
        doubleLinkedList.removeTailElement();

        assertNull(doubleLinkedList.getTailElement());
        assertEquals(0, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Удаление элемента tail из списка длиной 3 элемента")
    void removeTailFromListSizeThreeElements() {
        doubleLinkedList.addToTail("first");
        doubleLinkedList.addToTail("second");
        doubleLinkedList.addToTail("third");
        doubleLinkedList.removeTailElement();

        assertEquals("second", doubleLinkedList.getTailElement().getData());
        assertEquals(2, doubleLinkedList.getSize());
    }


    @Test
    @DisplayName("Pop (удаление и возврат) элемента tail из списка длиной 1 элемент")
    void popTailFromListSizeOneElement() {
        doubleLinkedList.addToHead("first");
        ListItem<String> popped = doubleLinkedList.popTailElement();

        assertEquals("first", popped.getData());
        assertEquals(0, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Pop (удаление и возврат) элемента tail из списка длиной 3 элемента")
    void popTailFromListSizeThreeElements() {
        doubleLinkedList.addToTail("first");
        doubleLinkedList.addToTail("second");
        doubleLinkedList.addToTail("third");

        ListItem<String> popped = doubleLinkedList.popTailElement();

        assertEquals("third", popped.getData());
        assertEquals(2, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Pop (удаление и возврат) элемента head из списка длиной 1 элемент")
    void popHeadFromListSizeOneElement() {
        doubleLinkedList.addToHead("first");
        ListItem<String> popped = doubleLinkedList.popHeadElement();

        assertEquals("first", popped.getData());
        assertEquals(0, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Pop (удаление и возврат) элемента head из списка длиной 3 элемента")
    void popHeadFromListSizeThreeElements() {
        doubleLinkedList.addToHead("first");
        doubleLinkedList.addToHead("second");
        doubleLinkedList.addToHead("third");

        ListItem<String> popped = doubleLinkedList.popHeadElement();

        assertEquals("third", popped.getData());
        assertEquals(2, doubleLinkedList.getSize());
    }

    @Test
    @DisplayName("Смешанное добавление и получение Head элементов")
    void mixAddAndPopHead() {
        doubleLinkedList.addToHead("head1");
        doubleLinkedList.addToTail("tail1");
        doubleLinkedList.addToHead("head2");
        doubleLinkedList.addToTail("tail2");

        assertEquals("head2", doubleLinkedList.popHeadElement().getData());
        assertEquals("head1", doubleLinkedList.popHeadElement().getData());
        assertEquals("tail1", doubleLinkedList.popHeadElement().getData());
        assertEquals("tail2", doubleLinkedList.popHeadElement().getData());
        assertNull(doubleLinkedList.getHeadElement());
    }

    @Test
    @DisplayName("Смешанное добавление и получение Tail элементов")
    void mixAddAndPopTail() {
        doubleLinkedList.addToHead("head1");
        doubleLinkedList.addToTail("tail1");
        doubleLinkedList.addToHead("head2");
        doubleLinkedList.addToTail("tail2");

        assertEquals("tail2", doubleLinkedList.popTailElement().getData());
        assertEquals("tail1", doubleLinkedList.popTailElement().getData());
        assertEquals("head1", doubleLinkedList.popTailElement().getData());
        assertEquals("head2", doubleLinkedList.popTailElement().getData());
        assertNull(doubleLinkedList.getTailElement());
    }
}
