/******************************************************************************
 *  Compilation:  javac WeightedQuickUnionPathCompressionUF.java
 *  Execution:  java WeightedQuickUnionPathCompressionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Weighted quick-union with path compression.
 *
 ******************************************************************************/

/**
 *  The {@code WeightedQuickUnionPathCompressionUF} class represents a
 *  union–find data structure.
 *  It supports the <em>union</em> and <em>find</em> operations, along with
 *  methods for determining whether two sites are in the same component
 *  and the total number of components.
 *  <p>
 *  This implementation uses weighted quick union (by size) with full path compression.
 *  The constructor takes &Theta;(<em>n</em>) time, where
 *  <em>n</em> is the number of elements.
 *  The <em>union</em> and <em>find</em> operations take
 *  &Theta;(log <em>n</em>) time in the worst case.
 *  The <em>count</em> operation takes &Theta;(1) time.
 *  Moreover, starting from an empty data structure with <em>n</em> sites,
 *  any intermixed sequence of <em>m</em> <em>union</em> and <em>find</em>
 *  operations takes <em>O</em>(<em>m</em> &alpha;(<em>n</em>)) time,
 *  where &alpha;(<em>n</em>) is the inverse of
 *  <a href = "https://en.wikipedia.org/wiki/Ackermann_function#Inverse">Ackermann's function</a>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *     
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class WeightedQuickUnionPathCompressionUF {
    private int[] parent;  // parent[i] = parent of i
    private int[] size;    // size[i] = number of sites in tree rooted at i
                           // Note: not necessarily correct if i is not a root node
    private int count;     // number of components

    /**
     * Initializes an empty union-find data structure with
     * {@code n} elements {@code 0} through {@code n-1}. 
     * Initially, each elements is in its own set.
     *
     * @param  n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WeightedQuickUnionPathCompressionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }
  

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param  p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root])
            root = parent[root];
        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }

    /**
     * Returns true if the two elements are in the same set.
     * 
     * @param  p one element
     * @param  q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     * @deprecated Replace with two calls to {@link #find(int)}.
     */
    @Deprecated
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }  

    /**
     * Merges the set containing element {@code p} with the 
     * the set containing element {@code q}.
     *
     * @param  p one element
     * @param  q the other element
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    /**
     * Reads an integer {@code n} and a sequence of pairs of integers
     * (between {@code 0} and {@code n-1}) from standard input, where each integer
     * in the pair represents some element;
     * if the elements are in different sets, merge the two sets
     * and print the pair to standard output.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionPathCompressionUF uf = new WeightedQuickUnionPathCompressionUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

}

