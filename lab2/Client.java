package lab2;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        LinearProbingHashTable LP;
        DoubleHashingHashTable DH;
        int entrySize, tableSize, primeSize;
        long startTime, endTime, totalTime, totalCount;
        double load, averageCount, averageTime;
        Scanner sc = new Scanner(System.in);

        // user input
        System.out.println("Enter number of entries: ");
        entrySize = sc.nextInt();
        System.out.println("Enter approximate load factor (e.g. 0.25, 0.5, 0.75): ");
        load = sc.nextDouble();
        tableSize = (int)(entrySize/load);
        primeSize = getPrime(tableSize);

        // initialize entries
        Entry[] entry = new Entry[entrySize];
        for (int i = 0; i < entrySize; i++) entry[i] = new Entry();
        System.out.println(entrySize + " entries have been generated.");
        System.out.println();

        // initialize Hash Table
        LP = new LinearProbingHashTable(primeSize) {
            @Override
            public int hashFunction(int key) {
                // A simple hash function
                return key % this.getSize();
            }
        };
        DH = new DoubleHashingHashTable(primeSize) {
            @Override
            public int hashFunction(int key) {
                // A simple hash function
                return key % this.getSize();
            }

            @Override
            public int doubleHashFunction(int key) {
                return 1 + (key % (this.getSize() - 1));
            }
        };

        // here reserved for Double Hashing Hash Table

        // insert
        for (Entry e : entry) {
            LP.insert(e.getKey(), e);
            DH.insert(e.getKey(), e);
        }
        System.out.println(entrySize + " entries have been inserted to a Linear Probing Hash Table of size " + LP.getSize());
        System.out.println(entrySize + " entries have been inserted to a Double Hashing Hash Table of size " + DH.getSize());
        System.out.printf("The actual load factor is %f\n", (double)entrySize/primeSize);
        System.out.println();

        // successful search for Linear Probing Hash Table
        System.out.println("======SUCCESSFUL SEARCH======");
        totalTime = 0;  // reset totalTime
        totalCount = 0;  // reset totalCount
        for (Entry e : entry) {
            int key = e.getKey();
            startTime = System.nanoTime();  // start timing
            LP.search(key);
            endTime = System.nanoTime();  // end timing
            totalTime += endTime - startTime;
            totalCount += LP.getLastCount();
        }
        averageCount = (double)totalCount/entrySize;
        averageTime = (double)totalTime/entrySize;
        System.out.println("Average number of comparisons in Linear Probing is " + averageCount);
        System.out.println("Average time in Linear Probing is " + averageTime + " ns");
        System.out.println();
        // continue searching for Double Hashing Hash Table
        totalTime = 0;  // reset totalTime
        totalCount = 0;  // reset totalCount
        for (Entry e : entry) {
            int key = e.getKey();
            startTime = System.nanoTime();  // start timing
            DH.search(key);
            endTime = System.nanoTime();  // end timing
            totalTime += endTime - startTime;
            totalCount += DH.getLastCount();
        }
        averageCount = (double)totalCount/entrySize;
        averageTime = (double)totalTime/entrySize;
        System.out.println("Average number of comparisons in Double Hashing is " + averageCount);
        System.out.println("Average time in Double Hashing is " + averageTime + " ns");
        System.out.println();

        // unsuccessful search for Linear Probing Hash Table
        System.out.println("======UNSUCCESSFUL SEARCH======");
        totalTime = 0;  // reset totalTime
        totalCount = 0;  // reset totalCount
        for (Entry e : entry) {
            int falseKey = e.getKey() - 20000000;
            startTime = System.nanoTime();  // start timing
            LP.search(falseKey);
            endTime = System.nanoTime();  // end timing
            totalTime += endTime - startTime;
            totalCount += LP.getLastCount();
        }
        averageCount = (double)totalCount/entrySize;
        averageTime = (double)totalTime/entrySize;
        System.out.println("Average number of comparisons in Linear Probing is " + averageCount);
        System.out.println("Average time in Linear Probing is " + averageTime + " ns");
        System.out.println();

        // continue searching for Double Hashing Hash Table
        totalTime = 0;  // reset totalTime
        totalCount = 0;  // reset totalCount
        for (Entry e : entry) {
            int falseKey = e.getKey() - 20000000;
            startTime = System.nanoTime();  // start timing
            DH.search(falseKey);
            endTime = System.nanoTime();  // end timing
            totalTime += endTime - startTime;
            totalCount += DH.getLastCount();
        }
        averageCount = (double)totalCount/entrySize;
        averageTime = (double)totalTime/entrySize;
        System.out.println("Average number of comparisons in Double Hashing is " + averageCount);
        System.out.println("Average time in Double Hashing is " + averageTime + " ns");
        System.out.println();

    }

    // get the nearest prime number that is larger than x
    public static int getPrime(int x) {
        for (int i = x; i < 2 * x; i++) {
            if (isPrime(i)) return i;
        }
        return x;
    }

    // test if the number is prime
    public static boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0) return false;
        //if not, then just check the odds
        for(int i = 3; i * i <= n; i += 2) {
            if(n % i == 0)
                return false;
        }
        return true;
    }
}
