package generator

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.tagobjects.Slow

class TestStringCombination extends AnyFlatSpec with Matchers {
  "String combination" should "return all possible combination possible" taggedAs Slow in {
    val results = stringCombination.generate(1 to 2)
    results should have size 20
  }
}

