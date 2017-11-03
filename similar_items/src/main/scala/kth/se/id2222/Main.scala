package kth.se.id2222

import scala.collection.immutable.SortedSet
/*
 * You are to implement the stages of finding textually similar
 * documents based on Jaccard similarity using the shingling,
 * minhashing, and locality-sensitive hashing (LSH)
 * techniques and corresponding algorithms.
 */
object Main {
  val k = 10 //Shingle length
  val c = 9973 //High prime
  val a = 4 //factor
  val hashFun = (s: String) => s.toInt * a % c //Simple hashfun
  val n = 100 //MinhashSignatureLength
  val t = 0.8 //threshold

  def main(args: Array[String]) {
    val start = System.nanoTime()
    val data = readInputDataSet()
    val shingledData = data.map((doc: List[String]) => Shingling.shingling(k, hashFun, doc))
    val signatures = shingledData.map((doc: SortedSet[Int]) => MinHashing.minHash(n, doc))
    val candidates = LSH.candidates(signatures, t)
    val similarItems = candidates.toList.filter((pair: Pair[Vector[Int], Vector[Int]]) => CompareSignatures.compare(pair._1, pair._2) >= t)
    val end = System.nanoTime()
    evaluate(similarItems, end - start)

  }

  def readInputDataSet(): List[List[String]] = ???

  def evaluate(similarItems: List[Pair[Vector[Int], Vector[Int]]], elapsedTime: Long): Unit = ???
}
