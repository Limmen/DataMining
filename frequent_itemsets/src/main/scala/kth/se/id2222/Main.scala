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
  val supportThreshold: Int = 5
  val confidenceThreshold = 0.5
  val k = 4
  val frequentItemSets = new HashMap[Int,List[(Set[Item[Int]], Int)]]()
  val assocRules = new HashMap[Int, List[AssociationRule[Int]]]()

  def main(args: Array[String]) {

    val baskets = DataUtils.readData(dataPath)
    frequentItemSets += (1 -> filterItemSets(Apriori.firstPass(baskets), supportThreshold))
    val countedItems = Apriori.firstPass(baskets)
    for(i <- (2 to k)){
      frequentItemSets += (i -> Apriori.kthPass(i, supportThreshold, frequentItemSets(i-1)))
      assocRules += (i -> AssocRules.findAllRules(supportThreshold, confidenceThreshold, frequentItemSets(i)))
    }
  }


  /*
   * Print results
   */
  def evaluate(baskets: List[Basket[Int]]) : Unit = {
    for(i <- (1 to k)) {
      println(s"Number Frequent Items of length $i: ${frequentItemSets(i).length}")
      frequentItemSets(i).foreach((p) => println(s"Item: ${p._1}, threshold: ${p._2}"))
      if(i > 1){
        println(s"Number of association rules for itemsets length: $i: ${assocRules(i).length}")
        assocRules(i).foreach((ar) => println(s"Association Rule: $ar, confidence: ${AssocRules.confidence(ar, baskets)}, interest: ${AssocRules.interest(ar, baskets)}"))
      }
    }
  }

  /*
   * Filter itemsets for given support threshold s
   */
  def filterItemSets[A](itemsets: List[(Set[Item[A]], Int)], s : Int) : List[(Set[Item[A]], Int)] = {
    itemsets.filter((p) => p._2 >= s)
  }
}
