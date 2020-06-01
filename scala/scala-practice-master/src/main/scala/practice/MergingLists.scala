package practice

import datastructures.LinkedListNode

object MergingLists {
  def mergeTwoLists(l1: Option[LinkedListNode], l2: Option[LinkedListNode]): Option[LinkedListNode] =
    (l1, l2) match {
      case (Some(left), None)  => Option(left)
      case (None, Some(right)) => Option(right)
      case (Some(left), Some(right)) => {
        if (left.v < right.v) {
          val next = mergeTwoLists(left.next, l2)
          left.next = next
          l1
        } else {
          val next = mergeTwoLists(l1, right.next)
          right.next = next
          l2
        }
      }
      case _ => None
    }
}
