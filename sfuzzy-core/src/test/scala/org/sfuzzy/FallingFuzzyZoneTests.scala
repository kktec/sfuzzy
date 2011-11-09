package org.sfuzzy

import org.specs._
import org.junit.runner._
import org.specs.runner.JUnit
import org.specs.runner.JUnitSuiteRunner
import org.junit.Before

@RunWith(classOf[JUnitSuiteRunner])
class FallingFuzzyZoneTests extends FuzzyZoneTestCase {

    var z: FallingFuzzyZone = null
    
    "FallingFuzzyZone" should {
      doBefore { setUp() }
    
    "as a String" in {
      zone.toString() must_== "zone(-50.0..150.0)"
    }
    
    "fuzzify" in {
      z.fuzzify(-50.1) must_== Fuzzy.MAX
      z.fuzzify(-50.0) must_== Fuzzy.MAX
      z.fuzzify(0.0) must_== Fuzzy(0.75)
      z.fuzzify(50.0) must_== Fuzzy(0.5)
      z.fuzzify(100.0) must_== Fuzzy(0.25)
      z.fuzzify(150.0) must_== Fuzzy.MIN
      z.fuzzify(150.1) must_== Fuzzy.MIN
    }
    
    "defuzzify" in {
      z.defuzzify(Fuzzy.MAX) must_== -50.0
      z.defuzzify(Fuzzy(0.5)) must_== -25.0
      z.defuzzify(Fuzzy.MIN) must_== 0.0
    }
    
    }


  override def setUp() = {
    super.setUp()
    z = zone.asInstanceOf[FallingFuzzyZone]
  }

  def create(n: String, min: Double, max: Double) = FallingFuzzyZone(n, min, max)

}