package divideandconquer

object MergeSort {

  def sortOption(array: Option[Array[Int]]): Option[Array[Int]] =
    array match {
      case null => sortOption(None)
      case _    => array.map(sort(_))
    }

  def sort(array: Array[Int]): Array[Int] =
    if (array.length > 1) {
      val pivot         = array.length / 2
      val (left, right) = array.splitAt(pivot)
      functionalMerge(sort(left), sort(right))
    } else {
      array
    }

  def functionalMerge(left: Array[Int], right: Array[Int]): Array[Int] =
    (left.headOption, right.headOption) match {
      case (Some(l), None) => left
      case (None, Some(r)) => right
      case (Some(l), Some(r)) => {
        if (l < r) Array(l) ++ functionalMerge(left.tail, right)
        else Array(r) ++ functionalMerge(left, right.tail)
      }
      case _ => Array.emptyIntArray

    }
//  def merge(left: Array[Int], right: Array[Int]) = {
//    val leftIter = left.iterator
//    val rightIter = right.iterator
//    while(left.iterator.hasNext || right.iterator.hasNext){
//      leftIter.
//    }
//
//  }

}
