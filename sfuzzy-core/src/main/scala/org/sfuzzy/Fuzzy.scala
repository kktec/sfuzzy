package org.sfuzzy

object Fuzzy {
  
  private val MIN_VALUE = 0.0
  
  private val MAX_VALUE = 1.0
  
  val MIN = new Fuzzy(MIN_VALUE)

  val MAX = new Fuzzy(MAX_VALUE)

  def apply() = MIN

  def apply(v: Number) = new Fuzzy(v)
}

class Fuzzy private (v: Number) extends Number {

  require(v.doubleValue() >= Fuzzy.MIN_VALUE && v.doubleValue() <= Fuzzy.MAX_VALUE)

  val value: Double = v.doubleValue()

  def intValue() = value.intValue()

  def longValue() = value.longValue()

  def floatValue() = value.floatValue()

  def doubleValue() = value.doubleValue()

  override def toString(): String = value.toString()

  override def equals(other: Any): Boolean =
    other match {
      case that: Fuzzy =>
        (that canEqual this) &&
          value == that.doubleValue()

      case _ => false
    }

  override def hashCode(): Int = value.hashCode()

  def canEqual(other: Any): Boolean = other.isInstanceOf[Fuzzy]

  def &(other: Fuzzy): Fuzzy = if (other.doubleValue() < value) other else this

  def |(other: Fuzzy): Fuzzy = if (other.doubleValue() > value) other else this
  
  def unary_! = Fuzzy(Fuzzy.MAX_VALUE - value)
    
}

