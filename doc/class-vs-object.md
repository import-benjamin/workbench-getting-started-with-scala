# Class versus object in Scala

Scala doesn't have static methods or fields. Instead, you should use object. 

- `class C` defines a class, just as in Java or C++.
- `object O` creates a singleton object `O` as instance of some anonymous class; it can be used to hold static members that are not associated with instances of some class.
- `object O extends T` makes the o`bject O` an instance of `trait T`; you can then pass `O` anywhere, a `T` is expected.
- if there is a `class C`, then `object C` is the [companion object](https://docs.scala-lang.org/overviews/scala-book/companion-objects.html) of `class C`; note that the companion object is __not__ automatically an instance of `C`.
  _The companion class and its companion object both must be defined in the same source file._

### object as host of static members

Most often, you need an `object` to hold methods and values/variables that shall be available without having to first instantiate an instance of some class. This use is closely related to `static` members in Java.

```scala
object A {
  def twice(i: Int): Int = 2*i
}
```

You can then call above method using `A.twice(2)`.
If `twice` were a member of some `class A`, then you would need to make an instance first:

```scala
class A() {
  def twice(i: Int): Int = 2 * i
}

val a = new A()
a.twice(2)
```

You can see how redundant this is, as `twice` does not require any instance-specific data.

### object as a special named instance

You can also use the `object` itself as some special instance of a class or trait. When you do this, your object needs to extend some `trait` in order to become an instance of a subclass of it.

Consider the following code:

```scala 3
object A extends B with C {
  ...
}
```

This declaration first declares an anonymous (inaccessible) class extending both B and C, and instantiates a single instance of this class named `A`.

This means `A` can be passed to functions expecting objects of type `B` or `C`, or `B with C`.

### Object as companion

To demonstrate how this feature works, here’s a class named Person along with an apply method in its companion object:

```scala 3
class Person {
    var name = ""
}

object Person {
    def apply(name: String): Person = {
        var p = new Person
        p.name = name
        p
    }
}
```

Now you can create a new instance of the Person class like this:

```scala 3
val p = Person("Fred Flinstone")
```

and this:

```scala 3
val zenMasters = List(
    Person("Nansen"),
    Person("Joshu")
)
```

That code directly calls apply in the companion object like this:

```scala 3
val p = Person.apply("Fred Flinstone")
```

### Additional Features of object

There also exist some special features of objects in Scala. I recommend to read the official documentation.

- `def apply(...)` enables the usual method name-less syntax of `A(...)`
- `def unapply(...)` allows to create custom pattern matching extractors
- if accompanying a class of the same name, the object assumes a special role when resolving implicit parameters

#### Creating multiples constructor 

You can create multiple `apply` methods in a companion object to provide multiple constructors. The following code shows how to create both one- and two-argument constructors. This example also shows how to use `Option` in a situation like this:

```scala
class Person {
    var name: Option[String] = None
    var age: Option[Int] = None
    override def toString = s"$name, $age"
}

object Person {

    // a one-arg constructor
    def apply(name: Option[String]): Person = {
        var p = new Person
        p.name = name
        p
    }

    // a two-arg constructor
    def apply(name: Option[String], age: Option[Int]): Person = {
        var p = new Person
        p.name = name
        p.age = age
        p
    }

}
```

If you paste that code into the REPL as before, you’ll see that you can create new `Person` instances like this:

```scala 3
val p1 = Person(Some("Fred"))
val p2 = Person(None)

val p3 = Person(Some("Wilma"), Some(33))
val p4 = Person(Some("Wilma"), None)
```

When you print those values you'll see these results:

```scala 3
val p1: Person = Some(Fred), None
val p2: Person = None, None
val p3: Person = Some(Wilma), Some(33)
val p4: Person = Some(Wilma), None
```

#### Adding an unapply method

Just as adding an `apply` method in a companion object lets you construct new object instances, adding an `unapply` lets you de-construct object instances. We’ll demonstrate this with an example.

Here’s a different version of a `Person` class, and a companion object:

```scala 3
class Person(var name: String, var age: Int)

object Person {
    def unapply(p: Person): String = s"${p.name}, ${p.age}"
}
```

Notice that the companion object defines an `unapply` method. That method takes an input parameter of the type `Person`, and returns a `String`. To test the `unapply` method manually, first create a new `Person` instance:

```scala 3
val p = new Person("Lori", 29)
```

Then test `unapply` like this:

```scala 3
val result = Person.unapply(p)
```

This is what the `unapply` result looks like in the REPL:

```scala 3
scala> val result = Person.unapply(p)
result: String = Lori, 29
```
As shown, `unapply` de-constructs the `Person` instance it’s given. In Scala, when you put an `unapply` method in a companion object, it’s said that you’ve created an extractor method, because you’ve created a way to extract the fields out of the object.

##### unapply extractors in the real world

A benefit of using `unapply` to create an extractor is that if you follow the proper Scala conventions, they enable a convenient form of pattern-matching in match expressions.