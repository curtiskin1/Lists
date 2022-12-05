// Created by Ming Tong (tong0154) and Curtis Kokuloku (kokul003)
// On 11/01/2022

public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private boolean isSorted;
    private int counter;

    public LinkedList() {
        head = null;
        isSorted = true;
    }

    // helper method to check if the list is sorted
    public boolean isSortedChecker() {
        if (head == null) {
            return true;
        }
        for (Node<T> t = head; t.getNext() != null; t = t.getNext()) {
            if (t.getData().compareTo(t.getNext().getData()) > 0) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean add(T element) {
        if (element != null) {
            Node<T> newNode = new Node<T>(element);
            if (head == null) {
                head = newNode;
                head.setNext(null);
            } else {
                Node<T> temp = head;
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
            counter++;
            isSorted = isSortedChecker();
            return true;
        }
        return false;
    }

    @Override
    public boolean add(int index, T element) {
        if (element != null && index >= 0 && index < size()) {

            int idx = 0;
            Node<T> newNode = new Node<T>(element);

            if (index == 0) {   // case 1: index and 0 and there's an element at teh head
                newNode.setNext(head);
                head = newNode;
                counter++;
                isSorted = isSortedChecker();
                return true;
            } else if (index == 1) {    // case 2: index is 1
                newNode.setNext(head.getNext());
                head.setNext(newNode);
                counter++;
                isSorted = isSortedChecker();
                return true;
            } else {    // case 3: index is at the end
                Node<T> temp = head;
                while (temp.getNext().getNext() != null && idx + 1 < index) {
                    idx++;
                    temp = temp.getNext();
                }
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
                counter++;
                isSorted = isSortedChecker();
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        counter = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (head != null && index >= 0 && index < counter) {
            int idx = 0;
            Node<T> temp = head;

            if (idx == index) {
                return temp.getData();
            } else {
                while (idx != index) {
                    idx++;
                    temp = temp.getNext();
                }
                return temp.getData();
            }
        }
        return null;
    }

    @Override
    public int indexOf(T element) {
        if (element != null && !isEmpty()) {
            Node<T> temp = head;
            int idx = 0;
            while (temp != null) {
                if (temp.getData().compareTo(element) == 0) {
                    return idx;
                }
                idx++;
                temp = temp.getNext();
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return counter == 0 || head == null;      // returns true if the list is empty, that is, its size/length is 0
    }

    @Override
    public int size() {
        return counter;
    }

    // helper method to use in sort() method
    // uses mergesort() method to sort the list
    public Node<T> sortHelper(Node<T> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node<T> fast = head;
        Node<T> slow = head;

        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            fast = fast.getNext().getNext();
            slow = slow.getNext();
        }
        fast = slow.getNext();
        slow.setNext(null);
        slow = sortHelper(head);
        fast = sortHelper(fast);
        return mergeSort(slow, fast);
    }

    // Merge Sort helper method to use in sort() method
    public Node<T> mergeSort(Node<T> slow, Node<T> fast) {
        Node<T> temp = new Node<T>();
        Node<T> curr = temp;

        while (slow != null && fast != null) {
            int compareVal = slow.getData().compareTo(fast.getData());
            if (compareVal <= 0) {
                curr.setNext(slow);
                slow = slow.getNext();
            } else {
                curr.setNext(fast);
                fast = fast.getNext();
            }
            curr = curr.getNext();
        }
        curr.setNext((slow == null) ? fast : slow);
        return temp.getNext();
    }

    @Override
    public void sort() {
        if (isSorted) {
            return;
        }
        head = sortHelper(head);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= counter) {
            return null;
        }
        Node<T> temp = head;
        Node<T> ptr = null;
        Node<T> nodeToRemove;
        int idx = 0;
        while (idx++ < index) {
            ptr = temp;
            temp = temp.getNext();
        }
        if (index == 0) {
            nodeToRemove = head;
            head = head.getNext();
        } else {
            nodeToRemove = ptr.getNext();
            ptr.setNext(temp.getNext());
        }
        counter--;
        isSorted = isSortedChecker();
        return nodeToRemove.getData();
    }

    @Override
    public void equalTo(T element) {
        if (element != null) {
            Node<T> prev = null;
            Node<T> curr = head;
            while (curr != null) {
                if (curr.getData().compareTo(element) != 0) {
                    if (curr == head) {
                        head = curr.getNext();
                        curr = head;
                    } else {
                        prev.setNext(curr.getNext());
                        curr = curr.getNext();

                    }
                    counter--;
                } else {
                    prev = curr;
                    curr = curr.getNext();
                }
            }
        }
    }

    @Override
    public void reverse() {
        if (head == null) {
            return;
        }
        Node<T> prev = null;
        Node<T> curr = head;
        while (curr != null) {
            Node<T> next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head = prev;
        isSorted = isSortedChecker();
    }

    @Override
    public void merge(List<T> otherList) { // this needed many comments to make it less confusing D:
        if (otherList.isEmpty()) {
            return;
        }

        LinkedList<T> other = (LinkedList<T>) otherList;
        this.sort();
        other.sort();
        isSorted = true;
        int currentSize = 0;
        int finalSize = other.size() + this.size();

        Node<T> finalListHead;
        Node<T> finalListTracker;
        Node<T> mainTracker = head;
        Node<T> otherTracker = other.head;
        int start = mainTracker.getData().compareTo(otherTracker.getData()); // -1 if main tracker is smaller, 0 if same, 1 if other tracker is smaller

        if (start < 0) { // if the main tracker has the smaller first node
            finalListHead = mainTracker;
            finalListTracker = mainTracker;
            currentSize++; //increments currSize by one because now there's one node in the list
            mainTracker = mainTracker.getNext();
        } else { // if the other tracker has the smaller first node, or they have the same one
            finalListHead = otherTracker;
            finalListTracker = otherTracker;
            currentSize++; //increments currSize by one because now there's one node in the list
            otherTracker = otherTracker.getNext();
        }

        while (currentSize < finalSize) {
            if (mainTracker.getNext() != null && otherTracker.getNext() != null) { // function breaks out of this block if the lists are unequal
                int comparison = mainTracker.getData().compareTo(otherTracker.getData());
                if(comparison < 0){
                    finalListTracker.setNext(mainTracker);
                    currentSize++;
                    finalListTracker = finalListTracker.getNext(); // increments the final list tracker forward then moves the main tracker so that it can compare the next values
                    mainTracker = mainTracker.getNext();
                } else{
                    finalListTracker.setNext(otherTracker);
                    currentSize++;
                    finalListTracker = finalListTracker.getNext(); // increments the final list tracker forward then moves the other tracker so that it can compare the next values
                    otherTracker = otherTracker.getNext();
                }
            } else if (mainTracker.getNext() == null && otherTracker.getNext() == null) { // Case 1: Main list and the other list are the same size
                int comparison = mainTracker.getData().compareTo(otherTracker.getData());
                if (comparison < 0){
                    finalListTracker.setNext(mainTracker);
                    finalListTracker = finalListTracker.getNext();
                    finalListTracker.setNext(otherTracker);
                } else {
                    finalListTracker.setNext(otherTracker);
                    finalListTracker = finalListTracker.getNext();
                    finalListTracker.setNext(mainTracker);
                }
            } else if (otherTracker.getNext() == null) { // Case 2: Other list is shorter
                finalListTracker.setNext(otherTracker);
                finalListTracker = finalListTracker.getNext();
                finalListTracker.setNext(mainTracker);
                currentSize = finalSize;
            } else if (mainTracker.getNext() == null) { // Case 3: Main list is shorter
                finalListTracker.setNext(mainTracker);
                finalListTracker = finalListTracker.getNext();
                finalListTracker.setNext(otherTracker);
                currentSize = finalSize;
            }
        }

        counter = currentSize;
        this.head = finalListHead; //sets final list head as the normal head to end it
    }

    @Override
    public void pairSwap() {
        Node<T> temp = head;
        while (temp != null && temp.getNext() != null) {
            T element = temp.getData();
            temp.setData(temp.getNext().getData());
            temp.getNext().setData(element);
            temp = temp.getNext().getNext();
        }
    }

    public String toString() {
        Node<T> temp = head;
        StringBuilder sb = new StringBuilder();
        while (temp != null) {
            sb.append(temp.getData()).append('\n');
            temp = temp.getNext();
        }
        return sb.toString();
    }

    @Override
    public boolean isSorted() {
        return isSortedChecker();
    }
}
