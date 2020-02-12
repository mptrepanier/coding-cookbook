package divideandconquer

object MaximumSubarray {

  /**
    * Using divide and conquer involves splitting array into two.
    * Maximum subarray can lie in one of three places.
    *       Entirely in left.
    *       Entirely in right.
    *       Spans midpoint.
     */




  def findMaximumSubarray(arr: Array[Int]): Array[Int] = {
    if (arr.length > 1){
      val pivot = arr.length / 2
      val (left, right) = arr.splitAt(pivot)
      val leftie = findMaximumSubarray(left)
      val rightie = findMaximumSubarray(right)
      val crossie = findMaxCrossingSubarray(left, right)
      chooseMax(leftie, rightie, crossie)

      // TODO: Could edge more elegantly, but meh.
    }else{
      if (arr.sum > 0){arr}else{Array.emptyIntArray}
    }
  }


  /**
    * The max crossing subarray MUST span the midpoint
    * @param left
    * @param right
    * @return
    */
  def findMaxCrossingSubarray(left: Array[Int], right: Array[Int]): Array[Int] = {
    val maxLeftSubarray = findSidedMaxArray(left)
    val maxRightSubarray = findSidedMaxArray(right.reverse).reverse
    maxLeftSubarray ++ maxRightSubarray
  }


  /**
    * Recursively checks for the maximum subarray.
    * @param arr
    * @return
    */
  def findSidedMaxArray(arr: Array[Int]): Array[Int] = {
    if (arr.length <= 1) {
      if (arr.sum > 0) {
        arr
      } else {
        Array.emptyIntArray
      }
    } else {
      val s = arr.sum
      val check = findSidedMaxArray(arr.tail)
      if (s > check.sum) {
        arr
      } else {
        check
      }
    }
  }

  def chooseMax(arrs: Array[Int]*): Array[Int] = {
    val sums = arrs.map(x => x.sum)
    val maxIndex = sums.indexOf(sums.max)
    arrs(maxIndex)
  }

}
