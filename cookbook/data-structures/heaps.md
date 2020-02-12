### Heaps

The **(binary) heap** data structure is an array object that can be viewed as a near complete binary tree.

* Each node in the tree corresponds to an array element.
* All levels are completely filled except for possibly the lowest, which is filled from the left up until a point.

An array `A` that represents a heap has two attributes, `A.length` and `A.heapSize`. `0 <= A.heapSize <= A.length`. The root of the tree is `A[1]` and given the index `i` of a node, we can easily compute the indices of its parent, left child, and right child.

```
parentIndex(i) = lower(i/2)
leftIndex(i) = 2i
rightIndex(i) = 2i + 1
```

A **max heap's** node values satisfy the following property:

```
A[parentIndex(i)] >= A[i] // Parent value always greater than or equal to child.
```

A **min heap's** node values satisfy the following property:

```
A[parentIndex(i)] <= A[i] // Parent value always less than or equal to child.
```

##### Example

```
Is [23, 17, 14, 6, 13, 10, 1, 5, 7, 12] a max heap?

                     23
         17                     14
    6        13             10        1
 5     7    12
 
 Yes! Parents are always greateer than children.
```

##### maxHeapify

```
// Takes in an array and index.
// Assumes the binary trees rooted at left(i) and right(i) are max-heaps, but A[i] might
// be smaller than its children, thus violating the max-heap property.
// Floats the value of A[i] down the tree, so that the subtree rooted at i obeys the
// property

maxHeapify(A,i)
l = leftIndex(i)
r = rightIndex(i)
// What is the largest, A[i], A[r], or A[l]
if l <= A.heapSize && A[l] > A[largest]
	largest = l
else
	largest = i
if r <= A.heapSize && A[r] > A[largest]
	largest = r
if largest != i
	exchange A[i] with A[largest]
	maxHeapify(A,largest) // Largest is now the child. C'mon textbook.
```

##### buildMaxHeap

```
// Leverages maxHeapify in a bottom-up manner to convert an array into a max-heap.

buildMaxHeap(A)
// By starting at lower(A.length/2), the chidren of the node at that index
// are necessarily leaves.
A.heapSize = A.length
for i = lower(A.length/2) downto 1
	maxHeapfiy(A,i)
	
```

##### heapSort

```
// The top of the heap is always the maximum. One of its children is always the largest.
// We just keep on bubblin' up them bigguns.
heapSort(A)
buildMaxHeap(A)
for i = A.length downto 2
	exchange A[1] with A[i]
	A.heapSize = A.heapSize - 1
	maxHeapify(A,1)
```



