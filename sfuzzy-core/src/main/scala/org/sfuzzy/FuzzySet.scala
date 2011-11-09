package org.sfuzzy
import scala.collection.immutable.Map

object FuzzySet {

  def apply(n: String, zl: List[FuzzyZone]) = new FuzzySet(n, zl)

}

class FuzzySet private (n: String, zl: List[FuzzyZone]) {

  val name = n

  val zones = zl

  def fuzzify(v: Double): Map[String, Fuzzy] = {
    val fuzzies = scala.collection.mutable.Map[String, Fuzzy]()
    for (z <- zones) {
      fuzzies(z.name) = z.fuzzify(v)
    }
    Map(fuzzies.toSeq: _*)
  }

  def fuzzies(): scala.collection.mutable.Map[String, Fuzzy] = {
    val fuzzies = scala.collection.mutable.Map[String, Fuzzy]()
    for (z <- zones) {
      fuzzies(z.name) = Fuzzy.MIN
    }
    fuzzies
  }

  def defuzzify(fv: Map[String, Fuzzy]): Double = {
    var dividend = 0.0
    var divisor = 0.0
    for (z <- zones) {
      val fuzzy = fv(z.name)
      dividend += z.defuzzify(fuzzy)
      divisor += fuzzy.doubleValue()
    }
    if (divisor != 0.0) dividend / divisor else 0.0
  }

}