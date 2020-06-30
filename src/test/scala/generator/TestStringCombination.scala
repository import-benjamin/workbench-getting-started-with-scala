package generator

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestStringCombination extends AnyFlatSpec with should.Matchers {
  "String combination" should "return all possible combination possible" in {
    val sc = new stringCombination
    val results = sc.generate(1 to 2)
    results should have size 20
  }
}
