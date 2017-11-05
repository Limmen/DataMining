package kth.se.id2222

/*
 * A class LSH that implements the LSH technique:
 * given a collection of minhash signatures (integer vectors)
 * and a similarity threshold t, the LSH class (using banding and hashing)
 * finds all candidate pairs of signatures that agree on
 * at least fraction t of their components.
 */
object LSH {

  def apply(signatures: List[Vector[Int]], t: Double, r: Int, b: Int): Set[(Vector[Int], Vector[Int])] = ???


}
