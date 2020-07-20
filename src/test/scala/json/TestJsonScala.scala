package json

import org.json4s.DefaultFormats
import org.json4s.JsonDSL._
import org.json4s.JsonAST.{JField, JInt, JObject, JString}
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

case class Person(name: String, age: Int)

class TestJsonScala extends AnyWordSpec with should.Matchers{

  implicit val formats: DefaultFormats.type = DefaultFormats
  "Json" when {
    "creating" should {
      val json: JObject =
        "Key" -> (
          ("attribute1" -> 0) ~
            ("attribute2" -> 1) ~
            ("attribute3" -> 2)
          )
      "provide syntax to access child" in {
        val res = json \ "Key" \ "attribute2"
        res.extract[Int] shouldBe 1
      }
    }

    "manipulating" should {
      val jsonValue: JObject = ("name" -> "john") ~ ("age" -> 10)
      "be convertible to scala case class" in {
        val john = jsonValue.extract[Person]
        john shouldBe Person("john", 10)
      }
    }

    "deleting" should {
      val jsonValue: JObject = ("name" -> "sarah") ~ ("age" -> 10)
      "be able to remove one entry" in {
        val sarah = (jsonValue removeField { _ == JField("age", JInt(10))})
        sarah.extractOpt[Person] shouldBe None
      }
      "remove null entry" in {
        val sarah = (jsonValue transformField {
          case JField("age", JInt(age)) => ("age", None: Option[Int])
        })
        sarah.extractOpt[Person] shouldBe None
      }
    }

    "updating" should {
      val jsonValue: JObject = ("name" -> "jane") ~ ("age" -> "10")
      "edit existing field" in {
        val jane = (jsonValue transformField {
          case JField("age", JString(s)) => ("age", JInt(s.toInt))
        }).extract[Person]
        jane shouldBe Person("jane", 10)
      }
    }
  }
}
