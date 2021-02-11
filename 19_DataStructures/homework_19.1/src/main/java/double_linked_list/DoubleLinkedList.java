package double_linked_list;

import java.util.Objects;

public class DoubleLinkedList<T> {
    private ListItem<T> head;
    private ListItem<T> tail;
    private int size;

    public ListItem<T> popHeadElement() {
        // TODO
        ListItem head = this.head;

        if(size != 0){
            if (size == 1){
                removeHeadElement();
            }
            else {
                ListItem newHead = this.head.getNext();
                newHead.setPrev(null);
                this.head = newHead;

                size--;
            }
        }
        return head;
    }

    public ListItem<T> popTailElement() {
        // TODO
        ListItem tail = this.tail;

        if(size != 0){
            if(size == 1){
                removeTailElement();
            }
            else {
                ListItem newTail = this.tail.getPrev();
                newTail.setNext(null);
                this.tail = newTail;

                size--;
            }
        }
        return tail;
    }

    public void removeHeadElement() {
        // TODO
        if(size != 0){
            if(size == 1){
                this.head = null;
                this.tail = null;
            }
            else {
                ListItem temp = this.head.getNext();
                temp.setPrev(null);
                this.head = temp;
            }
            size--;
        }

    }

    public void removeTailElement() {
        // TODO
        if(size != 0){
            if(size == 1){
                this.head = null;
                this.tail = null;
            }
            else {
                ListItem temp = getTailElement().getPrev();
                temp.setNext(null);
                tail = temp;

            }
            size--;
        }

    }

    public void addToHead(T data) {
        // TODO

        ListItem temp = new ListItem(data);
        if (getSize() == 0){
            this.head = temp;
            this.tail = temp;
        }
        else{
            temp.setNext(this.head);
            this.head.setPrev(temp);
            temp.setPrev(null);
            this.head = temp;
        }
        size++;
    }

    public void addToTail(T data) {
        // TODO
        ListItem temp = new ListItem(data);
        if (getSize() == 0){
            this.head = temp;
            this.tail = temp;
        }
        else{
            temp.setPrev(this.tail);
            this.tail.setNext(temp);
            temp.setNext(null);
            this.tail = temp;
        }
        size++;
    }

    public int getSize() {
        return size;
    }

    public ListItem<T> getHeadElement() {
        return head;
    }

    public ListItem<T> getTailElement() {
        return tail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleLinkedList<T> that = (DoubleLinkedList<T>) o;
        return Objects.equals(head, that.head) && Objects.equals(tail, that.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail);
    }

    @Override
    public String toString() {
        if (head == null) {
            return "DoubleLinkedList is empty size = " + size;
        }

        StringBuilder stringBuilder = new StringBuilder(head.toString());
        ListItem<T> item = head;
        while (item.next != null) {
            if (item.next.prev == item) {
                stringBuilder.append("<-");
            }

            stringBuilder.append(" -> ").append(item.next);
            item = item.next;
        }

        return "DoubleLinkedList{size=" + size + "\n" + stringBuilder.toString() + "}";
    }
}