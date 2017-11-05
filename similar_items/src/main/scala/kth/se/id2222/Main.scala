package kth.se.id2222

import com.madhukaraphatak.sizeof.SizeEstimator
import scala.collection.immutable.SortedSet
import scala.util.Random
/*
 * You are to implement the stages of finding textually similar
 * documents based on Jaccard similarity using the shingling,
 * minhashing, and locality-sensitive hashing (LSH)
 * techniques and corresponding algorithms.
 */
object Main {
  val random = new Random()
  val k = 10 //Shingle length
  val largePrime = 15485863 //High prime
  val a = 3 //factor
  val hashFun = (s: String) => s.hashCode //String hashfun for compression, dont care about buckets.
  val n = 100 //MinhashSignatureLength
  val t = 0.8 //threshold
  val InputDatasetPath = "src/resources/mini_newsgroups"

  def main(args: Array[String]) {
    val start = System.nanoTime()
    val dataset = Dataset(InputDatasetPath)
    val data = readInputDataSet(dataset).map {
      case (publisher, articles) =>
        (publisher, dataset.readFiles(articles))
    }
    val shingledData: Array[(String, List[(String, SortedSet[Int])])] = data.map {
      case (publisher, docs) =>
        (publisher, docs.map(doc => Shingling(k, hashFun, doc)))
    }
    println(s"Shingles size: ${SizeEstimator.estimate(shingledData)} bytes")
    val signatures = MinHashing(n, shingledData)
    println(s"Size after minhashing: ${SizeEstimator.estimate(signatures)} bytes")
    println(s"Number of candidates pre LSH is approx: ${signatures.size ^ 2}")
    //val signatures = shingledData.map((doc: SortedSet[Int]) => MinHashing.minHash(n, doc))
    //    val candidates = LSH.candidates(signatures, t)
    //    val similarItems = candidates.toList.filter((pair: Pair[Vector[Int], Vector[Int]]) => CompareSignatures.compare(pair._1, pair._2) >= t)
    //    val end = System.nanoTime()
    //    evaluate(similarItems, end - start)

  }

  /**
   * Reads input folders with publishers and documents and converts to publisher list of document strings
   *
   * @param dataset dataset utils
   * @return an array of (publisherName, List(doc1,doc2,doc3...))
   */
  def readInputDataSet(dataset: Dataset): Array[(String, List[String])] = {
    val dirs = dataset.getListOfSubDirectories
    for {
      dir <- dirs
    } yield dir.split("/").last -> dataset.getListOfFiles(dir).map(_.toString)
  }

  def evaluate(similarItems: List[(Vector[Int], Vector[Int])], elapsedTime: Long): Unit = {
    println(s"Time to compute similar items: $elapsedTime, number of similar items found: ${similarItems.length}")
  }

/**
  * Generates random hash function for row permutation for minhashing.
  * Uses the universal hashing scheme from the coursebook.
  * Universal hashing: h(x) = ((a*x + b) mod p) mod N where a,b are random integers,
  * p is a large prime number > N, and N is the number of buckets.
  *
  */
  def universalHashing(n: Int) = {
    val a = random.nextInt(1000)
    val b = random.nextInt(1000)
    (x: Int) => ((a * x + b) % largePrime) % n
  }

}
