public abstract class DoubleHashingHashTable {
    private long comparisonCount;

    private final int size;
    private final Entry[] table;

    // constructor with size specified.
    public DoubleHashingHashTable(int size) {
        this.size = size;
        // initialize hashCode: an array of Entries
        this.table = new Entry[size];
    }

    // hashFunction is defined by user
    public abstract int hashFunction(int key);

    // A simple modulo secondary hashing function
    public abstract int doubleHashFunction(int key);

    public void insert(int key, Entry entry) {
        int code = hashFunction(key);

        // If the hash cell has already been taken (collision)
        while (table[code] != null) {
            // double hashing, use the hashFunction to find another slot
            code = (code + doubleHashFunction(key)) % size;
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
            // if every cell has been searched, search unsuccessful
            if (comparisonCount >= getSize()) return null;
            // validate by comparing keys
            if (key == table[code].getKey()) {
                entry = table[code];
                break;
            }
            else {
                // Use the second hashing function to get the next slot
                code = (code + doubleHashFunction(key)) % size;
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
