package col106.assignment4.HashMap;
import java.util.Vector;
import java.util.ArrayList;

public class HashMap<V> implements HashMapInterface<V> {

	node<V>[] arr;
	int size;

	public HashMap(int size) {
		// write your code here
		arr = new node[size];
		this.size = size;
	}

	public int hash(String key) {

		byte[] b = key.getBytes();
		long a = 41;
		int sum = 0;
		int l = b.length;
		int i = l - 1;
		// System.out.println(Arrays.toString(b));
		while (i >= 0) {
			sum += (((ppow(a, i, size)) % size) * (b[i]) % size) % size;
			// System.out.println(sum);
			i--;
		}

		sum = sum % size;
		
		return sum;
	}

	public long ppow(long m, long n, long p) {
		if (n == 0) {
			return 1;
		} else if (n % 2 == 0) {
			return ppow((m % p) * (m % p), n / 2, p);
		} else {
			return (m % p) * (ppow((m % p) * (m % p), n / 2, p));
		}
	}

	public V put(String key, V value){
		// write your code here
		int h = hash(key) % size;
		while (arr[h] != null) {
			if (arr[h].key.equals(key)) {
				V prev = arr[h].val;
				arr[h].key = key;
				arr[h].val = value;
				return prev;
			}
			h++;
			h = h % size;
		}
		arr[h] = new node<V>(key, value);

		
		return null;
	}

	public V get(String key){
		// write your code here
		int h = hash(key) % size;
		int count = 0;
		while (arr[h] != null) {
			if (count == size) {
				break;
			}
			if (arr[h].key.equals(key)) {
				return arr[h].val;
			}
			h++;
			h = h % size;
			count++;
		}
		return null;
	}

	public boolean remove(String key){
		// write your code here
		int h = hash(key) % size;
		while (arr[h] != null) {
			if (arr[h].key.equals(key)) {
				int t = h;
				h++;
				h = h % size;
				while (arr[h] != null) {
					int nat = hash(arr[h].key);
					if (!between(nat, t, h)) {
						arr[t] = new node<V>(arr[h].key, arr[h].val);
						t++;
						t = h;
					}
					h++;
					h = h % size;
				}
				arr[t] = null;
				return true;
			}
			h++;
			h = h % size;
		}

		return false;
	}

	public boolean between(int nat, int t, int h) {
		if (t < h && t < nat && nat <= h) {
			return true;
		} else if (h < t && (t < nat || nat <= h)) {
			return true;
		}
		return false;
	}

	public boolean contains(String key){
		// write your code here
		int h = hash(key) % size;
		while (arr[h] != null) {
			if (arr[h].key.equals(key)) {
				return true;
			}
			h++;
			h = h % size;
		}
		return false;
	}

	public Vector<String> getKeysInOrder(){
		// write your code here
		Vector<String> v = new Vector<>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				v.add(arr[i].key);
			}
		}
		return v;
	}

	public void print() {
		ArrayList<String> s = new ArrayList<>();
		ArrayList<V> t = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null) {
				s.add(null);
				t.add(null);
			} else {
				s.add(arr[i].key);
				t.add(arr[i].val);
			}
		}
		System.out.println(s.toString());
		System.out.println(t.toString());
	}
}

class node<V> {
	String key;
	V val;

	public node(String key, V value) {
		this.key = key;
		this.val = value;
	}
}