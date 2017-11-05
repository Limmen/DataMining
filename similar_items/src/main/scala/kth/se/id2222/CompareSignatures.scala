package kth.se.id2222

/*
 * A class CompareSignatures that estimates
 * similarity of two integer vectors –
 * minhash signatures – as a fraction of
 * components, in which they agree.
 */
object CompareSignatures {

  def apply(sign1: Vector[Int], sign2: Vector[Int]): Double = {
    assert(sign1.length == sign2.length)
    var i = 0
    var rowMatch = 0
    for(i <- (0 to sign1.length-1)){
      if(sign1(i) == sign2(i))
        rowMatch += 1
    }
    rowMatch.toDouble/sign1.length.toDouble
  }

}
