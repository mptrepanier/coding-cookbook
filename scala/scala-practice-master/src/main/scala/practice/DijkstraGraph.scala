package practice

import scala.collection.mutable
import scala.collection.mutable.Queue

case class Vertex(id: Int, var visited: Boolean = false, var distance: Double = Double.PositiveInfinity) {
  def visit                     = this.visited = true
  def distance(d: Double): Unit = this.distance = d
}

class DijkstraGraph() {

  var vertices                                              = scala.collection.mutable.Set.empty[Vertex]
  var vertexMap                                             = scala.collection.mutable.Map.empty[Int, Vertex]
  var adjacencyList                                         = scala.collection.mutable.Map.empty[Int, Seq[Vertex]]
  var edges: scala.collection.mutable.Set[(Vertex, Vertex)] = scala.collection.mutable.Set.empty[(Vertex, Vertex)]

  def buildGraph(starts: Seq[Int], stops: Seq[Int]) = {
    vertices = scala.collection.mutable.Set((starts ++ stops).map(Vertex(_)): _*)
    adjacencyList = scala.collection.mutable.Map(vertices.toSeq.map(x => (x.id -> Seq.empty[Vertex])): _*)
    vertexMap = scala.collection.mutable.Map(vertices.map(vertex => (vertex.id -> vertex)).toSeq: _*)
    starts.zip(stops).foreach {
      case (start, stop) =>
        addEdge(vertexMap(start), vertexMap(stop))

    }
  }

  def addEdge(start: Vertex, stop: Vertex): Unit = {
    edges += ((start, stop))
    adjacencyList.get(start.id) match {
      case Some(neighbors) => adjacencyList += (start.id -> (neighbors :+ stop))
      case _               => adjacencyList += (start.id -> Seq(stop))
    }
  }

  def bfs(start: Vertex, target: Int): Option[Vertex] = {
    val frontier = Queue.empty[Vertex]
    frontier.enqueue(start)
    start.visit
    while (frontier.nonEmpty) {
      val current = frontier.dequeue()
      if (current.id == target) return Some(current)
      val neighbors = adjacencyList(current.id)
      neighbors.foreach { neighbor =>
        if (!neighbor.visited) {
          frontier.enqueue(neighbor)
          neighbor.visit
        }
      }
    }
    None
  }

  def dijkstra(start: Vertex, target: Int): Option[Vertex] = {
    val frontier = new mutable.PriorityQueue[Vertex]()(Ordering.by(_.distance))
    // vertices.foreach(frontier.enqueue(_))
    start.distance(0)
    frontier.enqueue(start)
    while (frontier.nonEmpty) {
      val current = frontier.dequeue()
      current.visit
      println(current.id)
      if (current.id == target) return Some(current)
      val neighbors = adjacencyList(current.id)
      neighbors.foreach { neighbor =>
        if (!neighbor.visited) {
          val alt = current.distance + 1
          if (alt < neighbor.distance)
            neighbor.distance(alt)
          frontier.enqueue(neighbor)
        }
      }
    }
    None
  }

}
