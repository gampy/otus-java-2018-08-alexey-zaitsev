
package ru.otus.l03;

import org.apache.commons.lang3.time.StopWatch;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        StopWatch timer = new StopWatch();

        System.out.println("\nDefault constructor invocation, ns");

        timer.start();
        MyArrayList<Integer> am1 = new MyArrayList<>();
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am1.toString() + "\tsize: " + am1.size());
        timer.reset();

        timer.start();
        ArrayList<Integer> a1 = new ArrayList<>();
        timer.stop();
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a1.toString() + "\tsize: " + a1.size());
        timer.reset();

        System.out.println("\nInit size = 100 constructor invocation, ns");

        timer.start();
        MyArrayList<Integer> am2 = new MyArrayList<>(100);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am2.toString() + "\tsize: " + am2.size());
        timer.reset();

        timer.start();
        ArrayList<Integer> a2 = new ArrayList<>(100);
        timer.stop();
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a2.toString() + "\tsize: " + a2.size());
        timer.reset();

        System.out.println("\nInit with a Collection constructor invocation, ns");

        Collection<Integer> c1 = Arrays.asList(new Integer[]{1,2,3,4,5});
        timer.start();
        MyArrayList<Integer> am3 = new MyArrayList<>(c1);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        ArrayList<Integer> a3 = new ArrayList<>(c1);
        timer.stop();
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nAppend one element, ns");

        timer.start();
        am3.add(6);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.add(6);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();


        System.out.println("\nInsert one element in the certain position, ns");

        timer.start();
        am3.add(3,7);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.add(3,7);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nAppend a number of elements, ns");

        Collection<Integer> c2 = Arrays.asList(new Integer[]{8,9,10,11});
        timer.start();
        am3.addAll(4,c2);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.addAll(4,c2);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nSort the elements, ns");

        timer.start();
        am3.sort(Integer::compare);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.sort(Integer::compare);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nCheck if the List contains a collection, ns");

        boolean ret;
        timer.start();
        ret = am3.containsAll(c2);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + ret + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        ret = a3.containsAll(c2);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + ret + "\tsize: " + a3.size());
        timer.reset();


        System.out.println("\nGet an element, ns");

        int i = 9;
        timer.start();
        am3.get(i);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.get(i) + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.get(i);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.get(i) + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nRemove one element at the specified position, ns");

        timer.start();
        am3.remove(0);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.remove(0);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();


        System.out.println("\nRemove the first occurrence of the specified element, ns");

        timer.start();
        am3.remove(Integer.valueOf(2));
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.remove(Integer.valueOf(2));
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nSet an element, ns");

        timer.start();
        am3.set(0, 100);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am3.toString() + "\tsize: " + am3.size());
        timer.reset();

        timer.start();
        a3.set(0, 100);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a3.toString() + "\tsize: " + a3.size());
        timer.reset();

        System.out.println("\nCollections.addAll check, ns");

        MyArrayList<String> am4 = new MyArrayList<>();
        ArrayList<String> a4 = new ArrayList<>();
        timer.start();
        Collections.addAll(am4, "One", "Two", "Three", "Four");
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am4.toString() + "\tsize: " + am4.size());
        timer.reset();

        timer.start();
        Collections.addAll(a4, "One", "Two", "Three", "Four");
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a4.toString() + "\tsize: " + a4.size());
        timer.reset();

        System.out.println("\nCollections.copy check, ns");

        MyArrayList<String> am5 = new MyArrayList<>(Arrays.asList(new String[]{null,null,null,null}));
        ArrayList<String> a5 = new ArrayList<>(Arrays.asList(new String[]{null,null,null,null}));
        timer.start();
        Collections.copy(am5, am4);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am5.toString() + "\tsize: " + am4.size());
        timer.reset();

        timer.start();
        Collections.copy(a5, a4);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a5.toString() + "\tsize: " + a4.size());
        timer.reset();

        System.out.println("\nCollections.sort check, ns");

        timer.start();
        Collections.sort(am5);
        timer.stop();
        System.out.println("\tMyArrayList:\t" + timer.getNanoTime() + "\t" + am5.toString() + "\tsize: " + am4.size());
        timer.reset();

        timer.start();
        Collections.sort(a5);
        System.out.println("\tArrayList:\t\t" + timer.getNanoTime()+ "\t" + a5.toString() + "\tsize: " + a4.size());
        timer.reset();

        System.out.println("\nRealized ListIterator");

        MyArrayList<Integer> list1 = new MyArrayList<>();
        list1.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));

        MyArrayList<Integer> list2 = new MyArrayList<>();
        list2.addAll(Arrays.asList(null, null, null, null, null, null, null, null, null, null, null));
        Collections.copy(list2, list1);
        System.out.println("\tMyArrayList:\t\t" + "\t" + list2.toString() + "\tsize: " + list2.size());

        System.out.println("\nListIterator's methods test");

        ListIterator<Integer> it = list1.listIterator();
        while (it.hasNext()) System.out.print (it.next() + " ");
        System.out.println();
        while (it.hasPrevious()) System.out.print (it.previous() + " ");

    }
}
