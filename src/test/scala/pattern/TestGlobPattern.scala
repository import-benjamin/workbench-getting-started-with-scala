
package pattern

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Paths

class TestGlobPattern extends AnyFlatSpec with Matchers{

  "Glob pattern" should "match valid files" in {
    val pattern = "src/main/scala/**/*.scala"
    val dir = Paths.get(".")
    val files = dir.getFileSystem.getPathMatcher("glob:"+pattern)
    println(files)
  }
}

