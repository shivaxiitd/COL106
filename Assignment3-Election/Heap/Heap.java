package col106.assignment3.Heap;

import java.util.HashMap;
import java.util.ArrayList;


public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	/*
	 * end code
	 */
	
	// write your code here	
	ArrayList<E> arr = new ArrayList<>();
	int last = 0;
	HashMap<E, T> m1 = new HashMap<>();
	HashMap<T, E> m2 = new HashMap<>();

	public Heap() {
		arr.add(null);
	}

	public void insert(T key, E value) {
		// write your code here
		arr.add(value);
		last++;
		recinsert(last);
		m1.put(value, key);
		m2.put(key, value);

	}

	private void recinsert(int ind) {
		int parent = ind / 2;
		if (parent != 0) {
			if (arr.get(ind).compareTo(arr.get(parent)) > 0) {
				E t = arr.get(ind);
				arr.set(ind, arr.get(parent));
				arr.set(parent, t);
				recinsert(parent);
			}
		}
	}

	public E extractMax() {
		// write your code here
		E t = arr.get(1);

		arr.set(1, arr.get(last));
		arr.remove(last);

		m2.remove(m1.get(t));
		m1.remove(t);

		last--;
		recHeap(1);

		return t;
	}

	private void recHeap(int ind) {
		int c1 = 2 * ind;
		int c2 = 2 * ind + 1;
		if ((c1 <= last && arr.get(ind).compareTo(arr.get(c1)) < 0)
				|| (c2 <= last && arr.get(ind).compareTo(arr.get(c2)) < 0)) {
			if (arr.get(c2).compareTo(arr.get(c1)) > 0) {
				E swap = arr.get(c2);
				arr.set(c2, arr.get(ind));
				arr.set(ind, swap);
				recHeap(c2);
			} else {
				E swap = arr.get(c1);
				arr.set(c1, arr.get(ind));
				arr.set(ind, swap);
				recHeap(c1);
			}
		}

	}

	private int getIndex(E val) {
		int i = 0;
		for (i = 1; i < arr.size(); i++) {
			if (arr.get(i).compareTo(val) == 0)
				break;

		}
		return i;
	}

	public void delete(T key) {
		// write your code here
		E val = m2.get(key);
		int i = getIndex(val);
		m2.remove(key);
		m1.remove(val);
		arr.set(i, arr.get(last));
		arr.remove(last);
		last--;
		recHeap(i);

	}

	public void increaseKey(T key, E value) {
		// write your code here

		int i = getIndex(m2.get(key));
		arr.set(i, value);
		m1.put(value, key);
		m2.put(key, value);
		recinsert(i);
		recHeap(i);
	}

	public void printHeap() {
		// write your code here
		for (int i = 1; i < arr.size(); i++) {
			System.out.println(m1.get(arr.get(i)).toString() + ", " + arr.get(i).toString());
		}

	}
}
