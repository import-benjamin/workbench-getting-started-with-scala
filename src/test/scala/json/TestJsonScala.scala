package json

import org.json4s.DefaultFormats
import org.json4s.JsonDSL._
import org.json4s.JsonAST.{JField, JInt, JNothing, JObject, JString, JValue}
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

case class Person(name: String, age: Int)

class TestJsonScala extends AnyWordSpec with should.Matchers {

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

      "using transform operator" in {
        val res = jsonValue transformField {
          case ("name", JString(s)) => ("name", JString(s.toUpperCase))
        }
        (res \ "name") shouldBe JString("JOHN")
      }

      "allow to flatten" in {
        val json: JObject =
          "Key" -> (
            "opt" -> (
              ("attribute1" -> 0) ~
                ("attribute2" -> 1) ~
                ("attribute3" -> 2)
              ))
        val res = json transformField {
          case JField("Key", attr) => JField("opt", attr \ "opt")
        }
        (res \ "opt" \ "attribute1") shouldBe JInt(0)
      }
    }

    "deleting" should {
      val jsonValue: JObject = ("name" -> "sarah") ~ ("age" -> 10)
      val jsonValue2: JObject = ("options" -> (
        ("name", "opt1") ~
          ("name", "opt2") ~
          ("name", "opt3")
        )
        ) ~ ("name", "filter")

      "be able to remove one entry" in {
        val sarah = jsonValue removeField {
          _ == JField("age", JInt(10))
        }
        sarah.extractOpt[Person] shouldBe None
      }

      "remove JArray" in {
        val result = jsonValue2 removeField {
          case ("options", _) => true
          case _ => false
        }
        (result \ "options") shouldBe JNothing
      }

      "remove null entry" in {
        val sarah = jsonValue transformField {
          case JField("age", JInt(_)) => ("age", None: Option[Int])
        }
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

  "Json4s" when {
    "parsing with case class" should {
      "allow extends without adding or editing existing field" in {
        import org.json4s.native.JsonMethods.parse
        val some_file: JValue = parse(
          """
            |{
            | "report": {
            |   "trees": {
            |     "tree": {
            |       "name": "oak",
            |       "size": "5m"
            |     }
            |   }
            | }
            |}
            |""".stripMargin)
        val some_file_two: JValue = parse(
          """
            |{
            | "report": {
            |   "tree": {
            |     "name": "birch",
            |     "size": "4.3m"
            |   }
            | }
            |}
            |""".stripMargin)
      }
    }
  }
}
