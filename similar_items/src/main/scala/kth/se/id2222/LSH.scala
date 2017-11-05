package kth.se.id2222

import scala.collection.mutable.{HashMap, Set, MultiMap}


/*
 * A class LSH that implements the LSH technique:
 * given a collection of minhash signatures (integer vectors)
 * and a similarity threshold t, the LSH class (using banding and hashing)
 * finds all candidate pairs of signatures that agree on
 * at least fraction t of their components.
 */
object LSH {

  def apply(signatures: List[(String, Vector[Int])], r: Int, b: Int): Set[((String, Vector[Int]), (String, Vector[Int]))] = {
    assert(r*b == signatures(0)._2.length)
    val buckets = new HashMap[Int, Set[(String, Vector[Int])]] with MultiMap[Int, (String, Vector[Int])]
    signatures.foreach((sign) => {
      val bands = sign._2.sliding(r, 1).toList
      bands.foreach(b => buckets.addBinding(b.hashCode, sign))
    })
    buckets.foldLeft(Set[((String, Vector[Int]), (String, Vector[Int]))]())((acc, bucket) => {
      val pairs = for(x <- bucket._2.toList; y <- bucket._2.toList if(!x.equals(y))) yield (x, y)
      val pairs2 = pairs.foldLeft(Set[((String, Vector[Int]), (String, Vector[Int]))]())((acc, pair) => {
        if(!acc.contains((pair)) && !acc.contains((pair._2, pair._1)))
          acc + pair
        else
          acc
      })
      acc ++ pairs2.toSet
    })
  }

}
