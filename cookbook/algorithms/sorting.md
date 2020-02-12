### The Sorting Problem

_Input_: A sequence of numbers `[a1, a2, ..., an]`.

_Output_: A permutation (reordering) `[a1', a2', ..., an']` of the input sequence such that `a1' <= a2' <= ... <= an'`

### Algorithms

#### Insertion Sort

_Worst Case Runtime:_ O(n^2)

_Average Case Runtime_: O(n^2)

_Sorts in place!_

```
Insertion-Sort(A)

for j = 2 to A.length

	key = A[j]
	// Insert A[j] into the sorted sequence A[1 .. j-1]
	// Scans sorted seq backwards for the place to insert.
	i = j - 1
	while i > 0 and A[i] > key
		A[i + 1] = A[i]
		i = i - 1
        A[i + 1] = key
	
```

#### Merge Sort

_Worst Case Runtime:_ O(nlog(n))

_Average Case Runtime_: O(nlog(n))

_Notes_: Does not sort in place!

```
// Sort array between indices p and r.
// Splits until p = r, depth conditition enforces array of length 1.
// Walks back up, mergin' and sortin' as it goes.
Merge-Sort(A,p,r)

if p < r
	q = lower((p + r) / 2)
	Merge-Sort(A,p,q)
	Merge-Sort(A,q+1,r)
	Merge(A,p,q,r)


// A: Array, sorted in-place.
// p, q, r are indices such that p <= q < r
Merge(A, p, q, r)
	n1 = q - p + 1 // Size of left.
	n2 = r - q // Size of right.
	let L[1..n1 + 1] and R[1..n2 + 1] be new arrays
	for i = 1 to n1
		L[i] = A[p + i - 1]
    for j = 1 to n2
    	R[j] = A[q + j]
    L[n1 + 1] = Inf
    R[n2 + 1] = Inf 
    i = 1
    j = 1
    // Walk through left/right, inserting the smaller of the two and incrementing
    // that counter.
    for k = p to r
    	if L[i] <= R[j]
    		A[k] = L[i]
    		i = i + 1
        else
        	A[k] = R[j]
        	j = j + 1
```

#### Heap Sort

_Worst Case Runtime:_ O(nlog(n))

_Average Case Runtime_: O(nlog(n))

_Sorts in place! Refers to the data structure, not garbage collected storage!_

**See Heaps in Data Structures**

#### Quick Sort

_Worst Case Runtime:_ Theta(n^2)

_Average Case Runtime_: O(nlog(n))

_Sorts in place!_

```
// Partitions (rearranges) the array A[p..r] intwo two (possibly empty) subarrays A[p..q-1]
// and A[q+1..r] such that each element of A[p..q-1] is less than or equal to A[q], which
// is, in turn, less than or equal to each element of A[q+1..r]

// "Pivots" around r by sliding an array of numbers larger than r across. Numbers smaller
// get moved prior to the array, numbers larger are appended to array.
// Pivot happens by swapping first item in "big" array with next encountered smaller 
// number.


Quicksort(a,p,r)
if p < r
	q = Partition(A,p,r)
	Quicksort(A,p,q-1)
	Quicksort(A,q+1,r)
	
Partition(A, p, r)
x = A[r]
i = p - 1
for j = p to r - 1
	if A[j] <= X
		i = i + 1
		exchange A[i] with A[j]
exchange A[i + 1] with A[r]
return i + 1
		
```











