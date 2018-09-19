package ru.otus.l03;

import java.util.*;
import java.lang.reflect.Array;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
//import java.util.function.UnaryOperator;

public class MyArrayList<E> extends AbstractList<E> implements List<E> {

    //private static final long serialVersionUID = 1L;
    private final static int INIT_SIZE = 10;
    private E[] list;
    private int size = 0;

    //Constructs an empty list with an initial capacity of ten.
    public MyArrayList() {
        this(INIT_SIZE);
    }

    //Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
    public MyArrayList (Collection<? extends E> c) {
        this(c.size() < INIT_SIZE? INIT_SIZE:c.size());
        this.addAll(c);
    }

    //Constructs an empty list with the specified initial capacity.
    public MyArrayList(int initialCapacity) {
        super();
        list = (E[]) Array.newInstance(Object.class, initialCapacity);
    }


    //Appends the specified element to the end of this list.
   @Override
    public boolean add(E e) {
        grow();
        list[size] = e;
        alterSize(1);
        return true;
    }

    //Inserts the specified element at the specified position in this list (optional operation).
    @Override
    public void add(int index, E e) {
        E[] clone = (E[]) Array.newInstance(Object.class, list.length + 1);
        System.arraycopy(list, 0, clone, 0, index);
        clone[index] = e;
        System.arraycopy(list, index, clone, index + 1, list.length - index);
        list = clone;
        alterSize(1);
    }

    //Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's Iterator.
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        Iterator<? extends E> it = c.iterator();

        while(it.hasNext()) {
            E e = it.next();
            if (this.add(e)) {
                modified = true;
            }
        }

        return modified;

    }

    //Inserts all of the elements in the specified collection into this list, starting at the specified position.
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

    //Removes all of the elements from this list.
    @Override
    public void clear() {
        super.clear();
    }

    //Returns a shallow copy of this ArrayList instance.
    public Object clone() {
        MyArrayList<E> clone = new MyArrayList<>(this.size());
        System.arraycopy(this, 0, clone, 0, this.size());
        return clone;
    }

    @Override
    public boolean contains(Object e) {
        return super.contains(e);
    }

    //Increases the capacity of this ArrayList instance, if necessary, to ensure that it can hold at least the number of elements specified by the minimum capacity argument.
    public void ensureCapacity(int minCapacity) {
        //ToDO
    }

    //Performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception.
    public void forEach(Consumer<? super E> action) {
        //ToDo
    }

    @Override
    public E get(int index) {
        return list[index];
    }

    //Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
    @Override
    public int indexOf(Object o) {
        return super.indexOf(o);
    }

    //Returns true if this list contains no elements.
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }


    //Returns an iterator over the elements in this list in proper sequence.
    @Override
    public Iterator<E>	iterator() {
       return super.listIterator();
    }

    //Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.
    @Override
    public int lastIndexOf(Object o) {
        return super.lastIndexOf(o);
    }

    //Returns a list iterator over the elements in this list (in proper sequence).
    @Override
    public ListIterator<E> listIterator() {
        return super.listIterator();
    }

    //Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
    //public ListIterator<E> 	listIterator(int index) {
    //    return super.listIterator();
    //}

    //Removes the element at the specified position in this list.
    @Override
    public E remove(int index) {
        E[] clone = (E[]) Array.newInstance(Object.class, list.length - 1);
        System.arraycopy(list, 0, clone, 0, index);
        System.arraycopy(list, index + 1, clone, index, list.length - index - 1);
        list = clone;
        alterSize(-1);
        return list[index];
    }

    //Removes the first occurrence of the specified element from this list, if it is present.
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

    //Removes from this list all of its elements that are contained in the specified collection.
    @Override
    public boolean 	removeAll(Collection<?> c) {
        //ToDo
        return false;
    }

    //Removes all of the elements of this collection that satisfy the given predicate.
    @Override
    public boolean 	removeIf(Predicate<? super E> filter) {
       return false;
        //ToDo
    }

    //Removes from this list all of the elements whose index is between fromIndex, inclusive, and toIndex, exclusive.
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        //ToDO
    }

    //Replaces each element of this list with the result of applying the operator to that element.
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        //ToDo
    }


    //Retains only the elements in this list that are contained in the specified collection.
    @Override
    public boolean 	retainAll(Collection<?> c) {
        return false;
        //ToDo
    }

    //Replaces the element at the specified position in this list with the specified element.
    @Override
    public E set(int index, E element) {
        E e = list[index];
        list[index] = element;
        return e;
    }

    //Returns the number of elements in this list.
    @Override
    public int	size() {
        return this.size;
    }

    //Sorts this list according to the order induced by the specified Comparator.
    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort(list, 0, this.size(), c);
    }

    //Creates a late-binding and fail-fast Spliterator over the elements in this list.
    /*
    public Spliterator<E> spliterator() {
        //ToDo
    }
    */

    //Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.
    @Override
    public List<E> 	subList(int fromIndex, int toIndex) {
        //ToDo
        List<E> l = null;
        return l;
    }

    //Returns an array containing all of the elements in this list in proper sequence (from first to last element).
    @Override
    public Object[] toArray() {
        return list;
    }

    //Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array.
    //public <T> T[] 	toArray(T[] a) {
        //ToDo
    //    return a;
    //}

    //Trims the capacity of this ArrayList instance to be the list's current size.
    public void trimToSize() {
        if (this.size() < list.length) {
            this.list = Arrays.copyOf(this.list, this.size() < INIT_SIZE? INIT_SIZE: this.size);
            setSize(list.length);
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

}
