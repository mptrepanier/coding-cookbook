## Dynamic Programming (WIP)

Developing a dynamic-programming algorithm:

1. Characterize the structure of an optimal solution.
2. Recursively define the value of an optimal solution.
3. Compute the value of an optimal solution in a bottom-up fashion.
4. Construct an optimal solution from computed information.

### Rod Cutting

#### Non Dynamic

Given a rod length of `n` inches and a table of prices `p_i` for `i = 1, 2, ..., n`, determine the maximum revenue `r_n`  obtainable by cutting up the rod and selling the pieces.

```
length i  : 1  2  3  4  5  6  7  8  9  10
price  p_i: 1  5  8  9  10 17 17 20 24 30 
```

A rod of this length can be cut `2^n-1` different ways. VERY SLOW! `T(2^n)`

```
def cutRod(p, n) = {
if (n == 0) return 0

// Iterates through all possible cutgs.
q = -INFINITY
	for (i <- 1 to n){
		q = max(q, p[i] + cutRod(p, n-i))
	}
}
```

#### Dynamic

Let's arrange for each subproblem to be solved only once, saving its solution in a map. If we need to refer to this subproblem's solution again later, we can just look it up, rather than recomputing it.

We use additional memory to save computation time!

#### Memoization

**Top Down:** Write problem in standard recursive fashion. Check if problem has been previously solved, if so, look it up.

**Bottom Up**: Depends on  some natural notion of the "size" of a subproblem, such that solving any particular subproblem depends only on solving "smaller" subproblems. We sort the subproblems by size, and solve them in size order.

First, the top down:

```
def memoizedCutRod(p,n) = {
	// let r[0..n] be a new array
	for i = 0 to n
		r[i] = -INFINITY
    memoizedCutRodAux(p,n,r)
}

// NOTE R is a GLOBAL
def memoizedCutRodAux(p,n,r) = {
	// If we have a soln!
	if r[n] >= 0
		return r[n]
	if n == 0
		q = 0
    else q = -INFINITY
    	for i = 1 to n
    		q = max(q, p[i] + memoizedCutRodAux(p, n-i, r))
    r[n] = q
    return q
}

```

Next, the bottom up. Less memory intensive, requires more thought.

```
def bottomUpCutRod(p,n) = {
	// let r[0..n] be a new array
	r[0] = 0
	for (j <- 1 to n){
		q = -INFINITY
		for (i <- 1 to j){
			q = max(q, p[i] + r[j-i])
		}
		r[j] = q
	}
	return r[n]
}
```



