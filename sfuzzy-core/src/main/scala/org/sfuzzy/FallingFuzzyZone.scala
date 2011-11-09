package org.sfuzzy

object FallingFuzzyZone {

  def apply(n: String, min: Double, max: Double) = new FallingFuzzyZone(n, min, max)

}

class FallingFuzzyZone private (n: String, min: Double, max: Double) extends FuzzyZone(n, min, max) {

  def fuzzify(v: Double): Fuzzy = {
    if (v <= from) Fuzzy.MAX
    else if (v >= to) Fuzzy.MIN
    else Fuzzy((to - v) / (to - from))
  }
  
  def defuzzify(f: Fuzzy): Double = from * f.doubleValue()

}