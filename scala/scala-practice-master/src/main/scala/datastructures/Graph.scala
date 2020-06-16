package datastructures

import datastructures.Status.Status
import javax.swing.plaf.synth.SynthToolTipUI

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Status extends Enumeration {
  type Status = Value
  val UNDISCOVERED, DISCOVERED, FINISHED = Value
}

case class Vertex(id: String,
                  var status: Status = Status.UNDISCOVERED,
                  var distance: Double = Double.PositiveInfinity,
                  var discoverTime: Double = Double.PositiveInfinity,
                  var finishTime: Double = Double.PositiveInfinity) {
  def discover(time: Double = Double.PositiveInfinity) = { this.status = Status.DISCOVERED; this.discoverTime = time }

  def finish(time: Double = Double.PositiveInfinity) = { this.status = Status.FINISHED; this.finishTime = time }
}

class Graph {

  var vertices      = mutable.Set.empty[Vertex]
  var adjacencyList = mutable.Map.empty[String, Seq[Vertex]]
  var edgeWeights   = mutable.Map.empty[(String, String), Int]
  var vertexMap     = mutable.Map.empty[String, Vertex]

  def buildGraph(starts: Seq[String], stops: Seq[String], weights: Seq[Int], directed: Boolean = false) = {
    vertices = mutable.Set((starts ++ stops).map(x => Vertex(x)): _*)
    vertexMap = mutable.Map(vertices.map(x => (x.id -> x)).toSeq: _*)
    starts.zip(stops).zip(weights).map {
      case ((start, stop), capacity) =>
        if (directed) addEdge(vertexMap(start), vertexMap(stop), capacity)
        else addUndirectedEdge(vertexMap(start), vertexMap(stop), capacity)
    }
    this
  }

  def addUndirectedEdge(start: Vertex, end: Vertex, weight: Int): Unit = {
    adjacencyList.get(start.id) match {
      case Some(neighbors) => {
        adjacencyList += (start.id -> (neighbors :+ end))
      }
      case _ => adjacencyList += (start.id -> Seq(end))
    }
    adjacencyList.get(end.id) match {
      case Some(neighbors) => {
        adjacencyList += (end.id -> (neighbors :+ start))
      }
      case _ => adjacencyList += (end.id -> Seq(start))
    }
    edgeWeights += ((start.id, end.id) -> weight)
    edgeWeights += ((end.id, start.id) -> weight)
  }

  def addEdge(start: Vertex, end: Vertex, weight: Int): Unit = {
    adjacencyList.get(start.id) match {
      case Some(neighbors) => {
        adjacencyList += (start.id -> (neighbors :+ end))
      }
      case _ => adjacencyList += (start.id -> Seq(end))
    }
    edgeWeights += ((start.id, end.id) -> weight)
  }

  // A graph cut partitions a graph.
  // The cut *respects* A (mst) if A is wholly in one of the partitions (no edge of A crosses the cut).
  // An edge is defined as a "light edge" if its weight is the minimum of any edge crossing the cut.
  // If a light edge crosses the cut, and one of the nodes of that edge belongs to A, that edge
  // is a "safe edge" and can safely be added to A.

  // Kruskal's algorithm.

  def kruskal = {
    // Assign each vertex to a unique set (or color).
    val mst                = scala.collection.mutable.Map.empty[(String, String), Int]
    val sets               = ArrayBuffer(vertices.toSeq: _*).map(x => Set(x.id))
    def findSet(s: String) = sets.indexWhere(_.contains(s))
    val sortedEdgeWeights  = edgeWeights.toSeq.sortBy(_._2)
    sortedEdgeWeights.foreach {
      case ((u, v), w) => {
        val uSet = findSet(u)
        val vSet = findSet(v)
        if (uSet != vSet) {
          mst += ((u, v) -> w)
          sets(uSet) = sets(uSet) ++ sets(vSet)
          sets.remove(vSet)
        }
      }
    }
    mst.foreach(println)
    mst
  }

  def bfs(start: Vertex, target: Int): Option[Vertex] = {
    val frontier = mutable.Queue.empty[Vertex]
    frontier.enqueue(start)
    start.discover()
    while (frontier.nonEmpty) {
      val current = frontier.dequeue()
      if (current.id == target) return Some(current)
      val neighbors = adjacencyList(current.id)
      neighbors.foreach { neighbor =>
        if (neighbor.status == Status.UNDISCOVERED) {
          frontier.enqueue(neighbor)
          neighbor.discover()
        }
      }
    }
    None
  }

