#include<iostream>
#include<cstdlib>
#include<ctime>
#include<iomanip>
#include<chrono>

using namespace std;

/*
_______________Insertion Sort Algorithm__________________
*/
long InsertionSort(int A[], int size) {
    long comparison = 0;
    for (int i = 1; i < size; i++) {
        for (int j = i; j > 0; j--) {
            comparison++;
            if (A[j] < A[j-1]) {
                swap(A[j-1],A[j]);
            }
            else break;
        }
    }
    return comparison;
}

/*
______________________MERGESORT ALGORITHM_________________________
*/
void merge(int A[], int first, int last, long &comparison) {
    if ((last - first) <= 0) return;
    else {
        int c = 0;
        int temp[last - first]; // auxillary array
        int mid = (first + last) / 2;
        int i = first, j = mid + 1;

        while ((i <= mid) && (j <= last)) {
            if (A[i] < A[j]) {
                temp[c++] = A[i++];
            }
            else {
                temp[c++] = A[j++];
            }
            comparison++;  // comparison increase
        }

        if (i <= mid) {
            for(int k = i; k <= mid; k++) {
                temp[c++] = A[k];
            }
        }
        else if (j <= last) {
            for(int k = j; k <= last; k++) {
                temp[c++] = A[k];
            }
        }

        int a = 0;
        for(int k = first; k <= last; k++) {
            A[k] = temp[a++];
        }
    }
    return;
}

void MergeSort(int A[],int first, int last, long &comparison) {
    if ((last - first) <= 0) {
        return;
    }
    else {
        int mid = (first + last)/2;
        MergeSort(A, first, mid, comparison);
        MergeSort(A, mid + 1, last, comparison);
    }
    merge(A, first, last, comparison);
}

void InsertStats(long timeTaken[], long comparisonTaken[], int arrayToBeSorted[], int siz, int index) {
    // Record start time
    clock_t begin = clock();
    // Execution
    comparisonTaken[index] = InsertionSort(arrayToBeSorted, siz);
    // Record end time
    clock_t end = clock();
    timeTaken[index] = (long double)(end - begin);
}

void MergeStat(long timeTaken[], long comparisonTaken[], int arrayToBeSorted[], int siz, int index) {
    long comparison = 0;
    // Record start time
    clock_t begin = clock();
    // Execution
    MergeSort(arrayToBeSorted, 0, siz - 1,comparison);
    // Record end time
    clock_t end = clock();
    timeTaken[index] = (long)(end - begin);
    comparisonTaken[index] = comparison;
    return;
}

void GenerateRandomNumber(int A[], int size) {
    // mt19937 rng;
    // rng.seed(std::random_device()());
    // uniform_int_distribution<mt19937::result_type> dist(0, size);

    for(int i = 0; i < size; i++) {
        A[i] = rand() % (size + 1);
    }
}

void GenerateNumbersInIncreasingOrder(int A[], int size) {
    for (int i = 0; i <= size; i++) {
        A[i] = i + 1;
    }
}

void GenerateNumbersInDecreasingOrder(int A[], int size) {
    for (int i = 0; i <= size; i++) {
        A[i] = size - i;
    }
}

int main() {
    // For result collection
    int entreis[10]; // a table to record the number of entries
    long timeOfMergeSort[10]; // table with time taken for MergeSort Algorithm
    long timeOfInsertionSort[10]; // table with time taken for Insertion Sort Algorithm
    int step = 1000;
    long comparisonOfMergeSort[10]; // table with comparison for MergeSort Algorithm
    long comparisonOfInsertionSort[10]; // table with comparison for Insertion Sort Algorithm

    int size = 1000; // size of the first array // increment is at the end of the loop

    /* --- randomly generated dataset --- */
    for (int j = 0; j < 10; j++) {
        entreis[j] = size;
        // Initialization
        int arrayForInsersionSort[size];
        int arrayForMergeSort[size];

        // random array
        GenerateRandomNumber(arrayForInsersionSort, size);
        for (int i = 0; i < size; i++) arrayForMergeSort[i] = arrayForInsersionSort[i];

        // generate results
        MergeStat(timeOfMergeSort,comparisonOfMergeSort,arrayForInsersionSort,size, j);
        InsertStats(timeOfInsertionSort, comparisonOfInsertionSort, arrayForMergeSort, size, j);
        size += step; // step size
    }

    cout << "================Randomly generated dataset================" << endl;
    for(int i = 0; i < 10; i++) {
        cout << left << setw(10) <<  "Entries" << setw(15) << "Insertion";
        cout << setw(10) << "Time(ms)" << setw(15) << "MergeSort" << setw(10) << "Time(ms)" << endl;
        cout << setw(10) << entreis[i] << setw(15) << comparisonOfInsertionSort[i] << setw(10) << timeOfInsertionSort[i];
        cout << setw(15) << comparisonOfMergeSort[i] << setw(10) << timeOfMergeSort[i] << endl;
    }

    size = 1000;  // restore size

    /* --- dataset of integers in increasing order --- */
    for (int j = 0; j < 10; j++) {
        entreis[j] = size;
        // Initialization
        int arrayForInsersionSort[size];
        int arrayForMergeSort[size];

        // array of integers sorted in increasing order
        GenerateNumbersInIncreasingOrder(arrayForInsersionSort, size);
        for (int i = 0; i < size; i++) arrayForMergeSort[i] = arrayForInsersionSort[i];

        // generate results
        MergeStat(timeOfMergeSort,comparisonOfMergeSort,arrayForInsersionSort,size, j);
        InsertStats(timeOfInsertionSort, comparisonOfInsertionSort, arrayForMergeSort, size, j);
        size += step; // step size
    }
    cout << "================Increasing integers dataset================" << endl;
    for(int i = 0; i < 10; i++) {
        cout << left << setw(10) <<  "Entries" << setw(15) << "Insertion";
        cout << setw(10) << "Time(ms)" << setw(15) << "MergeSort" << setw(10) << "Time(ms)" << endl;
        cout << setw(10) << entreis[i] << setw(15) << comparisonOfInsertionSort[i] << setw(10) << timeOfInsertionSort[i];
        cout << setw(15) << comparisonOfMergeSort[i] << setw(10) << timeOfMergeSort[i] << endl;
    }

    size = 1000;  // restore size

    /* --- dataset of integers in decreasing order --- */
    for (int j = 0; j < 10; j++) {
        entreis[j] = size;
        // Initialization
        int arrayForInsersionSort[size];
        int arrayForMergeSort[size];

        // array of integers sorted in increasing order
        GenerateNumbersInDecreasingOrder(arrayForInsersionSort, size);
        for (int i = 0; i < size; i++) arrayForMergeSort[i] = arrayForInsersionSort[i];

        // generate results
        MergeStat(timeOfMergeSort,comparisonOfMergeSort,arrayForInsersionSort,size, j);
        InsertStats(timeOfInsertionSort, comparisonOfInsertionSort, arrayForMergeSort, size, j);
        size += step; // step size
    }
    cout << "================Decreasing integers dataset================" << endl;
    for(int i = 0; i < 10; i++) {
        cout << left << setw(10) <<  "Entries" << setw(15) << "Insertion";
        cout << setw(10) << "Time(ms)" << setw(15) << "MergeSort" << setw(10) << "Time(ms)" << endl;
        cout << setw(10) << entreis[i] << setw(15) << comparisonOfInsertionSort[i] << setw(10) << timeOfInsertionSort[i];
        cout << setw(15) << comparisonOfMergeSort[i] << setw(10) << timeOfMergeSort[i] << endl;
    }



    return 0;
}
