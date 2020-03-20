## Single Source Shortest Paths

Given a weighted, directed graph `G = (V, E)`, find the path with minimum weight that connects `u` to `v`.

Variants:

* **Single-destination shortest-path:** Find a shortest path to a given destination `t` from each vertex `v`. By reversing the direction of each edge in the graph, we can reduce this to a single-source problem
* **Single-pair shortest-path:** Find the shortest path from `u` to `v` for given vertices `u` and `v`. Although we can solve this problem by running a single source algorithm once from each vertex, we can usually solve it faster.
* **All-pairs shortest-paths**: Find a shortest path from `u` to `v` for every pair of vertices `u` and `v`. Although we can solve this problem by running a single-source algorithm once for each vertex, we can usually solve it faster.

### Optimal Substructure of a Shortest Path

Shortest-paths algorithms typically rely on the property that a shortest path between two vertices contains other shortest paths within it. Basically, if you found a sub-path that was shorter between two points, that would constitute a shorter path overall.

#### Relaxation

First, we need to initialize a single source shortest path.

```
def initializeSingleSource(G,s) = {
	graph.vertices.foreach{vertex =>
		vertex.d = INFINITY // Shortest Path Estimate
		vertex.predecessor = None
	}
	s.d = 0
}
```

Next, if we find a shorter path to a node, update its **Shortest Path Estimate**.

```
def relax(current,next,w) = {
	if (next.d > current.d + w(current,next)){
		next.d = u.d + w(current,next) // We've found a shorter path.
		next.predecessor = current
	}
}
```

### The Bellman-Ford Algorithm

Solves the single-source shortest-paths problem in the general case in which edge weights may be negative.



The algorithm relaxes an estimate on `v.d` on the weight of a shortest path from the source `s` to each vertex `v` until it achieves the actual shortest-path weight `d_min(s,v)`. The algorithm returns true if and only if the graph contains no negative weight cycles that are reachable from the source.

```
def bellmanFord(graph, weights, start): Boolean = {
	initializeSingleSource(G,start)
	for (i <- 1 to |G.V| - 1){
		graph.edges.foreach(edge => relax(edge.start, edge.finish, weights))
	}
	graph.edges.exists{edge =>
		edge.finish.distance > edge.start.distance + weights(edge.start, edge.finish)
	}
}
```

Runs in O(V * E).

### Single Source Shortest Paths in DAGs

```
def dagShortestPaths(G,w,s) = {
	topologically sort G's vertices
	Initialize single source (G,s)
	for each vertex u taken in topologically sorted order
		for each vertex v in graph.neighbors(u)
			relax(u,v,w)
}
```

Runs in O(u,v,w)

#### Dijkstra's Algorithm

Solves the single source shortest paths problem on a weighted, directed graph G = (V,E).

Does indeed compute shortest path despite being greedy!

```
def dijkstra(graph, weights, s) = {
	initializeSingleSource(graph,s)
	val determined = Set.empty[Node]
	val priorityQueue = graph.vertices
	while(priorityQueue.nonEmpty){
		val current = priorityQueue.get()
		determined += u
		graph.neighbors(current).foreach{neighbor =>
			relax(u,v,w)
		}
	}
}
```

