package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        front = new Node(45, null, null);
        back = new Node(54, null, null);
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        size += 1;
        if (size == 1) {
            Node add = new Node(item, null, front.next);
            front.next = add;
            add.prev = front;
            add.next = back;
            back.prev = add;
        } else {
            Node add = new Node(item, null, null);
            front.next.prev = add;
            add.next = front.next;
            add.prev = front;
            front.next = add;
        }
    }

    public void addLast(T item) {
        size += 1;
        if (size == 1) {
            Node add = new Node(item, null, front.next);
            front.next = add;
            add.prev = front;
            add.next = back;
            back.prev = add;
        } else {
            Node add2 = new Node(item, null, null);
            back.prev.next = add2;
            add2.prev = back.prev;
            back.prev = add2;
            add2.next = back;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T num = front.next.value;
        front.next.next.prev = front;
        front.next = front.next.next;
        return num;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T last = back.prev.value;
        back.prev.prev.next = back;
        back.prev = back.prev.prev;
        return last;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        if (index > size / 2) {
            int num = size - 1 - index;
            Node temp = back;
            for (int i = 0; i < num; i++) {
                temp = temp.prev;
            }
            T re = (T) temp.prev.value;
            return re;
        } else {
            Node cur = front.next;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            T re = (T) cur.value;
            return re;
        }
    }

    public int size() {
        return size;
    }
}
