import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.language.postfixOps

class TestScala extends AnyFlatSpec with should.Matchers {
  "Function" should "work" in {
    def add2(x: Int): Int = x+2
    add2(2) shouldBe 4
  }

  it should "allow multiples parameters" in {
    def modulus(x:Double, y:Double): Double = math.sqrt(math.pow(x, 2)+math.pow(y, 2))
    modulus(math.sqrt(2)/2, math.sqrt(2)/2) shouldBe 1
  }

  "Lambda" should "work" in {
    val add2: Int => Int = _ + 2
    add2(2) shouldBe 4
  }

  it should "allow multiples parameters" in {
    val modulus = (x: Double, y: Double) => math.sqrt(math.pow(x,2)+math.pow(y,2))
    modulus(math.sqrt(2)/2, math.sqrt(2)/2) shouldBe 1
  }

  "Curring" should "work with lambda" in {
    // with lambda
    val g = (x:Int) => (y:Int) => (z:Int) => x+y+z

    val h = g(10)
    val i = h(20) // or g(10)(20)
    val j = i(30) // or h(20)(30) or g(10)(20)(10)
    j shouldBe 60
  }

  it should "work with function" in {
    // with function
    def f(x:Int)(y:Int)(z:Int) = x+y+z

    // function curring is more strict since you must specify parameters types
    // val h = f(10)(_:Int)(_:Int)
    // val i = f(10)(20)(_:Int)
    val j = f(10)(20)(30)
    j shouldBe 60
  }

  it should "be able to pass function as argument" in {
    def f(x:Int)(op: Int =>Int):Int = op(x)
    val res = f(10)(_+1) // or f(10)(x=>x+1)
    res shouldBe 11
  }

  "Implicit class" should "allow us to add function to extends class" in {
    implicit class RichString(x: String) {
      def more: String = x + ", extends this string"
    }
    ("Hello, world" more) should endWith (", extends this string")
  }

  "Case class" should "ease class creation" in {
    case class A(x: Int, y: Int)
    val a = A(5, 10)
    a.x shouldBe 5
    a.y shouldBe 10
  }

  "Pattern matching" should "be able to match type" in {
    case class A(x:Int)

    def switch(x: Any): Int = x match {
      case A(x: Int) => x
      case _ => 0
    }

    val a = A(4)
    val b = "should return 0"

    switch(a) shouldBe 4
    switch(b) shouldBe 0
  }

  it should "be able to match value" in {
    def switch(x: Int): String = x match {
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case _ => "Hello, there"
    }

    switch(1) shouldBe "one"
    switch(2) shouldBe "two"
    switch(3) shouldBe "three"
    switch(4) shouldBe "Hello, there"
  }

  "Binary" should "be able to increment" in {
    var b: Int = 65
    b.toBinaryString shouldBe "1000001"
    b += 1
    b.toBinaryString shouldBe "1000010"
    b += 10
    b.toBinaryString shouldBe "1001100"
  }

  "Json" should "be easy to manage" in {
    import org.json4s.JsonDSL._
    import org.json4s._
    implicit val formats: DefaultFormats.type = DefaultFormats

    val json: JObject =
      "Key" -> (
        ("attribute1" -> 0) ~
        ("attribute2" -> 1) ~
        ("attribute3" -> 2)
        )
    val res = json \ "Key" \ "attribute2"
    res.extract[Int] shouldBe 1
  }

  "Array of int" should "return the highest value when using max" in {
    val arr = Array( 2, 3, 4, 0, 10, 23, 4 )
    arr.max shouldBe 23
  }

  "Case class" should "allow quick class declaration" in {
    case class Student(name: String, age: Int)
    val someStudent: Student = Student("Jean", 24)
    someStudent.name shouldBe "Jean"
  }

  "for loop" should "run through all combinations given the number of iterator" in {
    val loop_result: Seq[(Int, Int)] = for (i <- 0 until 3; j <- 0 until 3) yield (i, j)
    // 0,1,2 all combinations give us a vector of 9 entries
    loop_result.size shouldBe 9
  }
}
