package col106.assignment3.BST;


import java.util.Queue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}
	/*
	 * end code
	 * start writing your code from here
	 */
	
	//write your code here 
    public node<T, E> root = null;
	private HashMap<T, E> map = new HashMap<>();

	public void insert(T key, E value) {
		node<T, E> n = new node<T, E>(key, value);
		if (root == null) {
			root = n;
		} else {
			node<T, E> k = root;
			node<T, E> t = root;
			while (t != null) {
				k = t;
				if (value.compareTo(t.value) >= 0) {
					t = t.right;
				} else {
					t = t.left;
				}

			}
			if (value.compareTo(k.value) >= 0) {
				k.right = n;
			} else {
				k.left = n;
			}

		}

		map.put(key, value);

		// write your code here
	}

	public void update(T key, E value) {
		delete(key);
		insert(key, value);
		// write your code here
	}

	public void delete(T key) {
		E value = map.get(key);
		if (value != null) {
			boolean done = false;

			node<T, E> n = root;
			node<T, E> k = root;
			while (!done) {

				if (value.compareTo(n.value) == 0) {
					done = true;
					map.remove(key);
					if (n.left == null && n.right == null) { // no child
						if (n == root) {
							root = null;
						} else if (k.left == n)
							k.left = null;
						else
							k.right = null;
					} else if (n.left == null || n.right == null) { // one child
						if (n.left == null) {
							if (n == root) {
								root = root.right;
							} else if (k.left == n) {
								k.left = n.right;
							} else
								k.right = n.right;
						} else {
							if (n == root) {
								root = root.left;
							}
							if (k.left == n) {
								k.left = n.left;
							} else
								k.right = n.left;
						}
					} else { // two child
						node<T, E> temp = n.right;
						node<T, E> temp1 = n.right;

						while (temp.left != null) {
							temp1 = temp;
							temp = temp.left;
						}
						if (n == root) {
							root = temp;
						} else if (k.left == n) {
							k.left = temp;
						} else {
							k.right = temp;
						}
						temp.left = n.left; // currentNode.left becomes nextInorder.left
						if (temp != temp1) {
							if (temp.right != null) {
								temp1.left = temp.right;
							} else {
								temp1.left = null;
							}
							temp.right = n.right;

						}

					}

				} else if (value.compareTo(n.value) > 0) {

					k = n;
					n = n.right;
				} else {

					k = n;
					n = n.left;
				}
			}
		}

		// write your code here
	}

	public void printBST() {
		Queue<node<T, E>> q = new LinkedList<node<T, E>>();
		q.add(root);
		String s = "";
		node<T, E> n = null;
		while (!q.isEmpty()) {
			n = q.remove();
			if (n.left != null) {
				q.add(n.left);
			}
			if (n.right != null) {
				q.add(n.right);
			}
			System.out.println(n.key.toString() + ", " + n.value.toString());
		}

		// write your code here
	}

	public ArrayList<T> getLevelOrder() {
		ArrayList<T> levelOrder = new ArrayList<>();
		Queue<node<T, E>> q = new LinkedList<node<T, E>>();
		q.add(root);
		String s = "";
		node<T, E> n = null;
		while (!q.isEmpty()) {
			n = q.remove();
			if (n.left != null) {
				q.add(n.left);
			}
			if (n.right != null) {
				q.add(n.right);
			}
			levelOrder.add(n.key);
		}
		return levelOrder;
	}

	

	private int klar = 0;
	private ArrayList<T> klarge = new ArrayList<>();
	private int temp = 0;

	public ArrayList<T> klarge(int k) {
		klar = k;
		temp = 0;
		getk(root);
		return klarge;
	}

	private void getk(node<T, E> n) {
		if (temp < klar) {
			if (n == null) {
				return;
			} else {
				getk(n.right);

				if (temp < klar) {
					klarge.add(n.key);
					temp++;
				}

				if (temp < klar)
					getk(n.left);
			}
		} else
			return;

	}

	private E max;
	private ArrayList<T> l = new ArrayList<>();

	public ArrayList<T> getLargest() {
		node<T, E> n = root;
		while (n.right != null) {
			n = n.right;
		}
		max = n.value;
		rec(root);
		ArrayList<T> done = (ArrayList<T>) l.clone();
		l.clear();
		return done;
	}

	private void rec(node<T, E> n) {
		if (n.right != null) {
			rec(n.right);
		}
		if (max.compareTo(n.value) == 0) {
			l.add(n.key);
		} else
			return;
	}

	public boolean hasKey(T key) {
		return map.containsKey(key);
	}

	public E getValue(T key) {
		return map.get(key);
	}

}

class node<T, E extends Comparable> {

	T key;
	E value;
	node<T, E> left;
	node<T, E> right;

	public node(T key, E value) {
		this.key = key;
		this.value = value;
		left = null;
		right = null;
	}

}