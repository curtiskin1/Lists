// Created by Curtis Kokuloku, kokul003
// on 11/01/2022

import java.util.Arrays;

public class ArrayList<T extends Comparable<T>> implements List<T> {

    private boolean isSorted;
    private int counter;

    private T[] arrayList;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        isSorted = true;
        counter = 0;
        arrayList = (T[]) new Comparable[2];
    }

    // helper method to check and change the size of the arrayList
    @SuppressWarnings("unchecked")
    private void checkCapacity() {
        if (counter == arrayList.length) {
            int newSize = arrayList.length;
            newSize = (int) (newSize * 2 + 1);
            T[] newArrayList = (T[]) new Comparable[newSize];
            System.arraycopy(arrayList, 0, newArrayList, 0, counter);
            arrayList = newArrayList;
        }
    }

    // helper method to check if the list is sorted by ascending order or descending order
    private boolean isSortedChecker() {
        boolean sorted = true;
        for (int i = 0; i < counter - 1; i++) {
            if (get(i).compareTo(get(i + 1)) > 0) {
                sorted = false;
            }
        }
        return sorted;
    }
    @Override
    public boolean add(T element) {
        if (element != null) {
            if (counter == arrayList.length) {
                checkCapacity();
            }
            arrayList[counter++] = element;
            if (counter == 1) {
                isSorted = true;
            } else {
                isSorted = isSortedChecker();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean add(int index, T element) {
        if (element != null && (index >= 0 && index < size())) {
            checkCapacity();
            for (int i = counter - 1; i >= index; i--) {
                arrayList[i + 1] = arrayList[i];
            }
            arrayList[index] = element;
            counter++;
            isSorted = isSortedChecker();
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(arrayList, null);   // for (int i = 0; i < arrayList.length; i++) {arrayList[i] = null;}
        counter = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size()) {
            return arrayList[index];
        }
        return null;
    }

    @Override
    public int indexOf(T element) {
        if (element != null && !isEmpty()) {
            for (int i = 0; i < counter; i++) {
                if (arrayList[i].compareTo(element) == 0) {
                    return i;
                } else if(isSorted && arrayList[i].compareTo(element) > 0) {
                    return -1;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public void sort() {
        if (isSorted) {     // if the list is already sorted, we will do nothing
            return;
        }
        // Using InsertionSort
        for (int i = 0; i < size() - 1; i++) {
            T minimum = get(i);
            int minimumIdx = i;
            for (int j = i + 1; j < size(); j++) {
                if (minimum.compareTo(get(j)) > 0) {
                    minimum = get(j);
                    minimumIdx = j;
                }
            }
            if (minimumIdx != i) {
                arrayList[minimumIdx] = get(i);
                arrayList[i] = minimum;
            }
        }
        isSorted = true;
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < counter) {
            T element = get(index);
            for (int i = index + 1; i < counter; i++) {
                arrayList[i - 1] = get(i);
            }
            counter--;
            isSorted = isSortedChecker();
            return element;
        }
        return null;
    }

    @Override
    public void equalTo(T element) {
        if (element != null) {
            int i = 0;
            if (isSorted) {
                // USing a more efficient method
                while (i < counter) {
                    if (arrayList[i].compareTo(element) < 0) {
                        remove(i);
                    } else if(arrayList[i].compareTo(element) == 0) {
                        i++;
                    } else {
                        counter = i;
                    }
                }
            } else {
                while (i < counter) {
                    if (!(arrayList[i].compareTo(element) == 0)) {
                        remove(i);
                    } else {
                        i++;
                    }
                }
            }
            isSorted = true;
        }
    }

    @Override
    public void reverse() {
        ArrayList<T> reversedList = new ArrayList<T>();
        for (int i = counter -1; i >= 0; i--) {
            reversedList.add(arrayList[i]);
        }
        arrayList = reversedList.arrayList;
        counter = reversedList.size();
        isSorted = isSortedChecker();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void merge(List<T> otherList) {
        if (otherList == null) {  // Same as saying 'otherList == null'
            return;
        }

        ArrayList<T> other = (ArrayList<T>) otherList;
        this.sort();
        other.sort();
        isSorted = true;

        int newSize = this.counter + other.size();
        T[] newArray = (T[]) new Comparable[newSize];

        int i = 0, j = 0, k = 0;

        while (i < this.counter && j < other.size()) {
            T element1 = (T) arrayList[i];
            T element2 = (T) other.get(j);

            if (element1.compareTo(element2) < 0) {
                newArray[k] = element1;
                k++; i++;
            } else {
                newArray[k] = element2;
                k++; j++;
            }
        }
        while (i < this.counter) {
            newArray[k] = (T) arrayList[i];
            k++; i++;
        }
        while (j < other.size()) {
            newArray[k] = (T) other.get(j);
            k++; j++;
        }

        arrayList = newArray;
        isSorted = isSortedChecker();
        this.counter = newSize;
    }

    @Override
    public void pairSwap() {
        for (int i = 0; i < size(); i += 2) {
            T temp = arrayList[i];
            arrayList[i] = arrayList[i + 1];
            arrayList[i + 1] = temp;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            s.append(get(i)).append("\n");
        }
        return s.toString();
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }
}