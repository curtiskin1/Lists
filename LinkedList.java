public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private boolean isSorted;
    private int count;

    public LinkedList() {
        head = null;
        isSorted = true;
    }
    @Override
    public boolean add(T element) {
        Node<T> temp;
        Node<T> ptr;
        if (element == null) {
            return false;
        }
        ptr = new Node<T>(element);
        if (head == null) {
            head = ptr;
        } else {
            temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(ptr);
        }
        count++;
        isSorted = false;
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        Node<T> temp;
        Node<T> ptr;
        if (element == null || (index < 0 || index >= count)) {
            return false;
        } else {
            ptr = new Node<T>(element);
            temp = head;
            int idx = 0;
            while (idx++ < index) {
                temp = temp.getNext();
            }
            temp.setNext(ptr);
        }
        count++;
        isSorted = false;
        return true;
    }

    @Override
    public void clear() {
        Node<T> ptr = head;
        Node<T> tail = null;
        if (head != null) {
            while (ptr != null) {
                tail = ptr.getNext();
                ptr.setNext(null);
                ptr = tail;
            }
        } else {
            System.out.println("List is already empty");
            isSorted = true;
        }
        count = 0;
    }

    @Override
    public T get(int index) {
        Node<T> temp;
        T element;
        if (index < 0 || index >= count) {
            return null;
        } else if (head == null) {
            return null;
        } else {
            temp = head;
            int idx = 0;
            while (idx++ < index) {
                temp = temp.getNext();
            }
            return temp.getData();
        }
    }

    @Override
    public int indexOf(T element) {
        Node<T> temp;
        Node<T> ptr;
        if (element == null) {
            return -1;
        } else if (isSorted) {
            System.out.println("Using some cool sort method and iterating over the size of the LinkedList...");
//            for (int i = 0; i < count; i++) {
//                temp = head;
//                ptr = temp.getNext();
//                while (ptr != null) {
//                    if (element.equals(ptr.getData())) {
//                        return i;
//                    }
//                    ptr = ptr.getNext();
//                }
//            }
        } else {
            temp = head;
            int idx = 0;
            while (temp != null) {
                if (temp.getData().compareTo(element) == 0) {
                    return idx;
                }
                temp = temp.getNext();
                idx++;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (count != 0) {
            System.out.println("The LinkedList is NOT empty");
        }
        return count == 0 || head == null;      // returns true if the list is empty, that is, its size/length is 0
    }

    @Override
    public int size() {
        Node<T> temp = head;
        return count;
    }

    @Override
    public void sort() {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public void equalTo(T element) {

    }

    @Override
    public void reverse() {

    }

    @Override
    public void merge(List<T> otherList) {

    }

    @Override
    public void pairSwap() {

    }

    @Override
    public boolean isSorted() {
        return false;
    }
}
