package sorting

object ExternalSort {
  // The heap data structure is an array object that can be viewed as a nearly complete binary tree.
  // Each node of the tree corresponds to an element of the array. The tree is completely filled on all levels
  // except possibly the lowest, which is filled from the left up to a point.
  def externalSort(bigArray: Array[Double], size: Int = 10) = {
    val smallArrays       = bigArray.sliding(size, size).toSeq
    val sortedSmallArrays = smallArrays.map(MergeSort.mergeSort(_))
    val queueCollection =
      smallArrays.map(smallArray => scala.collection.mutable.PriorityQueue(smallArray: _*)(Ordering[Double].reverse))
    val minHeapMap = queueCollection.zipWithIndex.map {
      case (a, b) =>
        (b -> a)
    }.toMap
    val minHeapOrdering = Ordering.by[(Double, Int), Double](_._1).reverse
    val minHeap         = scala.collection.mutable.PriorityQueue()(minHeapOrdering)
    val mins            = minHeapMap.map { case (index, queue) => (queue.dequeue(), index) }.toSeq
    minHeap.enqueue(mins: _*)
    while (minHeapMap.values.exists(_.nonEmpty)) {
      val (num, index) = minHeap.dequeue()
      println(num)
      if (minHeapMap(index).nonEmpty) minHeap.enqueue((minHeapMap(index).dequeue(), index))
    }
  }
}
