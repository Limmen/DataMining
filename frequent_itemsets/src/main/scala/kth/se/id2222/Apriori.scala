package kth.se.id2222

import DataUtils._

object Apriori {

  /**
   * Count items (use triangular matrix or triple table)
   */
  def firstPass[A](baskets: List[Basket[A]]): List[(Set[Item[A]], Int)] = {
    val allItems = baskets.flatMap(x => Set(x.items)).distinct
    val hm = support(allItems, baskets)
    allItems.map{ item =>
      (item, hm(item))
    }
  }

  /**
   * Count k-sets of items that are frequent only.
   * Migh use DataUtils.generateAllSetsOfSizeK(k, frequentItems) and
   * DataUtils.support(items, baskets)
   */
  def kthPass[A](
    k: Int,
    supportThreshold: Int,
    countedItemSets: List[(Set[Item[A]], Int)],
    baskets: List[Basket[A]]
  ): List[(Set[Item[A]], Int)] = {
    val allSingletons = countedItemSets.flatMap(x => x._1).toSet
    val setOfSets = generateAllSetsOfSizeK(k, allSingletons)
    val hm = support(setOfSets, baskets)
    setOfSets.map { set =>
      (set, hm(set))
    }
    //for {
    //  itemSet <- setOfSets
    //} yield (itemSet, support(itemSet, baskets))
  }

}