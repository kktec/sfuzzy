package org.sfuzzy

import org.specs._
import org.junit.runner._
import org.specs.runner.JUnit
import org.specs.runner.JUnitSuiteRunner
import scala.collection.immutable.ListMap

@RunWith(classOf[JUnitSuiteRunner])
class FuzzySetTests extends Specification with JUnit {

  "Fuzzy Set" should {

    "with 1 zone" in {
      val rfZone = RisingFallingFuzzyZone("Z", -10.0, 10.0)
      val set = FuzzySet("set", List(rfZone))

      set.name must_== "set"
      set.zones.size must_== 1
      set.zones(0) must_== rfZone

      "fuzzify at min" in {
        val fuzzies = set.fuzzify(-10.0)
        fuzzies.size must_== 1
        fuzzies("Z") must_== Fuzzy.MIN
      }

      "fuzzify at mid" in {
        val fuzzies = set.fuzzify(0.0)
        fuzzies.size must_== 1
        fuzzies("Z") must_== Fuzzy.MAX
      }

      "fuzzify at max" in {
        val fuzzies = set.fuzzify(10.0)
        fuzzies.size must_== 1
        fuzzies("Z") must_== Fuzzy.MIN
      }
    }

    "with 2 zones" in {
      val fZone = FallingFuzzyZone("N", -20.0, 20.0)
      val rZone = RisingFuzzyZone("P", -20.0, 20.0)
      val set = FuzzySet("set", List(fZone, rZone))

      set.name must_== "set"
      set.zones.size must_== 2
      set.zones(0) must_== fZone
      set.zones(1) must_== rZone

      "fuzzify at min" in {
        val fuzzies = set.fuzzify(-20.0)
        fuzzies.size must_== 2
        fuzzies("N") must_== Fuzzy.MAX
        fuzzies("P") must_== Fuzzy.MIN
      }

      "fuzzify at mid" in {
        val fuzzies = set.fuzzify(0.0)
        fuzzies.size must_== 2
        fuzzies("N") must_== Fuzzy(0.5)
        fuzzies("P") must_== Fuzzy(0.5)
      }

      "fuzzify at max" in {
        val fuzzies = set.fuzzify(20.0)
        fuzzies.size must_== 2
        fuzzies("N") must_== Fuzzy.MIN
        fuzzies("P") must_== Fuzzy.MAX
      }
    }

    "with 3 zones" in {
      val fZone = FallingFuzzyZone("N", -20.0, 0.0)
      val rfZone = RisingFallingFuzzyZone("Z", -20.0, 20.0)
      val rZone = RisingFuzzyZone("P", 0.0, 20.0)
      val set = FuzzySet("set", List(fZone, rfZone, rZone))

      set.name must_== "set"
      set.zones.size must_== 3
      set.zones(0) must_== fZone
      set.zones(1) must_== rfZone
      set.zones(2) must_== rZone

      "fuzzify at min" in {
        val fuzzies = set.fuzzify(-20.0)
        fuzzies.size must_== 3
        fuzzies("N") must_== Fuzzy.MAX
        fuzzies("Z") must_== Fuzzy.MIN
        fuzzies("P") must_== Fuzzy.MIN
      }

      "fuzzify at min_mid" in {
        val fuzzies = set.fuzzify(-10.0)
        fuzzies.size must_== 3
        fuzzies("N") must_== Fuzzy(0.5)
        fuzzies("Z") must_== Fuzzy(0.5)
        fuzzies("P") must_== Fuzzy.MIN
      }

      "fuzzify at mid" in {
        val fuzzies = set.fuzzify(0.0)
        fuzzies.size must_== 3
        fuzzies("N") must_== Fuzzy.MIN
        fuzzies("Z") must_== Fuzzy.MAX
        fuzzies("P") must_== Fuzzy.MIN
      }

      "fuzzify at mid_max" in {
        val fuzzies = set.fuzzify(10.0)
        fuzzies.size must_== 3
        fuzzies("N") must_== Fuzzy.MIN
        fuzzies("Z") must_== Fuzzy(0.5)
        fuzzies("P") must_== Fuzzy(0.5)
      }

      "fuzzify at max" in {
        val fuzzies = set.fuzzify(20.0)
        fuzzies.size must_== 3
        fuzzies("N") must_== Fuzzy.MIN
        fuzzies("Z") must_== Fuzzy.MIN
        fuzzies("P") must_== Fuzzy.MAX
      }
      
      "fuzzies" in {
        val fuzzies = set.fuzzies()
        fuzzies.size must_== 3
        fuzzies("N") must_== Fuzzy.MIN
        fuzzies("Z") must_== Fuzzy.MIN
        fuzzies("P") must_== Fuzzy.MIN
      }
      
      "defuzzify" in {
        set.defuzzify(Map("N" -> Fuzzy.MAX, "Z" -> Fuzzy.MIN, "P" -> Fuzzy.MIN)) must_== -20.0
        set.defuzzify(Map("N" -> Fuzzy.MIN, "Z" -> Fuzzy.MAX, "P" -> Fuzzy.MIN)) must_== 0.0
        set.defuzzify(Map("N" -> Fuzzy.MIN, "Z" -> Fuzzy.MIN, "P" -> Fuzzy.MAX)) must_== 20.0
        set.defuzzify(Map("N" -> Fuzzy.MIN, "Z" -> Fuzzy.MIN, "P" -> Fuzzy.MIN)) must_== 0.0
      }
    }

  }
}