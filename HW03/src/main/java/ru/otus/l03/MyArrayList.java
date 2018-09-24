package ru.otus.l03;

import java.util.*;
import java.lang.reflect.Array;
import java.util.function.UnaryOperator;

public class MyArrayList<E> implements List<E> {

    private final static int INIT_SIZE = 10;
    private E[] list;
    private int size = 0;

    /** Constructs an empty list with an initial capacity of ten. */
    public MyArrayList() {
        this(INIT_SIZE);
    }

    /** Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator. */
    public MyArrayList (Collection<? extends E> c) {
        this(c.size() < INIT_SIZE? INIT_SIZE:c.size());
        this.addAll(c);
    }

    /** Constructs an empty list with the specified initial capacity. */
    public MyArrayList(int initialCapacity) {
        super();
        list = (E[]) Array.newInstance(Object.class, initialCapacity);
    }


    /** Appends the specified element to the end of this list. */
   @Override
    public boolean add(E e) {
        grow();
        list[size] = e;
        alterSize(1);
        return true;
    }

    /** Inserts the specified element at the specified cursorition in this list (optional operation). */
    @Override
    public void add(int index, E e) {
        E[] clone = (E[]) Array.newInstance(Object.class, list.length + 1);
        System.arraycopy(list, 0, clone, 0, index);
        clone[index] = e;
        System.arraycopy(list, index, clone, index + 1, list.length - index);
        list = clone;
        alterSize(1);
    }

    /** Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's Iterator. */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;

