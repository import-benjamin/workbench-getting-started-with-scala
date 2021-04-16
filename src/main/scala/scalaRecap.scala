
// https://learnxinyminutes.com/docs/fr-fr/scala-fr/

/**
 * In this recap will list the very basic of scala features
 *
 * Most of theses features exists in src/test/scala/TestScala
 */
object scalaRecap extends App {

  // In scala type is defined as 'theValue: TheType'
  val immutableBool: Boolean = false
  var mutableBool: Boolean = true


  // def is used to declare function(): theType
  def functionDefinition(argument: Int): Int = {
    argument + 1 // last operation doesn't need a return statement
  }

  // Function that return nothing use the type Unit
  // Braces may be optional in function declaration
  def printIntegerFunction(value: Integer): Unit = println(value)

  // class declaration https://docs.scala-lang.org/tour/classes.html
  // Does not declare constructor implicitly
  class Animal(name: String)

  // Case classes are classes that have extra functionality built in.
  // The values in these classes tend to be private, and only methods are exposed.
  // The primary purpose of case classes is to hold immutable data.
  case class CaseAnimal(name: String)

  val cat = new Animal("puff")
  // when using case class, new is optional
  val dog = CaseAnimal("fluff")

  // cat.name is not available because there is no getter
  println(dog.name)

  // Methods notation

}
