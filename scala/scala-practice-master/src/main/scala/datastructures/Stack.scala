package datastructures

case class Stack(var top: Option[LinkedListNode] = None) {
  private var size = 0
  def pop: Option[Int] = top.map { t =>
    this.top = t.next
    size -= 1
    t.v
  }
  def push(i: Int): Unit = {
    this.top = Some(LinkedListNode(i, top))
    size += 1
  }
  def peek: Option[Int] = top.map(_.v)
}
