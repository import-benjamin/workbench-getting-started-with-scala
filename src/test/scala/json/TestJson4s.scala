package json

import org.json4s.{DefaultFormats, JArray, JObject, JsonAST}
import org.json4s.native.JsonMethods
import org.json4s.native.JsonMethods.{pretty, render}
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec


class TestJson4s extends AnyWordSpec with should.Matchers {
  implicit val formats: DefaultFormats.type = DefaultFormats

  "A JArray" when {
    "inside a JObject" should {
      val document = JsonMethods.parse(
        """
          |{
          | "document": {
          |   "content": [
          |     {
          |       "id": "hello",
          |       "content": "some_content"
          |      },
          |      {
          |       "id": "here",
          |       "content": "some_data"
          |      },
          |   ]
          | }
          |}
          |""".stripMargin)
      "Be convertible into multiples JObject" in {
        val res = document transformField {
          case ("content", JArray(s)) =>
            val list_it: List[(String, JsonAST.JObject)] = for (JObject(i) <- s) yield ((JObject(i) \ "id").extract[String], JObject(i))
            ("content", JObject(list_it))
        }
        println(pretty(render(res)))
      }
    }
  }
}
