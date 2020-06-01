package math

object MathStuff {

  def isPrime(n: Int): Boolean = {
    val marked = Array.fill(n + 1)(false)
    var x      = 2
    while (x < n) {
      if (!marked(x)) {
        for (i <- x to n by x) {
          marked(i) = true
        }
      }
      x += 1
    }
    marked(n)
  }

  def stupidMultiplyStrings(num1: String, num2: String) = (stringToInt(num1) * stringToInt(num2)).toString

  def stupidTenPower(n: Int) = Array.fill(n)(10).reduceLeftOption(_ * _).getOrElse(1)

  def stringToInt(s: String): Int =
    s.reverse.zipWithIndex.aggregate(0)(
      (sum, tuple) => sum + charToInt(tuple._1) * stupidTenPower(tuple._2),
      (a, b) => a + b
    )

  def charToInt(c: Char): Int = c match {
    case '0' => 0
    case '1' => 1
    case '2' => 2
    case '3' => 3
    case '4' => 4
    case '5' => 5
    case '6' => 6
    case '7' => 7
    case '8' => 8
    case '9' => 9
  }

//  def smartMultiplyStrings(num1: String, num2: String) = {
//    var tally    = Array.fill(12100)(0)
//    val arr1     = num1.reverse.zipWithIndex
//    val arr2     = num2.reverse.zipWithIndex
//    val maxPlace = 0
//
//    arr1.foreach {
//      case (n1, n1Place) =>
//        arr2.foreach {
//          case (n2, n2Place) => {
//            val product      = charToInt(n1) * charToInt(n2)
//            val currentIndex = n1Place + n2Place
//          }
//          tally(n1Place + n2Place) += charToInt(n1) * charToInt(n2)
//        }
//    }
//    val x = StringBuffer()
//
//  }

}
