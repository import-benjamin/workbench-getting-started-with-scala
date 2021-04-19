
package pattern

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class TestRegexPattern extends AnyFlatSpec with Matchers {

  "A numeric regex" should "match numeric values" in {
    val regex = "[0-9]+".r
    val result = regex.findAllIn(Array("abcd", "0935", "ab76").mkString)
    result.map(println)
  }
}

