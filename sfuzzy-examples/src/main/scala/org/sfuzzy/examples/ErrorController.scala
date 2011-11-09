package org.sfuzzy.examples

import org.sfuzzy.{FuzzySet, Fuzzy}


object ErrorController {

  def apply(err: FuzzySet, out: FuzzySet, minout: Double, maxout: Double, ct: ControllerType.Value) = new ErrorController(err, out, minout, maxout, ct)

  def apply(err: FuzzySet, out: FuzzySet, ct: ControllerType.Value) = new ErrorController(err, out, 0.0, 100.0, ct)

}

class ErrorController private (err: FuzzySet, out: FuzzySet, minout: Double, maxout: Double, ct: ControllerType.Value) extends Controller(minout, maxout, ct) {
  
  def control(input: Double) : Double =  {
    val error = input - setpoint
    val errors = err.fuzzify(error)
    val outputs = out.fuzzies()
    val inference = infer(errors, outputs)
    val result = defuzzify(inference)
    output = calculateOutput(result)
    output
  }
  
  private def infer(errors: Map[String, Fuzzy], outputs: scala.collection.mutable.Map[String, Fuzzy]) : Map[String, Fuzzy] = {
    outputs("PL") |= errors("NL")
    outputs("PS") |= errors("NS")
    outputs("ZE") |= errors("ZE")
    outputs("NS") |= errors("PS")
    outputs("NL") |= errors("PL")
    Map(outputs.toSeq: _*)
  }
  
  protected final def defuzzify(inference: Map[String, Fuzzy]) : Double = {
    out.defuzzify(inference)
  }
  
  protected def calculateOutput(result: Double) : Double = {
    var op = result
    if (controllerType == ControllerType.VELOCITY) op += output
    if (op < minOutput) { op = minOutput }
    else if (op > maxOutput) op = maxOutput
    op
  }
  
}

