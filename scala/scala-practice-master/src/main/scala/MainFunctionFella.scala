import datastructures.{ BinarySearchTree, FlowGraph, Vertex }
import sorting._
import divideandconquer._
import practice.{ MatchingParenthesis, MedianOfSortedArrays }

object MainFunctionFella {

  /**
    *
    * @param args: args(0) - Demographics path. args(1) - Notes path. args(2) - Output path. args(3) - Date column.
    */
  def main(args: Array[String]): Unit =
//    val test1 = Array(0, 0, -100, 100, 100, -100, 0, 0)
//    val test2 = Array(1000, -1, 1, 1)
//    MaximumSubarray.findMaximumSubarray(test1).foreach(println)
    // treeTest
    // fordFulkersonTest
//    longestSubStringTest
    // medianSortedTest
    matchedParensTest

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

}
