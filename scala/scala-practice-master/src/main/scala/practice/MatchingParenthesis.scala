package practice
import scala.collection.mutable.Stack

object MatchingParenthesis {

  def isValid(s: String): Boolean = {
    val stack   = new Stack[Char]()
    val mapping = Map[Char, Char](')' -> '(', '}' -> '{', ']' -> '[')
    s.foreach { ch =>
      if (mapping.keys.toArray.contains(ch)) {
        val topElement =
          if (stack.nonEmpty) stack.pop
          else '#'
        if (mapping(ch) != topElement) return false
      } else stack.push(ch)
    }
    stack.isEmpty
  }

}
