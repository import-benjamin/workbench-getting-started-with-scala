package pattern

import java.nio.file.Paths

import org.scalatest.wordspec.AnyWordSpec

class TestGlobPattern extends AnyWordSpec {

  "Given a specific pattern" when {
    val pattern = "somefile@src/main/scala/**/*.scala"
    val patternType, patternGlob = pattern.split('@')

    "The type specification" should {}
    "A glob pattern" should {
      "match case as good as other tools" in {

        val fileInstance = Paths.get(".")
        val files = fileInstance.resolve(patternGlob.mkString)
        println(files)
      }
    }
  }
}
