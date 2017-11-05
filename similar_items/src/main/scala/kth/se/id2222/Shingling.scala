package kth.se.id2222

import  scala.collection.JavaConversions
import com.google.common.base.Splitter

import scala.collection.immutable.SortedSet
import scala.collection.mutable.HashMap
/*
 * A class Shingling that constructs k–shingles of a given length k (e.g., 10)
 * from a given document, computes a hash value for each unique shingle, and
 * represents the document in the form of an ordered set of its hashed k-shingles.
 */
object Shingling {

  //Table to lookup String value given the compressed hash
  val lookupTable = HashMap.empty[Int, String]

  def apply(k: Int, hashFun: (String) => Int, document: String): (String, SortedSet[Int]) = {
    val shingles =  JavaConversions.iterableAsScalaIterable(Splitter.fixedLength(k).split(document)).toList
    val hashes = shingles.map(shingle => {lookupTable += (hashFun(shingle) -> shingle); hashFun(shingle)})
    (document, SortedSet(hashes: _*))

  }
}
