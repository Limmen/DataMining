package kth.se.id2222.spark

import scala.io.Source


object DataUtils {

  def readData(path: String): List[Basket[Int]] = {
    Source.fromFile(path).getLines.toList.map((line)=> Basket[Int](line.split(" ").map(item => Item[Int](item.toInt)).toSet))
  }

  def support[A](items: Set[Item[A]], baskets: List[Basket[A]]) : Int = {
    baskets.count((basket) => items.subsetOf(basket.items))
  }

  def generateAllSetsOfSizeK[A](k: Int, items: Set[Item[A]]) : List[Set[Item[A]]]= {
    items.subsets(k).toList
  }
}

case class Item[A](item: A)

case class Basket[A](items: Set[Item[A]])
