package cracking

object DistinctString {

  def distinctString(input: String) = input.distinct.length == input.length

  def distinctString2(input: String) = {
    val characterSet = scala.collection.mutable.Set.empty[Char]
    input.foreach(characterSet += _)
    characterSet.size == input
  }

}
