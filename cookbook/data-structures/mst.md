## Minimum Spanning Trees

A MST is the smallest tree that "spans" a graph G. Each algorithm runs in O(E log(V)), although Prim's can run in time (O E + VlgV) if we use a Fibonacci heap.

Each algorithm is greedy:

* Makes best possible choice given known information.
* Does not guarantee global optimum.

### Growing a MST

We assume we have a connected, undirected graph weigh a weight function. Prior to each iteration, A is a subset of some minimum spanning tree.

At each step, we determine an edge `(u, v)` that we can add to A without violating this invariant. We call such an edge a **safe edge** for A, since we can add it safely to A while maintaining the invariant.

```
def genericMST(G, w) => {
	A = empty
	while (A.size != G.size){
		find edge (u, v) that is safe for A // The tricky part.
		A = A Union {(u, v)}
	}
}

```

#### Prim's Algorithm

Basically, it's BFS with a priority queue. Requires a starting point, returns tree rooted at point.

```
def mstPrim(graph, weightFunction, start) = {
	graph.vertices.foreach{vertex =>
		vertex.key = INFINITY
		vertex.predecessor = None
	}
	start.key = 0 // START BECOMES ROOT OF MST!
	val priorityQueue = graph.vertices
	while (priorityQueue.nonEmpty){
		val current = priorityQueue.get()
		graph.neighbors(current).foreach{neighbor =>
			if (priorityQueue.contains(neighbor) && w(current, neighbor) < neighbor.key){
				neighbor.predecessor = current
				neighbor.key = weightFunction(u,v)
			}
			
		}
	}
}
```

