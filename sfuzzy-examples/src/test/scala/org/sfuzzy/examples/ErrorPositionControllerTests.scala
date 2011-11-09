package org.sfuzzy.examples

import org.specs._
import org.junit.runner._
import org.specs.runner.JUnit
import org.specs.runner.JUnitSuiteRunner
import org.sfuzzy.FuzzySet
import org.sfuzzy.FallingFuzzyZone
import org.sfuzzy.RisingFallingFuzzyZone
import org.sfuzzy.RisingFuzzyZone

@RunWith(classOf[JUnitSuiteRunner])
class ErrorPositionControllerTests extends Specification with JUnit {

  var err = FuzzySet("err", List(
    FallingFuzzyZone("NL", -10.0, -5.0),
    RisingFallingFuzzyZone("NS", -10.0, 0.0),
    RisingFallingFuzzyZone("ZE", -5.0, 5.0),
    RisingFallingFuzzyZone("PS", 0.0, 10.0),
    RisingFuzzyZone("PL", 5.0, 10.0)))

  var out = FuzzySet("out", List(
    FallingFuzzyZone("NL", 0.0, 25.0),
    RisingFallingFuzzyZone("NS", 0.0, 50.0),
    RisingFallingFuzzyZone("ZE", 25.0, 75.0),
    RisingFallingFuzzyZone("PS", 50.0, 100.0),
    RisingFuzzyZone("PL", 75.0, 100.0)))

  var controller = ErrorController(err, out, 0.0, 100.0, ControllerType.POSITION)

  "ErrorPositionController" should {

    "have default values for setpoint and output" in {
      var controller = ErrorController(err, out, ControllerType.POSITION)
      controller.setpoint must_== 0.0
      controller.output must_== 0.0
      controller.minOutput must_== 0.0
      controller.maxOutput must_== 100.0
    }

    "have an output range with min > max" in {
      var controller = ErrorController(err, out, -1.0, 1.0, ControllerType.POSITION)
      controller.minOutput must_== -1.0
      controller.maxOutput must_== 1.0
      ErrorController(err, out, 1.0, -1.0, ControllerType.POSITION) must throwA[IllegalArgumentException]
    }

    "with error NL" in {
      initSP_OP()
      controller.control(40.0) must_== 100.0
      controller.control(40.0) must_== 100.0
    }

    "with error NL_NS" in {
      initSP_OP()
      controller.control(42.5) must_== 87.5
      controller.control(42.5) must_== 87.5
    }

    "with error NS" in {
      initSP_OP()
      controller.control(45.0) must_== 75.0
      controller.control(45.0) must_== 75.0
    }

    "with error NS_ZE" in {
      initSP_OP()
      controller.control(47.5) must_== 62.5
      controller.control(47.5) must_== 62.5
    }

    "with error ZE" in {
      initSP_OP()
      controller.control(50.0) must_== 50.0
      controller.control(50.0) must_== 50.0
    }

    "with error ZE_PS" in {
      initSP_OP()
      controller.control(52.5) must_== 37.5
      controller.control(52.5) must_== 37.5
    }

    "with error PS" in {
      initSP_OP()
      controller.control(55.0) must_== 25.0
      controller.control(55.0) must_== 25.0
    }

    "with error PS_PL" in {
      initSP_OP()
      controller.control(57.5) must_== 12.5
      controller.control(57.5) must_== 12.5
    }

    "with error PL" in {
      initSP_OP()
      controller.control(60.0) must_== 0.0
      controller.control(60.0) must_== 0.0
    }

  }

  def initSP_OP() = {
    controller.setpoint = 50.0
    controller.output = 50.0
  }
}