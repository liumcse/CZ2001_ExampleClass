package lab2;

// Linear Probing Implementation
public abstract class LinearProbingHashTable {
    private int comparisonCount;

    private final int size;
    private final Entry[] table;

    // constructor with size specified.
    LinearProbingHashTable(int size) {
        this.size = size;
        // initialize hashCode: an array of Entries
        this.table = new Entry[size];
    }

    // hashFunction is defined by user
    public abstract int hashFunction(int key);

    public void insert(int key, Entry entry) {
        int code = hashFunction(key);

        // If the hash cell has already been taken (collision)
        while (table[code] != null) {
            // linear probing, increase key by 1
            code++;
            if (code == size) code = 0;  // wrap around
        }

        // until we find an empty cell
        table[code] = entry;
    }

    public Entry search(int key) {
        int code = hashFunction(key);

        // start searching until find
        Entry entry;
        comparisonCount = 0;
        while(true) {
            comparisonCount++;
            // if cell is empty, search unsuccessful
            if (table[code] == null) return null;
            // if back to start, search unsuccessful
            if (comparisonCount > getSize()) return null;
            // validate by comparing keys
            if (key == table[code].getKey()) {
                entry = table[code];
                break;
            }
            else {
                code++;  // linear probing again, increase key by 1 then search
                if (code == size) code = 0; // wrap around
            }
        }

//        System.out.println("Comparisons executed: " + getCount());
        return entry;  // return null if key is not found
    }

    public int getSize() {
        return size;
    }

    // only valid immediate after searching
    public int getLastCount() {
        return comparisonCount;
    }

    // for test only
    public void print() {
        for (int i = 0; i < getSize(); i++) {
            if (table[i] == null) continue;
            System.out.println("hashCode is " + i + ", " + table[i]);
        }
    }

}
