package cracking

import datastructures.LinkedListNode

object LinkedListDuplicateRemoval {

  def assembleLinkedList(arr: Seq[Int]): Option[LinkedListNode] =
    arr.headOption.map { head =>
      val headLinkedListNode = LinkedListNode(head)
      var current            = headLinkedListNode
      arr.tail.foreach { item =>
        val next = LinkedListNode(item)
        current.next = Some(next)
        current = next
      }
      headLinkedListNode
    }

  def printFromNode(LinkedListNode: LinkedListNode): Unit = {
    print(LinkedListNode.v + " "); LinkedListNode.next.foreach(printFromNode(_))
  }

  def recursiveRemove(head: LinkedListNode, target: Int): Option[LinkedListNode] = {
    def remove(current: LinkedListNode, prev: Option[LinkedListNode] = None): Option[LinkedListNode] =
      // If we have a hit.
      if (current.v == target) {
        prev match {
          case Some(p) => { p.next = current.next; Some(head) }
          case _       => current.next // No prev? We've got a new head!
        }
      } else {
        current.next match {
          case Some(n) => remove(n, Some(current))
          case _       => Some(head)
        }
      }
    remove(head)
  }

  def recursiveRemoveDupes(head: LinkedListNode): Option[LinkedListNode] = {
    val intSet  = scala.collection.mutable.Set.empty[Int]
    var newHead = Option(head)
    def remove(current: LinkedListNode, prev: Option[LinkedListNode] = None): Unit = {
      // If we have a hit.
      if (intSet.contains(current.v)) {
        prev match {
          case Some(p) => p.next = current.next
          case _       => newHead = (current.next)
        }
      }
      intSet += current.v
      current.next match {
        case Some(n) => remove(n, Some(current))
        case _       => {}
      }
    }
    remove(head)
    newHead
  }

  def removeIntWhile(head: LinkedListNode, target: Int): LinkedListNode = {
    if (head.v == target) head.next
    else {
      var current = head
      while (current.next.nonEmpty) {
        if (current.next.get.v == target) {
          current.next = current.next.get.next
        } else {
          current = current.next.get
        }
      }
    }
    head
  }
}
