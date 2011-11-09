package org.sfuzzy.examples
import org.sfuzzy.Fuzzy

abstract class Controller protected (minout: Double, maxout: Double, ct: ControllerType.Value) {

  require(maxout > minout)

  val controllerType = ct

  var setpoint: Double = 0.0

  var output: Double = 0.0

  val minOutput: Double = minout

  val maxOutput: Double = maxout

  def control(input: Double): Double
  
 }

object ControllerType extends Enumeration {
  val POSITION, VELOCITY = Value
}
