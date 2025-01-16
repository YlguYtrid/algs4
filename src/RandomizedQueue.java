import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] items; // array of items
    private int size; // number of items on the queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[INIT_CAPACITY]; // cannot use @SuppressWarnings("unchecked")
        size = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        // add some items
        System.out.println("Is the queue empty? " + queue.isEmpty());
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println("Queue size: " + queue.size());
        // print the items
        System.out.print("Items: ");
        for (int item : queue) {
            System.out.println(item);
        }
        // remove and print a few items
        System.out.println("Removing and printing a few items:");
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.dequeue());
        }
        // print samples
        System.out.println("Sampling a few items:");
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.sample());
        }
        // Exception test
        try {
            queue.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        while (!queue.isEmpty()) {
            queue.dequeue();
        }
        try {
            queue.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            queue.sample();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            queue.iterator().remove();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        try {
            queue.iterator().next();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity]; // cannot use @SuppressWarnings("unchecked")
        System.arraycopy(items, 0, copy, 0, size);
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item cannot be null");
        if (size == items.length)
            resize(size * 2);
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniformInt(size);
        Item item = items[index];
        items[index] = items[--size];
        items[size] = null;
        if (size == items.length / 4 && items.length > INIT_CAPACITY)
            resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniformInt(size);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indices;
        private int current;

        public RandomizedQueueIterator() {
            indices = new int[size];
            current = 0;
            for (int i = 0; i < size; i++)
                indices[i] = i;
            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements");
            return items[indices[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation");
        }
    }

}
