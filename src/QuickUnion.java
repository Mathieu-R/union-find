/*
  Given a set of N objects (integers),
  we want to find the maximal subset of connected objects.
  Each group has a representant that we call the root.
  Two objects are connected together if they have the same root.
  To avoid having deep Trees, we assign the root of the smaller Tree
  to the root of the larger Tree, and we compress path
  as we find the root of any objects.
  This is the weighted Quick Union with path compression algorithm.
*/

public class QuickUnion {

  // each object is an integer which is the index of the following array.
  // we keep track of the parent of any object.
  // an object whose parent is itself is the root.
  // e.g. id[i] = parent of object "i".
  private int[] id;

  // keep track of the size of any Tree.
  // sz[i] = size of Tree where object "i" belongs to.
  private int[] sz;

  // number of connected components (i.e. groups)
  private int n;

  public QuickUnion(int n) {
    id = new int[n];
    sz = new int[n];
    this.n = n;

    for (int i = 0; i < n; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  /*
  Find the root of object p
   */
  public int find(int p) {
    int pid = id[p];

    // bubble up to the parent while the object is not pointing to itself
    while (pid != id[pid]) {
      pid = id[pid];

      // path compression as we're looking for the root
      // assign to grandparent instead of parent
      id[pid] = id[id[pid]];
    }

    return pid;
  }

  public void union(int p, int q) {
    // find roots of p and q
    int pRoot = find(p);
    int qRoot = find(q);

    // if same root, they already belong to the same component
    if (pRoot == qRoot) {
      return;
    }

    // assign root of smaller Tree to root of larger Tree
    // in order to avoid deep Trees
    if (sz[qRoot] > sz[pRoot]) {
      id[pRoot] = qRoot;
      sz[qRoot] += sz[pRoot];
    } else {
      id[qRoot] = pRoot;
      sz[pRoot] += sz[qRoot];
    }

    // decrease number of connected components
    // since we merged 2 groups (that can be 2 unique objects)
    n--;
  }

  public boolean connected(int p, int q) {
    // find roots of p and q
    int pRoot = find(p);
    int qRoot = find(q);

    // if same root, they belong to the same component
    return pRoot == qRoot;
  }

  public int numberOfConnectedComponents() {
    return n;
  }

  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}