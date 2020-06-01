package datastructures

case class LinkedListNode(
    v: Int,
    var next: Option[LinkedListNode] = None
)
