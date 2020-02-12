package sorting

import scala.collection.mutable.ArrayBuffer

object MergeSort {

  def merge(left: Array[Int], right: Array[Int]): Array[Int] = {
    (left.nonEmpty, right.nonEmpty) match {
      case (true, false) => left
      case (false, true) => right
      case (false, false) => Array.emptyIntArray
      case _ => {
        if (left.head < right.head){
          Array(left.head ) ++ this.merge(left.tail, right)
        } else {
          Array(right.head) ++ this.merge(left, right.tail)
        }
      }
    }
  }

  def mergeSort(arr: Array[Int]): Array[Int] = {
    if (arr.length > 1){
      val key = arr.length / 2
      val (left, right) = arr.splitAt(key)
      merge(mergeSort(left), mergeSort(right))
    }else{
      arr
    }
  }
}
