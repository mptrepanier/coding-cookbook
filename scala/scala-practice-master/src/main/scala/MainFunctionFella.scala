import sorting._
import divideandconquer._

object MainFunctionFella {

  /**
    *
    * @param args: args(0) - Demographics path. args(1) - Notes path. args(2) - Output path. args(3) - Date column.
    */
  def main(args: Array[String]): Unit = {
    val test1 = Array(0, 0, -100, 100, 100, -100, 0, 0)
    val test2 = Array(1000,-1,1,1)
    MaximumSubarray.findMaximumSubarray(test1).foreach(println)
  }
}
