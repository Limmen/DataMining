package kth.se.id2222

import scala.collection.immutable.SortedSet

/*
 * A class CompareSets that estimates
 * the Jaccard similarity of two sets of integers
 * – two sets of hashed shingles.
 */
object CompareSets {

  def apply(set1: SortedSet[Int], set2: SortedSet[Int]): Double = {
    val intersect = set1.intersect(set2).size
    val union = set1.union(set2).size
    intersect/union
  }
}
