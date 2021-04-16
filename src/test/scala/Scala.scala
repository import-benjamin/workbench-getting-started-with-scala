import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class Scala extends AnyFlatSpec with Matchers {
  "Functions" should "allow no arguments" in {
    def myFunction(): Double = math.random()

    myFunction() shouldBe a[Double]
  }

  it should "allow one argument" in {
    def myFunction(value: Integer): Integer = value * value

    myFunction(2) shouldBe 4
  }

  it should "allow multiples arguments" in {
    def myFunction(value1: Integer, value2: Integer, value3: Integer): Integer = value1 + value2 + value3

    myFunction(1, 2, 3) shouldBe 6
  }

  it should "allow arguments curring" in {
    def myFunction(pre: String)(value: String): String = s"$pre, $value"

    val partialFunction = myFunction("Hello")(_: String)

    partialFunction("world") shouldBe "Hello, world"
    myFunction("Bonjour")("monde") shouldBe "Bonjour, monde"
  }

  it should "allow partial function" in {
    def f(x: Int)(op: Int => Int): Int = op(x)

    val res = f(10)(_ + 1) // or f(10)(x=>x+1)
    res shouldBe 11
  }

  "Lambdas" should "require at least one argument" in {
    val myLambda: Int => Int = _ + 2

    myLambda(2) shouldBe 4
  }

  it should "allow multiples parameters" in {
    val modulus = (x: Double, y: Double) => math.sqrt(math.pow(x, 2) + math.pow(y, 2))

    modulus(math.sqrt(2) / 2, math.sqrt(2) / 2) shouldBe 1
  }

  it should "allow curring" in {
    val g = (x: Int) => (y: Int) => (z: Int) => x + y + z

    val h = g(10)
    val i = h(20) // or g(10)(20)
    val j = i(30) // or h(20)(30) or g(10)(20)(10)
    j shouldBe 60
  }

  "Implicit" should "allow class extension" in {
    implicit class RichString(x: String) {
      def more: String = x + ", extends this string"
    }
    ("Hello, world" more) should endWith
    ", extends this string"
  }

  it should "allow function to do implicit conversions" in {
    import scala.language.implicitConversions
    case class Person(name: String) {
      def greet = s"Hello, $name"
    }

    implicit def stringToPerson(string: String): Person = Person(string)
    // "Tom".greet is now equivalent to stringToPerson("Tom").greet
    "Tom".greet shouldBe "Hello, Tom"
  }

  it should "pass variables to function with implicit arguments" in {
    def greet(name: String)(implicit greeting: String): String = s"$greeting, $name"

    implicit val greeting: String = "Hello"

    greet("Tom") shouldBe "Hello, Tom"
  }


  "Pattern matching" should "allow to match type" in {
    case class A(x: Int)

    def switch(x: Any): Int = x match {
      case A(x: Int) => x
      case _ => 0
    }

    val a = A(4)
    val b = "should return 0"

    assert(switch(a) == 4)
    assert(switch(b) == 0)
  }
  it should "allow to match value" in {
    def switch(x: Int): String = x match {
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case _ => "Hello, there"
    }

    assert(switch(1) == "one")
    assert(switch(2) == "two")
    assert(switch(3) == "three")
    assert(switch(4) == "Hello, there")
  }
  it should "allow cons cell function" in {
    val y: Seq[Int] = 1 :: 2 :: 3 :: Nil
    // _.toList == val y = List(1, 2, 3)

    def multiply(list: List[Int]): Int = list match {
      case Nil => 1
      case n :: rest => n * multiply(rest)
    }

    assert(multiply(y.toList) == 6)
  }

  "Binary" should "increment" in {
    var b: Int = 65
    assert(b.toBinaryString == "1000001")
    b += 1
    assert(b.toBinaryString == "1000010")
    b += 10
    assert(b.toBinaryString == "1001100")
  }

  "Array of int using max" should "return the highest value" in {
    val arr = Array(2, 3, 4, 0, 10, 23, 4)
    assert(arr.max == 23)
  }

  "loop" should "run through all combinations given the number of iterator" in {
    val loop_result: Seq[(Int, Int)] = for (i <- 0 until 3; j <- 0 until 3) yield (i, j)
    // 0,1,2 all combinations give us a vector of 9 entries
    assert(loop_result.size == 9)
  }

  "String" should "have count methode to return the number of a given char" in {
    assert("coucou les copains".count(_ == 'c') == 3)
  }
  it should "be convertible to Int" in {
    assert("100".toInt == 100)
  }
  it should "be convertible to Double" in {
    assert("100".toDouble == 100.0)
  }

  "File" should "be accessible in resources folder" in {
    import org.json4s.JValue
    import org.json4s.JsonAST.JString
    import org.json4s.jackson.JsonMethods.parse

    import scala.io.Source
    val rawFile: Iterator[String] = Source.fromResource("file.json").getLines()
    val jsonFile: JValue = parse(rawFile.mkString)

    assert(jsonFile \ "project" \ "source" == JString("scala"))
  }

  it should "be accessible in in working directory" in {
    import scala.io.Source
    val file = Source.fromFile("README.md")
    val rawFile: Seq[String] = file.getLines().toList
    file.close()

    rawFile should contain ("# :alembic: Getting started with scala")
  }

  "Glob pattern" should "match readme in working directory" in {
    import java.io.File
    val files = new File(".").listFiles().filter(_.isFile).filter(s => ".*md$".r.matches(s.getName))
    assert(files.length == 1)
  }

  "Scalatest" should "allow to handle exceptions" in {
    @throws(classOf[Exception])
    def throw_function(): Unit = throw new Exception

    assertThrows[Exception](throw_function())
  }
}

