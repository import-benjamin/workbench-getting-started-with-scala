import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestScala extends AnyFlatSpec with should.Matchers {
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
    val h = f(10)(_:Int)(_:Int)
    val i = f(10)(20)(_:Int)
    val j = f(10)(20)(30)
    j shouldBe 60
  }

  it should "be able to pass function as argument" in {
    def f(x:Int)(op:(Int)=>Int):Int = op(x)
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
}