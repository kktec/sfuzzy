package org.sfuzzy

object FuzzyZone {
  def mid(to: Double, from: Double) = {
    (to - from) / 2.0 + from
  }
}

abstract class FuzzyZone protected (n: String, min: Double, max: Double) {

  require(max >= min)

  val name = n
  val from = min
  val to = max

  def mid() = FuzzyZone.mid(from, to)

  def contains(v: Double): Boolean = if (v < from || v > to) false else true

  def fuzzify(v: Double): Fuzzy

  def defuzzify(f: Fuzzy): Double

  override def toString(): String = { name + "(" + from + ".." + to + ")" }

}