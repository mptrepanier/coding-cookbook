package datastructures

class MyQueue() {
  var first: Option[LinkedListNode] = None
  var last: Option[LinkedListNode]  = None

  def setFirst(i: Option[LinkedListNode]): Unit = first = i

  def setLast(i: Option[LinkedListNode]): Unit = last = i

  def enqueue(i: Int): Unit = {
    val current = Some(LinkedListNode(i))
    first match {
      case Some(f) => {
        last.foreach(_.next = current)
        setLast(current)
      }
      case _ => {
        setFirst(current)
        setLast(current)
      }
    }
  }

  def dequeue(i: Int): Option[Int] = first.map { f =>
    setFirst(f.next)
    f.v
  }
}
