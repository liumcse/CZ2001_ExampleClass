#include<iostream>
#include<cstdlib>
#include<ctime>
#include<iomanip>
#include<chrono>

using namespace std;

/*
_______________Insertion Sort Algorithm__________________
*/
unsigned long long InsertionSort(unsigned long long A[], unsigned long long s)
{
    int comparison = 0;
    for (unsigned long long i = 1; i < s; i++)
    {
        for (unsigned long long j = i; j > 0; j--)
        {
            comparison++;
            if (A[j] < A[j-1])
            {
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
void merge(unsigned long long A[], unsigned long long first, unsigned long long last, unsigned long long &comparison)
{
    if ((last - first) <= 0) return;
    else
    {
        unsigned long long c = 0;
        unsigned long long temp[last - first]; // auxillary array
        unsigned long long mid = (first + last) / 2;
        unsigned long long i = first, j = mid + 1;

        while ((i <= mid) && (j <= last))
        {
            if (A[i] < A[j])
            {
                temp[c++] = A[i++];
                comparison++;
            }
            else if (A[i] > A[j])
            {
                temp[c++] = A[j++];
                comparison += 2;
            }
            else
            {
                temp[c++] = A[i++];
                temp[c++] = A[j++];
                comparison += 2;
            }
        }

        if (i <= mid)
        {
            for(unsigned long long k = i; k <= mid; k++)
            {
                temp[c++] = A[k];
            }
        }
        else if (j <= last)
        {
            for(unsigned long long k = j; k <= last; k++)
            {
                temp[c++] = A[k];
            }
        }

        unsigned long long a = 0;
        for(unsigned long long k = first; k <= last; k++)
        {
            A[k] = temp[a++];
        }
    }
    return;
}

void MergeSort(unsigned long long int A[],unsigned long long int first, unsigned long long int last, unsigned long long &comparison)
{
    if ((last - first) <= 0) {
        return;
    }
    else {
        int mid = (first+last)/2;
        MergeSort(A, first, mid, comparison);
        MergeSort(A, mid + 1, last, comparison);
    }

    merge(A, first, last, comparison);
}

void GenerateRandomNumber(unsigned long long int A[], unsigned long long int siz) {
    for(unsigned long long int i = 0; i < siz; i++) {
        A[i] = rand() % siz;
    }
}

void InsertStats(unsigned long long int ins1[], long double ins2[], unsigned long long int arrayToBeSorted[], unsigned long long int siz, int cnt) {
    // Record start time
    clock_t begin = clock();
    // Execution
    ins2[cnt] = InsertionSort(arrayToBeSorted, siz);
    // Record end time
    clock_t end = clock();
    ins1[cnt] = (long double)(end - begin);
}

void MergeStat(unsigned long long int mer1[], long double mer2[], unsigned long long int arrayToBeSorted[], unsigned long long int siz, int cnt) {
    unsigned long long comparison = 0;
    // Record start time
    clock_t begin = clock();
    // Execution
    MergeSort(arrayToBeSorted, 0, siz - 1,comparison);
    // Record end time
    clock_t end = clock();
    mer1[cnt] = (long double)(end - begin);
    mer2[cnt] = comparison;
    return;
}


int main() {
    int cnt = 0; // counter
    // For result collection
    unsigned long long int entries[20]; // a table to record the number of entries
    unsigned long long int mer1[20]; // table with time taken for MergeSort Algorithm
    unsigned long long int ins1[20]; // table with time taken for Insertion Sort Algorithm
    unsigned long long int step = 5000;
    long double mer2[20]; // table with comparison for MergeSort Algorithm
    long double ins2[20]; // table with comparison for Insertion Sort Algorithm

    unsigned long long siz = 5000; // size of the first array // increment is at the end of the loop
    for (int j = 0; j < 20; j++) {
        entries[cnt] = siz;
        // Initialization
        unsigned long long int A1[siz];
        unsigned long long int A2[siz];

        // RANDOM ARRAY
        GenerateRandomNumber(A1,siz);
        for (unsigned long long int i = 0; i < siz; i++) A2[i] = A1[i];


        /* // ASCENDING ARRAY
        for (unsigned long long int i = 1; i <= siz; i++)
        {
            A1[i-1] = i;
            A2[i-1] = i;
        }
        */

        /* // DESCENDING ARRAY
        for (unsigned long long int i = 1; i <= siz; i++)
        {
            A[i-1] = siz-i+1;
            A2[i-1] = siz-i+1;
        }
        */

        // generate results
        MergeStat(mer1,mer2,A1,siz,cnt);
        InsertStats(ins1, ins2, A2, siz, cnt);
        cnt++;
        siz += step; // step size
    }
    for(int i = 0; i < 20; i++) {
        cout << left << setw(10) <<  "Entries" << setw(15) << "Insertion";
        cout << setw(10) << "Time(ms)" << setw(15) << "MergeSort" << setw(10) << "Time(ms)" << endl;
        cout << setw(10) << entries[i] << setw(15) << ins2[i] << setw(10) << ins1[i];
        cout << setw(15) << mer2[i] << setw(10) << mer1[i] << endl;
    }

    // test
    unsigned long long test[10] = {234, 345, 12312, 23, 5, 123, 56, 3422, 1455, 23123};
    InsertionSort(test, 10);
    for (int kk = 0; kk < 10; kk++) cout << test[kk] << ", ";

    return 0;
}
