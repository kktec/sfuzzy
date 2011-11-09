package org.sfuzzy

import org.specs._
import org.junit.runner._
import org.specs.runner.JUnit
import org.specs.runner.JUnitSuiteRunner

@RunWith(classOf[JUnitSuiteRunner])
class FuzzyTests extends Specification with JUnit {

  "Fuzzy" should {

    "Fuzzy Number values must equal value" in {
      val value = 0.25
      val fuzzy = Fuzzy(value)

      fuzzy.intValue() must_== value.intValue()
      fuzzy.longValue() must_== value.longValue()
      fuzzy.floatValue() must_== value.floatValue()
      fuzzy.doubleValue() must_== value
      fuzzy.toString() must_== value.toString()
    }

    "default Fuzzy must value is 0.0" in {
      val defaultFuzzy = Fuzzy()
      defaultFuzzy.doubleValue() must_== 0.0
    }

    "Equal Fuzzy values must be equal" in {
      Fuzzy(0.25) must_!= Fuzzy(0.75)
    }

    "Unequal Fuzzy values must not be equal" in {
      Fuzzy(0.25) must_!= Fuzzy(0.75)
    }

    "Fuzzy hashCode must be equal to value hashCode" in {
      Fuzzy(0.5).hashCode() must_== 0.5.hashCode()
    }

    "Fuzzy values must be between 0.0 and 1.0" in {
      Fuzzy(-0.001) must throwA[IllegalArgumentException]
      Fuzzy(0.0).doubleValue() must_== 0.0
      Fuzzy(0.5).doubleValue() must_== 0.5
      Fuzzy(1.0).doubleValue() must_== 1.0
      Fuzzy(1.001) must throwA[IllegalArgumentException]
    }

    "ANDing Fuzzies returns a new Fuzzy with the minimum value" in {
      val zero = Fuzzy.MIN
      val min = Fuzzy(0.24)
      val max = Fuzzy(0.26)
      var result = zero
      result = min & max
      result must_== min
      result &= zero
      result must_== zero
    }

    "ORing Fuzzies returns a new Fuzzy with the minimum value" in {
      val one = Fuzzy.MAX
      var result = Fuzzy()
      val min = Fuzzy(0.24)
      val max = Fuzzy(0.26)
      result = min | max
      result must_== max
      result |= one
      result must_== one
    }

    "NOTing Fuzzies returns a new Fuzzy with the 1.0 minus the value" in {
      val fuzzy = Fuzzy(0.24)
      !fuzzy must_== Fuzzy(0.76)
      fuzzy.not() must_== Fuzzy(0.76)
    }

  }

}