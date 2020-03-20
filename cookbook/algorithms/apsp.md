## All Pairs Shortest Paths

```
		 0                   if i = j
w_ij = { the weight          if i =/= j and (i,j) in E
         INFINITY            if i =/= j and (i,j) not in E
```



```
def printAllPairsShortestPath(predecessors, i, j) = {
	if (i == j){
		println(i)
	}else if (predecessors(i, j) == NONE){
		println("no path")	
	}else{
		printAllPairsShortestPath(predecsessors, i, predecessors_ij)
	}
}
```

### Shortest Paths and Matrix Multiplication

Developing a dynamic-programming algorithm:

1. Characterize the structure of an optimal solution.
2. Recursively define the value of an optimal solution.
3. Compute the value of an optimal solution in a bottom-up fashion.
4. Construct an optimal solution from computed information.

#### The Structure of a Shortest Path

All subpaths of a shortest path are shortest paths. Suppose we represent the graph by an adjacency matrix `W = (w_ij)`. Consider a shortest path `p` from `i` to `j`, and suppose that it contains at most `m` edges.

Assuming there are no negative weight cycles, `m` is finite. We can decompose path `p` into:

```
   p'
i -> k -> j
```

where path `p'` now contains at most `m - 1` edges. By Lemma 24.1, `p'` is a shortest path from `i` to `k`, and so `d(i,j) = d(i,k) + w_kj`



#### A Recursive Soln

Let `l_ij_m` be the minimum weight of any path from `i` to `j` that contains at most `m` edges. When `m = 0`, `i=j`. Thus, `l_ij_0` is INFINITY is `i != 0`.

For `m >= 1`, we compute `l_ij_m` as the minimum of `l_ij_m-1` and `l_ij_m`. Thus, we recursively define:  

```
l_ij_m = min(l_ij_m-1, min{l_ik_m-1 + w_kj})
       = min(l_ik_m-1 + w_kj) for 1<=k<= n
```

If the graph contains no negative weight cycles, then for every pair of vertices `i` and `j` for which a path exists, there is a shortest path from `i` to `j` that is simple and contains at *most* `n-1` edges.

#### Computing the Shortest Path Weights Bottom Up

Taking as our input matrix `W = w_ij`, we now compute a series of matrices `L_1, ..., L-n-1`, where for `m=1,2,...n-1`, we have `L_m = (l_ij_m)`. The final matrix `L(n-1)` contains the actual shortest-path weights. Observe that `L_ij_1 = W` as all paths of length one have a distance equal to their weight.

```
def extendShortestPaths(L,W) = {
	n = L.rows
	let L' = (l'_ij) be a new nxn matrix
	for (i <- 1 to n){
		for (j <- 1 to n){
			l'ij = INFINITY
			for (k <- 1 to n){
				l'_ij = min(l'_ij, l_ik + w_kj)
			}
		}
	}
	L'
}

// SLOW!
def slowAllPairsShortestPaths(W) = {
	n = W.rows
	L_1 = W
	for (m <- 2 to n-1){
		let L(m) be a new nxn matrix
		L(m) = extendShortestPaths(L(m-1), W)
	}
	return L(n-1)
}
```





