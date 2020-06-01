import cracking.LinkedListDuplicateRemoval
import datastructures.flow.FlowGraph
import datastructures.{ BinarySearchTree, Graph, Vertex }
import sorting._
import divideandconquer._
import math.MathStuff
import practice.{
  DijkstraGraph,
  Directory,
  DirectoryRecurse,
  File,
  MatchingParenthesis,
  MedianOfSortedArrays,
  MergingLists
}

object MainFunctionFella {

  /**
    *
    * @param args: args(0) - Demographics path. args(1) - Notes path. args(2) - Output path. args(3) - Date column.
    */
  def main(args: Array[String]): Unit = {
    println("Woo!")
    kruskalTest
  }

  //    val test1 = Array(0, 0, -100, 100, 100, -100, 0, 0)
  //    val test2 = Array(1000, -1, 1, 1)
  //    MaximumSubarray.findMaximumSubarray(test1).foreach(println)
  // treeTest
  // fordFulkersonTest
  //    longestSubStringTest
  // medianSortedTest
  // matchedParensTest
  // llTest
  // llTest
  // mathStuffTest
  // llMergeTest
  // dijkstraTest
  // directoryRecurseTest
  // mergeSortTest

  def treeTest = {
    val keys = Array(0, 10, -10, 5, -5, 15, -15, 20)
    val tree = new BinarySearchTree()
    keys.foreach(key => tree.insert(key))
    println("Tree walk!")
    tree.inOrderWalk
    println("Delete test!")
    tree.delete(0)
    tree.inOrderWalk
  }

  def dijkstraTest: Unit = {
    val graph = new DijkstraGraph()
    graph.buildGraph(Seq(0, 0, 1, 2, 2, 3, 3, 4, 4, 5), Seq(1, 2, 3, 1, 4, 2, 5, 3, 5, 7))
    val start = graph.vertexMap(0)
    graph.dijkstra(start, 7).foreach { v =>
      println(v.id); println(v.distance)
    }

  }

  def fordFulkersonTest: Unit = {
    val graph = new FlowGraph()
    graph.buildGraph(Seq(0, 0, 1, 2, 2, 3, 3, 4, 4),
                     Seq(1, 2, 3, 1, 4, 2, 5, 3, 5),
                     Seq(16, 13, 12, 4, 14, 9, 20, 7, 4))

    graph.adjacencyList.foreach { case (k, v) => print(k); print(" "); v.foreach(print(_)); println }
    val start = graph.vertexMap(0)
    val stop  = graph.vertexMap(5)
    graph.fordFulkerson(start, stop)
    graph.flows.foreach {
      case ((u, v), f) =>
        println(u + " " + v + " " + f)
    }

    val flow = graph.flows
      .filter {
        case ((start, stop), f) =>
          stop == 5
      }
      .aggregate(0d)((acc, tuple) => acc + tuple._2, (t1, t2) => t1 + t2)
    println(flow)
  }

  def longestSubStringTest: Unit = {
    println("Woo!")
    println(practice.LongestSubstring.lengthOfLongestSubstring(" "))
    println(practice.LongestSubstring.lengthOfLongestSubstring("abcabcbb"))
    println(practice.LongestSubstring.lengthOfLongestSubstring("bbbbb"))
    println(practice.LongestSubstring.lengthOfLongestSubstring("pwwkew"))
  }

  def medianSortedTest: Unit = {
    println("Woo!")
    println(MedianOfSortedArrays.findMedianSortedArrays(Array(1, 2), Array(3, 4)))
    println(MedianOfSortedArrays.findMedianSortedArrays(Array(1, 3), Array(2)))
  }

  def reverseTest = {
    println(practice.ReverseInteger.reverse(100))
    println(practice.ReverseInteger.reverse(-100))
    println(practice.ReverseInteger.reverse(0))
    println(practice.ReverseInteger.reverse(1534236469))

  }

  def matchedParensTest =
    println(MatchingParenthesis.isValid("()"))

  def llTest = {
    val arr  = Seq(1, 2, 1, 2, 4, 5, 7)
    val head = LinkedListDuplicateRemoval.assembleLinkedList(arr)
    head.map { h =>
      LinkedListDuplicateRemoval.printFromNode(h)
      LinkedListDuplicateRemoval.recursiveRemove(h, 4)
      println
      LinkedListDuplicateRemoval.printFromNode(h)
    }
  }

  def mathStuffTest = {
    println(MathStuff.isPrime(10))
    println(MathStuff.isPrime(61))
//    println(MathStuff.smartMultiplyStrings("10", "7"))
  }

  def llMergeTest = {
    // val arr1   = Seq.empty[Int]
    val arr1   = Seq(1, 2, 11, 17)
    val arr2   = Seq(1, 3, 4, 5, 6, 7, 8, 9, 20)
    val head1  = LinkedListDuplicateRemoval.assembleLinkedList(arr1)
    val head2  = LinkedListDuplicateRemoval.assembleLinkedList(arr2)
    val sorted = MergingLists.mergeTwoLists(head1, head2)
    sorted.foreach(LinkedListDuplicateRemoval.printFromNode(_))
  }

  def directoryRecurseTest = {
    val f1 = File("f1")
    val f2 = File("f2")
    val f3 = File("f3")
    val d2 = Directory("d2", Seq(f2, f3))
    val d1 = Directory("d1", Seq(f1, d2))
    DirectoryRecurse.recurse(d1).foreach(println)
  }

  def mergeSortTest = {
    val unsorted  = Array(4, 1, 89, 123, 2, 123, 434, 65)
    val sorted    = divideandconquer.MergeSort.sort(unsorted)
    val nullCheck = divideandconquer.MergeSort.sortOption(null)
    println(nullCheck)
    sorted.foreach(println)
  }

  def kruskalTest: Unit = {
    val starts = Seq("a", "a", "b", "b", "c", "c", "c", "d", "d", "e", "f", "g", "g", "h")
    val stops  = Seq("b", "h", "h", "c", "i", "f", "d", "f", "e", "f", "g", "i", "h", "i")
    val ws     = Seq(4, 8, 11, 8, 2, 4, 7, 14, 9, 10, 2, 6, 1, 7)
    val graph  = new Graph().buildGraph(starts, stops, ws)
    println(graph.kruskal.map(_._2).sum)
  }

}
