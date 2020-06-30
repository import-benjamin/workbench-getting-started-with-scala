package calculus

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestComplex extends AnyFlatSpec with should.Matchers {
  "Complex" should "return Exponential form" in {
    val c: Complex = new Complex(math.sqrt(2)/2, math.sqrt(2)/2)
    c.getExp shouldBe "1.0*exp(i*0.7853981633974483)"
  }
  it should "compute modulus correctly" in {
    val c: Complex = new Complex(math.sqrt(2)/2, math.sqrt(2)/2)
    c.getModulus shouldBe 1.0
  }

  it should "compute argument correctly" in {
    val c: Complex = new Complex(math.sqrt(2)/2, math.sqrt(2)/2)
    c.getArgument shouldBe 0.785 +- 0.001d
  }
}
