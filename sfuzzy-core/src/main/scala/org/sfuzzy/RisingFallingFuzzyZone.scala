package org.sfuzzy

object RisingFallingFuzzyZone {

  def apply(n: String, min: Double, max: Double) = new RisingFallingFuzzyZone(n, min, max)

  def apply(n: String, min: Double, max: Double, pk: Double) = new RisingFallingFuzzyZone(n, min, max, pk)
}

class RisingFallingFuzzyZone private (n: String, min: Double, max: Double, pk: Double) extends FuzzyZone(n, min, max) {

  require(pk >= from && pk <= to)

  val peak: Double = pk

  def this(n: String, min: Double, max: Double) = this(n, min, max, FuzzyZone.mid(min, max))

  override def toString(): String = { name + "(" + from + ".." + peak + ".." + to + ")" }

  def fuzzify(v: Double): Fuzzy = {
    if (v <= from) Fuzzy.MIN
    else if (v >= to) Fuzzy.MIN
    else if (v == peak) Fuzzy.MAX
    else if (v < peak) Fuzzy((v - from) / (peak - from))
    else Fuzzy((v - peak) / (to - peak)).not()
  }

  def defuzzify(f: Fuzzy): Double = peak * f.doubleValue()
}

