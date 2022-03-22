package col106.assignment4.WeakAVLMap;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class WeakAVLMap<K extends Comparable,V> implements WeakAVLMapInterface<K,V>{

	nodee<K, V> root = null;
	int rotateCount = 0;

	public WeakAVLMap(){
		// write your code here
		root = null;
	}

	public boolean nullnodee(nodee<K, V> n) {
		return n.key == null;
	}

	public void createnullnodee(nodee<K, V> n) {
		n.left = new nodee(null, null, n);
		n.right = new nodee(null, null, n);
	}

	public void promote(nodee<K, V> n) {
		n.rank++;
	}

	public void demote(nodee<K, V> n) {
		n.rank--;
	}

	public int rankDiff(nodee<K, V> n) {
		return n.parent.rank - n.rank;
	}

	public nodee<K, V> sibling(nodee<K, V> n) {
		if (n.parent.left == n) {
			return n.parent.right;
		} else
			return n.parent.left;
	}

	public nodee<K, V> rotateleft(nodee<K, V> n) {
		nodee<K, V> rc = n.right;
		nodee<K, V> cc = rc.left;
		n.right = cc;
		cc.parent = n;
		rc.left = n;
		n.parent = rc;
		rotateCount++;
		return rc;
	}

	public nodee<K, V> rotateright(nodee<K, V> n) {
		nodee<K, V> lc = n.left;
		nodee<K, V> cc = lc.right;
		n.left = cc;
		cc.parent = n;
		lc.right = n;
		n.parent = lc;
		rotateCount++;
		return lc;
	}
	
	public V put(K key, V value){
		// write your code here
		if (root == null || nullnodee(root)) {
			root = new nodee<K, V>(key, value, null);
			createnullnodee(root);
			root.rank = 1;
			return null;
		} else {
			nodee<K, V> t = root;
			nodee<K, V> parent = t;
			int c = 0;
			while (!nullnodee(t)) {
				parent = t;
				c = t.key.compareTo(key);
				if (c < 0) {
					t = t.right;
				} else if ((c > 0)) {
					t = t.left;
				} else {
					V prev = t.value;
					t.value = value;
					return prev;
				}
			}
			nodee<K, V> n = new nodee(key, value, parent);
			if (c < 0) {
				parent.right = n;
			} else {
				parent.left = n;
			}
			createnullnodee(n);
			createnullnodee(n.left);
			createnullnodee(n.right);
			promote(n);
			fixwavl(n);

			// print2D(root);
			return null;
		}
	}

	public void fixwavl(nodee<K, V> n) {
		if (n == root || rankDiff(n) == 1) {
			return;
		} else if (rankDiff(sibling(n)) == 1) {
			promote(n.parent);
			fixwavl(n.parent);
		} else if (rankDiff(sibling(n)) == 2) {
			nodee<K, V> p = n.parent; // parent
			nodee<K, V> gp = p.parent; // grandparent
			nodee<K, V> t = null; // child

			boolean pl = false; // check if n = n.parent.left
			boolean nl = false; // check if t is left child of n

			if (p.left == n) {
				pl = true;
			}
			if (rankDiff(n.left) == 1) {
				t = n.left;
				nl = true;
			} else {
				t = n.right;
			}

			boolean gppl = false; // check if parent of n = (grandparent of n).left
			if (gp != null && gp.left == p) {
				gppl = true;
			}

			if (pl && nl) {
				if (gp != null) {
					if (gppl) {
						gp.left = rotateright(p);
					} else {
						gp.right = rotateright(p);
					}
					n.parent = gp;
				} else {
					root = rotateright(p);
					root.parent = null;
				}
				demote(p);

			} else if (!pl && nl) {
				if (gp != null) {
					p.right = rotateright(n);
					t.parent = p;
					if (gppl) {
						gp.left = rotateleft(p);
					} else {
						gp.right = rotateleft(p);
					}
					t.parent = gp;

				} else {
					p.right = rotateright(n);
					t.parent = p;
					root = rotateleft(p);
					root.parent = null;
				}
				promote(t);
				demote(p);
				demote(n);

			} else if (!pl && !nl) {
				if (gp != null) {
					if (gppl) {
						gp.left = rotateleft(p);
					} else {
						gp.right = rotateleft(p);
					}
					n.parent = gp;
				} else {
					root = rotateleft(p);
					root.parent = null;
				}
				demote(p);

			} else {
				if (gp != null) {
					p.left = rotateleft(n);
					t.parent = p;
					if (gppl) {
						gp.left = rotateright(p);
					} else {
						gp.right = rotateright(p);
					}
					t.parent = gp;

				} else {
					p.left = rotateleft(n);
					t.parent = p;
					root = rotateright(p);
					root.parent = null;
				}
				promote(t);
				demote(p);
				demote(n);
			}

		}
	}

	public V remove(K key){
		// write your code here
		nodee<K, V> s = search(key);
		if (s.key == null) {
			return null;
		}
		V val = s.value;

		if (!nullnodee(s.left) && !nullnodee(s.right)) { // two child
			nodee<K, V> t = s.right;
			nodee<K, V> k = t.left;
			while (!nullnodee(k)) {
				k = k.left;
			}
			s.key = k.parent.key;
			s.value = k.parent.value;
			delWithOneChild(k.parent);
		} else {
			delWithOneChild(s);
		}
		// print2D(root);
		return val;
	}

	public void delWithOneChild(nodee<K, V> s) {
		nodee<K, V> p = s.parent;
		boolean pl = false; // Check if parent.left = s
		if (p != null && p.left == s) {
			pl = true;
		}
		if (!nullnodee(s.left)) { // only left child
			if (p != null) {
				if (pl) {
					p.left = s.left;
					s.left.parent = p;
				} else {
					p.right = s.left;
					s.left.parent = p;
				}
				fixdel(s.left);
			} else {
				root = s.left;
				root.parent = null;
			}
		} else {
			if (p != null) {
				if (pl) {
					p.left = s.right;
					s.right.parent = p;
				} else {
					p.right = s.right;
					s.right.parent = p;
				}
				fixdel(s.right);
			} else {
				root = s.right;
				root.parent = null;
			}

		}
	}

	public void fixdel(nodee<K, V> n) {
		if (n == root) {
			return;
		}

		if(rankDiff(sibling(n)) == 2 && nullnodee(sibling(n))){
			demote(n.parent);
			fixdel(n.parent);
			return;
		}

		if(rankDiff(n) < 3){
			return;
		}

		nodee<K, V> s = sibling(n); // sibling of n

		if (rankDiff(s) == 2) {
			demote(n.parent);
			fixdel(n.parent);

		} else if (rankDiff(s) == 1) {
			if (rankDiff(s.left) == 2 && rankDiff(s.right) == 2) { // both child of s have rankDiff = 2
				demote(n.parent);
				demote(s);
				fixdel(n.parent);
			} else { // atleast one child of s has rankDiff = 1
				nodee<K, V> t = null; // child of sibling
				nodee<K, V> p = n.parent; // parent
				nodee<K, V> gp = p.parent; // grandparent
				boolean gpl = false;
				if (gp != null && gp.left == p) {
					gpl = true;
				}

				if (rankDiff(s.left) == 1 && rankDiff(s.right) == 1) {
					if (p.left == s) {
						if (gp != null) {
							if (gpl) {
								gp.left = rotateright(p);
							} else {
								gp.right = rotateright(p);
							}
							s.parent = gp;
						} else {
							root = rotateright(p);
							root.parent = null;
						}

					} else {
						if (gp != null) {
							if (gpl) {
								gp.left = rotateleft(p);
							} else {
								gp.right = rotateleft(p);
							}
							s.parent = gp;
						} else {
							root = rotateleft(p);
							root.parent = null;
						}

					}
					promote(s);
					demote(p);
				} else {
					if (rankDiff(s.left) == 1) {
						t = s.left;
						if (p.left == s) {
							if (gp != null) {
								if (gpl) {
									gp.left = rotateright(p);
								} else {
									gp.right = rotateright(p);
								}
								s.parent = gp;
							} else {
								root = rotateright(p);
								root.parent = null;
							}
							promote(s);
							demote(p);
						} else {
							if (gp != null) {
								if (gpl) {
									p.right = rotateright(s);
									t.parent = p;
									gp.left = rotateleft(p);
								} else {
									p.right = rotateright(s);
									t.parent = p;
									gp.right = rotateleft(p);
								}
								t.parent = gp;
							} else {
								p.right = rotateright(s);
								t.parent = p;
								root = rotateleft(p);
								root.parent = null;
							}
							promote(t);
							promote(t);
							demote(s);
							demote(p);
							demote(p);

						}
					} else {
						t = s.right;
						if (p.right == s) {
							if (gp != null) {
								if (gpl) {
									gp.left = rotateleft(p);
								} else {
									gp.right = rotateleft(p);
								}
								s.parent = gp;
							} else {
								root = rotateleft(p);
								root.parent = null;
							}
							promote(s);
							demote(p);
						} else {
							if (gp != null) {
								if (gpl) {
									p.left = rotateleft(s);
									t.parent = p;
									gp.left = rotateright(p);
								} else {
									p.left = rotateleft(s);
									t.parent = p;
									gp.right = rotateright(p);
								}
								t.parent = gp;
							} else {
								p.left = rotateleft(s);
								t.parent = p;
								root = rotateright(p);
								root.parent = null;
							}
							promote(t);
							promote(t);
							demote(s);
							demote(p);
							demote(p);
						}
					}
				}
			}
		}

	}

	public V get(K key){
		// write your code here
		if (root == null) {
			return null;
		}
		return search(key).value;
	}

	public nodee<K, V> search(K key) {
		if (root == null) {
			return null;
		}
		nodee<K, V> t = root;
		while (!nullnodee(t)) {
			int c = t.key.compareTo(key);
			if (c < 0) {
				t = t.right;
			} else if (c > 0) {
				t = t.left;
			} else {
				return t;
			}
		}
		return t;
	}

	public Vector<V> searchRange(K key1, K key2){
		// write your code here
		Vector<V> v = new Vector<>();
		inOr(key1, key2, root, v);
		if (v.isEmpty()) {
			return null;
		}
		return v;
	}

	public void inOr(K key1, K key2, nodee<K, V> n, Vector<V> v) {
		if (nullnodee(n)) {
			return;
		}

		if (n.key.compareTo(key1) > 0) {
			inOr(key1, key2, n.left, v);
		}
		if (n.key.compareTo(key1) >= 0 && n.key.compareTo(key2) <= 0) {
			v.add(n.value);
		}
		if (n.key.compareTo(key2) < 0) {
			inOr(key1, key2, n.right, v);
		}

	}

	public int rotateCount(){
		// write your code here
		return rotateCount;
	}

	public int getHeight(){
		// write your code her 
		if (root == null) {
			return 0;
		}
		return height(root);
	}

	public int height(nodee<K, V> n) {
		if (nullnodee(n)) {
			return 0;
		}
		return 1 + Math.max(height(n.left), height(n.right));
	}

	public Vector<K> BFS(){
		// write your code her 
		Vector<K> v = new Vector<>();
		Queue<nodee<K, V>> q = new LinkedList<>();
		if (root != null && !nullnodee(root)) {
			q.add(root);
			nodee<K, V> t = null;
			while (!q.isEmpty()) {
				t = q.poll();
				v.add(t.key);
				if (!nullnodee(t.left)) {
					q.add(t.left);
				}
				if (!nullnodee(t.right)) {
					q.add(t.right);
				}

			}

			return v;
		}
		return v;
	}

}

class nodee<K, V> {
	K key;
	V value;
	int rank = 0;
	nodee<K, V> left = null;
	nodee<K, V> right = null;
	nodee<K, V> parent;

	public nodee(K key, V value, nodee<K, V> parent) {
		this.key = key;
		this.value = value;
		this.parent = parent;
	}

}