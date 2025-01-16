import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] items;
    private int start;
    private int size;

    // construct an empty deque
    public Deque() {
        items = (Item[]) new Object[INIT_CAPACITY]; // cannot use @SuppressWarnings("unchecked")
        start = 0;
        size = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        // add some items to the front and back
        System.out.println("Is Deque empty? " + deque.isEmpty());
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
        }
        for (int i = 10; i < 20; i++) {
            deque.addLast(i);
        }
        System.out.println("Deque size: " + deque.size());
        // Iterator test
        System.out.println("Deque items: ");
        for (int num : deque) {
            System.out.println(num);
        }
        System.out.println("Deque items end.");
        // remove some items from the front and back
        for (int i = 0; i < 5; i++) {
            System.out.println("Remove first: " + deque.removeFirst());
            System.out.println("Remove last: " + deque.removeLast());
        }
        System.out.println("Deque size: " + deque.size());
        // Exception test
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            deque.addLast(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        while (!deque.isEmpty()) {
            deque.removeLast();
        }
        System.out.println("Is Deque empty? " + deque.isEmpty());
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            deque.iterator().remove();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        try {
            deque.iterator().next();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        // Test String Deque
        Deque<String> strDeque = new Deque<>();
        strDeque.addFirst("Hello");
        strDeque.addLast("World");
        System.out.println(strDeque.removeFirst());
        System.out.println(strDeque.removeLast());
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity]; // cannot use @SuppressWarnings("unchecked")
        int stop = start + size;
        if (stop <= items.length) {
            System.arraycopy(items, start, copy, 0, size);
        } else {
            int leftLength = items.length - start;
            System.arraycopy(items, start, copy, 0, leftLength);
            int rightLength = stop - items.length;
            System.arraycopy(items, 0, copy, leftLength, rightLength);
        }
        items = copy;
        start = 0;
    }

    private void resizeDoubleIfFull() {
        if (size == items.length)
            resize(2 * items.length);
    }

    private void resizeHalfIfQuarter() {
        if (size == items.length / 4 && items.length > INIT_CAPACITY) {
            resize(items.length / 2);
        }
    }

    private int realIndex(int index) {
        return (start + index + items.length) % items.length;
    }

    private int endIndex() {
        return (start + size - 1) % items.length;
    }

    private int stopIndex() {
        return (start + size) % items.length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item cannot be null");
        resizeDoubleIfFull();
        start = realIndex(-1);
        items[start] = item;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item cannot be null");
        resizeDoubleIfFull();
        items[stopIndex()] = item;
        size++;
    }

    private Item remove(int index) {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        Item item = items[index];
        items[index] = null;
        return item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item item = remove(start);
        start = realIndex(1);
        size--;
        resizeHalfIfQuarter();
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item item = remove(endIndex());
        size--;
        resizeHalfIfQuarter();
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements");
            Item item = items[realIndex(index)];
            index++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation");
        }
    }

}
