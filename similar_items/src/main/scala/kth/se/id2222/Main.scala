package kth.se.id2222

import com.madhukaraphatak.sizeof.SizeEstimator
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
  val b = 10 //bands
  val t = 0.8 //threshold : (1/b)^(1/r)
  val InputDatasetPath = "src/resources/mini_newsgroups"

  /**
   * Program entrypoint, assembles the different steps of finding similar items into a pipeline:
   * readData -> shingleData -> minhashData -> LSH-to-find-candidates -> filterCandidates -> printResults
   */
  def main(args: Array[String]) {
    val start = System.nanoTime()
    val dataset = Dataset(InputDatasetPath)
    val data = readInputDataSet(dataset).map {
      case (publisher, articles) =>
        (publisher, dataset.readFiles(articles))
    }
    val shingledData = data.map {
      case (publisher, docs) =>
        (publisher, docs.map(doc => Shingling(k, hashFun, doc)))
    }
    println(s"Shingles size: ${SizeEstimator.estimate(shingledData)} bytes")
    val signatures: List[(String, Vector[Int])] = MinHashing(n, shingledData)
    println(s"Size after minhashing: ${SizeEstimator.estimate(signatures)} bytes")
    println(s"Number of candidates pre LSH is approx: ${Math.pow(signatures.size.toDouble, 2)}")
    val candidates = LSH(signatures, n / b, b)
    println(s"Number of candidates after LSH: ${candidates.size}")
    println(s"Size after LSH: ${SizeEstimator.estimate(candidates)} bytes")
    val similarItems = candidates.toList.map((pair) => (pair._1, pair._2, CompareSignatures(pair._1._2, pair._2._2))).filter((triple) => triple._3 >= t)
    println(s"Similar items: ${similarItems.size}")
    val end = System.nanoTime()
    val elapsedTime = end - start
    val elapsedSeconds = elapsedTime / 1000000000.0
    evaluate(similarItems, elapsedSeconds.toDouble)
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

  /**
   * Some final evaluation statistics
   */
  def evaluate(similarItems: List[((String, Vector[Int]), (String, Vector[Int]), Double)], elapsedTime: Double): Unit = {
    similarItems.foreach((pair) => println(s"Similar pair: \n ${pair._1._1}, \n ${pair._2._1} \n similarity: ${pair._3}"))
    println(s"Time to compute similar items: ${elapsedTime} seconds, number of similar items found: ${similarItems.length}")
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
