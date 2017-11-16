package kth.se.id2222

import scala.collection.mutable.HashMap

/*
 * You are to solve the first sub-problem: to implement the Apriori algorithm
 * for finding frequent itemsets with support at least s in a dataset of sales transactions.
 * Remind that support of an itemset is the number of transactions containing the itemset.
 * To test and evaluate your implementation, write a program that uses your Apriori algorithm
 * implementation to discover frequent itemsets with support at least s in a
 * given dataset of sales transactions.
 */
object Main {

  val dataPath = "src/resources/T10I4D100K.dat"
  val supportThreshold: Int = 1000
  val confidenceThreshold = 0.5
  val k = 4
  val frequentItemSets = new HashMap[Int, List[(Set[Item[Int]], Int)]]()
  val assocRules = new HashMap[Int, List[AssociationRule[Int]]]()

  /*
   * Progam entrypoint, otchestrates the procesisng pipeline of finding similar itemsets and
   * association rules
   */
  def main(cmdlineArgs: Array[String]): Unit = {
    val baskets: List[Basket[Int]] = DataUtils.readData(dataPath)
    println("Counting all singletons for " + baskets.size + " baskets")
    val uniqueItems = baskets.map(b => b.items).toSeq.flatten.toSet.size
    println(s"Total unique items to count: $uniqueItems")
    frequentItemSets += (1 -> filterItemSets(Apriori.firstPass(baskets), supportThreshold))
    println("Number of frequent singletons " + frequentItemSets(1).size)
    println("Filtering out baskets with no frequent itemsets..")
    var iterBaskets = filterBaskets(baskets, frequentItemSets(1))
    for (i <- 2 to k) {
      val prev = frequentItemSets(i - 1)
      println("Processing frequent items for " + i + "-sets, approximately " + Math.pow(prev.length, 2) / 2 + " sets to check and " + iterBaskets.size + " baskets")
      frequentItemSets += (i -> filterItemSets(Apriori.kthPass(i, supportThreshold, prev, iterBaskets), supportThreshold))
      println("Filtering out baskets with no frequent itemsets..")
      iterBaskets = filterBaskets(iterBaskets, frequentItemSets(i))
      println("Finding association rules for " + i + "-sets")
      assocRules += (i -> AssocRules.findAllRules(supportThreshold, confidenceThreshold, prev, frequentItemSets(i), baskets))
    }
    println("Done. Evaluating")
    evaluate(baskets)
  }

  /*
   * Print results
   */
  def evaluate(baskets: List[Basket[Int]]): Unit = {
    for (i <- (1 to k)) {
      println(s"Number Frequent Items of length $i: ${frequentItemSets(i).length}")
      //frequentItemSets(i).foreach(println)
      if (i > 1) {
        println(s"Number of association rules for itemsets length: $i: ${assocRules(i).length}")
        assocRules(i).foreach((ar) => println(s"Association Rule: $ar, confidence: ${AssocRules.confidence(ar, baskets)}, interest: ${AssocRules.interest(ar, baskets)}"))
      }
    }
  }

  /*
   * Filter itemsets for given support threshold s
   */
  def filterItemSets[A](itemsets: List[(Set[Item[A]], Int)], s: Int): List[(Set[Item[A]], Int)] = {
    itemsets.filter((p) => p._2 >= s)
  }

  /*
   * Filter basket not necessary to count anymore
   */
  def filterBaskets[A](baskets: List[Basket[A]], frequentItems: List[(Set[Item[A]], Int)]): List[Basket[A]] = {
    baskets.filter(b => {
      var subsetTrue = false
      var i = 0
      while (!subsetTrue && i < frequentItems.size) {
        if (frequentItems(i)._1.subsetOf(b.items))
          subsetTrue = true
        i += 1
      }
      subsetTrue
    })
  }
}
