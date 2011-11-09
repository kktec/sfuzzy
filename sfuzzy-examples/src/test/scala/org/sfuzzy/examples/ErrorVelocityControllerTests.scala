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
class ErrorVelocityControllerTests extends Specification with JUnit {

  var err = FuzzySet("err", List(
    FallingFuzzyZone("NL", -10.0, -5.0),
    RisingFallingFuzzyZone("NS", -10.0, 0.0),
    RisingFallingFuzzyZone("ZE", -5.0, 5.0),
    RisingFallingFuzzyZone("PS", 0.0, 10.0),
    RisingFuzzyZone("PL", 5.0, 10.0)))

  var out = FuzzySet("out", List(
    FallingFuzzyZone("NL", -5.0, -2.5),
    RisingFallingFuzzyZone("NS", -5.0, 0.0),
    RisingFallingFuzzyZone("ZE", -2.5, 2.5),
    RisingFallingFuzzyZone("PS", 0.0, 5.0),
    RisingFuzzyZone("PL", 2.5, 5.0)))

  var controller = ErrorController(err, out, 0.0, 100.0, ControllerType.VELOCITY)

  "ErrorVelocityController" should {

    "have default values for setpoint and output" in {
      var controller = ErrorController(err, out, ControllerType.VELOCITY)
      controller.setpoint must_== 0.0
      controller.output must_== 0.0
      controller.minOutput must_== 0.0
      controller.maxOutput must_== 100.0
    }

    "have an output range with min > max" in {
      var controller = ErrorController(err, out, -1.0, 1.0, ControllerType.VELOCITY)
      controller.minOutput must_== -1.0
      controller.maxOutput must_== 1.0
      ErrorController(err, out, 1.0, -1.0, ControllerType.VELOCITY) must throwA[IllegalArgumentException]
    }

    "with error NL" in {
      initSP_OP()
      controller.control(40.0) must_== 55.0
      controller.control(40.0) must_== 60.0
      controller.control(40.0) must_== 65.0
      controller.control(40.0) must_== 70.0
      controller.control(40.0) must_== 75.0
      controller.control(40.0) must_== 80.0
      controller.control(40.0) must_== 85.0
      controller.control(40.0) must_== 90.0
      controller.control(40.0) must_== 95.0
      controller.control(40.0) must_== 100.0
      controller.control(40.0) must_== 100.0
    }

    "with error NL_NS" in {
      initSP_OP()
      controller.control(42.5) must_== 53.75
      controller.control(42.5) must_== 57.5
    }

      "with error NS" in {
      initSP_OP()
      controller.control(45.0) must_== 52.5
      controller.control(45.0) must_== 55.0
    }

    "with error NS_ZE" in {
      initSP_OP()
      controller.control(47.5) must_== 51.25
      controller.control(47.5) must_== 52.5
    }

    "with error ZE" in {
      initSP_OP()
      controller.control(50.0) must_== 50.0
      controller.control(50.0) must_== 50.0
    }

    "with error ZE_PS" in {
      initSP_OP()
      controller.control(52.5) must_== 48.75
      controller.control(52.5) must_== 47.5
    }

    "with error PS" in {
      initSP_OP()
      controller.control(55.0) must_== 47.5
      controller.control(55.0) must_== 45.0
    }

    "with error PS_PL" in {
      initSP_OP()
      controller.control(57.5) must_== 46.25
      controller.control(57.5) must_== 42.5
    }


    "with error PL" in {
      initSP_OP()
      controller.control(60.0) must_== 45.0
      controller.control(60.0) must_== 40.0
      controller.control(60.0) must_== 35.0
      controller.control(60.0) must_== 30.0
      controller.control(60.0) must_== 25.0
      controller.control(60.0) must_== 20.0
      controller.control(60.0) must_== 15.0
      controller.control(60.0) must_== 10.0
      controller.control(60.0) must_== 5.0
      controller.control(60.0) must_== 0.0
      controller.control(60.0) must_== 0.0
    }

}

  def initSP_OP() = {
    controller.setpoint = 50.0
    controller.output = 50.0
  }
}