package datastructures.flow

import scala.collection.mutable
import scala.collection.mutable.{ Map, Set }

case class Vertex(
    id: Int,
    var f: Int = 0,
    var key: Int = 0,
    var distance: Double = Double.PositiveInfinity,
    var color: Int = 0,
    var predecessor: Option[Vertex] = None
)

case class Edge(
    start: Vertex,
    stop: Vertex,
    capacity: Double = 0,
    var flow: Double = 0
)

class FlowGraph {

  var vertices: Set[Vertex] = Set.empty[Vertex]

  var vertexMap: Map[Int, Vertex] = Map.empty[Int, Vertex]

  def addVertex(vertex: Vertex): this.type = { vertices += vertex; this }

  def addVertex(id: Int): this.type = { vertices += Vertex(id); this }

  def removeVertex(vertex: Vertex): this.type = { vertices -= vertex; this }

  def resetVertices = vertices.foreach { vertex =>
    vertex.distance = Double.PositiveInfinity
    vertex.predecessor = None
    vertex.color = 0
    vertex.key = 0
    vertex.f = 0
  }

  def safelyUpdateMap(oldVertex: Vertex, newVertex: Vertex) = {}

  var adjacencyList: scala.collection.mutable.Map[Int, Seq[Vertex]] = Map.empty[Int, Seq[Vertex]]

  var edges: scala.collection.mutable.Set[(Vertex, Vertex)] = Set.empty[(Vertex, Vertex)]

  var flows: scala.collection.mutable.Map[(Int, Int), Double] = Map.empty[(Int, Int), Double]

  var capacities: scala.collection.mutable.Map[(Int, Int), Double] = Map.empty[(Int, Int), Double]

  def addEdge(start: Vertex, end: Vertex, capacity: Double): Unit = {
    edges += ((start, end))
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

    capacities += ((start.id, end.id) -> capacity)
    capacities += ((end.id, start.id) -> 0)
    flows += ((start.id, end.id)      -> 0)
    flows += ((end.id, start.id)      -> 0)
    // flows += ((end, start)      -> 0)
  }

  def buildGraph(starts: Seq[Int], stops: Seq[Int], caps: Seq[Double]) = {
    vertices = Set((starts ++ stops).map(x => Vertex(x)): _*)
    vertexMap = Map(vertices.map(x => (x.id -> x)).toSeq: _*)
    // Initialize the graph to all empty!
    edges = edges.empty
    // vertices = vertices.empty
    adjacencyList = adjacencyList.empty
    flows = flows.empty
    capacities = capacities.empty
    starts.zip(stops).zip(caps).map {
      case ((start, stop), capacity) =>
        addEdge(vertexMap(start), vertexMap(stop), capacity)
    }
  }

  def targetedBFS(start: Vertex, stop: Vertex): Option[Vertex] = {
    resetVertices
    start.color = 1
    start.distance = 0
    start.predecessor = None
    val frontier = mutable.Queue.empty[Vertex]
    frontier.enqueue(start)
    while (frontier.nonEmpty) {
      val current         = frontier.dequeue()
      val neighbors       = adjacencyList(current.id)
      val viableNeighbors = neighbors.filter(neighbor => calculateResidualCapacity((current, neighbor)) != 0)
      viableNeighbors.foreach { neighbor =>
        if (neighbor.color == 0) {
          neighbor.color = 1
          neighbor.distance = current.distance + 1
          neighbor.predecessor = Some(current)
          frontier.enqueue(neighbor)
        }
        if (neighbor == stop) return Some(stop)
      }
      current.color = 2
    }
    None
  }

  def edgeToIdTuple(edge: (Vertex, Vertex)) = (edge._1.id, edge._2.id)

  def reverseEdge(edge: (Vertex, Vertex)) = (edge._2, edge._1)

  def getPath(vertex: Vertex) = {
    def traverse(maybeVertex: Option[Vertex]): Seq[Vertex] =
      maybeVertex.fold(Seq.empty[Vertex])(v => Seq(v) ++ traverse(v.predecessor))
    (Seq(vertex) ++ traverse(vertex.predecessor)).reverse
  }

  def getEdges(vertex: Vertex): Seq[(Vertex, Vertex)] = {
    def traverse(current: Vertex, maybeNext: Option[Vertex]): Seq[(Vertex, Vertex)] =
      maybeNext.fold(Seq.empty[(Vertex, Vertex)])(next => Seq((next, current)) ++ traverse(next, next.predecessor))
    traverse(vertex, vertex.predecessor)
  }

  def calculateMinCapOnPath(vertex: Vertex, currentMin: Double = Double.PositiveInfinity): Double = {
    val edges = getEdges(vertex)
    edges.map { case (v1, v2) => capacities(v1.id, v2.id) }.min
  }

  def calculateResidualCapacity(edge: (Vertex, Vertex)): Double = {
    val v1 = edge._1
    val v2 = edge._2
    capacities((v1.id, v2.id)) - flows((v1.id, v2.id))
  }

  def fordFulkerson(start: Vertex, stop: Vertex): Unit =
    // Find minimum capacity along path.
    targetedBFS(start, stop) match {
      case Some(vertex) => {
        val pathEdges = getEdges(vertex).toSet
        val minCap    = pathEdges.map { case (v1, v2) => capacities(v1.id, v2.id) - flows(v1.id, v2.id) }.min
        pathEdges.foreach { edge =>
          flows(edgeToIdTuple(edge)) += minCap
          flows(edgeToIdTuple(reverseEdge(edge))) -= minCap
        }
        fordFulkerson(start, stop)
      }
      case _ => {}
    }
}
