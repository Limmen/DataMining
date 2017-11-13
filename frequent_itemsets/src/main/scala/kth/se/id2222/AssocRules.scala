package kth.se.id2222

import DataUtils._

/**
 * Solve the second sub-problem,
 * i.e., develop and implement an algorithm for generating
 * association rules between frequent itemsets discovered by
 * using the Apriori algorithm in a dataset of sales transactions.
 * The rules must have support at least s and confidence at least c,
 * where s and c are given as input parameters.
 */
object AssocRules {

  /*
   * Confidence: Ratio of support for (I U j) over support for I
   */
  def confidence[A](rule: AssociationRule[A], baskets: List[Basket[A]]): Double = {
    support(rule.subject + rule.proposition, baskets).toDouble / DataUtils.support(rule.subject, baskets).toDouble
  }

  /*
   * Interest: difference between its confidence and the fraction of baskets that contain the
   * proposition of the rule.
   */
  def interest[A](rule: AssociationRule[A], baskets: List[Basket[A]]): Double = {
    confidence(rule, baskets) - support(Set(rule.proposition), baskets).toDouble / baskets.size.toDouble
  }

  /*
   * Find all rules with confidence over threshold
   */
  def findAllRules[A](supportThreshold: Int,
    confidenceThreshold: Double,
    countedItemSets1: List[(Set[Item[A]], Int)],
    countedItemSets2: List[(Set[Item[A]], Int)],
    baskets: List[Basket[A]]
  ): List[AssociationRule[A]] = {
    countedItemSets1.foldLeft(List[AssociationRule[A]]())((acc, setCount) => {
      if (setCount._2 >= supportThreshold) {
        countedItemSets2.foldLeft(acc)((acc2, setCount2) => {
          if (setCount._1.subsetOf(setCount2._1) && setCount2._2 > supportThreshold) {
            val rule = AssociationRule[A](setCount._1, (setCount2._1 diff setCount._1).head)
            if (confidence(rule, baskets) >= confidenceThreshold) {
              rule :: acc2
            } else
              acc2
          } else
            acc2
        })
      } else acc
    })
  }
}

case class AssociationRule[A](subject: Set[Item[A]], proposition: Item[A])
