package practice

object ReverseInteger {
  def reverse(x: Int): Int = {
    val a = x.toString.reverse

    val b = if (a.length > 0) {
      a.replaceAll("^0", "")
    } else {
      a
    }
    val c =
      if (b.endsWith("-")) ("-" + b.replaceAll("-$", ""))
      else a

    try {
      c.toInt
    } catch {
      case e: Exception => 0
    }
  }

}
