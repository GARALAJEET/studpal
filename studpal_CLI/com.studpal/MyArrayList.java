
import java.util.Arrays;

public class MyArrayList<T> {
    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[10];
        size = 0;
    }
    public void addE(T element) {
                if (size == elements.length) {
                    ensureCapacity();
                }
                elements[size++] = element;
            }
    public void add(college_Timetable timetable) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = timetable;
    }
    public void addT(Timetable timetable) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = timetable;
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) elements[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    public void clear() {
        size = 0;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; 
    }
}