        for (E e: c) {
            if (this.add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    /** Inserts all of the elements in the specified collection into this list, starting at the specified cursorition. */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        E[] clone = (E[]) Array.newInstance(Object.class, list.length + c.size());
        System.arraycopy(list, 0, clone, 0, index);
        System.arraycopy((E[]) c.toArray(), 0, clone, index , c.size());
        System.arraycopy(list, index, clone, index + c.size(), list.length - index);
        list = clone;
        alterSize(c.size());
        return list.equals(clone)? false: true;
    }

    /** Removes all of the elements from this list. */
    @Override
    public void clear() {
        list = (E[]) Array.newInstance(Object.class, INIT_SIZE);
    }

    /** Returns a shallow copy of this ArrayList instance. */
    public Object clone() {
        MyArrayList<E> clone = new MyArrayList<>(this.size());
        System.arraycopy(this, 0, clone, 0, this.size());
        return clone;
    }

   /** Returns true if the list contains this element */
    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) >= 0;
    }

    /** Returns true if the list contains all of the elements of the specified collection */
    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean ret0 = false;
        boolean ret = true;

        for (Object o: collection) {
            ret &= contains(o);
        }
        return ret0 | ret;
    }

    /**Performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception. */
    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    /** Returns the element at the specified cursorition in the list */
    @Override
    public E get(int index) {
        return list[index];
    }

    /** Returns the hash code value for this list. */
    @Override
    public int hashCode() {
        return list.hashCode();
    }

    /** Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element. */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (list[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /** Returns true if this list contains no elements. */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    /** Returns an iterator over the elements in this list in proper sequence. */
    @Override
    public Iterator<E>iterator() {
        throw new UnsupportedOperationException("Iterator");
    }

    /** Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element. */
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("lastIndexOf");
    }

    /** Returns a list iterator over the elements in this list (in proper sequence). */
    @Override
    public ListIterator<E> listIterator() {
        return new MyArrayList.MyListIterator(0);
    }

    /** Returns a list iterator over the elements in this list (in proper sequence), starting at the specified cursorition in the list. */
    public ListIterator<E> 	listIterator(int index) {
        throw new UnsupportedOperationException("listIterator");
    }


    /** Removes the element at the specified cursorition in this list. */
    @Override
    public E remove(int index) {
        E[] clone = (E[]) Array.newInstance(Object.class, list.length - 1);
        System.arraycopy(list, 0, clone, 0, index);
        System.arraycopy(list, index + 1, clone, index, list.length - index - 1);
        list = clone;
        alterSize(-1);
        return list[index];
    }

    /** Removes the first occurrence of the specified element from this list, if it is present. */
    @Override
    public boolean remove(Object o) {
        boolean modified = false;
        if (this.contains(o)) {
           this.remove(this.indexOf(o));
           modified = true;
        }
        if (modified) shrink();
        return modified;
    }

    /** Removes from this list all of its elements that are contained in the specified collection. */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    /** Replaces each element of this list with the result of applying the operator to that element. */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException("replaceAll");
    }

    /** Retains only the elements in this list that are contained in the specified collection. */
    @Override
    public boolean 	retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    /** Replaces the element at the specified cursorition in this list with the specified element. */
    @Override
    public E set(int index, E element) {
        E e = list[index];
        list[index] = element;
        return e;
    }

    /** Returns the number of elements in this list. */
    @Override
    public int	size() {
        return this.size;
    }

    /** Sorts this list according to the order induced by the specified Comparator. */
    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort(list, 0, this.size(), c);
    }

    /** Creates a late-binding and fail-fast Spliterator over the elements in this list. */
    public Spliterator<E> spliterator() {
        throw new UnsupportedOperationException("spliterator");
    }


    /** Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive. */
    @Override
    public List<E> 	subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }

    /** Returns an array containing all of the elements in this list in proper sequence (from first to last element). */
    @Override
    public Object[] toArray() {
        return list;
    }

    /** Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array. */
    public <T> T[] 	toArray(T[] a) {
        throw new UnsupportedOperationException("toArray(T[] a");
    }

    /** Trims the capacity of this ArrayList instance to be the list's current size. */
    public void trimToSize() {
        if (this.size() < list.length) {
            this.list = Arrays.copyOf(this.list, this.size() < INIT_SIZE? INIT_SIZE: this.size);
            setSize(list.length);
        }
    }

    @Override
    public String toString() {
        if (size() == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < this.size(); i++) {
                sb.append(list[i]);
                if (i < this.size() - 1) {
                    sb.append(',').append(' ');
                }
            }
            sb.append(']');
        return sb.toString();
        }
    }


    private void alterSize(int counter) {
        this.size += counter;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private void shrink() {
        if (list.length > size() * 2) {
            trimToSize();
        }
    }

    private void grow() {
        if (list.length == size()) {
            int oldCapacity = this.list.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            this.list = Arrays.copyOf(this.list, newCapacity);
        }
    }

    private class MyListIterator implements ListIterator<E> {
            int cursor;
            int index = -1;

        MyListIterator(int index) {
            this.cursor = index;
        }

        /** Returns true if this list iterator has more elements when traversing the list in the forward direction. */
        public boolean hasNext() {
            return cursor < MyArrayList.this.size()? true: false;
        }

        /**  Returns true if this list iterator has more elements when traversing the list in the reverse direction.*/
        public boolean hasPrevious() {
            return cursor > 0? true: false;
        }

        /** Returns the next element in the list and advances the cursor cursorition. */
        public E next() {
            if (cursor + 1 > MyArrayList.this.size()) {
                throw new NoSuchElementException();
            } else {
                this.index = cursor;
                cursor++;
                return MyArrayList.this.get(index);
            }
        }

        /** Returns the index of the element that would be returned by a subsequent call to next(). */
        public int nextIndex() {
            return this.cursor;
        }

        /** Returns the previous element in the list and moves the cursor cursorition backwards. */
        public E previous() {
            if (cursor <= 0) {
                throw new NoSuchElementException();
            } else {
                cursor--;
                this.index = cursor;
                return MyArrayList.this.get(index);
            }
        }

        /** Returns the index of the element that would be returned by a subsequent call to previous(). */
        public int previousIndex() {
            return this.cursor - 1;
        }

        public void add(E e) {
            throw new UnsupportedOperationException("add");
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        public void set(E e) {
            MyArrayList.this.set(this.index, e);
        }

    }





}

