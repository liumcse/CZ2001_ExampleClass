public abstract class LinearProbingHashTable {
    private long comparisonCount;

    private final int size;
    private final Entry[] table;

    // constructor with size specified.
    public LinearProbingHashTable(int size) {
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
            code = (code + 1) % size;
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
            if (comparisonCount >= getSize()) return null;
            // validate by comparing keys
            if (key == table[code].getKey()) {
                entry = table[code];
                break;
            }
            else {
                // linear probing again, increase key by 1 then search
                code = (code + 1) % size;
            }
        }

        return entry;  // return null if key is not found
    }

    public int getSize() {
        return size;
    }

    // only valid immediate after searching
    public long getLastCount() {
        return comparisonCount;
    }

}