  /**
    * DEPTH FIRST SEARCH
    *
    * Applications and Properties:
    *
    * PARENTHESIS THEOREM
    * If we represent the discovery of a vertex u with a left parenthesis "(u" and represent its finishing by a right
    * parenthesis "u)", then the history of discoveries and finishings makes a well-formed expression in the
    * sense that the parenthesis are properly nested.
    *
    *   In any DFS of a (directed or undirected) graph G = (V,E), for any two vertices u and v, exactly
    *   one of the three conditions holds:
    *     - The intervals [u.d, u.f] and [v.d, v.f] are entires disjoint, and neither u nor v is a descendant of the
    *     other in a depth first forest.
    *     - [u.d, u.f] is contained entirely withing [v.d, v.f], and u is a descendant of v in a depth-first tree.
    *     - Previous, but vice versa.
    *
    * TOPOLOGICAL SORTING:
    * A topological sorting of a graph means that all nodes are visited in order - thus, a parent must
    * be inserted in a list prior to its children.
    *
    * To attain a topological sorting via DFS, prepend each node to a list as it is finished.
    *
    * STRONGLY CONNECTED COMPONENTS:
    *  A strongly connected component of a directed graph G = (V, E) is a maximal set of vertices C in V,
    *  such that for every pair of vertices "u" and "v" in C, we have both u -> v and v -> u. This is,
    *  vertices u and v are reachable from one another.
    *
    *  Involves using the transpose of G (flipping the adjacency list edges).
    *   - Call DFS(G) to compute finishing times (u.f).
    *   - Compute GT + Transpose(G)
    *   - Call DFS(GT), but in the main loop of DFS, consider the vertices in order of decreasing finishing time.
    *   - Output the vertices of each tree in the DFS forest formed in line 3 as as
    *     separate strongly connected component.
    *
    * ACYCLIC GRAPHS:
    *  A graph has a cycle IF a child node leads back to its parent. Thus, if a node leads to a node in the "DISCOVERED"
    *  state, the graph is cyclic.
    */
  def recursiveDFT: Unit = {
    var time = 0
    def recursiveDFT(current: Vertex): Unit = {
      time += 1
      current.discover(time)
      val neighbors = adjacencyList(current.id)
      neighbors.foreach { neighbor =>
        if (neighbor.status == Status.UNDISCOVERED) recursiveDFT(neighbor)
      }
      time += 1
      current.finish(time)
    }
    vertices.foreach { start =>
      if (start.status == Status.UNDISCOVERED) recursiveDFT(start)
    }
  }

  def iterativeDFT = {
    var time = 0
    def iterativeDFT(start: Vertex) = {
      val frontier = mutable.Stack[Vertex]()
      frontier.push(start)
      while (frontier.nonEmpty) {
        val current = frontier.top
        time += 1
        if (current.status != Status.UNDISCOVERED) {
          current.finish()
          frontier.pop
        } else {
          current.discover(time)
          adjacencyList(current.id).foreach { neighbor =>
            if (neighbor.status == Status.UNDISCOVERED) {
              neighbor.discover(time)
              frontier.push(neighbor)
            }
          }
        }
      }
    }
    vertices.foreach { start =>
      if (start.status == Status.UNDISCOVERED) {
        iterativeDFT(start)
      }
    }
  }

  /**
    * Recursively looks for the target value. Also updates the DFS graph.
    * @param start
    * @param target
    * @return
    */
  def recursiveDFS(start: Vertex, target: Int): Option[Vertex] = {
    var time = 0
    def recursiveDFS(current: Vertex): Option[Vertex] = {
      time += 1
      current.discover(time)
      if (current.id == target) Some(current)
      else {
        val neighbors = adjacencyList(current.id)
        val found = neighbors.aggregate[Option[Vertex]](Option.empty[Vertex])(
          (vertexOpt, vertex) =>
            vertexOpt match {
              case Some(v) => vertexOpt
              case _       => recursiveDFS(vertex)
          },
          (vertexOpt1, vertexOpt2) => Seq(vertexOpt1, vertexOpt2).flatMap(x => x).headOption
        )
        time += 1
        current.finish(time)
        found
      }
    }
    recursiveDFS(start)
  }

  def iterativeDFS(start: Vertex, target: Int): Option[Vertex] = {
    val frontier = mutable.Stack[Vertex]()
    frontier.push(start)
    var time = 0
    while (frontier.nonEmpty) {
      val current = frontier.pop
      if (current.id == target) return Some(current)
      adjacencyList(current.id).foreach { neighbor =>
        if (neighbor.status == Status.UNDISCOVERED) {
          neighbor.discover(time)
          frontier.push(neighbor)
        }
      }
    }
    None
  }
}
