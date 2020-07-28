import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

import scala.io.BufferedSource
import scala.language.postfixOps

class TestScala extends AnyWordSpec with should.Matchers {
  "Function" when {
    "having one argument" should {
      "work" in {
        def add2(x: Int): Int = x+2
        add2(2) shouldBe 4
      }
    }
    "having multiples parameters" should {
      "work" in {
        def modulus(x:Double, y:Double): Double = math.sqrt(math.pow(x, 2)+math.pow(y, 2))
        modulus(math.sqrt(2)/2, math.sqrt(2)/2) shouldBe 1
      }
    }
    "using curring" should {
      "work" in {
        // with function
        def f(x:Int)(y:Int)(z:Int): Int = x+y+z

        // function curring is more strict since you must specify parameters types
        // val h = f(10)(_:Int)(_:Int)
        // val i = f(10)(20)(_:Int)
        val j = f(10)(20)(30)
        j shouldBe 60
      }
      "allow partial function parameter" in {
        def f(x:Int)(op: Int =>Int):Int = op(x)
        val res = f(10)(_+1) // or f(10)(x=>x+1)
        res shouldBe 11
      }
    }
  }

  "Lambda" when {
    "having one parameter" should {
      "work" in {
        val add2: Int => Int = _ + 2
        add2(2) shouldBe 4
      }
    }
    "having multiples parameters" should {
      "work" in {
        val modulus = (x: Double, y: Double) => math.sqrt(math.pow(x,2)+math.pow(y,2))
        modulus(math.sqrt(2)/2, math.sqrt(2)/2) shouldBe 1
      }
    }
    "using curring" when {
      "passing incomplete parameters" should {
          "return unit" in {
            // with lambda
            val g = (x:Int) => (y:Int) => (z:Int) => x+y+z

            val h = g(10)
            val i = h(20) // or g(10)(20)
            val j = i(30) // or h(20)(30) or g(10)(20)(10)
            j shouldBe 60
          }
      }
    }
  }

  "Implicit class" when {
    "contains function" should {
      "extends existing class" in {
        implicit class RichString(x: String) {
          def more: String = x + ", extends this string"
        }
        ("Hello, world" more) should endWith (", extends this string")
      }
    }
  }

  "Case class" when {
    "defined" should {
      "provide class with public attribute" in {
        case class A(x: Int, y: Int)
        val a = A(5, 10)
        a.x shouldBe 5
        a.y shouldBe 10
      }
      "allow quick class declaration" in {
        case class Student(name: String, age: Int)
        val someStudent: Student = Student("Jean", 24)
        someStudent.name shouldBe "Jean"
      }
    }
    "used in pattern matching" should {
      "allow to classify type" in {
        trait Animal
        case class Dog(name: String) extends Animal
        case class Cat(name: String) extends Animal
        case object Woodpecker extends Animal

        def matcher(v: Animal) = v match {
          case Dog(name) => "Dog:"+name
          case Cat(name) => "Cat:"+name
          case Woodpecker => "Woodpecker"
          case _ => "Other"
        }

        matcher(Cat("samantha")) shouldBe "Cat:samantha"
        matcher(Dog("Alfred")) shouldBe "Dog:Alfred"
      }
    }
  }

  "Pattern matching" when {
    "used" should {
      "allow to match type" in {
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
      "allow to match value" in {
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
      "allow cons cell function" in {
        val y: Seq[Int] = 1 :: 2 :: 3 :: Nil
        // _.toList == val y = List(1, 2, 3)

        def multiply(list: List[Int]): Int = list match {
          case Nil => 1
          case n :: rest => n * multiply(rest)
        }
        multiply(y.toList) shouldBe 6
      }
    }
  }

  "Binary" should {
    "increment" in {
      var b: Int = 65
      b.toBinaryString shouldBe "1000001"
      b += 1
      b.toBinaryString shouldBe "1000010"
      b += 10
      b.toBinaryString shouldBe "1001100"
    }
  }

  "Array of int" when {
    "using max" should {
      "return the highest value" in {
        val arr = Array(2, 3, 4, 0, 10, 23, 4)
        arr.max shouldBe 23
      }
    }
  }

  "loop" when {
    "using for" should {
      "run through all combinations given the number of iterator" in {
        val loop_result: Seq[(Int, Int)] = for (i <- 0 until 3; j <- 0 until 3) yield (i, j)
        // 0,1,2 all combinations give us a vector of 9 entries
        loop_result.size shouldBe 9
      }
    }
  }

  "String" when {
    "using count function" should {
      "return the number of a given char" in {
        "coucou les copains".count(_ == 'c') shouldBe 3
      }
    }
    "convert to Int" should {
      "return its value" in {
        "100".toInt shouldBe 100
      }
    }
    "convert to Double" should {
      "return its value" in {
        "100".toDouble shouldBe 100.0
      }
    }
  }

  "File" when {
    "in ressource folder" should {
      "be accessible" in {
        import org.json4s.JValue
        import org.json4s.JsonAST.JString
        import org.json4s.native.JsonMethods.parse
        import scala.io.Source
        val rawFile: Iterator[String] = Source.fromResource("file.json").getLines()
        val jsonFile: JValue = parse(rawFile.mkString)

        jsonFile \ "project" \ "source" shouldBe JString("scala")
      }
    }
    "in working directory" should {
      "be accessible" in {
        import scala.io.Source
        val file = Source.fromFile("README.md")
        val rawFile: Seq[String] = file.getLines().toList
        file.close()

        rawFile should contain ("# :alembic: Getting started with scala")
      }
    }
  }

  "Glob pattern" when {
    "matching markdown file" should {
      "match readme in working directory" in {
        import java.io.File
        val files = new File(".").listFiles().filter(_.isFile).filter(s => ".*md$".r.matches(s.getName))
        files.length shouldBe 1
      }
    }
  }

  "Scalatest" when {
    "Exception occur" should{
      "be able to handle it" in {
        @throws(classOf[Exception])
        def throw_function(): Unit = throw new Exception
        assertThrows[Exception](throw_function())
      }
    }
  }
}
