package cracking

object PermutationString {

  def isPermutation(a: String, b: String) = stringToMap(a) == stringToMap(b)

  def stringToMap(input: String) = {
    val map = scala.collection.mutable.HashMap.empty[Char, Int]
    input.foreach { char =>
      map.get(char) match {
        case Some(value) => map += (char -> (value + 1))
        case _           => map += char  -> 1
      }
      map
    }
  }
}
