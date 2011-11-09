package org.sfuzzy

object RisingFuzzyZone {

  def apply(n: String, min: Double, max: Double) = new RisingFuzzyZone(n, min, max)

}

class RisingFuzzyZone private (n: String, min: Double, max: Double) extends FuzzyZone(n, min, max) {

  def fuzzify(v: Double): Fuzzy = {
    if (v <= from) Fuzzy.MIN
    else if (v >= to) Fuzzy.MAX
    else Fuzzy((v - from) / (to - from))
  }
  
  def defuzzify(f: Fuzzy): Double = to * f.doubleValue()
}