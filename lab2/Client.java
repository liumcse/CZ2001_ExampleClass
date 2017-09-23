package lab2;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        LinearProbingHashTable LP;
        // Here reserved for DoubleHashingHashTable
        int entrySize, tableSize, totalCount = 0;
        long startTime, endTime, totalTime = 0;
        double load, averageCount, averageTime;
        Scanner sc = new Scanner(System.in);

        // user input
        System.out.println("Enter number of entries: ");
        entrySize = sc.nextInt();
        System.out.println("Enter load factor (e.g. 0.25, 0.5, 0.75): ");
        load = sc.nextDouble();
        tableSize = (int)(entrySize/load);

        // initialize entries
        Entry[] entry = new Entry[entrySize];
        for (int i = 0; i < entrySize; i++) entry[i] = new Entry();
        System.out.println(entrySize + " entries have been generated.");
        System.out.println();

        // initialize Linear Probing Hash Table
        LP = new LinearProbingHashTable(tableSize) {
            @Override
            public int hashFunction(int key) {
                // A simple hash function
                return key % this.getSize();
            }
        };

        // here reserved for Double Hashing Hash Table

        // insert
        for (Entry e : entry) LP.insert(e.getKey(), e);
        System.out.println(entrySize + " entries have been inserted.");
        System.out.println();

        // successful search
        System.out.println("======SUCCESSFUL SEARCH======");
        for (Entry e : entry) {
            int key = e.getKey();
            startTime = System.nanoTime();  // start timing
            LP.search(key);
            endTime = System.nanoTime();  // end timing
            totalTime = endTime - startTime;
            totalCount += LP.getLastCount();
        }
        averageCount = (double)totalCount/entrySize;
        averageTime = (double)totalTime/entrySize;
        System.out.println("Average number of comparisons in Linear Probing is " + averageCount);
        System.out.println("Average time in Linear Probing is " + averageTime + " ns");
        System.out.println();

        // unsuccessful search
        System.out.println("======UNSUCCESSFUL SEARCH======");
        for (Entry e : entry) {
            int falseKey = e.getKey() - 20000000;
            startTime = System.nanoTime();  // start timing
            LP.search(falseKey);
            endTime = System.nanoTime();  // end timing
            totalTime = endTime - startTime;
            totalCount += LP.getLastCount();
        }
        averageCount = (double)totalCount/entrySize;
        averageTime = (double)totalTime/entrySize;
        System.out.println("Average number of comparisons in Linear Probing is " + averageCount);
        System.out.println("Average time in Linear Probing is " + averageTime + " ns");
        System.out.println();

    }
}
