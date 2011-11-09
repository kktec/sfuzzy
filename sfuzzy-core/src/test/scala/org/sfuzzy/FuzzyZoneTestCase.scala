package org.sfuzzy

import org.specs._
import org.specs.matcher._
import org.junit.runner._
import org.specs.runner.JUnit
import org.specs.runner.JUnitSuiteRunner
import org.junit.Before

@RunWith(classOf[JUnitSuiteRunner])
abstract class FuzzyZoneTestCase extends Specification with JUnit {
  
	val zoneName = "zone"
    
    var zone : FuzzyZone = null
    
    def create(n: String, min: Double, max: Double) : FuzzyZone
    
    def setUp() = { zone = create(zoneName, -50.0, 150.0) }
	
	"all FuzzyZones" should {
	  setUp
	  
	  "set name, from, and to" in {
	    zone.name must_== zoneName
	    zone.from must_== -50.0
	    zone.to must_== 150.0
	  }
	  
	  "to cannot be less than from" in {
	    create(zoneName, 0.0, -0.01) must throwA[IllegalArgumentException]
	    create(zoneName, 0.0, 0.0)
	  }
	  
	  "zone mid is halfway between from and to" in {
	    zone.mid must_== 50.0
	  }
	  
	  "zone is a container of from..to" in {
	    zone.contains(-50.1) must_== false 
	    zone.contains(-50.0) must_== true
	    zone.contains(150.0) must_== true
	    zone.contains(150.1) must_== false
	  }
	}

}