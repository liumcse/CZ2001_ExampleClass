#include<iostream>
#include<cstdlib>
#include<ctime>
#include<iomanip>
#include<chrono>
#include<random>

using namespace std;

/*
_______________Insertion Sort Algorithm__________________
*/
long InsertionSort(int A[], int s) {
    int comparison = 0;
    for (int i = 1; i < s; i++) {
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
            comparison++;
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

void InsertStats(long ins1[], long ins2[], int arrayToBeSorted[], int siz, int cnt) {
    // Record start time
    clock_t begin = clock();
    // Execution
    ins2[cnt] = InsertionSort(arrayToBeSorted, siz);
    // Record end time
    clock_t end = clock();
    ins1[cnt] = (long double)(end - begin);
}

void MergeStat(long mer1[], long mer2[], int arrayToBeSorted[], int siz, int cnt) {
    long comparison = 0;
    // Record start time
    clock_t begin = clock();
    // Execution
    MergeSort(arrayToBeSorted, 0, siz - 1,comparison);
    // Record end time
    clock_t end = clock();
    mer1[cnt] = (long)(end - begin);
    mer2[cnt] = comparison;
    return;
}

void GenerateRandomNumber(int A[], int size) {
    mt19937 rng;
    rng.seed(std::random_device()());
    uniform_int_distribution<mt19937::result_type> dist(0, size);

    for(int i = 0; i < size; i++) {
        A[i] = dist(rng);
    }
}

int main() {
    int cnt = 0; // counter
    // For result collection
    int entries[20]; // a table to record the number of entries
    long mer1[20]; // table with time taken for MergeSort Algorithm
    long ins1[20]; // table with time taken for Insertion Sort Algorithm
    int step = 5000;
    long mer2[20]; // table with comparison for MergeSort Algorithm
    long ins2[20]; // table with comparison for Insertion Sort Algorithm

    int siz = 5000; // size of the first array // increment is at the end of the loop
    for (int j = 0; j < 2; j++) {
        entries[cnt] = siz;
        // Initialization
        int A1[siz];
        int A2[siz];

        // RANDOM ARRAY
        GenerateRandomNumber(A1,siz);
        for (int i = 0; i < siz; i++) A2[i] = A1[i];

        // generate results
        MergeStat(mer1,mer2,A1,siz,cnt);
        InsertStats(ins1, ins2, A2, siz, cnt);
        cnt++;
        siz += step; // step size
    }


    for(int i = 0; i < 2; i++) {
        cout << left << setw(10) <<  "Entries" << setw(15) << "Insertion";
        cout << setw(10) << "Time(ms)" << setw(15) << "MergeSort" << setw(10) << "Time(ms)" << endl;
        cout << setw(10) << entries[i] << setw(15) << ins2[i] << setw(10) << ins1[i];
        cout << setw(15) << mer2[i] << setw(10) << mer1[i] << endl;
    }

    return 0;
}
