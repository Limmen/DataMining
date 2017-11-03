package kth.se.id2222

import scala.collection.immutable.{HashMap, SortedSet}

/*
 * A class Shingling that constructs k–shingles of a given length k (e.g., 10)
 * from a given document, computes a hash value for each unique shingle, and
 * represents the document in the form of an ordered set of its hashed k-shingles.
 */
object Shingling {

  //Table to lookup String value given the compressed hash
  val lookupTable = HashMap.empty[Int, String]

  def shingling(k: Int, hashFun: (String) => Int, document: List[String]): SortedSet[Int] = ???

}
