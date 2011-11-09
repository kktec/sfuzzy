package org.sfuzzy

import org.specs._
import org.junit.runner._
import org.specs.runner.JUnit
import org.specs.runner.JUnitSuiteRunner
import org.junit.Before

@RunWith(classOf[JUnitSuiteRunner])
class RisingFallingFuzzyZoneTests extends FuzzyZoneTestCase {

  var z: RisingFallingFuzzyZone = null

  "RisingFallingFuzzyZone" should {
    doBefore { setUp() }

    "should have a default peak the same as mid" in {
      z.peak must_== zone.mid
    }

    "allow peak to be specified explicitly if contained" in {
      RisingFallingFuzzyZone(zone.name, zone.from, zone.to, 0.0).peak must_== 0.0
      RisingFallingFuzzyZone(zone.name, zone.from, zone.to, -50.1) must throwA[IllegalArgumentException]
      RisingFallingFuzzyZone(zone.name, zone.from, zone.to, 150.1) must throwA[IllegalArgumentException]
    }

    "as a String" in {
      zone.toString() must_== "zone(-50.0..50.0..150.0)"
    }
    
    "fuzzify a bipolar asymmetric zone" in {
      z = RisingFallingFuzzyZone("test", -50.0, 150.0, 0.0)
      z.fuzzify(-50.1) must_== Fuzzy.MIN
      z.fuzzify(-50.0) must_== Fuzzy.MIN
      z.fuzzify(-37.5) must_== Fuzzy(0.25)
      z.fuzzify(-25.0) must_== Fuzzy(0.5)
      z.fuzzify(-12.5) must_== Fuzzy(0.75)
      z.fuzzify(0.0) must_== Fuzzy.MAX
      z.fuzzify(37.5) must_== Fuzzy(0.75)
      z.fuzzify(75.0) must_== Fuzzy(0.5)
      z.fuzzify(112.5) must_== Fuzzy(0.25)
      z.fuzzify(150.0) must_== Fuzzy.MIN
      z.fuzzify(150.1) must_== Fuzzy.MIN
    }

  
    "fuzzify a bipolar symmetric zone" in {
      z = RisingFallingFuzzyZone("test", -50.0, 50.0)
      z.fuzzify(-50.1) must_== Fuzzy.MIN
      z.fuzzify(-50.0) must_== Fuzzy.MIN
      z.fuzzify(-37.5) must_== Fuzzy(0.25)
      z.fuzzify(-25.0) must_== Fuzzy(0.5)
      z.fuzzify(-12.5) must_== Fuzzy(0.75)
      z.fuzzify(0.0) must_== Fuzzy.MAX
      z.fuzzify(12.5) must_== Fuzzy(0.75)
      z.fuzzify(25.0) must_== Fuzzy(0.5)
      z.fuzzify(37.5) must_== Fuzzy(0.25)
      z.fuzzify(50.0) must_== Fuzzy.MIN
      z.fuzzify(50.1) must_== Fuzzy.MIN
    }

    "fuzzify a unipolar asymmetric zone" in {
      z = RisingFallingFuzzyZone("test", 0.0, 10.0, 2.0)
      z.fuzzify(-0.1) must_== Fuzzy.MIN
      z.fuzzify(0.0) must_== Fuzzy.MIN
      z.fuzzify(0.5) must_== Fuzzy(0.25)
      z.fuzzify(1.0) must_== Fuzzy(0.5)
      z.fuzzify(1.5) must_== Fuzzy(0.75)
      z.fuzzify(2.0) must_== Fuzzy.MAX
      z.fuzzify(4.0) must_== Fuzzy(0.75)
      z.fuzzify(6.0) must_== Fuzzy(0.5)
      z.fuzzify(8.0) must_== Fuzzy(0.25)
      z.fuzzify(10.0) must_== Fuzzy.MIN
      z.fuzzify(10.1) must_== Fuzzy.MIN
    }

    "fuzzify a unipolar symmetric zone" in {
      z = RisingFallingFuzzyZone("test", 0.0, 10.0, 2.0)
      z.fuzzify(-0.1) must_== Fuzzy.MIN
      z.fuzzify(0.0) must_== Fuzzy.MIN
      z.fuzzify(0.5) must_== Fuzzy(0.25)
      z.fuzzify(1.0) must_== Fuzzy(0.5)
      z.fuzzify(1.5) must_== Fuzzy(0.75)
      z.fuzzify(2.0) must_== Fuzzy.MAX
      z.fuzzify(4.0) must_== Fuzzy(0.75)
      z.fuzzify(6.0) must_== Fuzzy(0.5)
      z.fuzzify(8.0) must_== Fuzzy(0.25)
      z.fuzzify(10.0) must_== Fuzzy.MIN
      z.fuzzify(10.1) must_== Fuzzy.MIN
    }
    
    "defuzzify a symmetric unipolar zone" in {
      z = RisingFallingFuzzyZone("test", 10.0, 20.0)
      z.defuzzify(Fuzzy.MIN) must_== 0.0
      z.defuzzify(Fuzzy(0.25)) must_== 3.75
      z.defuzzify(Fuzzy(0.5)) must_== 7.5
      z.defuzzify(Fuzzy(0.75)) must_== 11.25
      z.defuzzify(Fuzzy.MAX) must_== 15.0
    }

    "defuzzify a unipolar asymemtric zone" in {
      z = RisingFallingFuzzyZone("test", 10.0, 20.0, 12.5)
      z.defuzzify(Fuzzy.MIN) must_== 0.0
      z.defuzzify(Fuzzy(0.25)) must_== 3.125
      z.defuzzify(Fuzzy(0.5)) must_== 6.25
      z.defuzzify(Fuzzy(0.75)) must_== 9.375
      z.defuzzify(Fuzzy.MAX) must_== 12.5
    }

    "defuzzify a symmetric bipolar zone" in {
      z = RisingFallingFuzzyZone("test", -10.0, 10.0)
      z.defuzzify(Fuzzy.MIN) must_== 0.0
      z.defuzzify(Fuzzy(0.25)) must_== 0.0
      z.defuzzify(Fuzzy(0.5)) must_== 0.0
      z.defuzzify(Fuzzy(0.75)) must_== 0.0
      z.defuzzify(Fuzzy.MAX) must_== 0.0
    }

    "defuzzify a symmetric bipolar zone" in {
      z = RisingFallingFuzzyZone("test", -10.0, 10.0, -5.0)
      z.defuzzify(Fuzzy.MIN) must_== 0.0
      z.defuzzify(Fuzzy(0.25)) must_== -1.25
      z.defuzzify(Fuzzy(0.5)) must_== -2.5
      z.defuzzify(Fuzzy(0.75)) must_== -3.75
      z.defuzzify(Fuzzy.MAX) must_== -5.0
    }

  }
  
 override def setUp() = {
    super.setUp()
    z = zone.asInstanceOf[RisingFallingFuzzyZone]
  }

  def create(n: String, min: Double, max: Double) = RisingFallingFuzzyZone(n, min, max)

}