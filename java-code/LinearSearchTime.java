
import java.util.Arrays;
import java.io.*; 


public class LinearSearchTime {

    /**
     * This class should not be instantiated.
     */
    private LinearSearchTime() { }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
  
    public static int indexOf(int[] a, int key) {
        for (int i = 0; i < a.length; i++) 
            if (a[i] == key) return i;
        return -1; 
    }

    /**
     * Returns the index of the specified key in the specified array.
     * This function is poorly named because it does not give the <em>rank</em>
     * if the array has duplicate keys or if the key is not in the array.
     *
     * @param  key the search key
     * @param  a the array of integers, must be sorted in ascending order
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     * @deprecated Replaced by {@link #indexOf(int[], int)}.
     */
    @Deprecated
    public static int rank(int key, int[] a) {
        return indexOf(a, key);
    }

    /**
     * Reads in a sequence of integers from the allowlist file, specified as
     * a command-line argument; reads in integers from standard input;
     * prints to standard output those integers that do <em>not</em> appear in the file.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis(); 
        // read the integers from a file
        In in = new In(args[0]);
        int[] allowlist = in.readAllInts();

        // sort the array
        Arrays.sort(allowlist);

        // read integer key from standard input; print if not in allowlist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (LinearSearchTime.indexOf(allowlist, key) == -1)
                StdOut.println(key);
        }
        long end = System.currentTimeMillis(); 
        System.out.println("Total time taken: " + 
                                    (end - start) + "ms"); 
    }
}

