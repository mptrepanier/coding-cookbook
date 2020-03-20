### Graphs

There exist two standard ways to represent a graph `G = (V, E)`

* Adjacency List
* Adjacency Matrix

Either way applies to both directed and undirected graphs. Adjacency lists provide a compact way to represent **sparse** graphs - `|E| <<< |V| ^2` - and are thus usually the method of choice. We may prefer an adjacency-matrix representation, however, when the graph is **dense**.

For example, all-pairs-shortest-path assumes the input graph is represented by adjacency matrices.

##### Adjacency List Representation

Consists of an array of `|v|` lists, one for each vertex in `V`. For each `u` in `V`, the adjacency list `Adj[u]` contains all the vertices `v` such that there is an edge `(u,v) in E`. Since the adjacency list represents the edges of a graph, in pseudocode we treat array `Adj` as an attribute of the graph, just as we treat the edge set `E`.

* If G is a directed graph, the sum off all the lengths of the adjacency lists is `|E|`. `2|E|` in undirected.

We can  readily adapt adjacency lists to represent **weighted graphs**. We simply store the weight along with the vertex in the adjacency list.

The main disadvantage of an adjacency list is that it presents no quicker way to determine whether a given edge `(u, v)` is present in the graph than to search for `v` in the list `Adj[u]`.

##### Adjacency Matrix Representation

Operating under the assumption that the vertices are numbered `1, 2, ...., |V|`, the adjacency matrix representation of a graph G consists of a `|V| x |V|` matrix such that:

```
A[i, j] = 1 if (i, j) in E
        = 0 otherwise.
```

The adjacency matrix of a graph requires `Theta(V^2)` memory, independent of the edge count. Note that the adjacency matrix of an undirected graph is its own transpose: `A = A^T`. Most algorithms that operate on graphs need to maintain attributes for vertices and/or edges.

##### Breadth-First Search

* Basis for Prim's MST algorithm and Dijkstra's single-source shortest-paths algorithm. Find in algorithms!

Given a graph `G = (V, E)` and a distinguished **source** vertex s, breadth-first search systematically explores the edges of `G` to "discover" every vertex that is reachable from `s`. It computes the distance (smallest number of edges) from `s` to each reachable vertex. It also produces a "breadth-first tree" with root `s` that contains all reachable vertices. For any vertex `v`, reachable from `s`, the simple path in the breadth-first tree from `s` to `v` corresponds to a "shortest path" from `s` to `v`.

1. Visit each neighbor in the frontier.
2. Color vertex white, gray, or black.
   * All start is white.
   * Gray has been visited.
   * Black have had all neighbors visited.
3. Whenever a neighbor is discovered by scanning its adjacency list, the vertex `v` and the edge `u,v` are added to the breadth-first tree. `u` is the parent of `v`. Discovery happens once, so `u` is always the parent.

```scala
def breadthFirstSearch(G, s) = {
	G.vertices.filter(v => v != s).foreach{v =>
		v.color = white      // Color
		v.d = -1             // Distance
		v.predecessor = None // Predecessor.
	}
    s.color = GRAY
    s.distance = 0
    s.predecessor = None
	val queue = new Queue()
	Enqueue(Q, s)
	while (queue.nonEmpty){
		val current = queue.get()
        val neighbors = G.adjacency(current)
        neighbors.foreach{neighbor =>
            if (neighbor.color == WHITE){
                neighbor.color == GRAY
                neighbor.distance = current.d + 1
                neighbor.predecessor = current
                queue.add(neighbor)
            }
        }
        current.color = BLACK
	}

}
```

Runs in `O(V+E)`, or linear in the size of the adjacency-list representation of `G`.

##### Shortest Paths

Let `dMin(s, v)` be the shortest path distance from `s` to `v`, defined as the minimum number of edges in any path from vertex `s` to `v`. If there is no path from `s` to `v`, then `dMin(s,v)= infinity`. 

* BFS search correctly computes the shortest path distance.

##### Breadth-First Trees

BFS builds a breadth-first tree as it searches the graph. For a graph `G = (V,E)` with source `s`, the **predecessor subgraph** of `G` is `G_predecessor = (V_predecessor, E_predecessor)`.

* All `v` must have a predecessor.
* Only contains the predecessor edges.

#### Depth First Search

Explores edges out of the most recently discovered vertex `v` that still has unexplored edges leaving it. Once all of `v's` edges have been explored, the search backtracks to explore edges leaving the vertex from which `v` was discovered.

