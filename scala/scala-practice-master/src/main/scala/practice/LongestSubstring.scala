package practice

object LongestSubstring {
  def lengthOfLongestSubstring(s: String): Int = {
    var longestSubstring = 0
    def traverse(start: Int = 0, end: Int = 0): Unit = {
      val next = end + 1
      if (next <= s.length) {
        val sub = s.substring(start, next)
        if (checkUnique(sub)) {
          if (sub.length > longestSubstring) { longestSubstring = sub.length }
          traverse(start, next)
        } else {
          // Find latest breaking character and start from there.
          val newStart = findPreviousInstance(s.substring(0, next), sub.last) + 1
          traverse(newStart, next)
        }
      }
    }
    traverse()
    longestSubstring
  }

  def checkUnique(s: String): Boolean = s.toSet.size == s.length

  // Could use map to speed WAY up.
  def findPreviousInstance(s: String, c: Char): Int = {
    val toCheck = s.reverse.tail.reverse
    toCheck.lastIndexOf(c)
  }
}
