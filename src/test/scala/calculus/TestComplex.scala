package calculus

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestComplex extends AnyFlatSpec with should.Matchers {
  "Complex" should "return Exponential form" in {
    val c: Complex = new Complex(1, 1)
    c.getExp shouldBe "1.4142135623730951*exp(i*0.7853981633974483)"
  }
  it should "compute modulus correctly" in {
    val c: Complex = new Complex(1, 1)
    c.getModulus shouldBe 1.414 +- 0.001d
  }

  it should "compute argument correctly" in {
    val c: Complex = new Complex(1, 1)
    c.getArgument shouldBe 0.785 +- 0.001d
  }
}