DFS does not form a single predecessor tree like BFS - instead, its predecessor graph may be composed of several trees as the search may repeat from multiple sources. Therefore, we define the `predecessor subgraph` of a DFS slightly different from that of BFS.

Let:

```
G_predecessor = (V, E_predecessor)
```

* Colors vertices during the search.
* White originally.
* Gray when discovered.
* Blackened when finished.

The technique guarantees that each vertex ends up in exactly one depth-first tree, so that these trees are disjoint.

Besides creating a depth-first forest, depth-first search also **timestamps** each vertex. Each vertex `v` has two timestamps:

* The first  records when `v` is discovered.
* The second records when `v` is blackened.

```


def depthFirstSearch(graph) = {
	var time = 0
	graph.vertices.foreach{vertex =>
		vertex.color = WHITE
		vertex.predecessor = None
	}
	
	graph.vertices.foreach{vertex =>
		if (u.color = WHITE){
			dfsVisit(G, u)
		}
	}
	// Function in function.
        def dfsVisit(G, current) = {
            time = time + 1 // White vertex u has just been discovered.
            current.d = time // Set discovery time.
            current.color = GRAY
            graph.adjacency(current).foreach{neighbor =>
            	if (neighbor.color == WHITE){
            		neighbor.predecessor = current
            		dfsVisit(G, neighbor)
            	}
            }
            current.color = BLACK // blacken u, it is finished.
            time = time + 1
            current.f = time
        }
}


```

When DFS returns, every node has been assigned a discovery time and a finishing time. As each vertex is considered, we say that edge `u, v` is *explored* by depth-first search.

**DFS Properties**:

* Yields valuable information about the structure of a graph.
* Most basic property of DFS is that the predecessor subgraph `G_predecessor` does indeed form a forest of trees, since the structure of the DF trees exactly mirrors the recursive calls of dfsVisit.
* Discovery and finishing times have a **parenthesis structure**.
  * IF we represent the discover of a vertex u with a left paren `(u` and its finishing by a right paren `u)`, then the history of discoveries and finishings makes an expression of properly nested parentheses.

##### White Path Theorem

In a depth-first forest of a graph, vertex `v` is a descendant of vertex `u` if an only if at the time `u.d` that the search discovers `u`, there is a path from `u` to `v` consisting entirely of white vertices.

##### Classification of Edges

![image-20200302220547901](C:\Users\Mike\OneDrive\coding\cookbook\data-structures\image-20200302220547901.png)

The search can be used to classify the edges of the input graph.

1. _Tree edges_ are edges in the depth-first forest. Edge `(u, v)` is a tree edge if `v` was first discovered by exploring edge `(u, v)`.
2. _Back edges_ are those edges connecting a vertex `u` to an ancestor `v` in a depth-first tree. We consider self-loops, which may occur in directed graphs, to be back edges.
3. _Forward Edges_ are those nontree edges connecting a vertex `u` to a descendant `v` in a depth first tree. Think _alternate paths_.
4. _Cross Edges_ are all other edges. They can go between vertices in the same depth-first tree, as long as one vertex is not an ancestor of the other,  or they can go between vertices in different depth-first trees.

##### Topological Sort

A **topological sort** of a DAG is a linear ordering of all its vertices such that if G contains an edge (u, v), then `u` appears before `v` in the ordering.  DAGs are used to indicate precedences among events.

For example, take getting dressed. A directed edge `(u, v)` in the dag ofg figure 22.7 indicates that garment `u` must be donned before garment `v`.

![image-20200302221300074](C:\Users\Mike\OneDrive\coding\cookbook\data-structures\image-20200302221300074.png)

```
def topologicalSort(graph): LinkedList = {
	val linkedList = depthFirstSearchLL(graph)
	// As each vertex is finished, insert it into the front of a linked list.
	linkedList
}
```

##### Strongly Connected Components

A strongly connected component of a directed graph `G = (V, E)` is a maximal set of vertices C such that for every pair of vertices`u` AND `V` IN `C`, we have both `u -> v` and `v -> u`.  Basically, `u` is reachable from `v` and `v` is reachable from `u`. 

![image-20200302222301504](C:\Users\Mike\OneDrive\coding\cookbook\data-structures\image-20200302222301504.png)

```
def stronglyConnectedComponents(G) = {
	depthFirstSearch(G) // compute finishing times
	// compute G_transpose (flip the edges from u,v to v,u)
	depthFirstSearch(G_transpose) // But in the main loop of DFS, consder the vertices in
								  // order of increasing finishing time.
    // Output the vertices of each tree of the transposed 
}
```



