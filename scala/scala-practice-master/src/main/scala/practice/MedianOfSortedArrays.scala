package practice

object MedianOfSortedArrays {
  def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
    var i           = 0
    var j           = 0
    val medianIndex = (1.0 * nums1.length + nums2.length) / 2.0
    var current     = -1
    def getNext(arr1: Array[Int], arr2: Array[Int]) = {
      val maybe1 = nums1.lift(i)
      val maybe2 = nums2.lift(j)
      (maybe1, maybe2) match {
        case (Some(m1), Some(m2)) => {
          if (m1 < m2) {
            i = i + 1
            current = m1
          } else {
            j = j + 1
            current = m2
          }
        }
        case (Some(m1), None) => {
          i = i + 1
          current = m1
        }
        case (None, Some(m2)) => {
          j = j + 1
          current = m2
        }
        case (None, None) => {}
      }
    }
    while (i + j < medianIndex) {
      getNext(nums1, nums2)
    }

    if (medianIndex % 1.0 == 0.0) {
      val prev = current
      getNext(nums1, nums2)
      (current + prev) / 2.0
    } else {
      current
    }
  }
}
